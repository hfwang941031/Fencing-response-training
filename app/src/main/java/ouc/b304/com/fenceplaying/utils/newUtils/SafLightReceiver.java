package ouc.b304.com.fenceplaying.utils.newUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.widget.Toast;
import io.realm.Realm;
import ouc.b304.com.fenceplaying.usbUtils.UsbConfig;


/**
 * SafLightReceiver
 *
 * @author
 * @date 2018/10/31
 */
public class SafLightReceiver extends BroadcastReceiver {
    private Realm mRealm;

    public SafLightReceiver(Realm realm) {
        mRealm = realm;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case AppConfig.ACTION_ANALYSIS_DEVICE:
                synchronized (this){
                    getSaveLight();
                }
                break;
            case AppConfig.ACTION_USB_PERMISSION:
                synchronized (this) {
                    UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (usbDevice != null) {
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            UsbConfig.getInstance().configPort();
                        } else {
                            ToastUtils.showToast(context, "权限未授予！", Toast.LENGTH_SHORT);
                        }
                    }
                }
                break;
            case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                // 有新的设备插入了，在这里一般会判断这个设备是不是我们想要的，是的话就去请求权限
                synchronized (this) {
                    UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (usbDevice != null) {
                        UsbConfig.getInstance().insertUsbDevice(usbDevice);
                    }
                }
                break;
            case UsbManager.ACTION_USB_DEVICE_DETACHED:
                // 有设备拔出了
                synchronized (this) {
                    UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (usbDevice != null) {
                        UsbConfig.getInstance().pullOutUsbDevice(usbDevice);
                    }
                }
                break;
        }
    }
    /**
     * 获取保存的光标
     */
    private void getSaveLight() {
        RealmUtils.queryDevice(mRealm, AppConfig.sDevice.getPanId(), new RealmUtils.SetOnIsHaveListener() {
            @Override
            public void onIsHaveListener(Boolean flag) {
                if (!flag) {
                    RealmUtils.saveDevice(mRealm, AppConfig.sDevice, new RealmUtils.SetOnSaveListener() {
                        @Override
                        public void onSaveListener(Boolean flag) {
                            if (!flag) {
                                ToastUtils.showToast(Utils.getContext(), "协调器保存失败", Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
            }
        });

    }
    public void usbRegisterReceiver(Context context) {
        IntentFilter inflater = new IntentFilter();
        inflater.addAction(AppConfig.ACTION_USB_PERMISSION);
        inflater.addAction(AppConfig.ACTION_ANALYSIS_DEVICE);
        inflater.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        inflater.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        context.registerReceiver(this, inflater);
    }

    public void usbUnregisterReceiver(Context context) {
        context.unregisterReceiver(this);
    }

}
