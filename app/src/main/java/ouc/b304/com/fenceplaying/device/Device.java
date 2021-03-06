package ouc.b304.com.fenceplaying.device;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ftdi.j2xx.D2xxManager;
import com.ftdi.j2xx.FT_Device;

import java.util.ArrayList;
import java.util.List;

import ouc.b304.com.fenceplaying.Bean.Constant;
import ouc.b304.com.fenceplaying.Bean.DeviceInfo;
import ouc.b304.com.fenceplaying.thread.Timer;

public class Device {
    //设备灯列表
    public static List<DeviceInfo> DEVICE_LIST = new ArrayList<>();

    public FT_Device ftDev;
    private D2xxManager ftdid2xx;

    //设备数量
    public int devCount;
    //是否已经初始化
    private boolean isConfiged;
    private int index;

    /* local variables 设备参数 */
    int baudRate = 115200; /* baud rate */
    byte stopBit = 1; /* 1:1stop bits, 2:2 stop bits */
    byte dataBit = 8; /* 8:8bit, 7: 7bit */
    byte parity = 0; /* 0: none, 1: odd, 2: even, 3: mark, 4: space */

    public Device(Context context)
    {
        try
        {
            ftdid2xx = D2xxManager.getInstance(context);
        } catch (D2xxManager.D2xxException e)
        {
            e.printStackTrace();
        }
    }

    public Device(Context context, int devCount) {
        try {
            ftdid2xx = D2xxManager.getInstance(context);
            this.devCount = devCount;
        } catch (D2xxManager.D2xxException e) {
            e.printStackTrace();
        }
    }

    //检测设备
    public boolean checkDevice(Context context)
    {
        if (devCount <= 0)
        {
            Toast.makeText(context, "还未插入协调器,请插入协调器!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (DEVICE_LIST.isEmpty() || DEVICE_LIST.size() == 0)
        {
            Toast.makeText(context, "未检测到任何设备,请开启设备!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else Toast.makeText(context,"检测到设备插入",Toast.LENGTH_SHORT).show();
        return true;
    }

    // 关闭activity时调用该方法
    public void disconnect()
    {
        devCount = -1;
        //currentIndex = -1;
        try
        {
            Thread.sleep(50);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        if (ftDev != null)
        {
            synchronized (ftDev)
            {
                if (true == ftDev.isOpen())
                {
                    ftDev.close();
                }
            }
        }
        Log.d("Method", "断开连接方法被启用");
    }

    public void initConfig()
    {
        if (ftDev == null || !ftDev.isOpen())
        {
            Log.e("j2xx", "SetConfig: device not open");
            return;
        }
        ftDev.setBitMode((byte) 0, D2xxManager.FT_BITMODE_RESET);
        ftDev.setBaudRate(baudRate);
        dataBit = D2xxManager.FT_DATA_BITS_8;
        stopBit = D2xxManager.FT_STOP_BITS_1;
        parity = D2xxManager.FT_PARITY_NONE;
        ftDev.setDataCharacteristics(dataBit, stopBit, parity);
        short flowCtrlSetting;
        flowCtrlSetting = D2xxManager.FT_FLOW_NONE;
        ftDev.setFlowControl(flowCtrlSetting, (byte) 0x0b, (byte) 0x0d);
        isConfiged = true;
    }

    //连接
    public void connect(Context context)
    {
        index = 0;
        if (null == ftDev)
        {
            ftDev = ftdid2xx.openByIndex(context, index);
        } else
        {
            synchronized (ftDev)
            {
                ftDev = ftdid2xx.openByIndex(context, index);
            }
        }
        isConfiged = false;
        if (ftDev == null)
            Log.i("AAAA", "ftDev==null");
    }

    // 更新连接设备列表，当重新打开程序或是熄灭屏幕之后重新打开都会执行此方法，应该次列表的设备数量一般情况下为1
    public void createDeviceList(Context context)
    {
        // 获取D2XX设备的数量，可以使用这个函数来确定连接到系统上的设备的数量
        int tempDevCount = ftdid2xx.createDeviceInfoList(context);
        if (tempDevCount > 0)
        {
            if (devCount != tempDevCount)
            {
                devCount = tempDevCount;
            }
        } else
        {
            devCount = -1;
            index = -1;
        }
        Log.d("devCount", "value:" + DEVICE_LIST.size());
    }

    /**
     * 区全部设备信息 电量、编号、短地址
     */
    public void sendGetDeviceInfo()
    {
        Log.d(Constant.LOG_TAG, " send get All DeviceInfo Order!");
        // 获取全部设备电量指令
        String data = "+04a";
        sendMessage(data);
    }

    //修改灯的PAN_ID 和 num
    public void changeLightPANID(String PAN_ID, char num, String address)
    {
        String order = "+14*" + PAN_ID + num + address;

        Log.d(Constant.LOG_TAG, "order:" + order);
        sendMessage(order);
    }

    //修改协调器PANID
    public void changeControllerPANID(String PAN_ID)
    {
        sendMessage("+08b" + PAN_ID);
    }

    public void getControllerPAN_ID()
    {
        sendMessage("+08c");
    }
/*打开全部灯*/

    public void turnOnAllTheLight() {
        for (DeviceInfo info : Device.DEVICE_LIST)
        {
            sendOrder(info.getDeviceNum(),
                    Order.LightColor.BLUE,
                    Order.VoiceMode.NONE,
                    Order.BlinkModel.NONE,
                    Order.LightModel.OUTER,
                    Order.ActionModel.NONE,
                    Order.EndVoice.NONE);
        }
    }




/*关闭全部灯*/
    public void turnOffAllTheLight()
    {
        //关闭全部灯
        if (devCount > 0)
        {
            for (DeviceInfo info : Device.DEVICE_LIST)
            {
                sendOrder(info.getDeviceNum(),
                        Order.LightColor.NONE,
                        Order.VoiceMode.NONE,
                        Order.BlinkModel.NONE,
                        Order.LightModel.TURN_OFF,
                        Order.ActionModel.NONE,
                        Order.EndVoice.NONE);
            }
        }
    }

    public void sendOrder(char lightId, Order.LightColor color, Order.VoiceMode voiceMode, Order.BlinkModel blinkModel,
                          Order.LightModel lightModel, Order.ActionModel actionModel, Order.EndVoice endVoice)
    {
        String order = Order.getOrder(lightId, color, voiceMode, blinkModel, lightModel, actionModel, endVoice);
        if (order.equals(""))
            return;
        sendMessage(order);
    }

    private synchronized void sendMessage(String data)
    {
        Timer.sleep(20);
        Log.d(Constant.LOG_TAG, "send message:" + data);

        if (ftDev.isOpen() == false)
        {
            Log.e("j2xx", "SendMessage: device not open");
            return;
        }
        ftDev.setLatencyTimer((byte) 128);
        byte[] OutData = data.getBytes();
        ftDev.write(OutData, data.length());
    }
}
