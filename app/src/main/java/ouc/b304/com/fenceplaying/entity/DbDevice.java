package ouc.b304.com.fenceplaying.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author 王海峰 on 2019/4/20 10:37
 */
public class DbDevice extends RealmObject {
    @PrimaryKey
    private long id;
    private String mChannelNumber;
    private String mPanId;
    private String mMacAddress;
    private long mCreateTime;
    private RealmList<DbLight> mDbLights;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getChannelNumber() {
        return mChannelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        mChannelNumber = channelNumber;
    }

    public String getPanId() {
        return mPanId;
    }

    public void setPanId(String panId) {
        mPanId = panId;
    }

    public String getMacAddress() {
        return mMacAddress;
    }

    public void setMacAddress(String macAddress) {
        mMacAddress = macAddress;
    }

    public long getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(long createTime) {
        mCreateTime = createTime;
    }

    public RealmList<DbLight> getDbLights() {
        return mDbLights;
    }

    public void setDbLights(RealmList<DbLight> dbLights) {
        mDbLights = dbLights;
    }

    @Override
    public String toString() {
        String dbLights = "";
        for (DbLight dbLight : mDbLights) {
            dbLights += dbLight.toString();
        }
        return "Device{" +
                "id=" + id +
                ", mChannelNumber='" + mChannelNumber + '\'' +
                ", mPanId='" + mPanId + '\'' +
                ", mMacAddress='" + mMacAddress + '\'' +
                ", mCreateTime=" + mCreateTime +
                ", mDbLights=" + dbLights +
                '}';
    }


}

