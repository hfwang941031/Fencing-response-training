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
import ouc.b304.com.fenceplaying.R;

/**
 * @author 王海峰 on 2018/12/5 09:18
 * <p>
 * 弹出的保存成绩对话框中的listview适配器
 */
public class SaveResultAdapter extends BaseAdapter {
    private Context context;

    //运动员姓名的集合
    private List<String> nameList;

    public SaveResultAdapter(Context context) {
        this.context = context;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    @Override
    public int getCount() {
        if (nameList == null) {
            return 0;
        } else {
            return nameList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return this.nameList.get(i);
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
            convertView = inflater.inflate(R.layout.item_player_saveresult, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (SaveResultAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.tvPlayername.setText(nameList.get(position) + "");
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_playername)
        TextView tvPlayername;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
