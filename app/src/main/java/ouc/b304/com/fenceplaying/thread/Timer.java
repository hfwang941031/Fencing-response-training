package ouc.b304.com.fenceplaying.thread;

import android.os.Handler;
import android.os.Message;

import java.text.DecimalFormat;

public class Timer extends Thread
{
    public final static int TIMER_FLAG = 0;

    public static void sleep(int value)
    {
        try
        {
            Thread.sleep(value);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    private boolean stopFlag = false;
    private Handler handler;
    private long beginTime;
    public int time;
    //计时总时间
    private int totalTime;
    public Timer(Handler handler)
    {
        this.handler = handler;
    }

    public Timer(Handler handler, int totaTime)
    {
        this(handler);
        this.totalTime = totaTime;
    }

    @Override
    public void run()
    {
        super.run();
        while (!stopFlag)
        {
            time = (int) (System.currentTimeMillis() - beginTime);
            if (totalTime != 0 && time >= totalTime)
            {
                time = totalTime;
                stopTimer();
            }
            int minute = time / (1000 * 60);
            int second = (time / 1000) % 60;
            int msec = time % 1000;
            String res = "";
            res += new DecimalFormat("00").format(minute) + ":";
            res += new DecimalFormat("00").format(second) + ":";
            res += new DecimalFormat("00").format(msec / 10);
            Message msg = Message.obtain();
            msg.what = TIMER_FLAG;
            msg.obj = res;
            handler.sendMessage(msg);
            sleep(20);
        }
    }

    public void stopTimer()
    {
        stopFlag = true;
    }

    public void setBeginTime(long beginTime)
    {
        this.beginTime = beginTime;
    }
}
