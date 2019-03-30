/*
 *  Author: Michael Pu
 *  Teacher: Mr. Radulovic
 *  Date: 2019/3/29
 *  Course: ICS4U
 */

import java.util.Arrays;

/**
 * IntegerSorter class with three different sorting methods.
 * It also has a timeout setting which will stop the sort when the timeout is passed for the first two sorting methods.
 */

public class IntegerSorter implements Sorter {

    // Time in nanoseconds to be passed before a sort method is stopped.
    public static final long KILL_TIME = 1_000_000_000L * 60L * 10L;


    // Number of iterations that should be passed in the sorting method before the time elapsed is checked
    // (checking elapsed time every loop can take quite a bit of time, which may distort the results)
    public static final long CHECK_INTERVAL_COUNT = 10_000_000_000L;

    private int[] list;

    // Sort method 1
    private void sort1() {
        long startTime = System.nanoTime();
        long itercount = 0;
        for (int end = list.length - 1; end > 0; end--) {
            for (int i = 0; i < end; i++) {
                itercount++;
                if (itercount > CHECK_INTERVAL_COUNT) {
                    itercount = 0;
                    if (System.nanoTime() - startTime > KILL_TIME) {
                        System.out.println("Killed.");
                        list = null;
                        return;
                    }
                }
                if (list[i] > list[i + 1]) {
                    int temp = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = temp;
                }
            }
        }
    }

    // Sort method 2
    private void sort2() {
        long startTime = System.nanoTime();
        long itercount = 0;
        for (int start = 0; start < list.length - 1; start++) {
            for (int i = start + 1; i < list.length; i++) {
                itercount++;

                if (itercount > CHECK_INTERVAL_COUNT) {
                    itercount = 0;
                    if (System.nanoTime() - startTime > KILL_TIME) {
                        System.out.println("Killed.");
                        list = null;
                        return;
                    }
                }
                if (list[start] > list[i]) {
                    int temp = list[i];
                    list[i] = list[start];
                    list[start] = temp;
                }
            }
        }
    }

    // Combines two sorted arrays into one sorted array in place (for sort method 3)
    private void combine(int arr1Start, int arr1End, int arr2Start, int arr2End) {

        int[] combinedArr = new int[arr2End - arr1Start];

        int arr1Point = 0;
        int arr2Point = 0;
        for (int i = 0; i < arr2End - arr1Start; i++) {
            if (arr1Point == arr1End - arr1Start) {
                combinedArr[i] = list[arr2Start + arr2Point];
                arr2Point++;
            } else if (arr2Point == arr2End - arr2Start) {
                combinedArr[i] = list[arr1Start + arr1Point];
                arr1Point++;
            } else {
                if (list[arr1Start + arr1Point] < list[arr2Start + arr2Point]) {
                    combinedArr[i] = list[arr1Start + arr1Point];
                    arr1Point++;
                } else {
                    combinedArr[i] = list[arr2Start + arr2Point];
                    arr2Point++;
                }
            }
        }

        System.arraycopy(combinedArr, 0, list, arr1Start, arr2End - arr1Start);
    }

    // Sort method 3 (in place)
    private void sort3(int[] bounds) {
        if (bounds[1] - bounds[0] > 1) {
            int[] arr1Bounds = {bounds[0], (bounds[1] + bounds[0]) / 2};
            int[] arr2Bounds = {(bounds[1] + bounds[0]) / 2, bounds[1]};
            sort3(arr1Bounds);
            sort3(arr2Bounds);
            combine(arr1Bounds[0], arr1Bounds[1], arr2Bounds[0], arr2Bounds[1]);
        }
    }

    @Override
    public void setList(int[] list) {
        this.list = list;
    }

    @Override
    public void sort(int type) {
        switch (type) {
            case 1:
                sort1();
                break;
            case 2:
                sort2();
                break;
            case 3:
                int[] sort3Bounds = {0, list.length};
                sort3(sort3Bounds);
                break;
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(this.list);
    }
}
