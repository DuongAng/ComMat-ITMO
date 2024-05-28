package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputReader {

    public int readIndex(String message, String notFoundMessage, int length) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print(message);
                String buffer = scanner.next();
                if (buffer.equals("exit"))
                    System.exit(0);
                int value = Integer.parseInt(buffer);
                if (value <= 0) {
                    System.out.println("A positive number is required.");
                    continue;
                }
                if (value - 1 >= length) {
                    System.out.println(notFoundMessage);
                    continue;
                }
                return value - 1;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Integer required.");
            }
        }
    }

    public double readDouble(String message) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print(message);
                String buffer = scanner.next();
                if (buffer.equals("exit"))
                    System.exit(0);
                buffer = buffer.replaceAll(",", ".");
                return Double.parseDouble(buffer);
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Number required.");
            }
        }
    }

    public double readPositiveDouble(String message) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print(message);
                String buffer = scanner.next();
                if (buffer.equals("exit"))
                    System.exit(0);
                buffer = buffer.replaceAll(",", ".");
                double value = Double.parseDouble(buffer);
                if (value >= 0)
                    return value;
                System.out.println("A positive number is required.");
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Number required.");
            }
        }
    }

    public boolean readYesNo(String message) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(message);
            System.out.print("Enter yes/no: ");
            String buffer = scanner.nextLine();
            if (buffer.equals("yes"))
                return true;
            else if (buffer.equals("no"))
                return false;
            else
                System.out.println("yes/no required.");
        }
    }

}
