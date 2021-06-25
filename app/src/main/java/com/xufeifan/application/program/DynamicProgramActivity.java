package com.xufeifan.application.program;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xufeifan.application.R;

public class DynamicProgramActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_program);

        //3行3列（无阻碍）
        notHinder(3, 3);

        //1代表障碍物 0代表无障碍物（有阻碍）
        int[][] crr = new int[][]{
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        hasHinder(crr);
    }

    /**
     * 有障碍物（可不止一个）
     *
     * @param obs 二维数组
     * @return 路线条数
     */
    private int hasHinder(int[][] obs) {
        int m = obs.length;//行
        int n = obs[0].length;//列
        int[][] dp = new int[m][n];

        //处理第一条数据
        dp[0][0] = obs[0][0] == 1 ? 0 : 1;
        //处理第一行里的数据
        for (int i = 1; i < n; i++) {
            if (obs[0][i] == 1 || dp[0][i - 1] == 0) {
                dp[0][i] = 0;
            } else {
                dp[0][i] = 1;
            }
        }
        //处理第一列里的数据
        for (int j = 1; j < m; j++) {
            if (obs[j][0] == 1 || dp[j - 1][0] == 0) {
                dp[j][0] = 0;
            } else {
                dp[j][0] = 1;
            }
        }

        //处理其余数据
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obs[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    /**
     * 没有障碍物
     *
     * @param m 行
     * @param n 列
     * @return 路线条数
     */
    private int notHinder(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    //第一行，第一列均只有一条路线
                    dp[i][j] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

}
