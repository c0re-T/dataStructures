package com.ittxf.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class QuickSort {
    public static void main(String[] args) {
        // int[] arr = {5, 3, 8, 1, 9, 2};
        // quickSort(arr, 0, arr.length - 1);

        // 测试一下快速排序的速度O(nlogn)，给80000个数据，测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80001); //生成一个[0,80000]的随机数
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("排序前的时间：" + simpleDateFormat.format(date1));

        quickSort(arr,0,arr.length-1);

        Date date2 = new Date();
        System.out.println("排序后的时间：" + simpleDateFormat.format(date2));
        System.out.println("排序所用时间：" + (date2.getTime() - date1.getTime()) + "ms");


        // System.out.println("快速排序的结果为：" + Arrays.toString(arr));
    }

    /**
     * 快速排序，递归实现，时间复杂度O(nlogn)
     * @param arr
     * @param low
     * @param high
     */
    public static void quickSort(int[] arr, int low, int high) {
        int l = low; // 左索引
        int r = high; // 右索引
        int pivot = arr[(l + r) / 2]; // 中轴值
        int temp; // 临时变量，用于交换

        // while 循环的目的是找到比中轴值大的数和比中轴值小的数
        // 大的数在右边，小的数在左边
        while (l < r) {
            // 在pivot的左边一直找，找到大于等于pivot值,才退出
            while (arr[l] < pivot) {
                l++;
            }
            // 在pivot的右边一直找，找到小于等于pivot值,才退出
            while (arr[r] > pivot) {
                r--;
            }
            // 如果l >= r说明pivot的左右两边的值，已经按照左边小于等于pivot，右边大于等于pivot排序
            if (l >= r) {
                break;
            }
            // 交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            // 目的：去重，防止出现[5,5,5,5,5,5,5]这种极端数组，l和r一直不动，死循环（CPU 飙升，程序卡死）
            // 如果交换完后，发现arr[l] == pivot, r-- 前进
            if (arr[l] == pivot) {
                r--;
            }
            // 如果交换完后，发现arr[r] == pivot, l++ 后移
            if (arr[r] == pivot) {
                l++;
            }
        }

        // 如果l == r, 必须l++, r--, 否则栈溢出
        if (l == r) {
            l++;
            r--;
        }

        // 目的：防止无限递归，导致 StackOverflowError（栈内存溢出，程序崩溃），例如[3,4,5]
        // 一直触发quickSort(arr, 0, 1)
        // 向左递归
        if (low < r) quickSort(arr, low, r);
        // 向右递归
        if (high > l) quickSort(arr, l, high);
    }
}
