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
import ouc.b304.com.fenceplaying.entity.DbLight;

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
 * @author 王海峰 on 2019/5/8 15:52
 */
public class LightOperateAdapter extends RecyclerView.Adapter<LightOperateAdapter.ViewHolder> {
    private Context mContext;
    private List<DbLight> mDbLights;
    private SetOnOperateListener mOnOperateListener;

    public LightOperateAdapter(Context context, List<DbLight> dbLights, SetOnOperateListener onOperateListener) {
        mContext = context;
//        List<DbLight> lightList = new ArrayList<>();
//        for (int i = 0; i < dbLights.size(); i++) {
//            DbLight light = dbLights.get(i);
//            if (light.getLightType() == 1){
//                lightList.add(light);
//            }
//        }
        mDbLights = dbLights;
        mOnOperateListener = onOperateListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_operate_light, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final DbLight light = mDbLights.get(i);
        viewHolder.mLight.setVisibility(View.VISIBLE);

        if (!"00".equals(light.getName())) {
            viewHolder.mLight.setText(light.getName());
        } else {
            viewHolder.mLight.setText(i + 1 + "");
        }
        Integer power = Integer.valueOf(light.getPower(), 16);
        if (power == 0) {
            viewHolder.mLight.setBackgroundResource(R.mipmap.light_default_bg);
        } else {
            if (light.isSelect()) {
                viewHolder.mLight.setBackgroundResource(R.mipmap.light_check_bg);
            } else {
                viewHolder.mLight.setBackgroundResource(R.mipmap.light);
            }
            viewHolder.mLight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (light.isSelect()) {
                        light.setSelect(false);
                    } else {
                        light.setSelect(true);
                    }
                    notifyDataSetChanged();
                    mOnOperateListener.onOperateListener(light);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mDbLights.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.light)
        TextView mLight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface SetOnOperateListener {
        /**
         * 当前灯的选择状态
         *
         * @param light
         */
        void onOperateListener(DbLight light);
    }
}

