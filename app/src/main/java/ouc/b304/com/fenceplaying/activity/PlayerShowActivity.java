package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.Bean.Constant;
import ouc.b304.com.fenceplaying.Dao.MatrixScoresDao;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.SingleLineScoresDao;
import ouc.b304.com.fenceplaying.Dao.SingleSpotScoresDao;
import ouc.b304.com.fenceplaying.Dao.entity.Player;
import ouc.b304.com.fenceplaying.Dao.entity.SingleLineScores;
import ouc.b304.com.fenceplaying.Dao.entity.SingleSpotScores;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.PlayerShowAdapter;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;

public class PlayerShowActivity extends Activity {
    @BindView(R.id.bt_distance_cancel)
    ImageView btDistanceCancel;
    @BindView(R.id.layout_cancel)
    LinearLayout layoutCancel;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_help)
    ImageView imgHelp;
    @BindView(R.id.img_save)
    ImageView imgSave;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_showPlayerInfo)
    TextView tvShowPlayerInfo;
    @BindView(R.id.lv_showPlayerInfo)
    ListView lvShowPlayerInfo;
    @BindView(R.id.ll_leftcontent)
    LinearLayout llLeftcontent;
    @BindView(R.id.tv_addPlayerInfo)
    TextView tvAddPlayerInfo;
    @BindView(R.id.tv_playerName)
    TextView tvPlayerName;
    @BindView(R.id.edt_playerName)
    EditText edtPlayerName;
    @BindView(R.id.tv_playerGroup)
    TextView tvPlayerGroup;
    @BindView(R.id.edt_playerGroup)
    EditText edtPlayerGroup;
    @BindView(R.id.tv_playerAge)
    TextView tvPlayerAge;
    @BindView(R.id.spinner_age)
    Spinner spinnerAge;
    @BindView(R.id.tv_playerHeight)
    TextView tvPlayerHeight;
    @BindView(R.id.spinner_height)
    Spinner spinnerHeight;
    @BindView(R.id.tv_playerWeight)
    TextView tvPlayerWeight;
    @BindView(R.id.spinner_weight)
    Spinner spinnerWeight;
    @BindView(R.id.tv_playerGender)
    TextView tvPlayerGender;
    @BindView(R.id.radiobtn_man)
    RadioButton radiobtnMan;
    @BindView(R.id.radiobtn_woman)
    RadioButton radiobtnWoman;
    @BindView(R.id.tv_playMode)
    TextView tvPlayMode;
    @BindView(R.id.radiobtn_epee)
    RadioButton radiobtnEpee;
    @BindView(R.id.radiobtn_foil)
    RadioButton radiobtnFoil;
    @BindView(R.id.radiobtn_sabre)
    RadioButton radiobtnSabre;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.ll_rightcontent)
    LinearLayout llRightcontent;
    @BindView(R.id.radiogroup_gender)
    RadioGroup radiogroupGender;
    @BindView(R.id.radiogroup_trainmode)
    RadioGroup radiogroupTrainmode;


    private Context context;
    private String age = null;
    private String height = null;
    private String weight = null;
    private String name = null;
    private String groupId= null;
    private String gender = null;
    private String trainMode = null;
    private RadioButton radioButton;
    private PlayerDao playerDao;
    private ArrayAdapter<String> ageAdapter=null;
    private ArrayAdapter<String> heightAdapter=null;
    private ArrayAdapter<String> weightAdapter=null;
    private PlayerShowAdapter playerShowAdapter = null;
    private Player player;

    private SingleSpotScoresDao singleSpotScoresDao;
    private SingleLineScoresDao singleLineScoresDao;
    private MatrixScoresDao matrixScoresDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playershow);
        ButterKnife.bind(this);
        context=getApplicationContext();
        playerDao= GreenDaoInitApplication.getInstances().getDaoSession().getPlayerDao();
        singleSpotScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getSingleSpotScoresDao();
        singleLineScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getSingleLineScoresDao();
        matrixScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getMatrixScoresDao();
        initView();
        initAdapter();

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
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.layout_cancel,  R.id.radiobtn_man, R.id.radiobtn_woman, R.id.radiobtn_epee, R.id.radiobtn_foil, R.id.radiobtn_sabre,  R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_cancel:
                this.finish();
                break;
            case R.id.radiobtn_man:
                break;
            case R.id.radiobtn_woman:
                break;
            case R.id.radiobtn_epee:
                break;
            case R.id.radiobtn_foil:
                break;
            case R.id.radiobtn_sabre:
                break;
                /*添加信息按钮点击事件*/
            case R.id.btn_add:
                Log.d("Button_Add","has been clicked");
                initDataExceptRadioGroup();
                if (name == null||name.equals("")) {
                    Toast.makeText(this, "请输入运动员姓名", Toast.LENGTH_SHORT).show();
                }
                else if (groupId == null||groupId.equals("")) {
                    Toast.makeText(this, "请输入运动员组别", Toast.LENGTH_SHORT).show();
                }
                else {

                    player = new Player();
                    player.setName(name);
                    player.setAge(age);
                    player.setGender(gender);
                    player.setGroupId(groupId);
                    player.setHeight(height);
                    player.setTrainMode(trainMode);
                    player.setWeight(weight);
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayerShowActivity.this);
                    builder.setTitle("确认信息");
                    builder.setMessage(name +" "+age +" " + groupId + " 组 " + height+" " + weight +" "+ trainMode +" "+ gender);
                    builder.setNegativeButton("修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.dismiss();
                        }
                    });
                    builder.setPositiveButton("确认添加", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            playerDao.insert(player);
                            dialogInterface.dismiss();
                            playerShowAdapter.update();
                            Log.d("PlayerDao", " ID: " + player.getId()+"count"+playerDao.count()+"Name"+player.getName()+"Age"+player.getAge()+"Gender"+player.getGender()+"GroupId"+player.getGroupId()+"TrainMode"+player.getTrainMode());
                            Toast.makeText(PlayerShowActivity.this,"信息添加成功",Toast.LENGTH_SHORT).show();
                            cleanInfo();
                            playerShowAdapter.update();
                        }
                    });
                    builder.show();
                }
                break;
        }
    }

    /*将Spinner的信息置为初始化状态*/
    public void setSpinnerToNull(ArrayAdapter<String> adapter, String content,Spinner spinner) {
        int position = adapter.getPosition(content);
        spinner.setSelection(position);
    }

    /*运动员的各项信息置为初始状态*/
    public void cleanInfo() {
        edtPlayerName.setText("");
        edtPlayerGroup.setText("");
        setSpinnerToNull(ageAdapter, "0岁", spinnerAge);
        setSpinnerToNull(heightAdapter, "0cm", spinnerHeight);
        setSpinnerToNull(weightAdapter, "0kg", spinnerWeight);
    }
    /*初始化布局*/
    public void initView() {
        tvTitle.setText("运动员信息");
        radiogroupGender.check(R.id.radiobtn_man);
        RadioButton testRadiobtn=findViewById(R.id.radiobtn_man);
        gender=testRadiobtn.getText().toString();
        Log.d("性别", gender + "");
        radiogroupTrainmode.check(R.id.radiobtn_epee);
        RadioButton testRadiobtn2=findViewById(R.id.radiobtn_epee);
        trainMode=testRadiobtn2.getText().toString();
        Log.d("模式", trainMode + "");
        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                age = spinnerAge.getItemAtPosition(i).toString().trim();

                Log.d("initData方法", age);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*从SpinnerItem中获取身高 */
        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                height = spinnerHeight.getItemAtPosition(i).toString().trim();
                Log.d("initData方法", height);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*从SpinnerItem中获取体重 */
        spinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                weight = spinnerWeight.getItemAtPosition(i).toString().trim();
                Log.d("initData方法", weight);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /*获取性别*/

        radiogroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectRadioBtnOnGender(radiogroupGender);
                Log.d("initData方法", gender);
            }
        });
        /*获取训练方式*/
        radiogroupTrainmode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectRadioBtnOnTrainMode(radiogroupTrainmode);
                Log.d("initData方法", trainMode);
            }
        });

        //设置listview的长按事件
        lvShowPlayerInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final Player player2 = playerShowAdapter.getItem(position);
                String name = player2.getName();
                final Long playerId = player2.getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayerShowActivity.this);
                builder.setTitle("确认删除");
                builder.setMessage("您确定要删除"+" "+name+" 个人信息及其所有个人成绩吗");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除单点成绩
                        QueryBuilder<SingleSpotScores> qbsingleSpot = singleSpotScoresDao.queryBuilder();
                        List<SingleSpotScores> list1 = qbsingleSpot.where(SingleSpotScoresDao.Properties.PlayerId.eq(playerId)).list();
                        for (SingleSpotScores s:list1
                             ) {
                            singleSpotScoresDao.delete(s);
                        }

                        //删除单列成绩
                        QueryBuilder<SingleLineScores> qbsingleLine = singleLineScoresDao.queryBuilder();
                        List<SingleLineScores> list2 = qbsingleLine.where(SingleLineScoresDao.Properties.PlayerId.eq(playerId)).list();
                        for (SingleLineScores s:list2
                                ) {
                            singleLineScoresDao.delete(s);
                        }
                        playerDao.delete(player2);

                    }
                });
                builder.show();
                playerShowAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    /*初始化数据*/
    public void initDataExceptRadioGroup() {

        /*获取姓名*/
        name = String.valueOf(edtPlayerName.getText());
        Log.d("initData方法", name);
        /*获取组别*/
        groupId = edtPlayerGroup.getText().toString();
        Log.d("initData方法",groupId+"组");
        /*从SpinnerItem中获取年龄 */

    }
    /*分别为三个Spinner和Listview初始化适配器*/
    public void initAdapter() {
        context = getApplicationContext();
        ageAdapter = new ArrayAdapter<>(PlayerShowActivity.this, R.layout.spinner_item, Constant.PLAYER_AGE);
        spinnerAge.setAdapter(ageAdapter);
        heightAdapter = new ArrayAdapter<>(PlayerShowActivity.this, R.layout.spinner_item, Constant.PLAYER_HEIGHT);
        spinnerHeight.setAdapter(heightAdapter);
        weightAdapter = new ArrayAdapter<>(PlayerShowActivity.this, R.layout.spinner_item, Constant.PLAYER_WEIGHT);
        spinnerWeight.setAdapter(weightAdapter);
        playerShowAdapter = new PlayerShowAdapter(context);
        lvShowPlayerInfo.setAdapter(playerShowAdapter);
    }
    private void selectRadioBtnOnGender(RadioGroup radioGroup) {
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        gender = radioButton.getText().toString();
    }
    private void selectRadioBtnOnTrainMode(RadioGroup radioGroup) {
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        trainMode = radioButton.getText().toString();
    }


}
