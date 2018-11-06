import java.util.Scanner;

public class HeapSortArray {
    public static void main(String[] args) {
        System.out.print("Enter the size of array: ");
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int[] array = new int[size];
        System.out.println("Enter "+size+" values.");
        while(size--!=0)
            array[size] = in.nextInt();
        bottomUpHeapify(array);
        heapSort(array);
        for(int e: array)
            System.out.print(e+" ");
        System.out.println();
        in.close();
    }

    public static void bottomUpHeapify(int[] array) {
        int i = array.length;
        while((i & (i+1)) != 0)
            i--;
        while(i!=-1) {
            check(i, array);
            i--;
        }
    }

    private static void check(int i, int[] array) {
        check(i, array, array.length);
    }

    private static void check(int i, int[] array, int max_range) {
        if(2*i+2 < max_range)
            if(array[2*i+1]<array[2*i+2]) {
                if(array[2*i+2]>array[i]) {
                    swap(2*i+2, i, array);
                    check(2*i+2, array, max_range);
                }
            }
            else {
                if(array[2*i+1]>array[i])
                    swap(2*i+1, i, array); {
                    check(2*i+1, array, max_range);
                }
            }
        else if (2*i+1 < max_range)
            if(array[2*i+1]>array[i]) {
                swap(i, 2*i+1, array);
                check(2*i+1, array, max_range);
            }
    }

    public static void swap(int pos1, int pos2, int[] array) {
        int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }

    public static void heapSort(int[] array) {
        int cap = array.length;
        while(cap!=0) {
            cap--;
            swap(0, cap, array);
            check(0, array, cap);
        }
    }
}
