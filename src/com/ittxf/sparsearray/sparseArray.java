package com.ittxf.sparsearray;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class sparseArray {
    public static void main(String[] args) {
        // 创建一个原始的二维数组11*11
        // 0:表示没有棋子，1 表示黑子 2 表示白子
        int[][] chessArr = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        // 输出原始的二维数组
        System.out.println("============原始的二维数组============");
        for (int[] row : chessArr) {
            for (int data : row) {
                System.out.print(data + "\t");
            }
            System.out.println();
        }

        // 将二维数组 转 稀疏数组
        // 1 先遍历二维数组 得到非0数据的个数
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0) {
                    sum++;
                }
            }
        }

        // 2 创建 稀疏数组 sparseArr int[sum+1][3]
        int[][] sparseArr = new int[sum + 1][3];
        // 给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;

        // 遍历二维数组，将非0的值赋给稀疏数组
        int count = 0; // count 用于记录是第几个非0数据
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr[i][j];
                }
            }
        }

        // 输出稀疏数组的形式
        // 二维数组的长度是指数组的行数
        System.out.println();
        System.out.println("============稀疏数组============");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.println(sparseArr[i][0] + "\t" + sparseArr[i][1] + "\t" + sparseArr[i][2]);
        }
        System.out.println();

        // 将稀疏数组保存到磁盘文件 map.data
        String filePath = "filePath/map.data";
        saveSparseArrToFile(sparseArr, filePath);
        System.out.println("============稀疏数组已保存到文件: " + new File(filePath).getAbsolutePath() + "============");
        System.out.println();

        // 从磁盘文件 map.data 读取稀疏数组，并恢复原来的二维数组
        int[][] sparseArrFromFile = loadSparseArrFromFile(filePath);

        // 将读取的稀疏数组 转换回二维数组
        /*
        * 1 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的 chessArr2=int[11][11]
        * 2 在读取稀疏数组后几行的数据，并赋给原始的二维数组即可
        * */

        // 1 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
        int[][] chessArr2 = new int[sparseArrFromFile[0][0]][sparseArrFromFile[0][1]];

        System.out.println("============从文件恢复的二维数组============");

        // 2 在读取稀疏数组后几行的数据，并赋给原始的二维数组即可
        for (int i = 1; i < sparseArrFromFile.length; i++) {
            chessArr2[sparseArrFromFile[i][0]][sparseArrFromFile[i][1]] = sparseArrFromFile[i][2];
        }
        for (int[] row : chessArr2) {
            for (int data : row) {
                System.out.print(data + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 将稀疏数组保存到磁盘文件
     * 文件格式：每行三个整数（row, col, val），用制表符分隔
     *
     * @param sparseArr 稀疏数组
     * @param filePath  文件路径
     */
    public static void saveSparseArrToFile(int[][] sparseArr, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int[] row : sparseArr) {
                writer.write(row[0] + "\t" + row[1] + "\t" + row[2]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("保存稀疏数组到文件时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 从磁盘文件读取稀疏数组
     *
     * @param filePath 文件路径
     * @return 稀疏数组
     */
    public static int[][] loadSparseArrFromFile(String filePath) {
        int[][] sparseArr = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<int[]> dataList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\t");
                if (parts.length == 3) {
                    int[] data = new int[]{
                            Integer.parseInt(parts[0].trim()),
                            Integer.parseInt(parts[1].trim()),
                            Integer.parseInt(parts[2].trim())
                    };
                    System.out.println(Arrays.toString(data));
                    dataList.add(data);
                }
            }
            // new int[0][]: 创建一个空的二维数组，长度为0，不够就在内部帮你创建一个全新的、长度正好等于 dataList.size() 的数组
            sparseArr = dataList.toArray(new int[0][]);
            System.out.println(Arrays.deepToString(sparseArr));
        } catch (IOException e) {
            System.err.println("从文件读取稀疏数组时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
        return sparseArr;
    }
}
