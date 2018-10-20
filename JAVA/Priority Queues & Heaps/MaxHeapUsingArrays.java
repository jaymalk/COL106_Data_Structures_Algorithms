import java.util.Arrays;

public class MaxHeapUsingArrays {
    int heapArray[], lastAdd;

    public MaxHeapUsingArrays(int first) {
        heapArray = new int[3];
        Arrays.fill(heapArray, -1);
        heapArray[0] = first;
        lastAdd = 0;
    }

    public MaxHeapUsingArrays() {
        heapArray = new int[1];
        heapArray[0] = -1;
        lastAdd = -1;
    }

    public void Insert(int a) {
        if(lastAdd+1 == heapArray.length)
            heapArray = increaseheight(heapArray);
        if(lastAdd%2 == 0) {
            lastAdd+=1;
            heapArray[lastAdd] = a;
        }
        else {
            lastAdd+=1;
            heapArray[lastAdd] = a;
        }
        bubbleUpHeapify(lastAdd);
    }

    public void bubbleUpHeapify(int index) {
        if(index<=0)
            return;
        int child = heapArray[index];
        int parent = heapArray[(index-1)/2];
        if(child<=parent)
            return;
        else {
            swapData(index, (index-1)/2);
            bubbleUpHeapify((index-1)/2);
        }
    }

    public int removeMax() {
        int removed = heapArray[0];
        heapArray[0] = heapArray[lastAdd];
        heapArray[lastAdd] = -1;
        lastAdd -= 1;
        bubbleDownHeapify(0);
        if(lastAdd<heapArray.length/3)
            heapArray = decreaseHeight(heapArray);
        return removed;
    }

    public void bubbleDownHeapify(int index) {
        if(isExternal(index))
            return;
        if(right(index) == -1) {
            if(heapArray[left(index)] > heapArray[index])
                swapData(left(index), index);
        }
        else {
            if(heapArray[left(index)] > heapArray[right(index)]) {
                if(heapArray[left(index)] > heapArray[index]) {
                    swapData(index, left(index));
                    bubbleDownHeapify(left(index));
                }
            }
            else {
                if(heapArray[right(index)] > heapArray[index]) {
                    swapData(index, right(index));
                    bubbleDownHeapify(right(index));
                }
            }
        }
    }

    public int left(int index) {
        if(2*index+1 >= heapArray.length)
            return -1;
        if(heapArray[2*index+1] == -1)
            return -1;
        return 2*index+1;
    }

    public int right(int index) {
        if(2*index+2 >= heapArray.length)
            return -1;
        if(heapArray[2*index+2] == -1)
            return -1;
        return 2*index+2;
    }

    public boolean isExternal(int index) {
        boolean is = false;
        if(left(index)!=-1)
            is = is || (heapArray[left(index)] != -1);
        if(right(index)!=-1)
            is = is || (heapArray[right(index)] != -1);
        return !is;
    }

    public void swapData(int index1, int index2) {
        int temp = heapArray[index1];
        heapArray[index1] = heapArray[index2];
        heapArray[index2] = temp;
    }

    public int[] increaseheight(int[] arr) {
        int[] newArr = new int[arr.length*2+1];
        Arrays.fill(newArr, -1);
        for(int i=0; i<arr.length; i++)
            newArr[i] = arr[i];
        return newArr;
    }

    public int[] decreaseHeight(int[] arr) {
        int[] newArr = new int[arr.length/2];
        Arrays.fill(newArr, -1);
        for(int i=0; i<=lastAdd; i++)
            newArr[i] = arr[i];
        return newArr;
    }

    public void printHeap(int... values) {
        int i=0, space=0;
        if(values.length == 2){
            i = values[0];
            space = values[1];
        }
        if(i>=heapArray.length)
            return;
        if(heapArray[i] == -1)
            return;
        printHeap(2*i+1, space+2);
        for(int k = 0; k<space; k++)
            System.out.print("   ");
        System.out.println("  > "+heapArray[i]);
        printHeap(2*i+2, space+2);
    }
}
