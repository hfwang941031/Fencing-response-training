package ouc.b304.com.fenceplaying.usbUtils;

/**
 * UsbUtil
 *
 * @author Skx
 * @date 2018/10/31
 */
public class UsbUtil {
    private static UsbUtil instance;
    private UsbUtil() {
    }
    public static UsbUtil getInstance() {
        if (instance == null) {
            synchronized (UsbUtil.class) {
                if (instance == null) {
                    instance = new UsbUtil();
                }
            }
        }
        return instance;
    }
    public void sendMessage(String data) {
        UsbConfig.getInstance().sendMessage(data);
    }
    public String readData() {
        return UsbConfig.getInstance().readData();
    }
}
