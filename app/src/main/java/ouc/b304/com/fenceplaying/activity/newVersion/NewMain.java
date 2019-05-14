package ouc.b304.com.fenceplaying.activity.newVersion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.utils.newUtils.SafLightReceiver;

/**
 * @author 王海峰 on 2019/4/20 18:57
 */
public class NewMain extends BaseActivity {
    @BindView(R.id.img_btn_initdevice)
    ImageButton imgBtnInitdevice;
    @BindView(R.id.lv_battery)
    ListView lvBattery;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.img_icon_playerinfo)
    ImageView imgIconPlayerinfo;
    @BindView(R.id.tv_manageplayinfo)
    TextView tvManageplayinfo;
    @BindView(R.id.rl_playerinfo)
    RelativeLayout rlPlayerinfo;
    @BindView(R.id.img_icon_train)
    ImageView imgIconTrain;
    @BindView(R.id.tv_train)
    TextView tvTrain;
    @BindView(R.id.rl_train)
    RelativeLayout rlTrain;
    @BindView(R.id.img_icon_historydata)
    ImageView imgIconHistorydata;
    @BindView(R.id.tv_historydata)
    TextView tvHistorydata;
    @BindView(R.id.rl_histroydata)
    RelativeLayout rlHistroydata;
    @BindView(R.id.img_icon_setting)
    ImageView imgIconSetting;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;


    private ExecutorService mExecutorService;
    private Context mContext;
    private Realm mRealm;
    private SafLightReceiver mSafLightReceiver;
    private Intent mintent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmain);
        ButterKnife.bind(this);
        mContext = this;
        mExecutorService = new ScheduledThreadPoolExecutor(2);
        mRealm = Realm.getDefaultInstance();
        mSafLightReceiver = new SafLightReceiver(mRealm);
        mSafLightReceiver.usbRegisterReceiver(mContext);
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
        if (mRealm != null) {
            mRealm.close();
        }
        mSafLightReceiver.usbUnregisterReceiver(mContext);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @OnClick(R.id.rl_playerinfo)
    public void onRlPlayerinfoClicked() {
    }

    @OnClick(R.id.rl_train)
    public void onRlTrainClicked() {
    }

    @OnClick(R.id.rl_histroydata)
    public void onRlHistroydataClicked() {
    }

    @OnClick(R.id.rl_setting)
    public void onRlSettingClicked() {
    }


    @OnClick(R.id.img_btn_initdevice)
    public void onViewClicked() {
    }
}
