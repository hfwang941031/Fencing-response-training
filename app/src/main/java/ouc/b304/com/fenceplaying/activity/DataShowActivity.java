package ouc.b304.com.fenceplaying.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.qmuiteam.qmui.widget.textview.QMUILinkTextView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import ouc.b304.com.fenceplaying.Bean.DataShowListBean;
import ouc.b304.com.fenceplaying.Dao.LScoresDao;
import ouc.b304.com.fenceplaying.Dao.MatrixScoresDao;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.SingleLineScoresDao;
import ouc.b304.com.fenceplaying.Dao.SingleSpotScoresDao;
import ouc.b304.com.fenceplaying.Dao.entity.LScores;
import ouc.b304.com.fenceplaying.Dao.entity.MatrixScores;
import ouc.b304.com.fenceplaying.Dao.entity.Player;
import ouc.b304.com.fenceplaying.Dao.entity.SingleLineScores;
import ouc.b304.com.fenceplaying.Dao.entity.SingleSpotScores;
import ouc.b304.com.fenceplaying.R;
import ouc.b304.com.fenceplaying.adapter.DataShowAdapter;
import ouc.b304.com.fenceplaying.application.GreenDaoInitApplication;


/**
 * @author 王海峰 on 2018/12/17 14:21
 */
public class DataShowActivity extends Activity {
    @BindView(R.id.tv_playername)
    TextView tvPlayername;
    @BindView(R.id.sp_playername)
    Spinner spPlayername;
    @BindView(R.id.tv_starttime)
    TextView tvStarttime;
    @BindView(R.id.btn_starttime)
    Button btnStarttime;
    @BindView(R.id.tv_endtime)
    TextView tvEndtime;
    @BindView(R.id.btn_endtime)
    Button btnEndtime;
    @BindView(R.id.tv_trainingmode)
    TextView tvTrainingmode;
    @BindView(R.id.sp_trainingmode)
    Spinner spTrainingmode;
    @BindView(R.id.btn_show)
    Button btnShow;
    @BindView(R.id.tv_nameshow)
    TextView tvNameshow;
    @BindView(R.id.tv_showtrainmode)
    TextView tvShowtrainmode;
    @BindView(R.id.tv_showtraintimes)
    TextView tvShowtraintimes;
    @BindView(R.id.tv_showscores)
    TextView tvShowscores;
    @BindView(R.id.lv_showdata)
    ListView lvShowdata;
    @BindView(R.id.linechartview_datashow)
    LineChartView linechartviewDatashow;
    @BindView(R.id.tv_chartname)
    QMUILinkTextView tvChartname;
    @BindView(R.id.columnchartview_datashow)
    ColumnChartView columnchartviewDatashow;
    @BindView(R.id.tv_columnchartname)
    QMUILinkTextView tvColumnchartname;
    private Player player;
    private PlayerDao playerDao;
    private List<String> nameList;//存放运动员姓名
    private List<Player> playerList;
    private ArrayAdapter<String> spNameAdapter;
    private Context context;
    private String selectedName;//列表中选中的运动员名字
    private TimePickerView startPVTime;
    private TimePickerView endPVTime;
    private Date startDate;
    private Date endDate;
    private SimpleDateFormat dateFormat;
    private ArrayAdapter<String> spTrainModeAdapter;
    private List<String> modeList;
    private String selectedTrainMode;
    private DataShowAdapter dataShowAdapter;
    //单点
    private SingleSpotScoresDao singleSpotScoresDao;
    private SingleSpotScores singleSpotScores;
    private List<SingleSpotScores> singleSpotScoresList;
    //单列
    private SingleLineScoresDao singleLineScoresDao;
    private SingleLineScores singleLineScores;
    private List<SingleLineScores> singleLineScoresList;
    //抗干扰
    private MatrixScoresDao matrixScoresDao;
    private MatrixScores matrixScores;
    private List<MatrixScores> matrixScoresList;
    //精准
    private LScoresDao lScoresDao;
    private LScores lScores;
    private List<LScores> lScoresList;

    private List<DataShowListBean> dataShowListBeans;
    /*private DataShowListBean dataShowListBean;*/


    private DataShowListBean dataShowListBean;

    //单点折线图变量相关
    private List<Float> averageScoreList = new ArrayList<>();
    private Line line = new Line().setColor(Color.GREEN);//声明并定义一条线
    private List<PointValue> values = new ArrayList<>();  //线上的点，一个PointValue形式为（x,y）
    private List<Line> lines = new ArrayList<>();
    private LineChartData data = new LineChartData();
    Axis axisX = new Axis();//x轴
    Axis axisY = new Axis();//y轴
    List<AxisValue> axisValuesOfLineChart=new ArrayList<>();

