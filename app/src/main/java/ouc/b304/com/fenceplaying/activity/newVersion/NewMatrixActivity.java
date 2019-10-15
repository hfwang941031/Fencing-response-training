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
import ouc.b304.com.fenceplaying.Bean.Constant;
import ouc.b304.com.fenceplaying.Dao.MatrixScoresDao;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.SingleLineScoresDao;
import ouc.b304.com.fenceplaying.Dao.entity.MatrixScores;
import ouc.b304.com.fenceplaying.Dao.entity.Player;
import ouc.b304.com.fenceplaying.Dao.entity.SingleLineScores;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.MatrixAdapter;
import ouc.b304.com.fenceplaying.adapter.SaveResultAdapter;
import ouc.b304.com.fenceplaying.adapter.SingleLineAdapter;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;
import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.entity.TimeInfo;
import ouc.b304.com.fenceplaying.order.CommandNew;
import ouc.b304.com.fenceplaying.order.CommandRules;
import ouc.b304.com.fenceplaying.order.Order;
import ouc.b304.com.fenceplaying.order.OrderUtils;
import ouc.b304.com.fenceplaying.thread.Timer;
import ouc.b304.com.fenceplaying.utils.IntegerToStringUtils;
import ouc.b304.com.fenceplaying.utils.NumberUtils;
import ouc.b304.com.fenceplaying.utils.PlayDaoUtils;
import ouc.b304.com.fenceplaying.utils.ScoreUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;
import ouc.b304.com.fenceplaying.utils.newUtils.RealmUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.ToastUtils;

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
 * @author 王海峰 on 2019/5/27 13:36
 */
public class NewMatrixActivity extends BaseActivity {

