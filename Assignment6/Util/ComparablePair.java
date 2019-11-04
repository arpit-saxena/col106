package Util;

public class ComparablePair
    <T extends Comparable<? super T>, E extends Comparable<? super E>> 
    implements Comparable<ComparablePair<T, E>> {
        public T first;
        public E second;

        public ComparablePair(T first, E second) {
            this.first = first;
            this.second = second;
        }

        public int compareTo(ComparablePair<T, E> other) {
            int res;
            if ((res = first.compareTo(other.first)) != 0) {
                return res;
            }

            return second.compareTo(other.second);
        }
    }