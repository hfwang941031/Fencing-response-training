package ouc.b304.com.fenceplaying.adapter;

import android.content.Context;
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
 * @author 王海峰 on 2018/11/26 09:08
 */
public class SingleLineAdapter extends BaseAdapter {
    private Context context;

    //每次时间的集合
    private ArrayList<Integer> timeList;

    public SingleLineAdapter(Context context) {
        this.context = context;
    }

    public void setTimeList(ArrayList<Integer> time) {
        this.timeList = time;
    }

    @Override
    public int getCount() {
        if (timeList == null)
            return 0;
        else
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
        SingleLineAdapter.ViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_single_spot_scores, null);
            viewHolder = new SingleLineAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (SingleLineAdapter.ViewHolder) convertView.getTag();

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
