package org.vtsukur.algorithms.priority_queue;

import java.lang.reflect.Array;

import static org.vtsukur.algorithms.util.ArrayUtils.swap;
import static org.vtsukur.algorithms.util.ComparableUtils.less;

/**
 * @author volodymyr.tsukur
 */
public final class HeapBasedMaxPriorityQueue<K extends Comparable<K>> extends BaseMaxPriorityQueue<K> {

    private static final int INITIAL_CAPACITY = 1;

    private K[] store;

    private int cursor;

    public HeapBasedMaxPriorityQueue() {
        createInitialStore();
    }

    private void createInitialStore() {
        store = createStore(INITIAL_CAPACITY);
        cursor = 0;
    }

    private K[] createStore(final int capacity) {
        //noinspection unchecked
        return (K[]) Array.newInstance(Comparable.class, capacity);
    }

    @Override
    public void add(final K key) {
        ensureEnoughCapacity(cursor + 1);
        store[cursor] = key;
        heapify(cursor);
        cursor++;
    }

    @Override
    public K pollMax() {
        final K max = peekMax();

        store[0] = null;
        int index = 0;
        int leftChildIndex = leftChildIndex(index);
        while (leftChildIndex < cursor) {
            final int rightChildIndex = rightChildIndex(index);
            final int nextIndex;
            if (rightChildIndex < cursor) {
                nextIndex = less(store[leftChildIndex], store[rightChildIndex])
                        ? rightChildIndex : leftChildIndex;
            } else {
                nextIndex = leftChildIndex;
            }
            swap(store, index, nextIndex);
            index = nextIndex;
            leftChildIndex = leftChildIndex(index);
        }
        if (index < cursor - 1) {
            store[index] = store[cursor - 1];
            heapify(index);
        }

        cursor--;
        ensureCapacityNotOverused(cursor);

        return max;
    }

    private void heapify(final int index) {
        int i = index;
        while (hasParent(i) && less(store[parent(i)], store[i])) {
            swap(store, parent(i), i);
            i = parent(i);
        }
    }

    private int leftChildIndex(final int index) {
        return (index << 1) + 1;
    }

    private int rightChildIndex(final int index) {
        return (index + 1) << 1;
    }

    private void ensureEnoughCapacity(final int newCapacity) {
        if (newCapacity == store.length) {
            reallocateStore(newCapacity, store.length * 2);
        }
    }

    private void ensureCapacityNotOverused(final int newCapacity) {
        if (newCapacity == store.length / 4) {
            reallocateStore(newCapacity, store.length / 2);
        }
    }

    private void reallocateStore(final int newCapacity, final int capacity) {
        final K[] newStore = createStore(capacity);
        System.arraycopy(store, 0, newStore, 0, newCapacity);
        store = newStore;
    }

    private boolean hasParent(final int index) {
        return index != 0;
    }

    private int parent(final int index) {
        return (index - 1) / 2;
    }

    @Override
    public K peekMax() {
        assertNotEmpty();
        return store[0];
    }

    @Override
    public boolean isEmpty() {
        return cursor == 0;
    }

    @Override
    public int size() {
        return cursor;
    }

    @Override
    public void clear() {
        createInitialStore();
    }

}