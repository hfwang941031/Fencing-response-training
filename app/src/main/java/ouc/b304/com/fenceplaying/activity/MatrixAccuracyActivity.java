package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.R;

/**
 * @author 王海峰 on 2018/11/12 16:13
 */
public class MatrixAccuracyActivity extends Activity {
    @BindView(R.id.bt_run_cancel)
    ImageView btRunCancel;
    @BindView(R.id.layout_cancel)
    LinearLayout layoutCancel;
    @BindView(R.id.btn_turnon)
    Button btnTurnon;
    @BindView(R.id.btn_turnoff)
    Button btnTurnoff;
    @BindView(R.id.sp_times)
    Spinner spTimes;
    @BindView(R.id.tv_avalibledevices)
    TextView tvAvalibledevices;
    @BindView(R.id.tv_devicenumbers)
    TextView tvDevicenumbers;
    @BindView(R.id.img_btn_refresh)
    ImageButton imgBtnRefresh;
    @BindView(R.id.tv_setdeviceparam)
    TextView tvSetdeviceparam;
    @BindView(R.id.tv_setdevicemode)
    TextView tvSetdevicemode;
    @BindView(R.id.sp_devicesmode)
    Spinner spDevicesmode;
    @BindView(R.id.tv_setdevicemaincolor)
    TextView tvSetdevicemaincolor;
    @BindView(R.id.sp_devicemaincolor)
    Spinner spDevicemaincolor;
    @BindView(R.id.tv_setdevicesecondcolor)
    TextView tvSetdevicesecondcolor;
    @BindView(R.id.sp_devicessecondcolor)
    Spinner spDevicessecondcolor;
    @BindView(R.id.tv_setlightmode)
    TextView tvSetlightmode;
    @BindView(R.id.sp_setlightmode)
    Spinner spSetlightmode;
    @BindView(R.id.tv_tishi)
    TextView tvTishi;
    @BindView(R.id.tv_beizhu)
    TextView tvBeizhu;
    @BindView(R.id.tvTotalTime)
    TextView tvTotalTime;
    @BindView(R.id.btn_startrun)
    Button btnStartrun;
    @BindView(R.id.btn_stoprun)
    Button btnStoprun;
    @BindView(R.id.lvTimes)
    ListView lvTimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fangzhen);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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


    @OnClick({R.id.btn_turnon, R.id.btn_turnoff, R.id.img_btn_refresh, R.id.btn_startrun, R.id.btn_stoprun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
        }
    }
}
