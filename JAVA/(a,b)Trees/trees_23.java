public class trees_23<E extends Comparable<? super E>> {
    Node<E> head;

    private class Node<T extends Comparable<? super T>> {
        private miniNode<T>[] nodes;
        private Node<T>[] children;
        int childnum, keys;

        public Node() {
            nodes = new miniNode[2];
            children = new Node[3];
            childnum = 0;
            keys = 0;
        }

        public miniNode<T> node(int i) {
            assert i<keys && i>=0 : "Not in range.";
            return nodes[i];
        }

        public void setNode(int i, T key) {
            nodes[i] = new miniNode<>(key, this);
        }

        public Node<T> child(int i) {
            assert i<childnum && i>=0 : "Not in range.";
            return children[i];
        }

        public void setChild(int i, Node<T> child) {
            children[i] = child;
        }
    }

    private class miniNode<T extends Comparable<? super T>> {
        private T key;
        private Node<T> container;

        public miniNode(T key, Node<T> container) {
            this.key = key;
            this.container = container;
        }

        public T key() {
            return key;
        }

        public Node<T> container() {
            return container;
        }
    }

    public trees_23() {
        this.head = new Node<E>();
    }

    public trees_23(Node<E> head) {
        this.head = head;
    }

    public boolean isEmpty() {
        return this.head.keys == 0;
    }
}
