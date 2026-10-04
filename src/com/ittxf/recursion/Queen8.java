package com.ittxf.recursion;

public class Queen8 {
    // 定义一个max表示共有多少个皇后
    int max = 8;
    // 定义数组array，用于存放皇后放置位置的结果
    // 比如：arr = {0, 4, 7, 5, 2, 6, 1, 3}
    int[] array = new int[max];
    // 统计解的个数
    int count = 0;
    // 统计判断次数
    int judgeCount = 0;
    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.place(0);
        System.out.println("一共有" + queen8.count + "种解法");
        System.out.println("判断次数为" + queen8.judgeCount);
    }

    // 写一个方法，可以将皇后摆放的位置打印出来
    private void print() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }
        System.out.println();
        count++;
    }

    // 查看当我们放置第n个皇后，就去检测该皇后是否和前面已经摆放的皇后冲突
    private boolean judge(int n) {
        judgeCount++;
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
    // 特别注意：place 是每一次递归时，进入到 place 中都有 for(int i = 0; i < max; i++)
    private void place(int n) {
        if (n == max) { // n = 8，表示8个皇后已经放好了
            print();
            return;
        }
        // 依次放入皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            // 先把当前这个皇后n，放到该行的第2列
            array[n] = i;
            // 判断是否冲突
            if (judge(n)) { // 不冲突
                // 如果不冲突，接着放n+1个皇后
                place(n + 1);
            }
            // 如果冲突，就继续执行array[n] = i; 即将第n个皇后，放到本行的后一列
        }
    }

}
