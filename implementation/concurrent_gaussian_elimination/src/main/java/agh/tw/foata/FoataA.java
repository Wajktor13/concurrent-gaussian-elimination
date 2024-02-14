package agh.tw.foata;

import agh.tw.operation.Operation;
import agh.tw.operation.OperationA;

import java.util.ArrayList;
import java.util.List;

public class FoataA implements Foata {

    @Override
    public void executeOperationsConcurrently(double[][] M, int N, double[][] m, double[][][] n, int x) throws InterruptedException {
        Operation operationA = new OperationA();
        List<Thread> threadsA = new ArrayList<>();

        for (int k = x + 1; k < N; k++) {
            final int finalK = k;
            threadsA.add(new Thread(() -> operationA.execute(M, N, m, n, finalK, x, -1)));
        }

        startAndJoinThreads(threadsA);
    }
}
