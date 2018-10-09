package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.Bean.Constant;
import ouc.b304.com.fenceplaying.Bean.DeviceInfo;
import ouc.b304.com.fenceplaying.Bean.PowerInfoComparetor;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.PowerAdapter;
import ouc.b304.com.fenceplaying.device.Device;
import ouc.b304.com.fenceplaying.thread.AutoCheckPower;
import ouc.b304.com.fenceplaying.thread.Timer;
import ouc.b304.com.fenceplaying.utils.DataAnalyzeUtils;

import static ouc.b304.com.fenceplaying.thread.ReceiveThread.POWER_RECEIVE_THREAD;

public class MainActivity extends Activity {

    @BindView(R.id.ll_leftmain)
    LinearLayout llLeftmain;
    @BindView(R.id.btn_playerInfo)
    Button btnPlayerInfo;
    @BindView(R.id.btn_progrgmSetting)
    Button btnProgrgmSetting;
    @BindView(R.id.btn_randomProgram)
    Button btnRandomProgram;
    @BindView(R.id.btn_historyData)
    Button btnHistoryData;
    @BindView(R.id.btn_Setting)
    Button btnSetting;
    @BindView(R.id.ll_rightmain)
    LinearLayout llRightmain;
    @BindView(R.id.lv_battery)
    ListView lvBattery;
    @BindView(R.id.btn_responsetraining)
    Button btnResponsetraining;
    private Device device;
    private Context context;
    private Boolean isLeave = false;
    private PowerAdapter powerAdapter;
    private final int POWER_RECEIVE = 2;
    private AutoCheckPower checkPowerThread;
    private static final String TAG = "MainActivity";

    /*onCreate方法*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.context = getApplicationContext();
        powerAdapter = new PowerAdapter(context);
        device = new Device(this);
        initView();
        Log.d(TAG, "---->onCreate");
        //设置全局广播监听--->USB插拔
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.setPriority(500);
        this.registerReceiver(mUsbReceiver, filter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "---->onResume");
        initDevice();
        isLeave = false;
        if (checkPowerThread == null) {
            Log.d(TAG, "checkPowerThread==null");
            checkPowerThread = new AutoCheckPower(context, device, POWER_RECEIVE_THREAD, handler);
            checkPowerThread.start();
        } else
            Log.d(TAG, "checkPowerThread!=null");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "---->onPause");
        isLeave = true;
        device.disconnect();
       /* if (checkPowerThread != null) {
            checkPowerThread.interrupt();
        }*/

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "----->onReStart");

        isLeave = false;
       /* if (checkPowerThread == null)
        {
            Log.d(TAG,"checkPowerThread==null");
            checkPowerThread=new AutoCheckPower();
            checkPowerThread.start();
        }
        else
            Log.d(TAG,"checkPowerThread!=null");   */
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //device.disconnect();
        Log.d(TAG, "----->onDestroy");
        if (checkPowerThread != null) {
            checkPowerThread.interrupt();
        }
        this.unregisterReceiver(mUsbReceiver);
    }

    @OnClick({R.id.btn_playerInfo, R.id.btn_progrgmSetting, R.id.btn_responsetraining, R.id.btn_randomProgram, R.id.btn_historyData, R.id.btn_Setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_playerInfo:
                startActivity(new Intent(context, PlayerShowActivity.class));
                break;
            case R.id.btn_progrgmSetting:
                break;
            case R.id.btn_responsetraining:
                startActivity(new Intent(context, ResponseSelect.class));

                break;
            case R.id.btn_randomProgram:
                break;
            case R.id.btn_historyData:
                break;
            case R.id.btn_Setting:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                break;
        }
    }


    /*初始化串口*/
    public void initDevice() {
        device.createDeviceList(context);
        // 判断是否插入协调器，
        if (device.devCount > 0) {
            device.connect(this);
            device.initConfig();
            Timer.sleep(200);
        } else {
            // 未检测到协调器
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("温馨提示");
            builder.setMessage("                       未检测到协调器，请先插入协调器！\n");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertdialog = builder.create();
            alertdialog.show();
        }


    }


    /*拔出usb设备后 直接断开连接*/
    public void notifyUSBDeviceDetach() {
        Toast.makeText(context, "设备已拔出，即将断开连接·······", Toast.LENGTH_SHORT).show();
        device.disconnect();
    }


    //读取电量信息
    private void readPowerData(final String data) {
        if (isLeave)
            return;
        List<DeviceInfo> powerInfos = DataAnalyzeUtils.analyzePowerData(data);
        Collections.sort(powerInfos, new PowerInfoComparetor());
        //清空电量列表
        Log.d(Constant.LOG_TAG, "清空列表");
        Device.DEVICE_LIST.clear();
        //tvDeviceCount.setText("共0个设备");
        //获取电量信息
        Device.DEVICE_LIST.addAll(powerInfos);
        powerAdapter.notifyDataSetChanged();
        setPowerFlag();

        /*tvDeviceCount.setText("共" + Math.min(12, powerInfos.size()) + "个");*/
        Log.i("AAA", powerInfos.size() + "");
    }


    private void setPowerFlag() {
        SharedPreferences sharedPreferences = getSharedPreferences("response", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < Device.DEVICE_LIST.size(); i++) {
            switch (String.valueOf(Device.DEVICE_LIST.get(i).getDeviceNum())) {
                case "A":
                    editor.putBoolean("flag1", true);
                    editor.commit();
                    break;
                case "B":
                    editor.putBoolean("flag2", true);
                    editor.commit();
                    break;
                case "C":
                    editor.putBoolean("flag3", true);
                    editor.commit();
                    break;
                case "D":
                    editor.putBoolean("flag4", true);
                    editor.commit();
                    break;
                case "E":
                    editor.putBoolean("flag5", true);
                    editor.commit();
                    break;
                case "F":
                    editor.putBoolean("flag6", true);
                    editor.commit();
                    break;

            }
        }
    }


    public void SetPowerFlagGone() {
        SharedPreferences sharedPreferences = getSharedPreferences("response",
                0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("flag1", false);
        editor.putBoolean("flag2", false);
        editor.putBoolean("flag3", false);
        editor.putBoolean("flag4", false);
        editor.putBoolean("flag5", false);
        editor.putBoolean("flag6", false);
        editor.commit();
    }


    public void initView() {

        lvBattery.setAdapter(powerAdapter);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case POWER_RECEIVE:
                    String data = msg.obj.toString();
                    readPowerData(data);
                    break;
            }
        }
    };

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String TAG = "FragL";
            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                Log.i(TAG, "DETACHED...");
                notifyUSBDeviceDetach();
            }
        }
    };


}
