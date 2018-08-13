public class QueueClassUsingLinkedList<E> {

  private QueueNode<E> head = null, last=null;
  private int size = 0;

  public void enqueue(E data) {
    QueueNode<E> x = new QueueNode<>(data);
    size+=1;
    if (this.isEmpty()) {
      head = x;
      last = x;
    }
    else {
      last.next = x;
      last = x;
    }
  }

  public E dequeue() {
    if (isEmpty())
      return null;
    size-=1;
    E data = head.data;
    head = head.next;
    return data;
  }

  public boolean isEmpty() {
    return head == null;
  }

  private class QueueNode<E> {
    E data;
    QueueNode<E> next = null;
    public QueueNode(E data) {
      this.data = data;
    }
  }

  public int getSize() {
    return this.size;
  }
}
