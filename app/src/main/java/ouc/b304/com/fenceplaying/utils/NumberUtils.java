package ouc.b304.com.fenceplaying.utils;

/**
 * @author 王海峰 on 2018/11/14 15:40
 */
public class NumberUtils {

    //产生范围内的随机数
    public static int randomNumber(int range) {
        return (int) (Math.random() * range);
    }
}
