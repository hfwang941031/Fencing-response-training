package ouc.b304.com.fenceplaying.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ouc.b304.com.fenceplaying.Bean.DataShowListBean;
import ouc.b304.com.fenceplaying.R;

/**
 * @author 王海峰 on 2018/12/19 10:01
 */
public class DataShowAdapter extends BaseAdapter {
    private Context context;
    private List<DataShowListBean> beansList;//一个beans包括姓名、训练模式、训练次数，训练成绩

    public DataShowAdapter(Context context) {
        this.context = context;
    }

    public void setBeansList(List<DataShowListBean> beansList) {
        this.beansList = beansList;
    }

    @Override
    public int getCount() {
        if (beansList == null) {
            return 0;
        } else {
            return beansList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return beansList.get(i);
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
            convertView = inflater.inflate(R.layout.item_datashow, null);
            viewHolder = new DataShowAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DataShowAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.tvNameshow.setText(beansList.get(position).getName() );
        viewHolder.tvShowtrainmode.setText(beansList.get(position).getTrainMode() );
        viewHolder.tvShowtraintimes.setText(String.valueOf(beansList.get(position).getTrainTimes()));
        viewHolder.tvShowscores.setText(beansList.get(position).getAverageScore() );
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_nameshow)
        TextView tvNameshow;
        @BindView(R.id.tv_showtrainmode)
        TextView tvShowtrainmode;
        @BindView(R.id.tv_showtraintimes)
        TextView tvShowtraintimes;
        @BindView(R.id.tv_showscores)
        TextView tvShowscores;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
