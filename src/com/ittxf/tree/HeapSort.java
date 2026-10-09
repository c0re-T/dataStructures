package com.ittxf.tree;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class HeapSort {
    public static void main(String[] args) {
        // 要求将数组进行升序排序
        // int[] arr = {4,6,8,5,9};

        // 测试一下冒泡排序的速度O(n^2)，给80000个数据，测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80001); //生成一个[0,80000]的随机数
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("排序前的时间：" + simpleDateFormat.format(date1));

        heapSort(arr);

        Date date2 = new Date();
        System.out.println("排序后的时间：" + simpleDateFormat.format(date2));
        System.out.println("排序所用时间：" + (date2.getTime() - date1.getTime()) + "ms");

    }

    /**
     * 堆排序，时间复杂度O(nlogn)
     * @param arr 待排序的数组
     */
    public static void heapSort(int[] arr) {
        System.out.println("堆排序");
        // 分步完成
        // adjustHeap(arr, 1, arr.length); // 调整后的数组：[4, 9, 8, 5, 6]
        // System.out.println(Arrays.toString(arr));
        // adjustHeap(arr, 0, arr.length); // 调整后的数组：[9, 6, 8, 5, 4]
        // System.out.println(Arrays.toString(arr));

        //将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        // System.out.println(Arrays.toString(arr));

        /**
         * 将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端；
         * 重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序。
         */
        int temp;
        for (int i = arr.length - 1; i > 0; i--) {
            // 将堆顶元素与末尾元素交换
            temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            // 重新调整成大顶堆
            adjustHeap(arr, 0, i);
        }

        // System.out.println(Arrays.toString(arr));

    }

    /**
     * 将一个数组（二叉树）转换成大根堆
     * 功能：完成将以i对应的非叶子节点的树调整成大根堆
     * i = 1 => adjustHeap => {4,9,8,5,6}
     * i = 0 => adjustHeap => {9,6,8,5,4}
     * @param arr 待调整数组
     * @param i 表示非叶子节点的索引
     * @param length 表示对多少个元素进行调整，length是在逐渐减少的
     */
    public static void adjustHeap(int[] arr, int i, int length) {
        int temp = arr[i]; // 保存当前节点的值
        for (int k = i * 2 + 1; k < length; k = i * 2 + 1) { // 左子节点的索引，重新指向左子节点
            if (k + 1 < length && arr[k] < arr[k + 1]) { // 右子节点大于左子节点
                k++; // k指向右子节点
            }
            if (arr[k] > temp) { // 子节点大于父节点
                arr[i] = arr[k]; // 子节点大于父节点，将子节点的值赋给父节点
                i = k; // i指向较大的子节点，继续循环比较
            } else {
                break;
            }
        } // 循环结束后，我们已经将以i为父结点的树的最大值，放在了最顶(局部)
        arr[i] = temp; // 将temp的值放在i位置，完成一次调整
    }

}
