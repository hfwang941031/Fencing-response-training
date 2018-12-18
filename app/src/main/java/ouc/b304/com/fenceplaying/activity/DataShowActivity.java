package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.entity.Player;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;


/**
 * @author 王海峰 on 2018/12/17 14:21
 */
public class DataShowActivity extends Activity {
    @BindView(R.id.tv_playername)
    TextView tvPlayername;
    @BindView(R.id.sp_playername)
    Spinner spPlayername;
    @BindView(R.id.tv_starttime)
    TextView tvStarttime;
    @BindView(R.id.btn_starttime)
    Button btnStarttime;
    @BindView(R.id.tv_endtime)
    TextView tvEndtime;
    @BindView(R.id.btn_endtime)
    Button btnEndtime;
    @BindView(R.id.tv_trainingmode)
    TextView tvTrainingmode;
    @BindView(R.id.sp_trainingmode)
    Spinner spTrainingmode;
    @BindView(R.id.btn_show)
    Button btnShow;
    @BindView(R.id.tv_nameshow)
    TextView tvNameshow;
    @BindView(R.id.tv_showtrainmode)
    TextView tvShowtrainmode;
    @BindView(R.id.tv_showtraintimes)
    TextView tvShowtraintimes;
    @BindView(R.id.tv_showscores)
    TextView tvShowscores;
    @BindView(R.id.lv_showdata)
    ListView lvShowdata;
    private Player player;
    private PlayerDao playerDao;
    private List<String> nameList;//存放运动员姓名
    private List<Player> playerList;
    private ArrayAdapter<String> spNameAdapter;
    private Context context;
    private String selectedName;//列表中选中的运动员名字
    private TimePickerView startPVTime;
    private TimePickerView endPVTime;
    private Date startDate;
    private Date endDate;
    private SimpleDateFormat dateFormat;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datashow);
        ButterKnife.bind(this);
        context = this.getApplicationContext();
        playerDao = GreenDaoInitApplication.getInstances().getDaoSession().getPlayerDao();

        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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

    @OnClick({R.id.btn_starttime, R.id.btn_endtime, R.id.btn_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_starttime:
                if (selectedName != null) {
                    startPVTime.show(view);
                } else {
                    Toast.makeText(context,"请先选择运动员",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_endtime:
                endPVTime.show(view);
                break;
            case R.id.btn_show:
                break;
        }
    }
    public void initData() {
        //初始化nameList,将姓名添加到nameList
        nameList = new ArrayList<>();
        playerList=playerDao.loadAll();
        for (Player p:playerList) {
            nameList.add(p.getName());
        }

        //初始化时间格式器
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    }
    public void initView() {

        //为nameSpinner设置适配器
        spNameAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, nameList);
        spPlayername.setAdapter(spNameAdapter);
        spPlayername.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedName = (String) adapterView.getSelectedItem();
                Log.d("selectedName--", selectedName + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //初始化开始时间选择器
        initStartTimePicker();
        //初始化结束时间选择器
        initEndTimePicker();

    }
    //初始化选择开始时间选择器
    public void initStartTimePicker() {
        startPVTime=new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //设置选择时间按钮的文字为当前选好的时间
                startDate=date;
                btnStarttime.setText(dateFormat.format(startDate));
            }
        }).setContentTextSize(30).isDialog(true).build();
        Dialog mDialog=startPVTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            startPVTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }


    }
    //初始化选择结束时间选择器
    public void initEndTimePicker() {
        endPVTime=new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                endDate=date;
                if (endDate.compareTo(startDate)>=0) {
                    btnEndtime.setText(dateFormat.format(endDate));
                } else {
                    Toast.makeText(context, "终止时间不能早于开始时间，请重新选择终止时间", Toast.LENGTH_SHORT).show();
                }

            }
        }).setContentTextSize(30).isDialog(true).build();
        Dialog mDialog=endPVTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            endPVTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }


    }
}
