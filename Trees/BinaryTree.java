public class BinaryTree<E> {
    protected BinaryTreeNode<E> root;


    // CONSTRUCTORS
    public BinaryTree() {
        root = null;
    }

    public BinaryTree(BinaryTreeNode<E> root) {
        this.root = root;
    }

    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
        root = new BinaryTreeNode<E>(data);
        if(leftTree.equals(this) || rightTree.equals(this))
            throw new IllegalArgumentException("A tree cannot be its own subtree...");
        if(leftTree != null) {
            root.left = leftTree.root;
            leftTree.root.parent = root;
        }
        if(rightTree != null) {
            root.right = rightTree.root;
            rightTree.root.parent = root;
        }
    }

    //EMPTY CHECK
    public boolean isEmpty() {
        return root == null;
    }

    // GETTING RIGHT AND LEFT SUBTREES
    public BinaryTree<E> getLeftSubTree() {
        if(!isEmpty() && root.left!=null) {
            root.left.parent = root;
            return new BinaryTree<E>(root.left);
        }
        else
            return null;
    }

    public BinaryTree<E> getRightSubTree() {
        if(!isEmpty() && root.right!=null) {
            root.right.parent = root;
            return new BinaryTree<E>(root.right);
        }
        else
            return null;
    }

    //SETTING RIGHT AND LEFT TREE
    public void setLeftSubTree(BinaryTree<E> lSbT) {
        if(root.left!=null && getSuperMostTree().containsData(lSbT.root.getData()))
            throw new IllegalArgumentException("Tree already has a left subtree");
        this.root.left = lSbT.root;
    }

    public void setRightSubTree(BinaryTree<E> rSbT) {
        if(root.right!=null && getSuperMostTree().containsData(rSbT.root.getData()))
            throw new IllegalArgumentException("Tree already has a right subtree");
        this.root.right = rSbT.root;
    }

    //DELETING A TREE/SUBTREE
    public void deleteTree() {
        if (isEmpty())
            throw new IllegalArgumentException("Tree is already empty");
        if(root.parent == null)
            root = null;
        else {
            if(root.parent.left.equals(root))
                root.parent.left = null;
            else
                root.parent.right = null;
        }
    }

    // FINDING WHETHER THE NODE (OR THE TREE) IS LEAF/EXTERNAL
    public boolean isLeaf() {
        if(isEmpty())
            throw new NullPointerException("Tree is Empty...");
        return root.isExternal();
    }

    // WORKING WITH THE IDEA OR PARENT
    public BinaryTree<E> getParentTree() {
        if(isEmpty())
            throw new NullPointerException("Tree is Empty...");
        if(root.parent == null)
            throw new IllegalArgumentException("The tree is supermost!");
        return new BinaryTree<E>(root.parent);
    }

    public boolean isSupermostTree() {
        if(isEmpty())
            throw new NullPointerException("Tree is Empty...");
        return root.parent == null;
    }

    public BinaryTree<E> getSuperMostTree() {
        BinaryTree<E> temp = this;
        while(!temp.isSupermostTree())
            temp = temp.getParentTree();
        return temp;
    }

    // DATA RELATED PROBLEMS
    public boolean containsData(E data) {
        if(isEmpty())
            return false;
        if(root.getData().equals(data))
            return true;
        if(isLeaf())
            return false;
        return (getLeftSubTree().containsData(data))||(getRightSubTree().containsData(data));
    }

    // PREORDER PRINT OF THE TREE
    public void printTreePreOrder(int i) {
        for(int k=i; k>0; k--)
            System.out.print("   ");
        if(!isEmpty())
            System.out.println(root.getData());
        if(getLeftSubTree()!=null)
            getLeftSubTree().printTreePreOrder(i+1);
        if(getRightSubTree()!=null)
            getRightSubTree().printTreePreOrder(i+1);
    }
}
