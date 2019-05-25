package ouc.b304.com.fenceplaying.activity.newVersion;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import ouc.b304.com.fenceplaying.Bean.Constant;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.SingleSpotScoresDao;
import ouc.b304.com.fenceplaying.Dao.entity.Player;
import ouc.b304.com.fenceplaying.Dao.entity.SingleSpotScores;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.LightOperateAdapter;
import ouc.b304.com.fenceplaying.adapter.SaveResultAdapter;
import ouc.b304.com.fenceplaying.adapter.SingleSpotAdapter;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;
import ouc.b304.com.fenceplaying.dialog.AlterOrderNumDialog;
import ouc.b304.com.fenceplaying.dialog.AlterSleepDialog;
import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.entity.TimeInfo;
import ouc.b304.com.fenceplaying.order.CommandNew;
import ouc.b304.com.fenceplaying.order.CommandRules;
import ouc.b304.com.fenceplaying.order.Order;
import ouc.b304.com.fenceplaying.order.OrderUtils;
import ouc.b304.com.fenceplaying.thread.Timer;
import ouc.b304.com.fenceplaying.utils.IntegerToStringUtils;
import ouc.b304.com.fenceplaying.utils.PlayDaoUtils;
import ouc.b304.com.fenceplaying.utils.ScoreUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;
import ouc.b304.com.fenceplaying.utils.newUtils.RealmUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.ToastUtils;

