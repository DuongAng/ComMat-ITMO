package angg;

import angg.approximation.*;
import angg.exceptions.NumberPointException;
import angg.exceptions.ReadDataException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Solver {
    private String filename_In, filename_Out;
    private File file_In, file_Out;
    private Scanner in, scannerIn, scannerOut;
    private double[][] points, pointsPlus;
    private double min_x, min_y;
    private Result[] results;

    public Solver() {
        in = new Scanner(System.in);
    }

    public void run() throws NumberPointException, ReadDataException {
        System.out.print("""
                Enter the file name to read:
                If entering from the keyboard, enter 0:
                """);
        filename_In = in.next();
        System.out.print("""
                Enter the file name to record results:
                If entering from the keyboard, enter 0:
                """);
        filename_Out = in.next();
        init();
        readData();
        System.out.println("\n");
        approximation();
        showResult();
    }

    private void init() {
        if (!Objects.equals(filename_In, "0")) {
            file_In = new File(filename_In);
            System.out.println("Enter file:" + filename_In);
            try {
                scannerIn = new Scanner(file_In);
            } catch (FileNotFoundException e) {
                System.out.println("Reading error");
            }
        } else {
            scannerIn = new Scanner(System.in);
            System.out.println("Keyboard input");
        }

        if (!Objects.equals(filename_Out, "0")) {
            file_Out = new File(filename_Out);
            System.out.println("Out file:" + filename_Out);
        } else {
            scannerOut = new Scanner(System.in);
            System.out.println("Console output");
        }
        results = new Result[6];
    }

    private void readData() throws NumberPointException, ReadDataException {
        System.out.println("Enter number of points:");
        int counter_points = Integer.parseInt(scannerIn.next());
        if(counter_points<8 || counter_points>12) {
            throw new NumberPointException();
        }
        points = new double[counter_points][2];
        pointsPlus = new double[counter_points][2];
        System.out.println("Enter points:");
        for (int i = 0; i < counter_points; i++) {
            try {
                points[i][0] = Double.parseDouble(scannerIn.next());
                points[i][1] = Double.parseDouble(scannerIn.next());
            }
            catch (NumberFormatException e){
                throw new ReadDataException();
            }
        }
    }

    private void approximation() {
        Linear_function linear_Function = new Linear_function();
        linear_Function.init(points);
        linear_Function.solve();
        results[0] = linear_Function.getResult();

        Square_function square_Function = new Square_function();
        square_Function.init(points);
        square_Function.solve();
        results[1] = square_Function.getResult();

        Cube_function cube_Function = new Cube_function();
        cube_Function.init(points);
        cube_Function.solve();
        results[2] = cube_Function.getResult();

        Exponential_function exponential_Function = new Exponential_function();
        exponential_Function.init(points);
        exponential_Function.solve();
        results[3] = exponential_Function.getResult();

        Logarithm_function logarithm_Function = new Logarithm_function();
        logarithm_Function.init(points);
        logarithm_Function.solve();
        results[4] = logarithm_Function.getResult();

        Degree_function degree_Function = new Degree_function();
        degree_Function.init(points);
        degree_Function.solve();
        results[5] = degree_Function.getResult();
    }

    private void showResult() {
        Result min_Result = results[0];

        if(Objects.equals(filename_Out, "0")){
            for (int i = 0; i < results.length; i++) {
                if (results[i].getEps() < min_Result.getEps()) {
                    min_Result = results[i];
                }
                System.out.println(results[i]);
                Draw_Graphic drawGraphic = new Draw_Graphic();
                drawGraphic.init(results[i].getname_Function(), points, results[i].getFunction());
                drawGraphic.show();
            }
        }
        else {
            try(FileWriter writer = new FileWriter(filename_Out, false))
            {
                for (int i = 0; i < results.length; i++) {
                    if (results[i].getEps() < min_Result.getEps()) {
                        min_Result = results[i];
                    }
                    writer.write(results[i].toString());
                    writer.flush();
                    Draw_Graphic drawGraphic = new Draw_Graphic();
                    drawGraphic.init(results[i].getname_Function(), points, results[i].getFunction());
                    drawGraphic.show();
                }
                writer.write("Best approximation:\n");
                writer.write(min_Result.toString());
                writer.flush();
                System.out.println("The result is written to a file");
            }
            catch(IOException ex){
                System.out.println("Write error");
            }
        }

        System.out.println("Best approximation:");
        System.out.println(min_Result);
        Draw_Graphic drawGraphic = new Draw_Graphic();
        drawGraphic.init(min_Result.getname_Function(), points, min_Result.getFunction());
        drawGraphic.show();
    }
}
