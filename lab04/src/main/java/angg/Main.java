package angg;

import java.io.File;
import java.util.List;

import angg.util.ApproximationPrinter;
import angg.util.InputReader;
import angg.util.OutputWriter;
import angg.approximation.Approximation;
import angg.approximation.ExponentialApproximation;
import angg.approximation.LinearApproximation;
import angg.approximation.LogarithmicApproximation;
import angg.approximation.PowerApproximation;
import angg.approximation.QuadraticApproximation;
;

public class Main {
    public static void main(String[] args) {
        boolean readFromFile = false, writeToFile = false;
        File source = null, target = null;
        if (args.length == 0) {
            readFromFile = false;
            writeToFile = false;
        } else {
            String flag = args[0];
            switch (flag) {
                case "-r":
                    if (args.length != 2) {
                        System.out.println("After the read flag, a read-only file must be specified.");
                        System.exit(0);
                    }
                    readFromFile = true;
                    writeToFile = false;
                    source = new File(args[1]);
                    break;
                case "-w":
                    if (args.length != 2) {
                        System.out.println("After the write flag, only the file to be written must be specified.");
                        System.exit(0);
                    }
                    readFromFile = false;
                    writeToFile = true;
                    target = new File(args[1]);
                    break;
                case "-rw":
                    if (args.length != 3) {
                        System.out.println("After the read and write flag, only the files to be read and written must be specified sequentially.");
                        System.exit(0);
                    }
                    readFromFile = true;
                    writeToFile = true;
                    source = new File(args[1]);
                    target = new File(args[2]);
                    break;
                default:
                    System.out.println("""
                            Unknown flag.
                             This program can accept the following flags:
                             -> -r (Read from the specified file)
                             -> -w (Write to the specified file)
                             -> -rw (Read from the first specified file and write to the second specified file)
                            """);
                    System.exit(0);
            }
        }

        if (readFromFile) {
            if (!source.exists()) {
                System.out.println("The specified file to read does not exist.");
                System.exit(0);
            }
            if (!source.canRead()) {
                System.out.println("The specified file to read cannot be read - check permissions.");
                System.exit(0);
            }
        }
        if (writeToFile) {
            if (target.exists() && !target.canWrite()) {
                System.out.println("The specified file cannot be written to - check access permissions.");
                System.exit(0);
            }
        }

        InputReader reader = new InputReader();
        List<double[]> points = null;

        if (readFromFile)
            points = reader.readPointsFromFile(source);
        else
            points = reader.readPointsFromTerminal();

        String res;

        Approximation linearApproximation = new LinearApproximation(points);
        Approximation quadraticApproximation = new QuadraticApproximation(points);
        Approximation powerApproximation = new PowerApproximation(points);
        Approximation exponentialApproximation = new ExponentialApproximation(points);
        Approximation logarithmicApproximation = new LogarithmicApproximation(points);

        res = linearApproximation.toString() + "\n\n" + quadraticApproximation.toString() + "\n\n" + powerApproximation.toString() + "\n\n" + exponentialApproximation.toString() + "\n\n" + logarithmicApproximation.toString() + "\n\n";

        double maxDeterminationCoefficient = 0;
        boolean maxDeterminationCoefficientIsFound = false;
        if (linearApproximation.isCorrect()) {
            maxDeterminationCoefficient = linearApproximation.calculateDeterminationCoefficient();
            maxDeterminationCoefficientIsFound = true;
        }
        if (maxDeterminationCoefficientIsFound && quadraticApproximation.isCorrect() && quadraticApproximation.calculateDeterminationCoefficient() > maxDeterminationCoefficient)
            maxDeterminationCoefficient = quadraticApproximation.calculateDeterminationCoefficient();
        if (!maxDeterminationCoefficientIsFound && quadraticApproximation.isCorrect()) {
            maxDeterminationCoefficient = quadraticApproximation.calculateDeterminationCoefficient();
            maxDeterminationCoefficientIsFound = true;
        }
        if (maxDeterminationCoefficientIsFound && powerApproximation.isCorrect() && powerApproximation.calculateDeterminationCoefficient() > maxDeterminationCoefficient)
            maxDeterminationCoefficient = powerApproximation.calculateDeterminationCoefficient();
        if (!maxDeterminationCoefficientIsFound && powerApproximation.isCorrect()) {
            maxDeterminationCoefficient = powerApproximation.calculateDeterminationCoefficient();
            maxDeterminationCoefficientIsFound = true;
        }
        if (maxDeterminationCoefficientIsFound && exponentialApproximation.isCorrect() && exponentialApproximation.calculateDeterminationCoefficient() > maxDeterminationCoefficient)
            maxDeterminationCoefficient = exponentialApproximation.calculateDeterminationCoefficient();
        if (!maxDeterminationCoefficientIsFound && exponentialApproximation.isCorrect()) {
            maxDeterminationCoefficient = exponentialApproximation.calculateDeterminationCoefficient();
            maxDeterminationCoefficientIsFound = true;
        }
        if (maxDeterminationCoefficientIsFound && logarithmicApproximation.isCorrect() && logarithmicApproximation.calculateDeterminationCoefficient() > maxDeterminationCoefficient)
            maxDeterminationCoefficient = logarithmicApproximation.calculateDeterminationCoefficient();
        if (!maxDeterminationCoefficientIsFound && logarithmicApproximation.isCorrect())
            maxDeterminationCoefficient = logarithmicApproximation.calculateDeterminationCoefficient();


        res += "The models have the most accurate approximation:\n";
        if (linearApproximation.isCorrect() && linearApproximation.calculateDeterminationCoefficient() == maxDeterminationCoefficient)
            res += "-> " + linearApproximation.getName() + "\n";
        if (quadraticApproximation.isCorrect() && quadraticApproximation.calculateDeterminationCoefficient() == maxDeterminationCoefficient)
            res += "-> " + quadraticApproximation.getName() + "\n";
        if (powerApproximation.isCorrect() && powerApproximation.calculateDeterminationCoefficient() == maxDeterminationCoefficient)
            res += "-> " + powerApproximation.getName() + "\n";
        if (exponentialApproximation.isCorrect() && exponentialApproximation.calculateDeterminationCoefficient() == maxDeterminationCoefficient)
            res += "-> " + exponentialApproximation.getName() + "\n";
        if (logarithmicApproximation.isCorrect() && logarithmicApproximation.calculateDeterminationCoefficient() == maxDeterminationCoefficient)
            res += "-> " + logarithmicApproximation.getName() + "\n";
        res += "\n" + "Explain the graph: " + "\n";
        if (linearApproximation.isCorrect())
            res += "Red line - linear function " + "\n";
        if (quadraticApproximation.isCorrect())
            res += "Yellow line - quadratic function " + "\n";
        if (powerApproximation.isCorrect())
            res += "Green line - power function " + "\n";
        if (exponentialApproximation.isCorrect())
            res += "Blue line - exponential function " + "\n";
        if (logarithmicApproximation.isCorrect())
            res += "Pink line - logarithmic function " + "\n";
        OutputWriter writer = new OutputWriter();
        if (writeToFile)
            writer.writeToFile(res, target);
        else
            writer.writeToTerminal(res);
        
        ApproximationPrinter printer = new ApproximationPrinter(points, linearApproximation, quadraticApproximation, powerApproximation, exponentialApproximation, logarithmicApproximation);
        printer.setVisible(true);
    }
}