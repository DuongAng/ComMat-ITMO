package angg.approximation;

import angg.Result;
import angg.interfaces.Function;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;

public class Exponential_function {
    private double eps = 0;
    private double[] x, y, f, e;
    private double[][] function_Table;
    private double sum_x = 0, sum_x2 = 0, sum_y = 0, sum_x_y = 0;
    private double min_x, min_y;
    private Result result;

    public void init(double[][] function_Table){
        this.function_Table = function_Table;
        for (int i = 0; i < function_Table.length; i++) {
            sum_x += function_Table[i][0];
            sum_x2 += Math.pow(function_Table[i][0],2);
            sum_y += Math.log(function_Table[i][1]);
            sum_x_y += function_Table[i][0] * Math.log(function_Table[i][1]);
        }
        x = new double[function_Table.length];
        y = new double[function_Table.length];
        f = new double[function_Table.length];
        e = new double[function_Table.length];
    }

    public void solve() {
        double[][] matrix = new double[][]{
                {sum_x2, sum_x},
                {sum_x, function_Table.length},
        };

        double[] constants = new double[]{sum_x_y, sum_y};
        DecompositionSolver solver = new LUDecomposition(new Array2DRowRealMatrix(matrix)).getSolver();
        double[] ress = solver.solve(new ArrayRealVector(constants)).toArray();

        Function function = x -> (Math.exp(ress[1])*Math.exp(ress[0]*x));

        result = new Result();
        result.setname_Function("Exponential");
        result.setstr_Function(Math.exp(ress[1])+"*e^"+ ress[0]+"x");

        for (int i = 0; i < function_Table.length; i++) {
            x[i] = function_Table[i][0]-min_x;
            y[i] = function_Table[i][1]-min_y;
            f[i] = function.calculate(x[i]);
            e[i] = f[i] - y[i];
            eps += Math.pow(e[i], 2);
        }
        eps = eps / function_Table.length;
        eps = Math.sqrt(eps);
        result.setX(x);
        result.setY(y);
        result.setF(f);
        result.setE(e);
        result.setEps(eps);
        result.setFunction(function);
    }

    public Result getResult() {
        return result;
    }
}
