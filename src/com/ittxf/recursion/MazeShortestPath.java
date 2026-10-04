package com.ittxf.recursion;

/**
 * 迷宫最短路径（递归穷举 + 回溯）
 * 写法参考同包的 MazeProblem，但目标不一样：
 * MazeProblem 找到"一条"能走通的路就收工；这个类要找"步数最少"的那一条。
 *
 * 两者的关键差别只有一处：
 * MazeProblem 里 map[i][j] = 2 之后不会还原，走过的格子就被永久占用，所以只能得到一条路；
 * 这里每次往下探完必须把格子改回 0（回溯复位），别的路径才能再经过它，从而把所有通路都试一遍。
 *
 * 地图约定（跟 MazeProblem 一致）：
 * 0 表示这个点还没走（可走）
 * 1 表示墙
 * 2 表示通路（当前这条路径走过的格子）
 * 3 这里用不上：MazeProblem 用 3 标记"死路"，而本类回溯时会把格子改回 0，不需要死路标记
 */
public class MazeShortestPath {

    // 地图大小、起点、终点，写成常量，方便改地图时一起调
    static final int ROW = 8;     // 8 行
    static final int COL = 7;     // 7 列
    static final int START_I = 1; // 起点行
    static final int START_J = 1; // 起点列
    static final int END_I = 6;   // 终点行
    static final int END_J = 5;   // 终点列

    // 下面几个变量是"穷举"过程中的全局战绩
    static int minStep = Integer.MAX_VALUE; // 目前找到的最短步数
    static int maxStep = 0;                 // 目前找到的最长步数，用来看"随便一条"能绕多远
    static int[][] bestMap = null;          // 最短那条路径对应的地图快照
    static int wayCount = 0;                // 一共走通过多少条路径

    public static void main(String[] args) {
        int[][] map0 = buildMap();
        System.out.println("地图情况如下：");
        printMap(map0);

        // ========== 一、MazeProblem 那种写法：只找第一条通路 ==========
        // 找路策略：下->右->上->左
        int[][] map1 = buildMap();
        findFirstWay(map1, START_I, START_J);
        System.out.println("【第一条通路】策略 下->右->上->左，步数 = " + countStep(map1));
        printMap(map1);

        // 换一种策略，得到的"第一条通路"步数就不一样了 —— 说明这条路好不好全看策略
        // 找路策略：上->右->下->左
        int[][] map2 = buildMap();
        findFirstWay2(map2, START_I, START_J);
        System.out.println("【第一条通路】策略 上->右->下->左，步数 = " + countStep(map2));
        printMap(map2);

        // ========== 二、穷举所有通路，取步数最少的那一条 ==========
        int[][] map3 = buildMap();
        resetRecord();
        findAllWay(map3, START_I, START_J, 0);
        System.out.println("【穷举求最短】一共走通过 " + wayCount + " 条通路，最短步数 = " + minStep
                + "，最长步数 = " + maxStep);
        System.out.println("（上面两条『第一条通路』都是 9 步，在这张图上刚好碰对了最短；"
                + "但同一张图里最长的一条有 " + maxStep + " 步，可见拿到一条能走通的路不等于最短）");
        System.out.println("最短路径如下：");
        printMap(bestMap);

        // ========== 三、换一张更容易绕远的地图，对比就明显了 ==========
        int[][] map4 = buildMap2();
        System.out.println("地图二（中间堵了两块，容易绕远）如下：");
        printMap(map4);

        int[][] map5 = buildMap2();
        findFirstWay(map5, START_I, START_J); // 还是 下->右->上->左
        System.out.println("【第一条通路】地图二，策略 下->右->上->左，步数 = " + countStep(map5));
        printMap(map5);

        int[][] map6 = buildMap2();
        resetRecord();
        findAllWay(map6, START_I, START_J, 0);
        System.out.println("【穷举求最短】地图二，共 " + wayCount + " 条通路，最短步数 = " + minStep
                + "，最长步数 = " + maxStep + "（第一条通路走了 " + countStep(map5) + " 步）");
        System.out.println("最短路径如下：");
        printMap(bestMap);
    }

    // 每穷举一张新地图，都要把战绩清零
    public static void resetRecord() {
        minStep = Integer.MAX_VALUE;
        maxStep = 0;
        bestMap = null;
        wayCount = 0;
    }

    /**
     * 造一张地图，跟 MazeProblem 用的是同一张（8*7，四周是墙，中间两块挡板）
     * 每次实验都要一张干净的地图，所以单独抽成一个方法
     */
    public static int[][] buildMap() {
        int[][] map = new int[ROW][COL];
        // 使用1 表示墙，先把上下全部设置为1
        for (int i = 0; i < COL; i++) {
            map[0][i] = 1;
            map[ROW - 1][i] = 1;
        }
        // 再把左右全部设置为1
        for (int i = 0; i < ROW; i++) {
            map[i][0] = 1;
            map[i][COL - 1] = 1;
        }
        // 设置挡板
        map[3][1] = 1;
        map[3][2] = 1;
        return map;
    }

