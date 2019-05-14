package ouc.b304.com.fenceplaying.activity.newVersion;

/**
 * @author 王海峰 on 2019/4/20 11:16
 */


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.zhy.autolayout.AutoLayoutActivity;

import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.set.StatusBarSet;


public class BaseActivity extends AutoLayoutActivity {

    private Boolean mDeviceFlag = false;
    private String mClassName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //强制横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        StatusBarSet.StatusBar(this, "", false);
        mClassName = getClass().getSimpleName();
        Log.d("ActivityLife", mClassName + "：base_onCreate");
    }

    /**
     * 初始化设备
     */
    protected void initBaseDevice() {

    }

    /**
     * 断开重新连接
     */
    protected void onReconnect() {
        Log.d("FIFIFIOA", "断网重连: ");
//        device.disconnect();
//        if (mDeviceFlag) {
//            device.createDeviceList(this);
//            //判断是否插入协调器
//            if (device.devCount() > 0) {
//                device.connect(this);
//                device.initConfig();
//            }
//        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        if (mDeviceFlag) {
//            device.createDeviceList(this);
//            //判断是否插入协调器
//            if (device.devCount() > 0) {
//                device.connect(this);
//                device.initConfig();
//            }
//        }

        Log.d("ActivityLife", mClassName + "：base_onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ActivityLife", mClassName + "：base_onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ActivityLife", mClassName + "：base_onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
//        if (mDeviceFlag) {
//            device.disconnect();
//        }
        Log.d("ActivityLife", mClassName + "：base_onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ActivityLife", mClassName + ": base_onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.activity_start, R.anim.activity_hold);
        Log.d("ActivityLife", mClassName + ": base_onDestroy");
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_start, R.anim.activity_hold);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_finish);
    }
}
