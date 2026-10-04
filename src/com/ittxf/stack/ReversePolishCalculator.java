package com.ittxf.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 逆波兰计算器（后缀表达式计算器），写法参考 PolandNotation，只用最普通的栈
 * 1 支持 + - * / ( )
 * 2 支持多位数、小数
 * 3 兼容处理：过滤任何空白字符（空格、制表符、换行、回车、换页）
 *
 * 分三步，中间只用到栈：
 * 第一步 infixToList       中缀字符串 => 中缀 ArrayList，例如 [1, +, (, 2, ), *]
 * 第二步 parseSuffixExpres 中缀 ArrayList => 后缀 ArrayList，用两个栈 s1（符号栈）、s2（结果栈）
 * 第三步 calculate         后缀 ArrayList => 结果，只用一个栈
 */
public class ReversePolishCalculator {

    public static void main(String[] args) {
        // ========== 一、直接给一个逆波兰（后缀）表达式，求值 ==========
        // 4*5-8+60+8/2 => 4 5 * 8 - 60 + 8 2 / +
        // 为了方便，逆波兰表达式用空格隔开
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        System.out.println("分割结果：" + getListString(suffixExpression));
        System.out.println("计算结果：" + show(calculate(getListString(suffixExpression))));

        // ========== 二、给一个中缀表达式，先转后缀，再求值 ==========
        // 1 "1+((2+3)*4)-5" => ArrayList [1, +, (, (, 2, +, 3, ), *, 4, ), -, 5]
        // 2 ArrayList [1,+,(,(,2,+,3,),*,4,),-,5] => ArrayList [1, 2, 3, +, 4, *, +, 5, -]
        String infixExpression = args.length > 0 ? args[0] : "1+((2+3)*4)-5";
        List<String> infixList = infixToList(infixExpression);
        List<String> suffixList = parseSuffixExpres(infixList);
        System.out.println("中缀表达式：" + infixExpression);
        System.out.println("中缀表达式分割结果：" + infixList);
        System.out.println("中缀表达式转后缀结果：" + suffixList);
        System.out.println("中缀表达式计算结果：" + show(calculate(suffixList)));

        // ========== 三、把三条需求一起测一遍 ==========
        System.out.println("==================用例自测==================");
        String[] testList = {
                "3+2*4-1",                        // 优先级
                "(3+2)*4-1",                     // 括号
                "2*(3+4*(5-3))",                 // 多层括号嵌套
                "10-2*3",                        // 多位数
                "3.5*2+0.5",                     // 小数
                "10 / (5-3) + 1.5*2",            // 空格 + 小数 + 括号
                "12 + 34 * 2",                   // 多位数 + 空格
                "\t3+2*6-2\n",                   // 制表符 + 换行
                "3*(2+4",                        // 少右括号
                "3+2)*4",                        // 多右括号
                "5/(3-3)",                       // 除数为 0
                "3+2*",                          // 缺操作数
                "1.2.3+1"                        // 非法数字
        };
        for (String exp : testList) {
            // 打印前先把空白去掉，否则制表符和换行会把输出搞乱
            String name = exp.replaceAll("\\s+", "");
            try {
                List<String> list = parseSuffixExpres(infixToList(exp));
                System.out.println(name + " = " + show(calculate(list)) + "    后缀：" + list);
            } catch (RuntimeException e) {
                System.out.println(name + " 算不了：" + e.getMessage());
            }
        }
    }

    /**
     * 把逆波兰（后缀）表达式字符串，按空白切成 ArrayList
     * 用 \\s+ 而不是 " "，这样多个连续空格、制表符都当成一个分隔符
     */
    public static List<String> getListString(String suffixExpression) {
        String[] split = suffixExpression.trim().split("\\s+");
        ArrayList<String> list = new ArrayList<>();
        for (String el : split) {
            list.add(el);
        }
        return list;
    }

    /**
     * 中缀表达式字符串 => 中缀对应的 ArrayList
     * 例："1+((2+3)*4)-5" => [1, +, (, (, 2, +, 3, ), *, 4, ), -, 5]
     */
    public static List<String> infixToList(String infixExpression) {
        List<String> list = new ArrayList<>();
        int i = 0;   // 指针，用于遍历中缀表达式字符串
        String str;  // 用于多位数、小数的拼接
        char c;      // 每次扫描到的字符
        do {
            c = infixExpression.charAt(i);
            if (isBlank(c)) {
                // 需求 3：空白字符直接跳过
                i++;
            } else if (isNumChar(c)) {
                // 需求 2：遇到数字就往后拼，直到不是数字为止，这样 12、3.5 都能拼成一个完整的数
                str = "";
                while (i < infixExpression.length() && isNumChar(infixExpression.charAt(i))) {
                    str += infixExpression.charAt(i);
                    i++;
                }
                if (!str.matches("\\d+(\\.\\d+)?")) {
                    // 像 1.2.3 或者单独一个 . 会拼出这种不像数字的东西，早点报错，别留到后面
                    throw new RuntimeException("不是一个合法数字：" + str);
                }
                list.add(str);
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                // 运算符和括号，一个字符就是一个 token
                list.add(String.valueOf(c));
                i++;
            } else {
                throw new RuntimeException("表达式含非法字符：" + c);
            }
        } while (i < infixExpression.length());
        return list;
    }

