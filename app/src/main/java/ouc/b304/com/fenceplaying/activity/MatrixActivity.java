package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.Bean.Constant;
import ouc.b304.com.fenceplaying.Bean.DeviceInfo;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.device.Device;
import ouc.b304.com.fenceplaying.device.Order;
import ouc.b304.com.fenceplaying.thread.ReceiveThread;

/**
 * @author 王海峰 on 2018/10/9 16:09
 */
public class MatrixActivity extends Activity {
    private static String TAG = "MatrixActivity";
    @BindView(R.id.bt_run_cancel)
    ImageView btRunCancel;
    @BindView(R.id.layout_cancel)
    LinearLayout layoutCancel;
/*    @BindView(R.id.sp_devices)
    Spinner spDevices;*/
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
    private Context context;
    private Device device;
    private char deviceNum;

    //存储在当前页面中可以用的设备编号
    private List<Character> list = new ArrayList<>();

    //所有两个设备编号组成的list集合
    private List<ArrayList<Character>> subList = new ArrayList<>();

    //选中的训练次数
    private int trainTimes=0;

    //训练次数下拉框适配器
    private ArrayAdapter<String> spTimesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"---->onCreate");
        setContentView(R.layout.activity_matrix);
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
        initData();
        initView();
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
        Log.d(TAG,"---->onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"---->onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"---->onPause");
        device.turnOffAllTheLight();
        ReceiveThread.stopThread();
        if (device.devCount > 0)
            device.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"---->onDestroy");
    }

    @OnClick({R.id.bt_run_cancel, R.id.layout_cancel, R.id.btn_turnon, R.id.btn_turnoff, R.id.btn_startrun, R.id.btn_stoprun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_run_cancel:
                break;
            case R.id.layout_cancel:
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
                device.turnOffAllTheLight();
                break;
            case R.id.btn_startrun:
                break;
            case R.id.btn_stoprun:
                break;
        }
    }
    //对设备数量有要求，如果当前可用的设备少于四个 该项训练不能进行
    public boolean checkDeviceNumber(List<Character> list) {
        if (list.size() >= 4) {
            return true;
        }
        else
            return false;
    }
    //产生范围内的随机数
    public int randomNumber(int range) {
        return (int) (Math.random() * range);
    }

    //返回生成包含两个不同随机设备编号的list
    public ArrayList<Character> randomSubList(List<Character> list) {
        List<Character> subList = new ArrayList<>();
        int random1= randomNumber(list.size());
        int random2= randomNumber(list.size());
        while (random1==random2) {
            random2 = randomNumber(list.size());
        }
        subList.add(list.get(random1));
        subList.add(list.get(random2));
        return (ArrayList<Character>) subList;
    }

    //将每个subList都添加到list中，形成所有subList的集合
    public List<ArrayList<Character>> listOfSubList(int trainTimes) {
        for (int i=0;i<trainTimes;i++) {
            subList.add(randomSubList(list));
        }
        return subList;
    }
}
