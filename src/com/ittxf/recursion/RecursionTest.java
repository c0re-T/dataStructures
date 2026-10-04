package com.ittxf.recursion;

public class RecursionTest {
    public static void main(String[] args) {
        // 测试递归方法调用机制
        System.out.println("===================打印问题===================");
        test(5);
        System.out.println("===================阶乘问题===================");
        int res = factorial(2);
        System.out.println("res = " + res);
    }

    // 打印问题
    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        } /*else {
            System.out.println("n = " + n);
        }*/
        // 不加 else 语句，程序会根据开辟栈的顺序，依次打印 n = 2, n = 3, n = 4, n = 5
        System.out.println("n = " + n);
    }

    // 阶乘问题
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
        // return n * factorial(n - 1);
    }
}
