import java.util.*;

public class MergeSort {

    public static void main(String[] args) {
        int[] unsortedArray = new int[]{5, 7, 1, 6, 9, 4, 3, 8, 16, 57, 11, 19, 12};

        mergeSort(unsortedArray, 0, unsortedArray.length - 1);

        System.out.println(Arrays.toString(unsortedArray));
    }

    private static void mergeSort(int[] array, int start, int end){
        if (start < end) {
            int midpoint = (start + end) / 2;

            mergeSort(array, start, midpoint);
            mergeSort(array, midpoint + 1, end);

            merge(array, start, midpoint, end);
        }
    }

    private static void merge(int[] array, int start, int midpoint, int end){
        int[] leftArray = Arrays.copyOfRange(array, start, midpoint + 1);
        int[] rightArray = Arrays.copyOfRange(array, midpoint + 1, end + 1);

        int leftCounter = 0;
        int rightCounter = 0;

        int counter = start;

        while (counter <= end) {
            if(leftCounter >= leftArray.length) {
                array[counter++] = rightArray[rightCounter++];
                continue;
            }
            if(rightCounter >= rightArray.length) {
                array[counter++] = leftArray[leftCounter++];
                continue;
            }

            int leftItem = leftArray[leftCounter];
            int rightItem = rightArray[rightCounter];

            if (leftItem < rightItem) {
                array[counter++] = leftItem;
                leftCounter++;
            } else {
                array[counter++] = rightItem;
                rightCounter++;
            }
        }
    }
}
