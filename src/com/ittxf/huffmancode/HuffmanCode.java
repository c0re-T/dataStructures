package com.ittxf.huffmancode;

import java.io.*;
import java.util.*;

public class HuffmanCode {
    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        byte[] bytes = content.getBytes(); // 拆解成一串二进制（字节），并存放到 byte[] 数组里。
        /*System.out.println(bytes.length); // 输出字节数组长度，即内容的字节数 40
        System.out.println("字节数组内容：" + Arrays.toString(bytes)); // 输出字节数组内容

        // 拆解版压缩过程
        List<Node> nodes = getNodes(bytes);
        System.out.println("nodes = " + nodes); // 输出节点信息

        Node huffmanTree = createHuffmanTree(nodes);
        System.out.println("huffmanTree = " + huffmanTree); // 输出赫夫曼树
        preOrder(huffmanTree); // 输出赫夫曼树的前序遍历

        // 获取赫夫曼编码表
        getCodes(huffmanTree, "", sb);
        System.out.println("生成的huffmanCodes = " + huffmanCodes); // 输出赫夫曼编码表
        // 调用方便形式
        Map<Byte, String> huffmanCodes = getCodes(huffmanTree);
        System.out.println("重载生成的huffmanCodes = " + huffmanCodes); // 输出赫夫曼编码表

        // 压缩赫夫曼编码表
        byte[] huffmanBytes = zip(bytes, huffmanCodes);
        System.out.println("压缩后的huffmanBytes = " + Arrays.toString(huffmanBytes)); // 输出压缩后的huffmanBytes 17
        */

        // 一键调用
        byte[] huffmanBytes = huffmanZip(bytes);
        System.out.println("压缩后的huffmanBytes = " + Arrays.toString(huffmanBytes)
                            + " 长度：" + huffmanBytes.length); // 输出压缩后的huffmanBytes 17

        // 测试把huffmanBytes转成char[]数组
        // System.out.println(getBinaryString(false, (byte) -1));
        // 测试压缩方法
        byte[] decode = decode(huffmanBytes, huffmanCodes);
        System.out.println("解码后的字符数组 = " + Arrays.toString(decode)); // 输出解码后的字符数组
        System.out.println("解码后的字符串 = " + new String(decode)); // 输出解码后的字符串

        // 测试压缩文件和解压文件
        // String srcFile = "E:\\Code\\fullstack\\dataStructures\\dataStructures\\filePath\\pg.png";
        // String dstFile = "E:\\Code\\fullstack\\dataStructures\\dataStructures\\filePath\\pg.png.huffman.zip";
        // zipFile(srcFile, dstFile);

