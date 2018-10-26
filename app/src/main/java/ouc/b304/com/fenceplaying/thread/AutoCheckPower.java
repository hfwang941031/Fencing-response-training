package ouc.b304.com.fenceplaying.thread;

import android.content.Context;
import android.os.Handler;


import ouc.b304.com.fenceplaying.device.Device;

import static ouc.b304.com.fenceplaying.thread.ReceiveThread.POWER_RECEIVE_THREAD;

public class AutoCheckPower extends Thread {

    private static final int POWER_RECEIVE = 2;
    private boolean powerFlag = true;
    private Context context;
    private Handler handler;
    private Device device;
    private int flag;
    public AutoCheckPower(Context context, Device device,int flag,Handler handler) {
        this.context=context;
        this.device=device;
        this.flag=flag;
        this.handler=handler;
    }

    public void setPowerFlag(boolean powerFlag) {
        this.powerFlag = powerFlag;
    }

    @Override
    public void run() {
        while (powerFlag) {
            // 发送获取全部设备电量指令
            device.sendGetDeviceInfo();
            //开启接收电量的线程
            new ReceiveThread(handler,device.ftDev,POWER_RECEIVE_THREAD,POWER_RECEIVE).start();
            Timer.sleep(10000);
        }
    }
}
