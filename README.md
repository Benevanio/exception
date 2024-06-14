# Next Reservation

Este projeto é um sistema simples de gerenciamento de reservas de quartos de hotel. Ele demonstra o uso dos princípios SOLID na refatoração de um código Java, focando especialmente no tratamento de erros e na separação de responsabilidades.

## Estrutura do Projeto

O projeto está dividido nas seguintes partes principais:

1. **Entities**: Contém a classe `Reservation` que representa uma reserva de quarto.
2. **Exceptions**: Contém a classe `DomainException` para tratamento de exceções específicas do domínio.
3. **Services**: Contém as interfaces e implementações para formatação de datas e leitura de entrada.
4. **App**: Classe principal que integra todos os componentes para executar a aplicação.

### Classes Principais

#### Reservation

A classe `Reservation` representa uma reserva de um quarto de hotel. Ela inclui métodos para obter e atualizar datas de check-in e check-out, bem como calcular a duração da estadia.

```java
package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import model.exceptions.DomainException;

public class Reservation {
    private Integer roomNumber;
    private Date checkIn;
    private Date checkOut;

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Reservation(Integer roomNumber, Date checkIn, Date checkOut) {
        if (!checkOut.after(checkIn)) {
            throw new DomainException("Check-out date must be after check-in date");
        }
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Integer getRoomNumber() {
        return this.roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getCheckIn() {
        return this.checkIn;
    }

    public Date getCheckOut() {
        return this.checkOut;
    }

    public long duration() {
        long diff = this.checkOut.getTime() - this.checkIn.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public void updateDates(Date checkIn, Date checkOut) throws DomainException {
        Date now = new Date();
        if (checkIn.before(now) || checkOut.before(now)) {
            throw new DomainException("Reservation dates for update must be future dates");
        }
        if (!checkOut.after(checkIn)) {
            throw new DomainException("Check-out date must be after check-in date");
        }
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Room " + this.roomNumber + ", check-in: " + sdf.format(this.checkIn) + ", check-out: "
                + sdf.format(this.checkOut) + ", " + this.duration() + " nights";
    }
}
```

#### DomainException

A classe `DomainException` é usada para lançar exceções específicas do domínio, garantindo que as regras de negócios sejam respeitadas.

```java
package model.exceptions;

public class DomainException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DomainException(String message) {
        super(message);
    }
}
```

#### DateFormatter Interface

A interface `DateFormatter` define métodos para formatação e análise de datas.

```java
package model.services;

import java.util.Date;

public interface DateFormatter {
    Date parse(String dateStr) throws Exception;
    String format(Date date);
}
```

#### SimpleDateFormatter

A classe `SimpleDateFormatter` implementa a interface `DateFormatter` usando `SimpleDateFormat`.

```java
package model.services;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatter implements DateFormatter {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Date parse(String dateStr) throws Exception {
        return sdf.parse(dateStr);
    }

    @Override
    public String format(Date date) {
        return sdf.format(date);
    }
}
```

#### InputReader Interface

A interface `InputReader` define métodos para leitura de entradas.

```java
package model.services;

public interface InputReader {
    int readInt(String prompt);
    String readString(String prompt);
}
```

#### ConsoleInputReader

A classe `ConsoleInputReader` implementa a interface `InputReader` usando `Scanner` para ler entradas do console.

```java
package model.services;

import java.util.Scanner;

public class ConsoleInputReader implements InputReader {
    private Scanner sc;

    public ConsoleInputReader() {
        sc = new Scanner(System.in);
    }

    @Override
    public int readInt(String prompt) {
        System.out.print(prompt);
        return sc.nextInt();
    }

    @Override
    public String readString(String prompt) {
        System.out.print(prompt);
        return sc.next();
    }

    public void close() {
        sc.close();
    }
}
```

#### App

A classe principal que integra todos os componentes e executa a aplicação.

```java
import java.util.Date;
import java.util.Locale;

import model.entities.Reservation;
import model.exceptions.DomainException;
import model.services.ConsoleInputReader;
import model.services.DateFormatter;
import model.services.InputReader;
import model.services.SimpleDateFormatter;

public class App {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        
        InputReader inputReader = new ConsoleInputReader();
        DateFormatter dateFormatter = new SimpleDateFormatter();

        try {
            int number = inputReader.readInt("Enter room number: ");
            Date checkIn = dateFormatter.parse(inputReader.readString("Check-in date (dd/MM/yyyy): "));
            Date checkOut = dateFormatter.parse(inputReader.readString("Check-out date (dd/MM/yyyy): "));

            Reservation reservation = new Reservation(number, checkIn, checkOut);
            System.out.println("Reservation: " + reservation);
            System.out.println();

            System.out.println("Enter data to update the reservation:");
            checkIn = dateFormatter.parse(inputReader.readString("Check-in date (dd/MM/yyyy): "));
            checkOut = dateFormatter.parse(inputReader.readString("Check-out date (dd/MM/yyyy): "));
            reservation.updateDates(checkIn, checkOut);
            System.out.println("Reservation: " + reservation);
        } catch (IllegalArgumentException e) {
            System.out.println("Error in reservation: " + e.getMessage());
        } catch (DomainException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((ConsoleInputReader) inputReader).close();
            System.out.println("End of program");
        }
    }
}
```

## Princípios SOLID Aplicados

1. **Single Responsibility Principle (SRP)**: Cada classe tem uma única responsabilidade.
2. **Open/Closed Principle (OCP)**: As classes podem ser estendidas sem serem modificadas.
3. **Liskov Substitution Principle (LSP)**: As subclasses podem substituir suas superclasses.
4. **Interface Segregation Principle (ISP)**: Interfaces específicas foram criadas para diferentes responsabilidades.
5. **Dependency Inversion Principle (DIP)**: Dependência em abstrações (interfaces) ao invés de implementações concretas.

## Como Executar

1. Compile todas as classes Java.
2. Execute a classe `App`.
3. Siga as instruções para inserir os dados de entrada.

## Tratamento de Erros

O sistema trata erros de entrada de dados e regras de negócio usando exceções, garantindo que as datas de check-in e check-out sejam válidas e consistentes com as regras do domínio.