package Byzantine;

interface CircularList<T> {
    T getNext();
    T getPrev();
    T getItemToRight(int num);
    T getItemToLeft(int num);
}
