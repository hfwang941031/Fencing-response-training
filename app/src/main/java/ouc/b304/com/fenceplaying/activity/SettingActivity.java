package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.Bean.DeviceInfo;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.device.Device;
import ouc.b304.com.fenceplaying.device.Order;
import ouc.b304.com.fenceplaying.thread.ReceiveThread;
import ouc.b304.com.fenceplaying.utils.DataAnalyzeUtils;
import ouc.b304.com.fenceplaying.utils.VersionUtils;

public class SettingActivity extends Activity {
    private Context settingContext;
    private Device device;
    private String panId;


    @BindView(R.id.bt_distance_cancel)
    ImageView btDistanceCancel;
    @BindView(R.id.layout_cancel)
    LinearLayout layoutCancel;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_help)
    ImageView imgHelp;
    @BindView(R.id.img_save)
    ImageView imgSave;
    @BindView(R.id.ll_setParam)
    LinearLayout llSetParam;
    @BindView(R.id.tv_default_device_num)
    TextView tvDefaultDeviceNum;
    @BindView(R.id.tv_edit_device_num)
    TextView tvEditDeviceNum;
    @BindView(R.id.tv_pan_id)
    TextView tvPanId;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.btn_turn_on_all_lights)
    Button btnTurnOnAllLights;
    @BindView(R.id.btn_turn_off_all_lights)
    Button btnTurnOffAllLights;
    @BindView(R.id.activity_setting)
    LinearLayout activitySetting;
    /*传递PANID*/
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    panId = DataAnalyzeUtils.analyzePAN_ID(msg.obj.toString());
                    tvPanId.setText("PAN_ID:" + panId);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        settingContext = getApplicationContext();
        device = new Device(settingContext);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        device.createDeviceList(this);
        // 判断是否插入协调器，
        if (device.devCount > 0) {
            device.connect(this);
            device.initConfig();
            device.getControllerPAN_ID();
            //开启接收panid信息的线程
            new ReceiveThread(handler, device.ftDev, ReceiveThread.PAN_ID_THREAD, 1).start();

        } else {
            tvPanId.setText("PAN_ID:当前未插入协调器");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        device.disconnect();
    }

    public void initView() {
        tvTitle.setText("设置");
        tvDefaultDeviceNum.setText("" + Math.min(12, Device.DEVICE_LIST.size()));
        tvVersion.setText("当前版本" + VersionUtils.getVerCode(settingContext));
    }

    @OnClick({R.id.ll_setParam, R.id.ll_about, R.id.btn_turn_on_all_lights, R.id.btn_turn_off_all_lights})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_setParam:
                break;
            case R.id.ll_about:
                break;
            case R.id.btn_turn_on_all_lights:
                Log.d("Button", "Turn on all the light has been clicked");
                for (DeviceInfo info : Device.DEVICE_LIST)
                {
                    device.sendOrder(info.getDeviceNum(),
                            Order.LightColor.BLUE,
                            Order.VoiceMode.NONE,
                            Order.BlinkModel.NONE,
                            Order.LightModel.OUTER,
                            Order.ActionModel.NONE,
                            Order.EndVoice.NONE);
                }
                break;
            case R.id.btn_turn_off_all_lights:
                device.turnOffAllTheLight();
                break;
            case R.id.layout_cancel:
                this.finish();
                break;
        }
    }

}
