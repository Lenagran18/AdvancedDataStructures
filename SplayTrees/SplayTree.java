package SplayTrees;

public class SplayTree {

    BTNode root;

    public void insertOrdinary(int key) {
        root = insertRec(root, key);
    }

    private BTNode insertRec(BTNode root, int key) {
        if (root == null) {
            root = new BTNode(key);
            return root;
        }

        if (key < root.data) {
            root.left = insertRec(root.left, key);
        } else if (key > root.data) {
            root.right = insertRec(root.right, key);
        }

        return root;
    }

    // if key is not in tree, find the predecessor - the largest element smaller
    // than key
    public BTNode findPredecessor(int key) {
        BTNode predecessor = root;
        BTNode current = root;

        while (current != null) {
            if (key < current.data) {
                current = current.left;
            } else if (key > current.data) {
                predecessor = current;
                current = current.right;

            } else {
                break;
            }
        }
        System.out.println("predecessor: " + predecessor.data);
        return predecessor;
    }

    // Deletes nodes with the key and returns the new root
    public BTNode splay(BTNode root, int key) {
        if (root == null || root.data == key) {
            return root;
        }

        if (!contains(key)) {
            BTNode predecessorNode = findPredecessor(key);
            if (predecessorNode == null) {
                // If there's no predecessor (key smaller than all elements), return the current
                // root
                return root;
            }
            key = predecessorNode.data;
        }
        // key is in left subtree
        if (key < root.data) {
            if (root.left == null) {
                return root;
            }
            // zig-zig left left
            if (key < root.left.data) {
                root.left.left = splay(root.left.left, key);
                root = rightRotate(root);
                // zig-zag left right
            } else if (key > root.left.data) {
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null) {
                    root.left = leftRotate(root.left);
                }
            }

            return (root.left == null) ? root : rightRotate(root);
            // key is in right subtree
        } else {
            if (root.right == null) {
                return root;
            }
            // zig-zig right right
            if (key > root.right.data) {
                root.right.right = splay(root.right.right, key);
                root = leftRotate(root);
            } else if (key < root.right.data) {
                root.right.left = splay(root.right.left, key);
                if (root.right.left != null) {
                    root.right = rightRotate(root.right);
                }
            }

            return (root.right == null) ? root : leftRotate(root);
        }
    }

    private BTNode rightRotate(BTNode node) {
        BTNode leftNode = node.left;
        node.left = leftNode.right;
        leftNode.right = node;
        return leftNode;
    }

    private BTNode leftRotate(BTNode node) {
        BTNode rightNode = node.right;
        node.right = rightNode.left;
        rightNode.left = node;
        return rightNode;
    }

    public void insert(int key) {
        if (root == null) {
            root = new BTNode(key);
            System.out.println("The key is inserted into the tree");
            System.out.println();
            return;

        }

        root = splay(root, key);

        if (root.data == key) {
            System.out.println("Duplicate key");
            System.out.println();
            return;
        } else {

            BTNode newNode = new BTNode(key);
            if (key < root.data) {
                newNode.right = root;
                newNode.left = root.left;
                root.left = null;
            } else {
                newNode.left = root;
                newNode.right = root.right;
                root.right = null;
            }
            root = newNode;
            System.out.println("The key is inserted into the tree");
            System.out.println();
        }
    }

    // if found - changes the structure of the tree to make the node with the key
    // the root
    public boolean find(int key) {
        if (root == null) {
            return false;
        }
        root = splay(root, key);

        if (root.data == key) {
            System.out.println("Search is successful");
            System.out.println();
            return true;
        } else {
            System.out.println("Search is unsuccessful");
            System.out.println();
            return false;
        }
    }

    public void delete(int key) {

        if (root == null) {
            return;
        }

        root = splay(root, key);

        if (root.data != key) {
            System.out.println("The key is not in the tree");
            System.out.println();
            return;
        }

        if (root.left == null) {
            root = root.right;
        } else {
            BTNode temp = root.right;
            BTNode maxLeft = findMaxNode(root.left);
            int max = maxLeft.data;
            root = splay(root.left, max);
            root.right = temp;
        }
        System.out.println("The key is deleted from the tree");
        System.out.println();
    }

    private BTNode findMaxNode(BTNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public boolean contains(int key) {
        BTNode currentNode = root;

        while (currentNode != null) {
            if (key == currentNode.data) {
                return true;
            } else if (key < currentNode.data) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }

        return false;
    }

    public void displayTree(BTNode node, int depth) {
        if (node == null) {
            return;
        }

        displayTree(node.right, depth + 1);

        // Debugging output for tree structure
        for (int i = 0; i < depth; i++) {
            System.out.print("    ");
        }
        System.out.println(node.data);

        displayTree(node.left, depth + 1);
    }
}