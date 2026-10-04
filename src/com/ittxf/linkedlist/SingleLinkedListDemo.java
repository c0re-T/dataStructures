package com.ittxf.linkedlist;

import java.util.Stack;

public class SingleLinkedListDemo {
    public static void main(String[] args) {
        // 测试，这里是直接插入尾部不考虑排序
        // 先创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        // 创建链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
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
        singleLinkedList.update(new HeroNode(2, "卢.com", "玉麒麟"));
        singleLinkedList.list();

        System.out.println("==================删除后的链表==================");

        // 删除节点
        singleLinkedList.delete(2);
        singleLinkedList.list();

        System.out.println("==================获取链表有效节点个数==================");

        // 获取链表的有效节点个数
        int length = getLength(singleLinkedList.getHead());
        System.out.println("链表的有效节点个数：" + length);

        System.out.println("==================查找链表中倒数第k个结点==================");

        // 查找链表中倒数第k个结点
        int index = 2;
        HeroNode heroNode = findLastIndexNode(singleLinkedList.getHead(), index);
        System.out.println("链表的倒数第" + index + "个结点：" + heroNode);

        System.out.println("==================单链表反转.txt(影响原链表)==================");

        reverseLinkedList(singleLinkedList.getHead());
        singleLinkedList.list();

        System.out.println("==================逆序打印单链表(不改变原链表)==================");
        reversePrint(singleLinkedList.getHead());

        System.out.println("==================合并两个有序的单链表==================");
        // 注意：合并只是改 next 指针，两条原链表的节点会被“搬走”，所以这里单独建两条新链表演示，
        //      以免弄坏上面 reversePrint / 反转 那几段的结果
        // 链表一（插入顺序故意打乱，靠 addByOrder 保证各自有序）：1 宋江 → 3 吴用 → 5 武松
        SingleLinkedList list1 = new SingleLinkedList();
        list1.addByOrder(new HeroNode(1, "宋江", "及时雨"));
        list1.addByOrder(new HeroNode(5, "武松", "行者"));
        list1.addByOrder(new HeroNode(3, "吴用", "智多星"));
        // 链表二：2 卢俊义 → 4 林冲 → 6 花荣
        SingleLinkedList list2 = new SingleLinkedList();
        list2.addByOrder(new HeroNode(4, "林冲", "豹子头"));
        list2.addByOrder(new HeroNode(2, "卢俊义", "玉麒麟"));
        list2.addByOrder(new HeroNode(6, "花荣", "小李广"));

        System.out.println("链表一：");
        list1.list();
        System.out.println("链表二：");
        list2.list();

        // 和其他方法保持一致，传的是哨兵头节点 getHead()
        SingleLinkedList merged = mergeSortedList(list1.getHead(), list2.getHead());
        System.out.println("合并后的链表（共 " + getLength(merged.getHead()) + " 个节点）：");
        merged.list();
    }

