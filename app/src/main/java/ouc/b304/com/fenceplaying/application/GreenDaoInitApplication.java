package ouc.b304.com.fenceplaying.application;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ouc.b304.com.fenceplaying.Dao.DaoMaster;
import ouc.b304.com.fenceplaying.Dao.DaoSession;
import ouc.b304.com.fenceplaying.usbUtils.UsbConfig;
import ouc.b304.com.fenceplaying.utils.newUtils.Utils;


public class GreenDaoInitApplication extends Application {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    //静态单例
    public static GreenDaoInitApplication instances;
    public List<Activity> mActivityList = new LinkedList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                //文件名
                .name("SafLightSample.realm")
                //版本号
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        UsbConfig.getInstance().init(this);
        //////////////////////////////////////
        instances = this;
        setDatabase();
    }
    public static GreenDaoInitApplication getInstances(){

        return instances;

    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "player.db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }
    /**
     * 添加Activity到容器中
     */
    public void addActivityOrder(Activity activity) {
        mActivityList.add(activity);
    }

    /**
     * 遍历所有的activity并finish
     */
    public void exitOrder() {
        for (Activity activity : mActivityList) {
            activity.finish();
        }
        mActivityList.clear();
    }

    /**
     * 关闭最后一个页面
     */
    public void finish() {
        mActivityList.get(mActivityList.size() - 1).finish();
        mActivityList.remove(mActivityList.size() - 1);
    }
}