import static android.widget.Toast.LENGTH_SHORT;

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

    @BindView(R.id.img_btn_refresh)
    ImageButton imgBtnRefresh;
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

    /*字段*/
    private Context mContext;
    private List<DbLight> mSelectLight = new ArrayList<>();
    private List<TimeInfo> mTimeInfoListData = new ArrayList<>();
    private List<String> mOrderStrList = new ArrayList<>();
    private LightOperateAdapter mLightOperateAdapter;
    private ScheduledExecutorService mExecutorService;

    private Realm mRealm;
    //每次训练的时间集合
    private ArrayList<Integer> timeList;
    private List<String> scoreList;
    private List<String> nameList;
    //设备选择下拉框适配器
    private ArrayAdapter<Character> spDeviceAdapter;

    //训练次数下拉框适配器
    private ArrayAdapter<String> spTimesAdapter;
    //选中的设备编号，也就是用哪个设备完成训练
    private String deviceNum;

    //选中的训练次数
    private int trainTimes = 0;

    //训练开始标志
    private boolean trainingBeginFlag = false;
    //训练开始时间
    private long startTime;

    //计数器
    private int counter = 0;

    /*平均值*/
    private float averageScore = 0;

    //定义成绩列表适配器
    private SingleSpotAdapter singleSpotAdapter;

    private Date date;

    private PlayerDao playerDao;

    private SingleSpotScoresDao singleSpotScoresDao;

    //对话框中listview的适配器
    private SaveResultAdapter saveResultAdapter;

    private SingleSpotScores singleSpotScores;

    private Player player;

    //设置一个布尔变量控制保存按钮的可按与否，当一次训练结束后，可以点击该保存按钮进行保存，点击过之后，不能再次进行点击，除非先进行下一次训练并得到一组时间值；
    private boolean saveBtnIsClickable = false;

    //结束标记
    private boolean endFlag = false;
    private final int UPDATE_TIMES = 3;
    private final int STOP_TRAINING = 4;
    private Timer timer;
    //存储在当前页面中可以用的设备编号，以方便在Spinner中选择设备
    private List<String> list = new ArrayList<>();
    private CommandRules.OutColor lightColor = CommandRules.OutColor.BLUE;
    private DbLight selectedLight = null;
    private CommandNew commandNew = new CommandNew();
    private boolean isLength = false;

    private AlterSleepDialog alterSleepDialog;

    private AlterOrderNumDialog alterOrderNumDialog;




    //广播监听
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
        mExecutorService = new ScheduledThreadPoolExecutor(2);
        registerReceiver();
        playerDao = GreenDaoInitApplication.getInstances().getDaoSession().getPlayerDao();
        singleSpotScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getSingleSpotScoresDao();
        updateLightName();
        //initData位于initView之前，因为initData的数据是Spinner中所需要的
        initData();
        initView();
        initListener();


    }

    //注册广播
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConfig.ACTION_TIME_INFO);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Timer.TIMER_FLAG:
                    String time = msg.obj.toString();
                    tvTotalTime.setText("总时间" + time);
                    break;
                case STOP_TRAINING:
                    //在结束之前先计算平均值
                    averageScore = ScoreUtils.calcAverageScore(timeList);
                    //设置值
                    avergeScore.setText(" " + averageScore + "毫秒");
                    stopTraining();
                    break;
                case UPDATE_TIMES:
                    singleSpotAdapter.setTimeList(timeList);
                    singleSpotAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

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
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @OnClick({R.id.bt_run_cancel, R.id.layout_cancel, R.id.btn_turnon, R.id.btn_turnoff, R.id.img_btn_refresh, R.id.btn_startrun, R.id.btn_stoprun, R.id.bt_save,R.id.sleepOrder,R.id.orderNum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_run_cancel:
                this.finish();
                break;
            case R.id.layout_cancel:
                break;
            case R.id.btn_turnon:
                //开选中的灯
                if (selectedLight == null) {
                    ToastUtils.makeText(mContext, "请先选择一个设备！", Toast.LENGTH_SHORT);
                } else {
                    Log.d("开灯设备编号", deviceNum);
                    OrderUtils.getInstance().turnOnLight(selectedLight);
                }
                break;
            case R.id.btn_turnoff:
                //关选中的灯
                OrderUtils.getInstance().turnOffLightList(AppConfig.sDbLights);

                break;
            case R.id.img_btn_refresh:
                //刷新当前页面的灯及其编号
                getSaveLight();
                ToastUtils.makeText(mContext, "设备已刷新", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_startrun:
                //训练开始的时候把上次的成绩置空
                averageScore = 0;
                avergeScore.setText("");

                if (deviceNum == "0" || trainTimes == 0)
                    Toast.makeText(this, "请先选择设备和训练次数！", LENGTH_SHORT).show();
                if (trainingBeginFlag) {
                } else {
                    startTraining();
                    btnTurnon.setClickable(false);
                    btnTurnoff.setClickable(false);
                }
                break;
            case R.id.btn_stoprun:
                stopTraining();
                break;
            case R.id.bt_save:
                if (saveBtnIsClickable) {
                    //首先把timeList<Integer>变成List<String>
                    //判空验证
                    if (timeList.size() == 0) {
                        Toast.makeText(mContext, "成绩列表为空，无法进行保存！请先进行训练", Toast.LENGTH_SHORT).show();
                    } else {
                        //确定保存时间
                        date = new Date();

                        //将Integer类型的List转换成String类型
                        IntegerToStringUtils.integerToString(timeList, scoreList);
                        final AlertDialog saveDialog = new AlertDialog.Builder(this).create();
                        //初始化对话中listview的布局
                        View view2 = LayoutInflater.from(this).inflate(R.layout.listview_savedialog, null);

                        final ListView lvSaveresult = view2.findViewById(R.id.lv_saveresult);
                        //设置对话框中listview的适配器
                        saveResultAdapter = new SaveResultAdapter(this);
                        lvSaveresult.setAdapter(saveResultAdapter);
                        //清空姓名list，防止重复出现
                        nameList.clear();
                        saveResultAdapter.setNameList(PlayDaoUtils.nameList(playerDao, nameList));
                        /*saveResultAdapter.notifyDataSetChanged();*/
                        //设置标题
                        saveDialog.setTitle("数据保存");
                        //添加布局
                        saveDialog.setView(view2);
                        //设置按键
                        saveDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        saveDialog.show();


                        //为对话框中的listview设置子项单击事件
                        lvSaveresult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view2, int position, long l) {
                                //获取到点击项的值，此处为名字
                                String name = String.valueOf(adapterView.getItemAtPosition(position));
                                //根据名字获取到Player实体
                                QueryBuilder query = playerDao.queryBuilder();
                                query.where(PlayerDao.Properties.Name.eq(name));
                                List<Player> nameList = query.list();
                                player = nameList.get(0);
                                //根据player实体设置一对多的ID
                                singleSpotScores = new SingleSpotScores();
                                singleSpotScores.setPlayerId(player.getId());

                                singleSpotScores.setAverageScores(averageScore);
                                singleSpotScores.setDate(date);
                                singleSpotScores.setScoresList(scoreList);
                                singleSpotScores.setTrainingTimes(trainTimes);
                                //插入成绩实体
                                singleSpotScoresDao.insert(singleSpotScores);
                                //给出数据插入成功提示
                                Toast.makeText(mContext, "数据插入成功", Toast.LENGTH_SHORT).show();
                                //将保存按钮设置为不可点击
                                saveBtnIsClickable = false;
                                saveDialog.dismiss();
                            }
                        });
                    }
                } else {
                    Toast.makeText(mContext, "请勿对该成绩进行二次保存,请进行下一次训练后再执行保存！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sleepOrder:
                alterSleepDialog = new AlterSleepDialog(mContext, new AlterSleepDialog.SetOnDialogListener() {
                    @Override
                    public void sureClick() {
                        sleepOrder.setText("命令间隔：" + AppConfig.sSleep + " ms");
                        alterSleepDialog.dismiss();
                    }
                });
                alterSleepDialog.show();
                break;
            case R.id.orderNum:
                alterOrderNumDialog = new AlterOrderNumDialog(mContext, new AlterOrderNumDialog.SetOnDialogListener() {
                    @Override
                    public void sureClick() {
                        orderNum.setText("命令重发次数：" + AppConfig.sNum);
                        alterOrderNumDialog.dismiss();
                    }
                });
                alterOrderNumDialog.show();
                break;
        }

    }

    //解析时间
    private void analyzeTimeData(TimeInfo timeInfo) {
        Log.d("ana", "时间解析");
/*
            if (timeInfo.getName() == selectedLight.getName()) {
*/
        counter += 1;
        Log.d("******", timeInfo.getName() + timeInfo.getTime());

        if (counter > trainTimes) {
            endFlag = true;
        }
        Log.d("#######", counter + "");
        timeList.add((int) timeInfo.getTime());
        Log.d("再次开灯前状态", selectedLight.toString());
        List<Order> orderList = new ArrayList<>();
        selectedLight.setOpen(true);
        commandNew.setBeforeOutColor(lightColor);
        commandNew.setInfraredEmission(CommandRules.InfraredEmission.OPEN);
        commandNew.setInfraredInduction(CommandRules.InfraredInduction.OPEN);
        commandNew.setInfraredModel(CommandRules.InfraredModel.NORMAL);
        Order order = new Order();
        order.setLight(selectedLight);
        order.setCommandNew(commandNew);
        orderList.add(order);
        OrderUtils.getInstance().sendCommand(orderList);
        Log.d("ananlyze中开灯", selectedLight.toString());

        //  }

        Message msg = Message.obtain();
        msg.what = UPDATE_TIMES;
        msg.obj = "";
        handler.sendMessage(msg);
        if (isTrainingOver()) {
            //训练结束后设置保存按钮可点击
            saveBtnIsClickable = true;
            Log.d("ifistrainingover", "has run" + counter);
            Message msg1 = Message.obtain();
            msg1.what = STOP_TRAINING;
            msg1.obj = "";
            handler.sendMessage(msg1);
        }

    }


    //检测灯数量
    public void checkLightNumber() {
        if (AppConfig.sDbLights.size() < 1) {
            ToastUtils.makeText(mContext, "可用设备不足一个,无法进行训练！", Toast.LENGTH_SHORT);
        }
    }

    //初始化数据
    public void initData() {

        //获取当前可用的设备编号，存储到list当中
        for (int i = 0; i < AppConfig.sDbLights.size(); i++) {
            String name = AppConfig.sDbLights.get(i).getName();
            list.add(name);
        }
        /*检测灯数量*/
        checkLightNumber();

        //初始化String类型的时间列表
        scoreList = new ArrayList<>();

        //初始化String类型的运动员姓名列表
        nameList = new ArrayList<>();
        timeList = new ArrayList<>();

    }

    //初始化控件
    private void initView() {
        //设置spinner设备号适配器
        spDeviceAdapter = new ArrayAdapter(this, R.layout.spinner_item, list);
        spDevices.setAdapter(spDeviceAdapter);

        //设置spinner次数适配器
        spTimesAdapter = new ArrayAdapter(this, R.layout.spinner_item, Constant.trainingTimes);
        spTimes.setAdapter(spTimesAdapter);

        //将选中的deviceNum存储到变量deviceNum中
        spDevices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //通过Spinner选择编号，
                deviceNum = (String) spDevices.getItemAtPosition(i);
                if (AppConfig.sDbLights != null && AppConfig.sDbLights.size() > 0) {
                    for (DbLight dbLight : AppConfig.sDbLights) {
                        //此处原本有Bug，用中间变量存储以后，bug修复
                        String name = dbLight.getName();
                        if (name.equals(deviceNum)) {
                            selectedLight = dbLight;
                            break;
                        }
                    }
                    if (selectedLight == null) {
                        ToastUtils.makeText(mContext, "可用设备不足一个", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("deviceNum", selectedLight.getName() + "");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //将选中的训练次数存储到变量trainTimes中
        spTimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                trainTimes = Integer.parseInt((String) spTimes.getSelectedItem());
                Log.d("trainTimes", trainTimes + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //初始化时间listview,即成绩显示列表
        singleSpotAdapter = new SingleSpotAdapter(this);
        lvTimes.setAdapter(singleSpotAdapter);
    }


    //开始训练
    public void startTraining() {
        Log.d(TAG, "startTraining has run");
        //首先清除时间列表
        AppConfig.sTimeInfoList.clear();
        trainingBeginFlag = true;
        //清空时间列表，防止将上次训练的成绩保存到下一次训练当中
        timeList.clear();
        singleSpotAdapter.setTimeList(timeList);
        singleSpotAdapter.notifyDataSetChanged();
        /////
        Log.d("开始训练前被选灯的状态", selectedLight.toString());
        endFlag = false;
        List<Order> orderList = new ArrayList<>();
        selectedLight.setOpen(true);
        commandNew.setBeforeOutColor(lightColor);
        commandNew.setInfraredEmission(CommandRules.InfraredEmission.OPEN);
        commandNew.setInfraredInduction(CommandRules.InfraredInduction.OPEN);
        commandNew.setInfraredModel(CommandRules.InfraredModel.NORMAL);
        Order order = new Order();
        order.setLight(selectedLight);
        order.setCommandNew(commandNew);
        orderList.add(order);
        OrderUtils.getInstance().sendCommand(orderList);
        Log.d("开始训练设置命令后", selectedLight.toString());
        //获得当前的系统时间
        startTime = System.currentTimeMillis();
        timer = new Timer(handler);
        timer.setBeginTime(startTime);
        timer.start();

    }

    //判断训练是否结束
    private boolean isTrainingOver() {
        if (counter >= trainTimes) {
            return true;
        } else {
            return false;
        }
    }

    //停止训练
    public void stopTraining() {
        trainingBeginFlag = false;
        endFlag = true;
        OrderUtils.getInstance().turnOffLightList(AppConfig.sDbLights);
        Log.d("停止训练灯状态", selectedLight.toString());
        if (timer != null) {
            timer.stopTimer();
        }
        //很重要的重置计数器
        counter = 0;
        Log.d("counter:", counter + "");

    }


    //更新灯及其编号
    private void getSaveLight() {
        RealmUtils.queryDevice(mRealm, AppConfig.sDevice.getPanId(), new RealmUtils.SetOnIsHaveListener() {
            @Override
            public void onIsHaveListener(Boolean flag) {
                if (!flag) {
                    RealmUtils.saveDevice(mRealm, AppConfig.sDevice, new RealmUtils.SetOnSaveListener() {
                        @Override
                        public void onSaveListener(Boolean flag) {
                            if (!flag) {
                            }
                        }
                    });
                }
            }
        });

    }

    //在oncreate中更新设备编号
    private void updateLightName() {
        //判空操作，否则进入Test报空指针
        if (AppConfig.sDevice != null) {
            //每次打开当前页面都要从数据库中读取灯的编号，否则编号默认为递增的自然数
            getSaveLight();
        }
    }

    private void initListener() {
        /*设置外圈灯感应前*/
        setOutBefore();
        /*设置内圈灯感应前*/
        setInBefore();
        /*设置红外*/
        setInfrared();
        /*设置感应*/
        setVibration();
        /*设置外圈灯感应后*/
        setOutAfter();
        /*设置内圈灯感应后*/
        setInAfter();
        /*设置感应前蜂鸣器*/
        setBuzzerBefore();
        /*设置感应后蜂鸣器*/
        setBuzzerAfter();

    }

    private void setBuzzerAfter() {
        afterBuzzer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterBuzzerNone:
                        commandNew.setAfterBuzzerModel(CommandRules.BuzzerModel.NONE);
                        break;
                    case R.id.afterBuzzerShort:
                        commandNew.setAfterBuzzerModel(CommandRules.BuzzerModel.SHORT);
                        break;
                    case R.id.afterBuzzer_1s:
                        commandNew.setAfterBuzzerModel(CommandRules.BuzzerModel.LONG_1);
                        break;
                    case R.id.afterBuzzer_2s:
                        commandNew.setAfterBuzzerModel(CommandRules.BuzzerModel.LONG_2);
                        break;
                    default:
                }
            }
        });
    }

    private void setBuzzerBefore() {
        buzzer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.buzzerNone:
                        commandNew.setBeforeBuzzerModel(CommandRules.BuzzerModel.NONE);
                        break;
                    case R.id.buzzerShort:
                        commandNew.setBeforeBuzzerModel(CommandRules.BuzzerModel.SHORT);
                        break;
                    case R.id.buzzer_1s:
                        commandNew.setBeforeBuzzerModel(CommandRules.BuzzerModel.LONG_1);
                        break;
                    case R.id.buzzer_2s:
                        commandNew.setBeforeBuzzerModel(CommandRules.BuzzerModel.LONG_2);
                        break;
                    default:
                }
            }
        });
    }

    private void setInAfter() {
        /*内圈灯亮灯模式*/
        afterInBlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterInBlinkNone:
                        commandNew.setAfterInBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.afterInBlinkAlways:
                        commandNew.setAfterInBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.afterInBlinkSlow:
                        commandNew.setAfterInBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.afterInBlinkFast:
                        commandNew.setAfterInBlink(CommandRules.OpenModel.FAST);
                        break;
                    default:
                }
            }
        });

        /*内圈灯亮灯颜色*/
        afterInColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterInColorBlue:
                        commandNew.setAfterInColor(CommandRules.InColor.BLUE);
                        break;
                    case R.id.afterInColorRed:
                        commandNew.setAfterInColor(CommandRules.InColor.RED);
                        break;
                    case R.id.afterInColorPurple:
                        commandNew.setAfterInColor(CommandRules.InColor.PURPLE);
                        break;
                    default:

                }
            }
        });
    }

    private void setOutAfter() {
        /*外圈灯亮灯模式*/
        afterOutBlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterOutBlinkNone:
                        commandNew.setAfterOutBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.afterOutBlinkAlways:
                        commandNew.setAfterOutBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.afterOutBlinkSlow:
                        commandNew.setAfterOutBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.afterOutBlinkFast:
                        commandNew.setAfterOutBlink(CommandRules.OpenModel.FAST);
                        break;
                    default:
                }
            }
        });
        /*外圈灯亮灯颜色*/
        afterOutColor1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterOutColorBlue:
                        if (afterOutColorBlue.isChecked()) {
                            afterOutColor2.clearCheck();
                            commandNew.setAfterOutColor(CommandRules.OutColor.BLUE);
                        }
                        break;
                    case R.id.afterOutColorRed:
                        if (afterOutColorRed.isChecked()) {
                            afterOutColor2.clearCheck();
                            commandNew.setAfterOutColor(CommandRules.OutColor.RED);
                        }
                        break;
                    case R.id.afterOutColorGreen:
                        if (afterOutColorGreen.isChecked()) {
                            afterOutColor2.clearCheck();
                            commandNew.setAfterOutColor(CommandRules.OutColor.GREEN);
                        }
                        break;
                    case R.id.afterOutColorPurple:
                        if (afterOutColorPurple.isChecked()) {
                            afterOutColor2.clearCheck();
                            commandNew.setAfterOutColor(CommandRules.OutColor.PURPLE);
                        }
                        break;
                    default:
                }
            }
        });
        afterOutColor2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.afterOutColorCyan:
                        if (afterOutColorCyan.isChecked()) {
                            afterOutColor1.clearCheck();
                            commandNew.setAfterOutColor(CommandRules.OutColor.CYAN);
                        }
                        break;
                    case R.id.afterOutColorYellow:
                        if (afterOutColorYellow.isChecked()) {
                            afterOutColor1.clearCheck();
                            commandNew.setAfterOutColor(CommandRules.OutColor.YELLOW);
                        }
                        break;
                    case R.id.afterOutColorWhite:
                        if (afterOutColorWhite.isChecked()) {
                            afterOutColor1.clearCheck();
                            commandNew.setAfterOutColor(CommandRules.OutColor.WHITE);
                        }
                        break;
                    default:
                }
            }
        });
    }

    private void setVibration() {
        /* 震动感应开关*/
        vibrationInduced.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.vibrationInducedOn:
                        commandNew.setVibrationInduced(CommandRules.VibrationInduced.OPEN);
                        break;
                    case R.id.vibrationInducedOff:
                        commandNew.setVibrationInduced(CommandRules.VibrationInduced.CLOSE);
                        break;
                    default:
                }
            }
        });
        /*震动强度*/
        vibrationIntensity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.vibrationIntensityL:
                        commandNew.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_L);
                        break;
                    case R.id.vibrationIntensityM:
                        commandNew.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_M);
                        break;
                    case R.id.vibrationIntensityH:
                        commandNew.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_H);
                        break;
                    default:
                }
            }
        });
        /*震动详情*/
        vibrationDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.vibrationDetailsOn:
                        commandNew.setVibrationDetails(CommandRules.VibrationDetails.OPEN);
                        break;
                    case R.id.vibrationDetailsOff:
                        commandNew.setVibrationDetails(CommandRules.VibrationDetails.CLOSE);
                        break;
                    default:
                }
            }
        });
    }

    private void setInfrared() {
        /*红外发射*/
        infraredEmission.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredEmissionOn:
                        commandNew.setInfraredEmission(CommandRules.InfraredEmission.OPEN);
                        break;
                    case R.id.infraredEmissionOff:
                        commandNew.setInfraredEmission(CommandRules.InfraredEmission.CLOSE);
                        break;
                    default:
                }
            }
        });
        /*红外感应*/
        infraredInduction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredInductionOn:
                        commandNew.setInfraredInduction(CommandRules.InfraredInduction.OPEN);
                        break;
                    case R.id.infraredInductionOff:
                        commandNew.setInfraredInduction(CommandRules.InfraredInduction.CLOSE);
                        break;
                    default:
                }
            }
        });
        /*红外模式*/
        infraredModel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredModelNormal:
                        commandNew.setInfraredModel(CommandRules.InfraredModel.NORMAL);
                        break;
                    case R.id.infraredModelContend:
                        commandNew.setInfraredModel(CommandRules.InfraredModel.CONTEND);
                        break;
                    default:
                }
            }
        });
        /*红外高度*/
        infraredHeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredHeightLow:
                        commandNew.setInfraredHeight(CommandRules.InfraredHeight.MIN_LOW);
                        break;
                    case R.id.infraredHeight_5cm:
                        commandNew.setInfraredHeight(CommandRules.InfraredHeight.LOW);
                        break;
                    case R.id.infraredHeight_30cm:
                        commandNew.setInfraredHeight(CommandRules.InfraredHeight.HIGH);
                        break;
                    case R.id.infraredHeightHigh:
                        commandNew.setInfraredHeight(CommandRules.InfraredHeight.MAX_HIGH);
                        break;
                    default:
                }
            }
        });
    }

    private void setInBefore() {
        /*内圈灯亮灯模式*/
        beforeInBlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeInBlinkNone:
                        commandNew.setBeforeInBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.beforeInBlinkAlways:
                        commandNew.setBeforeInBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.beforeInBlinkSlow:
                        commandNew.setBeforeInBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.beforeInBlinkFast:
                        commandNew.setBeforeInBlink(CommandRules.OpenModel.FAST);
                        break;
                    default:
                }
            }
        });
        /*内圈灯亮灯颜色*/
        beforeInColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeInColorBlue:
                        commandNew.setBeforeInColor(CommandRules.InColor.BLUE);
                        break;
                    case R.id.beforeInColorRed:
                        commandNew.setBeforeInColor(CommandRules.InColor.RED);
                        break;
                    case R.id.beforeInColorPurple:
                        commandNew.setBeforeInColor(CommandRules.InColor.PURPLE);
                        break;
                    default:

                }
            }
        });
    }

    private void setOutBefore() {
        /*外圈灯亮灯模式*/
        beforeOutBlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeOutBlinkNone:
                        commandNew.setBeforeOutBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.beforeOutBlinkAlways:
                        commandNew.setBeforeOutBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.beforeOutBlinkSlow:
                        commandNew.setBeforeOutBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.beforeOutBlinkFast:
                        commandNew.setBeforeOutBlink(CommandRules.OpenModel.FAST);
                        break;
                    default:
                }
            }
        });
        /*外圈灯亮灯颜色*/
        beforeOutColor1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeOutColorBlue:
                        if (beforeOutColorBlue.isChecked()) {
                            beforeOutColor2.clearCheck();
                            commandNew.setBeforeOutColor(CommandRules.OutColor.BLUE);
                        }
                        break;
                    case R.id.beforeOutColorRed:
                        if (beforeOutColorRed.isChecked()) {
                            beforeOutColor2.clearCheck();
                            commandNew.setBeforeOutColor(CommandRules.OutColor.RED);
                        }
                        break;
                    case R.id.beforeOutColorGreen:
                        if (beforeOutColorGreen.isChecked()) {
                            beforeOutColor2.clearCheck();
                            commandNew.setBeforeOutColor(CommandRules.OutColor.GREEN);
                        }
                        break;
                    case R.id.beforeOutColorPurple:
                        if (beforeOutColorPurple.isChecked()) {
                            beforeOutColor2.clearCheck();
                            commandNew.setBeforeOutColor(CommandRules.OutColor.PURPLE);
                        }
                        break;
                    default:
                }
            }
        });
        beforeOutColor2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeOutColorCyan:
                        if (beforeOutColorCyan.isChecked()) {
                            beforeOutColor1.clearCheck();
                            commandNew.setBeforeOutColor(CommandRules.OutColor.CYAN);
                        }
                        break;
                    case R.id.beforeOutColorYellow:
                        if (beforeOutColorYellow.isChecked()) {
                            beforeOutColor1.clearCheck();
                            commandNew.setBeforeOutColor(CommandRules.OutColor.YELLOW);
                        }
                        break;
                    case R.id.beforeOutColorWhite:
                        if (beforeOutColorWhite.isChecked()) {
                            beforeOutColor1.clearCheck();
                            commandNew.setBeforeOutColor(CommandRules.OutColor.WHITE);
                        }
                        break;
                    default:
                }
            }
        });
    }







}