    /**
     * 获取单链表的节点的个数(如果是带头结点的链表，需求不统计头节点)
     * @param head 头节点
     * @return 返回的有效节点个数
     */
    public static int getLength(HeroNode head) {
        int count = 0;
        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                if (count == 0) System.out.println("链表为空");
                break;
            }
            count++;
            temp = temp.next;
        }
        return count;
    }

    /**
     * 查找单链表中的倒数第k个结点（新浪面试题）
     *   1 编写一个，接收head头结点，同时接收一个index
     *   2 index 表示倒数第k个节点
     *   3 先把链表从头到尾遍历，得到链表的总的长度getLength()
     *   4 得到size 后，我们从链表的第一个开始遍历(size-index)个，就可以得到
     *   5 如果找到了，则返回该节点，否则返回null
     * @param head
     * @param index
     * @return
     */
    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        int size = getLength(head);
        // temp 指向链表的第一个节点，不加.next下方循环会重新指向第一个节点，浪费一次循环机会
        HeroNode temp = head.next;
        int pos = size - index;
        if (pos < 0 || temp.next == null) return null;
        for (int i = 0; i < pos; i++) {
            temp = temp.next;
        }
        return temp;
    }

    /**
     * 将单链表反转（腾讯面试）头插法
     */
    public static void reverseLinkedList(HeroNode head) {
        HeroNode temp = head.next;
        HeroNode next = null;
        HeroNode reverseHead = new HeroNode(0, "", "");
        // 如果当前链表为空，或者只有一个节点，无需反转，直接返回
        if (head.next == null || head.next.next == null) return;
        // 遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表reverseHead的最前端
        while (temp != null) {
            next = temp.next; // 1 先暂时保存当前节点的下一个节点（后面第二个节点），因为后面需要使用
            temp.next = reverseHead.next; // 2 将temp的下一个节点指向新的链表的最前端
            reverseHead.next = temp; // 3 让新链表的头结点指向当前节点（核心插入）
            temp = next; // 4 让temp后移，指向下一个节点
        }
        // 将head.next 指向reverseHead.next , 实现单链表的反转
        head.next = reverseHead.next;
    }

    /**
     * 利用栈这个数据结构，将各个节点压入到栈中，
     * 然后利用栈的先进后出的特点，就实现了逆序打印的效果
     */
    public static void reversePrint(HeroNode head) {
        HeroNode temp = head.next;
        if (head.next == null) {
            return; // 空链表不能打印
        }
        // 创建一个栈，将各个节点压入栈中
        Stack<HeroNode> stack = new Stack<>();
        while (temp != null) {
            stack.push(temp);
            temp = temp.next; // temp 向后移动
        }
        // 遍历栈，打印栈中的节点
        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
    }

    /**
     * 合并两个有序的单链表，合并之后的链表依然有序（归并排序里的 merge 过程）
     * 约定：参数传**哨兵头节点**（getHead()），与本类 getLength / reverseLinkedList 保持一致，
     *      比较时从各自第一个有效节点 head.next 开始，哨兵不参与排序
     * 说明：
     *  1 只改 next 指针、不新建数据节点，所以空是 O(1)（只多一个结果哨兵）
     *  2 节点会被“抽”到新链上，原 list1 / list2 的哨兵仍指着已被重接的旧首节点，合并后不要再用
     *  3 编号相等时先取链表一（<=），保证合并结果稳定
     * @return 一条全新的 SingleLinkedList（自带哨兵），可直接 .list() 打印
     */
    public static SingleLinkedList mergeSortedList(HeroNode head1, HeroNode head2) {
        SingleLinkedList merged = new SingleLinkedList();
        // cur 始终指向合并链表当前的最后一个节点，负责把下一个接上去
        HeroNode cur = merged.getHead();
        HeroNode p1 = head1 == null ? null : head1.next;
        HeroNode p2 = head2 == null ? null : head2.next;
        while (p1 != null && p2 != null) {
            if (p1.no <= p2.no) {
                cur.next = p1;
                p1 = p1.next; // p1 后移
            } else {
                cur.next = p2;
                p2 = p2.next; // p2 后移
            }
            cur = cur.next; // 合并链表增长一格
        }
        // 退出时最多只有一条链还没走完，剩下那一整段直接接上，不用逐个搬
        cur.next = (p1 != null) ? p1 : p2;
        return merged;
    }
}

// 定义 SingleLinkedList 管理我们的节点
class SingleLinkedList {
    // 先初始化一个头结点，头结点不要动，不存放具体的数据
    private final HeroNode head = new HeroNode(0, "", "");

    // 返回头节点，防止直接修改头节点
    public HeroNode getHead() {
        return head;
    }

    // 添加节点到单向链表的最后
    // 思路：当不考虑编号顺序时
    // 1 找到当前链表的最后节点
    // 2 将这个节点的 next 指向 新的节点
    public void add(HeroNode heroNode) {
        // 因为 head 节点不能动，因此我们需要一个辅助节点来帮助我们遍历链表
        HeroNode temp = head;
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
        // 将最后这个节点的 next 指向 新的节点
        temp.next = heroNode;
    }

    // 第二种方式在添加英雄时，根据排名将英雄插入到指定位置
    // 如果编号存在，则不添加，否则添加
    public void addByOrder(HeroNode heroNode) {
        // 因为 head 节点不能动，因此我们需要一个辅助节点来帮助我们遍历链表
        HeroNode temp = head;
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
        // 将最后这个节点的 next 指向 新的节点
        heroNode.next = temp.next;
        temp.next = heroNode;
    }

    // 修改节点的信息，根据 no 编号来修改，即 no 编号不能改
    // 说明
    // 1 根据 newHeroNode 的 no 来修改即可
    public void update(HeroNode newHeroNode) {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空，无法修改");
            return;
        }
        // 找到需要修改的节点，根据 no 编号
        HeroNode temp = head.next;
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
    // 1 head 不能动，因此我们需要一个temp辅助节点找到待删除节点的前一个节点
    // 2 说明我们在比较时，是temp.next.no和需要删除的节点的no比较
    public void delete(int no) {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空，无法删除");
            return;
        }
        // 找到需要删除的节点，根据 no 编号
        HeroNode temp = head;
        boolean flag = false; // 标识是否找到该节点
        while (true) {
            if (temp.next == null) {
                break; // 已经遍历完链表
            }
            if (temp.next.no == no) {
                flag = true;
                break; // 找到该节点
            }
            temp = temp.next;
        }
        if (flag) {
            // 删除该节点
            temp.next = temp.next.next;
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
        HeroNode temp = head.next;
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
class HeroNode {
    public int no; // 编号
    public String name; // 名称
    public String nickname; // 昵称
    public HeroNode next; // 指向下一个节点的指针

    // 构造方法
    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    // 重写 toString 方法，方便打印
    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + ", nickname=" + nickname + "]";
    }
}
