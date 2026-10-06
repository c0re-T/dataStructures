package com.ittxf.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 归并排序
 * mergeSort(0,7)                       ← 第1次调用
 * ├── A: mergeSort(0,3)                ← 第2次调用（暂停0,7）
 * │   ├── A: mergeSort(0,1)            ← 第3次调用（暂停0,3）
 * │   │   ├── A: mergeSort(0,0) 返回   ← 第4次调用（触底返回）
 * │   │   ├── B: mergeSort(1,1) 返回   ← 第5次调用（触底返回）
 * │   │   └── C: merge(0,0,1) ✅ 合并1
 * │   ├── B: mergeSort(2,3)            ← 第6次调用（暂停0,3）
 * │   │   ├── A: mergeSort(2,2) 返回
 * │   │   ├── B: mergeSort(3,3) 返回
 * │   │   └── C: merge(2,2,3) ✅ 合并2
 * │   └── C: merge(0,1,3) ✅ 合并3
 * ├── B: mergeSort(4,7)                ← 第7次调用（暂停0,7）
 * │   ├── A: mergeSort(4,5) → 内部合并 ✅ 合并4
 * │   ├── B: mergeSort(6,7) → 内部合并 ✅ 合并5
 * │   └── C: merge(4,5,7) ✅ 合并6
 * └── C: merge(0,3,7) ✅ 合并7（最终结果）
 */
public class MergeSort {
    public static void main(String[] args) {
        // int[] arr = {8, 4, 5, 7, 1, 3, 10, 16}; // 8 -> merge 7
        // int[] temp = new int[arr.length];
        // mergeSort(arr, 0, arr.length - 1, temp);

        // 测试一下归并排序的速度O(nlogn)，给80000个数据，测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80001); //生成一个[0,80000]的随机数
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("排序前的时间：" + simpleDateFormat.format(date1));

        mergeSort(arr, 0, arr.length - 1, new int[arr.length]);

        Date date2 = new Date();
        System.out.println("排序后的时间：" + simpleDateFormat.format(date2));
        System.out.println("排序所用时间：" + (date2.getTime() - date1.getTime()) + "ms");


        // System.out.println("归并排序后的数组：" + Arrays.toString(arr));
    }

    /**
     * 分+治+合
     * @param arr 待排序的数组
     * @param left 左边有序序列的初始索引
     * @param right 右边最后一个索引
     * @param temp  临时数组，用于存放合并后的结果
     */
    public static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2; // 中间索引
            mergeSort(arr, left, mid, temp); // 左边有序序列递归排序
            mergeSort(arr, mid + 1, right, temp); // 右边有序序列递归排序
            merge(arr, left, mid, right, temp); // 合并两个有序序列
        }
    }

    /**
     * 分治法排序，归并的方法，时间复杂度 O(nlogn)
     * @param arr 排序的原始数组
     * @param left 左边有序序列的初始索引
     * @param mid 中间索引
     * @param right 左边有序序列的初始索引
     * @param temp  临时数组，用于存放合并后的结果
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left; // 初始化i,左边有序序列的初始索引
        int j = mid + 1; // 初始化j, 右边有序序列的初始索引
        int t = 0; // 初始化t, 用于临时数组的索引

        // (一)
        // 先把左右两边(有序)的数据按照规则填充到temp数组
        // 直到左右两边的有序序列，有一边处理完毕为止
        while (i <= mid && j <= right) { // 继续
            // 如果左边的有序序列的当前元素小于等于右边的有序序列的当前元素
            // 即将左边的当前元素填充到temp数组
            // t++ i++
            if (arr[i] <= arr[j]) {
                temp[t] = arr[i];
                t++;
                i++;
            // 如果左边的有序序列的当前元素大于右边的有序序列的当前元素
            // 即将右边的当前元素填充到temp数组
            // t++ j++
            } else {
                temp[t] = arr[j];
                t++; // 为了给下方的temp[t] = arr[j/i]; 准备空间
                j++;
            }
        }

        // (二)
        // 把有剩余数据的一边的数据依次全部填充到temp
        while (i <= mid) { // 左边的有序序列还有剩余的元素，就全部填充到temp
            temp[t] = arr[i]; // 将左边的有序序列的当前元素填充到temp
            t++;
            i++;
        }
        while (j <= right) { // 右边的有序序列还有剩余的元素，就全部填充到temp
            temp[t] = arr[j];
            t++;
            j++;
        }

        // (三)
        // 将temp数组的元素拷贝到arr
        // 注意：并不是所有的arr都拷贝完temp，因为可能有剩余的元素
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) { // 第一次合并 tempLeft = 0, right = 1 // tempLeft = 2 right =3//
            arr[tempLeft] = temp[t];
            t++;
            tempLeft++;
        }
    }
}


