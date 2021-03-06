import java.util.NoSuchElementException;

public class BinaryTreeGeneric<E> {
    private NodeGeneric<E> root;
    private int nodes;

    // Constructors
    public BinaryTreeGeneric(E a) {
        this.root = new NodeGeneric<E>(a);
        nodes = 1;
    }

    public BinaryTreeGeneric(NodeGeneric<E> someNode) {
        this.root = someNode;
    }

    //Node Related Functions
    public NodeGeneric<E> getRoot() {
        return root;
    }

    public int numNodes() {
        return this.nodes;
    }

    public boolean contains(E data) {
        if (root.getData() == data)
            return true;
        boolean right = false, left = false;
        if (root.hasLeft())
            left = getLeftSubTree().contains(data);
        if (root.hasRight())
            right = getRightSubTree().contains(data);
        return (right||left);
    }

    public NodeGeneric<E> getNode(E data) {
        NodeGeneric<E> toReturn = null;
        if(root.getData() == data)
            toReturn = root;
        else if (root.hasLeft())
            if(getLeftSubTree().contains(data))
                toReturn =  getLeftSubTree().getNode(data);
        else if (root.hasRight())
            if (getRightSubTree().contains(data))
                toReturn = getRightSubTree().getNode(data);
        else
            throw new NoSuchElementException("No element with data "+data.toString()+" was found in the tree.");
        return toReturn;
    }

    public void addChild(E parent, E child) {
        if(contains(child))
            throw new IllegalArgumentException("Given node "+child.toString()+" is alreay present in the tree.");
        NodeGeneric<E> parentNode = getNode(parent);
        NodeGeneric<E> childNode = new NodeGeneric<E>(child);
        if(!parentNode.isInternal()) {
            if(!parentNode.hasLeft())
                parentNode.setLeftChild(childNode);
            else
                parentNode.setRightChild(childNode);
            nodes+=1;
        }
        else
            throw new IllegalArgumentException("Given node "+parent.toString()+" already has two children.");
    }

    public int getDepth(E data) {
        if(root.getData() == data)
            return 0;
        return 1+getDepth(getNode(data).getParent().getData());
    }

    public int nodeHeight(NodeGeneric<E> a) {
        if (a == null)
            return -1;
        return 1+Math.max(nodeHeight(a.getLeftChild()), nodeHeight(a.getRightChild()));
    }

    // Tree related functions.
    public BinaryTreeGeneric<E> getLeftSubTree() {
        if(this.root.getLeftChild() == null)
            throw new NullPointerException("The root tree has no left subtree");
        return new BinaryTreeGeneric<E>(this.root.getLeftChild());
    }

    public BinaryTreeGeneric<E> getRightSubTree() {
        if(this.root.getRightChild() == null)
            throw new NullPointerException("The root tree has no right subtree");
        return new BinaryTreeGeneric<E>(this.root.getRightChild());
    }
    public boolean isSuperMost() {
        return this.root.getParent() == null;
    }

    public int getHeight() {
        return nodeHeight(root);
    }

    // Printing the Binary Tree in various fashions...
    public void printPreOrder() {
        System.out.println("- "+this.root.getData());
        if(root.hasLeft())
            getLeftSubTree().printPreOrder(4);
        if(root.hasRight())
            getRightSubTree().printPreOrder(4);
    }

    public void printPreOrder(int intialspace) {
        int i = intialspace;
        while(i--!=0)
            System.out.print("  ");
        System.out.println("- "+this.root.getData());
        if(root.getLeftChild() != null)
            getLeftSubTree().printPreOrder(intialspace+4);
        if(root.getRightChild() != null)
            getRightSubTree().printPreOrder(intialspace+4);
    }

    public void printInOrder() {
        if(root.hasLeft())
            getLeftSubTree().printInOrder(4);
        System.out.println("- "+this.root.getData());
        if(root.hasRight())
            getRightSubTree().printInOrder(4);
    }

    public void printInOrder(int intialspace) {
        int i = intialspace;
        if(root.getLeftChild() != null)
            getLeftSubTree().printInOrder(intialspace+4);
        while(i--!=0)
            System.out.print("  ");
        System.out.println("- "+this.root.getData());
        if(root.getRightChild() != null)
            getRightSubTree().printInOrder(intialspace+4);
    }

    public void printPostOrder() {
        if(root.hasLeft())
            getLeftSubTree().printPostOrder(4);
        if(root.hasRight())
            getRightSubTree().printPostOrder(4);
        System.out.println("- "+this.root.getData());
    }

    public void printPostOrder(int intialspace) {
        int i = intialspace;
        if(root.getLeftChild() != null)
            getLeftSubTree().printPostOrder(intialspace+4);
        if(root.getRightChild() != null)
            getRightSubTree().printPostOrder(intialspace+4);
        while(i--!=0)
            System.out.print("  ");
        System.out.println("- "+this.root.getData());
    }
}
