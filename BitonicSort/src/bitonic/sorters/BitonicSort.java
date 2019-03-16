package bitonic.sorters;


import bitonic.ICallBack;

import java.util.Arrays;
import java.util.Observable;

public class BitonicSort implements Runnable {

    private int[] arrayToSort;
    private int arrayLenght;
    private boolean sortUp;
    private int low;
    private ICallBack callBack;

    public BitonicSort(int[] array, int low, int length, boolean up, ICallBack callBack) {
        this.arrayToSort = array;
        this.arrayLenght = length;
        this.sortUp      = up;
        this.low         = low;
        this.callBack    = callBack;
    }

    @Override
    public void run() {
        System.out.println("Starting sorter");
        this.bsort(this.arrayToSort, this.low, this.arrayToSort.length, this.sortUp);
        System.out.println("Done sorting");
    }

    private void bsort(int array[], int low, int cnt, boolean up)
    {
        if (cnt>1) {
            int k = cnt/2;
            // ascending order
            bsort(array, low, k, true);
            // descending order
            bsort(array,low+k, k, false);
            // Merge in ascending order
            merge(array, low, cnt, up);
        }
    }

    private void merge(int array[], int low, int cnt, boolean up) {
        if (cnt>1) {
            int k = cnt/2;
            for (int i=low; i<low+k; i++) {
                compAndSwap(array,i, i+k, up);
            }
            merge(array,low, k, up);
            merge(array,low+k, k, up);
        }
    }

    private void compAndSwap(int array[], int i, int j, boolean up) {
        if ((array[i] > array[j] && up) || (array[i] < array[j] && !up)) {
            // Swapping values
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
