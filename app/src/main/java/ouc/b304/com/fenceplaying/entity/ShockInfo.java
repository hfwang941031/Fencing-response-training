package ouc.b304.com.fenceplaying.entity;

import java.io.Serializable;

/**
 * ShockInfo
 *
 * @author Skx
 * @date 2018/11/27
 */
public class ShockInfo implements Serializable {
    private String mName;
    private String mAddress;
    private String[] shack_x = new String[10];
    private String[] shack_y = new String[10];
    private String[] shack_z = new String[10];

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

    public String[] getShack_x() {
        return shack_x;
    }

    public void setShack_x(String[] shack_x) {
        this.shack_x = shack_x;
    }

    public String[] getShack_y() {
        return shack_y;
    }

    public void setShack_y(String[] shack_y) {
        this.shack_y = shack_y;
    }

    public String[] getShack_z() {
        return shack_z;
    }

    public void setShack_z(String[] shack_z) {
        this.shack_z = shack_z;
    }

    @Override
    public String toString() {
        return "ShockInfo{}";
    }
}
