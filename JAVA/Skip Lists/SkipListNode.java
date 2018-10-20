public class SkipListNode {
    protected SkipListNode next, prev, above, below;
    private Object data;
    private int key;

    public SkipListNode(Object... inp) {
        if(inp.length == 1)
            key = (int)inp[0];
        if(inp.length == 2)
            data = inp[1];
    }

    public int getKey() {
        return this.key;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object o) {
        this.data = o;
    }

    public SkipListNode next() {
        return this.next;
    }

    public void setnext(SkipListNode toset) {
        this.next = toset;
    }

    public SkipListNode prev() {
        return this.prev;
    }

    public void setprev(SkipListNode toset) {
        this.prev = toset;
    }

    public SkipListNode above() {
        return this.above;
    }

    public void setabove(SkipListNode toset) {
        this.above = toset;
    }

    public SkipListNode below() {
        return this.below;
    }

    public void setbelow(SkipListNode toset) {
        this.below = toset;
    }

    public boolean hasBiggerKey(int k) {
        if(key == -1)
            return false;
        if(key == -2)
            return true;
        return key>k;
    }
}
