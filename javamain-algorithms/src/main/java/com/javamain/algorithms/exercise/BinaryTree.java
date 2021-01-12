package com.javamain.algorithms.exercise;

public class BinaryTree {
    private BinaryTree left;  //左节点
    private BinaryTree right; //右节点
    private String data;    //树的内容

    public BinaryTree(){
    }

    public BinaryTree(String data,BinaryTree left,BinaryTree right){
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public BinaryTree(String data){
        this(data,null,null);
    }

    /**
     * 插入左节点
     * @param node
     * @param value
     */
    public static void insertLeft(BinaryTree node,String value){
        if(null != node){
            if(null == node.left){
                node.setLeft(new BinaryTree(value));
            }else{
                BinaryTree newNode = new BinaryTree(value);
                newNode.left = node.left;
                node.left = newNode;

            }
        }
    }

    public static void insertRight(BinaryTree node,String value){
        if(null != node){
            if(null == node.right){
                node.setRight(new BinaryTree(value));
            }else{
                BinaryTree newNode = new BinaryTree(value);
                newNode.right = node.right;
                node.right = newNode;
            }
        }
    }

    public static void testInsert(){
        BinaryTree node_a = new BinaryTree("a");
        node_a.insertLeft(node_a,"b");
        node_a.insertRight(node_a,"c");

        BinaryTree node_b = node_a.left;
        node_b.insertRight(node_b,"d");

        BinaryTree node_c = node_a.right;
        node_c.insertLeft(node_c,"e");
        node_c.insertRight(node_c,"f");

        BinaryTree node_d = node_b.right;
        BinaryTree node_e = node_c.left;
        BinaryTree node_f = node_c.right;

        System.out.println("【node_a data】:" + node_a.getData());
        System.out.println("【node_b data】:" + node_b.getData());
        System.out.println("【node_c data】:" + node_c.getData());
        System.out.println("【node_d data】:" + node_d.getData());
        System.out.println("【node_e data】:" + node_e.getData());
        System.out.println("【node_f data】:" + node_f.getData());

    }




    public static void main(String[] args) {

        //testInsert();

    }




    public BinaryTree getLeft() {
        return left;
    }
    public void setLeft(BinaryTree left) {
        this.left = left;
    }
    public BinaryTree getRight() {
        return right;
    }
    public void setRight(BinaryTree right) {
        this.right = right;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
}
