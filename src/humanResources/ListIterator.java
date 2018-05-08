package humanResources;

import java.util.*;

public class ListIterator<T> implements Iterable<T>{
    private T[] list;
    private int size;

    public ListIterator(T[] list) {
        this.list = list;
        this.size = list.length;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                return list[index++];
            }
        };
        return iterator;
    }
}