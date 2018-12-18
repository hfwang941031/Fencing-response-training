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
 * @author 王海峰 on 2018/12/17 08:39
 */
public class DataShowSelect extends Activity {


    @BindView(R.id.btn_datashow)
    Button btnDatashow;
    @BindView(R.id.btn_datacompare)
    Button btnDatacompare;

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datashowselect);
        ButterKnife.bind(this);
        this.context=getApplicationContext();
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

    @OnClick({R.id.btn_datashow, R.id.btn_datacompare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_datashow:
                startActivity(new Intent(context, DataShowActivity.class));
                break;
            case R.id.btn_datacompare:
                startActivity(new Intent(context, DataCompareActivity.class));
                break;
        }
    }
}