    //柱状图相关

    private List<Float> concreteScoreList = new ArrayList<>();//每次的具体成绩

    private List<Column> columns = new ArrayList<>();
    private ColumnChartData columnChartData = new ColumnChartData();
    Axis axisXColunm = new Axis();//x轴
    Axis axisYColumn = new Axis();//y轴
    List<AxisValue> axisValuess=new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datashow);
        ButterKnife.bind(this);
        context = this.getApplicationContext();
        playerDao = GreenDaoInitApplication.getInstances().getDaoSession().getPlayerDao();
        singleSpotScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getSingleSpotScoresDao();
        singleLineScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getSingleLineScoresDao();
        matrixScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getMatrixScoresDao();
        lScoresDao = GreenDaoInitApplication.getInstances().getDaoSession().getLScoresDao();
        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.btn_starttime, R.id.btn_endtime, R.id.btn_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_starttime:
                if (selectedName != null) {
                    startPVTime.show(view);
                } else {
                    Toast.makeText(context, "请先选择运动员", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_endtime:
                if (startDate != null) {
                    endPVTime.show(view);
                } else {
                    Toast.makeText(context, "请先选择开始时间", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_show:
                if (selectedName!=null) {
                    if (startDate != null) {

                        if (endDate != null) {

                            if (selectedTrainMode != null) {
                                switch (selectedTrainMode) {
                                    case "单点训练":

                                        //清除averageScoreList
                                        if (averageScoreList.size() != 0) {
                                            averageScoreList.clear();
                                        }

                                        //首先清除beanlist,防止出现重复数据
                                        if (dataShowListBeans.size() != 0) {
                                            dataShowListBeans.clear();
                                        }


                                        //1、根据选定的运动员姓名确定该运动员ID，2、通过运动员ID确定成绩表中的属于该运动员的成绩3、并存储到singleSpotScoresList
                                        QueryBuilder<Player> qb = playerDao.queryBuilder();
                                        playerList = qb.where(PlayerDao.Properties.Name.eq(selectedName)).list();
                                        long playerId = playerList.get(0).getId();
                                        QueryBuilder<SingleSpotScores> queryBuilder = singleSpotScoresDao.queryBuilder();
                                        singleSpotScoresList = queryBuilder.where(SingleSpotScoresDao.Properties.PlayerId.eq(playerId)).list();
                                        Log.d("Size of ScoreList", singleSpotScoresList.size() + "");
                                        if (singleSpotScoresList.size() <= 0) {
                                            Toast.makeText(context, "无当前运动员单点训练成绩，请先进行训练", Toast.LENGTH_SHORT).show();
                                        } else {
                                            for (SingleSpotScores s : singleSpotScoresList) {
                                                //将平均成绩添加到averageScoreList中
                                                averageScoreList.add(s.getAverageScores());

                                                dataShowListBean = new DataShowListBean();
                                                dataShowListBean.setName(selectedName);
                                                dataShowListBean.setTrainMode(selectedTrainMode);
                                                dataShowListBean.setTrainTimes(s.getTrainingTimes());
                                                dataShowListBean.setAverageScore(String.valueOf(s.getAverageScores()));
                                                dataShowListBeans.add(dataShowListBean);
                                            }
                                            dataShowAdapter.setBeansList(dataShowListBeans);
                                            dataShowAdapter.notifyDataSetChanged();

                                            //设置折线图属性
                                            setChartViewPara();
                                            //开始画图
                                            showLineChartView(linechartviewDatashow, averageScoreList, values, lines, line, data, axisX, axisY);


                                            //设置listview子项的点击事件，通过点击该item形成对应的柱状图
                                            lvShowdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                    //首先清空上次的具体成绩list


                                                    DataShowListBean dataShowListBean1 = dataShowAdapter.getItem(position);//通过当前点击的item位置找到该位置所对应的item实体
                                                    String tempAverageScore = dataShowListBean1.getAverageScore();//通过item实体找到平均成绩值

                                                    String name = dataShowListBean1.getName();
                                                    int trainTimes = dataShowListBean1.getTrainTimes();//通过item实体找到训练次数
                                                    QueryBuilder<SingleSpotScores> queryBuilder1 = singleSpotScoresDao.queryBuilder();//新建一个查询
                                                    QueryBuilder<Player> queryBuilder2 = playerDao.queryBuilder();//新建一个关于运动员的查询
                                                    List<Player> playerList = queryBuilder2.where(PlayerDao.Properties.Name.eq(name)).list();
                                                    Long playerId = playerList.get(0).getId();
                                                    queryBuilder1.where(SingleSpotScoresDao.Properties.PlayerId.eq(playerId), queryBuilder1.and(SingleSpotScoresDao.Properties.TrainingTimes.eq(trainTimes), SingleSpotScoresDao.Properties.AverageScores.eq(tempAverageScore)));
/*
                                    queryBuilder1.and(,);//通过组合查询训练次数和平均成绩找到该item对应的单点成绩实体
*/
                                                    List<SingleSpotScores> singleSpotScoresList = queryBuilder1.list();//列出单点成绩实体
                                                    concreteScoreList.clear();
                                                    for (String s : singleSpotScoresList.get(0).getScoresList()//将单点成绩中的具体成绩list中的每一个元素添加到具体成绩list中
                                                            ) {
                                                        concreteScoreList.add(Float.valueOf(s));
                                                    }
                                                    axisValuess.clear();
                                                    for (int i = 0; i < concreteScoreList.size(); i++) {
                                                        axisValuess.add(new AxisValue(i).setLabel("第" + (i + 1) + "次"));
                                                    }

                                                    //设置柱状图属性
                                                    setColumnChartViewPara();
                                                    //开始画图
                                                    showColumnChartView(columnchartviewDatashow, concreteScoreList,/*columnValueList,*/columns, columnChartData, axisXColunm, axisYColumn);
                                                }
                                            });

                                        }
                                        break;
                                    case "单列训练":
                                        //清除averageScoreList
                                        if (averageScoreList.size() != 0) {
                                            averageScoreList.clear();
                                        }

                                        //首先清除beanlist,防止出现重复数据
                                        if (dataShowListBeans.size() != 0) {
                                            dataShowListBeans.clear();
                                        }
                                        //1、根据选定的运动员姓名确定该运动员ID，2、通过运动员ID确定成绩表中的属于该运动员的成绩3、并存储到singleSpotScoresList
                                        QueryBuilder<Player> qb1 = playerDao.queryBuilder();
                                        playerList = qb1.where(PlayerDao.Properties.Name.eq(selectedName)).list();
                                        long playerId1 = playerList.get(0).getId();
                                        QueryBuilder<SingleLineScores> queryBuilder1 = singleLineScoresDao.queryBuilder();
                                        singleLineScoresList = queryBuilder1.where(SingleLineScoresDao.Properties.PlayerId.eq(playerId1)).list();
                                        Log.d("Size of ScoreList", singleLineScoresList.size() + "");
                                        if (singleLineScoresList.size() <= 0) {
                                            Toast.makeText(context, "无当前运动员单列训练成绩，请先进行训练", Toast.LENGTH_SHORT).show();
                                        } else {
                                            for (SingleLineScores s : singleLineScoresList) {
                                                //将平均成绩添加到averageScoreList中
                                                averageScoreList.add(s.getAverageScores());
                                                dataShowListBean = new DataShowListBean();
                                                dataShowListBean.setName(selectedName);
                                                dataShowListBean.setTrainMode(selectedTrainMode);
                                                dataShowListBean.setTrainTimes(s.getTrainingTimes());
                                                dataShowListBean.setAverageScore(String.valueOf(s.getAverageScores()));
                                                dataShowListBeans.add(dataShowListBean);
                                            }
                                            dataShowAdapter.setBeansList(dataShowListBeans);
                                            dataShowAdapter.notifyDataSetChanged();

                                            //开始画图
                                            showLineChartView(linechartviewDatashow, averageScoreList, values, lines, line, data, axisX, axisY);

                                            //设置listview子项的点击事件，通过点击该item形成对应的柱状图
                                            lvShowdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                    //首先清空上次的具体成绩list


                                                    DataShowListBean dataShowListBean1 = dataShowAdapter.getItem(position);//通过当前点击的item位置找到该位置所对应的item实体
                                                    String tempAverageScore = dataShowListBean1.getAverageScore();//通过item实体找到平均成绩值

                                                    String name = dataShowListBean1.getName();
                                                    int trainTimes = dataShowListBean1.getTrainTimes();//通过item实体找到训练次数
                                                    QueryBuilder<SingleLineScores> queryBuilder1 = singleLineScoresDao.queryBuilder();//新建一个查询
                                                    QueryBuilder<Player> queryBuilder2 = playerDao.queryBuilder();//新建一个关于运动员的查询
                                                    List<Player> playerList = queryBuilder2.where(PlayerDao.Properties.Name.eq(name)).list();
                                                    Long playerId = playerList.get(0).getId();
                                                    queryBuilder1.where(SingleLineScoresDao.Properties.PlayerId.eq(playerId), queryBuilder1.and(SingleLineScoresDao.Properties.TrainingTimes.eq(trainTimes), SingleLineScoresDao.Properties.AverageScores.eq(tempAverageScore)));
/*
                                    queryBuilder1.and(,);//通过组合查询训练次数和平均成绩找到该item对应的单点成绩实体
*/
                                                    List<SingleLineScores> singleLineScoresList = queryBuilder1.list();//列出单点成绩实体
                                                    concreteScoreList.clear();
                                                    for (String s : singleLineScoresList.get(0).getScoresList()//将单点成绩中的具体成绩list中的每一个元素添加到具体成绩list中
                                                            ) {
                                                        concreteScoreList.add(Float.valueOf(s));
                                                    }
                                                    axisValuess.clear();
                                                    for (int i = 0; i < concreteScoreList.size(); i++) {
                                                        axisValuess.add(new AxisValue(i).setLabel("第" + (i + 1) + "次"));
                                                    }

                                                    //设置柱状图属性
                                                    setColumnChartViewPara();
                                                    //开始画图
                                                    showColumnChartView(columnchartviewDatashow, concreteScoreList,/*columnValueList,*/columns, columnChartData, axisXColunm, axisYColumn);
                                                }
                                            });

                                        }
                                        break;
                                    case "抗干扰训练":
                                        //清除averageScoreList
                                        if (averageScoreList.size() != 0) {
                                            averageScoreList.clear();
                                        }

                                        //首先清除beanlist,防止出现重复数据
                                        if (dataShowListBeans.size() != 0) {
                                            dataShowListBeans.clear();
                                        }
                                        //1、根据选定的运动员姓名确定该运动员ID，2、通过运动员ID确定成绩表中的属于该运动员的成绩3、并存储到singleSpotScoresList
                                        QueryBuilder<Player> qb3 = playerDao.queryBuilder();
                                        playerList = qb3.where(PlayerDao.Properties.Name.eq(selectedName)).list();
                                        long playerId2 = playerList.get(0).getId();
                                        QueryBuilder<MatrixScores> queryBuilder2 = matrixScoresDao.queryBuilder();
                                        matrixScoresList = queryBuilder2.where(MatrixScoresDao.Properties.PlayerId.eq(playerId2)).list();
                                        Log.d("Size of ScoreList", matrixScoresList.size() + "");
                                        if (matrixScoresList.size() <= 0) {
                                            Toast.makeText(context, "无当前运动员单点训练成绩，请先进行训练", Toast.LENGTH_SHORT).show();
                                        } else {
                                            for (MatrixScores s : matrixScoresList) {
                                                //将平均成绩添加到averageScoreList中
                                                averageScoreList.add(s.getAverageScores());

                                                dataShowListBean = new DataShowListBean();
                                                dataShowListBean.setName(selectedName);
                                                dataShowListBean.setTrainMode(selectedTrainMode);
                                                dataShowListBean.setTrainTimes(s.getTrainingTimes());
                                                dataShowListBean.setAverageScore(String.valueOf(s.getAverageScores()));
                                                dataShowListBeans.add(dataShowListBean);
                                            }
                                            dataShowAdapter.setBeansList(dataShowListBeans);
                                            dataShowAdapter.notifyDataSetChanged();

                                            //设置折线图属性
                                            setChartViewPara();
                                            //开始画图
                                            showLineChartView(linechartviewDatashow, averageScoreList, values, lines, line, data, axisX, axisY);


                                            //设置listview子项的点击事件，通过点击该item形成对应的柱状图
                                            lvShowdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                    //首先清空上次的具体成绩list


                                                    DataShowListBean dataShowListBean1 = dataShowAdapter.getItem(position);//通过当前点击的item位置找到该位置所对应的item实体
                                                    String tempAverageScore = dataShowListBean1.getAverageScore();//通过item实体找到平均成绩值

                                                    String name = dataShowListBean1.getName();
                                                    int trainTimes = dataShowListBean1.getTrainTimes();//通过item实体找到训练次数
                                                    QueryBuilder<MatrixScores> queryBuilder1 = matrixScoresDao.queryBuilder();//新建一个查询
                                                    QueryBuilder<Player> queryBuilder2 = playerDao.queryBuilder();//新建一个关于运动员的查询
                                                    List<Player> playerList = queryBuilder2.where(PlayerDao.Properties.Name.eq(name)).list();
                                                    Long playerId = playerList.get(0).getId();
                                                    queryBuilder1.where(MatrixScoresDao.Properties.PlayerId.eq(playerId), queryBuilder1.and(MatrixScoresDao.Properties.TrainingTimes.eq(trainTimes), MatrixScoresDao.Properties.AverageScores.eq(tempAverageScore)));
/*
                                    queryBuilder1.and(,);//通过组合查询训练次数和平均成绩找到该item对应的单点成绩实体
*/
                                                    List<MatrixScores> matrixScoresList = queryBuilder1.list();//列出单点成绩实体
                                                    concreteScoreList.clear();
                                                    for (String s : matrixScoresList.get(0).getScoresList()//将单点成绩中的具体成绩list中的每一个元素添加到具体成绩list中
                                                            ) {
                                                        concreteScoreList.add(Float.valueOf(s));
                                                    }
                                                    axisValuess.clear();
                                                    for (int i = 0; i < concreteScoreList.size(); i++) {
                                                        axisValuess.add(new AxisValue(i).setLabel("第" + (i + 1) + "次"));
                                                    }

                                                    //设置柱状图属性
                                                    setColumnChartViewPara();
                                                    //开始画图
                                                    showColumnChartView(columnchartviewDatashow, concreteScoreList,/*columnValueList,*/columns, columnChartData, axisXColunm, axisYColumn);
                                                }
                                            });

                                        }
                                        break;
                                    case "精准训练":
                                        break;
                                }
                            } else {
                                Toast.makeText(context, "请先选择训练类别", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "请先选择结束时间", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "请先选择开始时间", Toast.LENGTH_SHORT).show();
                    }

                    }
                 else {
                    Toast.makeText(context,"请先选择运动员",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public void initData() {
        //初始化nameList,将姓名添加到nameList
        nameList = new ArrayList<>();
        playerList = playerDao.loadAll();
        nameList.add("请选择运动员");
        for (Player p : playerList) {
            nameList.add(p.getName());
        }

        //初始化时间格式器
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //初始化modeList
        modeList = new ArrayList<>();
        modeList.add("请选择训练模式");
        modeList.add("单点训练");
        modeList.add("单列训练");
        modeList.add("抗干扰训练");
        modeList.add("精准训练");
        //初始化listview适配器
        dataShowAdapter = new DataShowAdapter(context);

        //初始化beansList
        dataShowListBeans = new ArrayList<>();


    }

    public void initView() {

        //为nameSpinner设置适配器
        spNameAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, nameList);
        spPlayername.setAdapter(spNameAdapter);
        spPlayername.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    Toast.makeText(context, "请先选择运动员", Toast.LENGTH_SHORT).show();
                } else {
                    selectedName = (String) adapterView.getSelectedItem();
                    Log.d("selectedName--", selectedName + "");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //初始化开始时间选择器
        initStartTimePicker();
        //初始化结束时间选择器
        initEndTimePicker();


        //为trainModeSpinner设置适配器
        spTrainModeAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, modeList);
        spTrainingmode.setAdapter(spTrainModeAdapter);
        spTrainingmode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (selectedName != null) {
                    if (startDate != null) {
                        if (endDate != null) {
                            if (selectedTrainMode==null) {
                                selectedTrainMode = (String) adapterView.getSelectedItem();
                                Log.d("selectedTrainMode--", selectedTrainMode + "");
                            } else {
                                selectedTrainMode = (String) adapterView.getSelectedItem();
                            }
                        } else {

                            Toast.makeText(context, "请先选择终止时间", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "请先选择起始时间", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "请先选择运动员", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //为listview设置适配器
        lvShowdata.setAdapter(dataShowAdapter);










    }

    //初始化选择开始时间选择器
    public void initStartTimePicker() {
        startPVTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //设置选择时间按钮的文字为当前选好的时间
                startDate = date;
                btnStarttime.setText(dateFormat.format(startDate));
            }
        }).setContentTextSize(30).isDialog(true).build();
        Dialog mDialog = startPVTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            startPVTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }


    }

    //初始化选择结束时间选择器
    public void initEndTimePicker() {
        endPVTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                endDate = date;
                if (startDate != null) {
                    if (endDate.compareTo(startDate) >= 0) {
                        btnEndtime.setText(dateFormat.format(endDate));
                    } else {
                        Toast.makeText(context, "终止时间不能早于开始时间，请重新选择终止时间", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "请先选择开始时间", Toast.LENGTH_SHORT).show();
                }
            }
        }).setContentTextSize(30).isDialog(true).build();
        Dialog mDialog = endPVTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            endPVTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }


    }

    //设置折线图属性
    public void setChartViewPara() {

        linechartviewDatashow.setZoomEnabled(true);//设置是否支持缩放
        linechartviewDatashow.setInteractive(true);//设置图表是否可以与用户互动
        linechartviewDatashow.setValueSelectionEnabled(true);//设置图表数据是否选中进行显示
        linechartviewDatashow.setBackgroundColor(ChartUtils.pickColor());
        line.setCubic(false);//设置为直线而非平滑线
        line.setPointColor(Color.RED);//设置点的颜色
        line.setHasLabels(true);
        //设置坐标轴各类属性
        axisX.setHasLines(true).setTextColor(Color.BLACK).setLineColor(ChartUtils.pickColor()).setTextSize(12).setName("训练次序");
        axisY.setHasLines(true).setTextColor(Color.BLACK).setLineColor(ChartUtils.pickColor()).setName("成绩/毫秒");
    }

    //开始画折线图
    public void showLineChartView(LineChartView lineChartView, List<Float> averageScoreList, List<PointValue> values, List<Line> lineList, Line line, LineChartData data, Axis axisX, Axis axisY) {
        //1、清除上次的数据，包括valueList和lineList
        values.clear();
        lineList.clear();
        //2、添加点的数据
        axisValuesOfLineChart.clear();
        for (int i = 0; i < averageScoreList.size(); i++) {
            values.add(new PointValue(i + 1, averageScoreList.get(i)));
            axisValuesOfLineChart.add(new AxisValue(i).setLabel("第" + (i) + "次"));
            if (i == averageScoreList.size() - 1) {
                axisValuesOfLineChart.add(new AxisValue(i+1).setLabel("第" + (i+1) + "次"));
            }

        }

        axisX.setValues(axisValuesOfLineChart);
        line.setValues(values);//把点添加到线上
        lineList.add(line);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setLines(lineList);
        lineChartView.setLineChartData(data);//给图表设置数据
        tvChartname.setText(selectedName + " " + selectedTrainMode + "成绩折线图");
    }

    //开始画柱状图
    public void showColumnChartView(ColumnChartView columnChartView, List<Float> concreteScoreList, /*List<SubcolumnValue> valueList,*/ List<Column> columnList,  ColumnChartData columnChartData, Axis axisX, Axis axisY) {
        //1、清除上次的数据，包括valueList和columnList
        columnList.clear();
        //2、添加点的数据
        for (int i = 0; i < concreteScoreList.size(); i++) {
            SubcolumnValue subcolumnValue = new SubcolumnValue(concreteScoreList.get(i), ChartUtils.pickColor());
            List<SubcolumnValue> valueList=new ArrayList<>();
            valueList.add(subcolumnValue);
            Column column = new Column(valueList);
            column.setHasLabels(true);//设置柱状图标签
            columnList.add(column);
        }
        columnChartData.setColumns(columnList);
        columnChartView.setColumnChartData(columnChartData);//给图表设置数据
        tvColumnchartname.setText("本次训练详细数据");

    }

    //设置柱状图属性
    public void setColumnChartViewPara() {
        columnchartviewDatashow.setZoomEnabled(true);//设置是否支持缩放
        columnchartviewDatashow.setInteractive(true);//设置图表是否可以与用户互动
        columnchartviewDatashow.setValueSelectionEnabled(true);//设置图表数据是否选中进行显示
        columnchartviewDatashow.setBackgroundColor(ChartUtils.COLOR_BLUE);//设置柱状图背景颜色


        //设置坐标轴各类属性
        axisXColunm.setValues(axisValuess);
        axisXColunm.setName("训练次序");
        axisXColunm.setLineColor(ChartUtils.pickColor());
        axisXColunm.setTextColor(Color.BLACK);
        axisYColumn.setHasLines(true).setName("成绩/毫秒");
        axisYColumn.setTextColor(Color.BLACK);
        axisYColumn.setLineColor(ChartUtils.pickColor());
        columnChartData.setAxisYLeft(axisYColumn);
        columnChartData.setAxisXBottom(axisXColunm);
    }
}
