package com.ittxf.stack;

public class Calculator {
    public static void main(String[] args) {
        // 完成表达式的运算，支持多位数与括号；也可以运行时用参数传表达式：java Calculator "(3+2)*4-1"
        String expression = args.length > 0 ? args[0] : "(3+2)*4-1";
        // 创建两个栈，一个数栈，一个符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operatorStack = new ArrayStack2(10);
        // 定义需要的变量
        int index = 0; // 用于扫描
        int num1 = 0; // 第一个数字
        int num2 = 0; // 第二个数字
        int operator = 0; // 运算符
        int res = 0; // 结果
        char ch = ' '; // 每次扫描的字符
        String keepNum = ""; // 用于临时保存多位数
        int bracket = 0; // 括号深度，用于校验括号是否配对
        // 开始while循环的扫描expression
        while (index < expression.length()) {
            // 获取expression的每一个字符
            ch = expression.charAt(index);
            // 判断ch是什么，然后做相应的处理
            if (ch == '(') {
                // 左括号：直接入符号栈，不做优先级比较
                // 因为 priority 的 else 分支会给 '(' 返回 -1（优先级最低），
                // 括号内部的运算符就不会跟括号外部的比优先级、提前被结算
                // 多个左括号会叠成一堆，靠 ')' 那边“结算到栈顶是 '(' 为止”自然就能处理多层嵌套
                bracket++;
                operatorStack.push(ch);
            } else if (ch == ')') {
                // 右括号：符号栈里一路结算，直到栈顶是跟它配对的那个左括号
                // 这里必须用 while，不能用 if：括号里面可能已经压了好几个运算符
                if (--bracket < 0) {
                    System.out.println("括号不匹配：第 " + index + " 位的 ')' 找不到对应的左括号");
                    return;
                }
                while (operatorStack.peek() != '(') {
                    num1 = numStack.pop();
                    num2 = numStack.pop();
                    operator = operatorStack.pop();
                    res = numStack.cal(num1, num2, operator);
                    // 把结果入数栈
                    numStack.push(res);
                }
                operatorStack.pop(); // 弹出左括号并丢弃：它只是分界线，本身不是运算符
            } else if (operatorStack.isOperator(ch)) { // 如果是运算符
                // 判断符号栈是否为空
                if (!operatorStack.isEmpty()) {
                    // 判断优先级
                    if (operatorStack.priority(ch) <= operatorStack.priority(operatorStack.peek())) {
                        // 弹出两个数和一个运算符进行计算
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        operator = operatorStack.pop();
                        res = numStack.cal(num1, num2, operator);
                        // 把结果入数栈
                        numStack.push(res);
                        operatorStack.push(ch);
                    } else {
                        operatorStack.push(ch);
                    }
                } else {
                    // 直接入栈
                    operatorStack.push(ch);
                }
            } else if (ch >= '0' && ch <= '9') {
                // 是数字：先只拿一位，再往后看，只有后面还是数字时才继续拼
                // ★ 这里不能再写成 !isOperator(下一位)：括号不属于运算符，那样会把 ')' 拼进数字里，
                //   得到 keepNum = "2)" 然后 Integer.parseInt 直接抛 NumberFormatException
                keepNum = "" + ch;
                while (index + 1 < expression.length()
                        && expression.charAt(index + 1) >= '0'
                        && expression.charAt(index + 1) <= '9') {
                    keepNum += expression.charAt(++index);
                }
                numStack.push(Integer.parseInt(keepNum));
            } else {
                // 空格一类的非法字符：以前会掉进 else 被当成数字算 ch-'0'（' '(32) - '0'(48) = -16）压进数栈，现在直接报错退出
                System.out.println("表达式含非法字符：'" + ch + "'（位置 " + index + "）");
                return;
            }
            index++;
        }

        // 扫描完还有括号没闭合，说明缺右括号；这种情况最后的结算循环会把 '(' 当运算符传给 cal，
        // switch 一个 case 都匹配不上 → 默默算成 0，得到一个“看起来正常”的错答案，所以必须在这拦住
        if (bracket != 0) {
            System.out.println("括号不匹配：缺少右括号");
            return;
        }

        while (!operatorStack.isEmpty()) {
            // 弹出两个数和一个运算符进行计算
            num1 = numStack.pop();
            num2 = numStack.pop();
            operator = operatorStack.pop();
            res = numStack.cal(num1, num2, operator);
            // 把结果入数栈
            numStack.push(res);
        }

        System.out.println("表达式 " + expression + " 的结果是 " + numStack.peek());
    }
}

// 先创建一个栈
// 补充方法
class ArrayStack2 {
    private int maxSize; // 栈的大小
    private int[] stack; // 栈数组
    private int top = -1; // 栈顶指针，初始值为-1

    // 构造器
    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new int[this.maxSize];
    }

    // 栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    // 栈空
    public boolean isEmpty() {
        return top == -1;
    }

    // 入栈
    public void push(int value) {
        if (isFull()) {
            System.out.println("栈满，无法入栈");
            return;
        }
        stack[++top] = value;
    }

    // 出栈
    public int pop() {
        if (isEmpty()) {
            // 不能返回值万一刚好是存入的值分不清
            throw new RuntimeException("栈空，无法出栈");
        }
        return stack[top--];
    }

    // 遍历栈
    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，无法遍历");
            return;
        }
        // 从栈顶开始遍历
        for (int i = top; i >= 0 ; i--) {
            System.out.println("stack[" + i + "]=" + stack[i]);
        }
    }

    // 返回运算符的优先级，优先级用数字表示，数字越大，优先级越高
    public int priority(int operator) {
        if (operator == '*' || operator == '/') {
            return 1;
        } else if (operator == '+' || operator == '-') {
            return 0;
        } else {
            return -1; // 假设输入的运算符都是四则运算符
        }
    }

    // 判断是不是运算符
    public boolean isOperator(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    // 计算方法
    public int cal(int num1, int num2, int operator) {
        int res = 0; // 用于存放计算结果
        switch (operator) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
        }
        return res;
    }

    // 查看当前栈顶的值，但不弹出
    // 必须判空：否则 top == -1 时会读 stack[-1]，报 ArrayIndexOutOfBoundsException: Index -1 out of bounds，
    // 而括号不配对的场景（比如 "3+2)*4"）恰好就会把符号栈弹空
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("栈空，无法查看栈顶");
        }
        return stack[top];
    }

}