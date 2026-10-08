package com.ittxf.search;

import java.util.ArrayList;
import java.util.List;

// 注意：二分查找要求数组是有序的
public class BinarySearch {
    public static void main(String[] args) {
        // int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // int index = binarySearch(arr, 0, arr.length - 1, 7);
        // if (index == -1) {
        //     System.out.println("没有找到");
        // } else {
        //     System.out.println("找到，下标为：" + index);
        // }

        int[] arr = {1, 2, 3, 4, 4, 4, 4, 5, 5, 6, 7, 8, 9, 10};
        ArrayList<Integer> indexList = binarySearch2(arr, 0, arr.length - 1, 4);
        if (indexList.isEmpty()) {
            System.out.println("没有找到");
        } else {
            System.out.println("找到，下标为：" + indexList);
        }
    }

    /**
     * 二分查找
     * @param arr 数组
     * @param left 左边的索引
     * @param right 右边的索引
     * @param value 要查找的值
     * @return 如果找到就返回下标，如果没有找到就返回-1
     */
    public static int binarySearch(int[] arr, int left, int right, int value) {
        // 如果left>right，说明递归整个数组，没有找到
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (arr[mid] == value) { // 找到
            return mid;
        } else if (arr[mid] > value) { // 向左递归，-1是为了避免死循环
            return binarySearch(arr, left, mid - 1, value);
        } else { // 向右递归，+1是为了避免死循环
            return binarySearch(arr, mid + 1, right, value);
        }
    }

    /**
     * {1,8,10,89,1000,1000,1234}当—个有序数组中，可能存在多个相同的值，如何将所有89都找到，给出下标
     * 1.先找到mid索引值，不要马上返回
     * 2.向mid的左边扫描，将所有满足1000的元素的下标，加入到ArrayList
     * 3.向mid的右边扫描，将所有满足1000的元素的下标，加入到ArrayList
     * 4.将ArrayList返回
     */
    public static ArrayList<Integer> binarySearch2(int[] arr, int left, int right, int value) {
        if (left > right) {
            return new ArrayList<>();
        }
        int mid = (left + right) / 2;
        if (arr[mid] == value) {
            // 向mid索引值的左边扫描，将所有满足1000，的元素的下标，加入到集合ArrayList
            ArrayList<Integer> resIndexList = new ArrayList<>();
            resIndexList.add(mid);
            int temp = mid - 1;
            while (true) {
                if (temp < 0 || arr[temp] != value) { // 退出循环
                    break;
                }
                resIndexList.add(temp);  // 将找到的下标加入到resIndexList
                temp--; // temp左移
            }

            // 向mid索引值的右边扫描，将所有满足1000，的元素的下标，加入到集合ArrayList
            temp = mid + 1;
            while (true) {
                if (temp > right || arr[temp] != value) { // 退出循环
                    break;
                }
                resIndexList.add(temp);  // 将找到的下标加入到resIndexList
                temp++; // temp右移
            }

            return resIndexList;
        } else if (arr[mid] > value) {
            return binarySearch2(arr, left, mid - 1, value);
        } else {
            return binarySearch2(arr, mid + 1, right, value);
        }
    }
}
