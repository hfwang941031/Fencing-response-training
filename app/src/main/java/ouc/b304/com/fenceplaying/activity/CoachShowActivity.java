package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coachshow);
        ButterKnife.bind(this);
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
                break;
            case R.id.btn_add:
                break;
        }
    }
}
