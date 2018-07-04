package ouc.b304.com.fenceplaying.DB.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ouc.b304.com.fenceplaying.Bean.Group;
import ouc.b304.com.fenceplaying.DB.SQLOpenHelper;
import ouc.b304.com.fenceplaying.utils.StringUtils;

public class GroupDao {

    private SQLiteOpenHelper helper;

    public GroupDao(Context context) {
        // TODO Auto-generated constructor stub
        helper = new SQLOpenHelper(context);
    }

    // 通过组名来修改Group
    public void updateGroupByID(Group group, String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update group1 set trainingmode=? , unitno = ?, timesno = ?, groupstr = ?, time = ?,Isdistance =?,distancestr=?,Ispai=?,paidistance=?,remark_group=? where _id =?";
        db.execSQL(
                sql,
                new Object[] { group.getTrainingmode(),
                        group.getUnitno(), group.getTimesno(),
                        group.getGroupstr(), group.getTime(),
                        group.getIsdistance(), group.getDistancestr(),
                        group.getIspai(), group.getPaidistance(),
                        group.getRemark(), id });
        db.close();
    }

    // 通过id来查询
    public List<Map<String, String>> SelectByGroupname(String groupname) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from group1 where groupname =?";
        Cursor cursor = db.rawQuery(sql, new String[] { groupname });
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
                if (col_name.equals("groupname")) {
                    col_value = cursor.getString(cursor
                            .getColumnIndex("trainingmode")) + col_value;
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }
    public List<Map<String, String>> SelectByID(String groupname) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from group1 where _id =?";
        Cursor cursor = db.rawQuery(sql, new String[] { groupname });
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
                if (col_name.equals("groupname")) {
                    col_value = cursor.getString(cursor
                            .getColumnIndex("trainingmode")) + col_value;
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }
    // 通过id来查询
    public List<Map<String, String>> ListGroupByMode(String mode) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from group1 where trainingmode = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { mode });
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
				/*if (col_name.equals("groupname")) {
					col_value = cursor.getString(cursor
							.getColumnIndex("trainingmode")) + col_value;
				}*/
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    // 插入数据
    public void addGroup(Group group) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into group1(trainingmode,groupname,unitno,timesno,groupstr,time,Isdistance,distancestr,Ispai,paidistance,remark_group)values(?,?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(
                sql,
                new Object[] { group.getTrainingmode(), group.getGroupname(),
                        group.getUnitno(), group.getTimesno(),
                        group.getGroupstr(), group.getTime(),
                        group.getIsdistance(), group.getDistancestr(),
                        group.getIspai(), group.getPaidistance(),
                        group.getRemark() });
        db.close();

    }

    // 通过程序名查询所有组
    public List<Map<String, String>> SelectGroupsByModeName(String trainmodename) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from group1 where trainingmode =?";
        Cursor cursor = db.rawQuery(sql, new String[] { trainmodename });
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
			/*	if (col_name.equals("groupname")) {
					col_value = cursor.getString(cursor
							.getColumnIndex("trainingmode")) + col_value;
				}*/
                map.put(col_name, col_value);
            }
            list.add(map);
        }
        cursor.close();
        db.close();
        return list;
    }

    // 通过程序名查询是否包含组
    public boolean IsContainGroups(String trainmodename) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from group1 where trainingmode =?";
        Cursor cursor = db.rawQuery(sql, new String[] { trainmodename });
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
                if (col_name.equals("groupname")) {
                    col_value = cursor.getString(cursor
                            .getColumnIndex("trainingmode")) + col_value;
                }
                map.put(col_name, col_value);
            }
            list.add(map);
        }
        cursor.close();
        db.close();
        if (list.size() == 0) {
            return false;
        } else {
            return true;
        }

    }

    // 获取group的数量
    public int GetGroupCount(String trainingmode) {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from group1 where trainingmode = ?",
                new String[] { trainingmode });
        int times = 0;
        if (cursor.moveToFirst()) {
            times = cursor.getCount();
        }
        db.close();
        return times;

    }

    public int GetGroupMaxID(String trainingmode) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        int num = 0;
        Cursor cursor = db.rawQuery(
                "select groupname from group1 where trainingmode=?",
                new String[] { trainingmode.toString().trim() });

        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex("groupname"));
            if (id == null) {
                id = "";
            }
            list.add(id);
        }

        if (list.size() == 0) {
            num = 0;
        } else {
            for (int i = 0; i < list.size(); i++) {
                String groupname = list.get(i);
                int groupnum = 0;
                String regex = "\\d*";
                Pattern p = Pattern.compile(regex);

                Matcher m = p.matcher(groupname);
                while (m.find()) {
                    if (!"".equals(m.group()))
                        groupnum = Integer.parseInt(m.group());
                }
                if (groupnum > num) {
                    num = groupnum;
                }
            }
            num = num + 1;
        }

        cursor.close();
        db.close();
        return num;

    }
    public void SetGroupName(int id,String trainingmode){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update group1 set groupname=groupname-1 where _id>? and trainingmode=?";
        db.execSQL(
                sql,
                new Object[] { id,trainingmode});
        db.close();


    }
    // 通过组名查询属于这个组的所有单元
    public String SelectUnitsByID(String groupname) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select groupstr from group1 where _id=?";
        Cursor cursor = db.rawQuery(sql, new String[] { groupname.toString()
                .trim() });
        String id = null;
        if (cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndex("groupstr"));
        }
        cursor.close();
        db.close();
        return id;
    }
    // 通过组名查询属于这个组的所有单元
    public String SelectUnitsByGroupName(String groupname) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select groupstr from group1 where groupname=?";
        Cursor cursor = db.rawQuery(sql, new String[] { groupname.toString()
                .trim() });
        String id = null;
        if (cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndex("groupstr"));
        }
        cursor.close();
        db.close();
        return id;
    }

    // 检测插入的数据是否存在
    public boolean IsGroupNameExist(String groupName) {
        boolean flag = false;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select groupname from group1 where groupname=?";
        Cursor cursor = db.rawQuery(sql, new String[] { groupName });
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("groupname"));
            if (name == groupName) {
                flag = false;
            } else {
                flag = true;
            }
        }
        return flag;
    }

    // 通过groupname来删除某一条数据
    public void deleteGroupByname(String groupname) {
        SQLiteDatabase db = helper.getWritableDatabase();
        System.out.print(groupname);
        String sql = "delete from group1 where groupname=?";
        db.execSQL(sql, new String[] { groupname });
        db.close();
    }
    public void deleteGroupID(String groupname) {
        SQLiteDatabase db = helper.getWritableDatabase();
        System.out.print(groupname);
        String sql = "delete from group1 where _id=?";
        db.execSQL(sql, new String[] { groupname });
        db.close();
    }
    public void deleteGroupByID(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "delete from group1 where _id=?";
        db.execSQL(sql, new String[] { id });
        db.close();
    }

    // 通过程序名查询所有组
    public List<Map<String, String>> SelectAllGroups() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from group1";
        Cursor cursor = db.rawQuery(sql, null);
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
                if (col_name.equals("groupname")) {
                    col_value = cursor.getString(cursor
                            .getColumnIndex("trainingmode")) + col_value;
                }
                map.put(col_name, col_value);
            }
            list.add(map);
        }
        cursor.close();
        db.close();
        return list;
    }

    // 通过组名来修改Group
    public void updateGroupByname2(String unitno, String timesno,
                                   String groupstr, String groupname) throws Exception{
        String groupName = StringUtils.SplitString(groupname);
        System.out.println("groupstr=="+groupstr+"groupName"+groupName);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update group1 set unitno = ?, timesno = ?, groupstr = ? where groupname =?";
        db.execSQL(sql, new Object[] { unitno, timesno, groupstr, groupName });
        db.close();
    }

    public List<Map<String, String>> SelectSearch(String name) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from sequence where name like '%" + name
                + "%' or setter like '%" + name + "%'";
        Cursor cursor = db.rawQuery(sql, null);
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
                if (col_name.equals("groupname")) {
                    col_value = cursor.getString(cursor
                            .getColumnIndex("trainingmode")) + col_value;
                }
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    public void delete() {
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "delete from group1 ";
        db.execSQL(sql, new String[] {});
        db.close();
    }
}
