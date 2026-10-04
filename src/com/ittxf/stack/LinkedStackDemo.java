package com.ittxf.stack;

import java.util.Scanner;

/**
 * 用链表模拟栈（链栈）：栈顶对准链表头部，入栈出栈都在头部这一端完成，所以都是 O(1)
 * 与数组版 ArrayStack 的关键差别：
 * 1 top 不再是数组下标，而是一个节点引用，空栈的表示从 top == -1 变成 top == null
 * 2 没有 maxSize，也就不存在 isFull —— 容量按需增长，代价是每个节点多背一个引用
 * 3 元素个数没法用 top + 1 直接算出来，要么遍历（O(n)），要么额外维护 count（O(1)）
 */
public class LinkedStackDemo {
    public static void main(String[] args) {
        // 测试链栈：不限制容量，所以构造时不用传 maxSize
        LinkedStack stack = new LinkedStack();
        String key = "";
        boolean loop = true; // 控制是否退出菜单
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            System.out.println("show: 显示栈的元素情况(从栈顶到栈底)");
            System.out.println("push: 入栈");
            System.out.println("pop: 出栈");
            System.out.println("peek: 查看栈顶元素(不弹出)");
            System.out.println("size: 查看栈中元素个数");
            System.out.println("exit: 退出程序");
            System.out.println("请输入你的选择");
            key = scanner.next();
            switch (key) {
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数字");
                    // 入栈是 void 方法，做不了也没什么结果要交回，直接执行即可（链栈不会满）
                    stack.push(scanner.nextInt());
                    break;
                case "pop":
                    try {
                        int res = stack.pop();
                        System.out.println("出栈的数字是" + res);
                    } catch (Exception e) {
                        // pop 必须返回一个 int，空栈时返回哪个数都可能是真实数据，只能用异常表达失败
                        System.out.println(e.getMessage());
                    }
                    break;
                case "peek":
                    try {
                        int res = stack.peek();
                        System.out.println("栈顶的数字是" + res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "size":
                    System.out.println("栈中目前有" + stack.size() + "个数据");
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    System.out.println("输入指令有误，请重新输入");
                    break;
            }
        }
    }
}

// 定义一个 LinkedStack 表示链栈
class LinkedStack {
    // 栈顶指针：指向栈顶节点本身，top == null 表示空栈
    // 注意这里不像数组版那样需要 maxSize，链表天生是动态扩容的
    private StackNode top = null;
    private int count = 0; // 维护元素个数，让 size() 也是 O(1)（代价：push/pop 都要同步改它）

    // 入栈：新节点插到链表头部
    public void push(int data) {
        StackNode node = new StackNode(data);
        // 顺序不能反：① 先让新节点接住原来整条链，② 再把 top 交给新节点
        // 如果先写 top = node，原链就只剩 node.next 一个引用可寻，而 node.next 还没连上 —— 整条链直接丢失
        node.next = top; // ① 新节点的后继指向旧的栈顶
        top = node; // ② top 停在新的栈顶上
        count++;
    }

    // 出栈：摘下头节点，top 后移
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空，无法出栈");
        }
        // 同样是"先取旧位置的值，再移动指针"，跟单链表 getQueue/出队一个套路
        StackNode oldTop = top;
        int value = oldTop.data;
        top = top.next; // top 后移一格，指向新的栈顶（可能是 null）
        oldTop.next = null; // 帮 GC 一把：JDK 的 ArrayDeque.poll 也会把腾出来的格子置 null
        count--;
        return value;
    }

    // 只看栈顶，不弹出
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("栈空，无法查看栈顶");
        }
        return top.data; // 不移动 top，这是 peek 与 pop 唯一的区别
    }

    // 栈空：top 是 null
    public boolean isEmpty() {
        return top == null;
    }

    // 元素个数：因为维护了 count，这里是 O(1)
    // 如果不想多这一个字段，就得从头遍历数一遍，O(n)
    public int size() {
        return count;
    }

    // 遍历栈：从栈顶往栈底方向走，跟数组版"从 top 下标往下数"是同一个视觉顺序
    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，无法遍历");
            return;
        }
        int depth = 1;
        StackNode temp = top;
        while (temp != null) {
            System.out.println("第" + depth++ + "层(栈顶起) = " + temp.data);
            temp = temp.next;
        }
    }
}

// 定义StackNode，每个 StackNode 对象就是一个栈节点
class StackNode {
    public int data; // 节点存放的数据
    public StackNode next; // 指向下一个节点的指针，默认为null

    public StackNode(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StackNode [data=" + data + "]";
    }
}
