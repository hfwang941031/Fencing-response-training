package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.activity.newVersion.NewMatrixActivity;
import ouc.b304.com.fenceplaying.activity.newVersion.NewSingleLineActivity;
import ouc.b304.com.fenceplaying.activity.newVersion.NewSingleSpotActivity;

/**
 * @author 王海峰 on 2018/9/17 10:46
 */
public class ResponseSelect extends Activity {

    @BindView(R.id.img_icon_responsetrain)
    ImageView imgIconResponsetrain;
    @BindView(R.id.tv_singlesopt)
    TextView tvSinglesopt;
    @BindView(R.id.rl_singlespot)
    RelativeLayout rlSinglespot;
    @BindView(R.id.img_icon_singleline)
    ImageView imgIconSingleline;
    @BindView(R.id.tv_singleline)
    TextView tvSingleline;
    @BindView(R.id.rl_singleline)
    RelativeLayout rlSingleline;
    @BindView(R.id.img_icon_matrix)
    ImageView imgIconMatrix;
    @BindView(R.id.tv_matrix)
    TextView tvMatrix;
    @BindView(R.id.rl_matrix)
    RelativeLayout rlMatrix;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responseselect);
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
    protected void onDestroy() {
        super.onDestroy();
    }



    @OnClick({R.id.rl_singlespot, R.id.rl_singleline, R.id.rl_matrix})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_singlespot:
                startActivity(new Intent(context, NewSingleSpotActivity.class));

                break;
            case R.id.rl_singleline:
                startActivity(new Intent(context, NewSingleLineActivity.class));

                break;
            case R.id.rl_matrix:
                startActivity(new Intent(context, NewMatrixActivity.class));

                break;
        }
    }
}
