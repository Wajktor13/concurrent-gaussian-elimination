package agh.tw;

public class Main {

    public static void main(String[] args) {
        String inputFilePath = "src/main/resources/input/input2.txt";
        String outputFilePath = "src/main/resources/output/output2.txt";

        FileIOManager fileIOManager = new FileIOManager();
        ConcurrentGaussianElimination concurrentGaussianElimination = new ConcurrentGaussianElimination();
        double[][] M;

        if (args.length==0) {
            System.out.println("running with default paths");
        }
        else if (args.length == 1) {
            System.out.println("running with default output path");
            inputFilePath = args[0];
        } else if (args.length == 2){
            inputFilePath = args[0];
            outputFilePath = args[1];
        } else {
            System.out.println("invalid number of arguments");
            return;
        }

        M = fileIOManager.readMatrixFromFile(inputFilePath);

        if (M != null) {
            try {
                M = concurrentGaussianElimination.execute(M);
                fileIOManager.saveMatrixToFile(outputFilePath, M);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
