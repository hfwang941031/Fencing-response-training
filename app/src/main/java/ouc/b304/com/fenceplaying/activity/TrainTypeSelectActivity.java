package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.R;

/**
 * @author 王海峰 on 2019/1/10 15:44
 */
public class TrainTypeSelectActivity extends Activity {

    @BindView(R.id.img_icon_responsetrain)
    ImageView imgIconResponsetrain;
    @BindView(R.id.tv_responsetrain)
    TextView tvResponsetrain;
    @BindView(R.id.rl_responsetrain)
    RelativeLayout rlResponsetrain;
    @BindView(R.id.img_icon_accuracytrain)
    ImageView imgIconAccuracytrain;
    @BindView(R.id.tv_accuracy)
    TextView tvAccuracy;
    @BindView(R.id.rl_accuracytrain)
    RelativeLayout rlAccuracytrain;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainselect);
        ButterKnife.bind(this);
        context = this.getApplicationContext();
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



    @OnClick({R.id.rl_responsetrain, R.id.rl_accuracytrain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_responsetrain:
                startActivity(new Intent(context, ResponseSelect.class));

                break;
            case R.id.rl_accuracytrain:
                startActivity(new Intent(context, LAccuracyActivity.class));

                break;
        }
    }
}
