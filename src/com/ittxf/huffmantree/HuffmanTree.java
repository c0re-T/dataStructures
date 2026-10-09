package com.ittxf.huffmantree;

import java.util.*;

public class HuffmanTree {
    public static void main(String[] args) {
        int arr[] = {13, 7, 8, 3, 29, 6, 1};
        Node huffmanTree = createHuffmanTree(arr);
        System.out.println("最终赫夫曼树 = " + huffmanTree);

        preOrder(huffmanTree);
    }

    // 创建赫夫曼树的方法
    public static Node createHuffmanTree(int[] arr) {
        // 第一步为了操作方便
        // 1 遍历 arr 数组
        // 2 将arr的每个元素构成成一个Node
        // 3 将 Node 放入到 ArrayList 中
        List<Node> nodes = new ArrayList<>();
        for (int value : arr) {
            nodes.add(new Node(value));
        }

        // 排序
        Collections.sort(nodes);
        System.out.println("原始nodes = " + nodes);

        while (nodes.size() > 1) {
            // 取出根节点权值最小的两颗二叉树
            // 1 取出权值最小的节点（二叉树）
            Node leftNode = nodes.get(0);
            // 2 取出权值第二小的节点 （二叉树）
            Node rightNode = nodes.get(1);
            // 3 组合成为一个新的二叉树
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;

            // 4 将已经处理过的两颗二叉树从 nodes ArrayList 中取出
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            // 5 将新的二叉树放入到 nodes ArrayList 中
            nodes.add(parent);

            Collections.sort(nodes);
            // System.out.println("第一次处理后nodes = " + nodes);
        }

        return nodes.get(0);
    }

    // 编写一个前序遍历的方法
    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("空树，无法遍历");
        }
    }
}

// 创建节点类
// 让 Node 对象持续排序Collections集合排序，需要让Node 实现Comparable接口
// 实现 Comparable 接口，就是告诉 Java：“请按照我写的规则来比较这两个 Node。”
class Node implements Comparable<Node> {
    int value; // 结点权值
    Node left; // 指向左子节点
    Node right; // 指向右子节点

    public Node(int value) {
        this.value = value;
    }

    // 写一个前序遍历
    public void preOrder() {
        System.out.print(this.value + " ");
        if (this.left != null) this.left.preOrder();
        if (this.right != null) this.right.preOrder();
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        // 从小到大排序
        return this.value - o.value;
    }
}