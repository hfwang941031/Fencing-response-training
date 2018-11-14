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
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.MatrixAccuracyAdapter;
import ouc.b304.com.fenceplaying.adapter.MatrixAdapter;
import ouc.b304.com.fenceplaying.device.Device;
import ouc.b304.com.fenceplaying.device.Order;
import ouc.b304.com.fenceplaying.thread.AutoCheckPower;
import ouc.b304.com.fenceplaying.thread.ReceiveThread;
import ouc.b304.com.fenceplaying.thread.Timer;

import static ouc.b304.com.fenceplaying.thread.ReceiveThread.POWER_RECEIVE_THREAD;

/**
 * @author 王海峰 on 2018/11/12 16:13
 */
public class MatrixAccuracyActivity extends Activity {

    private final static int TIME_RECEIVE = 1;
    private final static int POWER_RECEIVE = 2;
    private final static int UPDATE_TIMES = 3;
    private final static int STOP_TRAINING = 4;
    private final static String TAG = "MatrixAccuracyActivity";
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
    @BindView(R.id.tv_setdeviceparam)
    TextView tvSetdeviceparam;
    @BindView(R.id.tv_setdevicemode)
    TextView tvSetdevicemode;
    @BindView(R.id.png_16fangzhen)
    ImageView png16fangzhen;
    @BindView(R.id.tvTotalTime)
    TextView tvTotalTime;
    @BindView(R.id.btn_startrun)
    Button btnStartrun;
    @BindView(R.id.btn_stoprun)
    Button btnStoprun;
    @BindView(R.id.lvTimes)
    ListView lvTimes;

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
    private int flagOfMode = 0;

    //存储每四个构成的char数组集合，长度为9
    List<char[]> listOfCombination = new ArrayList<char[]>();

    private AutoCheckPower checkPowerThread;

    private MatrixAccuracyAdapter matrixAccuracyAdapter;

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
                    matrixAccuracyAdapter.setTimeList(timeList);
                    matrixAccuracyAdapter.notifyDataSetChanged();
                    break;

            }

        }
    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "---->onCreate");
        setContentView(R.layout.activity_fangzhen);
        ButterKnife.bind(this);
        context = this.getApplicationContext();
        initDevices();
        initView();
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "---->onStart");
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
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "---->onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "---->onStop");
    }


    @OnClick({R.id.btn_turnon, R.id.btn_turnoff, R.id.img_btn_refresh, R.id.btn_startrun, R.id.btn_stoprun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_run_cancel:
                this.finish();
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
                for (Character name : list) {
                    device.sendOrder(name,
                            Order.LightColor.NONE,
                            Order.VoiceMode.NONE,
                            Order.BlinkModel.NONE,
                            Order.LightModel.TURN_OFF,
                            Order.ActionModel.NONE,
                            Order.EndVoice.NONE);
                }
                break;
            case R.id.img_btn_refresh:
                updateData();
                Toast.makeText(this, "可用设备已刷新", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_startrun:
                break;
            case R.id.btn_stoprun:
                break;
        }
    }

    //初始化界面
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
    }

    //初始化设备
    private void initDevices() {
        device = new Device(this);
        //更新连接设备列表
        device.createDeviceList(this);
        //判断是否插入协调器
        if (device.devCount > 0) {
            device.connect(this);
            device.initConfig();
            Timer.sleep(200);
            checkPowerThread = new AutoCheckPower(context, device, POWER_RECEIVE_THREAD, handler);
            checkPowerThread.start();
        }
    }
    //初始化数据
    private void initData() {
        //获取当前可用的设备编号，存储到list当中
        for (DeviceInfo info : Device.DEVICE_LIST) {
            list.add(info.getDeviceNum());
        }
        //如果当前可用设备的数量满足要求，则提示可以进行训练并加载感应器分组数据
        if (checkDeviceNumber(list,16)) {
            Toast.makeText(context, "可以进行训练，请保持感应器数量", Toast.LENGTH_SHORT).show();
            listOfCombination.add(Constant.first);
            listOfCombination.add(Constant.second);
            listOfCombination.add(Constant.third);
            listOfCombination.add(Constant.forth);
            listOfCombination.add(Constant.fifth);
            listOfCombination.add(Constant.sixth);
            listOfCombination.add(Constant.seventh);
            listOfCombination.add(Constant.eighth);
            listOfCombination.add(Constant.nineth);
            Toast.makeText(context, "分组数据加载完毕", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "可用设备数不足16个，无法继续进行训练", Toast.LENGTH_SHORT).show();

        }
    }


    //停止训练
    private void stopTraining() {
        trainingBeginFlag = false;
        //停止接收线程
        ReceiveThread.stopThread();
        device.turnOffAllTheLight();
        timer.stopTimer();

        //很重要的重置计数器
        counter = 0;
    }



    //对设备数量有要求，如果当前可用的设备少于16个 该项训练不能进行
    public boolean checkDeviceNumber(List<Character> list,int size) {
        if (list.size() == size) {
            return true;
        } else
            return false;
    }


    public void analyzeTimeData(final String data){}

    public void updateData() {
        StringBuffer stringBuffer = new StringBuffer(list.size());
        for (int i = 0; i < list.size(); i++) {
            stringBuffer.append(list.get(i));
            stringBuffer.append(" ");
        }
        tvDevicenumbers.setText("");
        tvDevicenumbers.setText(stringBuffer.toString());

    }

}
