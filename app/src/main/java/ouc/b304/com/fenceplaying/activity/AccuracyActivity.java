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
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.Bean.DeviceInfo;
import ouc.b304.com.fenceplaying.Bean.Light;
import ouc.b304.com.fenceplaying.Bean.TimeInfo;
import ouc.b304.com.fenceplaying.Dao.AccuracyScoresDao;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.entity.AccuracyScores;
import ouc.b304.com.fenceplaying.Dao.entity.Player;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.AccuracyAdapter;
import ouc.b304.com.fenceplaying.adapter.SaveResultAdapter;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;
import ouc.b304.com.fenceplaying.device.Device;
import ouc.b304.com.fenceplaying.device.Order;
import ouc.b304.com.fenceplaying.thread.AutoCheckPower;
import ouc.b304.com.fenceplaying.thread.ReceiveThread;
import ouc.b304.com.fenceplaying.thread.Timer;
import ouc.b304.com.fenceplaying.utils.DataAnalyzeUtils;
import ouc.b304.com.fenceplaying.utils.PlayDaoUtils;

import static ouc.b304.com.fenceplaying.thread.ReceiveThread.POWER_RECEIVE_THREAD;

public class AccuracyActivity extends Activity {
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
    @BindView(R.id.tv_wronglightcolor)
    TextView tvWronglightcolor;
    @BindView(R.id.rb_green)
    RadioButton rbGreen;
    @BindView(R.id.rd_yellow)
    RadioButton rdYellow;
    @BindView(R.id.rg_wronglightcolor)
    RadioGroup rgWronglightcolor;
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
    @BindView(R.id.tv_hit_right_num)
    TextView tvHitRightNum;
    @BindView(R.id.tv_hit_wrong_num)
    TextView tvHitWrongNum;
    private Context context;
    private Device device;
    private AutoCheckPower checkPowerThread;
    //每次训练的时间集合
    private ArrayList<ArrayList<Integer>> timeList = new ArrayList<>();

    private final static int TIME_RECEIVE = 1;
    private final static int POWER_RECEIVE = 2;
    private final static int UPDATE_TIMES = 3;
    private final static int STOP_TRAINING = 4;
    private final static int TIME_RECEIVE_FOR_MATRIX = 5;
    //训练时间
    private long trainingTime = 0;
    //训练开始时间
    private long beginTime = 0;
    private Timer timer;

