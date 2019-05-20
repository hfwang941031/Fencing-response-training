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
import ouc.b304.com.fenceplaying.Bean.DeviceInfo;
import ouc.b304.com.fenceplaying.Bean.Light;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.SingleSpotScoresDao;
import ouc.b304.com.fenceplaying.Dao.entity.Player;
import ouc.b304.com.fenceplaying.Dao.entity.SingleSpotScores;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.activity.SingleSpotActivity;
import ouc.b304.com.fenceplaying.adapter.LightOperateAdapter;
import ouc.b304.com.fenceplaying.adapter.SaveResultAdapter;
import ouc.b304.com.fenceplaying.adapter.SingleSpotAdapter;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;

import ouc.b304.com.fenceplaying.dialog.CusProgressDialog;
import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.entity.TimeInfo;
import ouc.b304.com.fenceplaying.order.CommandNew;
import ouc.b304.com.fenceplaying.order.CommandRules;
import ouc.b304.com.fenceplaying.order.OrderUtils;
import ouc.b304.com.fenceplaying.order.Order;
import ouc.b304.com.fenceplaying.thread.ReceiveThread;
import ouc.b304.com.fenceplaying.thread.Timer;
import ouc.b304.com.fenceplaying.utils.IntegerToStringUtils;
import ouc.b304.com.fenceplaying.utils.PlayDaoUtils;
import ouc.b304.com.fenceplaying.utils.ScoreUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;
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

    private final int TIME_RECEIVE = 1;
    private final int POWER_RECEIVE = 2;
    private final int UPDATE_TIMES = 3;
    private final int STOP_TRAINING = 4;

    private Timer timer;

    //存储在当前页面中可以用的设备编号，以方便在Spinner中选择设备
    private List<String> list = new ArrayList<>();
    //灯光颜色
    /*private Order.LightColor lightColor = Order.LightColor.RED;*/
    //灯光模式（内圈还是外圈凉）
    /*private Order.LightModel lightModel = Order.LightModel.OUTER;*/
    //感应模式（红外还是触碰还是同时）测试期间默认为红外模式
    /*private Order.ActionModel actionModel = Order.ActionModel.LIGHT;*/

    private CommandRules.OutColor lightColor = CommandRules.OutColor.BLUE;


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

        mExecutorService = new ScheduledThreadPoolExecutor(2);
        registerReceiver();
        playerDao = GreenDaoInitApplication.getInstances().getDaoSession().getPlayerDao();
        singleSpotScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getSingleSpotScoresDao();
        //初始化数据
        initData();
        initView();
    }

    /*注册广播*/
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
                //接收到返回的时间
               case TIME_RECEIVE:
                    String data = msg.obj.toString();
                    if (data.length() > 7) {
                        //解析数据
                        /*analyzeTimeData(data);*/
                    }
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
                this.finish();
                break;
            case R.id.layout_cancel:
                break;
            case R.id.btn_turnon:
                //开选中的灯
                DbLight selectedLight = null;
                for (DbLight light : AppConfig.sDbLights) {
                    if (light.getName() == deviceNum) {
                        selectedLight = light;
                        break;
                    }
                }
                OrderUtils.getInstance().turnOnLight(selectedLight);
                break;
            case R.id.btn_turnoff:
                //关选中的灯
                DbLight selectedCloseLight = null;
                for (DbLight light : AppConfig.sDbLights) {
                    if (light.getName() == deviceNum) {
                        selectedCloseLight = light;
                        break;
                    }
                }
                OrderUtils.getInstance().turnOnLight(selectedCloseLight);
                break;
            case R.id.img_btn_refresh:
                tesst();
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
        }

    }

    /**
     * 解析时间
     * */
    private void analyzeTimeData(TimeInfo timeInfo) {
            Log.d("ana", "时间解析");
            if (timeInfo.getName() == deviceNum) {
                counter += 1;
                Log.d("******", timeInfo.getName() + timeInfo.getTime() );
                Log.d("#######", counter + "");
                if (counter > trainTimes) {
                    endFlag = true;
                }
                timeList.add((int) timeInfo.getTime());
                List<Order> orderList = new ArrayList<>();
                DbLight light = AppConfig.sDbLights.get(1);
                light.setOpen(true);
                CommandNew commandNew = new CommandNew();
                commandNew.setBeforeOutColor(lightColor);
                commandNew.setInfraredEmission(CommandRules.InfraredEmission.OPEN);
                commandNew.setInfraredInduction(CommandRules.InfraredInduction.OPEN);
                commandNew.setInfraredModel(CommandRules.InfraredModel.NORMAL);
                Order order = new Order();
                order.setLight(light);
                order.setCommandNew(commandNew);
                orderList.add(order);
                OrderUtils.getInstance().sendCommand(orderList);

            }

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

    private boolean isTrainingOver() {
        if (counter >= trainTimes)
            return true;
        else return false;
    }
    /*检测灯数量*/
    public void checkLightNumber() {
        if (AppConfig.sDbLights.size() < 1) {
            ToastUtils.makeText(mContext, "可用设备不足一个,无法进行训练！", Toast.LENGTH_SHORT);
        }
    }
    public void initData() {
        //获取当前可用的设备编号，存储到list当中
        for (int i=0;i<AppConfig.sDbLights.size();i++) {
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
                deviceNum =  (String) spDevices.getSelectedItem();
                Log.d("deviceNum", deviceNum + "");
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

        //设置感应模式
        rgActionmode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_touch:
                        /*actionModel = Order.ActionModel.TOUCH;*/
                        break;
                    case R.id.rd_redline:
                        /*actionModel = Order.ActionModel.LIGHT;*/
                        break;
                    case R.id.rb_touchandredline:
                        /*actionModel = Order.ActionModel.ALL;*/
                        break;


                }
            }
        });
        //设置单选按钮
        rgLightcolor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rb_red:
                        /*lightColor = Order.LightColor.RED;*/
                        break;
                    case R.id.rd_blue:
                        /*lightColor = Order.LightColor.BLUE;*/
                        break;

                }
            }
        });

        //设置灯光内外圈
        rgBlinkmode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_out:
                        /*lightModel = Order.LightModel.OUTER;*/
                        break;
                    case R.id.rd_in:
                        /*lightModel = Order.LightModel.CENTER;*/
                }
            }
        });


    }


    public void stopTraining() {
        trainingBeginFlag = false;
        endFlag=true;
        OrderUtils.getInstance().turnOffLightList(AppConfig.sDbLights);
        if (timer != null) {
            timer.stopTimer();
        }
        //很重要的重置计数器
        counter = 0;
    }

    public void tesst() {
        List<Order> orderList = new ArrayList<>();
        DbLight light = AppConfig.sDbLights.get(1);
        light.setOpen(true);
        CommandNew commandNew = new CommandNew();
        commandNew.setBeforeOutColor(lightColor);

        Order order = new Order();
        order.setLight(light);
        order.setCommandNew(commandNew);
        orderList.add(order);
        OrderUtils.getInstance().sendCommand(orderList);
    }

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
        List<Order> orderList = new ArrayList<>();
        DbLight light = AppConfig.sDbLights.get(1);
        light.setOpen(true);
        CommandNew commandNew = new CommandNew();
        commandNew.setBeforeOutColor(lightColor);
        commandNew.setInfraredEmission(CommandRules.InfraredEmission.OPEN);
        commandNew.setInfraredInduction(CommandRules.InfraredInduction.OPEN);
        commandNew.setInfraredModel(CommandRules.InfraredModel.NORMAL);
        Order order = new Order();
        order.setLight(light);
        order.setCommandNew(commandNew);
        orderList.add(order);
        OrderUtils.getInstance().sendCommand(orderList);
       /* //清除串口数据
        new ReceiveThread(handler, device.ftDev, ReceiveThread.CLEAR_DATA_THREAD, 0).start();*/

        //开启接收设备返回时间的监听线程
/*
        new ReceiveThread(handler, device.ftDev, ReceiveThread.TIME_RECEIVE_THREAD, TIME_RECEIVE).start();
*/

      /*  device.sendOrder(deviceNum,
                lightColor,
                Order.VoiceMode.values()[0],
                Order.BlinkModel.values()[0],
                lightModel,
                actionModel,
                Order.EndVoice.values()[0]);*/

        //获得当前的系统时间
        startTime = System.currentTimeMillis();
        timer = new Timer(handler);
        timer.setBeginTime(startTime);
        timer.start();

    }


}
