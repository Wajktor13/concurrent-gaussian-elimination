package agh.tw;

import java.io.*;

public class FileIOManager {

    public double[][] readMatrixFromFile(String filename) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line = bufferedReader.readLine();
            int N = Integer.parseInt(line);

            double[][] M = new double[N][N + 1];
            String[] splitLine;
            int i = 0, j;

            // matrix N x N
            while (i < N) {
                line = bufferedReader.readLine();

                if (line != null) {
                    splitLine = line.split(" ");
                    j = 0;

                    while (j < N) {
                        M[i][j] = Double.parseDouble(splitLine[j]);
                        j++;
                    }

                    i++;

                } else {
                    throw new IOException("invalid input file");
                }
            }

            // vector 1 x N
            if ((line = bufferedReader.readLine()) != null) {
                splitLine = line.split(" ");
                i = 0;

                while (i < N) {
                    M[i][N] = Double.parseDouble(splitLine[i]);
                    i++;
                }

            } else {
                throw new IOException("invalid input file");
            }

            return M;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveMatrixToFile(String savePath, double[][] matrix) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(savePath))) {
            int N = matrix.length;
            bufferedWriter.write(String.valueOf(N) + '\n');

            for (double[] doubles : matrix) {
                for (int j = 0; j < N; j++) {
                    bufferedWriter.write(String.valueOf(doubles[j]) + ' ');
                }
                bufferedWriter.write("\n");
            }

            for (double[] doubles : matrix) {
                bufferedWriter.write(String.valueOf(doubles[N]) + ' ');
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
