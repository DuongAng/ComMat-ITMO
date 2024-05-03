import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculation {

    // phương pháp tính định thức của ma trận
    public double getDeterminant(List<List<Double>> matrix) {
        int n = matrix.size();
        double det = 1.0;
        List<List<Double>> mat = cloneMatrix(matrix);


        for (int i = 0; i < n; i++) {
            int max = i;

            for (int j = i + 1; j < n; j++) {
                if (Math.abs(mat.get(j).get(i)) > Math.abs(mat.get(max).get(i)))
                    max = j;
            }

            //trường hợp tất cả j = thì định thức là 0
            if (mat.get(max).get(i) == 0.0)
                return 0.0;

            if (max != i) {
                List<Double> buffer = new ArrayList<>(mat.get(i));
                mat.set(i, new ArrayList<>(mat.get(max)));
                mat.set(max, buffer);
                det *= -1d;
            }

            // qua mỗi i sẽ nhân thêm vào
            double pivot = mat.get(i).get(i);
            det *= pivot;

            // đưa về ma trận tam giác trên
            for (int j = i + 1; j < n; j++) {
                double factor = mat.get(j).get(i) / pivot;
                for (int k = i; k < n; k++) {
                    double val = mat.get(j).get(k) - factor * mat.get(i).get(k);
                    mat.get(j).set(k, val);
                }
            }
        }

        return det;
    }

    // sao chép ma trận
    private List<List<Double>> cloneMatrix(List<List<Double>> original) {
        List<List<Double>> clone = new ArrayList<>(original.size());
        for (List<Double> row : original) {
            List<Double> newRow = new ArrayList<>(row);
            clone.add(newRow);
        }
        return clone;
    }


    // phương pháp rút gọn ma trận thành ma trận chiếm ưu thế theo đường chéo
    public void toConvergence(Data data) {
        List<List<Double>> A = data.getA();
        List<Double> B = data.getB();
        List<List<Integer>> rowPositions = new ArrayList<>();
        for (int i = 0; i < A.size(); i++)
            rowPositions.add(new ArrayList<>());

        for (int i = 0; i < A.size(); i++) {
            List<Double> row = A.get(i);

            double max = Math.abs(row.get(0));
            List<Integer> positions = new ArrayList<>();
            positions.add(0);
            double sum = 0;

            for (int j = 1; j < row.size(); j++) {
                double current = Math.abs(row.get(j));
                if (current > max) {
                    sum += max * positions.size();
                    max = current;
                    positions.clear();
                    positions.add(j);
                } else if (current == max) {
                    positions.add(j);
                } else {
                    sum += current;
                }
            }

            if (max < sum + max * (positions.size() - 1)) {
                System.out.print("""
                        The original matrix could not be converted to a diagonally dominated matrix.
                        Warning: if you continue solving, the final answer may not converge.""");
                InputReader inputReader = new InputReader();
                data.setIterations(inputReader.readPositiveInt("Enter number of iterations: "));
                return;
            }

            for (int position : positions)
                rowPositions.get(position).add(i);
        }


        int[] permutation = new int[A.size()];
        List<Integer> placedRows = new ArrayList<>();
        Arrays.fill(permutation, -1);


        for (int i = 0; i < A.size(); i++) {
            List<Integer> rowPosition = rowPositions.get(i);
            if (rowPosition.size() == 1) {
                permutation[i] = rowPosition.get(0);
                placedRows.add(rowPosition.get(0));
            }
        }


        for (int i = 0; i < A.size(); i++) {
            List<Integer> rowPosition = rowPositions.get(i);
            if (rowPosition.size() == 2) {
                if (!placedRows.contains(rowPosition.get(0))) {
                    permutation[i] = rowPosition.get(0);
                    placedRows.add(rowPosition.get(0));
                } else if (!placedRows.contains(rowPosition.get(1))) {
                    permutation[i] = rowPosition.get(1);
                    placedRows.add(rowPosition.get(1));
                } else {
                    System.out.print("""
                            The original matrix could not be converted to a diagonally dominated matrix.
                            Warning: if you continue solving, the final answer may not converge.""");
                    InputReader inputReader = new InputReader();
                    data.setIterations(inputReader.readPositiveInt("Enter number of iterations: "));
                    return;
                }
            }
        }


        List<List<Double>> new_A = new ArrayList<>();
        List<Double> new_B = new ArrayList<>();

        for (int i : permutation) {
            if (i == -1) {
                System.out.print("""
                        The original matrix could not be converted to a diagonally dominated matrix.
                        Warning: if you continue solving, the final answer may not converge""");
                InputReader inputReader = new InputReader();
                data.setIterations(inputReader.readPositiveInt("Enter number of iterations: "));
                return;
            }
            new_A.add(A.get(i));
            new_B.add(B.get(i));
        }

        data.setA(new_A);
        data.setB(new_B);
    }


    // phương pháp tìm lời giải với độ chính xác đã nêu
    public void iterate(Data data) {
        List<List<Double>> A = data.getA();
        List<Double> B = data.getB();
        int n = data.getA().size();
        double[] previousApproximation = new double[n];
        for (int i = 0; i < n; i++)
            previousApproximation[i] = data.getB().get(i);

        double[] newApproximation = new double[n];
        int iterationCounter = 0;

        InputReader inputReader = new InputReader();
        if (data.getIterations() == -1)
            data.setIterations(inputReader.readPositiveInt("Enter the maximum number of iterations: "));

        while (true) {

            if (iterationCounter != 0 && iterationCounter == data.getIterations()) {
                int c = 5;
                System.out.println(iterationCounter + " iterations have been carried out. But the answer was never found.");
                for (int i = 0; i < n; i++) {
                    System.out.println("x" + (i + 1) + "=" + String.format("%." + c + "f", newApproximation[i]) + "; The deviation is: " + String.format("%." + c + "f", Math.abs(newApproximation[i] - previousApproximation[i])));
                }
                break;
            }

            for (int i = 0; i < n; i++) {
                double newValue = B.get(i) / A.get(i).get(i);
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        newValue -= (A.get(i).get(j) / A.get(i).get(i)) * previousApproximation[j];
                        if (Double.isNaN(newValue) || Double.isInfinite(newValue)) {
                            System.out.println("Does not have a convergent solution.");
                            System.exit(1);
                        }
                    }
                }
                newApproximation[i] = newValue;
            }
            if (getMaxDeviation(previousApproximation, newApproximation) <= data.getAccuracy()
                    || (data.getIterations() != -1 && data.getIterations() == iterationCounter)) {
                int c = 5;
                System.out.println( iterationCounter + " iterations have been carried out.");
                for (int i = 0; i < n; i++) {
                    System.out.println("x" + (i + 1) + "=" + String.format("%." + c + "f", newApproximation[i]) + "; The deviation is: " + String.format("%." + c + "f", Math.abs(newApproximation[i] - previousApproximation[i])));
                }
                break;
            }
            for (int i = 0; i < n; i++)
                previousApproximation[i] = newApproximation[i];
            iterationCounter++;
        }
    }

    // phương pháp tìm độ lệch tối đa của phép tính gần đúng tiếp theo
    private double getMaxDeviation(double[] previousApproximation, double[] newApproximation) {
        double maxDeviation = 0;

        for (int i = 0; i < previousApproximation.length; i++) {
            double deviation = Math.abs(previousApproximation[i] - newApproximation[i]);
            if (deviation > maxDeviation)
                maxDeviation = deviation;
        }

        return maxDeviation;
    }
}