    /**
     * 中缀对应的 List => 后缀对应的 List，这里用两个普通栈
     * s1 是符号栈；s2 存中间结果，它只进不出（最后一次性倒出来），从栈底到栈顶正好就是后缀表达式
     */
    public static List<String> parseSuffixExpres(List<String> list) {
        Stack<String> s1 = new Stack<>(); // 符号栈
        Stack<String> s2 = new Stack<>(); // 储存中间结果的栈 s2
        for (String item : list) {
            if (item.matches("\\d+(\\.\\d+)?")) {
                // 是数字（支持小数），直接进 s2
                s2.push(item);
            } else if (item.equals("(")) {
                // 左括号直接进符号栈，不参与优先级比较
                s1.push(item);
            } else if (item.equals(")")) {
                // 右括号：把符号栈里的运算符依次弹出进 s2，直到遇到配对的那个左括号
                // 这里必须用 while，因为括号里面可能已经压了好几个运算符
                while (!s1.isEmpty() && !s1.peek().equals("(")) {
                    s2.push(s1.pop());
                }
                if (s1.isEmpty()) {
                    throw new RuntimeException("括号不匹配：多了一个右括号 )");
                }
                s1.pop(); // 弹出左括号，这一对括号到此丢弃，不进后缀
            } else {
                // 是运算符：只要栈顶的优先级不低于它，就先弹出来，然后再把自己压进去
                // 判断里带上 !s1.peek().equals("(")，是不让括号内部的运算符被外面提前结算
                while (!s1.isEmpty()
                        && !s1.peek().equals("(")
                        && priority(s1.peek()) >= priority(item)) {
                    s2.push(s1.pop());
                }
                s1.push(item);
            }
        }
        // 表达式扫描完了，符号栈里剩下的运算符全部倒进 s2
        while (!s1.isEmpty()) {
            String op = s1.pop();
            if (op.equals("(")) {
                throw new RuntimeException("括号不匹配：少了一个右括号 )");
            }
            s2.push(op);
        }
        return new ArrayList<>(s2); // 转成 ArrayList 返回，栈底到栈顶的顺序就是后缀表达式
    }

    /**
     * 对后缀表达式求值，只需要一个栈
     * 1) 从左到右扫描，遇到数字就入栈
     * 2) 遇到运算符，弹出两个数计算（先弹出来的是右操作数），结果再入栈
     * 3) 扫描结束，栈里剩下的那一个数就是结果
     */
    public static double calculate(List<String> list) {
        Stack<Double> stack = new Stack<>(); // 只需要一个栈即可
        double res = 0;
        for (String item : list) {
            if (item.matches("\\d+(\\.\\d+)?")) { // 正则匹配数字，支持多位数和小数
                stack.push(Double.parseDouble(item));
            } else {
                if (stack.size() < 2) {
                    throw new RuntimeException("表达式有误：运算符 " + item + " 缺少操作数（暂不支持负号开头的表达式）");
                }
                double b = stack.pop(); // 先弹出的，是运算符右边的数
                double a = stack.pop(); // 后弹出的，是运算符左边的数
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
                        if (b == 0) {
                            throw new RuntimeException("除数为 0");
                        }
                        res = a / b;
                        break;
                    default:
                        throw new RuntimeException("暂不支持的运算符：" + item);
                }
                stack.push(res);
            }
        }
        if (stack.size() != 1) {
            throw new RuntimeException("表达式有误：算完数栈里还剩 " + stack.size() + " 个数");
        }
        return stack.pop();
    }

    // 判断是不是"数字的一部分"：0-9 或者小数点
    public static boolean isNumChar(char c) {
        return (c >= '0' && c <= '9') || c == '.';
    }

    // 判断是不是空白字符：空格、制表符、换行、回车、换页
    public static boolean isBlank(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f';
    }

    // 判断运算符的优先级：乘除高于加减，其他（比如左括号）最低
    public static int priority(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        } else if (operator.equals("*") || operator.equals("/")) {
            return 2;
        } else {
            return 0;
        }
    }

    // 打印用：结果是整数就不带小数点，比如 19.0 打成 19
    public static String show(double d) {
        if (d == (long) d) {
            return String.valueOf((long) d);
        }
        return String.valueOf(d);
    }
}
