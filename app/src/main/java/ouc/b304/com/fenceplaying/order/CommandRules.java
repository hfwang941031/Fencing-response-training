package ouc.b304.com.fenceplaying.order;

/**
 * CommandRules
 *
 * @author Skx
 * @date 2018/11/22
 */
public class CommandRules {
    /**
     * 内圈灯颜色
     */
    public static enum InColor {
        /*蓝 红 绿 紫 青 黄 白*/
        BLUE, RED, GREEN, PURPLE, CYAN, YELLOW, WHITE;
    }

    /**
     * 外圈灯颜色
     */
    public static enum OutColor {
        /*蓝 红 绿 紫 青 黄 白*/
        BLUE, RED, GREEN, PURPLE, CYAN, YELLOW, WHITE;
    }

    /**
     * 亮灯模式
     */
    public static enum OpenModel {
        /*不亮 常亮 慢闪 快闪*/
        NONE, ALWAYS, SLOW, FAST;
    }

    /**
     * 蜂鸣器设置
     */
    public static enum BuzzerModel {
        /*不响 短响 长响1S 长响2S*/
        NONE, SHORT, LONG_1, LONG_2;
    }

    /**
     * 红外发射开关
     */
    public static enum InfraredEmission {
        /*关闭 开启*/
        CLOSE, OPEN;
    }

    /**
     * 红外感应开关
     */
    public static enum InfraredInduction {
        /*关闭 开启*/
        CLOSE, OPEN;
    }

    /**
     * 红外模式
     */
    public static enum InfraredModel {
        /*正常 竞争*/
        NORMAL, CONTEND;
    }

    /**
     * 红外感应高度
     */
    public static enum InfraredHeight {
        /*00最低档(目前没有)；01中低档；10中高档；11：最高档(目前没有)*/
        MIN_LOW, LOW, HIGH, MAX_HIGH;
    }

    /**
     * 震动感应
     */
    public static enum VibrationInduced {
        /*关闭 开启*/
        CLOSE, OPEN;
    }

    /**
     * 震动详情
     */
    public static enum VibrationDetails {
        /*关闭 开启*/
        CLOSE, OPEN;
    }

    /**
     * 震动强度
     */
    public static enum VibrationStrength {
        /*00触碰；01轻触；10重触*/
        TOUCH_M, TOUCH_L, TOUCH_H;
    }

    ///////////////////////////////// 跳绳命令/////////////////////////////////

    /**
     * 指令类型
     */
    public static enum OrderType {
        /*00 停止；01 按时计次；10 按次计时*/
        STOP, RECKON_BY_TIME, RECKON_BY_COUNT;

    }
}
