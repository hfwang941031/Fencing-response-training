package ouc.b304.com.fenceplaying.utils.newUtils;

import android.app.Application;
import android.content.Context;

/**
 * @author skx
 * @date 05/24
 */

public class Utils {

    private static Context context;
    private static int product_id = 24597;
    private static int vendor_id = 1027;
    private static int baudRate = 115200;
    /**
     * PAN_ID
     */
    public final static int PAN_ID_RECEIVE_THREAD = 1;
    /**
     * 线程标志 电量检测线程
     */
    public final static int POWER_RECEIVE_THREAD = 2;
    /**
     * 入网返回
     */
    public final static int NET_IN_RECEIVE_THREAD = 3;
    /**
     * 退网返回
     */
    public final static int REMOVE_NET_RECEIVE_THREAD = 4;
    /**
     * 数据返回
     */
    public final static int DATA_RECEIVE_THREAD = 5;

    public static void init(Application context) {
        Utils.context = context;
    }

    public static Context getContext() {
        return context;
    }

    public static int getProduct_id() {
        return product_id;
    }

    public static int getVendor_id() {
        return vendor_id;
    }

    public static int getBaudRate() {
        return baudRate;
    }

    /**
     * 把16进制字符串转换成字节数组
     *
     * @param hex
     * @return byte[]
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] chars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(chars[pos]) << 4 | toByte(chars[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * 数组转换成十六进制字符串
     *
     * @param bArray
     * @return HexString
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}
