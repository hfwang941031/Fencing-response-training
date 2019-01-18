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

public class AccuracyAdapter extends BaseAdapter {
    private ArrayList<Boolean> list = new ArrayList<>();
    private Context context;
    public void setList(ArrayList<Boolean> list){
        this.list = list;
    }
    public AccuracyAdapter(Context context,ArrayList<Boolean> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        if (list != null){
            return list.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null) {
            view = inflater.inflate(R.layout.item_single_spot_scores, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tvOrderth.setTextSize(25);
        if (list.get(i)){
            viewHolder.tvOrderth.setText("√");
        }else {
            viewHolder.tvOrderth.setText("×");
        }
        return view;
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
