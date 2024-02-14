package agh.tw.operation;

public class OperationA implements Operation {

    @Override
    public void execute(double[][] M, int N, double[][] m, double[][][] n, int k, int i, int j) {
        m[k][i] = M[k][i] / M[i][i];
    }
}
