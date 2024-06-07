package angg.util;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InputReader {

    public List<double[]> readPointsFromTerminal() {
        System.out.println("""
               Enter 8-12 points in the format:
                                
                 x1 y1
                 x2 y2
                 ...
                 xn yn
                                
               To stop typing, press Enter 2 times.
               To exit the program, enter exit.
                """);
        List<double[]> points = new ArrayList<>();
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                String buffer = scanner.nextLine();
                if (buffer.equals("exit"))
                    System.exit(0);
                if (buffer.isBlank()) {
                    if (points.size() < 8) {
                        System.out.println("At least 8 points must be entered.\n" +
                                            "Enter more " + (8 - points.size()) + ".");
                        continue;
                    }
                    break;
                }
                buffer = buffer.strip();
                while (buffer.contains("  "))
                    buffer = buffer.replaceAll("  ", " ");
                String[] parts = buffer.split(" ");
                if (parts.length != 2) {
                    System.out.println("You need to enter two point coordinates.");
                    continue;
                }
                parts[0] = parts[0].replace(",", ".");
                parts[1] = parts[1].replace(",", ".");
                Double x = Double.parseDouble(parts[0]);
                Double y = Double.parseDouble(parts[1]);
                points.add(new double[] {x, y});
                if (points.size() == 12) 
                    break;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("The coordinate must be a number.");
            }
        }
        return points;
    }

    public List<double[]> readPointsFromFile(File file) {    
        List<String> lines = new ArrayList<>();
        List<double[]> points = new ArrayList<>();
        try {  
            Scanner scanner = new Scanner(file);
            String buffer;
            while (scanner.hasNextLine()) {
                buffer = scanner.nextLine();
                if (buffer.isBlank())
                    continue;
                buffer = buffer.strip();
                while (buffer.contains("  "))
                    buffer = buffer.replaceAll("  ", " ");
                lines.add(buffer);
            }
            if (lines.size() < 8 || lines.size() > 12) {
                System.out.println("This program accepts 8-12 points.");
                System.exit(0);
            }
        } catch (Exception exception) {}

        try {
            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts.length != 2) {
                    System.out.println("Any point must contain two coordinates.");
                    continue;
                }
                parts[0] = parts[0].replace(",", ".");
                parts[1] = parts[1].replace(",", ".");
                Double x = Double.parseDouble(parts[0]);
                Double y = Double.parseDouble(parts[1]);
                points.add(new double[] {x, y});
            }
        } catch (NumberFormatException e) {
            System.out.println("Any coordinate must be a number.");
            System.exit(0);
        }
        return points;
    }

}
