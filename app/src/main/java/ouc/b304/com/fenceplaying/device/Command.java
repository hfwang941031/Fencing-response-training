package ouc.b304.com.fenceplaying.device;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;

import ouc.b304.com.fenceplaying.utils.SharedPreferencesUtils;

/**
 * @author 王海峰 on 2018/11/15 09:54
 */

/**
 * Description：命令的集合，灯的高度，颜色等设置集合
 * 在每个activity里面实例化赋值即可，采用set赋值，get取值Order
 */

public class Command implements Serializable {
    //灯的编号
    private char id;
    //灯的短地址
    private String shortAddress;
    //灯的高度 默认30cm
    private Order.LightsHight lightshigh = Order.LightsHight.HIGHT_30C;
    //灯的颜色 默认蓝色
    private Order.LightColor color = Order.LightColor.BLUE;
    //开灯响 默认不响
    private Order.VoiceMode voice = Order.VoiceMode.NONE;
    //闪烁模式 默认不闪
    private Order.BlinkModel blinkModel = Order.BlinkModel.NONE;
    //灯光模式 默认外圈
    private Order.LightModel lightModel = Order.LightModel.OUTER;
    //感应模式 默认红外
    private Order.ActionModel actionModel = Order.ActionModel.LIGHT;
    //亮灯模式 默认正常点亮
    private Order.LightsUpModel lightsUpModel = Order.LightsUpModel.NORMAL_LIGHT;
    //红外模式 默认关闭
    private Order.Infrared_emission infrared_emission = Order.Infrared_emission.CLOSE;
    //振动模式 默认关闭
    private Order.Vibration_details vibration_details = Order.Vibration_details.NONE;
    //关灯时响
    private Order.VoiceTime endVoice = Order.VoiceTime.ON;

    public void initCommand(Context context) {
        String spLightsHigh = (String) SharedPreferencesUtils.getParam(context, "lightshigh", "");
        String spColor = (String) SharedPreferencesUtils.getParam(context, "color", "");
        String spVoice = (String) SharedPreferencesUtils.getParam(context, "voice", "");
        String spBlinkModel = (String) SharedPreferencesUtils.getParam(context, "blinkModel", "");
        String spLightModel = (String) SharedPreferencesUtils.getParam(context, "lightModel", "");
        String spActionModel = (String) SharedPreferencesUtils.getParam(context, "actionModel", "");
        String spLightsUpModel = (String) SharedPreferencesUtils.getParam(context, "lightsUpModel", "");
        String spInfrared_emission = (String) SharedPreferencesUtils.getParam(context, "infrared_emission", "");
        String spVibration_details = (String) SharedPreferencesUtils.getParam(context, "vibration_details", "");
        String spEndVoice = (String) SharedPreferencesUtils.getParam(context, "endVoice", "");

        if (spLightsHigh != null && spLightsHigh.length() > 0) {
            Log.d("sharedpreferences", "lightsHight" + spLightsHigh);
            switch (spLightsHigh) {
                case "5cm":
                    this.lightshigh = Order.LightsHight.HIGHT_5C;
                    break;
                case "30cm":
                    this.lightshigh = Order.LightsHight.HIGHT_30C;
                    break;
                case "60cm":
                    this.lightshigh = Order.LightsHight.HIGHT_60C;
                    break;
                default:
                    break;
            }
        }

        if (spColor != null && spColor.length() > 0) {
            Log.d("sharedpreferences", "spColor" + spColor);
            switch (spColor) {
                case "none":
                    this.color = Order.LightColor.NONE;
                    break;
                case "red":
                    this.color = Order.LightColor.RED;
                    break;
                case "blue":
                    this.color = Order.LightColor.BLUE;
                    break;
                case "green":
                    this.color = Order.LightColor.GREEN;
                    break;
                case "yellow":
                    this.color = Order.LightColor.RED_GREEN;
                    break;
                case "ching":
                    this.color = Order.LightColor.BLUE_GREEN;
                    break;
                case "purple":
                    this.color = Order.LightColor.BLUE_RED;
                    break;
                case "white":
                    this.color = Order.LightColor.BLUE_RED_GREEN;
                    break;
            }
        }

        if (spVoice != null && spVoice.length() > 0) {
            Log.d("sharedpreferences", "spVoice" + spVoice);
            switch (spVoice) {
                case "none":
                    this.voice = Order.VoiceMode.NONE;
                    break;
                case "short":
                    this.voice = Order.VoiceMode.SHORT;
                    break;
                case "one":
                    this.voice = Order.VoiceMode.ONE;
                    break;
                case "two":
                    this.voice = Order.VoiceMode.TWO;
                    break;
            }
        }

        if (spBlinkModel != null && spBlinkModel.length() > 0) {
            Log.d("sharedpreferences", "spBlinkModel" + spBlinkModel);
            switch (spBlinkModel) {
                case "none":
                    this.blinkModel = Order.BlinkModel.NONE;
                    break;
                case "fast":
                    this.blinkModel = Order.BlinkModel.FAST;
                    break;
                case "slow":
                    this.blinkModel = Order.BlinkModel.SLOW;
                    break;
                case "reset":
                    this.blinkModel = Order.BlinkModel.RESET;
                    break;
                default:
                    break;
            }
        }

        if (spLightModel != null && spLightModel.length() > 0) {
            Log.d("sharedpreferences", "spLightModel" + spLightModel);
            switch (spLightModel) {
                case "outer":
                    this.lightModel = Order.LightModel.OUTER;
                    break;
                case "center":
                    this.lightModel = Order.LightModel.CENTER;
                    break;
                case "all":
                    this.lightModel = Order.LightModel.ALL;
                    break;
            }
        }

        if (spActionModel != null && spActionModel.length() > 0) {
            Log.d("sharedpreferences", "spActionModel" + spActionModel);
            switch (spActionModel) {
                case "none":
                    this.actionModel = Order.ActionModel.NONE;
                    break;
                case "light":
                    this.actionModel = Order.ActionModel.LIGHT;
                    break;
                case "touch":
                    this.actionModel = Order.ActionModel.TOUCH;
                    break;
                case "all":
                    this.actionModel = Order.ActionModel.ALL;
                    break;
                case "q_touch":
                    this.actionModel = Order.ActionModel.Q_TOUCH;
                    break;
                case "q_touch_light":
                    this.actionModel = Order.ActionModel.Q_TOUCH_LIGHT;
                    break;
                case "z_touch":
                    this.actionModel = Order.ActionModel.Z_TOUCH;
                    break;
                case "z_touch_light":
                    this.actionModel = Order.ActionModel.Z_TOUCH_LIGHT;
                    break;
            }
        }

        if (spLightsUpModel != null && spLightModel.length() > 0) {
            Log.d("sharedpreferences", "spLightsUpModel" + spLightsUpModel);
            switch (spLightsUpModel) {
                case "normal_light":
                    this.lightsUpModel = Order.LightsUpModel.NORMAL_LIGHT;
                    break;
                case "add_light":
                    this.lightsUpModel = Order.LightsUpModel.ADD_LIGHT;
                    break;
            }
        }

        if (spInfrared_emission != null && spInfrared_emission.length() > 0) {
            Log.d("sharedpreferences", "spInfrared_emission" + spInfrared_emission);
            switch (spInfrared_emission) {
                case "close":
                    this.infrared_emission = Order.Infrared_emission.CLOSE;
                    break;
                case "open":
                    this.infrared_emission = Order.Infrared_emission.OPEN;
                    break;

            }
        }

        if (spVibration_details != null && spVibration_details.length() > 0) {
            Log.d("sharedpreferences", "spVibration_details" + spVibration_details);
            switch (spVibration_details) {
                case "none":
                    this.vibration_details = Order.Vibration_details.NONE;
                    break;
                case "need":
                    this.vibration_details = Order.Vibration_details.NEED;
                    break;

            }
        }

        if (spEndVoice != null && spEndVoice.length() > 0) {
            Log.d("sharedpreferences", "spEndVoice" + spEndVoice);
            switch (spEndVoice) {
                case "on":
                    this.endVoice = Order.VoiceTime.ON;
                    break;
                case "off":
                    this.endVoice = Order.VoiceTime.OFF;
                    break;
            }
        }
    }

