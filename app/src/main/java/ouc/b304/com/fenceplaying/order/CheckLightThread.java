package ouc.b304.com.fenceplaying.order;

import android.os.SystemClock;


import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;


/**
 * RetryOrderThread
 *
 * @author Skx
 * @date 2018/12/6
 */
public class CheckLightThread extends Thread {
    private boolean mIsStop = true;
    @Override
    public void run() {
        super.run();
        while (mIsStop) {
            for (DbLight dbLight : AppConfig.sDbLights) {
                if (System.currentTimeMillis()-dbLight.getReadTime()>30000){
                    dbLight.setPower("0000");
                    dbLight.setUsable(false);
                }
            }
            SystemClock.sleep(5000);
        }
    }

    public void stopCheck() {
        mIsStop = false;
    }
}
