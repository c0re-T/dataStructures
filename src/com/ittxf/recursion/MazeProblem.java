package com.ittxf.recursion;

public class MazeProblem {
    public static void main(String[] args) {
        // 先创建一个二维数组，表示迷宫
        // 地图
        int[][] map = new int[8][7];
        // 使用1 表示墙
        // 先把上下全部设置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        // 再把左右全部设置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        // 设置挡板
        map[3][1] = 1;
        map[3][2] = 1;

        System.out.println("地图情况如下：");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }

        // 使用递归回溯给小球找路
        // findWay(map, 1, 1);
        // 改变找路策略
        findWay2(map, 1, 1);

        System.out.println("通路情况如下：");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 使用递归回溯来解决迷宫问题
     * 1 map 表示地图
     * 2 i,j表示从地图的哪个位置开始出发（1,1）
     * 3 如果小球能到map[6][5]位置，则说明通路找到。
     * 4 约定：当map[i][j]为0表示该点没有走过
     *      当为1表示墙；
     *      2表示通路可以走；
     *      3表示该点已经走过，但是走不通
     * 5 通路的策略：下->右->上->左，如果该点走不通，再回溯
     * @param map 表示地图
     * @param i 从哪个位置开始找
     * @param j 从哪个位置开始找
     * @return 如果找到通路，就返回true，否则返回false
     */
    public static boolean findWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) { // 通路已经找到
            return true;
        } else {
            if (map[i][j] == 0) { // 如果当前点是0，则说明没有走过
                map[i][j] = 2; // 标记为已走过
                // 向下走
                if (findWay(map, i + 1, j)) {
                    return true;
                } // 向右走
                else if (findWay(map, i, j + 1)) {
                    return true;
                } // 向上走
                else if (findWay(map, i - 1, j)) {
                    return true;
                } // 向左走
                else if (findWay(map, i, j - 1)) {
                    return true;
                } else {
                    map[i][j] = 3; // 标记为死路
                    return false;
                }
            } else { // 如果map[i][j] != 0，可能的情况是1、2、3
                return false;
            }
        }
    }

    // 修改找路的策略，上->右->下->左
    public static boolean findWay2(int[][] map, int i, int j) {
        if (map[6][5] == 2) { // 通路已经找到
            return true;
        } else {
            if (map[i][j] == 0) { // 如果当前点是0，则说明没有走过
                map[i][j] = 2; // 标记为已走过
                // 向上走
                if (findWay2(map, i - 1, j)) {
                    return true;
                } // 向右走
                else if (findWay2(map, i, j + 1)) {
                    return true;
                } // 向下走
                else if (findWay2(map, i + 1, j)) {
                    return true;
                } // 向左走
                else if (findWay2(map, i, j - 1)) {
                    return true;
                } else {
                    map[i][j] = 3; // 标记为死路
                    return false;
                }
            } else { // 如果map[i][j] != 0，可能的情况是1、2、3
                return false;
            }
        }
    }
}
