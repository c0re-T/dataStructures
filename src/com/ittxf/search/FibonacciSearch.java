package com.ittxf.search;

import java.util.Arrays;

public class FibonacciSearch {
    public static int maxSize = 20; // 静态变量，表示数组的最大长度
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 43, 53, 62, 71, 83, 94, 105};
        int index = fibonacciSearch(arr, 71);
        if (index == -1) {
            System.out.println("没有找到");
        } else {
            System.out.println("找到，下标为：" + index);
        }
    }

    // 因为后面我们mid=low+F(k-1)-1，需要使用到斐波那契数列，因此我们需要先获取到一个斐波那契数列
    // 非递归方法得到一个斐波那契数列
    public static int[] fibonacci(int n) {
        int[] f = new int[n];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i < n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    /**
     * 斐波那契查找，黄金分割法
     * @param arr 数组
     * @param key 要查找的值
     * @return 如果找到就返回对应的下标，如果没有找到就返回-1
     */
    public static int fibonacciSearch(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;
        int k = 0; // 表示斐波那契数列的下标
        int mid = 0; // 存放中间值
        int[] fibonacci = fibonacci(maxSize); // 获取斐波那契数列

        while (high > fibonacci[k] - 1) {
            k++;
        }
        // 因为f[k]值可能大于a 的长度，因此我们需要使用Arrays类，构造一个新的数组，并指向arr[]
        // 不足的部分用0填充
        int[] temp = Arrays.copyOf(arr, fibonacci[k]);
        // 实际上需求使用arr数组最后的数填充temp数组
        System.arraycopy(arr, 0, temp, 0, arr.length);
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }

        // 使用while来循环处理，找到我们的数key
        while (low <= high) { //只要这个条件满足，就可以找
            mid = low + fibonacci[k - 1] - 1;
            if (key < temp[mid]) { // 左边查找
                // 为什么是 k--
                // 说明
                // 1 全部元素= 前面的元素+ 后边元素
                // 2 f[k] = f[k-1] + f[k-2]
                // 因为前面有f[k-1]个元素,所以可以继续拆分 f[k-1] = f[k-2] + f[k-3]
                // 即在f[k-1] 的前面继续查找k--
                // 即下次循环 mid = f[k-1-1]-1
                high = mid - 1;
                k--;
            } else if (key > temp[mid]) { // 右边查找
                // 为什么是k-=2
                // 说明
                // 1 全部元素= 前面的元素+ 后边元素
                // 2 f[k] = f[k-1] + f[k-2]
                // 3 因为后面我们有f[k-2] 所以可以继续拆分f[k-1] = f[k-3] +f[k-4]/4。即在f[k-2]的前面进行查找k -=2
                // 5 即下次循环 mid = f[k - 1 - 2] - 1
                low = mid + 1;
                k-=2;
            } else { // 找到
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }
        return -1;
    }
}
