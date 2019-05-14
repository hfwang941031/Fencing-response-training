package ouc.b304.com.fenceplaying.activity.newVersion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.LightOperateAdapter;
import ouc.b304.com.fenceplaying.dialog.CusProgressDialog;
import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.entity.TimeInfo;
import ouc.b304.com.fenceplaying.order.CommandNew;
import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;

/**
 * //                       _oo0oo_
 * //                      o8888888o
 * //                      88" . "88
 * //                      (| -_- |)
 * //                      0\  =  /0
 * //                    ___/`---'\___
 * //                  .' \\|     |// '.
 * //                 / \\|||  :  |||// \
 * //                / _||||| -:- |||||- \
 * //               |   | \\\  -  /// |   |
 * //               | \_|  ''\---/''  |_/ |
 * //               \  .-\__  '-'  ___/-. /
 * //             ___'. .'  /--.--\  `. .'___
 * //          ."" '<  `.___\_<|>_/___.' >' "".
 * //         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * //         \  \ `_.   \_ __\ /__ _/   .-` /  /
 * //     =====`-.____`.___ \_____/___.-`___.-'=====
 * //                       `=---='
 * //
 * //
 * //     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * //
 * //               佛祖保佑         永无BUG
 * //
 * //
 * //
 *
 * @author 王海峰 on 2019/5/13 16:36
 */
public class NewSingleSpotActivity extends BaseActivity {
    private static final String TAG = "NewSingleSpotActivity";
    @BindView(R.id.bt_run_cancel)
    ImageView btRunCancel;
    @BindView(R.id.layout_cancel)
    LinearLayout layoutCancel;
    @BindView(R.id.tv_devicenumber)
    TextView tvDevicenumber;
    @BindView(R.id.sp_devices)
    Spinner spDevices;
    @BindView(R.id.btn_turnon)
    Button btnTurnon;
    @BindView(R.id.sp_times)
    Spinner spTimes;
    @BindView(R.id.btn_turnoff)
    Button btnTurnoff;
    @BindView(R.id.tv_avalibledevices)
    TextView tvAvalibledevices;
    @BindView(R.id.tv_devicenumbers)
    TextView tvDevicenumbers;
    @BindView(R.id.img_btn_refresh)
    ImageButton imgBtnRefresh;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.paraset)
    TextView paraset;
    @BindView(R.id.tv_actionmode)
    TextView tvActionmode;
    @BindView(R.id.rb_touch)
    RadioButton rbTouch;
    @BindView(R.id.rd_redline)
    RadioButton rdRedline;
    @BindView(R.id.rb_touchandredline)
    RadioButton rbTouchandredline;
    @BindView(R.id.rg_actionmode)
    RadioGroup rgActionmode;
    @BindView(R.id.tv_lightcolor)
    TextView tvLightcolor;
    @BindView(R.id.rb_red)
    RadioButton rbRed;
    @BindView(R.id.rd_blue)
    RadioButton rdBlue;
    @BindView(R.id.rg_lightcolor)
    RadioGroup rgLightcolor;
    @BindView(R.id.tv_blinkmode)
    TextView tvBlinkmode;
    @BindView(R.id.rb_out)
    RadioButton rbOut;
    @BindView(R.id.rd_in)
    RadioButton rdIn;
    @BindView(R.id.rg_blinkmode)
    RadioGroup rgBlinkmode;
    @BindView(R.id.tvTotalTime)
    TextView tvTotalTime;
    @BindView(R.id.btn_startrun)
    Button btnStartrun;
    @BindView(R.id.btn_stoprun)
    Button btnStoprun;
    @BindView(R.id.lvTimes)
    ListView lvTimes;
    @BindView(R.id.showAvergeScore)
    TextView showAvergeScore;
    @BindView(R.id.avergeScore)
    TextView avergeScore;
    @BindView(R.id.bt_save)
    Button btSave;

/*字段*/
    private Context mContext;
    private List<DbLight> mSelectLight = new ArrayList<>();
    private List<TimeInfo> mTimeInfoListData = new ArrayList<>();
    private List<String> mOrderStrList = new ArrayList<>();
    private LightOperateAdapter mLightOperateAdapter;
    private ScheduledExecutorService mExecutorService;
    private CommandNew mCommandNew;
    private Realm mRealm;
    //结束标记
    private boolean endFlag = false;


    /**
     * 广播监听
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case AppConfig.ACTION_TIME_INFO:
                    if (!endFlag) {
                        TimeInfo timeInfo = (TimeInfo) intent.getSerializableExtra("timeInfo");
                        Log.d("timeInfo", timeInfo.toString());
                        analyzeTimeData(timeInfo);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlespot);
        ButterKnife.bind(this);
        mContext = this;
        mRealm = Realm.getDefaultInstance();
        mCommandNew = new CommandNew();
        mExecutorService = new ScheduledThreadPoolExecutor(2);
        registerReceiver();
    }

    /*注册广播*/
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConfig.ACTION_TIME_INFO);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    protected void initBaseDevice() {
        super.initBaseDevice();
    }

    @Override
    protected void onReconnect() {
        super.onReconnect();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @OnClick({R.id.bt_run_cancel, R.id.layout_cancel, R.id.btn_turnon, R.id.btn_turnoff, R.id.img_btn_refresh, R.id.btn_startrun, R.id.btn_stoprun, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_run_cancel:
                break;
            case R.id.layout_cancel:
                break;
            case R.id.btn_turnon:
                break;
            case R.id.btn_turnoff:
                break;
            case R.id.img_btn_refresh:
                break;
            case R.id.btn_startrun:
                break;
            case R.id.btn_stoprun:
                break;
            case R.id.bt_save:
                break;
        }

    }

    /**
     * 解析时间
     * */
    private void analyzeTimeData(TimeInfo timeInfo) {
    }
}
