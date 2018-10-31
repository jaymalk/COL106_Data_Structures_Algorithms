import java.util.*;

public class SkipList123<E extends Comparable<? super E>> {
    // Extreme Nodes (Inf and -iNF)
    private Node<E> start, end;

    // Node class (Private)
    private class Node<T extends Comparable<? super T>> {
        private Node<T> left, right, up, down;
        private T key;
        private boolean ext;

        public Node(T key) {
            this.key = key;
            this.ext = false;
        }

        private Node(T key, boolean ext) {
            this.key = key;
            this.ext = ext;
        }

        // Data
        public T key() {
            return this.key;
        }

        // Extreme node checks (Inf and -iNF)
        public boolean isExtreme() {
            return ext;
        }

        // Linking functions
        public void setLeft(Node<T> left) {
            this.left = left;
        }
        public Node<T> left() {
            return this.left;
        }
        public void setRight(Node<T> right) {
            this.right = right;
        }
        public Node<T> right() {
            return this.right;
        }
        public void setUp(Node<T> up) {
            this.up = up;
        }
        public Node<T> up() {
            return this.up;
        }
        public void setDown(Node<T> down) {
            this.down = down;
        }
        public Node<T> down() {
            return this.down;
        }

        protected boolean equals(Node<E> node) {
            return node.key() == key;
        }

        // Override
        @Override
        public String toString() {
            return String.format("("+key+")");
        }
    }

    // SkipListGeneric
    public SkipList123() {
        this.start = new Node<E>(null, true);
        this.end = new Node<E>(null, true);
        this.start.setRight(end);
        this.end.setLeft(start);
    }

    // Node existence and finding.
    public boolean contains(E key) {
        Node<E> walk = start.down();
        while(walk!=null) {
            if(walk.right().isExtreme())
                if(walk.key().compareTo(key) <= -1) {
                    walk = walk.down();
                }
                else
                    return false;
            else if(walk.right().key().compareTo(key) <= -1) {
                walk = walk.right();
            }
            else if(walk.right().key().compareTo(key) >= 1) {
                walk = walk.down();
            }
            else
                return true;
        }
        return false;
    }

    private Node<E> getNode(E key) throws Exception {
        Node<E> walk = start.down();
        while(walk!=null) {
            if(walk.right().isExtreme())
                if(walk.key().compareTo(key) <= -1)
                    walk = walk.down();
                else
                    break;
            else if(walk.right().key().compareTo(key) <= -1)
                walk = walk.right();
            else if(walk.right().key().compareTo(key) >= 1)
                walk = walk.down();
            else
                return walk.right();
        }
        throw new Exception("No node with data "+key);
    }

    // Addition.
    public void addItem(E key) {
        try {
            add(key);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void add(E key) throws Exception{
        if(start.down() == null) {
            insertInBetween(start, end, new Node<E>(key));
            increaseLevel();
        }
        else {
            GenericStack<Node<E>> leftList = new GenericStack<>();
            leftList.push(start);
            GenericStack<Node<E>> rightList = new GenericStack<>();
            rightList.push(end);
            Node<E> walk = start.down();
            do{
                if(walk.right().isExtreme()) {
                    leftList.push(walk);
                    rightList.push(walk.right());
                    walk = walk.down();
                }
                else if(walk.right().key().compareTo(key) <= -1)
                    walk = walk.right();
                else if(walk.right().key().compareTo(key) >= 1) {
                    leftList.push(walk);
                    rightList.push(walk.right());
                    walk = walk.down();
                }
                else {
                    throw new Exception("Entry already exists. DuleftListication not allowed.");
                }
            } while(walk!=null);
            add(new Node<E>(key), leftList, rightList);
        }
    }

    private void add(Node<E> toAdd, GenericStack<Node<E>> leftList, GenericStack<Node<E>> rightList) {
        Node<E> left = leftList.pop();
        Node<E> right = rightList.pop();
        Random r = new Random();
        insertInBetween(left, right, toAdd);
        while(!leftList.isEmpty()) {
            left = leftList.pop();
            right = rightList.pop();
            if(countChildrenInBetween(left, right) <= 3)
                return;
            else {
                if(r.nextBoolean())
                    toAdd = right.down().left().left();
                else
                    toAdd = left.down().right().right();
                insertInBetween(left, right, new Node<E>(toAdd.key()));
                toAdd.setUp(left.right());
                left.right().setDown(toAdd);
            }
        }
        increaseLevel();
    }

    // Deletion.
    public void deleteItem(E key) {
        try {
            delete(key);
            if(start.down().right().equals(end.down()))
                reduceLevel();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void delete(E key) throws Exception{
        Node<E> delete = getNode(key);
        do {
            removeFromBetween(delete);
            delete = delete.down();
        }while(delete!=null);
    }

    // Level addition and removal.
    private void increaseLevel() {
        start.setUp(new Node<E>(null, true));
        start.up().setDown(start);
        end.setUp(new Node<E>(null, true));
        end.up().setDown(end);
        start = start.up();
        end = end.up();
        start.setRight(end);
        end.setLeft(start);
    }

    private void reduceLevel() {
        start = start.down();
        end = end.down();
    }

    // Adding and removing in/from between.
    private void insertInBetween(Node<E> left, Node<E> right, Node<E> _new) {
        left.setRight(_new);
        right.setLeft(_new);
        _new.setLeft(left);
        _new.setRight(right);
    }

    private void removeFromBetween(Node<E> node) {
        node.right().setLeft(node.left());
        node.left().setRight(node.right());
    }

    // Getting to the bottom most level
    private Node<E> getToBottom() {
        Node<E> temp = start;
        while(temp.down()!=null)
            temp = temp.down();
        return temp;
    }

    //Counting children of height one less in between two nodes of same level.
    private int countChildrenInBetween(Node<E> first, Node<E> second) {
        try {
            assert first.right() == second : "Arguments placed incorrectly.";
            first = first.down();
            second = second.down();
            int count =0;
            while(first!=second) {
                first = first.right();
                count++;
            }
            return count-1;
        }
        catch(AssertionError e) {
            System.out.println(e.getMessage());
            return 4;
        }
    }

    // Size of the list.
    public int size() {
        Node<E> walk = getToBottom().right();
        int size = 0;
        while(walk.key()!=null) {
            walk = walk.right();
            size++;
        }
        return size;
    }

    // Height of the list.
    private int height() {
        return nodeHeight(start);
    }

    public void printHeight() {
        System.out.println(height());
    }

    // Node height in a list.
    private int nodeHeight(Node<E> node){
            int height=0;
            while(node!=null) {
                node = node.down();
                height++;
            }
            return height-1;
    }

    private int nodeHeight(E key) {
        try {
            Node<E> node = getNode(key);
            return nodeHeight(node);
        }
        catch(Exception e) {
            return -1;
        }
    }

    public void printNodeHeight(E key) {
        if(nodeHeight(key) == -1)
            System.out.println("No such node in list.");
        else
            System.out.println(nodeHeight(key));
    }

    // String override.
    @Override
    public String toString() {
        String s = "";
        for(int i=height(); i>=0; i--) {
            Node<E> walk = getToBottom().right();
            s = s+"|  | ";
            while(walk.key()!=null) {
                if(nodeHeight(walk.key()) >= i)
                    s = s+ ""+walk.toString()+" ";
                else {
                    String str = "";
                    for(int j=0; j<walk.toString().length(); j++)
                        str = str+" ";
                    s = s+ ""+str+" ";
                }
                walk = walk.right();
            }
            s = s + "|  |\n";
        }
        return s;
    }
}
