package com.ittxf.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class BubbleSort {
    public static void main(String[] args) {
        // int[] arr = {3, 9, -1, 10, -2};
        // int[] arr = {1, 2, 3, 4, 5};

        // 测试一下冒泡排序的速度O(n^2)，给80000个数据，测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80001); //生成一个[0,80000]的随机数
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("排序前的时间：" + simpleDateFormat.format(date1));

        bubbleSort(arr);

        Date date2 = new Date();
        System.out.println("排序后的时间：" + simpleDateFormat.format(date2));
        System.out.println("排序所用时间：" + (date2.getTime() - date1.getTime()) + "ms");

        // System.out.println(Arrays.toString(arr));
    }

    /**
     * 冒泡排序，时间复杂度O(n^2)
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        int temp = 0; // 临时变量，用于交换
        boolean flag = false; // 标志位，用于优化，如果某趟排序没有发生交换，则说明数组已经有序，可以提前退出
        for (int i = 0; i < arr.length; i++) { // 外层循环，控制趟数
            // 内层循环，控制每趟比较的次数，每次比较后，最大的数会浮到右边，所以这里arr.length - i - 1
            for (int j = 0; j < arr.length - i - 1; j++) {
                // 如果前面的数比后面的数大，则交换
                if (arr[j] > arr[j + 1]) {
                    flag = true; // 发生了交换，将标志位设为true
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            if (!flag) {
                // System.out.println("数组已经有序，提前退出"); // 优化后的代码中，这里会提前退出，所以这句话不会打印
                break; // 如果某趟排序没有发生交换，则说明数组已经有序，可以提前退出
            }else {
                flag = false; // 重置标志位
                // System.out.println("第" + (i + 1) + "趟排序后的数组：" + Arrays.toString(arr));
            }
        }
    }
}
