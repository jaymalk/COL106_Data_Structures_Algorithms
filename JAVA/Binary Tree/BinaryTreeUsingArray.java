public class BinaryTreeUsingArray {
    private Object[] treeArray;
    private int root;

    public BinaryTreeUsingArray(Object o) {
        treeArray = new Object[3];
        treeArray[0] = o;
        root = 0;
    }

    public BinaryTreeUsingArray(int rootIndex, Object[] array) {
        treeArray = array;
        root = rootIndex;
    }

    // Tree Related Operations
    public BinaryTreeUsingArray leftSubtree() {
        if(hasLeftChild(root))
            return new BinaryTreeUsingArray(2*root+1, treeArray);
        else
            throw new NullPointerException("There is no Left Subtree!");
    }

    public BinaryTreeUsingArray rightSubtree() {
        if(hasRightChild(root))
            return new BinaryTreeUsingArray(2*root+2, treeArray);
        else
            throw new NullPointerException("There is no Right Subtree!");
    }

    /* VERY IMPORTANT FUNCTION WHEN WORKING WITH ARRAYS */
    public void isValidIndex(int index) throws Exception {
        if(index>treeArray.length) {
            throw new NullPointerException("Invalid Index for Parent.");
        }
        if(treeArray[index] == null) {
            throw new IllegalArgumentException("The Index is Empty.");
        }
    }

    public void addChild(int indexOfParent, Object o) {
        try {
            isValidIndex(indexOfParent);
            if(containsObject(o)) {
                System.out.println("Object "+o+" already exists in the tree...");
                return;
            }
            if(2*indexOfParent+1>=treeArray.length) {
                treeArray = doubleSize(treeArray);
                treeArray[2*indexOfParent+1] = o;
            }
            else {
                if(!hasLeftChild(indexOfParent)) {
                    treeArray[2*indexOfParent+1] = o;
                }
                else if(!hasRightChild(indexOfParent)) {
                    treeArray[2*indexOfParent+2] = o;
                }
                else
                    System.out.println("Node at "+indexOfParent+" already has both children.");
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int totalNodes() {
        if(isExternal(root))
            return 1;
        else if (isInternal(root))
            return 1+leftSubtree().totalNodes()+rightSubtree().totalNodes();
        else
            if(hasLeftChild(root))
                return 1+leftSubtree().totalNodes();
            else
                return 1+rightSubtree().totalNodes();
    }

    public boolean containsObject(Object o) {
        boolean flag = false;
        if(treeArray[root] == o)
            flag = true;
        if(hasLeftChild(root))
            flag = flag || leftSubtree().containsObject(o);
        if(hasRightChild(root))
            flag = flag || rightSubtree().containsObject(o);
        return flag;
    }

    public Object getRootIndex() {
        return root;
    }

    // Node Related Operations
    public boolean isExternal(int index){
        try {
            isValidIndex(index);
            if(2*index+1>=treeArray.length)
                return true;
            if(2*index+2>=treeArray.length)
                return treeArray[2*index+1] == null;
            if((treeArray[2*index+1] == null)&&(treeArray[2*index+2] == null))
                return true;
            return false;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean isInternal(int index) {
        try {
            if(2*index+2<treeArray.length)
                if((treeArray[2*index+1] != null)&&(treeArray[2*index+2] != null))
                    return true;
            return false;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean hasLeftChild(int index) {
        try {
            if(2*index+1>=treeArray.length)
                return false;
            return treeArray[2*index+1] != null;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean hasRightChild(int index) {
        try {
            if(2*index+2>=treeArray.length)
                return false;
            return treeArray[2*index+2] != null;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public int getIndex(Object o) {
        if(!containsObject(o))
            return -1;
        else {
            int neededIndex = -1;
            if(treeArray[root] == o)
                neededIndex = root;
            else if(hasRightChild(root))
                if(rightSubtree().containsObject(o))
                    neededIndex = rightSubtree().getIndex(o);
            else if(hasLeftChild(root))
                if(leftSubtree().containsObject(o))
                    neededIndex = leftSubtree().getIndex(o);
            return neededIndex;
        }
    }

    // Printing the Tree in various ways!
    public void preOrderPrint(int... values) {
        int i=0, space=0;
        if(values.length == 0) {
            i = root;
            space = 0;
        }
        else if(values.length == 2){
            i = values[0];
            space = values[1];
        }
        if(treeArray.length<=i)
            return;
        if(treeArray[i] == null)
            return;
        for(int k = 0; k<space; k++)
            System.out.print("   ");
        System.out.println("  - "+treeArray[i]);
        preOrderPrint(2*i+1, space+2);
        preOrderPrint(2*i+2, space+2);
    }

    public void inOrderPrint(int... values) {
        int i=0, space=0;
        if(values.length == 0) {
            i = root;
            space = 0;
        }
        else if(values.length == 2){
            i = values[0];
            space = values[1];
        }
        if(treeArray.length<=i)
            return;
        if(treeArray[i] == null)
            return;
        inOrderPrint(2*i+1, space+2);
        for(int k = 0; k<space; k++)
            System.out.print("   ");
        System.out.println("  - "+treeArray[i]);
        inOrderPrint(2*i+2, space+2);
    }

    public void postOrderPrint(int... values) {
        int i=0, space=0;
        if(values.length == 0) {
            i = root;
            space = 0;
        }
        else if(values.length == 2){
            i = values[0];
            space = values[1];
        }
        if(treeArray.length<=i)
            return;
        if(treeArray[i] == null)
            return;
        postOrderPrint(2*i+1, space+2);
        postOrderPrint(2*i+2, space+2);
        for(int k = 0; k<space; k++)
            System.out.print("   ");
        System.out.println("  - "+treeArray[i]);
    }



    // Size Computations for Array
    public Object[] doubleSize(Object[] arr) {
        Object[] newArr = new Object[arr.length*2+1];
        for(int i=0; i<arr.length; i++)
            newArr[i] = arr[i];
        return newArr;
    }
}
