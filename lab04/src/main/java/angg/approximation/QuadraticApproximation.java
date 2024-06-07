package angg.approximation;

import java.util.ArrayList;
import java.util.List;

import angg.util.LinearSystemSolver;
import angg.util.TableGenerator;

public class QuadraticApproximation extends Approximation {

    public QuadraticApproximation(List<double[]> points) {
        super(points);
        calculateCoefficients();
    }

    @Override
    void calculateCoefficients() {
        double[][] a = {{n(), sx(), sxx()}, {sx(), sxx(), sxxx()}, {sxx(), sxxx(), sxxxx()}};
        double[] b = {sy(), sxy(), sxxy()};
        double[] coefficients = LinearSystemSolver.solve(a, b);

        if (coefficients.length == 0) {
            setCorrect(false);
            return;
        }

        getCoefficients().put("a0", coefficients[0]);
        getCoefficients().put("a1", coefficients[1]);
        getCoefficients().put("a2", coefficients[2]);
    }

    @Override
    public double calculateValue(double x) {
        return getCoefficients().get("a0") + getCoefficients().get("a1") * x + getCoefficients().get("a2") * Math.pow(x, 2);
    }

    @Override
    public String getName() {
        return "QUADRATIC APPROXIMATION";
    }

    @Override
    public String toString() {
        String res = getName() + ":\n";
        if (!isCorrect())
            return res + "It was not possible to construct a quadratic approximation based on the entered data.";
        res += "The resulting approximating function: y = " + getCoefficients().get("a0") + " + " + getCoefficients().get("a1") + "x + " + getCoefficients().get("a2") + "x^2\n\n";

        List<String> headers = List.of("No .", "X", "Y", "y=a0+a1x+a2x^2", "εi");
        List<List<String>> data = new ArrayList<>();
        List<String> column;
        for (int i = 0; i < getPoints().size(); i++) {
            column = new ArrayList<>();
            double[] point = getPoints().get(i);
            column.add(String.format("%d", (i+1)));
            column.add(String.format("%f", point[0]));
            column.add(String.format("%f", point[1]));
            column.add(String.format("%f", calculateValue(point[0])));
            column.add(String.format("%f", calculateValue(point[0]) - point[1]));
            data.add(column);
        }
        res += TableGenerator.generate(headers, data) + "\n";

        res += "Standard deviation: 𝜹 = " + calculateStandartDeviation() + "\n";
        res += "Determination coefficient: R^2 = " + calculateDeterminationCoefficient() + " - " + getDeterminationCoefficientMessage(calculateDeterminationCoefficient());

        return res;
    }    

}