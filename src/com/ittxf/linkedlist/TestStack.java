package com.ittxf.linkedlist;

import java.util.Stack;

// 演示栈Stack的基本使用
// 详见stack包下的栈的实现
public class TestStack {
    public static void main(String[] args) {
        // 创建一个栈（先进后出）
        Stack<String> stack = new Stack<>();
        // 入栈
        stack.push("jack");
        stack.push("tom");
        stack.push("jerry");
        System.out.println(stack);  // [jack, tom, jerry]
        // 出栈
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
