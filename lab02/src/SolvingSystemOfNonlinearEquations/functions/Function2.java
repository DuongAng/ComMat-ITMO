package SolvingSystemOfNonlinearEquations.functions;

import java.util.ArrayList;
import java.util.List;

// function: y + cos(x) = 1.5
public class Function2 extends Function {

    @Override
    public double compute(double x, double y) {
        return y + Math.cos(x) - 1.5;
    }

    @Override
    public List<List<Double>> computePoints() {
        List<Double> xPoints = new ArrayList<>();
        List<Double> yPoints = new ArrayList<>();

        for (double x = -10d; x <= 10d; x += 0.1d) {
            xPoints.add(x);
            yPoints.add(1.5 - Math.cos(x));
        }

        return List.of(xPoints, yPoints);
    }

    @Override
    public double computeDerivativeX(double x, double y) {
        return -Math.sin(x);
    }

    @Override
    public double computeDerivativeY(double x, double y) {
        return 1;
    }

    @Override
    public String toString() {
        return "y + cos(x) = 1.5";
    }
}
