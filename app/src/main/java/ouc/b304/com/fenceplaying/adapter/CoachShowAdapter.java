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
import ouc.b304.com.fenceplaying.Dao.CoachDao;
import ouc.b304.com.fenceplaying.Dao.entity.Coach;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;

/**
 * @author 王海峰 on 2019/1/11 10:16
 */
public class CoachShowAdapter extends BaseAdapter {
    private List<Coach> list;
    private Context context;
    private CoachDao coachDao = GreenDaoInitApplication.getInstances().getDaoSession().getCoachDao();

    public CoachShowAdapter(Context context) {
        this.context = context;
        this.list = coachDao.loadAll();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Coach getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Coach coach = list.get(position);
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_coach, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            Log.d("getView 方法", "contentView为空");
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            Log.d("getView 方法", "contentView不为空");

        }
        String name=coach.getName();
        viewHolder.tvCoachShowName.setText(name);
        Log.d("Name", name+"***");

        String age=coach.getAge();
        viewHolder.tvCoachShowAge.setText(age);
        Log.d("Age", age+"***");

        String gender=coach.getGender();
        viewHolder.tvCoachShowGender.setText(gender);
        Log.d("Gender", gender+"***");

        String level=coach.getLevel();
        viewHolder.tvCoachLevel.setText(level);
        Log.d("level", level+"***");

        String height=coach.getHeight();
        viewHolder.tvCoachShowHeight.setText(height);
        Log.d("Height", height + "***");

        String weight=coach.getWeight();
        viewHolder.tvCoachShowWeight.setText(weight);
        Log.d("Weight", weight + "***");

        String trainMode = coach.getTrainMode();
        viewHolder.tvCoachShowTrainMode.setText(trainMode);
        Log.d("TrainMode", trainMode + "***");
        return convertView;
    }

    public void update() {
        list=coachDao.loadAll();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.tv_coachShowName)
        TextView tvCoachShowName;
        @BindView(R.id.tv_coachLevel)
        TextView tvCoachLevel;
        @BindView(R.id.tv_coachShowAge)
        TextView tvCoachShowAge;
        @BindView(R.id.tv_coachShowHeight)
        TextView tvCoachShowHeight;
        @BindView(R.id.tv_coachShowWeight)
        TextView tvCoachShowWeight;
        @BindView(R.id.tv_coachShowGender)
        TextView tvCoachShowGender;
        @BindView(R.id.tv_coachShowTrainMode)
        TextView tvCoachShowTrainMode;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
