package filmapp.tree;

import filmapp.model.Film;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AVLTree<T extends Film> {
    private AVLNode<T> root;

    private int height(AVLNode<T> n) {
        return (n == null) ? 0 : n.height;
    }

    private int getBalance(AVLNode<T> n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    private AVLNode<T> rotateRight(AVLNode<T> y) {
        AVLNode<T> x = y.left;
        AVLNode<T> T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private AVLNode<T> rotateLeft(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        AVLNode<T> T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private AVLNode<T> insertRec(AVLNode<T> node, T data) {
        if (node == null) return new AVLNode<>(data);

        if (data.getId().compareTo(node.getData().getId()) < 0)
            node.left = insertRec(node.left, data);
        else if (data.getId().compareTo(node.getData().getId()) > 0)
            node.right = insertRec(node.right, data);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance > 1 && data.getId().compareTo(node.left.getData().getId()) < 0)
            return rotateRight(node);

        if (balance < -1 && data.getId().compareTo(node.right.getData().getId()) > 0)
            return rotateLeft(node);

        if (balance > 1 && data.getId().compareTo(node.left.getData().getId()) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && data.getId().compareTo(node.right.getData().getId()) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public Optional<T> search(String id) {
        AVLNode<T> current = root;
        while (current != null) {
            int cmp = id.compareTo(current.getData().getId());
            if (cmp == 0)
                return Optional.of(current.getData());
            else if (cmp < 0)
                current = current.left;
            else
                current = current.right;
        }
        return Optional.empty();
    }

    public void inorderTraversal() {
        inorderRec(root);
    }

    private void inorderRec(AVLNode<T> node) {
        if (node == null) return;
        inorderRec(node.left);
        System.out.println(node.getData());
        inorderRec(node.right);
    }

    public List<T> getInorderList() {
        List<T> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(AVLNode<T> node, List<T> list) {
        if (node == null) return;
        inorderTraversal(node.left, list);
        list.add(node.getData());
        inorderTraversal(node.right, list);
    }
}
