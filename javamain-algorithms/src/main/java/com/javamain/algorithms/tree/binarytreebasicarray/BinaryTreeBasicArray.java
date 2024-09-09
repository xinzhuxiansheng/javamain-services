package com.javamain.algorithms.tree.binarytreebasicarray;

public class BinaryTreeBasicArray {

    public static void main(String[] args) {
        //先需要创建一颗二叉树
        BinaryTree binaryTree = new BinaryTree(20);
        //创建需要的结点
        BinaryNode root = new BinaryNode(0, "A");
        BinaryNode node1 = new BinaryNode(1, "B");
        BinaryNode node2 = new BinaryNode(2, "C");
        BinaryNode node3 = new BinaryNode(3, "D");
        BinaryNode node4 = new BinaryNode(4, "E");
        BinaryNode node5 = new BinaryNode(5, "F");
        BinaryNode node6 = new BinaryNode(6, "G");
        BinaryNode node7 = new BinaryNode(7, "H");
        BinaryNode node8 = new BinaryNode(8, "I");
        BinaryNode node10 = new BinaryNode(10, "J");
        BinaryNode node14 = new BinaryNode(14, "K");

        binaryTree.setRoot(root);
        binaryTree.setLeftNode(node1, 0);
        binaryTree.setRightNode(node2, 0);

        binaryTree.setLeftNode(node3, 1);
        binaryTree.setRightNode(node4, 1);

        binaryTree.setLeftNode(node5, 2);
        binaryTree.setRightNode(node6, 2);

        binaryTree.setLeftNode(node7, 3);
        binaryTree.setRightNode(node8, 3);

        binaryTree.setRightNode(node10, 4);
        binaryTree.setRightNode(node14, 6);


        //测试
        System.out.println("前序遍历"); // 1,2,3,5,4
        binaryTree.preOrderTraverse(0);

        //测试
//		System.out.println("中序遍历");
//		binaryTree.infixOrder(); // 2,1,5,3,4
//
//		System.out.println("后序遍历");
//		binaryTree.postOrder(); // 2,5,4,3,1
    }

}


class BinaryTree {
    private int capacity;
    private int nodeCount;
    private BinaryNode[] elements;

    public BinaryTree(int capacity) {
        this.capacity = capacity;
        this.nodeCount = 0;
        this.elements = new BinaryNode[capacity];
    }

    public void setRoot(BinaryNode root) {
        if (isEmpty()) {
            this.elements[0] = root;
            nodeCount++;
        } else {
            throw new RuntimeException("已存在 root节点");
        }
    }

    public boolean isEmpty() {
        return nodeCount == 0 ? true : false;
    }

    /**
     * @param no
     * @param name
     * @param index 为index的下标的节点添加左孩子
     */
    public void setLeftNode(int no, String name, int index) {
        BinaryNode leftNode = new BinaryNode(no, name);
        setLeftNode(leftNode, index);
    }

    public void setLeftNode(BinaryNode leftNode, int index) {
        int leftIndex = 2 * index + 1;
        if (index < 0 || index > capacity - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (this.elements[index] == null) {
            throw new NullPointerException();
        }
        this.elements[index].setLeft(leftIndex);
        this.elements[leftIndex] = leftNode;
        nodeCount++;
    }

    /**
     * @param no
     * @param name
     * @param index 为index的下标的节点添加右孩子
     */
    public void setRightNode(int no, String name, int index) {
        BinaryNode rightNode = new BinaryNode(no, name);
        setRightNode(rightNode, index);
    }

    public void setRightNode(BinaryNode rightNode, int index) {
        int rightIndex = 2 * index + 2;
        if (index < 0 || index > capacity - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (this.elements[index] == null) {
            throw new NullPointerException();
        }
        this.elements[index].setRight(rightIndex);
        this.elements[rightIndex] = rightNode;
        nodeCount++;
    }


    //删除结点
//    public void delNode(int no) {
//        if (root != null) {
//            //如果只有一个root结点, 这里立即判断root是不是就是要删除结点
//            if (root.getNo() == no) {
//                root = null;
//            } else {
//                //递归删除
//                root.delNode(no);
//            }
//        } else {
//            System.out.println("空树，不能删除~");
//        }
//    }

    /**
     * 前序遍历
     */
    public void preOrderTraverse(int index) {
        if (index == -1) {
            return;
        }
        System.out.println(this.elements[index]); //先输出父结点
        //递归向左子树前序遍历
        preOrderTraverse(this.elements[index].getLeft());
        //递归向右子树前序遍历
        preOrderTraverse(this.elements[index].getRight());
    }

    /**
     * 中序遍历
     */
    public void inOrderTraverse(int index) {
        if (index == -1) {
            return;
        }
        //递归向左子树中序遍历
        inOrderTraverse(this.elements[index].getLeft());
        //输出父结点
        System.out.println(this.elements[index]);
        //递归向右子树前序遍历
        preOrderTraverse(this.elements[index].getRight());
    }

    /**
     * 后序遍历
     */
    public void postOrderTraverse(int index) {
        if (index == -1) {
            return;
        }
        //递归向左子树中序遍历
        inOrderTraverse(this.elements[index].getLeft());
        //递归向右子树前序遍历
        preOrderTraverse(this.elements[index].getRight());
        //输出父结点
        System.out.println(this.elements[index]);
    }
}

//定义BinaryNode节点
class BinaryNode {
    private int no;
    private String name;
    private int left;
    private int right;

    /**
     * 左右节点 下标默认为-1
     *
     * @param no
     * @param name
     */
    public BinaryNode(int no, String name) {
        this.no = no;
        this.name = name;
        this.left = -1;
        this.right = -1;
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

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinaryNode [no=" + no + ", name=" + name + "]";
    }

}


