package ouc.b304.com.fenceplaying.activity.newVersion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.LightOperateAdapter;
import ouc.b304.com.fenceplaying.adapter.NewPowerAdapter;
import ouc.b304.com.fenceplaying.adapter.OrderAdapter;
import ouc.b304.com.fenceplaying.adapter.ShockInfoAdapter;
import ouc.b304.com.fenceplaying.adapter.TimeInfoAdapter;
import ouc.b304.com.fenceplaying.dialog.AlterDeviceDialog;
import ouc.b304.com.fenceplaying.dialog.AlterOrderNumDialog;
import ouc.b304.com.fenceplaying.dialog.AlterSleepDialog;
import ouc.b304.com.fenceplaying.dialog.CusProgressDialog;
import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.entity.ShockInfo;
import ouc.b304.com.fenceplaying.entity.TimeInfo;
import ouc.b304.com.fenceplaying.order.CommandNew;
import ouc.b304.com.fenceplaying.order.Order;
import ouc.b304.com.fenceplaying.order.OrderUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;
import ouc.b304.com.fenceplaying.utils.newUtils.RealmUtils;

/**
 * @author 王海峰 on 2019/4/21 17:07
 */
public class Test extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.newback)
    ImageView back;
    @BindView(R.id.sleep)
    Button sleep;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.turnOn)
    Button turnOn;
    @BindView(R.id.turnOff)
    Button turnOff;
    @BindView(R.id.lightList)
    RecyclerView lightList;
    @BindView(R.id.selectAll)
    Button selectAll;
    @BindView(R.id.selectNone)
    Button selectNone;
    @BindView(R.id.sleepOrder)
    TextView sleepOrder;
    @BindView(R.id.orderNum)
    TextView orderNum;
    @BindView(R.id.beforeOutBlinkNone)
    RadioButton beforeOutBlinkNone;
    @BindView(R.id.beforeOutBlinkAlways)
    RadioButton beforeOutBlinkAlways;
    @BindView(R.id.beforeOutBlinkSlow)
    RadioButton beforeOutBlinkSlow;
    @BindView(R.id.beforeOutBlinkFast)
    RadioButton beforeOutBlinkFast;
    @BindView(R.id.beforeOutBlink)
    RadioGroup beforeOutBlink;
    @BindView(R.id.beforeOutColorBlue)
    RadioButton beforeOutColorBlue;
    @BindView(R.id.beforeOutColorRed)
    RadioButton beforeOutColorRed;
    @BindView(R.id.beforeOutColorGreen)
    RadioButton beforeOutColorGreen;
    @BindView(R.id.beforeOutColorPurple)
    RadioButton beforeOutColorPurple;
    @BindView(R.id.beforeOutColor1)
    RadioGroup beforeOutColor1;
    @BindView(R.id.beforeOutColorCyan)
    RadioButton beforeOutColorCyan;
    @BindView(R.id.beforeOutColorYellow)
    RadioButton beforeOutColorYellow;
    @BindView(R.id.beforeOutColorWhite)
    RadioButton beforeOutColorWhite;
    @BindView(R.id.beforeOutColor2)
    RadioGroup beforeOutColor2;
    @BindView(R.id.beforeInBlinkNone)
    RadioButton beforeInBlinkNone;
    @BindView(R.id.beforeInBlinkAlways)
    RadioButton beforeInBlinkAlways;
    @BindView(R.id.beforeInBlinkSlow)
    RadioButton beforeInBlinkSlow;
    @BindView(R.id.beforeInBlinkFast)
    RadioButton beforeInBlinkFast;
    @BindView(R.id.beforeInBlink)
    RadioGroup beforeInBlink;
    @BindView(R.id.beforeInColorBlue)
    RadioButton beforeInColorBlue;
    @BindView(R.id.beforeInColorRed)
    RadioButton beforeInColorRed;
    @BindView(R.id.beforeInColorPurple)
    RadioButton beforeInColorPurple;
    @BindView(R.id.beforeInColor)
    RadioGroup beforeInColor;
    @BindView(R.id.infraredEmissionOn)
    RadioButton infraredEmissionOn;
    @BindView(R.id.infraredEmissionOff)
    RadioButton infraredEmissionOff;
    @BindView(R.id.infraredEmission)
    RadioGroup infraredEmission;
    @BindView(R.id.infraredInductionOn)
    RadioButton infraredInductionOn;
    @BindView(R.id.infraredInductionOff)
    RadioButton infraredInductionOff;
    @BindView(R.id.infraredInduction)
    RadioGroup infraredInduction;
    @BindView(R.id.infraredModelNormal)
    RadioButton infraredModelNormal;
    @BindView(R.id.infraredModelContend)
    RadioButton infraredModelContend;
    @BindView(R.id.infraredModel)
    RadioGroup infraredModel;
    @BindView(R.id.infraredHeightLow)
    RadioButton infraredHeightLow;
    @BindView(R.id.infraredHeight_5cm)
    RadioButton infraredHeight5cm;
    @BindView(R.id.infraredHeight_30cm)
    RadioButton infraredHeight30cm;
    @BindView(R.id.infraredHeightHigh)
    RadioButton infraredHeightHigh;
    @BindView(R.id.infraredHeight)
    RadioGroup infraredHeight;
    @BindView(R.id.vibrationInducedOn)
    RadioButton vibrationInducedOn;
    @BindView(R.id.vibrationInducedOff)
    RadioButton vibrationInducedOff;
    @BindView(R.id.vibrationInduced)
    RadioGroup vibrationInduced;
    @BindView(R.id.vibrationIntensityL)
    RadioButton vibrationIntensityL;
    @BindView(R.id.vibrationIntensityM)
    RadioButton vibrationIntensityM;
    @BindView(R.id.vibrationIntensityH)
    RadioButton vibrationIntensityH;
    @BindView(R.id.vibrationIntensity)
    RadioGroup vibrationIntensity;
    @BindView(R.id.vibrationDetailsOn)
    RadioButton vibrationDetailsOn;
    @BindView(R.id.vibrationDetailsOff)
    RadioButton vibrationDetailsOff;
    @BindView(R.id.vibrationDetails)
    RadioGroup vibrationDetails;
    @BindView(R.id.buzzerNone)
    RadioButton buzzerNone;
    @BindView(R.id.buzzerShort)
    RadioButton buzzerShort;
    @BindView(R.id.buzzer_1s)
    RadioButton buzzer1s;
    @BindView(R.id.buzzer_2s)
    RadioButton buzzer2s;
    @BindView(R.id.buzzer)
    RadioGroup buzzer;
    @BindView(R.id.afterOutBlinkNone)
    RadioButton afterOutBlinkNone;
    @BindView(R.id.afterOutBlinkAlways)
    RadioButton afterOutBlinkAlways;
    @BindView(R.id.afterOutBlinkSlow)
    RadioButton afterOutBlinkSlow;
    @BindView(R.id.afterOutBlinkFast)
    RadioButton afterOutBlinkFast;
    @BindView(R.id.afterOutBlink)
    RadioGroup afterOutBlink;
    @BindView(R.id.afterOutColorBlue)
    RadioButton afterOutColorBlue;
    @BindView(R.id.afterOutColorRed)
    RadioButton afterOutColorRed;
    @BindView(R.id.afterOutColorGreen)
    RadioButton afterOutColorGreen;
    @BindView(R.id.afterOutColorPurple)
    RadioButton afterOutColorPurple;
    @BindView(R.id.afterOutColor1)
    RadioGroup afterOutColor1;
    @BindView(R.id.afterOutColorCyan)
    RadioButton afterOutColorCyan;
    @BindView(R.id.afterOutColorYellow)
    RadioButton afterOutColorYellow;
    @BindView(R.id.afterOutColorWhite)
    RadioButton afterOutColorWhite;
    @BindView(R.id.afterOutColor2)
    RadioGroup afterOutColor2;
    @BindView(R.id.afterInBlinkNone)
    RadioButton afterInBlinkNone;
    @BindView(R.id.afterInBlinkAlways)
    RadioButton afterInBlinkAlways;
    @BindView(R.id.afterInBlinkSlow)
    RadioButton afterInBlinkSlow;
    @BindView(R.id.afterInBlinkFast)
    RadioButton afterInBlinkFast;
    @BindView(R.id.afterInBlink)
    RadioGroup afterInBlink;
    @BindView(R.id.afterInColorBlue)
    RadioButton afterInColorBlue;
    @BindView(R.id.afterInColorRed)
    RadioButton afterInColorRed;
    @BindView(R.id.afterInColorPurple)
    RadioButton afterInColorPurple;
    @BindView(R.id.afterInColor)
    RadioGroup afterInColor;
    @BindView(R.id.afterBuzzerNone)
    RadioButton afterBuzzerNone;
    @BindView(R.id.afterBuzzerShort)
    RadioButton afterBuzzerShort;
    @BindView(R.id.afterBuzzer_1s)
    RadioButton afterBuzzer1s;
    @BindView(R.id.afterBuzzer_2s)
    RadioButton afterBuzzer2s;
    @BindView(R.id.afterBuzzer)
    RadioGroup afterBuzzer;
    @BindView(R.id.tv_pan_id)
    TextView tvPanId;
    @BindView(R.id.mac_address)
    TextView macAddress;
    @BindView(R.id.alter)
    Button alter;
    @BindView(R.id.deviceList)
    RecyclerView deviceList;
    @BindView(R.id.orderRecyclerView)
    RecyclerView orderRecyclerView;
    @BindView(R.id.resetCoordinator)
    Button resetCoordinator;
    @BindView(R.id.clearData)
    Button clearData;
    @BindView(R.id.timeInfoList)
    RecyclerView timeInfoList;
    @BindView(R.id.shackView)
    RecyclerView shackView;
    @BindView(R.id.orderList)
    RecyclerView orderList;
    @BindView(R.id.receiveOrderList)
    RecyclerView receiveOrderList;

    private Context mContext;
    private NewPowerAdapter mPowerAdapter;
    private TimeInfoAdapter mTimeInfoAdapter;
    private ShockInfoAdapter mShockInfoAdapter;
    private OrderAdapter mAdapter;
    private AlterSleepDialog mAlterSleepDialog;
    private AlterDeviceDialog mAlterDeviceDialog;
    private AlertDialog mAlertDialog;
    private List<DbLight> mSelectLight = new ArrayList<>();
    private List<TimeInfo> mTimeInfoListData = new ArrayList<>();
    private List<ShockInfo> mShockInfoList = new ArrayList<>();
    private List<String> mOrderStrList = new ArrayList<>();
    private LightOperateAdapter mLightOperateAdapter;
    private ScheduledExecutorService mExecutorService;
    private AlterOrderNumDialog mAlterOrderNumDialog;
    private CommandNew mCommandNew;
    private CusProgressDialog mCusProgressDialog;
    private Realm mRealm;


    private void showAlterDialog() {
        mAlterDeviceDialog = new AlterDeviceDialog(mContext, mRealm, new AlterDeviceDialog.SetOnDialogListener() {
            @Override
            public void onDataChangeListener() {
                mPowerAdapter.notifyDataSetChanged();
                mLightOperateAdapter.notifyDataSetChanged();
            }
        });
        mAlterDeviceDialog.show();
    }

    /**
     * 广播监听
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case AppConfig.ACTION_TIME_INFO:
                    TimeInfo timeInfo = (TimeInfo) intent.getSerializableExtra("timeInfo");
                    mTimeInfoListData.add(timeInfo);
                    mTimeInfoAdapter.notifyDataSetChanged();
                    break;
                case AppConfig.ACTION_SHACK_INFO:
                    ShockInfo shockInfo = (ShockInfo) intent.getSerializableExtra("shackInfo");
                    mShockInfoList.add(shockInfo);
                    mShockInfoAdapter.notifyDataSetChanged();
                    break;
                case AppConfig.ACTION_ANALYSIS_DEVICE:
                    setDevice();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mContext = this;
        mRealm = Realm.getDefaultInstance();
        mCusProgressDialog = new CusProgressDialog(mContext);
        mCommandNew = new CommandNew();
        mExecutorService = new ScheduledThreadPoolExecutor(2);
        registerReceiver();
        /*initListener();*/
        initAdapter();
        setDevice();
    }

    private void initAdapter() {
        for (DbLight dbLight : AppConfig.sDbLights) {
            dbLight.setSelect(false);
        }
        lightList.setLayoutManager(new StaggeredGridLayoutManager(8, StaggeredGridLayoutManager.VERTICAL));
        mLightOperateAdapter = new LightOperateAdapter(mContext, AppConfig.sDbLights, new LightOperateAdapter.SetOnOperateListener() {
            @Override
            public void onOperateListener(DbLight light) {
                boolean flag = mSelectLight.contains(light);
                if (flag) {
                    mSelectLight.remove(light);
                } else {
                    mSelectLight.add(light);
                }
            }
        });
        lightList.setAdapter(mLightOperateAdapter);
        deviceList.setLayoutManager(new LinearLayoutManager(mContext));
        mPowerAdapter = new NewPowerAdapter(mContext, AppConfig.sDbLights);
        deviceList.setAdapter(mPowerAdapter);
        mExecutorService.scheduleAtFixedRate(new PowerRunnable(), 5, 5, TimeUnit.SECONDS);

        timeInfoList.setLayoutManager(new LinearLayoutManager(mContext));
        mTimeInfoAdapter = new TimeInfoAdapter(mContext, mTimeInfoListData);
        timeInfoList.setAdapter(mTimeInfoAdapter);

        shackView.setLayoutManager(new LinearLayoutManager(mContext));
        mShockInfoAdapter = new ShockInfoAdapter(mContext, mShockInfoList);
        shackView.setAdapter(mShockInfoAdapter);

        orderRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new OrderAdapter(mContext, mOrderStrList);
        orderRecyclerView.setAdapter(mAdapter);

    }


    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConfig.ACTION_TIME_INFO);
        intentFilter.addAction(AppConfig.ACTION_SHACK_INFO);
        intentFilter.addAction(AppConfig.ACTION_ANALYSIS_DEVICE);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void setDevice() {
        if (AppConfig.sDevice != null) {
            tvPanId.setText(AppConfig.sDevice.getChannelNumber() + AppConfig.sDevice.getPanId());
            macAddress.setText(getMac(AppConfig.sDevice.getMacAddress()));
        }
    }

    private String getMac(String macAddress) {
        String mac = "";
        for (int i = 0; i < macAddress.length(); i++) {
            if (i % 2 == 0) {
                mac += macAddress.charAt(i);
            } else {
                mac += macAddress.charAt(i) + ":";
            }
        }
        mac = mac.substring(0, mac.length() - 1);
        return mac;
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
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        backFinish();
    }

    @OnClick({R.id.sleep, R.id.send, R.id.turnOn, R.id.turnOff, R.id.selectAll, R.id.selectNone, R.id.beforeOutBlinkNone, R.id.beforeOutBlinkAlways, R.id.beforeOutBlinkSlow, R.id.beforeOutBlinkFast, R.id.beforeOutBlink, R.id.beforeOutColorBlue, R.id.beforeOutColorRed, R.id.beforeOutColorGreen, R.id.beforeOutColorPurple, R.id.beforeOutColor1, R.id.beforeOutColorCyan, R.id.beforeOutColorYellow, R.id.beforeOutColorWhite, R.id.beforeOutColor2, R.id.beforeInBlinkNone, R.id.beforeInBlinkAlways, R.id.beforeInBlinkSlow, R.id.beforeInBlinkFast, R.id.beforeInBlink, R.id.beforeInColorBlue, R.id.beforeInColorRed, R.id.beforeInColorPurple, R.id.beforeInColor, R.id.infraredEmissionOn, R.id.infraredEmissionOff, R.id.infraredEmission, R.id.infraredInductionOn, R.id.infraredInductionOff, R.id.infraredInduction, R.id.infraredModelNormal, R.id.infraredModelContend, R.id.infraredModel, R.id.infraredHeightLow, R.id.infraredHeight_5cm, R.id.infraredHeight_30cm, R.id.infraredHeightHigh, R.id.infraredHeight, R.id.vibrationInducedOn, R.id.vibrationInducedOff, R.id.vibrationInduced, R.id.vibrationIntensityL, R.id.vibrationIntensityM, R.id.vibrationIntensityH, R.id.vibrationIntensity, R.id.vibrationDetailsOn, R.id.vibrationDetailsOff, R.id.vibrationDetails, R.id.buzzerNone, R.id.buzzerShort, R.id.buzzer_1s, R.id.buzzer_2s, R.id.buzzer, R.id.afterOutBlinkNone, R.id.afterOutBlinkAlways, R.id.afterOutBlinkSlow, R.id.afterOutBlinkFast, R.id.afterOutBlink, R.id.afterOutColorBlue, R.id.afterOutColorRed, R.id.afterOutColorGreen, R.id.afterOutColorPurple, R.id.afterOutColor1, R.id.afterOutColorCyan, R.id.afterOutColorYellow, R.id.afterOutColorWhite, R.id.afterOutColor2, R.id.afterInBlinkNone, R.id.afterInBlinkAlways, R.id.afterInBlinkSlow, R.id.afterInBlinkFast, R.id.afterInBlink, R.id.afterInColorBlue, R.id.afterInColorRed, R.id.afterInColorPurple, R.id.afterInColor, R.id.afterBuzzerNone, R.id.afterBuzzerShort, R.id.afterBuzzer_1s, R.id.afterBuzzer_2s, R.id.afterBuzzer, R.id.alter, R.id.resetCoordinator, R.id.clearData})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*case R.id.newback:
                Log.d("back", "返回被点击");
                backFinish();

                break;*/
            case R.id.sleepOrder:
                mAlterSleepDialog = new AlterSleepDialog(mContext, new AlterSleepDialog.SetOnDialogListener() {
                    @Override
                    public void sureClick() {
                        sleepOrder.setText("命令间隔：" + AppConfig.sSleep + " ms");
                        mAlterSleepDialog.dismiss();
                    }
                });
                mAlterSleepDialog.show();
                break;
            case R.id.orderNum:
                mAlterOrderNumDialog = new AlterOrderNumDialog(mContext, new AlterOrderNumDialog.SetOnDialogListener() {
                    @Override
                    public void sureClick() {
                        orderNum.setText("命令重发次数：" + AppConfig.sNum);
                        mAlterOrderNumDialog.dismiss();
                    }
                });
                mAlterOrderNumDialog.show();
                break;
            case R.id.sleep:
                for (DbLight dbLight : mSelectLight) {
                    dbLight.setSelect(false);
                }
                mSelectLight.clear();
                mLightOperateAdapter.notifyDataSetChanged();
                OrderUtils.getInstance().sleepAllLight(AppConfig.sDbLights);
                break;
            case R.id.send:
                sendCommand();
                break;
            case R.id.turnOn:
                OrderUtils.getInstance().turnOnAllLight(AppConfig.sDbLights);
                break;
            case R.id.turnOff:
                OrderUtils.getInstance().closeAllLight(AppConfig.sDbLights);
                break;
            case R.id.selectAll:
                selectAll();
                break;
            case R.id.selectNone:
                selectNone();
                break;

            case R.id.alter:
                if (AppConfig.sDevice != null) {
                    showHintDialog("是否对该协调器下的光标进行编辑？");
                }
                break;
            case R.id.resetCoordinator:
                mAlertDialog = new AlertDialog.Builder(mContext)
                        .setTitle("提示：")
                        .setMessage("重置前，请尽可能保证光标在线？")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            //添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mAlertDialog.dismiss();
                                mCusProgressDialog.showMessage("协调器重置中...");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        resetCoordinator();
                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            //添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mAlertDialog.dismiss();
                            }
                        }).create();
                mAlertDialog.show();
                break;

            case R.id.clearData:
                mTimeInfoListData.clear();
                mShockInfoList.clear();
                mOrderStrList.clear();

                mTimeInfoAdapter.notifyDataSetChanged();
                mShockInfoAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void backFinish() {
        finish();
    }

    private void sendCommand() {
        if (mSelectLight.size() > 0) {
            List<Order> orderList = new ArrayList<>();
            for (DbLight dbLight : mSelectLight) {
                Order order = new Order();
                order.setCommandNew(mCommandNew);
                order.setLight(dbLight);
                orderList.add(order);
            }
            OrderUtils.getInstance().sendCommand(orderList, new OrderUtils.SetOnOrderListener() {
                @Override
                public void onOrderListener(String order) {
                    mOrderStrList.add(order);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    });
                }
            });
        } else {
            Toast.makeText(mContext, "未选择光标！", Toast.LENGTH_SHORT).show();
        }
    }


    private void selectAll() {
        mSelectLight.clear();
        for (DbLight dbLight : AppConfig.sDbLights) {
            if (dbLight.getPower() != "0000") {
                dbLight.setSelect(true);
                mSelectLight.add(dbLight);
            }
        }
        mLightOperateAdapter.notifyDataSetChanged();
    }


    private void selectNone() {
        mSelectLight.clear();
        for (DbLight dbLight : AppConfig.sDbLights) {
            dbLight.setSelect(false);
        }
        mLightOperateAdapter.notifyDataSetChanged();
    }


    private void showHintDialog(String hint) {
        //添加"Yes"按钮
        //添加取消
        mAlertDialog = new AlertDialog.Builder(this)
                .setTitle("编辑：")
                .setMessage(hint)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    //添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertDialog.dismiss();
                        showAlterDialog();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    //添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAlertDialog.dismiss();
                    }
                }).create();
        mAlertDialog.show();
    }

    private void resetCoordinator() {
        OrderUtils.getInstance().removeNet(AppConfig.sDbLights);
        SystemClock.sleep(2000);
        OrderUtils.getInstance().resetCoordinator();
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RealmUtils.deleteDevice(mRealm, AppConfig.sDevice, new RealmUtils.SetOnDeleteListener() {
                    @Override
                    public void onDeleteListener(Boolean flag) {
                        if (flag) {
                            /*初始化协调器*/
                            OrderUtils.getInstance().initDevice();
                            int waitNum = 0;
                            while (AppConfig.sDevice == null && waitNum < 3) {
                                SystemClock.sleep(500);
                            }
                            if (waitNum == 3) {
                                mCusProgressDialog.dismiss();
                                Toast.makeText(mContext, "未获取到协调器PanId", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            getSaveLight();
                        } else {
                            mCusProgressDialog.dismiss();
                            Toast.makeText(mContext, "数据清除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

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
                                Toast.makeText(mContext, "协调器保存失败", Toast.LENGTH_SHORT).show();
                            }
                            mCusProgressDialog.dismiss();
                        }
                    });
                }
            }
        });

    }

    @OnClick(R.id.newback)
    public void onViewClicked() {
        backFinish();
    }

    class PowerRunnable implements Runnable {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPowerAdapter.notifyDataSetChanged();
                    mLightOperateAdapter.notifyDataSetChanged();
                }
            });
        }
    }


}
