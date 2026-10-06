package com.ittxf.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class InsertSort {
    public static void main(String[] args) {
        // int[] arr = {101, 34, 119, 1, -1, 90, 123};

        // 测试一下冒泡排序的速度O(n^2)，给80000个数据，测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80001); //生成一个[0,80000]的随机数
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("排序前的时间：" + simpleDateFormat.format(date1));

        insertSort(arr);

        Date date2 = new Date();
        System.out.println("排序后的时间：" + simpleDateFormat.format(date2));
        System.out.println("排序所用时间：" + (date2.getTime() - date1.getTime()) + "ms");

        // System.out.println(Arrays.toString(arr));
    }

    /**
     * 插入排序，时间复杂度O(n^2)
     * @param arr
     */
    public static void insertSort(int[] arr) {
        int insertValue;
        int insertIndex;
        for (int i = 1; i < arr.length; i++) {
            insertValue = arr[i];
            insertIndex = i - 1;
            // insertIndex >= 0保证在给insertValue 找插入位置，不越界
            // arr[insertIndex] > insertValue 表示待插入的数比插入位置的数大
            // 较大的数后移，为insertValue提供插入位置，并且把需要比较的数前移
            // 如果要改成降序，则改为arr[insertIndex] < insertValue
            while (insertIndex >= 0 && arr[insertIndex] > insertValue) {
                arr[insertIndex + 1] = arr[insertIndex]; // 后移较大的那个数
                insertIndex--; // 为了比较前一个数，顺便给insertValue提供插入位置
            }
            // 当退出while循环时，说明插入位置找到，insertIndex + 1
            // 这里我们判断是否需要赋值
            if (insertIndex + 1 != i) {
                arr[insertIndex + 1] = insertValue;
            }
            // System.out.println("第" + i + "轮插入：" + Arrays.toString(arr));
        }
    }

}
