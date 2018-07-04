package ouc.b304.com.fenceplaying.DB.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ouc.b304.com.fenceplaying.Bean.Unit;
import ouc.b304.com.fenceplaying.DB.SQLOpenHelper;

public class UnitDao {

    private SQLOpenHelper helper;

    public UnitDao(Context context) {
        // TODO Auto-generated constructor stub
        helper = new SQLOpenHelper(context);
    }

    // 插入数据
    public void addUnit(Unit unit) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into unit1(name,unitstr,times,time,remark_unit)values(?,?,?,?,?)";
        db.execSQL(
                sql,
                new Object[] { unit.getName(), unit.getUnitstr(),
                        unit.getTimes(), unit.getTime(), unit.getRemark()+" " });
        db.close();

    }

    public List<Map<String, String>> ListUnit() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from unit1";
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
                if (col_name.equals("name")) {
                    col_value="单元"+col_value;
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
    public List<Map<String, String>> ListSequenceById(String id) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from sequence where _id = " + id + "";
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
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }

    // 通过id来删除某一条数据
    public void deleteUnitById(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from unit1 where _id=?";
        db.execSQL(sql, new Object[] { id });
        db.close();

    }

    // 通过id来修改sequence
    public int GetUnitMaxID() {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from unit1",
                new String[] {});
        int times = 0;
        if (cursor.moveToFirst()) {
            times = cursor.getCount();
        }
        db.close();
        return times;

    }
    //修改单元名称，将
    public void SetUnitName(int id){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update unit1 set name=name-1 where _id>?";
        db.execSQL(
                sql,
                new Object[] { id});
        db.close();


    }
    // 通过id来修改sequence
    public void updateUnitByID(Unit unit) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update unit1 set name=? , unitstr=? , times=? , time=? , remark_unit=? where _id =?";
        db.execSQL(
                sql,
                new Object[] { unit.getName(), unit.getUnitstr(),
                        unit.getTimes(), unit.getTime(), unit.getRemark()+" ",
                        unit.getId() });
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
                map.put(col_name, col_value);
            }

            list.add(map);
        }
        cursor.close();
        db.close();

        return list;

    }
}
