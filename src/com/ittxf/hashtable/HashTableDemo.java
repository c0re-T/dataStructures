package com.ittxf.hashtable;

import java.util.Scanner;

public class HashTableDemo {
    public static void main(String[] args) {
        // 创建哈希表
        HashTable hashTable = new HashTable(5);

        // 写一个简单的菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add: 添加雇员");
            System.out.println("list: 显示雇员");
            System.out.println("find: 查找雇员");
            System.out.println("exit: 退出系统");
            System.out.println("请输入你的选择：");
            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("请输入雇员的id：");
                    int id = scanner.nextInt();
                    System.out.println("请输入雇员的name：");
                    String name = scanner.next();
                    Emp emp = new Emp(id, name);
                    hashTable.add(emp);
                    break;
                case "list":
                    hashTable.list();
                    break;
                case "find":
                    System.out.println("请输入要查找的雇员的id：");
                    int findId = scanner.nextInt();
                    hashTable.findById(findId);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0); // 退出程序
            }
        }
    }
}

// 哈希表管理多个链表
class HashTable {
    private EmpLinkedList[] empLinkedListArray; // 哈希表管理多个链表
    private int size; // 表示哈希表的大小

    // 构造器
    public HashTable(int size) {
        this.size = size;
        this.empLinkedListArray = new EmpLinkedList[size];
        for (int i = 0; i < size; i++) { // 不初始化会报空指针异常
            this.empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    // 添加员工
    public void add(Emp emp) {
        // 根据员工的id，得到该员工应当添加到哪条链表中
        int empLinkedListNo = hashFun(emp.id);
        // 将emp添加到对应的链表中
        this.empLinkedListArray[empLinkedListNo].add(emp);
    }

    // 遍历哈希表
    public void list() {
        for (int i = 0; i < size; i++) {
            this.empLinkedListArray[i].list(i);
        }
    }

    // 根据id查找雇员
    public Emp findById(int id) {
        // 根据员工的id，得到该员工应当在哪条链表中
        int empLinkedListNo = hashFun(id);
        // 在对应的链表中查找
        Emp emp = this.empLinkedListArray[empLinkedListNo].findById(id);
        if (emp != null) {
            System.out.println("在第" + (empLinkedListNo + 1) + "条链表中找到雇员：" + emp.id + "，" + emp.name);
        } else {
            System.out.println("id=" + id + "的雇员不存在该哈希表中");
        }
        return emp;
    }

    // 编写散列函数
    public int hashFun(int id) {
        return id % size;
    }
}

// 员工类
class Emp {
    int id;
    String name;
    Emp next; // 指向下一个Emp对象，默认为null

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

// 链表类
class EmpLinkedList {
    private Emp head; // 头指针，指向链表的第一个Emp对象，默认为null

    // 添加员工
    // 假定，当添加雇员时，id是自增长，
    // 即id的分配总是从小到大因此我们将该雇员直接加入到本链表的最后即可
    public void add(Emp emp) {
        // 如果是添加第一个雇员
        Emp temp = head;
        if (temp == null) {
            head = emp;
            return;
        }
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = emp;
    }

    // 遍历链表
    public void list(int no) {
        Emp temp = head;
        if (temp == null) {
            System.out.println("第" + (no + 1) + "条链表为空");
            return;
        }
        System.out.print("第" + (no + 1) + "条链表中的数据：");
        while (temp != null) {
            System.out.println("id=" + temp.id + ", name=" + temp.name);
            temp = temp.next;
        }
    }

    // 根据id查找雇员
    // 如果查找到，就返回Emp，如果没有找到，就返回null
    public Emp findById(int id) {
        Emp temp = head;
        if (temp == null) {
            return null;
        }
        while (temp != null) {
            if (temp.id == id) {
                break;
            }
            temp = temp.next;
        }
        return temp;
    }
}
