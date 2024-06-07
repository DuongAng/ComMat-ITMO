package angg.approximation;

import java.util.ArrayList;
import java.util.List;

import angg.util.LinearSystemSolver;
import angg.util.TableGenerator;

public class PowerApproximation extends Approximation {

    public PowerApproximation(List<double[]> points) {
        super(points);
        calculateCoefficients();
    }

    @Override
    void calculateCoefficients() {
        for (double[] point : getPoints()) {
            if (point[0] <= 0 || point[1] <= 0) {
                setCorrect(false);
                return;
            }
        }

        double[][] a = {{sXX(), sX()}, {sX(), n()}};
        double[] b = {sXY(), sY()};
        double[] coefficients = LinearSystemSolver.solve(a, b);

        if (coefficients.length == 0) {
            setCorrect(false);
            return;
        }

        getCoefficients().put("a", Math.exp(coefficients[1]));
        getCoefficients().put("b", coefficients[0]);
    }

    @Override
    public double calculateValue(double x) {
        return getCoefficients().get("a") * Math.pow(x, getCoefficients().get("b"));
    }

    @Override
    public String getName() {
        return "POWER APPROXIMATION";
    }

    @Override
    public String toString() {
        String res = getName() + ":\n";
        if (!isCorrect())
            return res + "It was not possible to construct a power-law approximation based on the entered data.";
        res += "The resulting approximating function: y = " + getCoefficients().get("a") + "x^" + getCoefficients().get("b") + "\n\n";

        List<String> headers = List.of("No .", "X", "Y", "y=ax^x", "εi");
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
