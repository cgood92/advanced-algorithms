import java.util.*;

public class MergeSort {

    public static void main(String[] args) {
        int[] unsortedArray = new int[]{5, 7, 1, 6, 9, 4, 3, 8, 16, 57, 11, 19, 12};

        mergeSort(unsortedArray, 0, unsortedArray.length - 1);

        System.out.println(Arrays.toString(unsortedArray));
    }

    private static void mergeSort(int[] array, Integer start, Integer end){
        if (start < end) {
            Integer midpoint = (start + end) / 2;

            mergeSort(array, start, midpoint);
            mergeSort(array, midpoint + 1, end);

            merge(array, start, midpoint, end);
        }
    }

    private static void merge(int[] array, Integer start, Integer midpoint, Integer end){
        int[] leftArray = Arrays.copyOfRange(array, start, midpoint + 1);
        int[] rightArray = Arrays.copyOfRange(array, midpoint + 1, end + 1);

        Integer leftCounter = 0;
        Integer rightCounter = 0;

        Integer counter = start;

        while (counter <= end) {
            if(leftCounter >= leftArray.length) {
                array[counter++] = rightArray[rightCounter++];
                continue;
            }
            if(rightCounter >= rightArray.length) {
                array[counter++] = leftArray[leftCounter++];
                continue;
            }

            Integer leftItem = leftArray[leftCounter];
            Integer rightItem = rightArray[rightCounter];

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
