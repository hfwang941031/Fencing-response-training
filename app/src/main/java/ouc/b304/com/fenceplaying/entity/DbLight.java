package ouc.b304.com.fenceplaying.entity;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author 王海峰 on 2019/4/20 10:37
 */
public class DbLight extends RealmObject implements Serializable {
    @PrimaryKey
    private long id;
    private String mName;
    private String mAddress;
    private String mPanId;
    private String mPower;
    /**
     * 读取到的时间
     */
    private long mReadTime;
    /**
     * 是否选中（加入跑道）
     */
    private boolean isSelect;

    /**
     * 是否可用（电量）
     */
    private boolean isUsable;

    /**
     * 是否点亮（开灯）
     */
    private boolean isOpen;

    /**
     * 是否休眠
     */
    private boolean isSleep;

    /**
     * 开灯时间
     */
    private long mOpenTime;

    public long getOpenTime() {
        return mOpenTime;
    }

    public void setOpenTime(long openTime) {
        mOpenTime = openTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getPanId() {
        return mPanId;
    }

    public void setPanId(String panId) {
        mPanId = panId;
    }

    public String getPower() {
        return mPower;
    }

    public void setPower(String power) {
        mPower = power;
    }

    public long getReadTime() {
        return mReadTime;
    }

    public void setReadTime(long readTime) {
        mReadTime = readTime;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isSleep() {
        return isSleep;
    }

    public void setSleep(boolean sleep) {
        isSleep = sleep;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "DbLight{" +
                "id=" + id +
                ", mName='" + mName + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mPanId='" + mPanId + '\'' +
                ", mPower='" + mPower + '\'' +
                ", mReadTime=" + mReadTime +
                ", isSelect=" + isSelect +
                ", isUsable=" + isUsable +
                ", isOpen=" + isOpen +
                ", isSleep=" + isSleep +
                ", mOpenTime=" + mOpenTime +
                '}';
    }

}

