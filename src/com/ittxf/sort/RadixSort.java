package com.ittxf.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class RadixSort {
    public static void main(String[] args) {
        // int[] array = {12, 342, 54, 2, 3};
        // radixSort(array);

        // 测试一下基数排序（桶排序的扩展）的速度O(nk)，给80000个数据，测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80001); //生成一个[0,80000]的随机数
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("排序前的时间：" + simpleDateFormat.format(date1));

        radixSort(arr);

        Date date2 = new Date();
        System.out.println("排序后的时间：" + simpleDateFormat.format(date2));
        System.out.println("排序所用时间：" + (date2.getTime() - date1.getTime()) + "ms");

        // System.out.println(Arrays.toString(array));
    }

    /**
     * 基数排序，时间复杂度O(nk)，空间复杂度O(n + k)
     * 明确，基数排序是稳定的排序算法，空间换时间的经典算法
     * 缺点：基数排序只能用于排序整数，不能用于排序小数和字符，负数排序需要特殊处理
     * @param array
     */
    public static void radixSort(int[] array) {
        // 得到数组中最大数的位数
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        int maxLength = (max + "").length();

        // 定义一个二维数组，表示10个桶，每个桶就是一个一维数组，
        // 每个一维数组(桶)，大小定为arr.length，防止溢出
        int[][] bucket = new int[10][array.length];
        // 定义一个一维数组，记录每个桶中实际存放的数据个数
        int[] bucketElementCount = new int[10];
        // 遍历array数组，将每个元素放入对应的桶中
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            for (int j = 0; j < array.length; j++) {
                // 获取每个元素的n位数
                int digitOfElement = array[j] / n % 10;
                // 将元素放入对应的桶中
                bucket[digitOfElement][bucketElementCount[digitOfElement]] = array[j];
                // 桶中记录元素的个数加1
                bucketElementCount[digitOfElement]++;
            }
            // 按照桶的顺序(0 - 9)取出数据，放入原数组
            int index = 0;
            // 遍历桶数组，将每个桶中的数据放入原数组
            for (int k = 0; k < bucketElementCount.length; k++) {
                for (int j = 0; j < bucketElementCount[k]; j++) {
                    array[index] = bucket[k][j];
                    index++;
                }
            }
            // 清空桶元素个数记录数组
            Arrays.fill(bucketElementCount, 0);
            // System.out.println("第" + (i + 1) + "轮排序, 数组 = " + Arrays.toString(array));
        }

    }
}
