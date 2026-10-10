package com.ittxf.binarysorttree;

// 二叉排序树（BST）
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }
        binarySortTree.add(new Node(2));
        // 中序遍历刚好是有序的
        binarySortTree.infixOrder();

        // 测试删除叶子节点
        System.out.println("删除叶子节点5后");
        binarySortTree.delete(5);
        binarySortTree.infixOrder();

        // 测试删除只有一颗子树的节点
        System.out.println("删除只有一颗子树的节点1后");
        binarySortTree.delete(1);
        binarySortTree.infixOrder();

        // 测试删除有两颗子树的节点
        System.out.println("删除有两颗子树的节点7后");
        binarySortTree.delete(10);
        binarySortTree.infixOrder();
    }
}

class BinarySortTree {
    private Node root;

    /**
     * 添加节点
     * @param node
     */
    public void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空，无法遍历");
        }
    }

    /**
     * 查找节点
     * @param value 要查找的节点的值
     * @return 如果找到返回该结点，否则返回null
     */
    public Node search(int value) {
        if (root == null) return null;
        else return root.search(value);
    }

    /**
     * 查找要删除的节点的父节点
     * @param value 要删除的节点的值
     * @return 返回要删除的节点的父节点，如果没有找到返回null
     */
    public Node searchParent(int value) {
        if (root == null) return null;
        else return root.searchParent(value);
    }

    /**
     * 返回以node为根节点的二叉排序树的最小节点的值
     * 删除node为根节点的二叉排序树的最小节点
     * @param node 传入的节点(当做二叉排序树的根节点)
     * @return 返回的 以node为根节点的二叉排序树的最小节点的值
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        // 循环的查找左子节点，就会找到最小值
        while (target.left != null) {
            target = target.left;
        }
        // 这时 target 就指向了最小结点
        // 删除最小结点
        delete(target.value);
        return target.value;
    }

    /**
     * 删除节点
     * @param value
     */
    public void delete(int value) {
        if (root == null) return;
        else {
            // 1 先去找到要删除的结点 targetNode
            Node targetNode = search(value);
            if (targetNode == null) return;
            // 如果我们发现当前这颗二叉排序树只有一个结点
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }

            // 2 找到targetNode的父结点 parent
            Node parent = searchParent(value);
            if (targetNode.left == null && targetNode.right == null) {
                // 判断targetNode是父结点的左子结点还是右子结点
                if (parent.left != null && parent.left.value == value) { // 左子节点
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == value) { // 右子节点
                    parent.right = null;
                }
            }else if (targetNode.left != null && targetNode.right != null) {
                // 删除的结点有两颗子树
                int minVal = delRightTreeMin(targetNode.right);
                targetNode.value = minVal;
            }else { // 删除的结点只有一颗子树，一起判断都是左边还是右边
                if (targetNode.left != null) { // 如果targetNode有左子结点
                    if (parent != null) { // 防止空指针异常，删到最后两三个节点时
                        if (parent.left.value == targetNode.value) { // 如果targetNode是parent的左子结点
                            parent.left = targetNode.left;
                        } else if (parent.right.value == targetNode.value) { // 如果targetNode是parent的右子结点
                            parent.right = targetNode.left;
                        }
                    }else root = targetNode.left;
                }else { // 如果targetNode有右子结点
                    if (parent != null) { // 同上
                        if (parent.left.value == targetNode.value) { // 如果targetNode是parent的左子结点
                            parent.left = targetNode.right;
                        } else if (parent.right.value == targetNode.value) { // 如果targetNode是parent的右子结点
                            parent.right = targetNode.right;
                        }
                    }else root = targetNode.right;
                }

            }



        }
    }
}


class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    /**
     * 递归添加节点，满足二叉排序树的性质
     * @param node
     */
    public void add(Node node) {
        if (node == null) {
            return;
        }

        // 如果传入的节点的值小于当前节点的值
        if (node.value < this.value) {
            if (this.left == null) {
                this.left = node;
            } else { // 递归向左子树添加
                this.left.add(node);
            }
        } else { // 如果传入的节点的值大于当前节点的值
            if (this.right == null) {
                this.right = node;
            } else { // 递归向右子树添加
                this.right.add(node);
            }
        }
    }

    // 中序遍历
    public void infixOrder() {
        if (this.left != null) this.left.infixOrder();
        System.out.println(this);
        if (this.right != null) this.right.infixOrder();
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    /**
     * 查询要删除的节点
     * @param value 要删除的节点的值
     * @return 如果找到返回该结点，否则返回null
     */
    public Node search(int value) {
        if (value == this.value) return this;
        else if (value < this.value) { // 如果查找的值小于当前结点，向左子树递归查找
            if (this.left == null) return null;
            return this.left.search(value);
        } else { // 如果查找的值大于当前结点，向右子树递归查找
            if (this.right == null) return null;
            return this.right.search(value);
        }
    }

    /**
     * 查询要删除的节点的父节点
     * @param value 要删除的节点的值
     * @return 返回要删除的节点的父节点，如果没有找到返回null
     */
    public Node searchParent(int value) {
        // 如果当前结点就是要删除的结点的父结点，就返回
        if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
            return this;
        } else {
            if (value < this.value && this.left != null) { //向左子树递归查找
                return this.left.searchParent(value);
            } else if (value > this.value && this.right != null) { //向右子树递归查找
                return this.right.searchParent(value);
            }
        }
        return null; // 没有找到父节点
    }
}
