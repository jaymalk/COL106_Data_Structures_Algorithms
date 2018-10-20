public class HeapSort {
    MinHeapUsingArrays h;

    public HeapSort() {
        h = new MinHeapUsingArrays(0);
        h.removeMin();
    }

    public void heapSort(int[] arr) {
        for(int e: arr)
            h.Insert(e);
        for(int i=0; i<arr.length; i++)
            arr[i] = h.removeMin();
    }
}
