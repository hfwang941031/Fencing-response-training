package ouc.b304.com.fenceplaying.DB.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ouc.b304.com.fenceplaying.Bean.Result;
import ouc.b304.com.fenceplaying.DB.SQLOpenHelper;

public class ResultDao {

    private SQLOpenHelper helper;
    private Context context;

    public ResultDao(Context context) {
        // TODO Auto-generated constructor stub
        helper = new SQLOpenHelper(context);
        this.context = context;
    }

    // 通过id来删除某一条数据
    public void deleteResultById(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from result where id=?";
        db.execSQL(sql, new Object[]{id});
        db.close();

    }

    // 插入数据,对象形式
    public void SaveResult(Result result) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(
                "insert into result(trainingmode,trainingname,groupstr,unitno,timesno,groupresult,unitresult,timesresult,responsetime,grouptime,distance,time,player)values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{result.getTrainingmode(),
                        result.getTrainingname(), result.getGroupstr(),
                        result.getUnitno(), result.getTimesno(),
                        result.getGroupresult(), result.getUnitresult(),
                        result.getTimesresult(), result.getResponsetime(),
                        result.getGrouptime(), result.getDistance(),
                        result.getTime(), result.getPlayer()});

        db.close();
    }

    public void delete() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DROP TABLE result");
        db.close();
    }

    //查询所有运动员
    public List<String> listAllPlayers() {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select player from result";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("player"));
            if (id == null) {
                id = "";
            }
            list.add(id);
        }
        cursor.close();
        db.close();
        return list;
    }

    // 获取所有成绩信息
    public List<Map<String, String>> listResultByName(String name) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select  distinct group1._id,group1.trainingmode,group1.groupname,group1.unitno, group1.timesno,group1.groupstr,group1.time,group1.Isdistance,group1.distancestr,group1.Ispai,group1.paidistance,group1.remark_group from result,player,group1 where player=? and result.player=player.name and group1.groupname=result.trainingname ";
        Cursor cursor = db.rawQuery(sql, new String[]{name});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == "null") {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    //通过运动员和时间段查询成绩
    public List<Map<String, String>> listResultByNameTime(String name, String starttime, String endtime) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select  distinct group1._id,group1.trainingmode,group1.groupname,group1.unitno, group1.timesno,group1.groupstr,group1.time,group1.Isdistance,group1.distancestr,group1.Ispai,group1.paidistance,group1.remark_group from result,player,group1 where player=? and result.player=player.name and group1.groupname=result.trainingname  and result.time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{name, starttime, endtime});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == "null") {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;
    }

    //通过运动员时间段训练形式组合名称查询成绩

    public List<Map<String, String>> listResultByNameTimeModeGroup(String name, String trainingMode, String group, String starttime, String endtime) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select  distinct group1._id,group1.trainingmode,group1.groupname,group1.unitno, group1.timesno,group1.groupstr,group1.time,group1.Isdistance,group1.distancestr,group1.Ispai,group1.paidistance,group1.remark_group from result,player,group1 where player=? and result.player=player.name and group1.trainingmode=? and group1.groupname=? and group1.groupname=result.trainingname and result.time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{name, trainingMode, group, starttime, endtime});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == "null") {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;
    }

    //通过运动员时间段和训练形式查询成绩
    public List<Map<String, String>> listResultByNameTimeTrainMode(String name, String trainingMode, String starttime, String endtime){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select  distinct group1._id,group1.trainingmode,group1.groupname,group1.unitno, group1.timesno,group1.groupstr,group1.time,group1.Isdistance,group1.distancestr,group1.Ispai,group1.paidistance,group1.remark_group from result,player,group1 where player=? and result.player=player.name and group1.trainingmode=? and group1.trainingmode=result.trainingmode and result.time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{name, trainingMode, starttime, endtime});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == "null") {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;
    }

    // 通过时间段查询运动员
    public List<String> ListPlayerByTime(String starttime, String endtime) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select distinct player from result where  time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{starttime, endtime});
        while (cursor.moveToNext()) {

            String player = cursor.getString(cursor.getColumnIndex("player"));
            if (player == null) {
                player = "";
            }
            list.add(player);
        }
        cursor.close();
        db.close();

        return list;

    }

    // 通过运动员名称和程序名称查询反应时间
    public List<String> ListResponseByPAM(String player, String trainingmode) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select responsetime from result where player=? and trainingmode= ?";
        Cursor cursor = db.rawQuery(sql, new String[]{player, trainingmode});
        while (cursor.moveToNext()) {

            String responsetime = cursor.getString(cursor
                    .getColumnIndex("responsetime"));
            if (responsetime == null) {
                responsetime = "";
            }
            list.add(responsetime);
        }
        cursor.close();
        db.close();

        return list;

    }

    //联合查询组合名称
    public List<String> ListGroupName(String playername, String starttime,
                                      String endtime, String trainingMode) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql1 = "select distinct group1.groupname from result,group1,player where player=? and result.player=player.name and group1.trainingmode=? and result.time between ? and ?";
        String sql = "select distinct trainingname from result where player=? and trainingmode=? and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{playername, trainingMode, starttime,
                endtime});
        while (cursor.moveToNext()) {

            String groupname = cursor.getString(cursor
                    .getColumnIndex("trainingname"));
            if (groupname == null) {
                groupname = "";
            }
            list.add(groupname);
        }
        cursor.close();
        db.close();

        return list;

    }

    // 通过运动员查询运程序名称
    public List<String> ListModeByPlayer(String playername, String starttime,
                                         String endtime) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select distinct trainingmode from result where player=? and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{playername, starttime,
                endtime});
        while (cursor.moveToNext()) {

            String trainmode = cursor.getString(cursor
                    .getColumnIndex("trainingmode"));
            if (trainmode == null) {
                trainmode = "";
            }
            list.add(trainmode);
        }
        cursor.close();
        db.close();

        return list;

    }

    // 通过运动员查询组名
    public List<String> ListGroupByPlayer(String playername) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select groupname from result where playername=?";
        Cursor cursor = db.rawQuery(sql, new String[]{playername});
        while (cursor.moveToNext()) {

            String groupname = cursor.getString(cursor
                    .getColumnIndex("groupname"));
            if (groupname == null) {
                groupname = "";
            }
            list.add(groupname);
        }
        cursor.close();
        db.close();
        return list;

    }

    public List<Map<String, String>> ListResultByName(String trainingname) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from result where trainingname = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{trainingname});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    //通过运动员,模式，组合名称查询成绩
    public List<Map<String, String>> ListResultByNameModeGroup(String name, String mode, String groupname) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from result where player=? and trainingmode=? and trainingname=?";
        Cursor cursor = db.rawQuery(sql, new String[]{name, mode, groupname});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;
    }

    public List<Map<String, String>> ListResultByNameTimeModeGroup(String name, String mode, String groupname, String starttime, String endtime) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from result where player=? and trainingmode=? and trainingname=? and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{name, mode, groupname, starttime, endtime});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;
    }

    // ////////
    public List<Map<String, String>> ListResultByNameTime(String trainingname,
                                                          String starttime, String endtime) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from result where trainingname = ? and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{trainingname,
                starttime, endtime});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    public List<Map<String, String>> ListResultByNameModeTime(String name, String mode, String starttime, String endtime) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from result where player=? and trainingmode=? and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{name, mode, starttime, endtime});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;
    }

    public List<Map<String, String>> ListResultByMode(String mode) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from result where trainingmode = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{mode});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    public List<Map<String, String>> ListResultByModeTime(String mode,
                                                          String starttime, String endtime) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from result where trainingmode = ? and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{mode, starttime,
                endtime});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    public List<Map<String, String>> ListResultByName2Time(String trainingname,
                                                           String player, String starttime, String endtime) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from result where trainingname = ? and player=? and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{trainingname, player,
                starttime, endtime});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    public List<Map<String, String>> ListResultByPlayerTime(String player,
                                                            String starttime, String endtime) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from result where  player=? and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{player, starttime,
                endtime});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    public List<Map<String, String>> ListResultByName2(String trainingname,
                                                       String player) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from result where trainingname = ? and player=?";
        Cursor cursor = db.rawQuery(sql, new String[]{trainingname, player});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    public List<Map<String, String>> ListResultByPlayer(String player) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from result where  player=?";
        Cursor cursor = db.rawQuery(sql, new String[]{player});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    // 检查运动员是否有成绩，用于清空运动员成绩的操作
    public boolean ResultExist(String name) {
        // name="运动员1";
        boolean flag = false;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select name_player from result where name_player=?";
        Cursor cursor = db.rawQuery(sql, new String[]{name});
        while (cursor.moveToNext()) {
            String coachname = cursor.getString(cursor
                    .getColumnIndex("name_player"));
            if (coachname == name) {
                flag = false;// 存在成绩，能删除
            } else {
                flag = true;
            }
        }
        cursor.close();
        db.close();
        return flag;
    }

    // 通过运动员,时间段，程序名查询组名
    public List<String> ListgroupByTPM(String playername, String trainingmode,
                                       String starttime, String endtime) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select distinct trainingname from result where player=? and trainingmode =? and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{playername,
                trainingmode, starttime, endtime});
        while (cursor.moveToNext()) {

            String trainmode = cursor.getString(cursor
                    .getColumnIndex("trainingname"));
            if (trainmode == null) {
                trainmode = "";
            } else {
                list.add(trainmode);
            }

        }
        cursor.close();
        db.close();

        return list;

    }

    // 通过运动员名称和程序名称，时间段查询反应时间
    public List<String> ListResponseByPMT(String player, String trainingmode,
                                          String starttime, String endtime) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select responsetime from result where player=? and trainingmode= ? and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{player, trainingmode,
                starttime, endtime});
        while (cursor.moveToNext()) {

            String responsetime = cursor.getString(cursor
                    .getColumnIndex("responsetime"));
            if (responsetime == null) {
                responsetime = "";
            }
            list.add(String.valueOf(((int) (Double.valueOf(responsetime) * 1000))));
        }
        cursor.close();
        db.close();

        return list;

    }

    // 通过运动员名称和程序名称，时间段，组名查询反应时间
    public List<String> ListResponseByPMTG(String player, String trainingmode,
                                           String trainingname, String starttime, String endtime) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select responsetime from result where player=? and trainingmode= ? and trainingname=?and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{player, trainingmode,
                trainingname, starttime, endtime});
        while (cursor.moveToNext()) {

            String responsetime = cursor.getString(cursor
                    .getColumnIndex("responsetime"));
            if (responsetime == null) {
                responsetime = "";
            }
            list.add(String.valueOf(((int) (Double.valueOf(responsetime) * 1000))));
        }
        cursor.close();
        db.close();

        return list;

    }

    // 通过运动员名称和程序名称，时间段，组名查询反应时间
    public List<String> ListPosTimeByPMTG(String player, String trainingmode,
                                          String trainingname, String starttime, String endtime) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select groupresult from result where player=? and trainingmode= ? and trainingname=?and time between ? and ?";
        Cursor cursor = db.rawQuery(sql, new String[]{player, trainingmode,
                trainingname, starttime, endtime});
        while (cursor.moveToNext()) {

            String groupresult = cursor.getString(cursor
                    .getColumnIndex("groupresult"));
            if (groupresult == null) {
                groupresult = "";
            }
            list.add(groupresult.toString().trim());
        }
        cursor.close();
        db.close();

        return list;

    }

    //多条件查询成绩
    public List<Map<String, String>> searchResultByConditions(String name, String starttime, String endtime,
                                                              String trainingMode, String trainingName) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from result where 1=1";
        if (!"".equals(name)) {
            sql = sql + "and name=?";
        }
        if (!"".equals(trainingMode)) {
            sql = sql + "and trainingmode=?";
        }
        if (!"".equals(trainingName)) {
            sql = sql + "and trainingname=?";
        }
        if (!"".equals(starttime) && !"".equals(endtime)) {
            sql = sql + "and time between ? and ?";
        }
        Cursor cursor = db.rawQuery(sql, new String[]{name, trainingMode, trainingName, starttime, endtime});
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();

            for (int i = 0; i < colums; i++) {
                String col_name = cursor.getColumnName(i);
                String col_value = cursor.getString(cursor
                        .getColumnIndex(col_name));
                if (col_value == "null") {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;
    }

    //删除所有成绩
    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from result";
        db.execSQL(sql);
        db.close();
    }
}
