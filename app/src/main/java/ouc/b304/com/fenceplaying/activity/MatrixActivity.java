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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.Bean.Constant;
import ouc.b304.com.fenceplaying.Bean.DeviceInfo;
import ouc.b304.com.fenceplaying.Bean.TimeInfo;
import ouc.b304.com.fenceplaying.Dao.MatrixScoresDao;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.entity.MatrixScores;
import ouc.b304.com.fenceplaying.Dao.entity.Player;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.MatrixAdapter;
import ouc.b304.com.fenceplaying.adapter.SaveResultAdapter;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;
import ouc.b304.com.fenceplaying.device.Device;
import ouc.b304.com.fenceplaying.device.Order;
import ouc.b304.com.fenceplaying.thread.AutoCheckPower;
import ouc.b304.com.fenceplaying.thread.ReceiveThread;
import ouc.b304.com.fenceplaying.thread.Timer;
import ouc.b304.com.fenceplaying.utils.DataAnalyzeUtils;
import ouc.b304.com.fenceplaying.utils.IntegerToStringUtils;
import ouc.b304.com.fenceplaying.utils.NumberUtils;
import ouc.b304.com.fenceplaying.utils.PlayDaoUtils;
import ouc.b304.com.fenceplaying.utils.ScoreUtils;

import static ouc.b304.com.fenceplaying.thread.ReceiveThread.POWER_RECEIVE_THREAD;

/**
 * @author 王海峰 on 2018/10/9 16:09
 */
public class MatrixActivity extends Activity {

    @BindView(R.id.bt_run_cancel)
    ImageView btRunCancel;
    @BindView(R.id.layout_cancel)
    LinearLayout layoutCancel;
    @BindView(R.id.btn_turnon)
    Button btnTurnon;
    @BindView(R.id.sp_times)
    Spinner spTimes;
    @BindView(R.id.btn_turnoff)
    Button btnTurnoff;

    @BindView(R.id.tvTotalTime)
    TextView tvTotalTime;
    @BindView(R.id.btn_startrun)
    Button btnStartrun;
    @BindView(R.id.btn_stoprun)
    Button btnStoprun;
    @BindView(R.id.lvTimes)
    ListView lvTimes;
    private final static int TIME_RECEIVE = 1;
    private final static int POWER_RECEIVE = 2;
    private final static int UPDATE_TIMES = 3;
    private final static int STOP_TRAINING = 4;
    private final static String TAG = "MatrixActivity";
    @BindView(R.id.tv_avalibledevices)
    TextView tvAvalibledevices;
    @BindView(R.id.tv_devicenumbers)
    TextView tvDevicenumbers;
    @BindView(R.id.img_btn_refresh)
    ImageButton imgBtnRefresh;
    @BindView(R.id.showAvergeScore)
    TextView showAvergeScore;
    @BindView(R.id.avergeScore)
    TextView avergeScore;
    @BindView(R.id.bt_save)
    Button btSave;
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
    @BindView(R.id.tv_wronglightcolor)
    TextView tvWronglightcolor;

    @BindView(R.id.rg_wronglightcolor)
    RadioGroup rgWronglightcolor;
    @BindView(R.id.rb_green)
    RadioButton rbGreen;
    @BindView(R.id.rd_yellow)
    RadioButton rdYellow;
    private Context context;
    private Device device;
    private char deviceNum;

    //存储在当前页面中可以用的设备编号
    private List<Character> list = new ArrayList<>();

    //所有两个设备编号组成的list集合
    private List<ArrayList<Character>> subList = new ArrayList<>();

    //选中的训练次数
    private int trainTimes = 0;

    //训练次数下拉框适配器
    private ArrayAdapter<String> spTimesAdapter;

    //每次训练的时间集合
    private ArrayList<Integer> timeList;

    //训练开始时间
    private long startTime;

    //计数器
    private int counter = 0;
    private Timer timer;

    //训练开始标志
    private boolean trainingBeginFlag = false;

    private MatrixAdapter matrixAdapter;

    //存储分好组的设备编号集合
    private List<ArrayList<Character>> listOfSubList;
    //设置一个布尔变量控制保存按钮的可按与否，当一次训练结束后，可以点击该保存按钮进行保存，点击过之后，不能再次进行点击，除非先进行下一次训练并得到一组时间值；
    private boolean saveBtnIsClickable = false;

    private Player player;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Timer.TIMER_FLAG:
                    String time = msg.obj.toString();
                    tvTotalTime.setText("总时间" + time);
                    break;
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
                    matrixAdapter.setTimeList(timeList);
                    matrixAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private AutoCheckPower checkPowerThread;
    private Date date;
    private List<String> scoreList;
    //对话框中listview的适配器
    private SaveResultAdapter saveResultAdapter;
    private List<String> nameList;

    private PlayerDao playerDao;
    private MatrixScores matrixScores;

