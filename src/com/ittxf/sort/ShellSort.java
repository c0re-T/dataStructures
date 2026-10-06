package com.ittxf.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ShellSort {
    public static void main(String[] args) {
        // int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        // shellSortStep(arr);
        // exchangeShellSort(arr);

        // 测试一下两种希尔排序的速度O(n^2)，给80000个数据，测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80001); //生成一个[0,80000]的随机数
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("排序前的时间：" + simpleDateFormat.format(date1));

        // exchangeShellSort(arr);
        moveShellSort(arr);

        Date date2 = new Date();
        System.out.println("排序后的时间：" + simpleDateFormat.format(date2));
        System.out.println("排序所用时间：" + (date2.getTime() - date1.getTime()) + "ms");

        // System.out.println("希尔排序后数组：" + Arrays.toString(arr));
    }

    /**
     * 交换法希尔排序，时间复杂度最坏 O(n²)，平均 O(n^1.3)
     * 希尔排序时，对有序序列在插入时采用交换法，可以降低插入排序的时间复杂度
     * @param arr
     */
    public static void exchangeShellSort(int[] arr) {
        int temp;
        // 根据gap的值，进行分组比较
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 遍历这组数据（i = gap），步长为gap
            for (int i = gap; i < arr.length; i++) {
                // 遍历这组数据（j = i - gap），步长为gap
                for (int j = i - gap; j >= 0; j -= gap) {
                    // 如果当前元素大于加上步长后的那个元素，说明交换
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
        }
    }

    /**
     * 移动法希尔排序，对交换法希尔排序的优化，时间复杂度最坏 O(n²)，平均 O(n^1.3)
     * @param arr
     */
    public static void moveShellSort(int[] arr) {
        // 增量gap，并逐步的缩小增量
        int j; // 用于交换的索引
        int temp; // 用于交换的临时变量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 遍历这组数据（i = gap），步长为gap
            for (int i = gap; i < arr.length; i++) {
                j = i;
                temp = arr[j];
                // 寻找当前元素在组内的正确插入位置
                // 如果当前元素小于其步长为-gap元素，则交换
                while (j - gap >= 0 && temp < arr[j - gap]) {
                    // 移动元素
                    arr[j] = arr[j - gap];
                    // 步长减小
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }

    // 使用逐步推导的方式来编写希尔排序
    public static void shellSortStep(int[] arr) {
        int temp;
        // 希尔排序的第1轮排序
        // 因为第1轮排序，是将10个数据分成了5组，所以这里i = 5
        for (int i = 5; i < arr.length; i++) {
            // 遍历这5组数据（i = 5, 0 1 2 3 4），步长为5
            for (int j = i - 5; j >= 0; j -= 5) {
                // 如果当前元素大于加上步长后的那个元素，说明交换
                if (arr[j] > arr[j + 5]) {
                    temp = arr[j];
                    arr[j] = arr[j + 5];
                    arr[j + 5] = temp;
                }
            }
        }
        System.out.println("第一轮排序后的数组：" + Arrays.toString(arr));

        // 希尔排序的第2轮排序
        // 因为第2轮排序，是将10个数据分成了2组，所以这里i = 2
        for (int i = 2; i < arr.length; i++) {
            // 遍历这2组数据（i = 2, 0 1），步长为2
            for (int j = i - 2; j >= 0; j -= 2) {
                // 如果当前元素大于加上步长后的那个元素，说明交换
                if (arr[j] > arr[j + 2]) {
                    temp = arr[j];
                    arr[j] = arr[j + 2];
                    arr[j + 2] = temp;
                }
            }
        }
        System.out.println("第二轮排序后的数组：" + Arrays.toString(arr));

        // 希尔排序的第3轮排序
        // 因为第3轮排序，是将10个数据分成了1组，所以这里i = 1
        for (int i = 1; i < arr.length; i++) {
            // 遍历这1组数据（i = 5, 0 1 2 3 4），步长为5
            for (int j = i - 1; j >= 0; j -= 1) {
                // 如果当前元素大于加上步长后的那个元素，说明交换
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println("第三轮排序后的数组：" + Arrays.toString(arr));
    }
}
