package agh.tw.operation;

public class OperationC implements Operation {

    @Override
    public void execute(double[][] M, int N, double[][] m, double[][][] n, int k, int i, int j) {
        M[k][j] -= n[k][i][j];
    }
}
