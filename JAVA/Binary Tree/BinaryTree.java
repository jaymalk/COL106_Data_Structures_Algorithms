import java.util.NoSuchElementException;

public class BinaryTree {
    private Node root;
    private int nodes;

    // Constructors
    public BinaryTree(Object a) {
        this.root = new Node(a);
        nodes = 1;
    }

    public BinaryTree(Node someNode) {
        this.root = someNode;
    }

    //Node Related Functions
    public Node getRoot() {
        return root;
    }

    public int numNodes() {
        return this.nodes;
    }

    public boolean contains(Object data) {
        if (root.getData() == data)
            return true;
        boolean right = false, left = false;
        if (root.hasLeft())
            left = getLeftSubTree().contains(data);
        if (root.hasRight())
            right = getRightSubTree().contains(data);
        return (right||left);
    }

    public Node getNode(Object data) {
        Node toReturn = null;
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

    public void addChild(Object parent, Object child) {
        if(contains(child))
            throw new IllegalArgumentException("Given node "+child.toString()+" is alreay present in the tree.");
        Node parentNode = getNode(parent);
        Node childNode = new Node(child);
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

    public int getDepth(Object data) {
        if(root.getData() == data)
            return 0;
        return 1+getDepth(getNode(data).getParent().getData());
    }

    public int nodeHeight(Node a) {
        if (a == null)
            return -1;
        return 1+Math.max(nodeHeight(a.getLeftChild()), nodeHeight(a.getRightChild()));
    }

    // Tree related functions.
    public BinaryTree getLeftSubTree() {
        if(this.root.getLeftChild() == null)
            throw new NullPointerException("The root tree has no left subtree");
        return new BinaryTree(this.root.getLeftChild());
    }

    public BinaryTree getRightSubTree() {
        if(this.root.getRightChild() == null)
            throw new NullPointerException("The root tree has no right subtree");
        return new BinaryTree(this.root.getRightChild());
    }
    public boolean isSuperMost() {
        return this.root.getParent() == null;
    }

    public int getHeight() {
        return nodeHeight(root);
    }

    // Printing the Binary Tree in various fashions...
    public void printPreOrder() {
        System.out.println("-| "+this.root.getData());
        if(root.hasLeft())
            getLeftSubTree().printPreOrder(4);
        if(root.hasRight())
            getRightSubTree().printPreOrder(4);
    }

    public void printPreOrder(int intialspace) {
        int i = intialspace;
        while(i--!=0)
            System.out.print("  ");
        System.out.println("-| "+this.root.getData());
        if(root.getLeftChild() != null)
            getLeftSubTree().printPreOrder(intialspace+4);
        if(root.getRightChild() != null)
            getRightSubTree().printPreOrder(intialspace+4);
    }

    public void printInOrder() {
        if(root.hasLeft())
            getLeftSubTree().printInOrder(4);
        System.out.println("-| "+this.root.getData());
        if(root.hasRight())
            getRightSubTree().printInOrder(4);
    }

    public void printInOrder(int intialspace) {
        int i = intialspace;
        if(root.getLeftChild() != null)
            getLeftSubTree().printInOrder(intialspace+4);
        while(i--!=0)
            System.out.print("  ");
        System.out.println("-| "+this.root.getData());
        if(root.getRightChild() != null)
            getRightSubTree().printInOrder(intialspace+4);
    }

    public void printPostOrder() {
        if(root.hasLeft())
            getLeftSubTree().printPostOrder(4);
        if(root.hasRight())
            getRightSubTree().printPostOrder(4);
        System.out.println("-| "+this.root.getData());
    }

    public void printPostOrder(int intialspace) {
        int i = intialspace;
        if(root.getLeftChild() != null)
            getLeftSubTree().printPostOrder(intialspace+4);
        if(root.getRightChild() != null)
            getRightSubTree().printPostOrder(intialspace+4);
        while(i--!=0)
            System.out.print("  ");
        System.out.println("-| "+this.root.getData());
    }
}
