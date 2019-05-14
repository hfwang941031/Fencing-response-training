package ouc.b304.com.fenceplaying.utils.newUtils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import ouc.b304.com.fenceplaying.entity.DbDevice;
import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.entity.ShockInfo;
import ouc.b304.com.fenceplaying.entity.TimeInfo;
import ouc.b304.com.fenceplaying.order.OrderEntity;

/**
 * AppConfig
 *
 * @author Skx
 * @date 2018/11/26
 */
public class AppConfig {
    public static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    public static final String ACTION_HEART = "com.skx.getNewBleInfo";
    public static final String ACTION_ANALYSIS_DEVICE = "com.analysis.device";
    public static final String ACTION_TIME_INFO = "com.time.info";
    public static final String ACTION_SHACK_INFO = "com.shack.info";
    public static final String ACTION_ALTER_ROPE = "com.alter.rope";
    public static final String ACTION_REMOVE_LIGHT = "com.skx.remove.light";
    public static boolean isFlag = true;
    /**
     * 协调器
     */
    public static DbDevice sDevice;
    /**
     * 全局光标
     */
    public static List<DbLight> sDbLights = new ArrayList<>();
    /**
     * 灭灯时间返回
     */
//    public static List<TimeInfo> sTimeInfoList = new ArrayList<>();
    public static LinkedList<TimeInfo> sTimeInfoList = new LinkedList<>();
    /**
     * 震动时间返回
     */
    public static List<ShockInfo> sShockInfoList = new ArrayList<>();
    /**
     * 数据
     */
    public static String sData = "";
    /**
     * 命令间隔
     */
    public static long sSleep = 50;
    /**
     * 命令重发次数
     */
    public static long sNum = 3;
    /**
     * 命令
     */
    public static CopyOnWriteArrayList<OrderEntity> sOrderEntityList = new CopyOnWriteArrayList<>();
    /**
     * 是否开启默认响应信息
     * 开启：0x01
     * 关闭：0x00
     */
    public static String sEnable_rsp = "01";
    public static final Object LOCK_READ = new Object();

}
