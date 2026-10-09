package com.ittxf.tree;

public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        // 测试中序线索化二叉树
        HeroNode2 root = new HeroNode2(1, "宋江");
        HeroNode2 heroNode2 = new HeroNode2(2, "卢俊义");
        HeroNode2 heroNode3 = new HeroNode2(3, "吴用");
        HeroNode2 heroNode4 = new HeroNode2(4, "公孙胜");
        HeroNode2 heroNode5 = new HeroNode2(5, "关胜");
        HeroNode2 heroNode6 = new HeroNode2(6, "林冲");
        HeroNode2 heroNode7 = new HeroNode2(7, "武松");

        // 二叉树，后面我们要递归创建，现在简单处理使用手动创建
        // 原中序遍历：4 5 2 1 6 7 3
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
        threadedBinaryTree.setRoot(root);
        root.setLeftChild(heroNode2);
        root.setRightChild(heroNode3);
        heroNode2.setLeftChild(heroNode4);
        heroNode2.setRightChild(heroNode5);
        heroNode3.setLeftChild(heroNode6);
        heroNode3.setRightChild(heroNode7);

        // 测试中序线索化
        threadedBinaryTree.threadInOrder();

        // 测试：以5号节点测试
        HeroNode2 leftNode = heroNode5.getLeftChild(); // 获取5号节点的左子节点
        System.out.println("5号节点的前驱节点是：" + leftNode);
        HeroNode2 rightNode = heroNode5.getRightChild(); // 获取5号节点的右子节点
        System.out.println("5号节点的后继节点是：" + rightNode);

        // 当线索化二叉树后，能在使用原来的遍历方法
        // threadedBinaryTree.inOrder(); // 会报错，死循环，栈内存溢出
        System.out.println("使用线索化的方式遍历线索二叉树：");
        threadedBinaryTree.threadList(); // 4 5 2 1 6 7 3


    }
}

class ThreadedBinaryTree {
    private HeroNode2 root;
    // 为了实现线索化，需要创建要给指向当前结点的前驱结点的指针
    // 说明：preNode在递归进行线索化时，preNode总是保留前一个节点
    private HeroNode2 preNode;

    public void setRoot(HeroNode2 root) {
        this.root = root;
    }

    // 重载threadInOrder，方便调用
    public void threadInOrder() {
        this.threadInOrder(root);
    }

    // 遍历中序线索二叉树
    public void threadList() {
        // 定义一个变量，存储当前节点，遍历从root开始
        HeroNode2 node = root;
        while (node != null) {
            // 循环的找到leftType == 1的结点，即为当前遍历的节点
            // 后面随着遍历而变化，因为leftType == 1时，说明该节点是线索化后的节点
            // 处理后的有效节点
            while (node.getLeftType() == 0) {
                node = node.getLeftChild();
            }
            // 打印当前这个节点
            System.out.println(node);
            // 如果当前结点的右指针指向的是后继结点，就一直输出
            while (node.getRightType() == 1) {
                // 获取到当前结点的后继结点
                node = node.getRightChild();
                System.out.println(node);
            }
            // 替换遍历的节点
            node = node.getRightChild();
        }
    }

    /**
     * 中序线索化
     * @param node 表示当前需要线索化的节点
     */
    public void threadInOrder(HeroNode2 node) {
        // 如果node为空，无法线索化
        if (node == null) {
            return;
        }
        // (一)先线索化左子树
        if (node.getLeftType() == 0) threadInOrder(node.getLeftChild());
        // (二)线索化当前节点
        if (node.getLeftChild() == null) {
            // 当前节点的左子节点为空，设置当前节点的左子节点指针指向中序遍历的前驱节点
            node.setLeftChild(preNode);
            // 设置当前节点的左子节点指针类型为1
            node.setLeftType(1);
        }
        // node的前驱==pre的后继结点，所以给node设前驱就是给pre设后继
        if (preNode != null && preNode.getRightChild() == null) {
            // 当前节点的右子节点为空，设置当前节点的右子节点指针指向中序遍历的后继节点
            preNode.setRightChild(node);
            // 设置当前节点的右子节点指针类型为1
            preNode.setRightType(1);
        }
        // !!!每处理一个节点，让当前节点是下一个节点的前驱节点
        preNode = node;
        // (三)线索化右子树
        if (node.getRightType() == 0) threadInOrder(node.getRightChild());
    }

