package ouc.b304.com.fenceplaying.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;



import ouc.b304.com.fenceplaying.order.CheckLightThread;
import ouc.b304.com.fenceplaying.order.OrderUtils;
import ouc.b304.com.fenceplaying.order.ReadDataThread;
import ouc.b304.com.fenceplaying.order.RetryOrderThread;


/**
 * ReadDataService
 * 读取协调器数据
 *
 * @author Skx
 * @date 2018/11/30
 */
public class ReadDataService extends Service {
    private RetryOrderThread mRetryOrderThread;
    private ReadDataThread mReadDataThread;
    private CheckLightThread mCheckLightThread;

    @Override
    public void onCreate() {
        super.onCreate();
        startReadData();
        openRetryOrder();
        startCheckLight();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        OrderUtils.getInstance().initDevice();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopReadData();
        stopRetryOrder();
        stopCheckLight();
    }

    /**
     * 读取串口数据
     */
    public void startReadData() {
        mReadDataThread = new ReadDataThread();
        mReadDataThread.start();

    }

    /**
     * 停止读取数据
     */
    public void stopReadData() {
        if (mReadDataThread != null) {
            mReadDataThread.stopRead();
            mReadDataThread.interrupt();
        }
    }

    /**
     * 开启命令重发线程
     */
    public void openRetryOrder() {
        mRetryOrderThread = new RetryOrderThread();
        mRetryOrderThread.start();
    }

    /**
     * 停止命令重发
     */
    public void stopRetryOrder() {
        if (mRetryOrderThread != null) {
            mRetryOrderThread.stopRetry();
            mRetryOrderThread.interrupt();
        }
    }

    /**
     * 检查电量
     */
    private void startCheckLight() {
        mCheckLightThread = new CheckLightThread();
        mCheckLightThread.start();
    }

    /**
     * 停止检查电量
     */
    private void stopCheckLight() {
        if (mCheckLightThread != null) {
            mCheckLightThread.stopCheck();
            mCheckLightThread.interrupt();
        }
    }
}
