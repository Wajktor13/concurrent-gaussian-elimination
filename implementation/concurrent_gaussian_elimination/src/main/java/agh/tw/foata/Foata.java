package agh.tw.foata;

import java.util.List;

public interface Foata {

    void executeOperationsConcurrently(double[][] M, int N, double[][] m, double[][][] n, int x) throws InterruptedException;

    default void startAndJoinThreads(List<Thread> threadList) throws InterruptedException {
        for (Thread thread : threadList) {
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }
    }
}
