package SolvingSystemOfNonlinearEquations.functions;

import java.util.ArrayList;
import java.util.List;

// function 2x - sin(y - 0.5) = 1
public class Function1 extends Function {

    @Override
    public double compute(double x, double y) {
        return 2*x - Math.sin(y - 0.5) -1;
    }

    @Override
    public List<List<Double>> computePoints() {
        List<Double> xPoints = new ArrayList<>();
        List<Double> yPoints = new ArrayList<>();

        for (double y = -2d; y <= 2d; y+=0.01d) {
            yPoints.add(y);
            xPoints.add((Math.sin(y - 0.5) + 1)/2);
        }
        for (double y = 2d; y >= -2d; y -=0.01d) {
            yPoints.add(y);
            xPoints.add((Math.sin(y - 0.5) + 1)/2);
        }

        return List.of(xPoints, yPoints);
    }

    @Override
    public double computeDerivativeX(double x, double y) {
        return 2;
    }

    @Override
    public double computeDerivativeY(double x, double y) {
        return -Math.cos(y - 0.5);
    }

    @Override
    public String toString() {
        return "2x - sin(y - 0.5) = 1";
    }
}
