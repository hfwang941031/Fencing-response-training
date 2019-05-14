package ouc.b304.com.fenceplaying.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.order.CommandNew;
import ouc.b304.com.fenceplaying.order.OrderUtils;
import ouc.b304.com.fenceplaying.utils.newUtils.ToastUtils;

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
 * @author 王海峰 on 2019/5/8 15:49
 */
public class LightAdapter extends RecyclerView.Adapter<LightAdapter.ViewHolder> {
    private Context mContext;
    private List<DbLight> mDbLights;
    private int mAlterIndex = -1;
    private SetOnLightListener mOnLightListener;

    public LightAdapter(Context context, List<DbLight> dbLights, SetOnLightListener onLightListener) {
        mContext = context;
        mDbLights = dbLights;
        mOnLightListener = onLightListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_alter_light, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final DbLight light = mDbLights.get(i);

        viewHolder.mIvType.setImageResource(R.drawable.light);

        String name = light.getName();
        viewHolder.mAddress.setText(light.getAddress());
        if (!"00".equals(name)) {
            if (i == mAlterIndex) {
                viewHolder.mTvName.setVisibility(View.GONE);
                viewHolder.mEtName.setVisibility(View.VISIBLE);
                viewHolder.mEtName.setText(name);
                viewHolder.mOpen.setVisibility(View.VISIBLE);
                viewHolder.mAlter.setVisibility(View.GONE);
                viewHolder.mSure.setVisibility(View.VISIBLE);
                viewHolder.mOut.setVisibility(View.GONE);
                viewHolder.mCancel.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mTvName.setVisibility(View.VISIBLE);
                viewHolder.mTvName.setText(light.getName());
                viewHolder.mEtName.setVisibility(View.GONE);
                viewHolder.mOpen.setVisibility(View.VISIBLE);
                viewHolder.mAlter.setVisibility(View.VISIBLE);
                viewHolder.mSure.setVisibility(View.GONE);
                viewHolder.mOut.setVisibility(View.VISIBLE);
                viewHolder.mCancel.setVisibility(View.GONE);
            }
        } else {
            viewHolder.mTvName.setVisibility(View.GONE);
            viewHolder.mEtName.setVisibility(View.VISIBLE);
            viewHolder.mEtName.setText("");
            viewHolder.mOpen.setVisibility(View.VISIBLE);
            viewHolder.mAlter.setVisibility(View.GONE);
            viewHolder.mSure.setVisibility(View.VISIBLE);
            viewHolder.mOut.setVisibility(View.VISIBLE);
            viewHolder.mCancel.setVisibility(View.GONE);
        }
        //点亮
        viewHolder.mOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommandNew commandNew = new CommandNew();
                OrderUtils.getInstance().sendCommand(commandNew, light.getAddress(), new OrderUtils.SetOnOrderListener() {
                    @Override
                    public void onOrderListener(String order) {

                    }
                });
            }
        });
        //修改
        viewHolder.mAlter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnLightListener.onAlterListener(i);
            }
        });
        //确定
        viewHolder.mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = viewHolder.mEtName.getText().toString();
                if (!"".equals(num)) {
                    mOnLightListener.onNetInListener(light, num);
                } else {
                    ToastUtils.showToast(mContext, "请输入编号", Toast.LENGTH_SHORT);
                }
            }
        });
        //取消
        viewHolder.mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlterIndex = -1;
                notifyDataSetChanged();
            }
        });
        //退网
        viewHolder.mOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnLightListener.onWithDrawListener(light);
            }
        });
        //强制退网
        viewHolder.mOut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnLightListener.onWithDrawListener2(light);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDbLights.size();
    }

    public void setAlterIndex(int alterIndex) {
        mAlterIndex = alterIndex;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_type)
        ImageView mIvType;
        @BindView(R.id.address)
        TextView mAddress;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.et_name)
        EditText mEtName;
        @BindView(R.id.open)
        Button mOpen;
        @BindView(R.id.alter)
        Button mAlter;
        @BindView(R.id.out)
        Button mOut;
        @BindView(R.id.out2)
        Button mOut2;
        @BindView(R.id.sure)
        Button mSure;
        @BindView(R.id.cancel)
        Button mCancel;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface SetOnLightListener {
        /**
         * 入网
         *
         * @param light
         * @param name
         */
        void onNetInListener(DbLight light, String name);

        /**
         * 退网
         *
         * @param light
         */
        void onWithDrawListener(DbLight light);
        /**
         * 强制退网
         *
         * @param light
         */
        void onWithDrawListener2(DbLight light);

        /**
         * 修改
         *
         * @param alterIndex
         */
        void onAlterListener(int alterIndex);
    }
}

