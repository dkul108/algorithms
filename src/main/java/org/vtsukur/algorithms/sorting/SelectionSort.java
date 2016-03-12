package org.vtsukur.algorithms.sorting;

import static org.vtsukur.algorithms.sorting.SortUtils.less;
import static org.vtsukur.algorithms.sorting.SortUtils.swap;

/**
 * @author volodymyr.tsukur
 */
public class SelectionSort<T extends Comparable<T>> implements Sort<T> {

    public void sort(final T[] array) {
        for (int i = 0; i < array.length - 1; ++i) {
            int min = i;
            for (int j = i + 1; j < array.length; ++j) {
                if (less(array[j], array[min])) {
                    min = j;
                }
            }
            swap(array, i, min);
        }
    }

    @Override
    public String toString() {
        return "selection sort";
    }

}