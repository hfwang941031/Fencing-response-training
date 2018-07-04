package ouc.b304.com.fenceplaying.DB.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ouc.b304.com.fenceplaying.Bean.Sequence;
import ouc.b304.com.fenceplaying.DB.SQLOpenHelper;

public class SequenceDao {

    private SQLOpenHelper helper;

    public SequenceDao(Context context) {
        // TODO Auto-generated constructor stub
        helper = new SQLOpenHelper(context);
    }

    // 插入数据
    public void addSequence(Sequence sequence) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into sequence (name,sequence,time,times,setter,remark)values(?,?,?,?,?,?)";
        db.execSQL(
                sql,
                new Object[] { sequence.getName(), sequence.getSequence(),
                        sequence.getTime(), sequence.getTimes(),
                        sequence.getSetter(), sequence.getRemark() });
        db.close();

    }

    public List<Map<String, String>> ListSequence() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from sequence";
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
    //通过id来查询
    public List<Map<String, String>> ListSequenceById(String id) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from sequence where _id = "+id+"";
        Cursor cursor = db.rawQuery(sql,null );
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
    public void deleteSequenceById(String id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from sequence where _id=?";
        db.execSQL(sql, new Object[] { id });
        db.close();

    }
    //通过id来修改sequence
    public void updateSequenceByPlayer(Sequence sequence){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql ="update sequence set name=? , sequence = ?, time = ?, times = ?, setter = ?, remark = ? where _id =?";
        db.execSQL(sql, new Object[]{sequence.getName(), sequence.getSequence(),
                sequence.getTime(), sequence.getTimes(),
                sequence.getSetter(), sequence.getRemark(),sequence.getId() } );
        db.close();


    }

    public List<Map<String, String>> SelectSearch(String name) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from sequence where name like '%"+name+"%' or setter like '%"+name+"%'";
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
