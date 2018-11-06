public class ResizingArrayGenericQueue<Item> {
  @SuppressWarnings("unchecked")
  Item[] q = (Item[]) new Object[1];
  // UGLY CASTING...  (HELPLESS)

  private int first = 0, last = 0;

  public void enqueue(Item item) {
    q[last] = item;
    last = (last+1)%q.length;
    if(last == first) resize(2*q.length);
  }

  public Item dequeue() {
    Item item = q[first];
    q[first] = null;
    first = (first+1)%q.length;
    if(Math.abs(last-first)<=q.length/4) resize(q.length/2);
    return item;
  }

  @SuppressWarnings("unchecked")
  public void resize(int new_capacity) {

    Item[] copy = (Item[]) new Object[new_capacity];
    // UGLY CASTING... (HELPLESS)

    if(q[first] == null) {
      first = 0;
      last = 0;
      q = copy;
    }

    int i=first, j=0;
    do {
      copy[j] = q[i];
      j++;
      i = (i+1)%q.length;
    } while(q[i]!=null && i != first);
    first = 0;
    last = j%(copy.length);
    q = copy;

  }

  public boolean isEmpty() {
    return (q[first] == null);
  }

  public void printQueue() {
    for(Item s: q)
      System.out.print(s+" ");
    System.out.println();
  }
}
