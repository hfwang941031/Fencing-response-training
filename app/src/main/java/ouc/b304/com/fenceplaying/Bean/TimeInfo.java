package ouc.b304.com.fenceplaying.Bean;

public class TimeInfo {
    //设备编号
    private char deviceNum;
    //返回的时间
    private int time;
    //数据是否有效
    private boolean valid = true;

    public char getDeviceNum()
    {
        return deviceNum;
    }

    public void setDeviceNum(char deviceNum)
    {
        this.deviceNum = deviceNum;
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public boolean isValid()
    {
        return valid;
    }

    public void setValid(boolean valid)
    {
        this.valid = valid;
    }

    @Override
    public String toString()
    {
        return "TimeInfo{" +
                "deviceNum=" + deviceNum +
                ", time=" + time +
                ", valid=" + valid +
                '}';
    }
}
