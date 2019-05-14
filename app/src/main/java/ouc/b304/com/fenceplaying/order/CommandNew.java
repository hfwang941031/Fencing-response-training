package ouc.b304.com.fenceplaying.order;

/**
 * CommandNew
 *
 * @author Skx
 * @date 2018/11/22
 */
public class CommandNew {
    /**
     * 感应前外圈灯
     */
    private CommandRules.OpenModel mBeforeOutBlink = CommandRules.OpenModel.ALWAYS;
    private CommandRules.OutColor mBeforeOutColor = CommandRules.OutColor.BLUE;
    /**
     * 感应前内圈灯
     */
    private CommandRules.OpenModel mBeforeInBlink = CommandRules.OpenModel.NONE;
    private CommandRules.InColor mBeforeInColor = CommandRules.InColor.RED;
    /**
     * 感应前蜂鸣器
     */
    private CommandRules.BuzzerModel mBeforeBuzzerModel = CommandRules.BuzzerModel.NONE;
    /**
     * 红外感应设置
     */
    private CommandRules.InfraredInduction mInfraredInduction = CommandRules.InfraredInduction.CLOSE;
    private CommandRules.InfraredHeight mInfraredHeight = CommandRules.InfraredHeight.LOW;
    private CommandRules.InfraredEmission mInfraredEmission = CommandRules.InfraredEmission.CLOSE;
    private CommandRules.InfraredModel mInfraredModel = CommandRules.InfraredModel.NORMAL;
    /**
     * 震动感应设置
     */
    private CommandRules.VibrationInduced mVibrationInduced = CommandRules.VibrationInduced.CLOSE;
    private CommandRules.VibrationStrength mVibrationStrength = CommandRules.VibrationStrength.TOUCH_M;
    private CommandRules.VibrationDetails mVibrationDetails = CommandRules.VibrationDetails.CLOSE;
    /**
     * 感应后外圈灯
     */
    private CommandRules.OpenModel mAfterOutBlink = CommandRules.OpenModel.NONE;
    private CommandRules.OutColor mAfterOutColor = CommandRules.OutColor.BLUE;
    /**
     * 感应后内圈等
     */
    private CommandRules.OpenModel mAfterInBlink = CommandRules.OpenModel.NONE;
    private CommandRules.InColor mAfterInColor = CommandRules.InColor.RED;
    /**
     * 感应后蜂鸣器
     */
    private CommandRules.BuzzerModel mAfterBuzzerModel = CommandRules.BuzzerModel.NONE;

    /////////////////////////   跳绳属性   ///////////////////////////
    /**
     * 指令类型  00 停止；01 按时计次；10 按次计时
     */
    private CommandRules.OrderType mOrderType = CommandRules.OrderType.STOP;
    /**
     * 跳绳编号
     */
    private int mRopeName;
    /**
     * 按时计次有效时，计时的时间，以秒为单位
     */
    private int mTime;
    /**
     * 按次计时有效时，计次的次数，以个为单位
     */
    private int mCount;
    /**
     * 备用
     */
    private int mSpare;


    public CommandRules.OpenModel getBeforeOutBlink() {
        return mBeforeOutBlink;
    }

    public void setBeforeOutBlink(CommandRules.OpenModel beforeOutBlink) {
        mBeforeOutBlink = beforeOutBlink;
    }

    public CommandRules.OutColor getBeforeOutColor() {
        return mBeforeOutColor;
    }

    public void setBeforeOutColor(CommandRules.OutColor beforeOutColor) {
        mBeforeOutColor = beforeOutColor;
    }

    public CommandRules.OpenModel getBeforeInBlink() {
        return mBeforeInBlink;
    }

    public void setBeforeInBlink(CommandRules.OpenModel beforeInBlink) {
        mBeforeInBlink = beforeInBlink;
    }

    public CommandRules.InColor getBeforeInColor() {
        return mBeforeInColor;
    }

    public void setBeforeInColor(CommandRules.InColor beforeInColor) {
        mBeforeInColor = beforeInColor;
    }

    public CommandRules.BuzzerModel getBeforeBuzzerModel() {
        return mBeforeBuzzerModel;
    }

    public void setBeforeBuzzerModel(CommandRules.BuzzerModel beforeBuzzerModel) {
        mBeforeBuzzerModel = beforeBuzzerModel;
    }

    public CommandRules.InfraredInduction getInfraredInduction() {
        return mInfraredInduction;
    }

    public void setInfraredInduction(CommandRules.InfraredInduction infraredInduction) {
        mInfraredInduction = infraredInduction;
    }

