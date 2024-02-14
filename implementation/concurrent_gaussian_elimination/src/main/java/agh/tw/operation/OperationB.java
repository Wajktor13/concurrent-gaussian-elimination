package agh.tw.operation;

public class OperationB implements Operation {

    @Override
    public void execute(double[][] M, int N, double[][] m, double[][][] n, int k, int i, int j) {
        n[k][i][j] = M[i][j] * m[k][i];
    }
}
