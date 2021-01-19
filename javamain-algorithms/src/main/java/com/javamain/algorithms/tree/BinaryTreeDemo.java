package com.javamain.algorithms.tree;

public class BinaryTreeDemo {

    public static void main(String[] args) {
        //先需要创建一颗二叉树
        BinaryTree binaryTree = new BinaryTree();
        //创建需要的结点
        BinaryNode root = new BinaryNode(1, "宋江");
        BinaryNode node2 = new BinaryNode(2, "吴用");
        BinaryNode node3 = new BinaryNode(3, "卢俊义");
        BinaryNode node4 = new BinaryNode(4, "林冲");
        BinaryNode node5 = new BinaryNode(5, "关胜");

        //说明，我们先手动创建该二叉树，后面我们学习递归的方式创建二叉树
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);
        binaryTree.setRoot(root);

        //测试
		System.out.println("前序遍历"); // 1,2,3,5,4
		binaryTree.preOrderTraverse();

        //测试
//		System.out.println("中序遍历");
//		binaryTree.infixOrder(); // 2,1,5,3,4
//
//		System.out.println("后序遍历");
//		binaryTree.postOrder(); // 2,5,4,3,1

        //前序遍历
        //前序遍历的次数 ：4
//		System.out.println("前序遍历方式~~~");
//		HeroNode resNode = binaryTree.preOrderSearch(5);
//		if (resNode != null) {
//			System.out.printf("找到了，信息为 no=%d name=%s", resNode.getNo(), resNode.getName());
//		} else {
//			System.out.printf("没有找到 no = %d 的英雄", 5);
//		}

        //中序遍历查找
        //中序遍历3次
//		System.out.println("中序遍历方式~~~");
//		HeroNode resNode = binaryTree.infixOrderSearch(5);
//		if (resNode != null) {
//			System.out.printf("找到了，信息为 no=%d name=%s", resNode.getNo(), resNode.getName());
//		} else {
//			System.out.printf("没有找到 no = %d 的英雄", 5);
//		}

        //后序遍历查找
        //后序遍历查找的次数  2次
//		System.out.println("后序遍历方式~~~");
//		HeroNode resNode = binaryTree.postOrderSearch(5);
//		if (resNode != null) {
//			System.out.printf("找到了，信息为 no=%d name=%s", resNode.getNo(), resNode.getName());
//		} else {
//			System.out.printf("没有找到 no = %d 的英雄", 5);
//		}

        //测试一把删除结点

//        System.out.println("删除前,前序遍历");
//        binaryTree.preOrder(); //  1,2,3,5,4
//        binaryTree.delNode(5);
//        //binaryTree.delNode(3);
//        System.out.println("删除后，前序遍历");
//        binaryTree.preOrder(); // 1,2,3,4

    }
}


//定义BinaryTree 二叉树
class BinaryTree {
    private BinaryNode root;

    public void setRoot(BinaryNode root) {
        this.root = root;
    }

    //删除结点
    public void delNode(int no) {
        if (root != null) {
            //如果只有一个root结点, 这里立即判断root是不是就是要删除结点
            if (root.getNo() == no) {
                root = null;
            } else {
                //递归删除
                root.delNode(no);
            }
        } else {
            System.out.println("空树，不能删除~");
        }
    }

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

    //前序遍历
    public BinaryNode preOrderSearch(int no) {
        if (root != null) {
            return root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    //中序遍历
    public BinaryNode inOrderSearch(int no) {
        if (root != null) {
            return root.inOrderSearch(no);
        } else {
            return null;
        }
    }

    //后序遍历
    public BinaryNode postOrderSearch(int no) {
        if (root != null) {
            return this.root.postOrderSearch(no);
        } else {
            return null;
        }
    }
}

//定义BinaryNode节点
class BinaryNode {
    private int no;  //编号
    private String name;
    private BinaryNode left; //默认null
    private BinaryNode right; //默认null

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

    @Override
    public String toString() {
        return "BinaryNode [no=" + no + ", name=" + name + "]";
    }

    //递归删除结点
    //1.如果删除的节点是叶子节点，则删除该节点
    //2.如果删除的节点是非叶子节点，则删除该子树
    public void delNode(int no) {

        //思路
		/*
		 * 	1. 因为我们的二叉树是单向的，所以我们是判断当前结点的子结点是否需要删除结点，而不能去判断当前这个结点是不是需要删除结点.
			2. 如果当前结点的左子结点不为空，并且左子结点 就是要删除结点，就将this.left = null; 并且就返回(结束递归删除)
			3. 如果当前结点的右子结点不为空，并且右子结点 就是要删除结点，就将this.right= null ;并且就返回(结束递归删除)
			4. 如果第2和第3步没有删除结点，那么我们就需要向左子树进行递归删除
			5.  如果第4步也没有删除结点，则应当向右子树进行递归删除.

		 */
        //2. 如果当前结点的左子结点不为空，并且左子结点 就是要删除结点，就将this.left = null; 并且就返回(结束递归删除)
        if (this.left != null && this.left.no == no) {
            this.left = null;
            return;
        }
        //3.如果当前结点的右子结点不为空，并且右子结点 就是要删除结点，就将this.right= null ;并且就返回(结束递归删除)
        if (this.right != null && this.right.no == no) {
            this.right = null;
            return;
        }
        //4.我们就需要向左子树进行递归删除
        if (this.left != null) {
            this.left.delNode(no);
        }
        //5.则应当向右子树进行递归删除
        if (this.right != null) {
            this.right.delNode(no);
        }
    }

    //注意 System.out.println()  打印的位置

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

    /**
     * 前序遍历查找   注意: 递归查找,需要判断当前节点 是否相等即可
     *
     * @param no
     * @return
     */
    public BinaryNode preOrderSearch(int no) {
        //递归终止标记
        if (this.no == no) {
            return this;
        }
        BinaryNode resNode = null;
        if (this.left != null) {
            resNode = this.left.preOrderSearch(no);
        }
        //左子树遍历返回判断,如果已经找到,不要遍历右子树
        if (resNode != null) {
            return resNode;
        }
        if (this.right != null) {
            resNode = this.right.preOrderSearch(no);
        }
        return resNode;
    }

    /**
     * 中序遍历
     *
     * @param no
     * @return
     */
    public BinaryNode inOrderSearch(int no) {
        BinaryNode resNode = null;
        if (this.left != null) {
            resNode = this.left.inOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        if (this.no == no) {
            return this;
        }
        //否则继续进行右递归的中序查找
        if (this.right != null) {
            resNode = this.right.inOrderSearch(no);
        }
        return resNode;

    }

    /**
     * 后序遍历查找
     *
     * @param no
     * @return
     */
    public BinaryNode postOrderSearch(int no) {
        BinaryNode resNode = null;
        if (this.left != null) {
            resNode = this.left.postOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }

        if (this.right != null) {
            resNode = this.right.postOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        if (this.no == no) {
            return this;
        }
        return resNode;
    }

}


