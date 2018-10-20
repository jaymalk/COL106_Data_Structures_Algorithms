public class AVLTree<Item> {
    Node<Item> root;

    public class Node<E> {
        private int key;
        private E data;
        private Node<E> left, right, parent;

        public Node(int key, E data, Node<E> parent) {
            this.data = data;
            this.parent = parent;
            this.key = key;
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

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> newLeft) {
            this.left = newLeft;
        }

        public boolean isLeftChild() {
            Node<E> parent = getParent();
            if(parent != null)
                if(parent.hasLeft())
                    return parent.getLeft().equals(this);
            return false;
        }

        public boolean hasRight() {
            return right != null;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> newRight) {
            this.right = newRight;
        }

        public boolean isRightChild() {
            Node<E> parent = getParent();
            if(parent != null)
                if(parent.hasRight())
                    return parent.getRight().equals(this);
            return false;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> newParent) {
            this.parent = newParent;
        }

        public E data() {
            return this.data;
        }

        public void setData(E data) {
            this.data = data;
        }

        public int key() {
            return this.key;
        }

        public boolean equals(Node<E> e) {
            return (e.key() == key) && (e.data() == data);
        }

        @Override
        public String toString() {
            return String.format("Key: "+key()+" Data: "+data());
        }
    }

    // CONSTRUCTORS
    public AVLTree() {
        root = null;
    }

    public AVLTree(Node<Item> root) {
        this.root = root;
    }

    // AVL Structural Constraint Check

    public void AVLCheck(Node<Item> checkNode) {
        if(checkNode == null)
            return;
        if(Math.abs(nodeHeight(checkNode.getLeft()) - nodeHeight(checkNode.getRight())) <= 1) { // BALANCED
            AVLCheck(checkNode.getParent());
            return;
        }
        if(nodeHeight(checkNode.getLeft()) - nodeHeight(checkNode.getRight()) > 1) {    // LEFT HEAVY
            if(nodeHeight(checkNode.getLeft().getLeft()) > nodeHeight(checkNode.getLeft().getRight())) {
                Node<Item> x = checkNode;
                rightRotate(x);
            }
            else {
                Node<Item> y = checkNode.getLeft();
                leftRotate(y);
                rightRotate(checkNode);
            }
        }
        else {                                                                          // RIGHT HEAVY
            if(nodeHeight(checkNode.getRight().getRight()) > nodeHeight(checkNode.getRight().getLeft())) {
                Node<Item> x = checkNode;
                leftRotate(x);
            }
            else {
                Node<Item> y = checkNode.getRight();
                rightRotate(y);
                leftRotate(checkNode);
            }
        }
    }

    private void leftRotate(Node<Item> parent) {
        Node<Item> rightChild = parent.getRight();
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

    private void rightRotate(Node<Item> parent) {
        Node<Item> leftChild = parent.getLeft();
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
    public Node<Item> getRoot() {
        return this.root;
    }

    private int nodeHeight(Node<Item> n) {
        if(n == null)
            return -1;
        if(n.isExternal())
            return 0;
        else
            return 1+Math.max(nodeHeight(n.getLeft()), nodeHeight(n.getRight()));
    }

    public void nodeHeight(int k) {
        if(contains(k)) {
            System.out.println(nodeHeight(getNode(k)));
        }
        else {
            System.out.println("Node with key: "+k+" doesn't exist in the tree");
        }
    }

    public void addNewElement(int k, Item e) {
        addItem(k, e);
        setNewRoot();
    }

    private void addItem(int k, Item e) {
        if(isEmpty()) {
            root = new Node<Item>(k, e, null);
        }
        else if(getRoot().key() >= k) {
            if(getRoot().hasLeft())
                leftSubTree().addItem(k, e);
            else {
                getRoot().setLeft(new Node<Item>(k, e, getRoot()));
                AVLCheck(getRoot());
            }
        }
        else {
            if(getRoot().hasRight())
                rightSubTree().addItem(k, e);
            else {
                getRoot().setRight(new Node<Item>(k, e, getRoot()));
                AVLCheck(getRoot());
            }
        }
    }

    public void deleteItem(int k) {
        // NOT NEEDED RIGHT NOW...
    }

    public boolean contains(int k) {
        if(isEmpty())
            return false;
        if(root.key() == k)
            return true;
        try {
            return (leftSubTree().contains(k) || rightSubTree().contains(k));
        }
        catch(Exception e) {
            return false;
        }
    }

    private Node<Item> getNode(int k) {
        if(contains(k)) {
            if(root.key() == k)
                return root;
            else if(root.hasLeft())
                if(leftSubTree().contains(k))
                    return leftSubTree().getNode(k);
            return rightSubTree().getNode(k);
        }
        throw new NullPointerException("No node with key "+k+" present in the tree.");
    }

    // NODE ACCESS OPERATIONS

    public void getData(int k) {
        try {
            System.out.println(getNode(k).data());
        }
        catch(NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void changeData(int k, Item newData) {
        try {
            getNode(k).setData(newData);
        }
        catch(NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public Node<Item> getInorderSuccessor(int k) throws Exception{
        if(contains(k)) {
            Node<Item> temp = getNode(k);
            return getInorderSuccessor(temp);
        }
        throw new NullPointerException("No such node present in the tree.");
    }

    public Node<Item> getInorderSuccessor(Node<Item> givenNode) throws Exception {
        if(givenNode.hasRight()) {
            givenNode = givenNode.getRight();
            while(givenNode.hasLeft())
                givenNode = givenNode.getLeft();
            return givenNode;
        }
        else {
            Node<Item> ancestor = givenNode.getParent();
            while ((ancestor != null) && (givenNode.isRightChild())) {
                givenNode = ancestor;
                ancestor = givenNode.getParent();
            }
            if (ancestor == null)
                throw new NullPointerException("The element is the largest in the tree.");
            return ancestor;
        }
    }

    public Node<Item> getInorderPredecessor(int k) throws Exception {
        if(contains(k)) {
            Node<Item> temp = getNode(k);
            return getInorderSuccessor(temp);
        }
        throw new NullPointerException("No such node present in the tree.");
    }

    public Node<Item> getInorderPredecessor(Node<Item> givenNode) throws Exception {
        if(givenNode.hasLeft()) {
            givenNode = givenNode.getLeft();
            while(givenNode.hasRight())
                givenNode = givenNode.getRight();
            return givenNode;
        }
        else {
            Node<Item> ancestor = givenNode.getParent();
            while ((ancestor != null) && (givenNode.isLeftChild())) {
                givenNode = ancestor;
                ancestor = givenNode.getParent();
            }
            if (ancestor == null)
                throw new NullPointerException("The element is the smallest in the tree.");
            return ancestor;
        }
    }

    public void getRange(int k) {
        if(!contains(k)) {
            if(isEmpty()) {
                System.out.println("-iNF & Inf");
                return;
            }
            if(k<getRoot().key()) {
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
                    leftSubTree().getRange(k);
                }
            }
            if(k>getRoot().key()) {
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
                    rightSubTree().getRange(k);
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

    public void treeHeight() {
        nodeHeight(root.key());
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
            System.out.println("Key: "+root.key()+" Data: "+root.data());
        else
            rightSubTree().getMax();
    }

    public AVLTree<Item> leftSubTree() {
        return new AVLTree<Item>(getRoot().getLeft());
    }

    public AVLTree<Item> rightSubTree() {
        return new AVLTree<Item>(getRoot().getRight());
    }

    public AVLTree<Item> getSuperTree() {
        Node<Item> temp = getRoot();
        while(temp.getParent() != null)
            temp = temp.getParent();
        return new AVLTree<Item>(temp);
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
    public void Inorder(int... param) {
        if(isEmpty())
            return;
        int space = 0;
        if(param.length == 1)
            space = param[0];
        leftSubTree().Inorder(space+2);
        for(int i=0; i<space; i++)
            System.out.print("     ");
        System.out.println("("+root.key()+", "+root.data()+")");
        rightSubTree().Inorder(space+2);
    }

    private void setNewRoot() {
        while(root.getParent()!=null)
            root = root.getParent();
    }
}