    //训练开始标志
    private boolean trainingBeginFlag = false;
    //存储在当前页面中可以用的设备编号
    private List<Character> list = new ArrayList<>();
    //12个灯矩阵列表
    private ArrayList<ArrayList<Light>> lightListForMatrix = new ArrayList<>();
    //当前组亮起的3盏灯，0：目标灯 1~n：干扰项
    private ArrayList<Character> nowLightListForMatrix = new ArrayList<>();
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
   /* //成绩列表
    private ArrayList<GradeForAccuracy> gradeForAccuracies = new ArrayList<>();*/


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
    private Order.LightColor lightColor=Order.LightColor.RED;
    //灯光模式（内圈还是外圈凉）
    private Order.LightModel lightModel= Order.LightModel.OUTER;
    //感应模式（红外还是触碰还是同时）测试期间默认为红外模式
    private Order.ActionModel actionModel=Order.ActionModel.LIGHT;
    //干扰灯光色
    private Order.LightColor wrongLightColor = Order.LightColor.GREEN;

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
                        stopTraining();
                        saveBtnIsClickable=true;
                        hitRate = (float)counterForMatrixTraining / (float)(counterForWrongNum + counterForMatrixTraining);
                        /*//成绩保存，只有在训练时间全部结束时才保存，手动结束时不保存
                        GradeForAccuracy grade = new GradeForAccuracy();
                        grade.setTrainingTime((int) (trainingTime/60));
                        grade.setHitRightNum(counterForMatrixTraining);
                        grade.setHitWrongNum(counterForWrongNum);
                        grade.setHitRate(counterForMatrixTraining/(counterForMatrixTraining + counterForWrongNum));
                        gradeForAccuracies.add(grade);*/
                    }
                    break;

                case TIME_RECEIVE_FOR_MATRIX:
                    String data1 = msg.obj.toString();
                    if (data1.length() > 0) {
                        List<TimeInfo> timeInfo = DataAnalyzeUtils.analyzeTimeData(data1);
                        analyzeDateForMatrix(timeInfo);
                    }
                    break;
                case STOP_TRAINING:
                    stopTraining();
                    break;
                case UPDATE_TIMES:
                    break;
            }
        }
    };
    private PlayerDao playerDao;
    private Player player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accuracy);
       /* for (DeviceInfo info : Device.DEVICE_LIST) {
            list.add(info.getDeviceNum());
        }*/
        ButterKnife.bind(this);
        context = this.getApplicationContext();
        playerDao = GreenDaoInitApplication.getInstances().getDaoSession().getPlayerDao();
        accuracyScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getAccuracyScoresDao();
        initData();
        initView();
        initDevices();
        setListener();
        showAvergeScore.setText("命中率：");
        accuracyAdapter = new AccuracyAdapter(this, isHitArrayList);
        lvTimes.setAdapter(accuracyAdapter);



    }

    private void initView() {
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

    private void initData() {
        for (DeviceInfo info : Device.DEVICE_LIST) {
            list.add(info.getDeviceNum());
        }
        //初始化String类型的运动员姓名列表
        nameList = new ArrayList<>();
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

    public void startTrainingForMatrix() {
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
        new ReceiveThread(handler, device.ftDev, ReceiveThread.TIME_RECEIVE_THREAD, TIME_RECEIVE_FOR_MATRIX).start();
        openLightForMatrixTraining();

    }

    private void stopTraining() {

        trainingBeginFlag = false;
        //停止接收线程
        ReceiveThread.stopThread();
        device.turnOffAllTheLight();
        timer.stopTimer();

    }

    private void analyzeDateForMatrix(List<TimeInfo> timeInfo) {

        for (TimeInfo info : timeInfo) {
            Log.i("TAG", info.getDeviceNum() + "");
            if (info.getDeviceNum() == nowLightListForMatrix.get(0)) {
                counterForMatrixTraining++;
                tvHitRightNum.setText(counterForMatrixTraining+"");
                isHitArrayList.add(true);
                turnOffNowLight();
                openLightForMatrixTraining();
            } else {
                counterForWrongNum++;
                tvHitWrongNum.setText(counterForWrongNum+"");
                isHitArrayList.add(false);
                turnOffNowLight();
                openLightForMatrixTraining();
            }
            avergeScore.setText((int)(100 * counterForMatrixTraining / (counterForWrongNum + counterForMatrixTraining))+"%");
            accuracyAdapter.notifyDataSetChanged();
        }




    }

    public void openLightForMatrixTraining() {
        Random random = new Random();
        int randomMiddleLight = random.nextInt(12);
        nowLightListForMatrix.clear();
        nowLightListForMatrix.add(list.get(randomMiddleLight));
        inputInterfenceTerm(randomMiddleLight);

        Log.i("promise", randomMiddleLight + "========" + nowLightListForMatrix);
        device.sendOrder(list.get(randomMiddleLight),
                Order.LightColor.BLUE,
                Order.VoiceMode.values()[0],
                Order.BlinkModel.values()[0],
                Order.LightModel.OUTER,
                Order.ActionModel.ALL,
                Order.EndVoice.values()[0]);
        for (int i = 1;i<nowLightListForMatrix.size();i++) {
            device.sendOrder(nowLightListForMatrix.get(i), Order.LightColor.RED,
                    Order.VoiceMode.values()[0],
                    Order.BlinkModel.values()[0],
                    Order.LightModel.OUTER,
                    Order.ActionModel.ALL,
                    Order.EndVoice.values()[0]);
        }
    }

    private void turnOffNowLight() {
        for (int i = 0; i < nowLightListForMatrix.size(); i++) {
            device.sendOrder(nowLightListForMatrix.get(i),
                    Order.LightColor.NONE,
                    Order.VoiceMode.NONE,
                    Order.BlinkModel.NONE,
                    Order.LightModel.TURN_OFF,
                    Order.ActionModel.NONE,
                    Order.EndVoice.NONE);
        }
    }

    public void inputInterfenceTerm(int randomMiddleLight) {
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

    @OnClick({R.id.bt_run_cancel, R.id.btn_turnon, R.id.btn_turnoff, R.id.img_btn_refresh, R.id.btn_startrun, R.id.btn_stoprun, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_run_cancel:
                if (trainingBeginFlag == false) {
                    finish();
                } else {
                    Toast.makeText(context, "训练进行中", Toast.LENGTH_SHORT).show();
                }
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
            case R.id.img_btn_refresh:
                Log.d("图片按钮被点击拉", "o");
                updateData();
                Toast.makeText(this, "可用设备已刷新", Toast.LENGTH_LONG).show();
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
                                accuracyScores.setTrainingTime((int) (trainingTime/60));
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

    private void updateData() {
        StringBuffer stringBuffer = new StringBuffer(list.size());
        for (int i = 0; i < list.size(); i++) {
            stringBuffer.append(list.get(i));
            stringBuffer.append(" ");
        }
        tvDevicenumbers.setText("");
        tvDevicenumbers.setText(stringBuffer.toString());
    }
}
