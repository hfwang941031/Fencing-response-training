package ouc.b304.com.fenceplaying.utils.newUtils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by lenovo on 2018/4/21.
 */

public class ToastUtils {
    private static Toast mToast;
    public static Toast makeText(Context context, int str, int lengthShort) {
        if (mToast != null) {
            mToast.setText(context.getResources().getString(str));
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast = Toast.makeText(context, context.getResources().getString(str), Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        return  mToast;
    }
    public static Toast makeText(Context context, String str, int lengthShort) {
        if (mToast != null) {
            mToast.setText(str);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        return  mToast;
    }
    public static void showToast(Context context, String str, int lengthShort) {
        if (mToast != null) {
            mToast.setText(str);
            mToast.setDuration(lengthShort);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast = Toast.makeText(context, str, lengthShort);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast.show();
    }

    /**
     * @author 王海峰 on 2019/3/20 16:47
     */
    public static class RealmUtils1 {
    }
}
