import java.util.Iterator;

public class AVLTreeGeneric<E extends Comparable<E>> implements Iterable<E> {
    Node<E> root;

    private class Node<T extends Comparable<T>> {
        private T key;
        private Node<T> left, right, parent;

        public Node(T key, Node<T> parent) {
            this.key = key;
            this.parent = parent;
        }

        public boolean isExternal() {
            return (left == null) && (right == null);
        }

        public boolean isInternal() {
            return (left != null) && (right != null);
        }

        public boolean hasLeft() {
            return left != null;
        }

        public Node<T> getLeft() {
            return left;
        }

        public void setLeft(Node<T> newLeft) {
            this.left = newLeft;
        }

        public boolean isLeftChild() {
            Node<T> parent = getParent();
            if(parent != null)
                if(parent.hasLeft())
                    return parent.getLeft().equals(this);
            return false;
        }

        public boolean hasRight() {
            return right != null;
        }

        public Node<T> getRight() {
            return right;
        }

        public void setRight(Node<T> newRight) {
            this.right = newRight;
        }

        public boolean isRightChild() {
            Node<T> parent = getParent();
            if(parent != null)
                if(parent.hasRight())
                    return parent.getRight().equals(this);
            return false;
        }

        public Node<T> getParent() {
            return parent;
        }

        public void setParent(Node<T> newParent) {
            this.parent = newParent;
        }

        public T key() {
            return this.key;
        }

        protected void changeKey(T key) {
            this.key = key;
        }

        public boolean equals(Node<T> node) {
            return node.key().compareTo(this.key()) == 0;
        }

        @Override
        public String toString() {
            return String.format("Key: "+key());
        }
    }

    // CONSTRUCTORS
    public AVLTreeGeneric() {
        root = null;
    }

    public AVLTreeGeneric(Node<E> root) {
        this.root = root;
    }

    // AVL Structural Constraint Check

    public void AVLCheck(Node<E> checkNode) {
        if(checkNode == null)
            return;
        if(Math.abs(nodeHeight(checkNode.getLeft()) - nodeHeight(checkNode.getRight())) <= 1) { // BALANCED
            AVLCheck(checkNode.getParent());
            return;
        }
        if(nodeHeight(checkNode.getLeft()) - nodeHeight(checkNode.getRight()) > 1) {    // LEFT HEAVY
            if(nodeHeight(checkNode.getLeft().getLeft()) > nodeHeight(checkNode.getLeft().getRight())) {
                Node<E> x = checkNode;
                rightRotate(x);
            }
            else {
                Node<E> y = checkNode.getLeft();
                leftRotate(y);
                rightRotate(checkNode);
            }
        }
        else {                                                                          // RIGHT HEAVY
            if(nodeHeight(checkNode.getRight().getRight()) > nodeHeight(checkNode.getRight().getLeft())) {
                Node<E> x = checkNode;
                leftRotate(x);
            }
            else {
                Node<E> y = checkNode.getRight();
                rightRotate(y);
                leftRotate(checkNode);
            }
        }
    }

    private void leftRotate(Node<E> parent) {
        Node<E> rightChild = parent.getRight();
        rightChild.setParent(parent.getParent());
        if(parent.getParent()!=null) {
            if(parent.isLeftChild())
                parent.getParent().setLeft(rightChild);
            else
                parent.getParent().setRight(rightChild);
        }
        parent.setRight(rightChild.getLeft());
        if(rightChild.hasLeft())
            rightChild.getLeft().setParent(parent);
        parent.setParent(rightChild);
        rightChild.setLeft(parent);
    }

    private void rightRotate(Node<E> parent) {
        Node<E> leftChild = parent.getLeft();
        leftChild.setParent(parent.getParent());
        if(parent.getParent()!=null) {
            if(parent.isLeftChild())
                parent.getParent().setLeft(leftChild);
            else
                parent.getParent().setRight(leftChild);
        }
        parent.setLeft(leftChild.getRight());
        if(leftChild.hasRight())
            leftChild.getRight().setParent(parent);
        parent.setParent(leftChild);
        leftChild.setRight(parent);
    }

    // NODE RELATED OPERATIONS
    public Node<E> getRoot() {
        return this.root;
    }

    public E getRootKey(){
        return getRoot().key();
    }

    private int nodeHeight(Node<E> node) {
        if(node == null)
            return -1;
        if(node.isExternal())
            return 0;
        else
            return 1+Math.max(nodeHeight(node.getLeft()), nodeHeight(node.getRight()));
    }

