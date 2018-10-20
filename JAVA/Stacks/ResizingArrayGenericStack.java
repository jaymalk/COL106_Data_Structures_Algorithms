public class ResizingArrayGenericStack<Item> {

  //private Item[] s = new Item[1];
  // JAVA DOESN'T SUPPORT GENERIC ARRAY CREATION
  // SO, WE WILL OPT FOR THE 'UGLY CASTING' BELOW
  private Item[] s = (Item[]) new Object[1];
  // REMEMBER, A GOOD CODE DOESN'T HAVE ANY CASTING !

  private int N = 0;

  public boolean isEmpty() {
    return N == 0;
  }

  public void push(Item item) {
    if(N == s.length) resize(2*s.length);
    s[N++] = item;
  }

  public void resize(int capacity) {

    Item[] copy = (Item[]) new Object[capacity];
    // SAME PROBLEM AS ABOVE...

    for(int i=0; i<N; i++)
      copy[i] = s[i];
    s = copy;
  }
  // AMORTIZED RESIZING
  // OVERALL TAKES O(N) TIME TO ADD N ITEMS
  // MUCH BETTER COMPARED TO GENERAL O(N*N)

  public Item pop() {
    if(isEmpty())
      return null;
    //return s[--N];
    // PREVENTING LOITERINNG
    Item item = s[--N];
    s[N] = null;
    if(N>0 && N == s.length/4) resize(s.length/2);
    return item;
  }
}