    public void setId(char id) {
        this.id = id;
    }

    public char getId() {
        return id;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    public Order.LightsHight getLightshigh() {
        return lightshigh;
    }

    public Order.VoiceTime getEndVoice() {
        return endVoice;
    }

    public void setEndVoice(Order.VoiceTime endVoice) {
        this.endVoice = endVoice;
    }

    public void setLightshigh(Order.LightsHight lightshigh) {
        this.lightshigh = lightshigh;
    }

    public void setColor(Order.LightColor color) {
        this.color = color;
    }

    public void setVoice(Order.VoiceMode voice) {
        this.voice = voice;
    }

    public void setBlinkModel(Order.BlinkModel blinkModel) {
        this.blinkModel = blinkModel;
    }

    public void setLightModel(Order.LightModel lightModel) {
        this.lightModel = lightModel;
    }

    public void setActionModel(Order.ActionModel actionModel) {
        this.actionModel = actionModel;
    }

    public void setLightsUpModel(Order.LightsUpModel lightsUpModel) {
        this.lightsUpModel = lightsUpModel;
    }

    public void setInfrared_emission(Order.Infrared_emission infrared_emission) {
        this.infrared_emission = infrared_emission;
    }

    public void setVibration_details(Order.Vibration_details vibration_details) {
        this.vibration_details = vibration_details;
    }

    public void setVoiceTime(Order.VoiceTime endVoice) {
        this.endVoice = endVoice;
    }

    public Order.LightsHight getLighthigh() {
        return lightshigh;
    }

    public Order.LightColor getColor() {
        return color;
    }

    public Order.VoiceMode getVoice() {
        return voice;
    }

    public Order.BlinkModel getBlinkModel() {
        return blinkModel;
    }

    public Order.LightModel getLightModel() {
        return lightModel;
    }

    public Order.ActionModel getActionModel() {
        return actionModel;
    }

    public Order.LightsUpModel getLightsUpModel() {
        return lightsUpModel;
    }

    public Order.Infrared_emission getInfrared_emission() {
        return infrared_emission;
    }

    public Order.Vibration_details getVibration_details() {
        return vibration_details;
    }

    public Order.VoiceTime getVoiceTime() {
        return endVoice;
    }

    @Override
    public String toString() {
        return "Command{" +
                "id=" + id +
                ", shortAddress='" + shortAddress + '\'' +
                ", lightshigh=" + lightshigh +
                ", color=" + color +
                ", voice=" + voice +
                ", blinkModel=" + blinkModel +
                ", lightModel=" + lightModel +
                ", actionModel=" + actionModel +
                ", lightsUpModel=" + lightsUpModel +
                ", infrared_emission=" + infrared_emission +
                ", vibration_details=" + vibration_details +
                ", endVoice=" + endVoice +
                '}';
    }
}

