package ouc.b304.com.fenceplaying.Bean;

import java.util.Comparator;

public class PowerInfoComparetor implements Comparator {
    @Override
    public int compare(Object object1, Object object2) {
        DeviceInfo info1 = (DeviceInfo) object1;
        DeviceInfo info2 = (DeviceInfo) object2;
        return info1.getDeviceNum() - info2.getDeviceNum();
    }
}
