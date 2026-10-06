package com.ittxf.search;

public class SeqSearch {
    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 1, 9, 2}; // 没有顺序的数组
        int index = seqSearch(arr, 8);
        if (index == -1) {
            System.out.println("没有找到");
        } else {
            System.out.println("找到，下标为：" + index);
        }
    }

    /**
     * 线性查找算法，遍历数组，找到返回下标，找不到返回-1
     * @param arr
     * @param value
     * @return
     */
    public static int seqSearch(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }
}
