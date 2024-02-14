package agh.tw.foata;

import agh.tw.operation.Operation;
import agh.tw.operation.OperationB;

import java.util.ArrayList;
import java.util.List;

public class FoataB implements Foata {

    @Override
    public void executeOperationsConcurrently(double[][] M, int N, double[][] m, double[][][] n, int x) throws InterruptedException {
        Operation operationB = new OperationB();
        List<Thread> threadsB = new ArrayList<>();

        for (int k = x + 1; k < N; k++) {
            for (int j = x; j <= N; j ++) {
                final int finalK = k;
                final int finalJ = j;
                threadsB.add(new Thread(() -> operationB.execute(M, N, m, n, finalK, x, finalJ)));
            }
        }

        startAndJoinThreads(threadsB);
    }
}
