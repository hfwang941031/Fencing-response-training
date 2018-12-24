package ouc.b304.com.fenceplaying.utils;

import java.util.List;

/**
 * @author 王海峰 on 2018/12/3 14:15
 */

/*
 *将Integer类型的列表转换成String类型 */
public class IntegerToStringUtils {
    public static List<String> integerToString(List<Integer> timeList, List<String> scoreList) {
        //首先清空scoreList
        scoreList.clear();
        if (timeList == null) {
            return null;
        }
        for (Integer score : timeList) {
            scoreList.add(String.valueOf(score));
        }
        return scoreList;
    }
}
