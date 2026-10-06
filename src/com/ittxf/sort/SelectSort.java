package com.ittxf.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class SelectSort {
    public static void main(String[] args) {
        // int[] arr = {101, 34, 119, 1, -1, 90, 123};

        // 测试一下选择排序的速度0(n^2)，给80000个数据，测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80001); //生成一个[0,80000]的随机数
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("排序前的时间：" + simpleDateFormat.format(date1));

        selectSort(arr);

        Date date2 = new Date();
        System.out.println("排序后的时间：" + simpleDateFormat.format(date2));
        System.out.println("排序所用时间：" + (date2.getTime() - date1.getTime()) + "ms");

        // System.out.println(Arrays.toString(arr));

    }

    /**
     * 选择排序，时间复杂度O(n^2)
     * @param arr
     */
    public static void selectSort(int[] arr) {
        int min;
        int minIndex;
        for (int i = 0; i < arr.length - 1; i++) {
            min = arr[i];
            minIndex = i;
            for (int j = i + 1; j < arr.length; j++) { // 从i+1开始，因为i到i+1已经排序好了
                if (min > arr[j]) { // 如果要从大到小排序，只需将大于号改为小于号即可
                    min = arr[j]; // 重置最小值min
                    minIndex = j; // 重置最小值索引minIndex
                }
            }
            arr[minIndex] = arr[i];
            arr[i] = min;
            // System.out.println("第" + (i + 1) + "轮排序后的数组：" + Arrays.toString(arr));
        }
    }
}
