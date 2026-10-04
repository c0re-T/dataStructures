package com.ittxf.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolandNotation {
    public static void main(String[] args) {
        // 先定义一个逆波兰表达式
        // (3+4)×5-6 => 3 4 + 5 × 6 -
        // 4 * 5-8+ 60+ 8/ 2 => 4 5 * 8 - 60 + 8 2 / +
        // 为了方便，逆波兰表达式，用空格隔开
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        // 思路
        // 1 先将"3 4 + 5 × 6 -" => 放到ArrayList中
        // 2 将ArrayList传递给一个方法，配合栈，完成计算
        System.out.println("分割结果：" + getListString(suffixExpression));
        System.out.println("计算结果：" + calculate(getListString(suffixExpression)));


        // 完成一个中缀表达式转后缀表达式的转换
        // 1 1+((2+3)×4)-5 => 转成 1 2 3 + 4 × + 5 -
        // 2 因为直接对str进行操作，不方便，因此先将str放入ArrayList中
        //   即 "1+((2+3)*4)-5" => ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]
        // 3 将得到的中缀表达式对应的List => 后缀表达式对应的List
        //   即 ArrayList [1,+,(,(,2,+,3,),*,4,),-,5] =》 ArrayList [1 2 3 + 4 * + 5 -]
        String infixExpression = "1+((2+3)*4)-5";
        List<String> infixList = infixToSuffix(infixExpression);
        List<String> stringList = infixToSuffix(infixList);
        int calculate = calculate(stringList);
        System.out.println("中缀表达式转后缀表达式分割结果：" + infixList);
        System.out.println("中缀表达式转后缀表达式转换结果：" + stringList);
        System.out.println("中缀表达式转后缀表达式计算结果：" + calculate);


    }

    // ArrayList [1,+,(,(,2,+,3,),*,4,),-,5] => ArrayList [1 2 3 + 4 * + 5 -]
    // 将得到的中缀表达式对应的List => 后缀表达式对应的List
    public static List<String> infixToSuffix(List<String> list) {
        // 定义两个栈
        Stack<String> s1 = new Stack<>(); // 符号栈
        // 因为s2这个栈，在整个转换过程中，没有pop操作，而且后面我们还需要逆序输出
        Stack<String> s2 = new Stack<>(); // 储存中间结果的栈s2
        // 遍历list
        for (String item : list) {
            // 如果是一个数，加入s2
            if (item.matches("\\d+")) {
                s2.push(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                // 如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
                while (!s1.peek().equals("(")) {
                    s2.push(s1.pop());
                }
                s1.pop(); // 弹出左括号 "("
            } else {
                // 当item的优先级小于等于s1栈顶运算符，将s1栈顶的运算符弹出并加入到s2中，再次转到(4.1)与s1中新的栈顶运算符相比较
                // 4.1:如果s1为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈；
                // 问题：我们缺少一个比较优先级高低的方法
                while (!s1.isEmpty()
                        && OperatorUtils.priority(s1.peek())
                        >= OperatorUtils.priority(item)) {
                    s2.push(s1.pop());
                }
                // 还需要将item压入栈
                s1.push(item);
            }
        }
        // 将s1中剩余的运算符加入s2
        while (!s1.isEmpty()) {
            s2.push(s1.pop());
        }
        return new ArrayList<>(s2); // 转换为ArrayList
    }

    // 中缀表达式转后缀表达式分割成ArrayList
    public static List<String> infixToSuffix(String infixExpression) {
        // 定义一个List,存放中缀表达式对应的内容
        List<String> list = new ArrayList<>();
        int i = 0; // 这是一个指针，用于遍历中缀表达式字符串
        String str; // 多位数的拼接
        char c; // 遍历中缀表达式字符串
        do {
            c = infixExpression.charAt(i);
            if (c < 48 || c > 57) { // 如果c是一个非数字，即运算符，则将c入栈
                list.add(String.valueOf(c));
                i++;
            } else { // 如果c是一个数字，需要考虑多位数
                str = "";
                while (i < infixExpression.length()
                        && infixExpression.charAt(i) >= 48
                        && infixExpression.charAt(i) <= 57) {
                    str += infixExpression.charAt(i); // str += c;
                    i++;
                } // while
                list.add(str);
            }
        } while (i < infixExpression.length());
        return list;
    }


    /**
     * 分割逆波兰表达式为ArrayList
     * @param suffixExpression
     * @return
     */
    public static List<String> getListString(String suffixExpression) {
        // 将suffixExpression放到ArrayList中
        String[] split = suffixExpression.split(" ");
        ArrayList<String> list = new ArrayList<>();
        for (String el : split) {
            list.add(el);
        }
        return list;
    }

    // 完成对逆波兰表达式的运算

    /**
     * 1)从左至右扫描，将3和4压入堆栈；
     * 2)遇到+运算符，因此弹出4和3(4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
     * 3)将5入栈；
     * 4)接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
     * 5)将6入栈；
     * 6)最后是-运算符，计算出35-6的值，即29，由此得出最终结果
     * @param list
     * @return
     */
    public static int calculate(List<String> list) {
        // 创建一个栈，只需要一个栈即可
        Stack<String> stack = new Stack<>();
        int res = 0;
        // 遍历
        for (String item : list) {
            // 这里使用正则表达式判断
            if (item.matches("\\d+")) { // 匹配多位数
                // 入栈
                stack.push(item);
            } else {
                // 拿前一个数和后一个数做运算
                int b = Integer.parseInt(stack.pop());
                int a = Integer.parseInt(stack.pop());
                switch (item) {
                    case "+":
                        res = a + b;
                        break;
                    case "-":
                        res = a - b;
                        break;
                    case "*":
                        res = a * b;
                        break;
                    case "/":
                        res = a / b;
                        break;
                }
                stack.push(String.valueOf(res));
            }
        }
        return Integer.parseInt(stack.pop());
    }
}

// 工具类，用于判断运算符的优先级
class OperatorUtils{
    public static int priority(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        } else if (operator.equals("*") || operator.equals("/")) {
            return 2;
        } else {
            return 0;
        }
    }
}