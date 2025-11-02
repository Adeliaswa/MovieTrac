package filmapp.tree;

import filmapp.util.AbstractNode;

public class AVLNode<T> extends AbstractNode<T> {
    AVLNode<T> left;
    AVLNode<T> right;
    int height;

    public AVLNode(T data) {
        super(data);
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}
