package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ouc.b304.com.fenceplaying.R;

/**
 * @author 王海峰 on 2018/9/17 10:46
 */
public class ResponseSelect extends Activity {
    @BindView(R.id.btn_singlespot)
    Button btnSinglespot;
    @BindView(R.id.btn_singlecolumn)
    Button btnSinglecolumn;
    @BindView(R.id.btn_matrix)
    Button btnMatrix;
    private Context context;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
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

    @OnClick({R.id.btn_singlespot, R.id.btn_singlecolumn, R.id.btn_matrix})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_singlespot:
                startActivity(new Intent(context,SingleSpotActivity.class));
                break;
            case R.id.btn_singlecolumn:
                break;
            case R.id.btn_matrix:
                startActivity(new Intent(context,MatrixActivity.class));
                break;
        }
    }
}
