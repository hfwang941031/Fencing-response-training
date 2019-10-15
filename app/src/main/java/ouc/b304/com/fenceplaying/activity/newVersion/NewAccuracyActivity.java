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
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import ouc.b304.com.fenceplaying.Bean.Light;
import ouc.b304.com.fenceplaying.Dao.AccuracyScoresDao;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.entity.AccuracyScores;
import ouc.b304.com.fenceplaying.Dao.entity.Player;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.AccuracyAdapter;
import ouc.b304.com.fenceplaying.adapter.SaveResultAdapter;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;
import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.entity.TimeInfo;
import ouc.b304.com.fenceplaying.order.CommandNew;
import ouc.b304.com.fenceplaying.order.CommandRules;
import ouc.b304.com.fenceplaying.order.Order;
import ouc.b304.com.fenceplaying.order.OrderUtils;
import ouc.b304.com.fenceplaying.thread.ReceiveThread;
import ouc.b304.com.fenceplaying.thread.Timer;
import ouc.b304.com.fenceplaying.utils.PlayDaoUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;
import ouc.b304.com.fenceplaying.utils.newUtils.RealmUtils;

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
 * @author 王海峰 on 2019/5/28 15:03
 */
public class NewAccuracyActivity extends BaseActivity {
    @BindView(R.id.bt_run_cancel)
    ImageView btRunCancel;
    @BindView(R.id.layout_cancel)
    LinearLayout layoutCancel;
    @BindView(R.id.tv_change_light)
    TextView tvChangeLight;
    @BindView(R.id.btn_turnon)
    Button btnTurnon;
    @BindView(R.id.btn_turnoff)
    Button btnTurnoff;
    @BindView(R.id.sp_times)
    Spinner spTimes;
    @BindView(R.id.tv_avalibledevices)
    TextView tvAvalibledevices;
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
    @BindView(R.id.beforeOutBlinkNonefalse)
    RadioButton beforeOutBlinkNonefalse;
    @BindView(R.id.beforeOutBlinkAlwaysfalse)
    RadioButton beforeOutBlinkAlwaysfalse;
    @BindView(R.id.beforeOutBlinkSlowfalse)
    RadioButton beforeOutBlinkSlowfalse;
    @BindView(R.id.beforeOutBlinkFastfalse)
    RadioButton beforeOutBlinkFastfalse;
    @BindView(R.id.beforeOutBlinkfalse)
    RadioGroup beforeOutBlinkfalse;
    @BindView(R.id.beforeOutColorBluefalse)
    RadioButton beforeOutColorBluefalse;
    @BindView(R.id.beforeOutColorRedfalse)
    RadioButton beforeOutColorRedfalse;
    @BindView(R.id.beforeOutColorGreenfalse)
    RadioButton beforeOutColorGreenfalse;
    @BindView(R.id.beforeOutColorPurplefalse)
    RadioButton beforeOutColorPurplefalse;
    @BindView(R.id.beforeOutColor1false)
    RadioGroup beforeOutColor1false;
    @BindView(R.id.beforeOutColorCyanfalse)
    RadioButton beforeOutColorCyanfalse;
    @BindView(R.id.beforeOutColorYellowfalse)
    RadioButton beforeOutColorYellowfalse;
    @BindView(R.id.beforeOutColorWhitefalse)
    RadioButton beforeOutColorWhitefalse;
    @BindView(R.id.beforeOutColor2false)
    RadioGroup beforeOutColor2false;
    @BindView(R.id.beforeInBlinkNonefalse)
    RadioButton beforeInBlinkNonefalse;
    @BindView(R.id.beforeInBlinkAlwaysfalse)
    RadioButton beforeInBlinkAlwaysfalse;
    @BindView(R.id.beforeInBlinkSlowfalse)
    RadioButton beforeInBlinkSlowfalse;
    @BindView(R.id.beforeInBlinkFastfalse)
    RadioButton beforeInBlinkFastfalse;
    @BindView(R.id.beforeInBlinkfalse)
    RadioGroup beforeInBlinkfalse;
    @BindView(R.id.beforeInColorBluefalse)
    RadioButton beforeInColorBluefalse;
    @BindView(R.id.beforeInColorRedfalse)
    RadioButton beforeInColorRedfalse;
    @BindView(R.id.beforeInColorPurplefalse)
    RadioButton beforeInColorPurplefalse;
    @BindView(R.id.beforeInColorfalse)
    RadioGroup beforeInColorfalse;
    @BindView(R.id.infraredEmissionOnfalse)
    RadioButton infraredEmissionOnfalse;
    @BindView(R.id.infraredEmissionOfffalse)
    RadioButton infraredEmissionOfffalse;
    @BindView(R.id.infraredEmissionfalse)
    RadioGroup infraredEmissionfalse;
    @BindView(R.id.infraredInductionOnfalse)
    RadioButton infraredInductionOnfalse;
    @BindView(R.id.infraredInductionOfffalse)
    RadioButton infraredInductionOfffalse;
    @BindView(R.id.infraredInductionfalse)
    RadioGroup infraredInductionfalse;
    @BindView(R.id.infraredModelNormalfalse)
    RadioButton infraredModelNormalfalse;
    @BindView(R.id.infraredModelContendfalse)
    RadioButton infraredModelContendfalse;
    @BindView(R.id.infraredModelfalse)
    RadioGroup infraredModelfalse;
    @BindView(R.id.infraredHeightLowfalse)
    RadioButton infraredHeightLowfalse;
    @BindView(R.id.infraredHeight_5cmfalse)
    RadioButton infraredHeight5cmfalse;
    @BindView(R.id.infraredHeight_30cmfalse)
    RadioButton infraredHeight30cmfalse;
    @BindView(R.id.infraredHeightHighfalse)
    RadioButton infraredHeightHighfalse;
    @BindView(R.id.infraredHeightfalse)
    RadioGroup infraredHeightfalse;
    @BindView(R.id.vibrationInducedOnfalse)
    RadioButton vibrationInducedOnfalse;
    @BindView(R.id.vibrationInducedOfffalse)
    RadioButton vibrationInducedOfffalse;
    @BindView(R.id.vibrationInducedfalse)
    RadioGroup vibrationInducedfalse;
    @BindView(R.id.vibrationIntensityLfalse)
    RadioButton vibrationIntensityLfalse;
    @BindView(R.id.vibrationIntensityMfalse)
    RadioButton vibrationIntensityMfalse;
    @BindView(R.id.vibrationIntensityHfalse)
    RadioButton vibrationIntensityHfalse;
    @BindView(R.id.vibrationIntensityfalse)
    RadioGroup vibrationIntensityfalse;
    @BindView(R.id.vibrationDetailsOnfalse)
    RadioButton vibrationDetailsOnfalse;
    @BindView(R.id.vibrationDetailsOfffalse)
    RadioButton vibrationDetailsOfffalse;
    @BindView(R.id.vibrationDetailsfalse)
    RadioGroup vibrationDetailsfalse;
    @BindView(R.id.buzzerNonefalse)
    RadioButton buzzerNonefalse;
    @BindView(R.id.buzzerShortfalse)
    RadioButton buzzerShortfalse;
    @BindView(R.id.buzzer_1sfalse)
    RadioButton buzzer1sfalse;
    @BindView(R.id.buzzer_2sfalse)
    RadioButton buzzer2sfalse;
    @BindView(R.id.buzzerfalse)
    RadioGroup buzzerfalse;
    @BindView(R.id.tvTotalTime)
    TextView tvTotalTime;
    @BindView(R.id.btn_startrun)
    Button btnStartrun;
    @BindView(R.id.btn_stoprun)
    Button btnStoprun;
    @BindView(R.id.tv_hit_right_num)
    TextView tvHitRightNum;
    @BindView(R.id.tv_hit_wrong_num)
    TextView tvHitWrongNum;
    @BindView(R.id.lvTimes)
    ListView lvTimes;
    @BindView(R.id.showAvergeScore)
    TextView showAvergeScore;
    @BindView(R.id.avergeScore)
    TextView avergeScore;
    @BindView(R.id.bt_save)
    Button btSave;

