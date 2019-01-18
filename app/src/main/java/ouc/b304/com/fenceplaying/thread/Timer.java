package ouc.b304.com.fenceplaying.thread;

import android.os.Handler;
import android.os.Message;

import java.text.DecimalFormat;

public class Timer extends Thread
{
    public final static int TIMER_FLAG = 0;
    public final static int TIMER_DOWN = -1;

    public static void sleep(int value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private boolean stopFlag = false;
    private Handler handler;
    private long beginTime;
    public int time;
    public int time_down;//倒计时
    private long stopTime;
    //计时总时间
    private int totalTime = -1;

    public Timer(Handler handler) {
        this.handler = handler;
    }

    public Timer(Handler handler, int totalTime) {
        this(handler);
        this.totalTime = totalTime;
    }

    @Override
    public void run() {
        super.run();
        while (!stopFlag) {
            time = (int) (System.currentTimeMillis() - beginTime);
            stopTime = System.currentTimeMillis();
            if (totalTime != -1) {
                if (totalTime != 0 && time >= totalTime) {
                    time = totalTime;
                    stopTimer();
                }
                //倒计时
                time_down = totalTime - time;
                int minute_down = time_down / (1000 * 60);
                int second_down = (time_down / 1000) % 60;
                int msec_down = time_down % 1000;
                String res_down = "";
                res_down += new DecimalFormat("00").format(minute_down) + ":";
                res_down += new DecimalFormat("00").format(second_down) + ".";
                res_down += new DecimalFormat("00").format(msec_down / 10);
                Message msg2 = Message.obtain();
                msg2.what = TIMER_DOWN;
                msg2.obj = res_down;
                handler.sendMessage(msg2);
            } else {
                int minute = time / (1000 * 60);
                int second = (time / 1000) % 60;
                int msec = time % 1000;
                String res = "";
                res += new DecimalFormat("00").format(minute) + ":";
                res += new DecimalFormat("00").format(second) + ".";
                res += new DecimalFormat("00").format(msec / 10);
                //总时间
                Message msg = Message.obtain();
                msg.what = TIMER_FLAG;
                msg.obj = res;
                handler.sendMessage(msg);
            }
            sleep(20);
        }
    }

    public void stopTimer() {
        stopFlag = true;

    }

    public int getTime() {
        return time;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public long getStopTime() {
        return stopTime;
    }
}
