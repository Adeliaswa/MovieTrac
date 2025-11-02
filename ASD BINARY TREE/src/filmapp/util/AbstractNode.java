package filmapp.util;

public class AbstractNode<T> {
    private T data;

    public AbstractNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
