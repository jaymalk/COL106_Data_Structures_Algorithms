public class NodeGeneric<E> {
    private E data;
    private NodeGeneric<E> parent, leftChild, rightChild;

    public NodeGeneric(E data) {
        this.data = data;
    }

    public void setParent(NodeGeneric<E> parent) {
        this.parent = parent;
    }

    public NodeGeneric<E> getParent() {
        return this.parent;
    }

    public boolean hasLeft() {
        return this.leftChild != null;
    }

    public void setLeftChild(NodeGeneric<E> child) {
        this.leftChild = child;
        leftChild.setParent(this);
    }

    public NodeGeneric<E> getLeftChild() {
        return this.leftChild;
    }

    public boolean hasRight() {
        return this.rightChild != null;
    }

    public void setRightChild(NodeGeneric<E> child) {
        this.rightChild = child;
        rightChild.setParent(this);
    }

    public NodeGeneric<E> getRightChild() {
        return this.rightChild;
    }

    public boolean isExternal() {
        return (leftChild==null)&&(rightChild==null);
    }

    public boolean isInternal() {
        return (leftChild!=null)&&(rightChild!=null);
    }

    public BinaryTreeGeneric<E> getAssociatedTree() {
        return new BinaryTreeGeneric<E>(this);
    }

    public E getData() {
        return this.data;
    }

    /*--------------
    OVERRIDING SOME OBJECT FUNCTIONS
    --------------*/
    @Override
    public String toString() {
        return String.format("NodeGeneric<E> with data: "+getData().toString());
    }
}
