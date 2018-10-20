import java.util.Random;

public class SkipList {
    private SkipListNode startNode;
    private Random ran = new Random();

    public SkipList() {
        startNode = new SkipListNode(-1);
        startNode.setbelow(new SkipListNode(-1));
        startNode.setnext(new SkipListNode(-2));
        startNode.below().setnext(new SkipListNode(-2));
    }

    public SkipListNode skipSearch(int key) {
        SkipListNode p = startNode;
        while(p.below() != null) {
            p = p.below();
            while(!p.hasBiggerKey(key))
                p = p.next;
        }
        return p;
    }

    public void Insert(int key, Object o) {
        SkipListNode start = skipSearch(key);
        if(start.getKey() == key)
            while(start!=null) {
                start.setData(o);
                start = start.above();
            }
        else {
            
        }
    }
}
