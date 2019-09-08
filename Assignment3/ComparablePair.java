public class ComparablePair<K extends Comparable<K>, T> 
extends Pair<K, T>
implements Comparable<ComparablePair<K, T>> {
    public ComparablePair(K first, T second) {
        super(first, second);
    }

    @Override
    public int compareTo(ComparablePair<K, T> p) {
        return this.first.compareTo(p.first);
    }
}