package angg.approximation;

import angg.Result;
import angg.interfaces.Function;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;

public class Linear_function {
    private double eps = 0;
    private double[] x, y, f, e;
    private double[][] function_Table;
    private double sum_x = 0, sum_x2 = 0, sum_y = 0, sum_x_y = 0;
    private Result result;

    public void init(double[][] function_Table){
        this.function_Table = function_Table;
        for (int i = 0; i < function_Table.length; i++) {
            sum_x += function_Table[i][0];
            sum_x2 += Math.pow(function_Table[i][0], 2);
            sum_y += function_Table[i][1];
            sum_x_y += function_Table[i][0] * function_Table[i][1];
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

        Function function = x -> (ress[0]*x+ ress[1]);

        result = new Result();
        result.setname_Function("Linear");
        result.setstr_Function(ress[0]+"x + "+ ress[1]);

        for (int i = 0; i < function_Table.length; i++) {
            x[i] = function_Table[i][0];
            y[i] = function_Table[i][1];
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
        result.setR(coefficient());
        result.setFunction(function);
    }

    private double coefficient(){
        double r, xc=0, yc=0, nm=0, dn1=0, dn2=0, dn=0;
        for(int i=0;i< function_Table.length;i++){
            xc+=x[i];
            yc+=y[i];
        }
        xc=xc/function_Table.length;
        yc=yc/function_Table.length;

        for(int i=0;i< function_Table.length;i++){
            nm +=(x[i]-xc)*(y[i]-yc);
            dn1 += Math.pow(x[i]-xc,2);
            dn2 += Math.pow(y[i]-yc,2);
        }
        dn=dn1*dn2;
        dn=Math.sqrt(dn);
        r=nm/dn;
        return r;
    }

    public Result getResult() {
        return result;
    }

}
