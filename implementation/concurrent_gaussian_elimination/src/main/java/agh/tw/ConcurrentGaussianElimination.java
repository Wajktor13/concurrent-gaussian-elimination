package agh.tw;

import agh.tw.foata.Foata;
import agh.tw.foata.FoataA;
import agh.tw.foata.FoataB;
import agh.tw.foata.FoataC;

public class ConcurrentGaussianElimination {

    double[][] execute(double[][] M) throws InterruptedException {
        int N = M.length;
        double[][] m = new double[N][N+1];
        double[][][] n = new double[N][N][N+1];

        Foata foataA = new FoataA();
        Foata foataB = new FoataB();
        Foata foataC = new FoataC();

        // converts input matrix to upper triangular matrix - concurrent part
        for (int x = 0; x < N - 1; x++){
            // apply pivotting before each iteration
            applyPivotting(M, N, x);

            // solve column x
            foataA.executeOperationsConcurrently(M, N, m, n, x);
            foataB.executeOperationsConcurrently(M, N, m, n, x);
            foataC.executeOperationsConcurrently(M, N, m, n, x);
        }

        // solve upper triangular matrix - not concurrent part
        M = solveUpperTriangularMatrix(M, N);

        return M;
    }

    void applyPivotting(double[][] M, int N, int iteration) {
        // find the maximum element in the current column
        int pivotRow = iteration;
        for (int i = iteration + 1; i < N; i++) {
            if (Math.abs(M[i][iteration]) > Math.abs(M[pivotRow][iteration])) {
                pivotRow = i;
            }
        }

        // swap rows to make the element with maximum absolute value the pivot
        if (pivotRow != iteration) {
            double[] temp = M[iteration];
            M[iteration] = M[pivotRow];
            M[pivotRow] = temp;
        }
    }

    double[][] solveUpperTriangularMatrix(double[][] M, int N) {
        // convert to diagonal matrix
        for (int row1 = 1; row1 < N; row1++) {
            for (int row2 = 0; row2 < row1; row2++) {
                double m = M[row2][row1] / M[row1][row1];

                for (int col = row1; col <= N; col++) {
                    M[row2][col] -= M[row1][col] * m;
                }
            }
        }

        // convert to identity matrix
        for (int row = 0; row < N; row++) {
            M[row][N] /= M[row][row];
            M[row][row] = 1;
        }

        return M;
    }
}

