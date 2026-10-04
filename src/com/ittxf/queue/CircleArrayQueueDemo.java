package com.ittxf.queue;

import java.util.Scanner;

public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        // 测试数组模拟环型队列测试
        // 创建一个环型队列，设置数组大小为4，说明数组最大可以存放3个元素
        CircleArrayQueue arrayQueue = new CircleArrayQueue(4);
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
class CircleArrayQueue {
    private int maxSize; // 表示数组的最大容量
    // front 变量的含义做一个调整：front 就指向队列的第一个元素，也就是说arr[front]就是队列的第一个元素
    // front 初始值为0
    private int front;
    // rear 变量的含义做一个调整：rear 指向队列的最后一个元素的后一个位置。因为需要一个位置来判断队列是否满
    // rear 初始值为0
    private int rear;
    private int[] arr; // 该数组用于存放数据，模拟队列

    // 创建队列的构造器
    public CircleArrayQueue(int arrMaxSize) {
        this.maxSize = arrMaxSize;
        this.front = 0; // 对比队列作出改变
        this.rear = 0; // 对比队列作出改变
        this.arr = new int[maxSize];
    }

    // 判断队列是否满
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
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
        // 因为rear本身就指向了队列的最后一个元素的后一个位置，所以直接赋值
        arr[rear] = n;
        // rear后移，必须考虑rear+1对maxSize取模，保证rear在0到maxSize-1之间循环
        rear = ++rear % maxSize;
    }

    // 获取队列的数据，出队列
    public int getQueue() {
        // 判断队列是否为空
        if (isEmpty()) {
            // 通过抛出异常处理
            throw new RuntimeException("队列为空，不能取数据");
        }
        // 这里需要分析出front是指向队列的第一个元素
        // 1 先把front对应的值保留到一个临时变量
        // 2 将 front 后移，必须考虑front+1对maxSize取模，保证front在0到maxSize-1之间循环
        // 3 将临时保存的变量返回
        int value = arr[front];
        front = ++front % maxSize;
        return value;
    }

    // 显示目前队列的所有数据，因为front不一定指向队列的第一个元素
    public void showQueue() {
        // 判断队列是否为空
        if (isEmpty()) {
            System.out.println("队列为空，没有数据");
            return;
        }
        // 思路：从front开始遍历，直到rear
        for (int i = front; i < front + getMaxSize(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    // 求出当前队列有效数据的个数
    public int getMaxSize() {
        // rear = 1, front = 0, maxSize = 3
        // (1 + 3 - 0) % 3 = 1
        return (rear + maxSize - front) % maxSize;
    }

    // 显示队列的头数据
    public int headQueue() {
        // 判断队列是否为空
        if (isEmpty()) {
            // 通过抛出异常处理
            throw new RuntimeException("队列为空，没有数据");
        }
        return arr[front];
    }
}

