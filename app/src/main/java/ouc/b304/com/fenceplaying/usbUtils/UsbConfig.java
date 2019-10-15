package ouc.b304.com.fenceplaying.usbUtils;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import ouc.b304.com.fenceplaying.service.ReadDataService;
import ouc.b304.com.fenceplaying.usbUtils.usb_interface.UsbSerialDriver;
import ouc.b304.com.fenceplaying.usbUtils.usb_interface.UsbSerialPort;
import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;
import ouc.b304.com.fenceplaying.utils.newUtils.Utils;


/**
 * UsbConfig
 *
 * @author Skx
 * @date 2018/11/19
 */
public class UsbConfig {
    private Context mContext;
    private UsbManager mUsbManager;
    private UsbDevice mUsbDevice;
    private UsbInterface mUsbInterface;
    private UsbEndpoint usbEndpointOut, usbEndpointIn;
    private UsbSerialPort mUsbSerialPort = null;
    private PendingIntent mPendingIntent;
    private UsbDeviceConnection mConnection;
    private static final Object LOCK_READ = new Object();
    private static UsbConfig instance;

    public UsbConfig() {
    }

    public static UsbConfig getInstance() {
        if (instance == null) {
            synchronized (UsbConfig.class) {
                if (instance == null) {
                    instance = new UsbConfig();
                }
            }
        }
        return instance;
    }

