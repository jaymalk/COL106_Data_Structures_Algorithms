
public class StackClassUsingLinkedList<E> {

  private StackNode first = null;
  private int size = 0;

  private class StackNode {
    E data;
    StackNode next;
  }

  public boolean isEmpty() {
    return first==null;
  }

  public void push(E data_entry) {
    StackNode second = first;
    first = new StackNode();
    first.data = data_entry;
    first.next = second;
    this.size+=1;
  }

  public E pop() {
    if (this.isEmpty())
      return null;
    E data = first.data;
    first = first.next;
    this.size-=1;
    return data;
  }

  public int size() {
    return this.size;
  }

}
