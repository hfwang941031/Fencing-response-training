package ouc.b304.com.fenceplaying.utils;

import android.content.Context;

/**
 * @author 王海峰 on 2018/10/26 10:11
 *
 * px与dp相互转换工具类
 */

    public class PXUtils {
    public static float dpToPx(Context context, int dp) {
        //获取屏蔽的像素密度系数
        float density = context.getResources().getDisplayMetrics().density;
        return dp * density;
    }

    public static float pxTodp(Context context, int px) {
        //获取屏蔽的像素密度系数
        float density = context.getResources().getDisplayMetrics().density;
        return px / density;
    }
}