    public void init(Application context) {
        Log.i("wj","init");
        mContext = context;
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        mPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(AppConfig.ACTION_USB_PERMISSION), 0);
        initUsbControl();
    }

    public void initUsbControl() {
        Log.i("wj","initUsbControl");
        //全部设备
        List<UsbSerialDriver> usbSerialDrivers = UsbSerialProbe.getDefaultProbe().findAllDrivers(mUsbManager);
        //全部端口
        List<UsbSerialPort> usbSerialPorts = new ArrayList<>();
        for (UsbSerialDriver driver : usbSerialDrivers) {
            List<UsbSerialPort> ports = driver.getPorts();
            usbSerialPorts.addAll(ports);
        }

        //校验设备，设备是 Ft230x设备
        for (UsbSerialPort port : usbSerialPorts) {
            UsbSerialDriver driver = port.getDriver();
            UsbDevice usbDevice = driver.getDevice();
            mUsbDevice = usbDevice;
            mUsbSerialPort = port;
        }
        if (mUsbDevice != null && mUsbSerialPort != null) {
            if (!mUsbManager.hasPermission(mUsbDevice)) {
                mUsbManager.requestPermission(mUsbDevice, mPendingIntent);
            } else {
                configPort();
            }
        } else {
            Toast.makeText(mContext, "未插入协调器！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 配置串口信息
     */
    public void configPort() {
        Log.i("wj","configPort");
        //成功获取端口，打开连接
        mConnection = mUsbManager.openDevice(mUsbDevice);
        if (mConnection == null) {
            Toast.makeText(mContext, "端口打开失败！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            mUsbSerialPort.open(mConnection);
            //设置串口信息                         波特率                       数据位                     停止位                     校验位
            mUsbSerialPort.setParameters(Utils.getBaudRate(), UsbSerialPort.DATABITS_8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_SPACE);
            findInterfaceAndEpt();
        } catch (IOException e) {
            //打开端口失败，关闭！
            try {
                mUsbSerialPort.close();
                Toast.makeText(mContext, "端口初始化失败！", Toast.LENGTH_SHORT).show();
            } catch (IOException e2) {
            }

            return;
        }
    }

    /**
     * 寻找接口和分配节点
     */
    public void findInterfaceAndEpt() {
        Log.i("wj","findInterfaceAndEpt");
        // 获取设备接口，一般都是一个接口，你可以打印getInterfaceCount()方法查看接
        mUsbInterface = mUsbDevice.getInterface(0);
        if (mUsbInterface == null) {
            Toast.makeText(mContext, "没有找到接口！", Toast.LENGTH_SHORT).show();
            return;
        }
        //声明接口，true可进行通信
        if (!mConnection.claimInterface(mUsbInterface, true)) {
            mConnection.close();
            return;
        }
        //用UsbDeviceConnection 与 UsbInterface 进行端点设置和通讯
        for (int i = 0; i < mUsbInterface.getEndpointCount(); ++i) {
            UsbEndpoint end = mUsbInterface.getEndpoint(i);
            if (end.getDirection() == UsbConstants.USB_DIR_IN) {
                usbEndpointIn = end;
            } else {
                usbEndpointOut = end;
            }
        }
        if (usbEndpointIn == null || usbEndpointOut == null) {
            return;
        }
        //开启服务
        Intent serviceIntent = new Intent(mContext, ReadDataService.class);
        mContext.startService(serviceIntent);
    }

    /**
     * 获取USB设备的UsbInterface
     */
    public UsbInterface get_Interface(Context context) {
        mUsbInterface = mUsbDevice.getInterface(0);
        if (mUsbInterface == null) {
            Toast.makeText(context, "can't get usbInterface", Toast.LENGTH_SHORT).show();
            return null;
        }
        return mUsbInterface;
    }

    /**
     * 插入usb
     *
     * @param usbDevice
     */
    public void insertUsbDevice(UsbDevice usbDevice) {
        UsbSerialDriver usbSerialDriver = UsbSerialProbe.getDefaultProbe().probeDevice(usbDevice);
        List<UsbSerialPort> ports = usbSerialDriver.getPorts();
        for (UsbSerialPort port : ports) {
            mUsbDevice = usbDevice;
            mUsbSerialPort = port;
            if (!mUsbManager.hasPermission(usbDevice)) {
                mUsbManager.requestPermission(usbDevice, mPendingIntent);
            } else {
                configPort();
            }
            break;
        }
    }

    /**
     * 拔出usb
     *
     * @param usbDevice
     */
    public void pullOutUsbDevice(UsbDevice usbDevice) {
        if (mConnection != null) {
            mConnection.releaseInterface(get_Interface(mContext));
            try {
                mUsbSerialPort.close();
                mConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(mContext, "协调器已拔除或松动！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 枚举设备
     */
    public void enumDevice() {
        //获取usb设备，失败直接return
        if (mUsbManager != null) {
            //通过对应设备，映射到每一个设备名，
            HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
            Iterator<UsbDevice> device = deviceList.values().iterator();
            while (device.hasNext()) {
                UsbDevice usbDevice = device.next();
                if (usbDevice.getVendorId() == 1027 && usbDevice.getProductId() == 24577) {
                    mUsbDevice = usbDevice;
                }
            }
        }
    }


    public int sendMessage(String order) {
        if (mConnection != null && usbEndpointOut != null) {
            byte[] bytes = Utils.hexStringToByte(order);
            Log.d("FinalOrder", order + "");
            return mConnection.bulkTransfer(usbEndpointOut, bytes, bytes.length, 10);
        }
        return -1;
    }

    public String readData() {
        synchronized (LOCK_READ) {
            String dataStr = "";
            if (mConnection != null && usbEndpointIn != null) {
                boolean isContinue = true;
                while (isContinue) {
                    byte[] data = new byte[512];
                    int len = mConnection.bulkTransfer(usbEndpointIn, data, data.length, 10);
                    if (len != -1) {
                        byte[] data2 = new byte[len];
                        System.arraycopy(data, 0, data2, 0, len);
                        dataStr += Utils.bytesToHexString(data2);
                    } else {
                        isContinue = false;
                    }
                }
            }
            return dataStr;
        }
    }

    /**
     * 关闭usb
     */
    public void closeUsb() {
        if (mConnection != null) {
            mConnection.releaseInterface(get_Interface(mContext));
            try {
                mUsbSerialPort.close();
                mConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
