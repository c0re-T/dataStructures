package com.ittxf.linkedlist;

import java.util.Stack;

public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        // 测试，这里是直接插入尾部不考虑排序
        // 先创建节点
        DHeroNode hero1 = new DHeroNode(1, "宋江", "及时雨");
        DHeroNode hero2 = new DHeroNode(2, "卢俊义", "玉麒麟");
        DHeroNode hero3 = new DHeroNode(3, "吴用", "智多星");
        DHeroNode hero4 = new DHeroNode(4, "林冲", "豹子头");
        // 创建链表
        DoubleLinkedList singleLinkedList = new DoubleLinkedList();
        // 尾部加入
        /*singleLinkedList.add(hero1);
        singleLinkedList.add(hero4);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);*/
        // 有序加入
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.list();

        System.out.println("==================修改后的链表==================");

        // 修改节点
        singleLinkedList.update(new DHeroNode(2, "卢.com", "玉麒麟"));
        singleLinkedList.list();

        System.out.println("==================删除后的链表==================");

        // 删除节点
        singleLinkedList.delete(3);
        singleLinkedList.list();
    }
}

// 定义 DoubleLinkedList 管理我们的节点
class DoubleLinkedList {
    // 先初始化一个头结点，头结点不要动，不存放具体的数据
    private final DHeroNode head = new DHeroNode(0, "", "");

    // 返回头节点，防止直接修改头节点
    public DHeroNode getHead() {
        return head;
    }

    // 添加节点到双向链表的最后
    // 思路：当不考虑编号顺序时
    // 1 找到当前链表的最后节点
    // 2 将这个节点的 next 指向 新的节点
    public void add(DHeroNode heroNode) {
        // 因为 head 节点不能动，因此我们需要一个辅助节点来帮助我们遍历链表
        DHeroNode temp = head;
        // 遍历链表，找到最后
        while (true) {
            // 找到链表的最后
            if (temp.next == null) {
                break;
            }
            // 如果没有找到最后，就将 temp 向后移动
            temp = temp.next;
        }
        // 当退出 while 循环时，temp 就指向了链表的最后
        // 将最后这个节点的 next 指向 新的节点，pre 指向前一个节点，形成一个双向链表
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    // 第二种方式在添加英雄时，根据排名将英雄插入到指定位置
    // 如果编号存在，则不添加，否则添加
    public void addByOrder(DHeroNode heroNode) {
        // 因为 head 节点不能动，因此我们需要一个辅助节点来帮助我们遍历链表
        DHeroNode temp = head;
        // 遍历链表，找到最后
        while (true) {
            // 找到链表的最后
            if (temp.next == null) {
                break;
            }
            // 只能添加到指定位置的前一个节点，否则插入不了
            if (temp.next.no > heroNode.no) { // 位置找到，就在temp的后面插入
                break;
            } else if (temp.next.no == heroNode.no) { // 如果找到编号的比较，就说明编号存在，不能添加
                System.out.println("编号" + heroNode.no + "存在，不能添加");
                return;
            }
            // 如果没有找到最后，就将 temp 向后移动
            temp = temp.next;
        }
        // 当退出 while 循环时，temp 就指向了链表的应该插入的位置
        // 将新节点的 next 指向 temp 的 next
        // 将最后这个节点的 next 指向 新的节点，pre 指向前一个节点，形成一个双向链表
        heroNode.next = temp.next;
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    // 修改节点的信息，根据 no 编号来修改，即 no 编号不能改
    // 修改一个节点的内容，可以看到双向链表的节点内容修改和单向链表一样
    // 1 根据 newHeroNode 的 no 来修改即可
    public void update(DHeroNode newHeroNode) {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空，无法修改");
            return;
        }
        // 找到需要修改的节点，根据 no 编号
        DHeroNode temp = head.next;
        boolean flag = false; // 标识是否找到该节点
        while (true) {
            if (temp == null) {
                break; // 已经遍历完链表
            }
            if (temp.no == newHeroNode.no) {
                flag = true;
                break; // 找到该节点
            }
            temp = temp.next;
        }
        if (flag) {
            // 根据 no 编号修改
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        } else {
            System.out.println("没有找到编号为" + newHeroNode.no + "该节点，无法修改");
        }
    }

    // 删除节点：被删除的节点被Java垃圾回收
    // 思路
    // 1 对于双向链表，我们可以直接找到要删除的这个节点
    // 2 找到后，自我删除即可
    public void delete(int no) {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空，无法删除");
            return;
        }
        // 找到需要删除的节点，根据 no 编号
        DHeroNode temp = head.next; // 用来分别是否停在待删除节点的前一个节点
        boolean flag = false; // 标识是否找到该节点
        while (true) {
            if (temp == null) {
                break; // 已经遍历完链表
            }
            if (temp.no == no) {
                flag = true;
                break; // 找到该节点
            }
            temp = temp.next;
        }
        if (flag) {
            // 删除该节点
            temp.pre.next = temp.next;
            // 如果是最后一个节点，就不需要执行下面这句话，否则出现空指针
            if (temp.next != null) temp.next.pre = temp.pre;
        } else {
            System.out.println("没有找到编号为" + no + "该节点，无法删除");
        }
    }

    // 显示链表[遍历]
    public void list() {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 因为 head 节点不能动，因此我们需要一个辅助节点来帮助我们遍历链表
        DHeroNode temp = head.next;
        while (true) {
            // 判断是否到链表的最后
            if (temp == null) {
                break;
            }
            // 输出节点信息
            System.out.println(temp);
            // 将 temp 向后移动
            temp = temp.next;
        }
    }

}

// 定义HeroNode，每个 HeroNode 对象就是一个节点
class DHeroNode {
    public int no; // 编号
    public String name; // 名称
    public String nickname; // 昵称
    public DHeroNode next; // 指向下一个节点的指针，默认为null
    public DHeroNode pre; // 指向前一个节点的指针，默认为null

    // 构造方法
    public DHeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    // 重写 toString 方法，方便打印
    @Override
    public String toString() {
        return "DHeroNode [no=" + no + ", name=" + name + ", nickname=" + nickname + "]";
    }
}
