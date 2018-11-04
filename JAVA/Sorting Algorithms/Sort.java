
import java.util.Random;

public class Sort {
    private static <X extends Comparable<X>> void swap(int pos1, int pos2, X[] items) {
        X temp = items[pos1];
        items[pos1] = items[pos2];
        items[pos2] = temp;
    }

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

    public static <X extends Comparable<X>> X[] insertionSort(X[] items) {
        for(int i=0; i<items.length; i++) {
            int pos = i;
            X item = items[i];
            boolean flag = false;
            for(int j=0; j<pos; j++) {
                if(flag) {
                    items[i] = items[j];
                    items[j] = item;
                }
                if(items[j].compareTo(item)>0 && !flag) {
                    items[i] = items[j];
                    items[j] = item;
                    flag = true;
                }
            }
        }
        return items;
    }

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
}
