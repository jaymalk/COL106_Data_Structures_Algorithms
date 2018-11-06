
import java.util.Random;

public class Sort {

    // SWAPPING OF ELEMENTS IN AN ARRAY
    private static <X extends Comparable<X>> void swap(int pos1, int pos2, X[] items) {
        X temp = items[pos1];
        items[pos1] = items[pos2];
        items[pos2] = temp;
    }

    //\\//\\//\\//\\//\\//\\//\\//\\//\\
    // SELECTION SORT ALGORITHM (NATURAL)
    public static <X extends Comparable<X>> X[] selectionSort(X[] items) {
        for(int i=0; i<items.length; i++) {
            X item = items[i];
            int pos = i;
            for(int j=i+1; j<items.length; j++) {
                if(item.compareTo(items[j])>0) {
                    item = items[j];
                    pos = j;
                }
            }
            items[pos] = items[i];
            items[i] = item;
        }
        return items;
    }

    //\\//\\//\\//\\//\\//\\//\\//\\//\\
    // INSERTION SORT ALGORITHM (NATURAL)
    public static <X extends Comparable<X>> X[] insertionSort(X[] items) {
        for(int i=0; i<items.length; i++) {
            int j;
            X item = items[i];
            for(j=0; j<i; j++)
                if(items[j].compareTo(item)>0) {
                    item = items[j];
                    items[j] = items[i];
                    items[i] = item;
                    break;
                }
            while(j++<i) {
                item = items[j];
                items[j] = items[i];
                items[i] = item;
            }
        }
        return items;
    }

    //\\//\\//\\//\\//\\//\\//\\//\\//\\
    // BUBBLE SORT ALGORITHM (NATURAL)
    public static <X extends Comparable<X>> X[] bubbleSort(X[] items) {
        for(int i = items.length-1; i>0; i--) {
            for(int j=0; j<i; j++) {
                if(items[j].compareTo(items[j+1])>0) {
                    swap(j, j+1, items);
                }
            }
        }
        return items;
    }

    //\\//\\//\\//\\//\\//\\//\\//\\//\\
    // QUICK SORT ALGORITHM (USES LAST ELEMENT AS PIVOT)
    public static <X extends Comparable<X>> X[] quickSort(X[] items) {
        quickSort(items, 0, items.length-1);
        return items;
    }

    private static <X extends Comparable<X>> void quickSort(X[] items, int left, int right) {
        if(right<=left)
            return;
        int pos = partition(items, left, right);
        quickSort(items, left, pos-1);
        quickSort(items, pos+1, right);
    }

    // PARTITIONING FOR QUICK SORT USIGN LAST ELEMENT AS PIVOT
    private static <X extends Comparable<X>> int partition(X[] items, int left, int right) {
        X pivot = items[right];
        int l = left-1, r = right;
        while(true) {
            while(items[++l].compareTo(pivot)<0);
            while(items[--r].compareTo(pivot)>0)
                if(r == left)
                    break;
            if(l>=r)
                break;
            swap(l, r, items);
        }
        swap(l, right, items);
        return l;
    }

    //\\//\\//\\//\\//\\//\\//\\//\\//\\
    // MERGE SORT ALGORITHM (TENTATIVE NATURAL)
    public static <X extends Comparable<X>> X[] mergeSort(X[] items) {
        mergeSort(items, 0, items.length-1);
        return items;
    }

    private static <X extends Comparable<X>> void mergeSort(X[] items, int left, int right) {
        if(left>=right)
            return;
        mergeSort(items, left, (left+right)/2);
        mergeSort(items, (left+right)/2+1, right);
        merge(items, left, (left+right)/2, right);
    }

    // SORTED ARRAY MERGING USING AUXILARY ARRAY
    @SuppressWarnings("unchecked")
    private static <X extends Comparable<X>> void merge(X[] items, int left, int mid, int right) {
        Object[] aux = new Object[right-left+1];
        int i, j, k;
        for(i = left, j = mid+1, k=0; i<mid+1 && j<right+1;k++) {
            if(items[i].compareTo(items[j])>0)
                aux[k] = items[j++];
            else
                aux[k] = items[i++];
        }
        if(i == mid+1) {
            while(j<right+1)
                aux[k++] = items[j++];
        }
        else {
            while(i<mid+1)
                aux[k++] = items[i++];
        }
        for(i=0; i<aux.length; i++) {
            items[left+i] = (X)aux[i];
        }
    }

    //\\//\\//\\//\\//\\//\\//\\//\\//\\
    // HEAPSORT ALGORITHM (NATURAL, USING BOTTUM UP CONSTRUCTION)
    public static <X extends Comparable<X>> X[] heapSort(X[] items) {
        bottomUpHeapify(items);
        int cap = items.length;
        while(cap--!=0) {
            swap(0, cap, items);
            check(0, items, cap);
        }
        return items;
    }

    // IN-PLACE BOTTUM UP CONSTRUCTION OF HEAP FROM ARRAY
    private static <X extends Comparable<X>> void bottomUpHeapify(X[] array) {
        int i = array.length;
        while((i & (i+1)) != 0)
            i--;
        while(i!=-1) {
            check(i, array);
            i--;
        }
    }

    private static <X extends Comparable<X>> void check(int i, X[] array) {
        check(i, array, array.length);
    }

    private static <X extends Comparable<X>> void check(int i, X[] array, int max_range) {
        if(2*i+2 < max_range)
            if(array[2*i+1].compareTo(array[2*i+2])<0) {
                if(array[2*i+2].compareTo(array[i])>0) {
                    swap(2*i+2, i, array);
                    check(2*i+2, array, max_range);
                }
            }
            else {
                if(array[2*i+1].compareTo(array[i])>0) {
                    swap(2*i+1, i, array);
                    check(2*i+1, array, max_range);
                }
            }
        else if (2*i+1 < max_range)
            if(array[2*i+1].compareTo(array[i])>0) {
                swap(i, 2*i+1, array);
                check(2*i+1, array, max_range);
            }
    }


    //\\//\\//\\//\\//\\//\\//\\//\\//\\
}
