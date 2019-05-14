package ouc.b304.com.fenceplaying.entity;

import java.io.Serializable;

/**
 * TimeInfo
 *
 * @author Skx
 * @date 2018/11/27
 */
public class TimeInfo implements Serializable {
    private String mName;
    private String mAddress;
    private String mReactionModel;
    private long mTime;
    private int mCount;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getReactionModel() {
        return mReactionModel;
    }

    public void setReactionModel(String reactionModel) {
        mReactionModel = reactionModel;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    @Override
    public String toString() {
        return "TimeInfo{" +
                "mName='" + mName + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mReactionModel='" + mReactionModel + '\'' +
                ", mTime=" + mTime +
                ", mCount=" + mCount +
                '}';
    }
}
