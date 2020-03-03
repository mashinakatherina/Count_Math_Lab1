import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ColorUtils.Utils.colorize;

public class Matrix {


    private static final Double EPSILON = 1e-9;

    private List<Equation> matrix;
    private int count = 1;
    private Double getItem(int row, int column) {
        return matrix.get(row).getItem(column);
    }

    Matrix(List<Equation> matrix) {
        this.matrix = matrix;
    }

    void print() {
        for (Equation l : matrix) {
            l.formatOutput();
        }
    }

    List<Double> getResidual(List<Double> ans) {
        List<Double> residuals = new ArrayList<Double>();
        int n = matrix.size();
        for (int i = 0; i < n; i++) {
            Equation curEquation = matrix.get(i);
            Double res = 0.0;
            for (int j = 0; j < n; j++) {
                res += curEquation.getItem(j) * ans.get(j);
            }
            residuals.add(res - curEquation.getItem(n));
        }
        return residuals;
    }

    List<Double> getAnswers() {
        int n = matrix.size();
        straightRun();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (Math.abs(this.getItem(i, j)) > EPSILON) {
                    System.out.format(colorize("[[RED]]Error: matrix is not triangular. " +
                            "Item at row %d and column %d " +
                            "is not zero!%n[[WHITE]]"), i + 1, j + 1);
                    return null;
                }
            }
        }
        return backRun();
    }
    private void straightRun(){
        int n = matrix.size();
        boolean isLineOfZeros = false;
        for (int i = 0; i < n; i++) {
            int zeros = 0;
            if (Math.abs(this.getItem(i, i)) < EPSILON) {
                isLineOfZeros = true;

                for (int j = i + 1; j < n; j++)
                    if ((Math.abs(this.getItem(i, j)) < EPSILON)) zeros++;


                if ((zeros == n - i - 1) && Math.abs(this.getItem(i, n)) > EPSILON) {
                    System.out.println(colorize("[[RED]]No solutions![[WHITE]]"));
                    System.exit(0);
                }

            }
        }
        if (isLineOfZeros == true) {
            System.out.println(colorize("[[RED]]Infinite " +
                    "number of solutions![[WHITE]]"));
            System.exit(0);
        }
    }
    private List<Double> backRun(){
        int n = matrix.size();
        List<Double> unknowns = new ArrayList<Double>(Collections.nCopies(n, 0.0));
        for (int i = n - 1; i >= 0; i--) {
            Equation curEquation = matrix.get(i);
            Double ans = curEquation.getItem(n);

            for (int j = i + 1; j < n; j++) {
                ans -= curEquation.getItem(j) * unknowns.get(j);
            }
            unknowns.set(i, ans / curEquation.getItem(i));
        }
        return unknowns;
    }


    double getTriangularMatrix() {
        int sizeOfMatrix = matrix.size();
        int topRowIndex = 0;
        for (int i = 0; i < sizeOfMatrix; i++) {
            int firstNotZero = -1;
            for (int j = topRowIndex; j < sizeOfMatrix && firstNotZero == -1; j++) {
                if (Math.abs(this.getItem(j, i)) > EPSILON) {
                    firstNotZero = j;
                }
            }
            if (firstNotZero == -1) continue;

            if (topRowIndex != firstNotZero) {
                Equation tmp = matrix.get(topRowIndex);
                matrix.set(topRowIndex, matrix.get(firstNotZero));
                matrix.set(firstNotZero, tmp);
                count *= (-1);
            }

            Equation topRow = matrix.get(topRowIndex);
            for (int j = topRowIndex + 1; j < sizeOfMatrix; j++) {
                matrix.set(j, matrix.get(j).summarize(topRow.multiply(-this.getItem(j, i) / topRow.getItem(i))));
            }
            topRowIndex++;
        }

        double determinant = 1.0;
        for (int i = 0; i < sizeOfMatrix; i++) {
            determinant *= this.getItem(i, i);
        }
        if (Math.abs(determinant)<EPSILON) return 0;
        return determinant * count;
    }



}