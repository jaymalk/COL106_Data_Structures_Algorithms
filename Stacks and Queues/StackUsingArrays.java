
public class StackUsingArrays {
  private int last=-1;
  private int[] stack = new int[10];

  public void push(int a) {
    if(last+1 == this.stack.length)
      this.stack = makeNewStack(this.stack);
    this.stack[++last] = a;
  }

  public int pop() {
    if (this.isEmpty())
      return 0;
    return this.stack[last--];
  }

  public boolean isEmpty() {
    return this.last==-1;
  }

  private int[] makeNewStack(int[] oldStack) {
    int[] newStack = new int[2*oldStack.length];
    for(int i=0; i<oldStack.length; i++)
      newStack[i] = oldStack[i];
    return newStack;
  }
}
