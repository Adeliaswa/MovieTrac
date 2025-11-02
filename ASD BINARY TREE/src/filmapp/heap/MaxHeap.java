package filmapp.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MaxHeap<T> {
    private final List<T> heap;
    private final Comparator<T> cmp;

    public MaxHeap() {
        this(null);
    }

    public MaxHeap(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.cmp = comparator;
    }

    public void insert(T value) {
        heap.add(value);
        siftUp(heap.size() - 1);
    }

    public T extractMax() {
        if (heap.isEmpty()) return null;
        T max = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapify(0);
        }
        return max;
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i) { return 2 * i + 1; }
    private int right(int i) { return 2 * i + 2; }

    @SuppressWarnings("unchecked")
    private int compare(T a, T b) {
        if (cmp != null) return cmp.compare(a, b);
        Comparable<T> ca = (Comparable<T>) a;
        return ca.compareTo(b);
    }

    private void siftUp(int index) {
        while (index > 0) {
            int p = parent(index);
            if (compare(heap.get(index), heap.get(p)) <= 0) break;
            swap(index, p);
            index = p;
        }
    }

    private void heapify(int i) {
        int largest = i;
        int l = left(i);
        int r = right(i);
        if (l < heap.size() && compare(heap.get(l), heap.get(largest)) > 0) largest = l;
        if (r < heap.size() && compare(heap.get(r), heap.get(largest)) > 0) largest = r;
        if (largest != i) {
            swap(i, largest);
            heapify(largest);
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

    public List<T> topN(int n) {
        if (n <= 0) return new ArrayList<>();
        MaxHeap<T> tmp = new MaxHeap<>(cmp);
        tmp.heap.addAll(this.heap);
        tmp.heapifyAll();
        List<T> out = new ArrayList<>();
        while (n-- > 0) {
            T x = tmp.extractMax();
            if (x == null) break;
            out.add(x);
        }
        return out;
    }

    private void heapifyAll() {
        for (int i = parent(heap.size() - 1); i >= 0; i--) heapify(i);
    }
}
