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
