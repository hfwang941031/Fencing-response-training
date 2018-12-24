package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.Bean.Constant;
import ouc.b304.com.fenceplaying.Bean.DeviceInfo;
import ouc.b304.com.fenceplaying.Bean.TimeInfo;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.SingleSpotScoresDao;
import ouc.b304.com.fenceplaying.Dao.entity.Player;
import ouc.b304.com.fenceplaying.Dao.entity.SingleSpotScores;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.SaveResultAdapter;
import ouc.b304.com.fenceplaying.adapter.SingleSpotAdapter;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;
import ouc.b304.com.fenceplaying.device.Device;
import ouc.b304.com.fenceplaying.device.Order;
import ouc.b304.com.fenceplaying.thread.ReceiveThread;
import ouc.b304.com.fenceplaying.thread.Timer;
import ouc.b304.com.fenceplaying.utils.DataAnalyzeUtils;
import ouc.b304.com.fenceplaying.utils.IntegerToStringUtils;
import ouc.b304.com.fenceplaying.utils.PlayDaoUtils;
import ouc.b304.com.fenceplaying.utils.ScoreUtils;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * @author 王海峰 on 2018/9/17 10:44
 * <p>
 * 流程：
 * 1、初始化当前所有可用的设备并存储到List中，在init()中完成
 * 2、选择训练次数和训练所需的单个设备
 * 3、开始训练，达到训练次数后跳出并执行stopTraining()
 * <p>
 * 注意：（后期可能会进行更改的地方）
 * 1、analyzeTimeData函数运行的时候开启了一个线程
 * 2、在上述函数中的开灯指令也是在一个匿名线程中进行的
 */
public class SingleSpotActivity extends Activity {
    private static final String TAG = "SingleSpotActivity";
    @BindView(R.id.bt_run_cancel)
    ImageView btRunCancel;
    @BindView(R.id.layout_cancel)
    LinearLayout layoutCancel;
    @BindView(R.id.sp_devices)
    Spinner spDevices;
    @BindView(R.id.btn_turnon)
    Button btnTurnon;
    @BindView(R.id.sp_times)
    Spinner spTimes;
    @BindView(R.id.btn_turnoff)
    Button btnTurnoff;
    @BindView(R.id.edit_beizhu)
    EditText editBeizhu;
    @BindView(R.id.tvTotalTime)
    TextView tvTotalTime;
    @BindView(R.id.btn_startrun)
    Button btnStartrun;
    @BindView(R.id.btn_stoprun)
    Button btnStoprun;
    @BindView(R.id.lvTimes)
    ListView lvTimes;
    private final int TIME_RECEIVE = 1;
    private final int POWER_RECEIVE = 2;
    private final int UPDATE_TIMES = 3;
    private final int STOP_TRAINING = 4;
    @BindView(R.id.showAvergeScore)
    TextView showAvergeScore;
    @BindView(R.id.avergeScore)
    TextView avergeScore;
    @BindView(R.id.bt_save)
    Button btSave;
/*
    @BindView(R.id.lv_saveresult)
    ListView lvSaveresult;*/

    private Context context;

    private Device device;

    //存储在当前页面中可以用的设备编号，以方便在Spinner中选择设备
    private List<Character> list = new ArrayList<>();

    //设备选择下拉框适配器
    private ArrayAdapter<Character> spDeviceAdapter;

    //训练次数下拉框适配器
    private ArrayAdapter<String> spTimesAdapter;

    //选中的设备编号，也就是用哪个设备完成训练
    private char deviceNum;

    //选中的训练次数
    private int trainTimes = 0;

    //训练开始标志
    private boolean trainingBeginFlag = false;

    //每次训练的时间集合
    private ArrayList<Integer> timeList;

    private Timer timer;

    //训练开始时间
    private long startTime;

    //计数器
    private int counter = 0;

    /*平均值*/
    private float averageScore = 0;

    //定义成绩列表适配器
    private SingleSpotAdapter singleSpotAdapter;

    private Date date;

    private List<String> scoreList;

    private PlayerDao playerDao;

    private SingleSpotScoresDao singleSpotScoresDao;

    //对话框中listview的适配器
    private SaveResultAdapter saveResultAdapter;

    private List<String> nameList;

    private SingleSpotScores singleSpotScores;

    private Player player;

