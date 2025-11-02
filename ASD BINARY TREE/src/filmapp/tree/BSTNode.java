package filmapp.tree;

import filmapp.util.AbstractNode;

public class BSTNode<T> extends AbstractNode<T> {
    BSTNode<T> left, right;

    public BSTNode(T data) {
        super(data);
    }
}