    private MatrixScoresDao matrixScoresDao;

    /*平均值*/
    private float averageScore = 0;

    //灯光颜色
    private Order.LightColor lightColor=Order.LightColor.RED;
    //灯光模式（内圈还是外圈凉）
    private Order.LightModel lightModel= Order.LightModel.OUTER;
    //感应模式（红外还是触碰还是同时）测试期间默认为红外模式
    private Order.ActionModel actionModel=Order.ActionModel.LIGHT;
    //干扰灯光色
    private Order.LightColor wrongLightColor = Order.LightColor.GREEN;

    private void stopTraining() {
        trainingBeginFlag = false;
        //停止接收线程
        ReceiveThread.stopThread();
        device.turnOffAllTheLight();
        timer.stopTimer();

        //很重要的重置计数器
        counter = 0;
        //清空sublist
        subList.clear();
        listOfSubList.clear();
    }

    private void analyzeTimeData(String data) {

        Log.d("analyzeTimeData", "analyzeTimeData Run");
        Log.d("what's in data", data);
        List<TimeInfo> infos = DataAnalyzeUtils.analyzeTimeData(data);
        for (TimeInfo info : infos) {
            Log.i("编号", info.getDeviceNum() + "");
            if (info.getDeviceNum() == listOfSubList.get(counter).get(0)) {
                Log.i("counter", counter + "");
                counter += 1;
                Log.d("***infos.size***", infos.size() + "");
                timeList.add(info.getTime());
                device.turnOffAllTheLight();
                if (counter>=trainTimes)
                    break;
                device.sendOrder(listOfSubList.get(counter).get(0), lightColor, Order.VoiceMode.NONE, Order.BlinkModel.NONE, lightModel, actionModel, Order.EndVoice.NONE);
                device.sendOrder(listOfSubList.get(counter).get(1), wrongLightColor, Order.VoiceMode.NONE, Order.BlinkModel.NONE, lightModel, Order.ActionModel.NONE, Order.EndVoice.NONE);
                Log.d("开目标灯是：", listOfSubList.get(counter).get(0) + ""+listOfSubList.indexOf(listOfSubList.get(counter).get(0)));
                Log.d("开干扰灯的是：", listOfSubList.get(counter).get(1) + ""+listOfSubList.indexOf(listOfSubList.get(counter).get(1)));
            }

        }
        Message msg = Message.obtain();
        msg.what = UPDATE_TIMES;
        msg.obj = "";
        handler.sendMessage(msg);
        if (isTrainingOver()) {
            //训练结束后，清空sublist
            subList.clear();
            listOfSubList.clear();
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "---->onCreate");
        setContentView(R.layout.activity_matrix);
        ButterKnife.bind(this);
        context = this.getApplicationContext();
        initDevices();
        playerDao = GreenDaoInitApplication.getInstances().getDaoSession().getPlayerDao();
        matrixScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getMatrixScoresDao();
        initView();
        initData();

    }

    public void initDevices() {
        device = new Device(this);
        //更新连接设备列表
        device.createDeviceList(this);
        //判断是否插入协调器
        if (device.devCount > 0) {
            device.connect(this);
            device.initConfig();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkPowerThread = new AutoCheckPower(context, device, POWER_RECEIVE_THREAD, handler);
            checkPowerThread.start();
        }
    }

