
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class DisjointSet<T> {
    private final Map<T, T> parent = new HashMap<>();
    private final Map<T, Integer> rank = new HashMap<>();

    // MAKE-SET(x)
    public void makeSet(T x) {
        if (!parent.containsKey(x)) {
            parent.put(x, x);
            rank.put(x, 0);
        }
    }

    // FIND-SET(x)
    public T findSet(T x) {
        if (!x.equals(parent.get(x))) {
            T root = findSet(parent.get(x));
            parent.put(x, root);
            return root;
        }
        return x;
    }

    // UNION(x, y)
    public void union(T x, T y) {
        T rootX = findSet(x);
        T rootY = findSet(y);
        
        if (!rootX.equals(rootY)) {
            // LINK(rootX, rootY)
            int rankX = rank.get(rootX);
            int rankY = rank.get(rootY);
            
            if (rankX > rankY) {
                parent.put(rootY, rootX);
            } else {
                parent.put(rootX, rootY);
                if (rankX == rankY) {
                    rank.put(rootY, rankY + 1);
                }
            }
        }
    }
    
    public Set<T> getAllElements() {
        return parent.keySet();
    }
}