package ouc.b304.com.fenceplaying.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLOpenHelper extends SQLiteOpenHelper {
    public SQLOpenHelper(Context context) {
        super(context, "response.db", null, 10);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //String sql = "create table coach(_id integer primary key autoincrement,name varchar(64))";
        String sql1 = "create table result(id integer primary key autoincrement,trainingmode,trainingname,groupstr,unitno,timesno,groupresult,unitresult,timesresult,responsetime,grouptime,distance,time,player)";
        db.execSQL(sql1);
        String sql2 = "create table player(_id integer primary key autoincrement,name, age, height, weight)";
        db.execSQL(sql2);

        String sql3 = "create table group1(_id integer primary key autoincrement, trainingmode,groupname,unitno,timesno,groupstr,time,Isdistance,distancestr,Ispai,paidistance,remark_group)";
        db.execSQL(sql3);

        String sql4 = "create table trainingmode(_id integer primary key autoincrement,trainingname varchar(64))";
        db.execSQL(sql4);

        String sql5 = "create table unit1(_id integer primary key autoincrement,name integer,unitstr,times varchar(64),time varchar(64),remark_unit varchar(64))";
        db.execSQL(sql5);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub


        db.execSQL("alter table player add groupname varchar(64)");
        db.execSQL("alter table player add sex varchar(64)");
        db.execSQL("alter table player add playMode varchar(64)");

    }
}
