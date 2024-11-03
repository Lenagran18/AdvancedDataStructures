package ExpressionManipulations;

public class BTNode {
    String data;
    BTNode left;
    BTNode right;

    public BTNode(String data) {
        this.data = data;
        left = right = null;
    }
}