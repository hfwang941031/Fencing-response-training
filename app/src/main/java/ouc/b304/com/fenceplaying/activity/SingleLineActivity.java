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
import ouc.b304.com.fenceplaying.adapter.SingleLineAdapter;
import ouc.b304.com.fenceplaying.device.Device;
import ouc.b304.com.fenceplaying.device.Order;
import ouc.b304.com.fenceplaying.thread.AutoCheckPower;
import ouc.b304.com.fenceplaying.thread.ReceiveThread;
import ouc.b304.com.fenceplaying.thread.Timer;
import ouc.b304.com.fenceplaying.utils.DataAnalyzeUtils;
import ouc.b304.com.fenceplaying.utils.NumberUtils;

import static ouc.b304.com.fenceplaying.thread.ReceiveThread.POWER_RECEIVE_THREAD;

/**
 * @author 王海峰 on 2018/11/26 08:51
 */
public class SingleLineActivity extends Activity {
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
    @BindView(R.id.tv_devicenumbers)
    TextView tvDevicenumbers;
    @BindView(R.id.img_btn_refresh)
    ImageButton imgBtnRefresh;
    @BindView(R.id.tv_setdeviceparam)
    TextView tvSetdeviceparam;
    @BindView(R.id.tvTotalTime)
    TextView tvTotalTime;
    @BindView(R.id.btn_startrun)
    Button btnStartrun;
    @BindView(R.id.btn_stoprun)
    Button btnStoprun;
    @BindView(R.id.lvTimes)
    ListView lvTimes;
    /*
    * 在开始训练的时候先对数量选择进行判断，判断选择的设备数量和可用设备数量是否一致，如果设备数量不符合
    * 应该给出提示，并禁止训练继续下去，直到用户选择了正确的设备数量
    * */

    private final static int TIME_RECEIVE = 1;
    private final static int POWER_RECEIVE = 2;
    private final static int UPDATE_TIMES = 3;
    private final static int STOP_TRAINING = 4;
    private final static String TAG = "SingleLineActivity";

    private Context context;
    private Device device;

    //存储在当前页面中可以用的设备编号
    private List<Character> list = new ArrayList<>();

    //将选中的数量的设备组成一个list
    private List<Character> selectedList = new ArrayList<>();

    //选中的训练次数
    private int trainTimes = 0;

    //选中的训练设备数量
    private int deviceAmount=0;

    //训练次数下拉框适配器
    private ArrayAdapter<String> spTimesAdapter;

    //训练所需设备数量适配器
    private ArrayAdapter spDeviceAmountAdapter;


    //每次训练的时间集合
    private ArrayList<Integer> timeList;

    //训练开始时间
    private long startTime;

    //计数器
    private int counter = 0;

    private Timer timer;

    //训练开始标志
    private boolean trainingBeginFlag = false;

    private SingleLineAdapter singleLineAdapter;

    private AutoCheckPower checkPowerThread;

