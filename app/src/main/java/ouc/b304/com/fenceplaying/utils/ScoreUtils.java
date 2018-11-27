package ouc.b304.com.fenceplaying.utils;

import java.util.List;

/**
 * @author 王海峰 on 2018/11/27 08:51
 * 功能：
 * 1、计算成绩的平均值
 */
public class ScoreUtils {
    public static float calcAverageScore(List<Integer> timeList) {
        int sum=0;
        //累加成绩
        for (Integer score:timeList
             ) {
            sum=sum+score;
        }
        //成绩和 除以成绩个数
        return sum / timeList.size();
    }
}
