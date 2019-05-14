package ouc.b304.com.fenceplaying.entity;

public class BackCommand {
    private String mAddress;
    private String mCommandId;
    private String mStatus;
    private int mLightType;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getCommandId() {
        return mCommandId;
    }

    public void setCommandId(String commandId) {
        mCommandId = commandId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public int getLightType() {
        return mLightType;
    }

    public void setLightType(int lightType) {
        mLightType = lightType;
    }
}