    private boolean endFlag = false;
    //每次训练的时间集合
    private ArrayList<ArrayList<Integer>> timeList = new ArrayList<>();

    private final static int TIME_RECEIVE = 1;
    private final static int POWER_RECEIVE = 2;
    private final static int UPDATE_TIMES = 3;
    private final static int STOP_TRAINING = 4;
    private final static int TIME_RECEIVE_FOR_MATRIX = 5;
    private final static int CHANGE_LISTVIEW = 6;
    //训练时间
    private long trainingTime = 0;
    //训练开始时间
    private long beginTime = 0;
    private Timer timer;
    //训练开始标志
    private boolean trainingBeginFlag = false;
    //存储在当前页面中可以用的设备编号
    private List<DbLight> list = new ArrayList<>();
    //
    //12个灯矩阵列表
    private ArrayList<ArrayList<Light>> lightListForMatrix = new ArrayList<>();
    //当前组亮起的3盏灯，0：目标灯 1~n：干扰项
    private ArrayList<DbLight> nowLightListForMatrix = new ArrayList<>();
    //计分
    private int counterForMatrixTraining = 0;
    //统计触碰到错误灯的次数
    private int counterForWrongNum = 0;
    //向adapter中传递是否命中
    private ArrayList<Boolean> isHitArrayList = new ArrayList<>();
    //干扰项编号
    private int interfenceTerm1 = 0;
    private int interfenceTerm2 = 0;
    private AccuracyAdapter accuracyAdapter;
    private ArrayAdapter<String> arrayAdapter;
    //设置一个布尔变量控制保存按钮的可按与否，当一次训练结束后，可以点击该保存按钮进行保存，点击过之后，不能再次进行点击，除非先进行下一次训练并得到一组时间值；
    private boolean saveBtnIsClickable = false;
    private Date date;
    //对话框中listview的适配器
    private SaveResultAdapter saveResultAdapter;
    private List<String> nameList;
    private AccuracyScores accuracyScores;
    private AccuracyScoresDao accuracyScoresDao;
    private float hitRate = 0;

