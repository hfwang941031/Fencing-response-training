package ouc.b304.com.fenceplaying.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.entity.ShockInfo;

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
 * @author 王海峰 on 2019/5/8 15:39
 */
public class ShockInfoAdapter extends RecyclerView.Adapter<ShockInfoAdapter.ViewHolder> {
    private Context mContext;
    private List<ShockInfo> mShockInfoList;

    public ShockInfoAdapter(Context context, List<ShockInfo> shockInfoList) {
        mContext = context;
        mShockInfoList = shockInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shock, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ShockInfo shockInfo = mShockInfoList.get((mShockInfoList.size() - 1) - i);
        viewHolder.mNum.setText(mShockInfoList.size() - i + "");
        viewHolder.mName.setText(shockInfo.getName());
        String x = "";
        for (String s : shockInfo.getShack_x()) {
            x += s + ":";
        }
        String y = "";
        for (String s : shockInfo.getShack_y()) {
            y += s + ":";
        }
        String z = "";
        for (String s : shockInfo.getShack_z()) {
            z += s + ":";
        }
        viewHolder.mShock.setText("X(" + x + ")Y(" + y + ")Z(" + z + ")");

    }

    @Override
    public int getItemCount() {
        return mShockInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.num)
        TextView mNum;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.shock)
        TextView mShock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

