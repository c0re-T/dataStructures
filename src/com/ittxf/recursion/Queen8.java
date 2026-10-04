package com.ittxf.recursion;

public class Queen8 {
    // 定义一个max表示共有多少个皇后
    int max = 8;
    // 定义数组array，用于存放皇后放置位置的结果
    // 比如：arr = {0, 4, 7, 5, 2, 6, 1, 3}
    int[] array = new int[max];
    public static void main(String[] args) {

    }

    // 写一个方法，可以将皇后摆放的位置打印出来
    private void print() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }
        System.out.println();
    }

    // 查看当我们放置第n个皇后，就去检测该皇后是否和前面已经摆放的皇后冲突
    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            // array[i] == array[n] 表示在同一列
            // Math.abs(n - i) == Math.abs(array[n] - array[i]) 表示在对角线
            // 比如：n = 1, i = 0, array[n] = 1, array[i] = 0
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    // 编写一个方法，放置第n个皇后
    private void place(int n) {
    }

}
