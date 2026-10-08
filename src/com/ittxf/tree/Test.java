package com.ittxf.tree;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        // 以ArrayList 为例，看看是怎样进行数组扩容
        /*
        * ArrayList 源码分析
        * ArrayList 底层是一个数组，数组是定长的，所以当数组满了之后，需要进行扩容
        * 扩容的过程：
        * 1. 创建一个新数组，新数组的长度是原数组长度的1.5倍
        * 2. 将原数组中的元素复制到新数组中
        * 3. 将新数组的引用指向原数组的引用
        * */
        ArrayList<Integer> list = new ArrayList<>();
    }
}
