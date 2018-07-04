package ouc.b304.com.fenceplaying.DB.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ouc.b304.com.fenceplaying.Bean.Player;
import ouc.b304.com.fenceplaying.DB.SQLOpenHelper;

public class PlayerDao {

    private SQLOpenHelper helper;

    public PlayerDao(Context context) {
        // TODO Auto-generated constructor stub
        helper = new SQLOpenHelper(context);
    }


    // 插入数据
    public void addPlayerName(String name) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into player(name)values(?)", new Object[] {
                name });

        db.close();
    }
    //插入全部数据
    public void addPlayer(Player player) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into player(name,age,height,weight,groupname,sex,playMode)values(?,?,?,?,?,?,?)", new Object[] {
                player.getName(),player.getAge(),player.getHeight(),player.getWeight(),player.getGroupname(),player.getSex(),player.getPlayMode() });

        db.close();
    }
    //通过id来删除某一条数据
    public void deleteById(String id){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql ="delete from player where _id=?";
        db.execSQL(sql, new Object[]{id} );
        db.close();

    }
    public void updatePlayerByID(Player player){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql ="update player set name=?, age=?, height=?, weight=?, groupname=?, sex=?, playMode=?  where _id =?";
        db.execSQL(sql, new Object[]{player.getName(),player.getAge(),player.getHeight(),player.getWeight(),player.getGroupname()
                ,player.getSex(),player.getPlayMode(),player.getId()} );
        db.close();


    }

    //只单纯的选出运动员的姓名
    public List<String> selectPlayerName() {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select name from player", null);

        while (cursor.moveToNext()) {

            String id =cursor.getString(cursor.getColumnIndex("name"));
            if (id == null) {
                id = "";
            }
            list.add(id);
        }

        cursor.close();
        db.close();
        return list;

    }



    //删除所有运动员信息
    public void deleteALLPlayer() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from player");
        db.close();

    }

    //通过运动员姓名删除该运动员的所有信息，包括所对应的教练
    public void deleteAllByPlayer(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql ="delete from player where name=?";
        db.execSQL(sql, new Object[]{name} );
        db.close();


    }

    //通过运动员姓名来修改教练员
    public void updateCoachByPlayer(String name_coach,String name_player){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql ="update player set coach=? where name =?";
        db.execSQL(sql, new Object[]{name_coach,name_player} );
        db.close();


    }







    //选出运动员的所有信息  即姓名和所对应的的教练
    public List<Map<String, String>> listPlayereMap() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from player";
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

    //通过姓名选出运动员的所有信息
    public List<Map<String, String>> listPlayerByName(String name) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from player where name=?";
        Cursor cursor = db.rawQuery(sql, new String[]{name});
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

    //模糊查询
    public List<Map<String, String>> listPlayerByWord(String word) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from player where name like ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + word + "%"});
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

    //通过教练员来获取相应的运动员姓名
    public List<Map<String, String>> listPlayereByCoach(String coach) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select name from player where coach =?";
        Cursor cursor = db.rawQuery(sql, new String[]{coach});

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

    //查询运动员姓名，检测插入的数据是否相同
    public boolean PlayerNameExist(String name){
        boolean flag = false;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql="select name from player where name=?";
        Cursor cursor = db.rawQuery(sql, new String[]{name});
        while(cursor.moveToNext()){
            String playername =cursor.getString(cursor.getColumnIndex("name"));
            if(playername==name){
                flag=false;
            }else {
                flag=true;
            }
        }
        return flag;
    }
    //查询是否存在某教练员姓名
    public boolean CoachNameExist(String name){
        boolean flag = false;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql="select coach from player where coach=?";
        Cursor cursor = db.rawQuery(sql, new String[]{name});
        while(cursor.moveToNext()){
            String coachname =cursor.getString(cursor.getColumnIndex("coach"));
            if(coachname==name){
                flag=false;
            }else {
                flag=true;
            }
        }
        return flag;
    }

}
