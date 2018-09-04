public class BinaryTreeNode<E> {
    private E data;
    protected BinaryTreeNode<E> left, right, parent;

    public BinaryTreeNode(E data) {
        this.data = data;
    }

    public E getData() {
        return this.data;
    }

    public boolean isExternal() {
        return (left == null) && (right == null);
    }

    public String toString() {
        return data.toString();
    }

    public BinaryTree<E> getAssociatedTree() {
        return new BinaryTree<E>(this);
    }

}
