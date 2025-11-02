package filmapp.heap;

import filmapp.util.AbstractNode;

public class HeapNode<T> extends AbstractNode<T> {
    private double key;

    public HeapNode(T data, double key) {
        super(data);
        this.key = key;
    }

    public double getKey() { return key; }
}
