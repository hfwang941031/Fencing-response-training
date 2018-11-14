package ouc.b304.com.fenceplaying.Bean;

import android.os.Environment;

import java.util.ArrayList;

public class Constant {
    public static String[] PLAYER_AGE = { "0岁","15岁", "16岁", "17岁", "18岁", "19岁",
            "20岁", "21岁", "22岁", "23岁", "24岁", "25岁", "26岁", "27岁", "28岁",
            "29岁", "30岁", "31岁", "32岁", "33岁", "34岁", "35岁", "36岁", "37岁",
            "38岁", "39岁", "40岁", "41岁", "42岁", "43岁", "44岁", "45岁", "46岁",
            "47岁", "48岁", "49岁", "50岁" };
    public static String[] PLAYER_HEIGHT = { "0cm","150cm", "151cm", "152cm",
            "153cm", "154cm", "155cm", "156cm", "157cm", "158cm", "159cm",
            "160cm", "161cm", "162cm", "163cm", "164cm", "165cm", "166cm",
            "167cm", "168cm", "169cm", "170cm", "171cm", "172cm", "173cm", "174cm",
            "175cm", "176cm", "177cm", "178cm", "179cm", "180cm", "181cm", "182cm", "183cm",
            "184cm", "185cm", "186cm", "187cm", "188cm", "189cm", "190cm", "191cm", "192cm",
            "193cm", "194cm", "195cm", "196cm", "197cm", "198cm", "199cm", "200cm", "201cm",
            "202cm", "203cm", "204cm", "205cm", "206cm", "207cm", "208cm", "209cm", "210cm" };
    public static String[] PLAYER_WEIGHT = { "0kg","45kg","46kg","47kg","48kg","49kg","50kg","51kg","52kg","53kg","54kg","55kg","56kg","57kg","58kg","59kg","60kg","61kg","62kg","63kg","64kg","65kg","66kg","67kg","68kg","69kg","70kg","71kg","72kg","73kg","74kg","75kg","76kg","77kg","78kg","79kg","80kg","81kg","82kg","83kg","84kg","85kg","86kg","87kg","88kg","89kg" ,"90kg","91kg","92kg","93kg","94kg","95kg","96kg","97kg","98kg","99kg","100kg","101kg","102kg","103kg","104kg","105kg","106kg","107kg","108kg","109kg","110kg","111kg","112kg","113kg","114kg","115kg","116kg","117kg","118kg","119kg"};

    public static String[] unitnostr = { "0次", "1次", "2次", "3次", "4次", "5次", "6次",
            "7次", "8次", "9次", "10次", "11次", "12次", "13次", "14次", "15次",
            "16次", "17次", "18次", "19次", "20次", "21次", "22次", "23次", "24次", "25次", "26次",
            "27次", "28次", "29次", "30次", "31次", "32次", "33次", "34次", "35次",
            "36次", "37次", "38次", "39次", "40次", "41次", "42次", "43次", "44次",
            "45次", "46次", "47次", "48次", "49次", "50次", "51次", "52次", "53次",
            "54次", "55次", "56次", "57次", "58次", "59次", "60次"  };

    public static String[] action_model={"感应","触碰","同时"};
    public static String[] light_model={"外圈","里圈","全部"};
    public static String[] color_model={"蓝色","红色","绿色"};
    public static String[] sound_model={"不响","开灯时响","关灯时响","开关都响"};
    public static String[] trainingTimes={
        "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60"};




    //服务器域名,IP
    //public final static String SERVER_IP = "http://www.saflight.com.cn";
    public final static String SERVER_IP = "http://www.saflight.com.cn/saflight";

    public final static String LOG_TAG = "TRAINING_DEBUG";

    //app文件存储路径
    public final static String APP_STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/Saflight/";
    //下载路径
    public final static String DOWNLOAD_PATH = APP_STORAGE_PATH + "/Download/";
    // 用于判断进入主界面时是否需要更新电量数据
    public static boolean NeedUpdatePower = false;

    public static boolean GetNeedUpdate() {
        return NeedUpdatePower;
    }
    public static void SetNeedUpdate(boolean update) {
        NeedUpdatePower = update;
    }
    public static int max=0;
    public static int min=0;
    public static int size=0;


    public static char[] first = new char[]{'A', 'B', 'E', 'F'};
    public static char[] second = new char[]{'C', 'D', 'G', 'H'};
    public static char[] third = new char[]{'B', 'C', 'F', 'G'};
    public static char[] forth = new char[]{'E', 'F', 'I', 'J'};
    public static char[] fifth = new char[]{'F', 'G', 'J', 'K'};
    public static char[] sixth = new char[]{'G', 'H', 'K', 'L'};
    public static char[] seventh = new char[]{'I', 'J', 'M', 'N'};
    public static char[] eighth = new char[]{'J', 'K', 'N', 'O'};
    public static char[] nineth = new char[]{'K', 'L', 'O', 'P'};

}
