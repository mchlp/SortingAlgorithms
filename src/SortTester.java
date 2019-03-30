/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovic
 *  Date: 2019/3/29
 *  Course: ICS4U
 */

import java.util.Random;

/**
 * SortTester class to run benchmark tests on IntegerSorter with different sized arrays and different sorting methods.
 */

public class SortTester {

    // Sizes of the arrays to run benchmark tests on
    public static final int[] ARRAY_SIZES = {16, 128, 1024, 16384, 131072, 1048576, 16777216};

    // Number of sorting methods
    public static final int NUM_SORT_METHODS = 3;

    // Number of trials to run each sort method with each array size
    public static final int NUM_TRIALS = 3;

    public static void main(String[] args) {
        IntegerSorter integerSorter = new IntegerSorter();
        Random random = new Random();

        long resultTimes[][][] = new long[NUM_SORT_METHODS][ARRAY_SIZES.length][NUM_TRIALS];

        // loops through all the array sizes
        for (int i = 0; i < ARRAY_SIZES.length; i++) {
            int arraySize = ARRAY_SIZES[i];

            System.out.println("===== SIZE " + arraySize + " =====");

            // generates array and fills it with "arraySize" number of random integers
            int arr[] = new int[arraySize];
            for (int j = 0; j < arraySize; j++) {
                arr[j] = random.nextInt();
            }

            // loops through trials
            for (int trial = 1; trial <= NUM_TRIALS; trial++) {
                System.out.println("--- Trial " + trial + " ---");

                String[] results = new String[NUM_SORT_METHODS];

                // loops through sort methods
                for (int j = 1; j <= NUM_SORT_METHODS; j++) {

                    // copies test array to be passed into sorter
                    int[] arrCopy = arr.clone();
                    integerSorter.setList(arrCopy);

                    // start timing sort
                    long startTime = System.nanoTime();

                    integerSorter.sort(j);

                    // stop timing sort
                    long endTime = System.nanoTime();

                    // calculate elapsed time and store result
                    long elapsedTime = endTime - startTime;
                    if (integerSorter.toString() == "null") {
                        elapsedTime = -1;
                    }
                    resultTimes[j - 1][i][trial - 1] = elapsedTime;
                    System.out.println("Method " + j + ": " + elapsedTime + " nano sec");
                    results[j - 1] = integerSorter.toString();
                }

                // check if all sort methods output the same result
                boolean allSame = true;
                for (int j = 1; j < NUM_SORT_METHODS; j++) {
                    if (!results[j - 1].equals(results[j])) {
                        allSame = false;
                    }
                }

                if (allSame) {
                    System.out.println("Size " + arraySize + ": All " + NUM_SORT_METHODS + " sort methods returned the same result.");
                } else {
                    System.out.println("Size " + arraySize + ": Error: All " + NUM_SORT_METHODS + " sort methods did not return the same result. One or more sort methods may have been killed before completion.");
                }
            }

            // calculates and outputs average of results
            System.out.println("--- Average ---");
            for (int j = 0; j < NUM_SORT_METHODS; j++) {
                long sum = 0;
                for (long time : resultTimes[j][i]) {
                    sum += time;
                }
                double avgNano = sum / (double) NUM_SORT_METHODS;

                System.out.println("Method " + (j + 1) + ": " + avgNano);
            }
        }

        System.out.println("===== COMMA SEPARATED VALUES =====");
        String res = "";
        res += ",";
        for (int arraySize : ARRAY_SIZES) {
            res += "," + arraySize;
        }

        res += "\n";
        for (int i = 0; i < NUM_SORT_METHODS; i++) {
            res += "Method " + (i + 1);
            long total[] = new long[ARRAY_SIZES.length];
            for (int j = 0; j < NUM_TRIALS; j++) {
                res += ",Trial " + (j + 1);
                for (int k = 0; k < ARRAY_SIZES.length; k++) {
                    res += "," + resultTimes[i][k][j];
                    total[k] += resultTimes[i][k][j];
                }
                res += "\n";
            }
            res += ",Average";
            for (int k = 0; k < ARRAY_SIZES.length; k++) {
                res += "," + total[k] / (double) NUM_TRIALS;
            }
            res += "\n";
        }
        System.out.println(res);
    }

}
