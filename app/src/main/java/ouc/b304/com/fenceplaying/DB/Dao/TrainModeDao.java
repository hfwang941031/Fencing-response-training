package ouc.b304.com.fenceplaying.DB.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ouc.b304.com.fenceplaying.DB.SQLOpenHelper;


public class TrainModeDao {

    private SQLOpenHelper helper;

    public TrainModeDao(Context context) {
        // TODO Auto-generated constructor stub
        helper = new SQLOpenHelper(context);
    }

    //插入程序名称
    public void addTrainMode(String name) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into trainingmode(trainingname)values(?)", new Object[] { name });
        db.close();
    }
    // 通过姓名来删除某一条数据
    public void deleteTrainModeByName(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from trainingmode where trainingname=?";
        db.execSQL(sql, new Object[] { name });
        db.close();
    }
    //查询所有的程序名称
    public List<String> selectAllTrainMode() {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select trainingname from trainingmode", null);

        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex("trainingname"));
            if (id == null) {
                id = "";
            }
            list.add(id);
        }

        cursor.close();
        db.close();
        return list;

    }
    //查询名称，检测插入的数据是否相同
    public boolean trainModeExist(String name){
        boolean flag = false;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql="select trainingname from trainingmode where trainingname=?";
        Cursor cursor = db.rawQuery(sql, new String[]{name});
        while(cursor.moveToNext()){
            String trainingname =cursor.getString(cursor.getColumnIndex("trainingname"));
            if(trainingname==name){
                flag=false;
            }else {
                flag=true;
            }
        }
        return flag;
    }
    // 通过姓名来删除某一条数据
    public void delete() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from trainingmode ";
        db.execSQL(sql, new Object[] {  });
        db.close();
    }
}