        String zipFile = "E:\\Code\\fullstack\\dataStructures\\dataStructures\\filePath\\pg.png.huffman.zip";
        String dstFile = "E:\\Code\\fullstack\\dataStructures\\dataStructures\\filePath\\pg.unzip.png";
        unZipFile(zipFile, dstFile);
    }

    /**
     * 转换字节数组为Node集合
     * @param bytes 原始字节数组
     * @return Node集合
     */
    private static List<Node> getNodes(byte[] bytes) {
        // 1 创建一个ArrayList
        List<Node> nodes = new ArrayList<>();

        // 遍历 bytes 数组，统计每一个byte出现的次数 -> map
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) { // map里面没有该byte
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }

        // 把每一个键值对转成一个Node对象，并加入到nodes集合
        // 遍历map，用entrySet
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }

        return nodes;
    }

    /**
     * 根据nodes创建Huffman树
     * @param nodes 节点集合
     * @return Huffman树的根节点
     */
    public static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            // 从小到大排序，保证最小的在前面
            Collections.sort(nodes);
            // 取出两棵最小的树，左小右大
            Node left = nodes.get(0);
            Node right = nodes.get(1);
            // 组合成一棵新的树，只有权值没有数据
            Node parent = new Node(null,  left.weight + right.weight);
            // 将新的树重新加入nodes
            parent.left = left;
            parent.right = right;
            // 从nodes中移除left和right
            nodes.remove(left);
            nodes.remove(right);
            // 将新的树重新加入nodes
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    // 前序遍历
    public static void preOrder(Node root) {
        if (root != null) root.preOrder();
        else System.out.println("空树");
    }

    /**
     * 为了方便调用getCodes方法，重载方法
     */
    private static Map<Byte, String> getCodes(Node root) {
        if (root == null) {
            return null;
        }
        getCodes(root, "", sb);
        return huffmanCodes;
    }

    /**
     * 生成赫夫曼树对应的赫夫曼编码表
     * 思路：
     * 1 将赫夫曼编码表存放在Map<Byte,String> 中
     *  32->01 97->100100->11000等等[形式]
     * 2 在生成赫夫曼编码表时，需要一个StringBuilder来拼接路径，存储某个叶子节点的路径
     * 功能：将传入的node结点的所有叶子结点的赫夫曼编码得到，并放入到huffmanCodes集合
     * @param node 传入的节点
     * @param code 路径：左子节点路径为0，右子节点路径为1
     * @param sb   用于拼接路径
     */
    static Map<Byte, String> huffmanCodes = new HashMap<>(); // 赫夫曼编码表
    static StringBuilder sb = new StringBuilder();
    public static void getCodes(Node node, String code, StringBuilder sb) {
        // 创建一个StringBuilder，用于拼接路径
        StringBuilder sb2 = new StringBuilder(sb);
        // 将code加入到sb2
        sb2.append(code);
        if (node != null) { // 如果root == null不处理
            // 判断当前node 是叶子结点还是非叶子结点
            if (node.data == null) { // 非叶子结点
                // 向左 递归处理
                getCodes(node.left, "0", sb2);
                // 向右 递归处理
                getCodes(node.right, "1", sb2);
            } else { // 叶子结点
                // 就表示找到某个叶子结点的最后
                // 将sb2加入到huffmanCodes集合
                huffmanCodes.put(node.data, sb2.toString());
            }
        }
    }

    /**
     * 编写一个方法，将字符串对应的byte[]数组，通过生成的赫夫曼编码表，返回一个赫夫曼编码压缩后的byte[]数组
     * @param bytes 原始的byte[]数组
     * @param huffmanCodes 赫夫曼编码表
     * @return 返回赫夫曼编码处理后的byte[]数组
     * 举例说明：
     * String content = "i like like like java do you like a java";
     * byte[] bytes = content.getBytes(); // 字节数组，8位一个字节,105 32 108 105 107 101...
     * ！！！bytes底层就是二进制数组，8位一个字节，给的是补码，负数反码等于补码-1，负数原码等于反码除了符号位取反，正数原码等于反码和补码
     * byte[] huffmanBytes = huffmanZip(bytes);
     * System.out.println("huffmanBytes = " + Arrays.toString(huffmanBytes));
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        // 1 利用huffmanCodes将bytes转换成赫夫曼编码对应字符串
        StringBuilder stringBuilder = new StringBuilder();
        // 遍历bytes，使用huffmanCodes将bytes转换成赫夫曼编码对应字符串
        for (byte b : bytes) { // 需要传递的字符串转根据赫夫曼编码变成二进制字符串
            stringBuilder.append(huffmanCodes.get(b));
        }

        // 2 根据赫夫曼编码表，将stringBuilder转换成byte[]数组
        // 一句话搞定就是int len = (stringBuilder.length() + 7) / 8
        int len;
        if (stringBuilder.length() % 8 == 0) len = stringBuilder.length() / 8;
        else len = stringBuilder.length() / 8 + 1;
        // 创建一个存储压缩后的byte数组，长度为len
        byte[] huffmanBytes = new byte[len];
        for (int i = 0; i < stringBuilder.length(); i += 8) { // 步长为8
            // 每8位取出一次
            String str;
            if (i + 8 > stringBuilder.length()) str = stringBuilder.substring(i, stringBuilder.length());
            else str = stringBuilder.substring(i, i + 8);
            // 将str转换成byte，放入huffmanBytes
            huffmanBytes[i / 8] = (byte) Integer.parseInt(str, 2);
        }

        return huffmanBytes;
    }

    /**
     * 使用一个方法封装上面的方法，便于调用
     * @param bytes 原始的byte[]数组
     * @return 返回赫夫曼编码处理后的byte[]数组
     */
    private static byte[] huffmanZip(byte[] bytes) {
        return zip(bytes,
                getCodes(
                        createHuffmanTree(
                                getNodes(bytes))));
    }

    /**
     * 完成数据的解压
     * 思路
     * 1 首先根据赫夫曼编码表将huffmanBytes转换成赫夫曼编码对应的二进制字符串
     * 2 遍历赫夫曼编码对应的字符串，根据赫夫曼编码表的反向查询，将赫夫曼编码对应的字符串转换成原始字符串
     */
    /**
     * 将byte转换成二进制的字符串
     * @param b 需要转换的byte
     * @param needFullEight 是否需要补高位
     * @return 该byte对应的二进制的字符串
     */
    public static String getBinaryString(boolean needFullEight, byte b) {
        // 使用变量保存 huffmanBytes
        int temp = b; // 由于int是4字节，所以需要将temp强转成int
        // 如果temp是正数，仍然按位补足8位
        if (needFullEight) { // 为了安全截取后8位
            temp |= 256; // 按位或 256 1 0000 0000 | 0000 0001 => 1 0000 0001
        }
        String str = Integer.toBinaryString(temp); // 返回的是temp的二进制的补码，
        // System.out.println(b + "对应的二进制字符串为：" + str);
        if (needFullEight) return str.substring(str.length() - 8);
        return str;
    }

    /**
     * 解码方法
     * @param huffmanBytes 压缩后的byte数组
     * @param huffmanCodes 赫夫曼编码表
     * @return 原始的byte数组
     */
    public static byte[] decode(byte[] huffmanBytes, Map<Byte, String> huffmanCodes) {
        // 1 先得到huffmanBytes 对应的二进制的字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) { // 改用索引遍历
            byte huffmanByte = huffmanBytes[i];
            // 判断当前是不是最后一个字节（用索引 i 判断！）
            boolean isLastByte = (i == huffmanBytes.length - 1);
            // 最后一个字节：需要截取为8位(！isLastByte)；中间的字节：不截取，直接拼接
            stringBuilder.append(getBinaryString(!isLastByte, huffmanByte));
        }
        // System.out.println("赫夫曼字节数组对应的二进制字符串=" + stringBuilder.toString());
        // 2 把字符串安装指定的赫夫曼编码进行解码
        // 把赫夫曼编码表进行调换，因为反向查询a->100 100->a
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        // System.out.println("反向查询map=" + map);

        // 创建一个集合，用于存储byte
        List<Byte> bytes = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1; // 计数器
            boolean flag = true; // 标志位
            Byte b = null; // 用于存储找到的字符
            while (flag) {
                // i是索引，指向开始位置，count是count是长度，比如i=0, count=1, 就是找到第一个字符
                String key = stringBuilder.substring(i, i + count);
                // 根据key去map中查询对应的value
                b = map.get(key);
                if (b == null) { // 如果找不到，则count++，继续查询
                    count++;
                } else { // 如果找到，则退出
                    flag = false;
                }
            }
            bytes.add(b);
            i += count;
        }

        // 当for循环结束后,我们list中就存放了所有的字符
        // 把list 中的数据放入到byte[] 并返回
        byte[] b = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            b[i] = bytes.get(i);
        }
        return b;
    }

    /**
     * 文件压缩
     * @param srcFile 原文件全路径
     * @param dstFile 压缩后文件目标全路径
     */
    public static void zipFile(String srcFile, String dstFile) {
        try (
            // 因为文件输入输出只认byte，所以需要创建对象流对java对象进行序列化写入和读取
            // 流写在括号里，会自动关闭（顺序：先关 oos，再关 fos，最后关 fis）
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(dstFile); // 创建文件输出流，相当于创建目标文件
            ObjectOutputStream oos = new ObjectOutputStream(fos); // 创建对象输出流，用于序列化写入对象
        ) {
            // 把硬盘文件读成内存里的 byte[]
            byte[] b = new byte[fis.available()];
            fis.read(b); // fis -> b

            // 使用赫夫曼编码对byte[]数组进行压缩
            byte[] huffmanBytes = huffmanZip(b);

            // 写入压缩后的字节数组和编码表 huffman... -> oos
            // oos 内部自动调用了底层 fos.write(字节)
            // 把对象（byte[]、Map、List等）直接序列化写入
            oos.writeObject(huffmanBytes); // 把压缩后的字节数组写入
            oos.writeObject(huffmanCodes); // 把赫夫曼编码表写入

            System.out.println("压缩成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件解压
     * @param zipFile 需要解压缩文件全路径
     * @param dstFile 解压后文件目标全路径
     */
    public static void unZipFile(String zipFile, String dstFile) {
        try (
            // 创建文件输入流，读取压缩文件
            FileInputStream fis = new FileInputStream(zipFile);
            // 创建对象输入流，反序列化读取对象
            ObjectInputStream ois = new ObjectInputStream(fis);
            // 创建文件输出流，相当于创建目标文件
            FileOutputStream fos = new FileOutputStream(dstFile);
        ) {
            // 读取压缩后的字节数组和编码表，存顺序就是读顺序 huffman... <- ois
            byte[] huffmanBytes = (byte[]) ois.readObject();
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();

            // 解码
            byte[] bytes = decode(huffmanBytes, huffmanCodes);

            // 需要手动写入目标文件
            fos.write(bytes);
            System.out.println("解压成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// 创建Node
class Node implements Comparable<Node> {
    Byte data; // 存放数据本身,比如a=> 97=> 32
    int weight; // 权值，就是数据出现的次数
    Node left; // 左子节点
    Node right; // 右子节点

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }

    public void preOrder() {
        System.out.println(this);
        if (this.left != null) this.left.preOrder();
        if (this.right != null) this.right.preOrder();
    }
}


