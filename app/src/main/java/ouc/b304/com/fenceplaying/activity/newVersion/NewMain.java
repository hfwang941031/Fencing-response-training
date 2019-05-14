package ouc.b304.com.fenceplaying.activity.newVersion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.activity.DataShowSelect;
import ouc.b304.com.fenceplaying.activity.InfoSelectActivity;
import ouc.b304.com.fenceplaying.activity.SettingActivity;
import ouc.b304.com.fenceplaying.activity.TrainTypeSelectActivity;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;
import ouc.b304.com.fenceplaying.utils.newUtils.SafLightReceiver;

/**
 * @author 王海峰 on 2019/4/20 18:57
 */
public class NewMain extends BaseActivity {


    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.img_btn_initdevice)
    ImageButton imgBtnInitdevice;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.rl_playerinfo)
    LinearLayout rlPlayerinfo;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.rl_train)
    LinearLayout rlTrain;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.rl_histroydata)
    LinearLayout rlHistroydata;
    @BindView(R.id.img6)
    ImageView img6;
    @BindView(R.id.rl_setting)
    LinearLayout rlSetting;
    @BindView(R.id.viewPage)
    FrameLayout viewPage;
    @BindView(R.id.light_num)
    TextView lightNum;
    private ExecutorService mExecutorService;
    private Context mContext;
    private Realm mRealm;
    private SafLightReceiver mSafLightReceiver;
    private Intent mintent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GreenDaoInitApplication.getInstances().addActivityOrder(this);
        setContentView(R.layout.activity_newversionmain);
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


    @OnClick({R.id.img_btn_initdevice, R.id.rl_playerinfo, R.id.rl_train, R.id.rl_histroydata, R.id.rl_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_btn_initdevice:


                break;
            case R.id.rl_playerinfo:
                startActivity(new Intent(mContext, InfoSelectActivity.class));

                break;
            case R.id.rl_train:
                startActivity(new Intent(mContext, TrainTypeSelectActivity.class));

                break;
            case R.id.rl_histroydata:
                startActivity(new Intent(mContext, DataShowSelect.class));

                break;
            case R.id.rl_setting:
                startActivity(new Intent(mContext, Test.class));

                break;
        }
    }
}
