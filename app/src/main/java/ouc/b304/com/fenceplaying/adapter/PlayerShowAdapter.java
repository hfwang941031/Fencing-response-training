package ouc.b304.com.fenceplaying.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.entity.Player;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;

public class PlayerShowAdapter extends BaseAdapter {
    private List<Player> list;
    private Context context;

    private PlayerDao playerDao = GreenDaoInitApplication.getInstances().getDaoSession().getPlayerDao();

    public PlayerShowAdapter(Context context) {
        this.list=playerDao.loadAll();
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Player getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Player player=list.get(position);
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView=inflater.inflate(R.layout.item_player, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            Log.d("getView 方法", "contentView为空");
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            Log.d("getView 方法", "contentView不为空");

        }
        String name=player.getName();
        viewHolder.tvPlayerShowName.setText(name);
        Log.d("Name", name+"***");

        String age=player.getAge();
        viewHolder.tvPlayerShowAge.setText(age);
        Log.d("Age", age+"***");

        String gender=player.getGender();
        viewHolder.tvPlayerShowGender.setText(gender);
        Log.d("Gender", gender+"***");

        String groupId=player.getGroupId();
        viewHolder.tvPlayerShowGroupId.setText(groupId);
        Log.d("GroupId", groupId+"***");

        String height=player.getHeight();
        viewHolder.tvPlayerShowHeight.setText(height);
        Log.d("Height", height + "***");

        String weight=player.getWeight();
        viewHolder.tvPlayerShowWeight.setText(weight);
        Log.d("Weight", weight + "***");

        String trainMode = player.getTrainMode();
        viewHolder.tvPlayerShowTrainMode.setText(trainMode);
        Log.d("TrainMode", trainMode + "***");
        return convertView;

    }
    public void update() {
        list=playerDao.loadAll();
        notifyDataSetChanged();
    }


    static class ViewHolder {
        @BindView(R.id.tv_playerShowName)
        TextView tvPlayerShowName;
        @BindView(R.id.tv_playerShowGroupId)
        TextView tvPlayerShowGroupId;
        @BindView(R.id.tv_playerShowAge)
        TextView tvPlayerShowAge;
        @BindView(R.id.tv_playerShowHeight)
        TextView tvPlayerShowHeight;
        @BindView(R.id.tv_playerShowWeight)
        TextView tvPlayerShowWeight;
        @BindView(R.id.tv_playerShowGender)
        TextView tvPlayerShowGender;
        @BindView(R.id.tv_playerShowTrainMode)
        TextView tvPlayerShowTrainMode;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
