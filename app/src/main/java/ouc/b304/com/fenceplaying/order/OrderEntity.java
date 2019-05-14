package ouc.b304.com.fenceplaying.order;

/**
 * OrderEntity
 *
 * @author Skx
 * @date 2018/12/5
 */
public class OrderEntity {
    private String mName;
    private String mAddress;
    /**
     * 发送的命令
     */
    private String mOrder = "";
    /**
     * 发送的次数
     */
    private int mSendNum;
    /**
     * 帧ID
     */
    private int mID;
    /**
     * 命令ID
     */
    private String mOrderId;
    /**
     * 剩余毫秒数
     */
    private int mTime = 500;

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

    public String getOrder() {
        return mOrder;
    }

    public void setOrder(String order) {
        mOrder = order;
    }

    public int getSendNum() {
        return mSendNum;
    }

    public void setSendNum(int sendNum) {
        mSendNum = sendNum;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        mOrderId = orderId;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "mName='" + mName + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mOrder='" + mOrder + '\'' +
                ", mSendNum=" + mSendNum +
                ", mID=" + mID +
                ", mOrderId='" + mOrderId + '\'' +
                ", mTime=" + mTime +
                '}';
    }
}