    //存放随机获取到的被选择list中的元素并组成一个新的randomList，元素个数为训练次数
    private List<Character> randomList=new ArrayList<>();

    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Timer.TIMER_FLAG:
                    String time = msg.obj.toString();
                    tvTotalTime.setText("总时间" + time);
                    break;
                case TIME_RECEIVE:
                    String data = msg.obj.toString();
                    if (data.length() > 0) {                 ///此处为什么需要data.length>7
                        //解析数据
                        analyzeTimeData(data);
                    }
                    break;
                case STOP_TRAINING:
                    stopTraining();
                    break;
                case UPDATE_TIMES:
                    singleLineAdapter.setTimeList(timeList);
                    singleLineAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "---->onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleline);
        ButterKnife.bind(this);
        context = this.getApplicationContext();
        initDevices();
        initView();
        /*initData();*/
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "---->onStart");
        super.onStart();
        /*initData();*/
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "---->onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "---->onResume");
        super.onResume();
        //初始化设备编号并存储到list中
        initData();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "---->onPause");
        super.onPause();
        device.turnOffAllTheLight();
        ReceiveThread.stopThread();
        if (device.devCount > 0)
            device.disconnect();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "---->onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "---->onDestroy");
        super.onDestroy();
    }

    @OnClick({R.id.bt_run_cancel, R.id.layout_cancel, R.id.btn_turnon, R.id.btn_turnoff, R.id.btn_startrun, R.id.btn_stoprun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_run_cancel:
                this.finish();
                break;
            case R.id.layout_cancel:
                this.finish();
                break;
                //开启选中的设备灯
            case R.id.btn_turnon:
                turnOnLightByDeviceNum(selectedList);
                break;
                //关闭全部可用设备灯
            case R.id.btn_turnoff:
                turnOffLightByDeviceNum(selectedList);
                break;
            case R.id.btn_startrun:
                if (list.size() > 2) {
                    if (checkDeviceNum(list.size(), deviceAmount)) {
                        if (trainTimes == 0) {
                            Toast.makeText(this, "请选择训练次数，并重新开始训练！", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!trainingBeginFlag) {
                                createRandomList(trainTimes,selectedList);
                                startTraining();
                                btnTurnon.setClickable(false);
                                btnTurnoff.setClickable(false);
                            }
                        }
                    } else {
                        Toast.makeText(this, "可用设备数量小于选择的数量，请开启更多设备并重启该页面！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "当前可用设备不足2个，请开启更多设备并重启该页面！", Toast.LENGTH_SHORT).show();
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

    private void initDevices() {
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

        //设置spinner设备数量选择适配器
        spDeviceAmountAdapter = new ArrayAdapter(this, R.layout.spinner_item, Constant.deviceAmounts);
        spDevicenumbers.setAdapter(spDeviceAmountAdapter);
        spDevicenumbers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deviceAmount = Integer.parseInt((String) spDevicenumbers.getSelectedItem());
                Log.d("deviceAmounts", deviceAmount + "");
                for (int j=0;j<deviceAmount;j++) {
                    selectedList.add(list.get(j));
                    Log.d("编号", String.valueOf(list.get(j)));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        singleLineAdapter = new SingleLineAdapter(this);
        lvTimes.setAdapter(singleLineAdapter);
        spDeviceAmountAdapter.notifyDataSetChanged();
    }

    //存储可用的设备编号
    private void initData() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               Log.d("初始化数据", "填充list开始运行");
               //获取当前可用的设备编号，存储到list当中
               for (DeviceInfo info : Device.DEVICE_LIST) {
                   list.add(info.getDeviceNum());
               }
               /*//根据选择的设备数量，从list中顺序取出前设备数量个元素存储到selectdList中
               for (int i=0;i<deviceAmount;i++) {
                   selectedList.add(list.get(i));
                   Log.d("编号", String.valueOf(list.get(i)));
               }*/
           }
       }).start();


    }

    //检查当前选择的设备数是否没超过可用设备数量
    public boolean checkDeviceNum(int listSize,int deviceAmount) {
        if (deviceAmount <= listSize) {
            return true;
        } else {

            return false;
        }
    }

    //构成randomList,该list长度为训练次数
    public void createRandomList(int trainTimes,List<Character> selectedList) {
        for (int i=0;i<trainTimes;i++) {
            randomList.add(selectedList.get(NumberUtils.randomNumber(selectedList.size())));
            Log.d("randomList.["+i+"]", selectedList.get(NumberUtils.randomNumber(selectedList.size())) + "");
        }
    }

    private void startTraining() {
        trainingBeginFlag = true;
        timeList = new ArrayList<>(trainTimes);
        singleLineAdapter.setTimeList(timeList);
        singleLineAdapter.notifyDataSetChanged();
        //清除串口数据
        new ReceiveThread(handler, device.ftDev, ReceiveThread.CLEAR_DATA_THREAD, 0).start();

        //开启接收设备返回时间的监听线程
        new ReceiveThread(handler, device.ftDev, ReceiveThread.TIME_RECEIVE_THREAD, TIME_RECEIVE).start();

        device.sendOrder(randomList.get(0),       //randomList的第一个元素
                    Order.LightColor.values()[1],//此处需要更改设备颜色
                    Order.VoiceMode.values()[0],
                    Order.BlinkModel.values()[0],
                    Order.LightModel.OUTER,
                    Order.ActionModel.values()[1],//此处需要更改感应模式  0是无动作  1是感应
                    Order.EndVoice.values()[0]);


        //获得当前的系统时间
        startTime = System.currentTimeMillis();
        timer = new Timer(handler);
        timer.setBeginTime(startTime);
        timer.start();
    }

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

            ++counter;

            //将时间数据添加到timeList中
            timeList.add(info.getTime());
            if (counter >trainTimes-1) {
                break;
            }
            //打开下一个设备灯，下一个设备就是randomList中，counter作为索引
            turnOnLightByDeviceNum(randomList.get(counter));

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

    //用计数器判断开灯次数是否达到了训练次数，达到了就结束训练
    private boolean isTrainingOver() {
        if (counter >= trainTimes) {
            return true;
        } else {
            return false;
        }
    }

    //开单个设备
    public void turnOnLightByDeviceNum(Character deviceNum) {
        device.sendOrder(deviceNum,
                Order.LightColor.BLUE,
                Order.VoiceMode.NONE,
                Order.BlinkModel.NONE,
                Order.LightModel.OUTER,
                Order.ActionModel.values()[1],
                Order.EndVoice.NONE);
    }

    //根据设备编号开灯
    public void turnOnLightByDeviceNum(List<Character> selectedList) {
        for (Character devicenum :selectedList
             ) {
            device.sendOrder(devicenum,
                    Order.LightColor.BLUE,
                    Order.VoiceMode.NONE,
                    Order.BlinkModel.NONE,
                    Order.LightModel.OUTER,
                    Order.ActionModel.NONE,
                    Order.EndVoice.NONE);

        }
    }

    //根据设备编号关灯
    public void turnOffLightByDeviceNum(List<Character> selectedList) {
        for (Character devicenum :selectedList
                ) {
            device.sendOrder(devicenum,
                    Order.LightColor.values()[0],
                    Order.VoiceMode.NONE,
                    Order.BlinkModel.NONE,
                    Order.LightModel.OUTER,
                    Order.ActionModel.NONE,
                    Order.EndVoice.NONE);

        }
    }
}
