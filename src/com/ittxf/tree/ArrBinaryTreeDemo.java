package com.ittxf.tree;

public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
        // 创建一个ArrBinaryTree
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        arrBinaryTree.allOrder(2);
    }
}

// 编写一个ArrayBinaryTree，实现顺序存储二叉树遍历
class ArrBinaryTree{
    private int[] arr;

    public ArrBinaryTree(int[] arr){
        this.arr = arr;
    }

    //重载showArray
    public void allOrder(){
        allOrder(0);
    }

    //编写一个方法，完成顺序存储二叉树的前/中/后序遍历
    public void allOrder(int index){
        if(arr == null || arr.length == 0){
            System.out.println("数组为空，不能遍历");
        }
        //输出当前这个元素
        System.out.println(arr[index]);
        //向左递归遍历
        if(index * 2 + 1 < arr.length){
            allOrder(index * 2 + 1);
        }
        // System.out.println(arr[index]); 中序遍历
        //向右递归遍历
        if(index * 2 + 2 < arr.length){
            allOrder(index * 2 + 2);
        }
        // System.out.println(arr[index]); 后序遍历
    }
}