    private static final String TAG = "NewMatrixActivity";
    @BindView(R.id.bt_run_cancel)
    ImageView btRunCancel;
    @BindView(R.id.layout_cancel)
    LinearLayout layoutCancel;
    @BindView(R.id.btn_turnon)
    Button btnTurnon;
    @BindView(R.id.btn_turnoff)
    Button btnTurnoff;
    @BindView(R.id.tv_traintimes)
    TextView tvTraintimes;
    @BindView(R.id.sp_times)
    Spinner spTimes;
    @BindView(R.id.tv_devicenumber)
    TextView tvDevicenumber;
    @BindView(R.id.sp_devicenumbers)
    Spinner spDevicenumbers;
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
    @BindView(R.id.lvTimes)
    ListView lvTimes;
    @BindView(R.id.showAvergeScore)
    TextView showAvergeScore;
    @BindView(R.id.avergeScore)
    TextView avergeScore;
    @BindView(R.id.bt_save)
    Button btSave;
    private Context mContext;
    private Realm mRealm;
    private PlayerDao playerDao;
    private boolean endFlag = false;
    private List<DbLight> selectedLights = new ArrayList<>();
    //训练页面初始化以后可用的灯数量（建立好连接的灯数量）
    private int numOfUsableLight = 0;
    //可用设备数量集合
    private List<Integer> lightNumberList = new ArrayList<>();
    //训练次数Spinner适配器
    private ArrayAdapter<String> spTimesAdapter;
    //灯数量Spinner适配器
    private ArrayAdapter<Integer> spLightNumAdapter;
    //选择的灯数量
    private int numberOfLight = 0;
    //选择的训练次数
    private int trainTimes = 0;
    //目标灯
    private DbLight trueLight;
    //干扰灯
    private DbLight falseLight;
    //目标灯的参数设置类
    private CommandNew commandNewTrue = new CommandNew();
    //干扰灯的参数设置类
    private CommandNew commandNewFalse = new CommandNew();
    //训练开始标志
    private boolean trainingBeginFlag = false;
    //更新成绩列表的标志
    private final int UPDATE_TIMES = 3;
    //训练停止标志位
    private final int STOP_TRAINING = 4;
    //训练平均成绩
    private float averageScore = 0;
    //设置一个布尔变量控制保存按钮的可按与否，当一次训练结束后，可以点击该保存按钮进行保存，点击过之后，不能再次进行点击，除非先进行下一次训练并得到一组时间值；
    private boolean saveBtnIsClickable = false;
    //最终成绩列表
    private List<String> scoreList;
    //对话框中listview的适配器
    private SaveResultAdapter saveResultAdapter;
    //每次训练的时间集合
    private ArrayList<Integer> timeList;
    //定义成绩列表适配器
    private MatrixAdapter matrixAdapter;
    //训练开始时间
    private long startTime;
    //计时类
    private Timer timer;
    //计数器
    private int counter = 0;
    //时间
    private Date date;
    //运动员名字集合（从数据库中取出来的）
    private List<String> nameList;
    //运动员实体
    private Player player;
    //抗干扰成绩实体类
    private MatrixScores matrixScores;
    //抗干扰成绩dao类
    private MatrixScoresDao matrixScoresDao;


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
                        analyzeTimeData2(timeInfo);
                    }
                    break;
            }
        }
    };

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
                    matrixAdapter.setTimeList(timeList);
                    matrixAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmatrix);
        ButterKnife.bind(this);
        initField();
        registerReceiver();
        updateLightName();
        initData();
        initView();
        initListener();

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

    @OnClick({R.id.bt_run_cancel, R.id.btn_turnon, R.id.btn_turnoff, R.id.btn_startrun, R.id.btn_stoprun, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_run_cancel:
                this.finish();
                break;
            case R.id.btn_turnon:
                //开启选好的灯，方便寻找
                if (selectedLights == null || selectedLights.size() < 3) {
                    Toast.makeText(mContext, "请先开启更多设备，并选择好设备数量再点击该按钮！", Toast.LENGTH_SHORT).show();
                } else {
                    OrderUtils.getInstance().turnOnAllLight(selectedLights);
                    btnTurnon.setClickable(false);
                    btnTurnoff.setClickable(true);
                }
                break;
            case R.id.btn_turnoff:
                //关闭选好的灯
                OrderUtils.getInstance().turnOffLightList(selectedLights);
                btnTurnon.setClickable(true);
                btnTurnoff.setClickable(false);
                break;
            case R.id.btn_startrun:
                //训练开始的时候把上次的成绩置空
                averageScore = 0;
                avergeScore.setText("");
                startTraining();
                btnTurnon.setClickable(false);
                btnTurnoff.setClickable(false);
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
                                matrixScores = new MatrixScores();
                                matrixScores.setPlayerId(player.getId());

                                matrixScores.setAverageScores(averageScore);
                                matrixScores.setDate(date);
                                matrixScores.setScoresList(scoreList);
                                matrixScores.setTrainingTimes(trainTimes);
                                //插入成绩实体
                                matrixScoresDao.insert(matrixScores);
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
        }
    }

    //初始化字段值
    private void initField() {
        mContext = getApplicationContext();
        mRealm = Realm.getDefaultInstance();
        playerDao = GreenDaoInitApplication.getInstances().getDaoSession().getPlayerDao();
        matrixScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getMatrixScoresDao();
    }

    //注册广播
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConfig.ACTION_TIME_INFO);
        registerReceiver(mBroadcastReceiver, intentFilter);
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

    private void analyzeTimeData2(final TimeInfo timeInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("ana", "时间解析");
                //关所有灯（关闭目标灯时，连带着关闭干扰项）
                OrderUtils.getInstance().turnOffLight(falseLight);
                counter += 1;
                Log.d("时间信息", timeInfo.getName() + timeInfo.getTime());

                if (counter > trainTimes) {
                    endFlag = true;
                }
                Log.d("计数器值", counter + "");
                timeList.add((int) timeInfo.getTime());
                /*Random random = new Random();
                int randomNum3=random.nextInt(2)+1;*/
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //设置目标灯
                int randomNum1 = NumberUtils.randomNumber(numberOfLight);
                trueLight = selectedLights.get(randomNum1);
                List<Order> orderList = new ArrayList<>();
                trueLight.setOpen(true);
                Order order = new Order();
                order.setLight(trueLight);
                order.setCommandNew(commandNewTrue);
                Log.d("目标灯命令", order.toString() + "");

                orderList.add(order);
                //设置干扰灯
                int randomNum2 = NumberUtils.randomNumber(numberOfLight);
                falseLight = selectedLights.get(randomNum2);
                falseLight.setOpen(true);
                Order order1 = new Order();
                order1.setLight(falseLight);
                order1.setCommandNew(commandNewFalse);
                Log.d("干扰灯命令", order1.toString() + "");
                orderList.add(order1);
                //开目标灯和干扰灯
                OrderUtils.getInstance().sendCommand(orderList);
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
        }).start();
    }

    //解析抗干扰训练返回的时间数据
    private void analyzeTimeData(TimeInfo timeInfo) {
        Log.d("ana", "时间解析");
        //关所有灯（关闭目标灯时，连带着关闭干扰项）
        OrderUtils.getInstance().turnOffLightList(selectedLights);
        counter += 1;
        Log.d("时间信息", timeInfo.getName() + timeInfo.getTime());

        if (counter > trainTimes) {
            endFlag = true;
        }
        Log.d("计数器值", counter + "");
        timeList.add((int) timeInfo.getTime());
        //设置目标灯
        int randomNum1 = NumberUtils.randomNumber(numberOfLight);
        trueLight = selectedLights.get(randomNum1);
        Log.d("再次开灯前状态", trueLight.toString());
        List<Order> orderList = new ArrayList<>();
        trueLight.setOpen(true);
        Order order = new Order();
        order.setLight(trueLight);
        order.setCommandNew(commandNewTrue);
        orderList.add(order);
        //设置干扰灯
        int randomNum2 = NumberUtils.randomNumber(numberOfLight);
        falseLight = selectedLights.get(randomNum2);
        Log.d("再次开灯前干扰灯状态", falseLight.toString());
        /*List<Order> orderList1 = new ArrayList<>();*/
        falseLight.setOpen(true);
        Order order1 = new Order();
        order1.setLight(falseLight);
        order1.setCommandNew(commandNewFalse);
        orderList.add(order1);
        /*orderList1.add(order1);*/
        //开目标灯和干扰灯
        OrderUtils.getInstance().sendCommand(orderList);
        /*OrderUtils.getInstance().sendCommand(orderList1);
         */
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

    //判断训练是否到了结束位置
    private boolean isTrainingOver() {
        if (counter >= trainTimes) {
            return true;
        } else {
            return false;
        }
    }

    //初始化控件及其数据
    private void initView() {
        //设置spinner次数适配器
        spTimesAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, Constant.trainingTimes);
        spTimes.setAdapter(spTimesAdapter);
        //设置spinner灯数量适配器
        spLightNumAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, lightNumberList);
        spDevicenumbers.setAdapter(spLightNumAdapter);
        //将选中的训练次数保存到变量中
        spTimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                trainTimes = Integer.parseInt(String.valueOf(spTimes.getItemAtPosition(i)));
                Log.d("训练次数：", trainTimes + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //将选中的灯数量保存到变量中
        spDevicenumbers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numberOfLight = (int) spDevicenumbers.getItemAtPosition(i);
                Log.d("参训灯数量：", numberOfLight + "");
                //将参训灯保存到list中，根据灯数量，取出全局灯的前n个,先清空再添加
                selectedLights.clear();
                for (int j = 0; j < numberOfLight; j++) {
                    DbLight light = AppConfig.sDbLights.get(j);
                    selectedLights.add(light);
                    Log.d("参训灯编号：", light.getName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //初始化成绩显示listview的适配器
        matrixAdapter = new MatrixAdapter(mContext);
        lvTimes.setAdapter(matrixAdapter);

    }

    //初始化数据
    private void initData() {
        //初始化可用灯数量
        int num = 0;
        if (AppConfig.sDbLights != null && AppConfig.sDbLights.size() >= 3) {
            for (DbLight dbLight : AppConfig.sDbLights) {
                num++;
            }
            numOfUsableLight = num;

            //填充灯Spinner数量
            for (int i = 3; i <= numOfUsableLight; i++) {
                lightNumberList.add(i);
            }
            //

        } else {
            ToastUtils.makeText(mContext, "可用设备数小于3，请开启更多设备再进行训练！", Toast.LENGTH_SHORT).show();
        }
        //初始化训练时间集合
        timeList = new ArrayList<>();
        //初始化最终成绩集合
        scoreList = new ArrayList<>();
        //初始化运动员姓名集合
        nameList = new ArrayList<>();
        //停止训练按钮设置为不可点击
        btnStoprun.setClickable(false);

    }

    //初始化监听器
    private void initListener() {
        //目标灯参数设置
        /*设置外圈灯感应前*/
        setOutBefore();
        /*设置内圈灯感应前*/
        setInBefore();
        /*设置红外*/
        setInfrared();
        /*设置震动*/
        setVibration();
        /*设置感应前蜂鸣器*/
        setBuzzerBefore();

        //干扰灯参数设置
        /*设置外圈灯感应前*/
        setOutBeforeFalse();
        /*设置内圈灯感应前*/
        setInBeforeFalse();
        /*设置红外*/
        setInfraredFalse();
        /*设置震动*/
        setVibrationFalse();
        /*设置感应前蜂鸣器*/
        setBuzzerBeforeFalse();

    }

    //目标灯参数设置
    //蜂鸣器设置
    private void setBuzzerBefore() {
        buzzer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.buzzerNone:
                        commandNewTrue.setBeforeBuzzerModel(CommandRules.BuzzerModel.NONE);
                        break;
                    case R.id.buzzerShort:
                        commandNewTrue.setBeforeBuzzerModel(CommandRules.BuzzerModel.SHORT);
                        break;
                    case R.id.buzzer_1s:
                        commandNewTrue.setBeforeBuzzerModel(CommandRules.BuzzerModel.LONG_1);
                        break;
                    case R.id.buzzer_2s:
                        commandNewTrue.setBeforeBuzzerModel(CommandRules.BuzzerModel.LONG_2);
                        break;
                    default:
                }
            }
        });
    }

    //震动设置
    private void setVibration() {
        /* 震动感应开关*/
        vibrationInduced.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.vibrationInducedOn:
                        commandNewTrue.setVibrationInduced(CommandRules.VibrationInduced.OPEN);
                        break;
                    case R.id.vibrationInducedOff:
                        commandNewTrue.setVibrationInduced(CommandRules.VibrationInduced.CLOSE);
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
                        commandNewTrue.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_L);
                        break;
                    case R.id.vibrationIntensityM:
                        commandNewTrue.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_M);
                        break;
                    case R.id.vibrationIntensityH:
                        commandNewTrue.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_H);
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
                        commandNewTrue.setVibrationDetails(CommandRules.VibrationDetails.OPEN);
                        break;
                    case R.id.vibrationDetailsOff:
                        commandNewTrue.setVibrationDetails(CommandRules.VibrationDetails.CLOSE);
                        break;
                    default:
                }
            }
        });
    }

    //红外设置
    private void setInfrared() {
        /*红外发射*/
        infraredEmission.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredEmissionOn:
                        commandNewTrue.setInfraredEmission(CommandRules.InfraredEmission.OPEN);
                        break;
                    case R.id.infraredEmissionOff:
                        commandNewTrue.setInfraredEmission(CommandRules.InfraredEmission.CLOSE);
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
                        commandNewTrue.setInfraredInduction(CommandRules.InfraredInduction.OPEN);
                        break;
                    case R.id.infraredInductionOff:
                        commandNewTrue.setInfraredInduction(CommandRules.InfraredInduction.CLOSE);
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
                        commandNewTrue.setInfraredModel(CommandRules.InfraredModel.NORMAL);
                        break;
                    case R.id.infraredModelContend:
                        commandNewTrue.setInfraredModel(CommandRules.InfraredModel.CONTEND);
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
                        commandNewTrue.setInfraredHeight(CommandRules.InfraredHeight.MIN_LOW);
                        break;
                    case R.id.infraredHeight_5cm:
                        commandNewTrue.setInfraredHeight(CommandRules.InfraredHeight.LOW);
                        break;
                    case R.id.infraredHeight_30cm:
                        commandNewTrue.setInfraredHeight(CommandRules.InfraredHeight.HIGH);
                        break;
                    case R.id.infraredHeightHigh:
                        commandNewTrue.setInfraredHeight(CommandRules.InfraredHeight.MAX_HIGH);
                        break;
                    default:
                }
            }
        });
    }

    //内圈灯设置
    private void setInBefore() {
        /*内圈灯亮灯模式*/
        beforeInBlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeInBlinkNone:
                        commandNewTrue.setBeforeInBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.beforeInBlinkAlways:
                        commandNewTrue.setBeforeInBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.beforeInBlinkSlow:
                        commandNewTrue.setBeforeInBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.beforeInBlinkFast:
                        commandNewTrue.setBeforeInBlink(CommandRules.OpenModel.FAST);
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
                        commandNewTrue.setBeforeInColor(CommandRules.InColor.BLUE);
                        break;
                    case R.id.beforeInColorRed:
                        commandNewTrue.setBeforeInColor(CommandRules.InColor.RED);
                        break;
                    case R.id.beforeInColorPurple:
                        commandNewTrue.setBeforeInColor(CommandRules.InColor.PURPLE);
                        break;
                    default:

                }
            }
        });
    }

    //外圈灯设置
    private void setOutBefore() {
        /*外圈灯亮灯模式*/
        beforeOutBlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeOutBlinkNone:
                        commandNewTrue.setBeforeOutBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.beforeOutBlinkAlways:
                        commandNewTrue.setBeforeOutBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.beforeOutBlinkSlow:
                        commandNewTrue.setBeforeOutBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.beforeOutBlinkFast:
                        commandNewTrue.setBeforeOutBlink(CommandRules.OpenModel.FAST);
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
                            commandNewTrue.setBeforeOutColor(CommandRules.OutColor.BLUE);
                        }
                        break;
                    case R.id.beforeOutColorRed:
                        if (beforeOutColorRed.isChecked()) {
                            beforeOutColor2.clearCheck();
                            commandNewTrue.setBeforeOutColor(CommandRules.OutColor.RED);
                        }
                        break;
                    case R.id.beforeOutColorGreen:
                        if (beforeOutColorGreen.isChecked()) {
                            beforeOutColor2.clearCheck();
                            commandNewTrue.setBeforeOutColor(CommandRules.OutColor.GREEN);
                        }
                        break;
                    case R.id.beforeOutColorPurple:
                        if (beforeOutColorPurple.isChecked()) {
                            beforeOutColor2.clearCheck();
                            commandNewTrue.setBeforeOutColor(CommandRules.OutColor.PURPLE);
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
                            commandNewTrue.setBeforeOutColor(CommandRules.OutColor.CYAN);
                        }
                        break;
                    case R.id.beforeOutColorYellow:
                        if (beforeOutColorYellow.isChecked()) {
                            beforeOutColor1.clearCheck();
                            commandNewTrue.setBeforeOutColor(CommandRules.OutColor.YELLOW);
                        }
                        break;
                    case R.id.beforeOutColorWhite:
                        if (beforeOutColorWhite.isChecked()) {
                            beforeOutColor1.clearCheck();
                            commandNewTrue.setBeforeOutColor(CommandRules.OutColor.WHITE);
                        }
                        break;
                    default:
                }
            }
        });
    }
    ///////////////////////////////////////////

    //干扰灯参数设置
    //蜂鸣器设置
    private void setBuzzerBeforeFalse() {
        buzzerfalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.buzzerNonefalse:
                        commandNewFalse.setBeforeBuzzerModel(CommandRules.BuzzerModel.NONE);
                        break;
                    case R.id.buzzerShortfalse:
                        commandNewFalse.setBeforeBuzzerModel(CommandRules.BuzzerModel.SHORT);
                        break;
                    case R.id.buzzer_1sfalse:
                        commandNewFalse.setBeforeBuzzerModel(CommandRules.BuzzerModel.LONG_1);
                        break;
                    case R.id.buzzer_2sfalse:
                        commandNewFalse.setBeforeBuzzerModel(CommandRules.BuzzerModel.LONG_2);
                        break;
                    default:
                }
            }
        });
    }

    //震动设置
    private void setVibrationFalse() {
        /* 震动感应开关*/
        vibrationInducedfalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.vibrationInducedOnfalse:
                        commandNewFalse.setVibrationInduced(CommandRules.VibrationInduced.OPEN);
                        break;
                    case R.id.vibrationInducedOfffalse:
                        commandNewFalse.setVibrationInduced(CommandRules.VibrationInduced.CLOSE);
                        break;
                    default:
                }
            }
        });
        /*震动强度*/
        vibrationIntensityfalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.vibrationIntensityLfalse:
                        commandNewFalse.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_L);
                        break;
                    case R.id.vibrationIntensityMfalse:
                        commandNewFalse.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_M);
                        break;
                    case R.id.vibrationIntensityHfalse:
                        commandNewFalse.setVibrationStrength(CommandRules.VibrationStrength.TOUCH_H);
                        break;
                    default:
                }
            }
        });
        /*震动详情*/
        vibrationDetailsfalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.vibrationDetailsOnfalse:
                        commandNewFalse.setVibrationDetails(CommandRules.VibrationDetails.OPEN);
                        break;
                    case R.id.vibrationDetailsOfffalse:
                        commandNewFalse.setVibrationDetails(CommandRules.VibrationDetails.CLOSE);
                        break;
                    default:
                }
            }
        });
    }

    //红外设置
    private void setInfraredFalse() {
        /*红外发射*/
        infraredEmissionfalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredEmissionOnfalse:
                        commandNewFalse.setInfraredEmission(CommandRules.InfraredEmission.OPEN);
                        break;
                    case R.id.infraredEmissionOfffalse:
                        commandNewFalse.setInfraredEmission(CommandRules.InfraredEmission.CLOSE);
                        break;
                    default:
                }
            }
        });
        /*红外感应*/
        infraredInductionfalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredInductionOnfalse:
                        commandNewFalse.setInfraredInduction(CommandRules.InfraredInduction.OPEN);
                        break;
                    case R.id.infraredInductionOfffalse:
                        commandNewFalse.setInfraredInduction(CommandRules.InfraredInduction.CLOSE);
                        break;
                    default:
                }
            }
        });
        /*红外模式*/
        infraredModelfalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredModelNormalfalse:
                        commandNewFalse.setInfraredModel(CommandRules.InfraredModel.NORMAL);
                        break;
                    case R.id.infraredModelContendfalse:
                        commandNewFalse.setInfraredModel(CommandRules.InfraredModel.CONTEND);
                        break;
                    default:
                }
            }
        });
        /*红外高度*/
        infraredHeightfalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infraredHeightLowfalse:
                        commandNewFalse.setInfraredHeight(CommandRules.InfraredHeight.MIN_LOW);
                        break;
                    case R.id.infraredHeight_5cmfalse:
                        commandNewFalse.setInfraredHeight(CommandRules.InfraredHeight.LOW);
                        break;
                    case R.id.infraredHeight_30cmfalse:
                        commandNewFalse.setInfraredHeight(CommandRules.InfraredHeight.HIGH);
                        break;
                    case R.id.infraredHeightHighfalse:
                        commandNewFalse.setInfraredHeight(CommandRules.InfraredHeight.MAX_HIGH);
                        break;
                    default:
                }
            }
        });
    }

    //内圈灯设置
    private void setInBeforeFalse() {
        /*内圈灯亮灯模式*/
        beforeInBlinkfalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeInBlinkNonefalse:
                        commandNewFalse.setBeforeInBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.beforeInBlinkAlwaysfalse:
                        commandNewFalse.setBeforeInBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.beforeInBlinkSlowfalse:
                        commandNewFalse.setBeforeInBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.beforeInBlinkFastfalse:
                        commandNewFalse.setBeforeInBlink(CommandRules.OpenModel.FAST);
                        break;
                    default:
                }
            }
        });
        /*内圈灯亮灯颜色*/
        beforeInColorfalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeInColorBluefalse:
                        commandNewFalse.setBeforeInColor(CommandRules.InColor.BLUE);
                        break;
                    case R.id.beforeInColorRedfalse:
                        commandNewFalse.setBeforeInColor(CommandRules.InColor.RED);
                        break;
                    case R.id.beforeInColorPurplefalse:
                        commandNewFalse.setBeforeInColor(CommandRules.InColor.PURPLE);
                        break;
                    default:

                }
            }
        });
    }

    //外圈灯设置
    private void setOutBeforeFalse() {
        /*外圈灯亮灯模式*/
        beforeOutBlinkfalse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeOutBlinkNonefalse:
                        commandNewFalse.setBeforeOutBlink(CommandRules.OpenModel.NONE);
                        break;
                    case R.id.beforeOutBlinkAlwaysfalse:
                        commandNewFalse.setBeforeOutBlink(CommandRules.OpenModel.ALWAYS);
                        break;
                    case R.id.beforeOutBlinkSlowfalse:
                        commandNewFalse.setBeforeOutBlink(CommandRules.OpenModel.SLOW);
                        break;
                    case R.id.beforeOutBlinkFastfalse:
                        commandNewFalse.setBeforeOutBlink(CommandRules.OpenModel.FAST);
                        break;
                    default:
                }
            }
        });
        /*外圈灯亮灯颜色*/
        beforeOutColor1false.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeOutColorBluefalse:
                        if (beforeOutColorBluefalse.isChecked()) {
                            beforeOutColor2false.clearCheck();
                            commandNewFalse.setBeforeOutColor(CommandRules.OutColor.BLUE);
                        }
                        break;
                    case R.id.beforeOutColorRedfalse:
                        if (beforeOutColorRedfalse.isChecked()) {
                            beforeOutColor2false.clearCheck();
                            commandNewFalse.setBeforeOutColor(CommandRules.OutColor.RED);
                        }
                        break;
                    case R.id.beforeOutColorGreenfalse:
                        if (beforeOutColorGreenfalse.isChecked()) {
                            beforeOutColor2false.clearCheck();
                            commandNewFalse.setBeforeOutColor(CommandRules.OutColor.GREEN);
                        }
                        break;
                    case R.id.beforeOutColorPurplefalse:
                        if (beforeOutColorPurplefalse.isChecked()) {
                            beforeOutColor2false.clearCheck();
                            commandNewFalse.setBeforeOutColor(CommandRules.OutColor.PURPLE);
                        }
                        break;
                    default:
                }
            }
        });
        beforeOutColor2false.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.beforeOutColorCyanfalse:
                        if (beforeOutColorCyanfalse.isChecked()) {
                            beforeOutColor1false.clearCheck();
                            commandNewFalse.setBeforeOutColor(CommandRules.OutColor.CYAN);
                        }
                        break;
                    case R.id.beforeOutColorYellowfalse:
                        if (beforeOutColorYellowfalse.isChecked()) {
                            beforeOutColor1false.clearCheck();
                            commandNewFalse.setBeforeOutColor(CommandRules.OutColor.YELLOW);
                        }
                        break;
                    case R.id.beforeOutColorWhitefalse:
                        if (beforeOutColorWhitefalse.isChecked()) {
                            beforeOutColor1false.clearCheck();
                            commandNewFalse.setBeforeOutColor(CommandRules.OutColor.WHITE);
                        }
                        break;
                    default:
                }
            }
        });
    }
    //////////////////////////////////////////

    //开始训练
    public void startTraining() {
        btnStartrun.setClickable(false);
        btnStoprun.setClickable(true);
        Log.d(TAG, "startTraining has run");
        //首先清除时间信息列表
        AppConfig.sTimeInfoList.clear();
        trainingBeginFlag = true;
        //清空时间成绩列表，防止将上次训练的成绩保存到下一次训练当中
        timeList.clear();
        matrixAdapter.setTimeList(timeList);
        matrixAdapter.notifyDataSetChanged();
        /////
        endFlag = false;
        //设置目标灯
        int randomNum1 = NumberUtils.randomNumber(numberOfLight);
        int randomNum2 = NumberUtils.randomNumber(numberOfLight);
        while (randomNum1 == randomNum2) {
            randomNum1 = NumberUtils.randomNumber(numberOfLight);
            randomNum2 = NumberUtils.randomNumber(numberOfLight);
        }
        Log.d("randomNum1,randomNum2", randomNum1 + " " + randomNum2);
        trueLight = selectedLights.get(randomNum1);
        Log.d("开始训练前目标灯的状态", trueLight.toString());
        List<Order> orderList = new ArrayList<>();
        trueLight.setOpen(true);
        Order order = new Order();
        order.setLight(trueLight);
        order.setCommandNew(commandNewTrue);
        orderList.add(order);
        Log.d("开始训练设置命令后", trueLight.toString());
        //设置干扰灯
        falseLight = selectedLights.get(randomNum2);
        Log.d("开始训练前干扰灯的状态", falseLight.toString());
        /*List<Order> orderList1 = new ArrayList<>();*/
        falseLight.setOpen(true);
        Order order1 = new Order();
        order1.setLight(falseLight);
        order1.setCommandNew(commandNewFalse);
        orderList.add(order1);
        /*orderList1.add(order1);*/
        Log.d("开始训练设置命令后", trueLight.toString());
        //两个命令一起发
        OrderUtils.getInstance().sendCommand(orderList);
        /*OrderUtils.getInstance().sendCommand(orderList1);*/
        //获得当前的系统时间
        startTime = System.currentTimeMillis();
        timer = new Timer(handler);
        timer.setBeginTime(startTime);
        timer.start();

    }

    //停止训练
    private void stopTraining() {
        btnTurnon.setClickable(true);
        btnTurnoff.setClickable(true);
        btnStartrun.setClickable(true);
        btnStoprun.setClickable(false);
        trainingBeginFlag = false;
        endFlag = true;
        OrderUtils.getInstance().turnOffLightList(AppConfig.sDbLights);
        if (timer != null) {
            timer.stopTimer();
        }
        //很重要的重置计数器
        counter = 0;
        Log.d("counter:", counter + "");
    }


}
