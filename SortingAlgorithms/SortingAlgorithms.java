//Lena Gran 
//N03734335

package SortingAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SortingAlgorithms {

    public static int insertionSort(double[] arr) {
        int comparisons = 0;
        for (int i = 1; i < arr.length; i++) {
            double key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                comparisons++;
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
            if (j >= 0) {
                comparisons++; // Count the last comparison
            }
        }
        return comparisons;
    }

    public static int shellSort(double[] arr, String gapSequence) {
        int comparisons = 0;
        int n = arr.length;
        List<Integer> gaps = new ArrayList<>();

        // Generate the gap sequence
        switch (gapSequence.toLowerCase()) {
            case "shell": // Shell sequence: n/2
                for (int gap = n / 2; gap > 0; gap /= 2) {
                    gaps.add(gap);
                }
                break;

            case "hibbard": // Hibbard's sequence: 1, 3, 7, 15, ..., 2^k - 1
                for (int gap = 1; gap < n; gap = 2 * gap + 1) {
                    gaps.add(gap);
                }
                Collections.reverse(gaps);
                break;

            case "knuth": // Knuth's sequence: 1, 2, 14, ..., (3^i + 1) / 2
                int gap = 1;
                while (gap <= n - 1) {
                    for (int i = 0; i < n - 1; i++) {
                        gap = (int) Math.pow(3, i) + 1;
                        gap = gap / 2;
                        if (gap >= n) {
                            break;
                        }
                        gaps.add(gap);
                    }
                }

                Collections.sort(gaps, Collections.reverseOrder());
                break;

            case "gonnet": // Gonnet's sequence: n / 2.2
                gap = (int) Math.floor(n / 2.2);
                while (gap > 0) {
                    gaps.add(gap);
                    gap = (int) Math.floor(gap / 2.2);
                }
                if (gaps.get(gaps.size() - 1) != 1) {
                    gaps.add(1);
                }
                break;

            case "sedgewick": // Sedgewick's sequence: {9*4^i - 9*2^i + 1} U {4^i - 3*2^i + 1} 1, 5, 19, 41,
                              // 109, 209, 505, 929...
                int i = 0;
                while (true) {
                    int gap1 = 9 * (int) Math.pow(4, i) - 9 * (int) Math.pow(2, i) + 1;
                    if (gap1 < n)
                        gaps.add(gap1);

                    if (i >= 2) {
                        int gap2 = (int) Math.pow(4, i) - 3 * (int) Math.pow(2, i) + 1;
                        if (gap2 >= n)
                            break;
                        gaps.add(gap2);
                    }
                    i++;
                }
                Collections.sort(gaps, Collections.reverseOrder());
                break;

            default:
                throw new IllegalArgumentException("Gap sequence: " + gapSequence);
        }

        // sort using gap sequence
        for (int currentGap : gaps) {

            for (int i = currentGap; i < n; i++) {

                double temp = arr[i];
                int j = i;
                while (j >= currentGap && arr[j - currentGap] > temp) {
                    comparisons++;
                    arr[j] = arr[j - currentGap];
                    j -= currentGap;
                }
                // Only count the final comparison if it was actually done
                if (j >= currentGap) {
                    comparisons++;
                }
                arr[j] = temp; // Place temp at the correct position
            }
        }
        return comparisons;
    }

    public static int heapify(double[] arr, int n, int root, int comparisons) {
        int largest = root; // Initialize largest as root
        int left = 2 * root + 1; // Left child index
        int right = 2 * root + 2; // Right child index

        // Compare left child with root
        if (left < n) {
            comparisons++;
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }

        // Compare right child with root
        if (right < n) {
            comparisons++;
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }

        // If largest is not root, swap and continue heapifying
        if (largest != root) {
            // swap
            double temp = arr[root];
            arr[root] = arr[largest];
            arr[largest] = temp;

            // Recursively heapify the affected subtree and pass the comparison count
            comparisons = heapify(arr, n, largest, comparisons);
        }
        return comparisons;
    }

    // Main method to perform heap sort and count comparisons
    public static int heapSort(double[] arr) {
        int n = arr.length;
        int comparisons = 0;

        // Build a max heap from the array
        for (int i = n / 2 - 1; i >= 0; i--) {
            comparisons = heapify(arr, n, i, comparisons);
        }

        // Extract elements from the heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Move the root (largest element) to the end of the array
            double temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Reduce the size of the heap (using i for n) and heapify the root (first
            // element in the array)
            comparisons = heapify(arr, i, 0, comparisons);
        }
        return comparisons;
    }

    public static int mergeSort(double[] array, int left, int right) {
        int comparisons = 0;
        if (left >= right)
            return 0; // Single element - no more comparisons

        int mid = left + (right - left) / 2;

        // break up halves recursively and sort
        comparisons += mergeSort(array, left, mid);
        comparisons += mergeSort(array, mid + 1, right);

        // create temp array and pointers to track subarrays
        double[] temp = new double[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        // Merge the two halves - only place where comparison happens
        while (i <= mid && j <= right) {
            comparisons++;
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        // Copy remaining elements from left half
        while (i <= mid) {
            temp[k++] = array[i++];
        }

        // Copy remaining elements from right half
        while (j <= right) {
            temp[k++] = array[j++];
        }

        // Copy sorted elements back to original array
        System.arraycopy(temp, 0, array, left, temp.length);

        return comparisons;
    }

    public static int quickSort(double[] array, int left, int right) {
        int comparisons = 0;
        if (left >= right) {
            return 0; // no comparisons needed
        }

        double pivot = array[left]; // Choose the first element as the pivot
        int i = left + 1; // left pointer
        int j = right; // right pointer

        // Partition the array
        while (i <= j) { // Continue until pointers cross
            // Move i to right until element larger than pivot is found
            while (i <= j) {
                comparisons++;
                if (array[i] > pivot)
                    break;
                i++;
            }

            // Move j to left until element smaller than pivot is found
            while (i <= j) {
                comparisons++;
                if (array[j] <= pivot)
                    break;
                j--;
            }
            // Swap elements if pointers haven't crossed
            if (i < j) {
                double temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }

        // Place pivot where j is
        double temp = array[left];
        array[left] = array[j];
        array[j] = temp;

        int pivotIndex = j;

        // Recursively sort left and right partitions and add comparisons
        comparisons += quickSort(array, left, pivotIndex - 1);
        comparisons += quickSort(array, pivotIndex + 1, right);

        return comparisons;
    }

    public static double[] generateRandomArray(int size) {
        Random random = new Random();
        double[] array = new double[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextDouble();
        }
        return array;
    }

    public static void generateResults() {
        int[] sizes = { 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000 };
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-6s %-12s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s%n",
                "N", "Insertion", "Shellsort", "Hibbard", "Knuth", "Gonnet", "Sedgewick", "Heapsort", "Mergesort",
                "Quicksort", "NlogN");
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------");

        for (int size : sizes) {
            double[] arr = generateRandomArray(size);
            System.out.println(" ");
            System.out.printf("%-6d %-12d %-10d %-10d %-10d %-10d %-10d %-10d %-10d %-10d %-10d%n",
                    size,
                    insertionSort(arr.clone()),
                    shellSort(arr.clone(), "shell"),
                    shellSort(arr.clone(), "hibbard"),
                    shellSort(arr.clone(), "knuth"),
                    shellSort(arr.clone(), "gonnet"),
                    shellSort(arr.clone(), "sedgewick"),
                    heapSort(arr.clone()),
                    mergeSort(arr.clone(), 0, arr.length - 1),
                    quickSort(arr.clone(), 0, arr.length - 1),
                    (int) (size * Math.log(size) / Math.log(2))); // Approximation of NlogN
        }
    }

    public static void main(String[] args) {
        double[] arr = { 8.0, 1.0, 3.0, 5.0, 10.0, 12.0, 2.0, 4.0, 11.0, 7.0 };

        System.out.println("Insertion Sort: " + insertionSort(arr.clone()));
        System.out.println("Shell Sort (Shell): " + shellSort(arr.clone(), "shell"));
        System.out.println("heapsort: " + heapSort(arr.clone()));
        System.out.println("Mergesort: " + mergeSort(arr.clone(), 0, arr.length - 1));
        System.out.println("Quicksort: " + quickSort(arr.clone(), 0, arr.length - 1));
    }
}