    public void nodeHeight(E key) {
        if(contains(key)) {
            System.out.println(nodeHeight(getNode(key)));
        }
        else {
            System.out.println("Node with key: "+key+" doesn't exist in the tree");
        }
    }

    private int nodeDepth(Node<E> node) {
        if(node == null)
            return -1;
        return 1+nodeDepth(node.getParent());
    }

    public void nodeDepth(E key) {
        if(contains(key)) {
            System.out.println(nodeDepth(getNode(key)));
        }
        else {
            System.out.println("Node with key: "+key+" doesn't exist in the tree");
        }
    }

    public void addNewElement(E key) {
        try {
            addItem(key);
        }
        catch(Exception e) {
            System.out.println("Node already exists. Repetition not allowed.");
        }
        setNewRoot();
    }

    private void addItem(E key) {
        if(contains(key))
            throw new IllegalArgumentException("Node repetition not allowed.");
        if(isEmpty()) {
            root = new Node<E>(key, null);
        }
        else if(getRoot().key().compareTo(key) >= 0) { // root.key() >= key
            if(getRoot().hasLeft())
                leftSubTree().addItem(key);
            else {
                getRoot().setLeft(new Node<E>(key, getRoot()));
                AVLCheck(getRoot());
            }
        }
        else {                                          // root.key() <= key
            if(getRoot().hasRight())
                rightSubTree().addItem(key);
            else {
                getRoot().setRight(new Node<E>(key, getRoot()));
                AVLCheck(getRoot());
            }
        }
    }

    public void deleteItem(E key) {
        try {
            Node<E> node = getNode(key);
            deleteItem(node);
            setNewRoot();
        }
        catch(Exception e) {
            System.out.println("No such node in the tree");
        }
    }

    private void deleteItem(Node<E> node) {
        if(node.isExternal()) {
            if(node.getParent() != null) {
                if(node.isLeftChild())
                    node.getParent().setLeft(null);
                else
                    node.getParent().setRight(null);
                AVLCheck(node.getParent());
            }
            else
                root = null;
        }
        else if(node.isInternal()) {
            try {
                Node<E> temp = getInorderPredecessor(node);
                Node<E> succ = getInorderSuccessor(node);
                if(nodeDepth(succ) > nodeDepth(temp))
                    temp = succ;
                node.changeKey(temp.key());
                deleteItem(temp);
            }
            catch(Exception e) {
                System.out.println("This condition should never come.");
            }
        }
        else {
            if(node.hasLeft()) {
                node.getLeft().setParent(node.getParent());
                if(node.getParent() != null)
                    if(node.isLeftChild())
                        node.getParent().setLeft(node.getLeft());
                    else
                        node.getParent().setRight(node.getLeft());
            }
            else {
                node.getRight().setParent(node.getParent());
                if(node.getParent() != null)
                    if(node.isLeftChild())
                        node.getParent().setLeft(node.getRight());
                    else
                        node.getParent().setRight(node.getRight());
            }
            AVLCheck(node.getParent());
        }
    }

    public boolean contains(E key) {
        if(isEmpty())
            return false;
        if(root.key().equals(key))
            return true;
        try {
            return (leftSubTree().contains(key) || rightSubTree().contains(key));
        }
        catch(Exception e) {
            return false;
        }
    }

    public Node<E> getNode(E key) {
        if(contains(key)) {
            if(root.key().equals(key))
                return root;
            else if(root.hasLeft())
                if(leftSubTree().contains(key))
                    return leftSubTree().getNode(key);
            return rightSubTree().getNode(key);
        }
        throw new NullPointerException("No node with key "+key+" present in the tree.");
    }

    // NODE ACCESS OPERATIONS
    public Node<E> getInorderSuccessor(E key) throws Exception{
        if(contains(key)) {
            Node<E> temp = getNode(key);
            return getInorderSuccessor(temp);
        }
        throw new NullPointerException("No such node present in the tree.");
    }

    public Node<E> getInorderSuccessor(Node<E> givenNode) throws Exception {
        if(givenNode.hasRight()) {
            givenNode = givenNode.getRight();
            while(givenNode.hasLeft())
                givenNode = givenNode.getLeft();
            return givenNode;
        }
        else {
            Node<E> ancestor = givenNode.getParent();
            while ((ancestor != null) && (givenNode.isRightChild())) {
                givenNode = ancestor;
                ancestor = givenNode.getParent();
            }
            if (ancestor == null)
                throw new NullPointerException("The element is the largest in the tree.");
            return ancestor;
        }
    }

