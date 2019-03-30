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

        // loops through all the array sizes
        for (int arraySize : ARRAY_SIZES) {
            System.out.println("===== SIZE " + arraySize + " =====");

            // generates array and fills it with "arraySize" number of random integers
            int arr[] = new int[arraySize];
            for (int i=0; i<arraySize; i++) {
                arr[i] = random.nextInt();
            }

            // creates 2D array to store results from trials
            long[][] resultTimes = new long[NUM_SORT_METHODS][NUM_TRIALS];

            // loops through trials
            for (int trial=1; trial<=NUM_TRIALS; trial++) {
                System.out.println("--- Trial " + trial + " ---");

                String[] results = new String[NUM_SORT_METHODS];

                // loops through sort methods
                for (int i = 1; i <= NUM_SORT_METHODS; i++) {

                    // copies test array to be passed into sorter
                    int[] arrCopy = arr.clone();
                    integerSorter.setList(arrCopy);

                    // start timing sort
                    long startTime = System.nanoTime();

                    integerSorter.sort(i);

                    // stop timing sort
                    long endTime = System.nanoTime();

                    // calculate elapsed time and store result
                    long elapsedTime = endTime - startTime;
                    resultTimes[i-1][trial-1] = elapsedTime;
                    System.out.println("Method " + i + ": " + elapsedTime + " nano sec");
                    results[i - 1] = integerSorter.toString();
                }

                // check if all sort methods output the same result
                boolean allSame = true;
                for (int i = 1; i < NUM_SORT_METHODS; i++) {
                    if (!results[i - 1].equals(results[i])) {
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
            for (int i=0; i<NUM_SORT_METHODS; i++) {
                long sum = 0;
                for (long time : resultTimes[i]) {
                    sum += time;
                }
                double avgNano = sum / (double) NUM_SORT_METHODS;

                System.out.println("Method " + (i+1) + ": " + avgNano);
            }
        }
    }

}
