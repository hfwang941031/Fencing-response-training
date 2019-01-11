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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.Bean.Constant;
import ouc.b304.com.fenceplaying.Dao.CoachDao;
import ouc.b304.com.fenceplaying.Dao.entity.Coach;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.CoachShowAdapter;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;

/**
 * @author 王海峰 on 2019/1/11 09:37
 */
public class CoachShowActivity extends Activity {

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
    @BindView(R.id.tv_showCoachInfo)
    TextView tvShowCoachInfo;
    @BindView(R.id.tv_playerShowName)
    TextView tvPlayerShowName;
    @BindView(R.id.tv_playerShowGroupId)
    TextView tvPlayerShowGroupId;
    @BindView(R.id.tv_playerShowAge)
    TextView tvPlayerShowAge;
    @BindView(R.id.tv_playerShowHeight)
    TextView tvPlayerShowHeight;
    @BindView(R.id.tv_playerShowWeight)
    TextView tvPlayerShowWeight;
    @BindView(R.id.tv_playerShowGender)
    TextView tvPlayerShowGender;
    @BindView(R.id.tv_playerShowTrainMode)
    TextView tvPlayerShowTrainMode;
    @BindView(R.id.lv_showCoachInfo)
    ListView lvShowCoachInfo;
    @BindView(R.id.ll_leftcontent)
    LinearLayout llLeftcontent;
    @BindView(R.id.tv_addCoachInfo)
    TextView tvAddCoachInfo;
    @BindView(R.id.tv_coachName)
    TextView tvCoachName;
    @BindView(R.id.edt_coachName)
    EditText edtCoachName;
    @BindView(R.id.tv_coachGroup)
    TextView tvCoachGroup;
    @BindView(R.id.spinner_level)
    Spinner spinnerLevel;
    @BindView(R.id.tv_coachAge)
    TextView tvCoachAge;
    @BindView(R.id.spinner_age)
    Spinner spinnerAge;
    @BindView(R.id.tv_coachHeight)
    TextView tvCoachHeight;
    @BindView(R.id.spinner_height)
    Spinner spinnerHeight;
    @BindView(R.id.tv_coachWeight)
    TextView tvCoachWeight;
    @BindView(R.id.spinner_weight)
    Spinner spinnerWeight;
    @BindView(R.id.tv_coachGender)
    TextView tvCoachGender;
    @BindView(R.id.radiobtn_man)
    RadioButton radiobtnMan;
    @BindView(R.id.radiobtn_woman)
    RadioButton radiobtnWoman;
    @BindView(R.id.radiogroup_gender)
    RadioGroup radiogroupGender;
    @BindView(R.id.tv_playMode)
    TextView tvPlayMode;
    @BindView(R.id.radiobtn_epee)
    RadioButton radiobtnEpee;
    @BindView(R.id.radiobtn_foil)
    RadioButton radiobtnFoil;
    @BindView(R.id.radiobtn_sabre)
    RadioButton radiobtnSabre;
    @BindView(R.id.radiogroup_trainmode)
    RadioGroup radiogroupTrainmode;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.ll_rightcontent)
    LinearLayout llRightcontent;

    private Context context;
    private String gender=null;
    private String trainMode=null;
    private String age=null;
    private ArrayAdapter<String> ageAdapter=null;
    private ArrayAdapter<String> heightAdapter=null;
    private ArrayAdapter<String> weightAdapter=null;
    private ArrayAdapter<String> levelAdapter=null;
    private String height=null;
    private String weight=null;
    private String name=null;
    private String level = null;
    private CoachShowAdapter coachShowAdapter = null;
    private Coach coach;

    private CoachDao coachDao;

    private RadioButton radioButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coachshow);
        ButterKnife.bind(this);
        context = this.getApplicationContext();
        coachDao = GreenDaoInitApplication.getInstances().getDaoSession().getCoachDao();
        initView();
        initAdapter();
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

    @OnClick({R.id.layout_cancel, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_cancel:
                this.finish();

                break;
            case R.id.btn_add:
                Log.d("Button_Add","has been clicked");
                initDataExceptRadioGroup();
                if (name == null||name.equals("")) {
                    Toast.makeText(this, "请输入教练员姓名", Toast.LENGTH_SHORT).show();
                }

                else {

                    coach = new Coach();
                    coach.setName(name);
                    coach.setAge(age);
                    coach.setGender(gender);
                    coach.setLevel(level);
                    coach.setHeight(height);
                    coach.setTrainMode(trainMode);
                    coach.setWeight(weight);
                    AlertDialog.Builder builder = new AlertDialog.Builder(CoachShowActivity.this);
                    builder.setTitle("确认信息");
                    builder.setMessage(name +" "+age +" " + level + "  " + height+" " + weight +" "+ trainMode +" "+ gender);
                    builder.setNegativeButton("修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setPositiveButton("确认添加", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            coachDao.insert(coach);
                            dialogInterface.dismiss();
                            coachShowAdapter.update();
                            Toast.makeText(CoachShowActivity.this,"信息添加成功",Toast.LENGTH_SHORT).show();
                            cleanInfo();
                            coachShowAdapter.update();
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

    /*教练员的各项信息置为初始状态*/
    public void cleanInfo() {
        edtCoachName.setText("");
        setSpinnerToNull(ageAdapter, "0岁", spinnerAge);
        setSpinnerToNull(heightAdapter, "0cm", spinnerHeight);
        setSpinnerToNull(weightAdapter, "0kg", spinnerWeight);
        setSpinnerToNull(levelAdapter, "国家级", spinnerLevel);
    }
    /*初始化布局*/
    public void initView() {
        tvTitle.setText("教练员信息");
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

        //获取教练等级
        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                level = spinnerLevel.getItemAtPosition(i).toString().trim();
                Log.d("initData方法", level);
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
        lvShowCoachInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final Coach coach1 = coachShowAdapter.getItem(position);
                String name = coach1.getName();
                final Long coach1Id = coach1.getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(CoachShowActivity.this);
                builder.setTitle("确认删除");
                builder.setMessage("您确定要删除"+" "+name+" 个人信息吗");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        coachDao.delete(coach1);
                        coachShowAdapter.update();
                        Toast.makeText(context,"删除成功！",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

                return false;
            }
        });


    }

    /*初始化数据*/
    public void initDataExceptRadioGroup() {

        /*获取姓名*/
        name = String.valueOf(edtCoachName.getText());
        Log.d("initData方法", name);


    }
    /*分别为三个Spinner和Listview初始化适配器*/
    public void initAdapter() {
        context = getApplicationContext();
        ageAdapter = new ArrayAdapter<>(CoachShowActivity.this, R.layout.spinner_item, Constant.PLAYER_AGE);
        spinnerAge.setAdapter(ageAdapter);
        heightAdapter = new ArrayAdapter<>(CoachShowActivity.this, R.layout.spinner_item, Constant.PLAYER_HEIGHT);
        spinnerHeight.setAdapter(heightAdapter);
        weightAdapter = new ArrayAdapter<>(CoachShowActivity.this, R.layout.spinner_item, Constant.PLAYER_WEIGHT);
        spinnerWeight.setAdapter(weightAdapter);
        levelAdapter = new ArrayAdapter<>(CoachShowActivity.this, R.layout.spinner_item, Constant.COACH_LEVEL);
        spinnerLevel.setAdapter(levelAdapter);
        coachShowAdapter = new CoachShowAdapter(context);
        lvShowCoachInfo.setAdapter(coachShowAdapter);
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
