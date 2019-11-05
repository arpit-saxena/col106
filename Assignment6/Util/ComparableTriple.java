package Util;

public class ComparableTriple
    <U extends Comparable<? super U>, 
     V extends Comparable<? super V>,
     W extends Comparable<? super W>> 
    implements Comparable<ComparableTriple<U, V, W>> {
        public U first;
        public V second;
        public W third;

        public ComparableTriple(U first, V second, W third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        protected ComparableTriple() {}

        public int compareTo(ComparableTriple<U, V, W> other) {
            int res;
            if ((res = first.compareTo(other.first)) != 0) {
                return res;
            }

            if ((res = second.compareTo(other.second)) != 0) {
                return res;
            }

            return third.compareTo(other.third);
        }
    }