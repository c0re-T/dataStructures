package com.ittxf.search;

import java.util.Arrays;

public class InsertValueSearch {
    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i + 1;
        }
        System.out.println(Arrays.toString(arr));
        int index = insertValueSearch(arr, 0, arr.length - 1, 1);
        if (index == -1) {
            System.out.println("Not Found");
        } else {
            System.out.println("Found at Index: " + index);
        }
    }

    /**
     * 插值查找，是二分查找的升级版，适用于数组中元素分布比较均匀的情况
     * @param arr 数组
     * @param left 左索引
     * @param right 右索引
     * @param findValue 要查找的值
     * @return 如果找到就返回对应的下标，如果没有找到就返回-1
     */
    public static int insertValueSearch(int[] arr, int left, int right, int findValue) {
        // 必须加上，否则会数组越界
        if (left > right || findValue < arr[left] || findValue > arr[right]) {
            return -1;
        }
        // 插值查找公式
        int mid = left + (right - left) * (findValue - arr[left]) / (arr[right] - arr[left]);
        if (arr[mid] == findValue) {
            return mid;
        } else if (arr[mid] > findValue) {
            return insertValueSearch(arr, left, mid - 1, findValue);
        } else {
            return insertValueSearch(arr, mid + 1, right, findValue);
        }
    }
}
