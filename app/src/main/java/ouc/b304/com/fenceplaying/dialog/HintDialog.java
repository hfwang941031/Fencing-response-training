package ouc.b304.com.fenceplaying.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ouc.b304.com.fenceplaying.R;

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
 * @author 王海峰 on 2019/5/8 15:43
 */
public class HintDialog extends Dialog {
    private Context mContext;
    private String mHintString;
    private String mCancelStr;
    private String mSureStr;

    private DialogOperation mDialogOperation;
    private ViewHolder mViewHolder;

    public HintDialog(Context context, String hintString, String cancelStr, String sureStr, DialogOperation dialogOperation) {
        super(context, R.style.Dialog);
        setCancelable(true);
        mContext = context;
        mHintString = hintString;
        mCancelStr = cancelStr;
        mSureStr = sureStr;
        mDialogOperation = dialogOperation;
        showDialog();
    }


    private void showDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_hint, null);
        mViewHolder = new ViewHolder(view);
        mViewHolder.mHintText.setText(mHintString);
        mViewHolder.mCancel.setText(mCancelStr);
        mViewHolder.mSure.setText(mSureStr);
        mViewHolder.mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogOperation.sureClick();

            }
        });
        mViewHolder.mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogOperation.cancelClick();
            }
        });
        super.setContentView(view);
    }

    public void setCancelText(String text) {
        mViewHolder.mCancel.setText(text);
    }

    public interface DialogOperation {
        void sureClick();

        void cancelClick();
    }

    static class ViewHolder {
        @BindView(R.id.hintText)
        TextView mHintText;
        @BindView(R.id.cancel)
        TextView mCancel;
        @BindView(R.id.sure)
        TextView mSure;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

