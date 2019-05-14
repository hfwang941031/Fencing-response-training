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
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import ouc.b304.com.fenceplaying.Bean.Constant;
import ouc.b304.com.fenceplaying.Bean.DeviceInfo;
import ouc.b304.com.fenceplaying.Bean.PowerInfoComparetor;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.activity.newVersion.BaseActivity;
import ouc.b304.com.fenceplaying.activity.newVersion.Test;
import ouc.b304.com.fenceplaying.adapter.PowerAdapter;
import ouc.b304.com.fenceplaying.device.Device;
import ouc.b304.com.fenceplaying.dialog.CusProgressDialog;
import ouc.b304.com.fenceplaying.thread.AutoCheckPower;
import ouc.b304.com.fenceplaying.thread.Timer;
import ouc.b304.com.fenceplaying.utils.DataAnalyzeUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.SafLightReceiver;

import static ouc.b304.com.fenceplaying.thread.ReceiveThread.POWER_RECEIVE_THREAD;

/**
 * @author 王海峰 on 2019/1/9 16:31
 */
public class NewMainActivity extends Activity {
    @BindView(R.id.img_icon_playerinfo)
    ImageView imgIconPlayerinfo;
    @BindView(R.id.tv_manageplayinfo)
    TextView tvManageplayinfo;
    @BindView(R.id.img_icon_train)
    ImageView imgIconTrain;
    @BindView(R.id.tv_train)
    TextView tvTrain;
    @BindView(R.id.img_icon_historydata)
    ImageView imgIconHistorydata;
    @BindView(R.id.tv_historydata)
    TextView tvHistorydata;
    @BindView(R.id.img_icon_setting)
    ImageView imgIconSetting;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.rl_playerinfo)
    RelativeLayout rlPlayerinfo;
    @BindView(R.id.rl_train)
    RelativeLayout rlTrain;
    @BindView(R.id.rl_histroydata)
    RelativeLayout rlHistroydata;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.img_btn_initdevice)
    ImageButton imgBtnInitdevice;
    @BindView(R.id.lv_battery)
    ListView lvBattery;
    /*@BindView(R.id.name)
    TextView name;*/
    private Context context;
    private Device device;
    private Boolean isLeave = false;
    private PowerAdapter powerAdapter;
    private final int POWER_RECEIVE = 2;
    private AutoCheckPower checkPowerThread;
    private static final String TAG = "NewMainActivity";


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
    /*新版本增加的字段*/
    private Realm mRealm;
    private ExecutorService mExecutorService;
    private SafLightReceiver mSafLightReceiver;
    /**/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmain);
        ButterKnife.bind(this);
        context = this.getApplicationContext();
        powerAdapter = new PowerAdapter(context);
        device = new Device(this);
        Log.d(TAG, "---->onCreate");
        /*//设置全局广播监听--->USB插拔
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.setPriority(500);
        this.registerReceiver(mUsbReceiver, filter);*/
        /*initDevice();*/
        initView();

        /*新版本*/
        mExecutorService = new ScheduledThreadPoolExecutor(2);
        mRealm = Realm.getDefaultInstance();
        //注册广播
        mSafLightReceiver = new SafLightReceiver(mRealm);
        mSafLightReceiver.usbRegisterReceiver(context);
        /*  */
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isLeave = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLeave = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isLeave = true;
        device.disconnect();
        if (checkPowerThread != null) {
            checkPowerThread.setPowerFlag(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (checkPowerThread != null) {
            checkPowerThread.interrupt();
        }*/
        mSafLightReceiver.usbUnregisterReceiver(context);

    }

    @OnClick({R.id.rl_playerinfo, R.id.rl_train, R.id.rl_histroydata, R.id.rl_setting, R.id.img_btn_initdevice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_playerinfo:
                startActivity(new Intent(context, InfoSelectActivity.class));
                break;
            case R.id.rl_train:
                startActivity(new Intent(context, TrainTypeSelectActivity.class));

                break;
            case R.id.rl_histroydata:
                startActivity(new Intent(context, DataShowSelect.class));

                break;
            case R.id.rl_setting:
                startActivity(new Intent(context, SettingActivity.class));

                break;
            case R.id.img_btn_initdevice:
                startActivity(new Intent(context, Test.class));
                break;
        }
    }

    /*private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String TAG = "FragL";
            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                Log.i(TAG, "DETACHED...");
                notifyUSBDeviceDetach();
            }
        }
    };*/

    /*拔出usb设备后 直接断开连接*/
    public void notifyUSBDeviceDetach() {
        Toast.makeText(context, "设备已拔出，即将断开连接·······", Toast.LENGTH_LONG).show();
        device.disconnect();
        powerAdapter.notifyDataSetChanged();
    }

    private void initView() {
        //设置电量listview的适配器
        lvBattery.setAdapter(powerAdapter);
    }

    /*初始化串口*/
    public void initDevice() {
        device.createDeviceList(context);
        // 判断是否插入协调器，
        if (device.devCount > 0) {
            device.connect(this);
            device.initConfig();
            Timer.sleep(200);
            checkPowerThread = new AutoCheckPower(context, device, POWER_RECEIVE_THREAD, handler);
            checkPowerThread.start();
        } else {
            // 未检测到协调器
            AlertDialog.Builder builder = new AlertDialog.Builder(NewMainActivity.this);
            builder.setTitle("温馨提示");
            builder.setMessage("未检测到协调器，请先连接协调器！\n");
            builder.setNegativeButton("这就去连接", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertdialog = builder.create();
            alertdialog.show();
        }


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
        //获取电量信息
        Device.DEVICE_LIST.addAll(powerInfos);
        powerAdapter.notifyDataSetChanged();
        setPowerFlag();
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


}
