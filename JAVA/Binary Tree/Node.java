public class Node {
    private Object data;
    private Node parent, leftChild, rightChild;

    public Node(Object data) {
        this.data = data;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return this.parent;
    }

    public boolean hasLeft() {
        return this.leftChild != null;
    }

    public void setLeftChild(Node child) {
        this.leftChild = child;
        leftChild.setParent(this);
    }

    public Node getLeftChild() {
        return this.leftChild;
    }

    public boolean hasRight() {
        return this.rightChild != null;
    }

    public void setRightChild(Node child) {
        this.rightChild = child;
        rightChild.setParent(this);
    }

    public Node getRightChild() {
        return this.rightChild;
    }

    public boolean isExternal() {
        return (leftChild==null)&&(rightChild==null);
    }

    public boolean isInternal() {
        return (leftChild!=null)&&(rightChild!=null);
    }

    public BinaryTree getAssociatedTree() {
        return new BinaryTree(this);
    }

    public Object getData() {
        return this.data;
    }

    /*--------------
    OVERRIDING SOME OBJECT FUNCTIONS
    --------------*/
    @Override
    public String toString() {
        return String.format("Node with data: "+getData().toString());
    }
}
