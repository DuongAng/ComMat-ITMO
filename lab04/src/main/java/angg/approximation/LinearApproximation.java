package angg.approximation;

import java.util.ArrayList;
import java.util.List;

import angg.util.LinearSystemSolver;
import angg.util.TableGenerator;

// y = ax + b
public class LinearApproximation extends Approximation {

    public LinearApproximation(List<double[]> points) {
        super(points);
        calculateCoefficients();
    }

    double calculateCorrelationCoefficient() {
        double avgX = 0;
        for (double[] point : getPoints())
            avgX += point[0];
        avgX /= n();

        double avgY = 0;
        for (double[] point : getPoints())
            avgY += point[1];
        avgY /= n();

        double correlationMoment = 0;
        for (double[] point : getPoints())
            correlationMoment += (point[0] - avgX) * (point[1] - avgY);

        double dispertionX = 0;
        for (double[] point : getPoints())
            dispertionX += Math.pow(point[0] - avgX, 2);

        double dispertionY = 0;
        for (double[] point : getPoints())
            dispertionY += Math.pow(point[1] - avgY, 2);
        
        return correlationMoment / Math.sqrt(dispertionX * dispertionY);
    }

    @Override
    void calculateCoefficients() {
        double[][] a = {{sxx(), sx()}, {sx(), n()}};
        double[] b = {sxy(), sy()};
        double[] coefficients = LinearSystemSolver.solve(a, b);

        if (coefficients.length == 0) {
            setCorrect(false);
            return;
        }

        getCoefficients().put("a", coefficients[0]);
        getCoefficients().put("b", coefficients[1]);
    }

    @Override
    public double calculateValue(double x) {
        return getCoefficients().get("a") * x + getCoefficients().get("b");
    }

    @Override
    public String getName() {
        return "LINEAR APPROXIMATION";
    }

    @Override
    public String toString() {
        String res = getName() + ":\n";
        if (!isCorrect())
            return res + "It was not possible to construct a linear approximation based on the entered data.";
        res += "The resulting approximating function: y = " + getCoefficients().get("a") + "x + " + getCoefficients().get("b") + "\n\n";

        List<String> headers = List.of("No .", "X", "Y", "y=ax+b", "εi");
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

        res += "Correlation coefficient: r = " + calculateCorrelationCoefficient() + "\n";
        res += "Standard deviation: 𝜹 = " + calculateStandartDeviation() + "\n";
        res += "Determination coefficient: R^2 = " + calculateDeterminationCoefficient() + " - " + getDeterminationCoefficientMessage(calculateDeterminationCoefficient());

        return res;
    }
}