    //设置一个布尔变量控制保存按钮的可按与否，当一次训练结束后，可以点击该保存按钮进行保存，点击过之后，不能再次进行点击，除非先进行下一次训练并得到一组时间值；
    private boolean saveBtnIsClickable = false;

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
                        analyzeTimeData(data);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "---->onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlespot);
        ButterKnife.bind(this);
        context = this.getApplicationContext();
        device = new Device(this);
        //更新连接设备列表
        device.createDeviceList(this);
        //判断是否插入协调器
        if (device.devCount > 0) {
            device.connect(this);
            device.initConfig();
        }
        playerDao = GreenDaoInitApplication.getInstances().getDaoSession().getPlayerDao();
        singleSpotScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getSingleSpotScoresDao();
        //初始化数据放在resume里面还是onCreate里面
        initData();
        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "---->onResume");
        Log.d("***list", list.size() + "");
        /*initView();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        device.turnOffAllTheLight();
        ReceiveThread.stopThread();
        if (device.devCount > 0)
            device.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.bt_run_cancel, R.id.btn_turnon, R.id.btn_turnoff, R.id.btn_startrun, R.id.btn_stoprun, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_run_cancel:
                this.finish();
                break;
            case R.id.btn_turnon:
                device.sendOrder(deviceNum,
                        Order.LightColor.BLUE,
                        Order.VoiceMode.NONE,
                        Order.BlinkModel.NONE,
                        Order.LightModel.OUTER,
                        Order.ActionModel.NONE,
                        Order.EndVoice.NONE);
                break;
            case R.id.btn_turnoff:
                device.turnOffAllTheLight();
                break;
            case R.id.btn_startrun:
                //训练开始的时候把上次的成绩置空
                averageScore = 0;
                avergeScore.setText("");
                if (!device.checkDevice(SingleSpotActivity.this))
                    return;
                if (deviceNum == '\0' || trainTimes == 0)
                    Toast.makeText(this, "请先选择设备和训练次数！", LENGTH_SHORT).show();
                if (trainingBeginFlag) {
                } else {
                    startTraining();
                    btnTurnon.setClickable(false);
                    btnTurnoff.setClickable(false);
                }
                break;
            case R.id.btn_stoprun:

                if (trainingBeginFlag) {
                    stopTraining();
                    btnTurnon.setClickable(true);
                    btnTurnoff.setClickable(true);
                }
                break;
            case R.id.bt_save:
                if (saveBtnIsClickable) {
                    //首先把timeList<Integer>变成List<String>
                    //判空验证
                    if (timeList.size() == 0) {
                        Toast.makeText(context, "成绩列表为空，无法进行保存！请先进行训练", Toast.LENGTH_SHORT).show();
                    } else {
                        //确定保存时间
                        date = new Date();

                        //将Integer类型的List转换成String类型
                        IntegerToStringUtils.integerToString(timeList, scoreList);
                        final AlertDialog saveDialog = new AlertDialog.Builder(this).create();
                        //设置对话框ICON
                        /*saveDialog.setIcon();*/
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

    public void startTraining() {
        Log.d(TAG, "startTraining has run");
        trainingBeginFlag = true;
        //清空时间列表，防止将上次训练的成绩保存到下一次训练当中
        timeList.clear();
        singleSpotAdapter.setTimeList(timeList);
        singleSpotAdapter.notifyDataSetChanged();

        //清除串口数据
        new ReceiveThread(handler, device.ftDev, ReceiveThread.CLEAR_DATA_THREAD, 0).start();

        //开启接收设备返回时间的监听线程
        new ReceiveThread(handler, device.ftDev, ReceiveThread.TIME_RECEIVE_THREAD, TIME_RECEIVE).start();

        device.sendOrder(deviceNum,
                Order.LightColor.values()[1],
                Order.VoiceMode.values()[0],
                Order.BlinkModel.values()[0],
                Order.LightModel.OUTER,
                Order.ActionModel.values()[1],
                Order.EndVoice.values()[0]);

        //获得当前的系统时间
        startTime = System.currentTimeMillis();
        timer = new Timer(handler);
        timer.setBeginTime(startTime);
        timer.start();

    }

    public void stopTraining() {
        trainingBeginFlag = false;
        //停止接收线程
        ReceiveThread.stopThread();
        device.turnOffAllTheLight();
        timer.stopTimer();

        //很重要的重置计数器
        counter = 0;
    }

    //解析返回来数据
    private void analyzeTimeData(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("analyzeTimeData", "analyzeTimeData Run");
                Log.d("what's in data", data);


                List<TimeInfo> infos = DataAnalyzeUtils.analyzeTimeData(data);
                for (TimeInfo info : infos) {
                    if (info.getDeviceNum() == deviceNum) {
                        counter += 1;
                        if (counter > trainTimes)
                            break;
                        Log.d("******", infos.size() + "");
                        Log.d("#######", counter + "");
                        timeList.add(info.getTime());
                        turnOnLight(info.getDeviceNum());
                    }
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
        }).start();
    }

    private boolean isTrainingOver() {
        if (counter >= trainTimes)
            return true;
        else return false;
    }

    public void turnOnLight(final char deviceNum) {
        //实现Runnable接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                Timer.sleep(5000);
                if (!trainingBeginFlag)
                    return;
                device.sendOrder(deviceNum,
                        Order.LightColor.values()[1],
                        Order.VoiceMode.values()[0],
                        Order.BlinkModel.values()[0],
                        Order.LightModel.OUTER,
                        Order.ActionModel.values()[1],
                        Order.EndVoice.values()[0]);
            }
        }).start();
    }

    public void initData() {
        //获取当前可用的设备编号，存储到list当中
        for (DeviceInfo info : Device.DEVICE_LIST) {
            list.add(info.getDeviceNum());
        }

        //初始化String类型的时间列表
        scoreList = new ArrayList<>();

        //初始化String类型的运动员姓名列表
        nameList = new ArrayList<>();
        timeList = new ArrayList<>();

    }

    private void initView() {
        //设置spinner设备号适配器
        spDeviceAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, list);
        spDevices.setAdapter(spDeviceAdapter);

        //设置spinner次数适配器
        spTimesAdapter = new ArrayAdapter(this, R.layout.spinner_item, Constant.trainingTimes);
        spTimes.setAdapter(spTimesAdapter);

        //将选中的deviceNum存储到变量deviceNum中
        spDevices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deviceNum = (char) spDevices.getSelectedItem();
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


    }


}
