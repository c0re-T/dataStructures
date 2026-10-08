package com.ittxf.tree;

public class BinaryTreeDemo {
    public static void main(String[] args) {
        // 需要创建一个二叉树
        BinaryTree binaryTree = new BinaryTree();
        // 创建需要的节点
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");

        // 说明，先手动创建该二叉树的节点，再将二叉树的节点添加到二叉树中
        // 后面将以递归的方式创建二叉树
        binaryTree.setRoot(root);
        root.setLeftChild(node2);
        root.setRightChild(node3);
        node2.setLeftChild(node4);
        node2.setRightChild(node5);

        // 测试
        System.out.println("前序遍历：");
        binaryTree.preOrder();
        System.out.println("中序遍历：");
        binaryTree.inOrder();
        System.out.println("后序遍历：");
        binaryTree.postOrder();

        // 测试遍历查找
        System.out.println("前序遍历查找："); // 4次
        HeroNode resNode1 = binaryTree.preOrderSearch(5);
        if (resNode1 != null) {
            System.out.println("找到节点：" + resNode1);
        } else {
            System.out.println("没有找到节点");
        }

        System.out.println("中序遍历查找："); // 3次
        HeroNode resNode2 = binaryTree.inOrderSearch(5);
        if (resNode2 != null) {
            System.out.println("找到节点：" + resNode2);
        } else {
            System.out.println("没有找到节点");
        }

        System.out.println("后序遍历查找："); // 2次
        HeroNode resNode3 = binaryTree.postOrderSearch(5);
        if (resNode3 != null) {
            System.out.println("找到节点：" + resNode3);
        } else {
            System.out.println("没有找到节点");
        }

        /*System.out.println("删除前：");
        binaryTree.preOrder();
        // binaryTree.deleteNode(5);
        binaryTree.deleteNode(3);
        System.out.println("删除后：");
        binaryTree.preOrder();*/

        System.out.println("进阶删除前：");
        binaryTree.preOrder();
        binaryTree.deleteNodeAdvanced(2);
        System.out.println("进阶删除后：");
        binaryTree.preOrder();

    }
}

// 创建二叉树
class BinaryTree {
    private HeroNode root;

    public void setRoot(HeroNode root) {
        this.root = root;
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
    public HeroNode preOrderSearch(int no) {
        if (this.root != null) {
            return this.root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    // 中序遍历查找
    public HeroNode inOrderSearch(int no) {
        if (this.root != null) {
            return this.root.inOrderSearch(no);
        } else {
            return null;
        }
    }

    // 后序遍历查找
    public HeroNode postOrderSearch(int no) {
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

// 先创建HeroNode节点
class HeroNode {
    private int no;
    private String name;
    private HeroNode leftChild; // 左子节点，默认为空
    private HeroNode rightChild; // 右子节点，默认为空

    public HeroNode(int no, String name) {
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

    public HeroNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(HeroNode leftChild) {
        this.leftChild = leftChild;
    }

    public HeroNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(HeroNode rightChild) {
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
    public HeroNode preOrderSearch(int no) {
        System.out.println("前序遍历查找");
        // 比较当前节点
        if (this.no == no) {
            return this;
        }
        // 1 则判断当前结点的左子节点是否为空，如果不为空，则递归前序查找
        // 2 如果左递归前序查找，找到结点，则返回
        HeroNode resNode = null;
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
    public HeroNode inOrderSearch(int no) {
        HeroNode resNode = null;

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
    public HeroNode postOrderSearch(int no) {
        HeroNode resNode = null;
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
        HeroNode target = null; // 目标节点
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
