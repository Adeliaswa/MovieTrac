package filmapp.tree;

import java.util.ArrayList;
import java.util.List;

public class GenreBST {
    private Node root;

    private static class Node {
        String data;
        Node left, right;
        Node(String data) {
            this.data = data;
        }
    }

    public void insert(String genre) {
        root = insertRec(root, genre);
    }

    private Node insertRec(Node node, String genre) {
        if (node == null) return new Node(genre);
        if (genre.compareToIgnoreCase(node.data) < 0)
            node.left = insertRec(node.left, genre);
        else if (genre.compareToIgnoreCase(node.data) > 0)
            node.right = insertRec(node.right, genre);
        return node;
    }

    public List<String> getInorderList() {
        List<String> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    private void inorderRec(Node node, List<String> result) {
        if (node == null) return;
        inorderRec(node.left, result);
        result.add(node.data);
        inorderRec(node.right, result);
    }
}
