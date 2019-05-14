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
import ouc.b304.com.fenceplaying.entity.TimeInfo;

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
 * @author 王海峰 on 2019/5/8 15:34
 */
public class TimeInfoAdapter extends RecyclerView.Adapter<TimeInfoAdapter.ViewHolder> {
    private Context mContext;
    private List<TimeInfo> mTimeInfoList;

    public TimeInfoAdapter(Context context, List<TimeInfo> timeInfoList) {
        mContext = context;
        mTimeInfoList = timeInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_time_info, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TimeInfo timeInfo = mTimeInfoList.get((mTimeInfoList.size() - 1) - i);
        viewHolder.mNum.setText(mTimeInfoList.size() - i + "");
        viewHolder.mTime.setText(timeInfo.getTime() + "ms");
        if ("00".equals(timeInfo.getName())) {
            viewHolder.mName.setText(timeInfo.getAddress());
        } else {
            viewHolder.mName.setText(timeInfo.getName());
        }

        viewHolder.mModel.setText(timeInfo.getReactionModel());
    }

    @Override
    public int getItemCount() {
        return mTimeInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.num)
        TextView mNum;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.model)
        TextView mModel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