    // 前序遍历
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 中序遍历
    public void inOrder() {
        if (this.root != null) {
            this.root.inOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 后序遍历
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 前序遍历查找
    public HeroNode2 preOrderSearch(int no) {
        if (this.root != null) {
            return this.root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    // 中序遍历查找
    public HeroNode2 inOrderSearch(int no) {
        if (this.root != null) {
            return this.root.inOrderSearch(no);
        } else {
            return null;
        }
    }

    // 后序遍历查找
    public HeroNode2 postOrderSearch(int no) {
        if (this.root != null) {
            return this.root.postOrderSearch(no);
        } else {
            return null;
        }
    }

    // 删除节点
    public void deleteNode(int no) {
        if (root != null) {
            if (root.getNo() == no) {
                root = null;
            } else {
                root.deleteNode(no);
            }
        } else {
            System.out.println("二叉树为空，无法删除");
        }
    }

    // 删除节点进阶
    public void deleteNodeAdvanced(int no) {
        if (root != null) {
            if (root.getNo() == no) {
                root = null;
            } else {
                root.deleteNodeAdvanced(no);
            }
        } else {
            System.out.println("二叉树为空，无法删除");
        }
    }
}

// 创建HeroNode
class HeroNode2 {
    private int no;
    private String name;
    private HeroNode2 leftChild; // 左子节点，默认为空
    private HeroNode2 rightChild; // 右子节点，默认为空

    // leftType 0 表示左子节点指针指向的是左子节点 1 表示左子节点指针指向的是前驱节点
    // rightType 0 表示右子节点指针指向的是右子节点 1 表示右子节点指针指向的是后继节点
    private int leftType;
    private int rightType;

    public HeroNode2(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
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

    public HeroNode2 getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(HeroNode2 leftChild) {
        this.leftChild = leftChild;
    }

    public HeroNode2 getRightChild() {
        return rightChild;
    }

    public void setRightChild(HeroNode2 rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    // 编写前序遍历的方法
    public void preOrder() {
        System.out.println(this); // 先输出父节点
        if (this.leftChild != null) { // 如果左子节点不为空，则递归遍历左子节点
            this.leftChild.preOrder();
        }
        if (this.rightChild != null) { // 如果右子节点不为空，则递归遍历右子节点
            this.rightChild.preOrder();
        }
    }
    // 编写中序遍历的方法
    public void inOrder() {
        if (this.leftChild != null) {
            this.leftChild.inOrder();
        }
        System.out.println(this);
        if (this.rightChild != null) {
            this.rightChild.inOrder();
        }
    }
    // 编写后序遍历的方法
    public void postOrder() {
        if (this.leftChild != null) {
            this.leftChild.postOrder();
        }
        if (this.rightChild != null) {
            this.rightChild.postOrder();
        }
        System.out.println(this);
    }

    /**
     * 前序遍历查找
     * @param no 要查找的节点编号
     * @return 如果找到则返回该节点，否则返回null
     */
    public HeroNode2 preOrderSearch(int no) {
        System.out.println("前序遍历查找");
        // 比较当前节点
        if (this.no == no) {
            return this;
        }
        // 1 则判断当前结点的左子节点是否为空，如果不为空，则递归前序查找
        // 2 如果左递归前序查找，找到结点，则返回
        HeroNode2 resNode = null;
        if (this.leftChild != null) {
            resNode = this.leftChild.preOrderSearch(no);
            if (resNode != null) { // 如果左递归前序查找，找到结点，则返回，否则继续判断
                return resNode;
            }
        }

        if (this.rightChild != null) {
            resNode = this.rightChild.preOrderSearch(no);
            if (resNode != null) { // 如果右递归前序查找，找到结点，则返回，否则继续判断
                return resNode;
            }
        }
        return resNode;
    }

    /**
     * 中序遍历查找
     * @param no 要查找的节点编号
     * @return 如果找到则返回该节点，否则返回null
     */
    public HeroNode2 inOrderSearch(int no) {
        HeroNode2 resNode = null;

        if (this.leftChild != null) {
            resNode = this.leftChild.inOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        System.out.println("中序遍历查找");
        // 如果找到，则返回，如果没有找到，就和当前结点比较，如果是则返回当前结点
        if (this.no == no) {
            return this;
        }
        if (this.rightChild != null) {
            resNode = this.rightChild.inOrderSearch(no);
        }
        return resNode;
    }

    /**
     * 后序遍历查找
     * @param no 要查找的节点编号
     * @return 如果找到则返回该节点，否则返回null
     */
    public HeroNode2 postOrderSearch(int no) {
        HeroNode2 resNode = null;
        if (this.leftChild != null) {
            resNode = this.leftChild.postOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        if (this.rightChild != null) {
            resNode = this.rightChild.postOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        System.out.println("后序遍历查找");
        if (this.no == no) {
            return this;
        }
        return resNode;
    }

    /**
     * 删除节点
     * @param no 要删除的节点编号
     *   1 因为我们的二叉树是单向的，所以我们是判断当前结点的子结点是否需要删除结点，而不能去判断当前这个结点是不是需要删除结点。
     *   2 如果当前结点的左子结点不为空，并且左子结点就是要删除结点，就将this.left = null;并且就返回(结束递归删除)
     *   3 如果当前结点的右子结点不为空，并且右子结点 就是要删除结点，就将this.right= null; ;并且就返回(结束递归删除)
     *   4 如果第2和第3步没有删除结点，那么我们就需要向左子树进行递归删除
     *   5 如果第4步也没有删除结点，则应当向右子树进行递归删除。
     */
    public void deleteNode(int no) {
        if (this.leftChild != null && this.leftChild.no == no) {
            this.leftChild = null;
            return;
        }
        if (this.rightChild != null && this.rightChild.no == no) {
            this.rightChild = null;
            return;
        }
        if (this.leftChild != null) {
            this.leftChild.deleteNode(no);
        }
        if (this.rightChild != null) {
            this.rightChild.deleteNode(no);
        }
    }

    /**
     * 删除节点进阶
     * 如果要删除的节点是非叶子节点，现在我们不希望将该非叶子节点为根节点的子树删除，需要指定规则,假如规定如下：
     * 如果该非叶子节点A只有一个子节点B，则子节点B替代节点A
     * 如果该非叶子节点A有左子节点B和右子节点C，则让左子节点B替代节点A。
     * @param no 要删除的节点编号
     */
    public void deleteNodeAdvanced(int no) {
        HeroNode2 target = null; // 目标节点
        if (this.leftChild != null && this.leftChild.no == no) {
            target = this.leftChild;
            if (target.leftChild != null) {
                // 左子节点存在，让左子节点替代自己
                // 并将原本右子节点挂在左子节点的右子树上（防止丢失）
                if (target.rightChild != null) {
                    target.leftChild.rightChild = target.rightChild;
                }
                this.leftChild = target.leftChild;
            } else if (target.rightChild != null) {
                // 只有右子节点，直接让右子节点替代
                this.leftChild = target.rightChild;
            }
            return;
        }
        if (this.rightChild != null && this.rightChild.no == no) {
            target = this.rightChild;
            if (target.leftChild != null) {
                // 和上面同理
                if (target.rightChild != null) {
                    target.leftChild.rightChild = target.rightChild;
                }
                this.rightChild = target.leftChild;
            } else {
                this.rightChild = target.rightChild;
            }
            return;
        }
        if (this.leftChild != null) {
            this.leftChild.deleteNodeAdvanced(no);
        }
        if (this.rightChild != null) {
            this.rightChild.deleteNodeAdvanced(no);
        }
    }
}

