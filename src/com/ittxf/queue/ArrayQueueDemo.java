package com.ittxf.queue;

import java.util.Scanner;

public class ArrayQueueDemo {
    public static void main(String[] args) {
        // 测试
        // 创建一个队列
        ArrayQueue arrayQueue = new ArrayQueue(3);
        char key = ' '; // 接受用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        // 输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 显示队列的头数据");
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                case 'a':
                    System.out.println("请输入一个数字");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = arrayQueue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }
        }
        System.out.println("程序退出...........");
    }
}

// 使用数组模拟队列-编写一个ArrayQueue类
class ArrayQueue {
    private int maxSize; // 表示数组的最大容量
    private int front; // 队列头
    private int rear; // 队列尾
    private int[] arr; // 该数组用于存放数据，模拟队列

    // 创建队列的构造器
    public ArrayQueue(int arrMaxSize) {
        this.maxSize = arrMaxSize;
        this.front = -1; // 指向队列头部，分析出front是指向队列头的前一个位置
        this.rear = -1; // 指向队列尾部，指向队列尾部的数据（即就是队列的最后一个数据）
        this.arr = new int[maxSize];
    }

    // 判断队列是否满
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    // 添加数据到队列
    public void addQueue(int n) {
        // 判断队列是否满
        if (isFull()) {
            System.out.println("队列已满，不能添加数据");
            return;
        }
        rear++; // 让rear后移，代表存入一个元素
        arr[rear] = n;
    }

    // 获取队列的数据，出队列
    public int getQueue() {
        // 判断队列是否为空
        if (isEmpty()) {
            // 通过抛出异常处理
            throw new RuntimeException("队列为空，不能取数据");
        }
        front++; // front后移，代表取出一个元素
        return arr[front];
    }

    // 显示目前队列的所有数据，因为front不一定指向队列的第一个元素
    public void showQueue() {
        // 判断队列是否为空
        if (isEmpty()) {
            System.out.println("队列为空，没有数据");
            return;
        }
        for (int i = front + 1; i <= rear; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    // 显示队列的头数据
    public int headQueue() {
        // 判断队列是否为空
        if (isEmpty()) {
            // 通过抛出异常处理
            throw new RuntimeException("队列为空，没有数据");
        }
        return arr[front + 1];
    }
}

