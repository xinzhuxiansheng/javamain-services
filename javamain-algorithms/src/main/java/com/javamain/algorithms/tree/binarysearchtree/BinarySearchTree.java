package com.javamain.algorithms.tree.binarysearchtree;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BinarySearchTree {

}


class BinaryTree {
    int nodeCount;
    private BinaryNode root;

    public void setRoot(BinaryNode root) {
        this.root = root;
        nodeCount++;
    }

    public int size() {
        return nodeCount;
    }

    public boolean isEmpty() {
        return nodeCount == 0;
    }

    public void insert(int no, String name) {
        insert(root, no, name);
    }

    private BinaryNode insert(BinaryNode node, int no, String name) {
        if (node == null) {
            nodeCount++;
            return new BinaryNode(no, name);
        }
        if (no == node.getNo()) {
            node.setName(name);
        } else if (no < node.getNo()) {
            node.setLeft(insert(node.getLeft(), no, name));
        } else {
            node.setRight(insert(node.right, no, name));
        }
        return node;
    }

    public boolean contain(int no) {
        return contain(root, no);
    }

    private boolean contain(BinaryNode node, int no) {
        if (node == null) {
            return false;
        }
        if (no == node.getNo()) {
            return true;
        } else if (no < node.getNo()) {
            return contain(node.getLeft(), no);
        } else {
            return contain(node.getRight(), no);
        }
    }

    public String search(BinaryNode node, int no) {
        if (node == null) {
            return null;
        }
        if (no == node.getNo()) {
            return node.getName();
        } else if (no < node.getNo()) {
            return search(node.getLeft(), no);
        } else {
            return search(node.getRight(), no);
        }
    }

    /**
     * 深度优先遍历
     */
    //前序遍历
    public void preOrderTraverse() {
        if (this.root != null) {
            this.root.preOrderTraverse();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //中序遍历
    public void inOrderTraverse() {
        if (this.root != null) {
            this.root.inOrderTraverse();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //后序遍历
    public void postOrderTraverse() {
        if (this.root != null) {
            this.root.postOrderTraverse();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //广度优先遍历
    public void levelOrder() {
        Queue<BinaryNode> queue = new LinkedBlockingQueue();
        queue.add(root);
        while (!queue.isEmpty()) {
            BinaryNode node = queue.poll();
            System.out.println(node);

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
    }

    public int minimum() {
        assert nodeCount != 0;
        BinaryNode minNode = minimum(root);
        return minNode.getNo();
    }

    private BinaryNode minimum(BinaryNode node) {
        if (node.getLeft() != null) {
            return node;
        }
        return minimum(node);
    }

    public int maximum() {
        assert nodeCount != 0;
        BinaryNode minNode = maximum(root);
        return minNode.getNo();
    }

    private BinaryNode maximum(BinaryNode node) {
        if (node.getRight() != null) {
            return node;
        }
        return maximum(node);
    }

    void removeMin() {
        if (root != null) {
            root = removeMin(root);
        }
    }

    BinaryNode removeMin(BinaryNode node) {
        if (node.getLeft() == null) {
            BinaryNode rightNode = node.getRight();
            nodeCount--;
            return rightNode;
        }
        node.setLeft(removeMin(node.getLeft()));
        return node;
    }

    void removeMax() {
        if (root != null) {
            root = removeMax(root);
        }
    }

    private BinaryNode removeMax(BinaryNode node) {
        if (node.getRight() == null) {
            BinaryNode leftNode = node.getLeft();
            nodeCount--;
            return leftNode;
        }
        node.setRight(removeMax(node.getRight()));
        return node;
    }

    void remove(int no) {
        remove(root, no);
    }

    private BinaryNode remove(BinaryNode node, int no) {
        if (node == null) {
            return null;
        }
        if (no < node.getNo()) {
            node.setLeft(remove(node.getLeft(), no));
            return node;
        }
        if (no > node.getNo()) {
            node.setRight(remove(node.getRight(), no));
            return node;
        }

        if (node.getLeft() == null) {
            BinaryNode rightNode = node.getRight();
            nodeCount--;
            return rightNode;
        }
        if (node.getRight() == null) {
            BinaryNode leftNode = node.getLeft();
            nodeCount--;
            return leftNode;
        }

        //这里就是相等
        BinaryNode successor = minimum(node.getRight());
        nodeCount++;
        successor.setRight(removeMin(node.right));
        successor.setLeft(node.getLeft());
        nodeCount--;
        return successor;

    }

}

class BinaryNode {
    private int no;
    private String name;
    BinaryNode left;
    BinaryNode right;

    public BinaryNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BinaryNode getLeft() {
        return left;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public BinaryNode getRight() {
        return right;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }

    /**
     * 前序遍历
     */
    public void preOrderTraverse() {
        System.out.println(this); //先输出父结点
        //递归向左子树前序遍历
        if (this.left != null) {
            this.left.preOrderTraverse();
        }
        //递归向右子树前序遍历
        if (this.right != null) {
            this.right.preOrderTraverse();
        }
    }

    /**
     * 中序遍历
     */
    public void inOrderTraverse() {
        //递归向左子树中序遍历
        if (this.left != null) {
            this.left.inOrderTraverse();
        }
        //输出父结点
        System.out.println(this);
        //递归向右子树中序遍历
        if (this.right != null) {
            this.right.inOrderTraverse();
        }
    }

    /**
     * 后序遍历
     */
    public void postOrderTraverse() {
        if (this.left != null) {
            this.left.postOrderTraverse();
        }
        if (this.right != null) {
            this.right.postOrderTraverse();
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "BinaryNode [no=" + no + ", name=" + name + "]";
    }
}
