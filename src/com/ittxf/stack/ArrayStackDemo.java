package com.ittxf.stack;

import java.util.Scanner;

public class ArrayStackDemo {
    public static void main(String[] args) {
        // 测试一下ArrayStack 是否正确
        // 先创建一个ArrayStack对象->表示栈
        ArrayStack arrayStack = new ArrayStack(4);
        String key = "";
        boolean loop = true; // 控制是否退出菜单
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            System.out.println("show: 显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 入栈");
            System.out.println("pop: 出栈");
            System.out.println("list: 显示栈");
            System.out.println("请输入你的选择");
            key = scanner.next();
            switch (key) {
                case "show":
                    arrayStack.list();
                    break;
                case "exit":
                    loop = false;
                    break;
                case "push":
                    System.out.println("请输入一个数字");
                    int value = scanner.nextInt();
                    arrayStack.push(value);
                    break;
                case "pop":
                    try {
                        int res = arrayStack.pop();
                        System.out.println("出栈的数字是" + res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }
    }
}

// 定义一个 ArrayStack 表示栈
class ArrayStack {
    private int maxSize; // 栈的大小
    private int[] stack; // 栈数组
    private int top = -1; // 栈顶指针，初始值为-1

    // 构造器
    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new int[this.maxSize];
    }

    // 栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    // 栈空
    public boolean isEmpty() {
        return top == -1;
    }

    // 入栈
    public void push(int value) {
        if (isFull()) {
            System.out.println("栈满，无法入栈");
            return;
        }
        stack[++top] = value;
    }

    // 出栈
    public int pop() {
        if (isEmpty()) {
            // 不能返回值万一刚好是存入的值分不清
            throw new RuntimeException("栈空，无法出栈");
        }
        return stack[top--];
    }

    // 遍历栈
    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，无法遍历");
            return;
        }
        // 从栈顶开始遍历
        for (int i = top; i >= 0 ; i--) {
            System.out.println("stack[" + i + "]=" + stack[i]);
        }
    }
}
