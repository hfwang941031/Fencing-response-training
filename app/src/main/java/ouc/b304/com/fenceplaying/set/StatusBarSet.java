package ouc.b304.com.fenceplaying.set;


import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;


/**
 * Created by lenovo on 2018/2/1.
 */

public class StatusBarSet {
    /**
     * 设置是否设置状态栏颜色
     *
     * @param context 上下文
     * @param colour  颜色值
     * @param flag    是否设置颜色值
     */

    public static void StatusBar(Activity context, String colour, Boolean flag) {
        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(context, true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        if (flag) {
            // 自定义颜色
            tintManager.setTintColor(Color.parseColor(colour));
        } else {
            //状态栏设置为透明
            tintManager.setStatusBarAlpha(0.0f);
        }
    }

    @TargetApi(19)
    private static void setTranslucentStatus(Activity context, boolean on) {
        Window win = context.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
