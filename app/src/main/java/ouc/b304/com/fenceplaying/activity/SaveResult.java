package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.R;

/**
 * @author 王海峰 on 2018/12/3 16:00
 * <p>
 * 创建一个页面，可以让多个训练页面跳转至此并进行保存
 */
public class SaveResult extends Activity {

    @BindView(R.id.bt_run_cancel)
    ImageView btRunCancel;
    @BindView(R.id.lv_player)
    ListView lvPlayer;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_nosave)
    Button btnNosave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saveresult);
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

    @OnClick({R.id.bt_run_cancel, R.id.btn_save, R.id.btn_nosave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_run_cancel:
                break;
            case R.id.btn_save:
                break;
            case R.id.btn_nosave:
                break;
        }
    }
}
