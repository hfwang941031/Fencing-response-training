package ouc.b304.com.fenceplaying.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ouc.b304.com.fenceplaying.R;

/**
 * @author 王海峰 on 2018/11/14 15:59
 */
public class MatrixAccuracyAdapter extends BaseAdapter {

    private Context context;

    //每次时间的集合
    private ArrayList<Integer> timeList;

    public void setTimeList(ArrayList<Integer> time) {
        this.timeList = time;
    }


    @Override
    public int getCount() {
        //不加if容易造成空指针异常
        if (timeList == null) {
            return 0;
        }
        return timeList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_single_spot_scores, null);
            viewHolder = new MatrixAccuracyAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
            Log.d("getView 方法", "contentView为空");
        } else {
            viewHolder = (MatrixAccuracyAdapter.ViewHolder) convertView.getTag();
            Log.d("getView 方法", "contentView不为空");

        }
        viewHolder.tvOrderth.setText("第" + (position + 1) + "次");
        viewHolder.tvScores.setText(timeList.get(position) + "");
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_orderth)
        TextView tvOrderth;
        @BindView(R.id.tv_scores)
        TextView tvScores;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
