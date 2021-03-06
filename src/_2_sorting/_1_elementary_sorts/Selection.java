package _2_sorting._1_elementary_sorts;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Selection {

    public static void main(String[] args) {
        Integer[] array = {0, 9, 2, 7, 6, 4, 3, 8, 1, 5, 11, 12, 14, 18, 16, 17, 19, 10, 13, 15};
        sort(array);
        Arrays.stream(array).forEach(x -> StdOut.print(x + " "));
    }

    static void sort(Comparable[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i+1; j < arr.length; j++) {
                if (less(arr[j], arr[min])) {
                    min = j;
                }
            }
            swap(arr, i, min);
        }
    }

    private static void swap(Comparable[] arr, int i, int j) {
        Comparable tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }
}