    /**
     * 第二张地图：在第一张的基础上，再把 (2,3)、(3,3) 堵上
     * 这样"下->右->上->左"这种策略就会被拐进一条绕远的路，最短和第一条的差距能看出来
     */
    public static int[][] buildMap2() {
        int[][] map = buildMap();
        map[2][3] = 1;
        map[3][3] = 1;
        return map;
    }

    /**
     * MazeProblem 的写法：找到一条通路就返回，走过的格子（置 2）不还原
     * 策略：下->右->上->左
     */
    public static boolean findFirstWay(int[][] map, int i, int j) {
        if (map[END_I][END_J] == 2) { // 通路已经找到
            return true;
        }
        if (map[i][j] == 0) { // 这个点没走过
            map[i][j] = 2; // 标记为通路
            if (findFirstWay(map, i + 1, j)) { // 向下走
                return true;
            } else if (findFirstWay(map, i, j + 1)) { // 向右走
                return true;
            } else if (findFirstWay(map, i - 1, j)) { // 向上走
                return true;
            } else if (findFirstWay(map, i, j - 1)) { // 向左走
                return true;
            } else {
                map[i][j] = 3; // 这个点是死路
                return false;
            }
        } else { // 是墙(1)、通路(2)、死路(3)，都不能再走
            return false;
        }
    }

    /**
     * 同上，只把策略换成 上->右->下->左，用来说明"第一条通路"的长度完全取决于策略
     */
    public static boolean findFirstWay2(int[][] map, int i, int j) {
        if (map[END_I][END_J] == 2) {
            return true;
        }
        if (map[i][j] == 0) {
            map[i][j] = 2;
            if (findFirstWay2(map, i - 1, j)) { // 向上走
                return true;
            } else if (findFirstWay2(map, i, j + 1)) { // 向右走
                return true;
            } else if (findFirstWay2(map, i + 1, j)) { // 向下走
                return true;
            } else if (findFirstWay2(map, i, j - 1)) { // 向左走
                return true;
            } else {
                map[i][j] = 3;
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 穷举所有通路，并把步数最少的那一条记下来
     *
     * @param map  地图
     * @param i    当前所在行
     * @param j    当前所在列
     * @param step 从起点走到当前这个格子，已经走了几步（起点是 0 步）
     */
    public static void findAllWay(int[][] map, int i, int j, int step) {
        // 越界直接返回（这张图四周都是墙，正常走不出去，加上更保险）
        if (i < 0 || i >= ROW || j < 0 || j >= COL) {
            return;
        }
        // 是墙，或者这条路径已经走过这个格子
        if (map[i][j] != 0) {
            return;
        }
        map[i][j] = 2; // 先走这一步，标记成通路
        if (i == END_I && j == END_J) {
            // 走到终点了，说明这是一条能走通的路
            wayCount++;
            if (step > maxStep) {
                maxStep = step; // 顺便记下最长的，用来做对比
            }
            if (step < minStep) {
                minStep = step;
                bestMap = copyMap(map); // 趁现在地图上就是这条路径，赶紧拍张快照存下来
            }
            map[i][j] = 0; // 别忘了还原，否则终点被占住，后面的路径都进不来
            return;
        }
        // 四个方向都去试一遍，不设"找到就停"，这样才能穷举
        findAllWay(map, i + 1, j, step + 1); // 下
        findAllWay(map, i, j + 1, step + 1); // 右
        findAllWay(map, i - 1, j, step + 1); // 上
        findAllWay(map, i, j - 1, step + 1); // 左
        // ★ 这一行就是求最短路径的关键：回溯时把这个格子还原成"没走过"
        //   只有还原了，别的路径才可能经过这里，才有可能比现在这条更短
        map[i][j] = 0;
    }

    /**
     * 复制一份地图（快照）
     * 注意不能直接写 map.clone()：那是浅拷贝，复制出来的行数组还是同一批对象，
     * 回溯时一改就把快照也改掉了，最后存的路径是错的
     */
    public static int[][] copyMap(int[][] map) {
        int[][] copy = new int[map.length][];
        for (int i = 0; i < map.length; i++) {
            copy[i] = map[i].clone(); // 每一行是一维数组，这样克隆才是真复制
        }
        return copy;
    }

    /**
     * 数一数地图上有多少个通路格子（2），换算成步数
     * n 个格子连成一条路，走的是 n-1 步
     */
    public static int countStep(int[][] map) {
        int count = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 2) {
                    count++;
                }
            }
        }
        return count - 1;
    }

    // 打印地图，跟 MazeProblem 里那段内层循环一样，只是抽出来免得重复写四遍
    public static void printMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
