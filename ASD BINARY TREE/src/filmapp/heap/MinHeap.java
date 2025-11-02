package filmapp.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MinHeap<T> {
    private final List<T> heap = new ArrayList<>();
    private final Comparator<T> cmp;

    public MinHeap() {
        this.cmp = null;
    }

    public MinHeap(Comparator<T> comparator) {
        this.cmp = comparator;
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i) { return 2 * i + 1; }
    private int right(int i) { return 2 * i + 2; }

    private int compare(T a, T b) {
        if (cmp != null) return cmp.compare(a, b);
        @SuppressWarnings("unchecked")
        Comparable<T> ca = (Comparable<T>) a;
        return ca.compareTo(b);
    }

    public void insert(T item) {
        heap.add(item);
        siftUp(heap.size() - 1);
    }

    private void siftUp(int i) {
        while (i > 0 && compare(heap.get(i), heap.get(parent(i))) < 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    public T extractMin() {
        if (heap.isEmpty()) return null;
        T root = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapify(0);
        }
        return root;
    }

    private void heapify(int i) {
        int smallest = i;
        int l = left(i);
        int r = right(i);
        if (l < heap.size() && compare(heap.get(l), heap.get(smallest)) < 0) smallest = l;
        if (r < heap.size() && compare(heap.get(r), heap.get(smallest)) < 0) smallest = r;
        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    private void swap(int i, int j) {
        T tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public void clear() {
        heap.clear();
    }

    public List<T> toList() {
        return new ArrayList<>(heap);
    }

    public void printHeap() {
        if (heap.isEmpty()) {
            System.out.println("Heap kosong.");
            return;
        }
        for (int i = 0; i < heap.size(); i++) {
            System.out.println((i + 1) + ". " + heap.get(i));
        }
    }
    
    public List<T> getAllElements() {
        return new ArrayList<>(heap); // heap = ArrayList<T> internal di MinHeap
    }

}
