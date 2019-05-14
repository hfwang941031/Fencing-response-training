package ouc.b304.com.fenceplaying.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;

/**
 * //                       _oo0oo_
 * //                      o8888888o
 * //                      88" . "88
 * //                      (| -_- |)
 * //                      0\  =  /0
 * //                    ___/`---'\___
 * //                  .' \\|     |// '.
 * //                 / \\|||  :  |||// \
 * //                / _||||| -:- |||||- \
 * //               |   | \\\  -  /// |   |
 * //               | \_|  ''\---/''  |_/ |
 * //               \  .-\__  '-'  ___/-. /
 * //             ___'. .'  /--.--\  `. .'___
 * //          ."" '<  `.___\_<|>_/___.' >' "".
 * //         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * //         \  \ `_.   \_ __\ /__ _/   .-` /  /
 * //     =====`-.____`.___ \_____/___.-`___.-'=====
 * //                       `=---='
 * //
 * //
 * //     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * //
 * //               佛祖保佑         永无BUG
 * //
 * //
 * //
 *
 * @author 王海峰 on 2019/5/8 15:42
 */
public class AlterOrderNumDialog extends Dialog {
    private ViewHolder mViewHolder;
    private SetOnDialogListener mOnDialogListener;
    private Context mContext;

    public AlterOrderNumDialog(Context context, SetOnDialogListener onDialogListener) {
        super(context, R.style.Dialog);
        setCancelable(true);
        mContext = context;
        mOnDialogListener = onDialogListener;
        createDialog();
    }

    /**
     * 创建
     */
    private void createDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_alter_order_num, null);
        mViewHolder = new ViewHolder(view);
        mViewHolder.mNum.setText(AppConfig.sNum + "");
        mViewHolder.mNum.setSelection(mViewHolder.mNum.getText().toString().length());
        mViewHolder.mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mViewHolder.mNum.getText().toString();
                if (!"".equals(s)) {
                    AppConfig.sNum = Long.valueOf(s);
                    mOnDialogListener.sureClick();
                }
            }
        });
        super.setContentView(view);
    }


    public interface SetOnDialogListener {
        /**
         * 确定
         */
        void sureClick();
    }


    static class ViewHolder {
        @BindView(R.id.num)
        EditText mNum;
        @BindView(R.id.sure)
        Button mSure;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

