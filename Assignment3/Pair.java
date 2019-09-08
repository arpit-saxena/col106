public class Pair<K, T> {
    K first;
    T second;

    public Pair(K first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return first.toString() + second.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Pair<K, T> pair = (Pair<K, T>) obj;
        return first.equals(pair.first) && second.equals(pair.second);
    }
}