    //灯光颜色
    private CommandRules.OutColor lightColor = CommandRules.OutColor.BLUE;

    //干扰灯光色
    private CommandRules.OutColor wrongLightColor = CommandRules.OutColor.RED;

    private PlayerDao playerDao;
    private Player player;
    private Context context;
    boolean timedownFlag = false;

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
                        analyzeDateForMatrix2(timeInfo);

                    }
                    break;
            }
        }
    };
    private Realm mRealm;

    private void analyzeDateForMatrix2(final TimeInfo info) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!timedownFlag) {
                    Log.i("analyzeDFM", info.getAddress() + "======" + nowLightListForMatrix.get(0).getAddress());
                    if (info.getAddress().equals(nowLightListForMatrix.get(0).getAddress())) {
                        counterForMatrixTraining++;
                        Message message = handler.obtainMessage();
                        message.what = CHANGE_LISTVIEW;
                        handler.sendMessage(message);
//                    tvHitRightNum.setText(counterForMatrixTraining + "");
                        isHitArrayList.add(true);
                        OrderUtils.getInstance().turnOffLightList(nowLightListForMatrix);

                    } else {
                        counterForWrongNum++;
                        Message message = handler.obtainMessage();
                        message.what = CHANGE_LISTVIEW;
                        handler.sendMessage(message);
//                    tvHitWrongNum.setText(counterForWrongNum + "");
                        isHitArrayList.add(false);
                        OrderUtils.getInstance().turnOffLightList(nowLightListForMatrix);

                    }
                    Random random = new Random();
                    int randomNum3 = random.nextInt(3) + 1;
                    try {
                        Thread.sleep(randomNum3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        openLightForMatrixTraining();
                    }
                } else {
                    OrderUtils.getInstance().turnOffLightList(nowLightListForMatrix);
                }


            }
        }).start();
    }
    private void analyzeDateForMatrix(TimeInfo info) {
        Log.i("analyzeDFM", info.getAddress() + "======" + nowLightListForMatrix.get(0).getAddress());
        if (info.getAddress().equals(nowLightListForMatrix.get(0).getAddress())) {
            counterForMatrixTraining++;
            tvHitRightNum.setText(counterForMatrixTraining + "");
            isHitArrayList.add(true);
            OrderUtils.getInstance().turnOffLightList(nowLightListForMatrix);
            openLightForMatrixTraining();
        } else {
            counterForWrongNum++;
            tvHitWrongNum.setText(counterForWrongNum + "");
            isHitArrayList.add(false);
            OrderUtils.getInstance().turnOffLightList(nowLightListForMatrix);
            openLightForMatrixTraining();
        }
        avergeScore.setText((int) (100 * counterForMatrixTraining / (counterForWrongNum + counterForMatrixTraining)) + "%");
        accuracyAdapter.notifyDataSetChanged();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Timer.TIMER_FLAG:
                    String time = msg.obj.toString();
                    tvTotalTime.setText("总时间" + time);
                    break;
                case Timer.TIMER_DOWN:

                    String time1 = msg.obj.toString();
                    tvTotalTime.setText("总时间" + time1);
                    if (timer.time >= trainingTime * 1000) {
                        timedownFlag = true;
                        stopTraining();
                        saveBtnIsClickable = true;
                        hitRate = (float) counterForMatrixTraining / (float) (counterForWrongNum + counterForMatrixTraining);
                    }
                    break;


                case STOP_TRAINING:
                    stopTraining();
                    break;

                case CHANGE_LISTVIEW:
                    tvHitRightNum.setText(counterForMatrixTraining + "");
                    tvHitWrongNum.setText(counterForWrongNum + "");
                    avergeScore.setText((int) (100 * counterForMatrixTraining / (counterForWrongNum + counterForMatrixTraining)) + "%");
                    accuracyAdapter.notifyDataSetChanged();
                    break;

            }
        }
    };


    private void stopTraining() {
        trainingBeginFlag = false;
        //停止接收线程
        OrderUtils.getInstance().closeAllLight(list);
        timer.stopTimer();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccuracy);
        ButterKnife.bind(this);
        initField();
        initData();
        initView();
        setListener();
        registerReceiver();
        updateLightName();
    }

    /*注册广播*/
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConfig.ACTION_TIME_INFO);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void initView() {
        accuracyAdapter = new AccuracyAdapter(context, isHitArrayList);
        lvTimes.setAdapter(accuracyAdapter);
    }

    private void initData() {
        for (DbLight dbLight : AppConfig.sDbLights) {
            list.add(dbLight);
            if (!"0000".equals(dbLight.getPower())) {
                dbLight.setUsable(true);
            }
        }
        //初始化String类型的运动员姓名列表
        nameList = new ArrayList<>();

    }

    public void setListener() {
        //训练时间spinner监听
        final String[] trainingTimeArray = {"1min", "3min", "5min", "10min", "15min", "20min"};
        arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, trainingTimeArray);
        spTimes.setAdapter(arrayAdapter);
        spTimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spTimes.getItemAtPosition(i).toString()) {
                    case "1min":
                        trainingTime = 60;
                        break;
                    case "3min":
                        trainingTime = 180;
                        break;
                    case "5min":
                        trainingTime = 300;
                        break;
                    case "10min":
                        trainingTime = 600;
                        break;
                    case "15min":
                        trainingTime = 900;
                        break;
                    case "20min":
                        trainingTime = 1200;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //TODO:参数设置RB监听


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

    @OnClick({R.id.bt_run_cancel, R.id.btn_turnon, R.id.btn_turnoff, R.id.btn_startrun, R.id.btn_stoprun, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_run_cancel:
                this.finish();
                break;
            case R.id.btn_turnon:
                OrderUtils.getInstance().turnOnAllLight(AppConfig.sDbLights);
                break;
            case R.id.btn_turnoff:
                OrderUtils.getInstance().closeAllLight(AppConfig.sDbLights);
                break;
            case R.id.btn_startrun:
                if (list.size() < 12) {
                    Toast.makeText(context, "设备不足", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (trainingBeginFlag == false) {
                    startTrainingForMatrix();
                } else {
                    Toast.makeText(context, "训练进行中", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_stoprun:
                if (trainingBeginFlag == true) {
                    stopTraining();
                } else {

                }
                break;
            case R.id.bt_save:
                if (saveBtnIsClickable) {
                    if (isHitArrayList.size() == 0) {
                        Toast.makeText(context, "成绩列表为空，无法进行保存！请先进行训练", Toast.LENGTH_SHORT).show();
                    } else {
                        //确定保存时间
                        date = new Date();

                       /* //将Integer类型的List转换成String类型
                        IntegerToStringUtils.integerToString(timeList, scoreList);*/
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
                                accuracyScores = new AccuracyScores();
                                accuracyScores.setPlayerId(player.getId());
                                accuracyScores.setDate(date);
                                accuracyScores.setTrainingTime((int) (trainingTime / 60));
                                accuracyScores.setHitRightNum(counterForMatrixTraining);
                                accuracyScores.setHitWrongNum(counterForWrongNum);
                                accuracyScores.setHitRate(hitRate);
                                //插入成绩实体
                                accuracyScoresDao.insert(accuracyScores);
                                //给出数据插入成功提示
                                Toast.makeText(context, "数据插入成功", Toast.LENGTH_SHORT).show();
                                //将保存按钮设置为不可点击
                                saveBtnIsClickable = false;
                                saveDialog.dismiss();
                            }
                        });
                    }

                } else {
                    Toast.makeText(context, "请勿对该成绩进行二次保存,请进行下一次训练后再执行保存！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void startTrainingForMatrix() {
        trainingBeginFlag = true;

        for (int i = 0; i < 5; i++) {
            nowLightListForMatrix.add(null);
        }
        counterForMatrixTraining = 0;
        counterForWrongNum = 0;
        tvHitRightNum.setText("00");
        tvHitWrongNum.setText("00");
        avergeScore.setText("");
        isHitArrayList.clear();
        accuracyAdapter.notifyDataSetChanged();
        beginTime = System.currentTimeMillis();

        timer = new Timer(handler, (int) (trainingTime * 1000));
        timer.setBeginTime(System.currentTimeMillis());
        timer.start();

        openLightForMatrixTraining();
    }

    private void openLightForMatrixTraining() {
        Random random = new Random();
        int randomMiddleLight = random.nextInt(12);
        nowLightListForMatrix.clear();
        nowLightListForMatrix.add(list.get(randomMiddleLight));
        inputInterfenceTerm(randomMiddleLight);
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        CommandNew commandNew = new CommandNew();
        commandNew.setBeforeOutColor(lightColor);
        commandNew.setInfraredModel(CommandRules.InfraredModel.NORMAL);
        commandNew.setInfraredEmission(CommandRules.InfraredEmission.OPEN);
        commandNew.setInfraredInduction(CommandRules.InfraredInduction.OPEN);
        order.setCommandNew(commandNew);
        order.setLight(list.get(randomMiddleLight));
        orderList.add(order);
        for (int i = 1; i < nowLightListForMatrix.size(); i++) {
            Order order1 = new Order();
            CommandNew commandNew1 = new CommandNew();
            commandNew1.setBeforeOutColor(wrongLightColor);
            commandNew1.setInfraredModel(CommandRules.InfraredModel.NORMAL);
            commandNew1.setInfraredEmission(CommandRules.InfraredEmission.OPEN);
            commandNew1.setInfraredInduction(CommandRules.InfraredInduction.OPEN);
            order1.setCommandNew(commandNew1);
            order1.setLight(nowLightListForMatrix.get(i));
            orderList.add(order1);
        }
        OrderUtils.getInstance().sendCommand(orderList);
    }

    private void inputInterfenceTerm(int randomMiddleLight) {
        int dY = randomMiddleLight % 4;
        int dX = randomMiddleLight / 4;
        Log.i("promise", dX + "======" + dY);
        switch (dY) {
            case 0:
                if (dX == 0) {
                    nowLightListForMatrix.add(list.get(1));
                    nowLightListForMatrix.add(list.get(4));
                } else if (dX == 1) {
                    nowLightListForMatrix.add(list.get(0));
                    nowLightListForMatrix.add(list.get(5));
                    nowLightListForMatrix.add(list.get(8));
                } else {
                    nowLightListForMatrix.add(list.get(4));
                    nowLightListForMatrix.add(list.get(9));
                }
                break;
            case 1:
                if (dX == 0) {
                    nowLightListForMatrix.add(list.get(0));
                    nowLightListForMatrix.add(list.get(5));
                    nowLightListForMatrix.add(list.get(2));
                } else if (dX == 1) {
                    nowLightListForMatrix.add(list.get(1));
                    nowLightListForMatrix.add(list.get(4));
                    nowLightListForMatrix.add(list.get(9));
                    nowLightListForMatrix.add(list.get(6));
                } else {
                    nowLightListForMatrix.add(list.get(8));
                    nowLightListForMatrix.add(list.get(5));
                    nowLightListForMatrix.add(list.get(10));
                }
                break;
            case 2:
                if (dX == 0) {
                    nowLightListForMatrix.add(list.get(1));
                    nowLightListForMatrix.add(list.get(6));
                    nowLightListForMatrix.add(list.get(3));
                } else if (dX == 1) {
                    nowLightListForMatrix.add(list.get(5));
                    nowLightListForMatrix.add(list.get(2));
                    nowLightListForMatrix.add(list.get(7));
                    nowLightListForMatrix.add(list.get(10));
                } else {
                    nowLightListForMatrix.add(list.get(9));
                    nowLightListForMatrix.add(list.get(6));
                    nowLightListForMatrix.add(list.get(11));
                }
                break;
            case 3:
                if (dX == 0) {
                    nowLightListForMatrix.add(list.get(2));
                    nowLightListForMatrix.add(list.get(7));
                } else if (dX == 1) {
                    nowLightListForMatrix.add(list.get(3));
                    nowLightListForMatrix.add(list.get(6));
                    nowLightListForMatrix.add(list.get(11));
                } else {
                    nowLightListForMatrix.add(list.get(10));
                    nowLightListForMatrix.add(list.get(7));
                }
                break;
        }
    }

    //在oncreate中更新设备编号
    private void updateLightName() {
        //判空操作，否则进入Test报空指针
        if (AppConfig.sDevice != null) {
            //每次打开当前页面都要从数据库中读取灯的编号，否则编号默认为递增的自然数
            getSaveLight();
        }
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

    //初始化字段值
    private void initField() {
        context = getApplicationContext();
        mRealm = Realm.getDefaultInstance();
        playerDao = GreenDaoInitApplication.getInstances().getDaoSession().getPlayerDao();
        accuracyScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getAccuracyScoresDao();
    }
}