    public CommandRules.InfraredHeight getInfraredHeight() {
        return mInfraredHeight;
    }

    public void setInfraredHeight(CommandRules.InfraredHeight infraredHeight) {
        mInfraredHeight = infraredHeight;
    }

    public CommandRules.InfraredEmission getInfraredEmission() {
        return mInfraredEmission;
    }

    public void setInfraredEmission(CommandRules.InfraredEmission infraredEmission) {
        mInfraredEmission = infraredEmission;
    }

    public CommandRules.InfraredModel getInfraredModel() {
        return mInfraredModel;
    }

    public void setInfraredModel(CommandRules.InfraredModel infraredModel) {
        mInfraredModel = infraredModel;
    }

    public CommandRules.VibrationInduced getVibrationInduced() {
        return mVibrationInduced;
    }

    public void setVibrationInduced(CommandRules.VibrationInduced vibrationInduced) {
        mVibrationInduced = vibrationInduced;
    }

    public CommandRules.VibrationStrength getVibrationStrength() {
        return mVibrationStrength;
    }

    public void setVibrationStrength(CommandRules.VibrationStrength vibrationStrength) {
        mVibrationStrength = vibrationStrength;
    }

    public CommandRules.VibrationDetails getVibrationDetails() {
        return mVibrationDetails;
    }

    public void setVibrationDetails(CommandRules.VibrationDetails vibrationDetails) {
        mVibrationDetails = vibrationDetails;
    }

    public CommandRules.OpenModel getAfterOutBlink() {
        return mAfterOutBlink;
    }

    public void setAfterOutBlink(CommandRules.OpenModel afterOutBlink) {
        mAfterOutBlink = afterOutBlink;
    }

    public CommandRules.OutColor getAfterOutColor() {
        return mAfterOutColor;
    }

    public void setAfterOutColor(CommandRules.OutColor afterOutColor) {
        mAfterOutColor = afterOutColor;
    }

    public CommandRules.OpenModel getAfterInBlink() {
        return mAfterInBlink;
    }

    public void setAfterInBlink(CommandRules.OpenModel afterInBlink) {
        mAfterInBlink = afterInBlink;
    }

    public CommandRules.InColor getAfterInColor() {
        return mAfterInColor;
    }

    public void setAfterInColor(CommandRules.InColor afterInColor) {
        mAfterInColor = afterInColor;
    }

    public CommandRules.BuzzerModel getAfterBuzzerModel() {
        return mAfterBuzzerModel;
    }

    public void setAfterBuzzerModel(CommandRules.BuzzerModel afterBuzzerModel) {
        mAfterBuzzerModel = afterBuzzerModel;
    }

    public CommandRules.OrderType getOrderType() {
        return mOrderType;
    }

    public void setOrderType(CommandRules.OrderType orderType) {
        mOrderType = orderType;
    }

    public int getRopeName() {
        return mRopeName;
    }

    public void setRopeName(int ropeName) {
        mRopeName = ropeName;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getSpare() {
        return mSpare;
    }

    public void setSpare(int spare) {
        mSpare = spare;
    }

    @Override
    public String toString() {
        return "CommandNew{" +
                "mBeforeOutBlink=" + mBeforeOutBlink +
                ", mBeforeOutColor=" + mBeforeOutColor +
                ", mBeforeInBlink=" + mBeforeInBlink +
                ", mBeforeInColor=" + mBeforeInColor +
                ", mBeforeBuzzerModel=" + mBeforeBuzzerModel +
                ", mInfraredInduction=" + mInfraredInduction +
                ", mInfraredHeight=" + mInfraredHeight +
                ", mInfraredEmission=" + mInfraredEmission +
                ", mInfraredModel=" + mInfraredModel +
                ", mVibrationInduced=" + mVibrationInduced +
                ", mVibrationStrength=" + mVibrationStrength +
                ", mVibrationDetails=" + mVibrationDetails +
                ", mAfterOutBlink=" + mAfterOutBlink +
                ", mAfterOutColor=" + mAfterOutColor +
                ", mAfterInBlink=" + mAfterInBlink +
                ", mAfterInColor=" + mAfterInColor +
                ", mAfterBuzzerModel=" + mAfterBuzzerModel +
                ", mOrderType=" + mOrderType +
                ", mRopeName=" + mRopeName +
                ", mTime=" + mTime +
                ", mCount=" + mCount +
                ", mSpare=" + mSpare +
                '}';
    }
}
