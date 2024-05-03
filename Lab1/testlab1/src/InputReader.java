import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InputReader {

    public Data readFromTerminal() {
        int n = readPositiveInt("Enter matrix dimension (n): ");

        double accuracy = readPositiveDouble("Enter precision (e): ");

        System.out.println("""
                Enter the coefficients line by line (A|B).
                For example, if yours looks like:
                a11 a12 | b1
                a21 a22 | b2
                The input will be as follows:
                a11 a12 b1
                a21 a22 b2""");
        List<List<Double>> A = new ArrayList<>();
        List<Double> B = new ArrayList<>();
        String buffer;
        Scanner scanner = new Scanner(System.in);
        repeat:
        while (true) {
            for (int i = 0; i < n; i++) {
                List<Double> row = new ArrayList<>();
                for (int j = 0; j < n + 1; j++) {
                    try {
                        buffer = scanner.next();
                        buffer = buffer.replaceAll(",", ".");
                        if (j == n)
                            B.add(Double.parseDouble(buffer));
                        else
                            row.add(Double.parseDouble(buffer));
                    } catch (InputMismatchException | NumberFormatException e) {
                        System.out.println("An error occurred while entering the " + (i * n + j + 2) + " element. Number required.");
                        scanner = new Scanner(System.in);
                        A.clear();
                        B.clear();
                        continue repeat;
                    }
                }
                A.add(row);
            }
            break;
        }

        return new Data(A, B, accuracy);
    }

    public Data readFromFile(String filename) {
        File file = new File(filename);

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("The file does not exist.");
            System.exit(1);
        }

        int n = 0;
        try {
            n = scanner.nextInt();
            if (n <= 0) {
                System.out.println("Requires n > 0.");
                System.exit(1);
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("""
                                An error occurred while reading the matrix dimension.
                                Integer required. (For example 1, 2, 3,...) """);
            System.exit(1);
        }

        String buffer;

        double accuracy = 0;
        try {
            buffer = scanner.next();
            buffer = buffer.replaceAll(",", ".");
            accuracy = Double.parseDouble(buffer);
            if (accuracy <= 0) {
                System.out.println("Positive precision required (e>0).");
                System.exit(1);
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("""
                    An error occurred while entering the matrix dimension. 
                    Integer required. (For example 1, 2, 3,...)
                    """);
            System.exit(1);
        }

        List<List<Double>> A = new ArrayList<>();
        List<Double> B = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < n + 1; j++) {
                try {
                    buffer = scanner.next();
                    buffer = buffer.replaceAll(",", ".");
                    if (j == n)
                        B.add(Double.parseDouble(buffer));
                    else
                        row.add(Double.parseDouble(buffer));
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("An error occurred while entering the" + (i * n + j + 2) + " element. Number required.");
                    System.exit(1);
                } catch (NoSuchElementException e) {
                    System.out.println("You have not entered enough elements. An error occurred while reading " + (i * n + j + 2) + " element.");
                    System.exit(1);
                }
            }
            A.add(row);
        }

        return new Data(A, B, accuracy);
    }

    public int readPositiveInt(String message) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print(message);
                // in dòng yêu cầu trước khi nhập n
                String buffer = scanner.next();
                if (buffer.equals("exit"))
                    System.exit(0);
                //kiểm tra n dương
                int value = Integer.parseInt(buffer);
                if (value > 0)
                    return value;
                System.out.println("A positive number is required.");
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Integer required.");
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
                if (value > 0)
                    return value;
                System.out.println("A positive number is required.");
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Number required.");
            }
        }
    }

}