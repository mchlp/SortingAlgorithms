import java.util.Random;

public class SortTester {

    public static final int[] ARRAY_SIZES = {16, 128, 1024, 16384, 131072, 1048576, 16777216};
    public static final int NUM_SORT_METHODS = 3;
    public static final int NUM_TRIALS = 3;

    public static void main(String[] args) {
        IntegerSorter integerSorter = new IntegerSorter();
        Random random = new Random();

        for (int arraySize : ARRAY_SIZES) {
            System.out.println("===== SIZE " + arraySize + " =====");
            int arr[] = new int[arraySize];
            for (int i=0; i<arraySize; i++) {
                arr[i] = random.nextInt();
            }

            long[][] resultTimes = new long[NUM_SORT_METHODS][NUM_TRIALS];
            for (int trial=1; trial<=NUM_TRIALS; trial++) {
                System.out.println("--- Trial " + trial + " ---");

                String[] results = new String[NUM_SORT_METHODS];
                for (int i = 1; i <= NUM_SORT_METHODS; i++) {
                    int[] arrCopy = arr.clone();
                    integerSorter.setList(arrCopy);
                    long startTime = System.nanoTime();
                    integerSorter.sort(i);
                    long endTime = System.nanoTime();
                    long elapsedTime = endTime - startTime;
                    resultTimes[i-1][trial-1] = elapsedTime;
                    System.out.println("Method " + i + ": " + elapsedTime + " nano sec");
                    results[i - 1] = integerSorter.toString();
                }

                boolean allSame = true;
                for (int i = 1; i < NUM_SORT_METHODS; i++) {
                    if (!results[i - 1].equals(results[i])) {
                        allSame = false;
                    }
                }

                if (allSame) {
                    System.out.println("Size " + arraySize + ": All " + NUM_SORT_METHODS + " sort methods returned the same result.");
                } else {
                    System.out.println("Size " + arraySize + ": Error: All " + NUM_SORT_METHODS + " sort methods did not return the same result.");
                }
            }
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
