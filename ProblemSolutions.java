/******************************************************************
 *
 *   Julia Harper / 272 Data Structures
 *
 *   This java file contains the problem solutions for the methods selectionSort,
 *   mergeSortDivisibleByKFirst, asteroidsDestroyed, and numRescueCanoes methods.
 *
 ********************************************************************/

import java.util.Arrays;

public class ProblemSolutions {

    /**
     * Method SelectionSort
     *
     * This method performs a selection sort. This file will be spot checked,
     * so ENSURE you are performing a Selection Sort!
     *
     * This is an in-place sorting operation that has two function signatures. This
     * allows the second parameter to be optional, and if not provided, defaults to an
     * ascending sort. If the second parameter is provided and is false, a descending
     * sort is performed.
     *
     * @param values        - int[] array to be sorted.
     * @param ascending     - if true, method performs an ascending sort, else descending.
     *                        There are two method signatures allowing this parameter
     *                        to not be passed and defaulting to 'true' (ascending sort).
     */
    public void selectionSort(int[] values) {
        selectionSort(values, true);
    }

    public static void selectionSort(int[] values, boolean ascending) {
        int n = values.length;
        for (int i = 0; i < n - 1; i++) {
            int idx = i; // index of current min/max element
            for (int j = i + 1; j < n; j++) {
                if (ascending && values[j] < values[idx]) {
                    idx = j; // update min index
                } else if (!ascending && values[j] > values[idx]) {
                    idx = j; // update max index
                }
            }
            int temp = values[i];  // swap current element with min/max
            values[i] = values[idx];
            values[idx] = temp;
        }
    } // End method selectionSort

    /**
     * Method mergeSortDivisibleByKFirst
     *
     * This method will perform a merge sort algorithm. However, all numbers
     * that are divisible by the argument 'k' are returned first in the sorted
     * list. Example:
     *      values = {10, 3, 25, 8, 6}, k = 5
     *      Sorted result should be --> {10, 25, 3, 6, 8}
     *
     *      values = {30, 45, 22, 9, 18, 39, 6, 12}, k = 6
     *      Sorted result should be --> {30, 18, 6, 12, 9, 22, 39, 45}
     *
     * As shown above, this is a normal merge sort operation, except for the numbers
     * divisible by 'k' are first in the sequence.
     *
     * @param values    - input array to sort per definition above
     * @param k         - value k, such that all numbers divisible by this value are first
     */
    public void mergeSortDivisibleByKFirst(int[] values, int k) {
        // Protect against bad input values
        if (k == 0 || values.length <= 1) return;
        mergeSortDivisibleByKFirst(values, k, 0, values.length - 1);
    }

    /**
     * Private helper method to recursively split array for merge sort
     */
    private void mergeSortDivisibleByKFirst(int[] arr, int k, int left, int right) {
        if (left >= right) return; // base case
        int mid = left + (right - left) / 2;
        mergeSortDivisibleByKFirst(arr, k, left, mid);      // sort left half
        mergeSortDivisibleByKFirst(arr, k, mid + 1, right); // sort right half
        mergeDivisibleByKFirst(arr, k, left, mid, right);   // merge sorted halves
    }

    /**
     * Private helper method for merging two halves
     * Numbers divisible by k come first, then non-divisible
     */
    private void mergeDivisibleByKFirst(int[] arr, int k, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];
    int index = 0;

    // Collect divisible by k from both halves (order doesn't matter)
    for (int i = left; i <= right; i++) {
        if (arr[i] % k == 0) temp[index++] = arr[i];
    }

    // Collect non-divisible by k, then sort them
    int[] nonDiv = new int[right - left + 1 - index];
    int nonIdx = 0;
    for (int i = left; i <= right; i++) {
        if (arr[i] % k != 0) nonDiv[nonIdx++] = arr[i];
    }
    Arrays.sort(nonDiv);

    for (int i = 0; i < nonDiv.length; i++) {
        temp[index++] = nonDiv[i];
    }

    // Copy back to original array
    for (int i = 0; i < temp.length; i++) {
        arr[left + i] = temp[i];
    }
}


    /**
     * Method asteroidsDestroyed
     *
     * You are given an integer 'mass', which represents the original mass of a planet.
     * You are further given an integer array 'asteroids', where asteroids[i] is the mass
     * of the ith asteroid.
     *
     * You can arrange for the planet to collide with the asteroids in any arbitrary order.
     * If the mass of the planet is greater than or equal to the mass of the asteroid, the
     * asteroid is destroyed and the planet gains the mass of the asteroid. Otherwise, the
     * planet is destroyed.
     *
     * Return true if possible for all asteroids to be destroyed. Otherwise, return false.
     */
    public static boolean asteroidsDestroyed(int mass, int[] asteroids) {
        Arrays.sort(asteroids); // sort asteroids to destroy smallest first
        for (int asteroid : asteroids) {
            if (mass >= asteroid) mass += asteroid; // destroy asteroid and increase mass
            else return false; // planet cannot destroy asteroid
        }
        return true; // all asteroids destroyed
    }

    /**
     * Method numRescueSleds
     *
     * You are given an array people where people[i] is the weight of the ith person,
     * and an infinite number of rescue sleds where each sled can carry a maximum weight
     * of limit. Each sled carries at most two people at the same time, provided the
     * sum of the weight of those people is at most limit. Return the minimum number
     * of rescue sleds to carry every given person.
     */
    public static int numRescueSleds(int[] people, int limit) {
        Arrays.sort(people); // sort people weights
        int i = 0, j = people.length - 1, sleds = 0;
        while (i <= j) {
            if (people[i] + people[j] <= limit) i++; // pair lightest and heaviest if possible
            j--; // heaviest always goes in a sled
            sleds++; // increment sled count
        }
        return sleds; // return total sleds needed
    }
} // End Class ProblemSolutions
