package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.R;

/**
 * @author 王海峰 on 2018/11/12 14:00
 */
public class AccuracySelectActivity extends Activity {
    @BindView(R.id.btn_fangzhen)
    Button btnFangzhen;
    @BindView(R.id.btn_Lshape)
    Button btnLshape;
    private Context context;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accuracyselect);
        ButterKnife.bind(this);
        this.context = getApplicationContext();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        this.finish();
    }


    @OnClick({R.id.btn_fangzhen, R.id.btn_Lshape})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fangzhen:
                startActivity(new Intent(context,MatrixAccuracyActivity.class));

                break;
            case R.id.btn_Lshape:
                startActivity(new Intent(context,LAccuracyActivity.class));

                break;
        }
    }
}
