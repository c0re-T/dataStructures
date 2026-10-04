package com.ittxf.linkedlist;

public class Josepfu {
    public static void main(String[] args) {
        // 测试创建环形链表
        CircleSingleLinkedList circleLinkedList = new CircleSingleLinkedList();
        circleLinkedList.addBoy(5);
        circleLinkedList.showBoy();

        // 测试出圈顺序
        circleLinkedList.countBoy(2, 2, 8);
    }
}

// 创建一个环形的单向链表
class CircleSingleLinkedList {
    private Boy first = null; // 表示当前循环链表的第一个节点

    // 添加节点，构建成一个环形链表
    public void addBoy(int nums) {
        if (nums < 1) {
            System.out.println("nums的值不正确");
            return;
        }
        Boy curBoy = null; // 辅助指针，帮助构建环形链表
        // 使用for循环来创建环形链表
        for (int i = 1; i <= nums; i++) {
            Boy boy = new Boy(i);
            if (i == 1) {
                first = boy;
                first.setNext(first); // 构成环形
                curBoy = first; // 让curBoy指向第一个节点，辅助构建环形链表
            } else {
                curBoy.setNext(boy); // 让上一个节点指向当前节点
                boy.setNext(first); // 让当前节点指向第一个节点
                curBoy = boy; // 让curBoy后移指向当前节点
            }
        }
    }

    // 遍历环形链表
    public void showBoy() {
        if (first == null) {
            System.out.println("链表为空");
            return;
        }
        Boy curBoy = first;
        while (true) {
            System.out.println("编号：" + curBoy.getNo());
            if (curBoy.getNext() == first) {
                break; // 遍历结束
            }
            curBoy = curBoy.getNext();
        }
    }

    // 根据用户的输入，计算出出圈的顺序
    public void countBoy(int startNo, int countNum, int nums) {
        if (startNo < 1 || startNo > nums || countNum < 1) {
            System.out.println("参数输入有误，请重新输入");
            return;
        }
        addBoy(nums);
        if (first == null) {
            System.out.println("链表为空");
            return;
        }
        // 创建一个辅助指针(变量)helper，事先应该指向环形链表的最后这个节点
        Boy helper = first;
        while (helper.getNext() != first) {
            helper = helper.getNext();
        }
        //小孩报数前，先让first 和 helper 移动k - 1次，找到报数的起始节点和helper节点
        // 如果就是一开始的位置，则直接跳过
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        // 当小孩报数时，让first 和helper 指针同时的移动 m - 1 次，然后出圈
        // 这里是一个循环操作，直到圈中只有一个节点，即first == helper时退出循环
        while (true) {
            // 让 first 和 helper 指针同时 的移动 countNum - 1 次
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            // 这里first指向的节点就是要出圈的节点
            System.out.println("编号：" + first.getNo() + "出圈");
            // 指向出圈节点的下一个节点，即为新的first节点，让垃圾回收机制回收出圈节点
            first = first.getNext();
            helper.setNext(first);
            if (first == helper) {
                break; // 链表中只有一个节点时退出循环
            }
        }
        System.out.println("最后剩下编号：" + first.getNo());
    }
}

// 创建一个Boy类，表示一个节点
class Boy {
    private int no; // 编号
    private Boy next; // 指向下一个节点

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}

