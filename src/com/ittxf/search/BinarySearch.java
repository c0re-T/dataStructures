package com.ittxf.search;

// 注意：二分查找要求数组是有序的
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int index = binarySearch(arr, 0, arr.length - 1, 7);
        if (index == -1) {
            System.out.println("没有找到");
        } else {
            System.out.println("找到，下标为：" + index);
        }
    }

    /**
     * 二分查找
     * @param arr 数组
     * @param left 左边的索引
     * @param right 右边的索引
     * @param value 要查找的值
     * @return 如果找到就返回下标，如果没有找到就返回-1
     */
    public static int binarySearch(int[] arr, int left, int right, int value) {
        // 如果left>right，说明递归整个数组，没有找到
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (arr[mid] == value) { // 找到
            return mid;
        } else if (arr[mid] > value) { // 向左递归，-1是为了避免死循环
            return binarySearch(arr, left, mid - 1, value);
        } else { // 向右递归，+1是为了避免死循环
            return binarySearch(arr, mid + 1, right, value);
        }
    }
}