    private void initView() {

        //设置spinner次数适配器
        spTimesAdapter = new ArrayAdapter(this, R.layout.spinner_item, Constant.trainingTimes);
        spTimes.setAdapter(spTimesAdapter);
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

        matrixAdapter = new MatrixAdapter(this);
        lvTimes.setAdapter(matrixAdapter);

        //设置感应模式
        rgActionmode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_touch:
                        actionModel = Order.ActionModel.TOUCH;
                        break;
                    case R.id.rd_redline:
                        actionModel = Order.ActionModel.LIGHT;
                        break;
                    case R.id.rb_touchandredline:
                        actionModel = Order.ActionModel.ALL;
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
                        lightColor = Order.LightColor.RED;
                        break;
                    case R.id.rd_blue:
                        lightColor = Order.LightColor.BLUE;
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
                        lightModel = Order.LightModel.OUTER;
                        break;
                    case R.id.rd_in:
                        lightModel = Order.LightModel.CENTER;
                }
            }
        });

        rgWronglightcolor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_green:
                        wrongLightColor = Order.LightColor.GREEN;
                        break;
                    case R.id.rd_yellow:
                        wrongLightColor = Order.LightColor.YELLOW;
                }

            }
        });
    }

    public void updateData() {
        StringBuffer stringBuffer = new StringBuffer(list.size());
        for (int i = 0; i < list.size(); i++) {
            stringBuffer.append(list.get(i));
            stringBuffer.append(" ");
        }
        tvDevicenumbers.setText("");
        tvDevicenumbers.setText(stringBuffer.toString());

    }

    private void initData() {


        //获取当前可用的设备编号，存储到list当中
        for (DeviceInfo info : Device.DEVICE_LIST) {
            list.add(info.getDeviceNum());
        }
        timeList = new ArrayList<>();
        //初始化String类型的时间列表
        scoreList = new ArrayList<>();

        //初始化String类型的运动员姓名列表
        nameList = new ArrayList<>();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "---->onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "---->onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "---->onPause");
        device.turnOffAllTheLight();
        ReceiveThread.stopThread();
        if (device.devCount > 0)
            device.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "---->onDestroy");
    }

    @OnClick({R.id.bt_run_cancel, R.id.layout_cancel, R.id.btn_turnon, R.id.btn_turnoff, R.id.btn_startrun, R.id.btn_stoprun, R.id.img_btn_refresh, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_run_cancel:
                this.finish();
                break;
            case R.id.layout_cancel:

                break;
            case R.id.btn_turnon:
                for (Character name : list) {
                    device.sendOrder(name,
                            Order.LightColor.BLUE,
                            Order.VoiceMode.NONE,
                            Order.BlinkModel.NONE,
                            Order.LightModel.OUTER,
                            Order.ActionModel.NONE,
                            Order.EndVoice.NONE);
                }
                break;
            case R.id.btn_turnoff:
                device.turnOffAllTheLight();
                break;
            case R.id.btn_startrun:
                if (!device.checkDevice(MatrixActivity.this))
                    return;
                else if (trainTimes == 0)
                    Toast.makeText(this, "请先选择训练次数！", Toast.LENGTH_SHORT).show();
                else if (!checkDeviceNumber(list)) {
                    Toast.makeText(this, "当前可用的感应器数量不足四个，无法进行方阵训练", Toast.LENGTH_SHORT).show();
                    trainingBeginFlag = true;
                } else if (!trainingBeginFlag) {
                    listOfSubList = listOfSubList(trainTimes);
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
            case R.id.img_btn_refresh:
                Log.d("图片按钮被点击拉", "o");
                updateData();
                Toast.makeText(this, "可用设备已刷新", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_save:
                if (saveBtnIsClickable) {
                    if (timeList.size() == 0) {
                        Toast.makeText(context, "成绩列表为空，无法进行保存！请先进行训练", Toast.LENGTH_SHORT).show();
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

    private void startTraining() {
        counter = 0;
        trainingBeginFlag = true;
        //清空时间列表，防止将上次训练的成绩保存到下一次训练当中
        timeList.clear();
        matrixAdapter.setTimeList(timeList);
        matrixAdapter.notifyDataSetChanged();
        //清除串口数据
        new ReceiveThread(handler, device.ftDev, ReceiveThread.CLEAR_DATA_THREAD, 0).start();

        //开启接收设备返回时间的监听线程
        new ReceiveThread(handler, device.ftDev, ReceiveThread.TIME_RECEIVE_THREAD, TIME_RECEIVE).start();

        //第一次发送为目标 第二次发送为干扰灯
        device.sendOrder(listOfSubList.get(0).get(0), lightColor, Order.VoiceMode.NONE, Order.BlinkModel.NONE, lightModel, actionModel, Order.EndVoice.NONE);
        device.sendOrder(listOfSubList.get(0).get(1), wrongLightColor, Order.VoiceMode.NONE, Order.BlinkModel.NONE, lightModel, Order.ActionModel.NONE, Order.EndVoice.NONE);

        //获得当前的系统时间
        startTime = System.currentTimeMillis();
        timer = new Timer(handler);
        timer.setBeginTime(startTime);
        timer.start();
    }

    //对设备数量有要求，如果当前可用的设备少于四个 该项训练不能进行
    public boolean checkDeviceNumber(List<Character> list) {
        if (list.size() >= 4) {
            return true;
        } else
            return false;
    }

    //返回生成包含两个不同随机设备编号的list
    public ArrayList<Character> randomSubList(List<Character> list) {
        List<Character> subList = new ArrayList<>();
        int random1 = NumberUtils.randomNumber(list.size());
        int random2 = NumberUtils.randomNumber(list.size());
        while (random1 == random2) {
            random2 = NumberUtils.randomNumber(list.size());
        }
        subList.add(list.get(random1));
        Log.d("zi1", list.get(random1)+"");
        subList.add(list.get(random2));
        Log.d("zi2", list.get(random2)+"");
        return (ArrayList<Character>) subList;
    }

    //将每个subList都添加到list中，形成所有subList的集合
    public List<ArrayList<Character>> listOfSubList(int trainTimes) {
        for (int i = 0; i < trainTimes; i++) {
            subList.add(randomSubList(list));
            Log.d("listofsublist"+i, subList.get(i) + "");
        }
        return subList;
    }

}