    public Node<E> getInorderPredecessor(E key) throws Exception {
        if(contains(key)) {
            Node<E> temp = getNode(key);
            return getInorderSuccessor(temp);
        }
        throw new NullPointerException("No such node present in the tree.");
    }

    public Node<E> getInorderPredecessor(Node<E> givenNode) throws Exception {
        if(givenNode.hasLeft()) {
            givenNode = givenNode.getLeft();
            while(givenNode.hasRight())
                givenNode = givenNode.getRight();
            return givenNode;
        }
        else {
            Node<E> ancestor = givenNode.getParent();
            while ((ancestor != null) && (givenNode.isLeftChild())) {
                givenNode = ancestor;
                ancestor = givenNode.getParent();
            }
            if (ancestor == null)
                throw new NullPointerException("The element is the smallest in the tree.");
            return ancestor;
        }
    }

    public void getRange(E key) {
        if(!contains(key)) {
            if(isEmpty()) {
                System.out.println("-iNF & Inf");
                return;
            }
            if(getRoot().key().compareTo(key) == 1) {
                if(!getRoot().hasLeft()) {
                    try {
                        System.out.print(getSuperTree().getInorderPredecessor(getRoot()).key());
                    }
                    catch(Exception e) {
                        System.out.print("-iNF");
                    }
                    System.out.println(" & " + getRoot().key());

                }
                else {
                    leftSubTree().getRange(key);
                }
            }
            if(getRoot().key().compareTo(key) == -1) {
                if(!getRoot().hasRight()) {
                    System.out.print(getRoot().key()+" & ");
                    try {
                        System.out.println(getSuperTree().getInorderSuccessor(getRoot()).key());
                    }
                    catch(Exception e) {
                        System.out.println("Inf");
                    }
                }
                else {
                    rightSubTree().getRange(key);
                }
            }
            return;
        }
        throw new IllegalArgumentException("Key already exists in the tree.");
    }

    // TREE RELATED OPERATIONS
    public boolean isEmpty() {
        return root == null;
    }

    public int totalNodes() {
        if(isEmpty())
            return 0;
        return 1+leftSubTree().totalNodes()+rightSubTree().totalNodes();
    }

    public int treeHeight() {
        return nodeHeight(root);
    }

    public void getMin() {
        if(isEmpty())
            return;
        if(!root.hasLeft())
            System.out.println(root);
        else
            leftSubTree().getMin();
    }

    public void getMax() {
        if(isEmpty())
            return;
        if(!root.hasRight())
            System.out.println(root);
        else
            rightSubTree().getMax();
    }

    public AVLTreeGeneric<E> leftSubTree() {
        return new AVLTreeGeneric<E>(getRoot().getLeft());
    }

    public AVLTreeGeneric<E> rightSubTree() {
        return new AVLTreeGeneric<E>(getRoot().getRight());
    }

    public AVLTreeGeneric<E> getSuperTree() {
        Node<E> temp = getRoot();
        while(temp.getParent() != null)
            temp = temp.getParent();
        return new AVLTreeGeneric<E>(temp);
    }

    public boolean isSuperTree() {
        return root.getParent() == null;
    }

    public void clearTree() {
        if(isSuperTree())
            root = null;
        else
            System.out.println("Tree is a subtree. I am not allowed to clear it.");
    }

    // PRINTING THE TREE
    public void treeLikeInorder(int... param) {
        if(isEmpty())
            return;
        int space = 0;
        if(param.length == 1)
            space = param[0];
        leftSubTree().treeLikeInorder(space+2);
        for(int i=0; i<space; i++)
            System.out.print("     ");
        System.out.println("("+root.key()+")");
        rightSubTree().treeLikeInorder(space+2);
    }

    public void inorderPrint() {
        Iterator<E> it = iterator();
        while(it.hasNext())
            System.out.println(it.next());
    }

    private void setNewRoot() {
        while(root.getParent()!=null)
            root = root.getParent();
    }

    // ITERATOR
    public Iterator<E> iterator() {
        return new InorderIterator();
    }

    private class InorderIterator implements Iterator<E> {
        private Node<E> current;

        public  InorderIterator() {
            current = getRoot();
            while(current.hasLeft())
                current = current.getLeft();
        }

        public boolean hasNext() {
            return current != null;
        }

        public E next() {
            E data = current.key();
            try {
                current = getInorderSuccessor(current);
            }
            catch(Exception e) {
                current = null;
            }
            return data;
        }

        public void remove() {
            deleteItem(current);
        }
    }
}
