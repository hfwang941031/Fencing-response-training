package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.Bean.Constant;
import ouc.b304.com.fenceplaying.Bean.DeviceInfo;
import ouc.b304.com.fenceplaying.Bean.TimeInfo;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.MatrixAdapter;
import ouc.b304.com.fenceplaying.device.Command;
import ouc.b304.com.fenceplaying.device.Device;
import ouc.b304.com.fenceplaying.device.Order;
import ouc.b304.com.fenceplaying.thread.AutoCheckPower;
import ouc.b304.com.fenceplaying.thread.ReceiveThread;
import ouc.b304.com.fenceplaying.thread.Timer;
import ouc.b304.com.fenceplaying.utils.DataAnalyzeUtils;
import ouc.b304.com.fenceplaying.utils.NumberUtils;

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
    private Context context;
    private Device device;
    private char deviceNum;

    //存储在当前页面中可以用的设备编号
    private List<Character> list = new ArrayList<>();

    //所有两个设备编号组成的list集合
    private List<ArrayList<Character>> subList = new ArrayList<>();

    //选中的训练次数
    private int trainTimes = 0;

    //选中的感应模式
    private String actionModeOfString=null;

    //训练次数下拉框适配器
    private ArrayAdapter<String> spTimesAdapter;

    //感应模式选择适配器
    private ArrayAdapter spModeAdapter;

    //每次训练的时间集合
    private ArrayList<Integer> timeList;

    //训练开始时间
    private long startTime;

    //计数器
    private int counter = 0;
    //第二个计数器
    private int counter2 = 1;

    private Timer timer;

    //训练开始标志
    private boolean trainingBeginFlag = false;

    private MatrixAdapter matrixAdapter;

    //存储分好组的设备编号集合
    private List<ArrayList<Character>> listOfSubList;

    //感应模式标识数字
    private int flagOfMode=0;


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

    private void stopTraining() {
        trainingBeginFlag = false;
        //停止接收线程
        ReceiveThread.stopThread();
        device.turnOffAllTheLight();
        timer.stopTimer();

        //很重要的重置计数器
        counter = 0;
    }
    private void analyzeTimeData(String data) {

                Log.d("analyzeTimeData", "analyzeTimeData Run");
                Log.d("what's in data", data);
                List<TimeInfo> infos = DataAnalyzeUtils.analyzeTimeData(data);
                for (TimeInfo info : infos) {
                    counter+=1;
                    if (counter > trainTimes) {
                        listOfSubList = listOfSubList(trainTimes);
                        counter2=1;
                        break;
                    }
                     Log.d("***infos.size***", infos.size()+"");
                    /*Log.d("#######", counter+"");*/
                    timeList.add(info.getTime());
                    device.turnOffAllTheLight();
                    turnOnLight2(listOfSubList.get(counter2).get(0),1,2);
                    Log.d("开红灯的是：", listOfSubList.get(counter2).get(0)+"");
                    turnOnLight2(listOfSubList.get(counter2).get(1),0,1);
                    Log.d("开蓝灯的是：", listOfSubList.get(counter2).get(1)+"");
                    counter2+=1;
                    if (counter2 > listOfSubList.size()-1) {
                        counter2=listOfSubList.size()-1;
                    }
                }
                Message msg=Message.obtain();
                msg.what=UPDATE_TIMES;
                msg.obj = "";
                handler.sendMessage(msg);
                if (isTrainingOver()) {
                    Log.d("ifistrainingover", "has run"+counter);
                    Message msg1 = Message.obtain();
                    msg1.what = STOP_TRAINING;
                    msg1.obj = "";
                    handler.sendMessage(msg1);
                }
            }


    /*public void analyzeTimeData(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("analyzeTimeData", "analyzeTimeData Run");
                Log.d("what's in data", data);
                List<TimeInfo> infos = DataAnalyzeUtils.analyzeTimeData(data);
                for (TimeInfo info : infos) {
                    counter+=1;
                    if (counter > trainTimes) {
                        listOfSubList = listOfSubList(trainTimes);
                        counter2=1;
                        break;
                    }
                     *//*Log.d("******", infos.size()+"");
                    Log.d("#######", counter+"");*//*
                    timeList.add(info.getTime());
                    device.turnOffAllTheLight();
                    turnOnLight2(listOfSubList.get(counter2).get(0),1,2);
                    Log.d("开红灯的是：", listOfSubList.get(counter2).get(0)+"");
                    turnOnLight2(listOfSubList.get(counter2).get(1),0,1);
                    Log.d("开蓝灯的是：", listOfSubList.get(counter2).get(1)+"");
                    counter2+=1;
                    if (counter2 > listOfSubList.size()-1) {
                        counter2=listOfSubList.size()-1;
                    }
                }
                Message msg=Message.obtain();
                msg.what=UPDATE_TIMES;
                msg.obj = "";
                handler.sendMessage(msg);
                if (isTrainingOver()) {
                    Log.d("ifistrainingover", "has run"+counter);
                    Message msg1 = Message.obtain();
                    msg1.what = STOP_TRAINING;
                    msg1.obj = "";
                    handler.sendMessage(msg1);
                }
            }
        }).start();
    }*/
   /* public void analyzeTimeData(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("analyzeTimeData", "analyzeTimeData Run");
                Log.d("what's in data", data);
                List<TimeInfo> infos = DataAnalyzeUtils.analyzeTimeData(data);
                for (TimeInfo info : infos) {
                    counter += 1;
                    if (counter > trainTimes) {
                        listOfSubList = listOfSubList(trainTimes);
                        counter2 = 1;
                        break;
                    }
                    timeList.add(info.getTime());
                    device.turnOffAllTheLight();
                    //1是感应 0是常开
                    //2 是红色 1是蓝色
                    turnOnLight2(listOfSubList.get(counter2).get(0), 1, 2);
                    turnOnLight2(listOfSubList.get(counter2).get(1), 0, 1);
                    counter2 += 1;
                    if (counter2 > listOfSubList.size() - 1) {
                        counter2 = listOfSubList.size() - 1;
                    }
                }
                Message msg = Message.obtain();
                msg.what = UPDATE_TIMES;
                msg.obj = "";
                handler.sendMessage(msg);
                if (isTrainingOver()) {
                    Log.d("ifistrainingover", "has run" + counter);
                    Message msg1 = Message.obtain();
                    msg1.what = STOP_TRAINING;
                    msg1.obj = "";
                    handler.sendMessage(msg1);
                }
            }
        }).start();
    }*/

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
        initView();
        /* initData();*/

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
        //设置spinner感应模式适配器
        spModeAdapter = new ArrayAdapter(this, R.layout.spinner_item, Constant.action_model);
        spDevicesmode.setAdapter(spModeAdapter);
        //将选中的感应模式存储到变量actionMode中
        spDevicesmode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                actionModeOfString= (String) spDevicesmode.getSelectedItem();
                Log.d("actionMode", actionModeOfString + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        matrixAdapter = new MatrixAdapter(this);
        lvTimes.setAdapter(matrixAdapter);
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
        initData();
       /* for (int i=0;i<listOfSubList.size();i++) {
            Log.d("第" + i + "组", listOfSubList.get(i).get(0)+listOfSubList.get(i).get(1)+"");

        }*/
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

    @OnClick({R.id.bt_run_cancel, R.id.layout_cancel, R.id.btn_turnon, R.id.btn_turnoff, R.id.btn_startrun, R.id.btn_stoprun})
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


        }
    }

 /*   public void turnOnLight(final char deviceNum) {
        //实现Runnable接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                Timer.sleep(500);
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
    }*/

    public void turnOnLight2(final char deviceNum, final int actionMode, final int color) {
        //实现Runnable接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                    Timer.sleep(5000);
                if (!trainingBeginFlag)
                    return;
                device.sendOrder(deviceNum,
                        Order.LightColor.values()[color],
                        Order.VoiceMode.values()[0],
                        Order.BlinkModel.values()[0],
                        Order.LightModel.OUTER,
                        Order.ActionModel.values()[actionMode],
                        Order.EndVoice.values()[0]);
            }
        }).start();
    }

    private void startTraining() {
        trainingBeginFlag = true;
        timeList = new ArrayList<>(trainTimes);
        matrixAdapter.setTimeList(timeList);
        matrixAdapter.notifyDataSetChanged();
        Command mCommand = new Command();

        //清除串口数据
        new ReceiveThread(handler, device.ftDev, ReceiveThread.CLEAR_DATA_THREAD, 0).start();

        //开启接收设备返回时间的监听线程
        new ReceiveThread(handler, device.ftDev, ReceiveThread.TIME_RECEIVE_THREAD, TIME_RECEIVE).start();

        for (int i = 0; i < 2; i++) {


            Log.d("第一次发送", "i:" + i);
            device.sendOrder(listOfSubList.get(0).get(i),
                    Order.LightColor.values()[2-i],
                    Order.VoiceMode.values()[0],
                    Order.BlinkModel.values()[0],
                    Order.LightModel.OUTER,
                    Order.ActionModel.values()[1-i],
                    Order.EndVoice.values()[0]);
        }

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
        subList.add(list.get(random2));
        return (ArrayList<Character>) subList;
    }

    //将每个subList都添加到list中，形成所有subList的集合
    public List<ArrayList<Character>> listOfSubList(int trainTimes) {
        for (int i = 0; i < trainTimes; i++) {
            subList.add(randomSubList(list));
        }
        return subList;
    }


    @OnClick(R.id.img_btn_refresh)
    public void onViewClicked() {
        Log.d("图片按钮被点击拉", "o");

        updateData();
        Toast.makeText(this, "可用设备已刷新", Toast.LENGTH_LONG).show();
    }

    public void setParmOfDevice(String actionModeOfString) {
        if (actionModeOfString.equals("触碰")) {
            flagOfMode=2;
        }
        else flagOfMode=1;
    }
}
