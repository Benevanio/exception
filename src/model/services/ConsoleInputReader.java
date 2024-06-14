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
