package ouc.b304.com.fenceplaying.Bean;

public class Result {
    private int id;
    private String trainingmode;//本次测试的训练模式
    private String trainingname;//
    private String groupstr;
    private String unitno;
    private String timesno;
    private String groupresult;
    private String unitresult;
    private String timesresult;
    private String responsetime;
    private String grouptime;
    private String distance;
    private String time;//本次测试的时间
    private String player;//本次测试的受体
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTrainingmode() {
        return trainingmode;
    }
    public void setTrainingmode(String trainingmode) {
        this.trainingmode = trainingmode;
    }
    public String getTrainingname() {
        return trainingname;
    }
    public void setTrainingname(String trainingname) {
        this.trainingname = trainingname;
    }
    public String getGroupstr() {
        return groupstr;
    }
    public void setGroupstr(String groupstr) {
        this.groupstr = groupstr;
    }
    public String getUnitno() {
        return unitno;
    }
    public void setUnitno(String unitno) {
        this.unitno = unitno;
    }
    public String getTimesno() {
        return timesno;
    }
    public void setTimesno(String timesno) {
        this.timesno = timesno;
    }
    public String getGroupresult() {
        return groupresult;
    }
    public void setGroupresult(String groupresult) {
        this.groupresult = groupresult;
    }
    public String getUnitresult() {
        return unitresult;
    }
    public void setUnitresult(String unitresult) {
        this.unitresult = unitresult;
    }
    public String getTimesresult() {
        return timesresult;
    }
    public void setTimesresult(String timesresult) {
        this.timesresult = timesresult;
    }
    public String getResponsetime() {
        return responsetime;
    }
    public void setResponsetime(String responsetime) {
        this.responsetime = responsetime;
    }
    public String getGrouptime() {
        return grouptime;
    }
    public void setGrouptime(String grouptime) {
        this.grouptime = grouptime;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getPlayer() {
        return player;
    }
    public void setPlayer(String player) {
        this.player = player;
    }
    @Override
    public String toString() {
        return "Result [id=" + id + ", trainingmode=" + trainingmode
                + ", trainingname=" + trainingname + ", groupstr=" + groupstr
                + ", unitno=" + unitno + ", timesno=" + timesno
                + ", groupresult=" + groupresult + ", unitresult=" + unitresult
                + ", timesresult=" + timesresult + ", responsetime="
                + responsetime + ", grouptime=" + grouptime + ", distance="
                + distance + ", time=" + time + ", player=" + player + "]";
    }
    public Result(int id, String trainingmode, String trainingname,
                  String groupstr, String unitno, String timesno, String groupresult,
                  String unitresult, String timesresult, String responsetime,
                  String grouptime, String distance, String time, String player) {
        super();
        this.id = id;
        this.trainingmode = trainingmode;
        this.trainingname = trainingname;
        this.groupstr = groupstr;
        this.unitno = unitno;
        this.timesno = timesno;
        this.groupresult = groupresult;
        this.unitresult = unitresult;
        this.timesresult = timesresult;
        this.responsetime = responsetime;
        this.grouptime = grouptime;
        this.distance = distance;
        this.time = time;
        this.player = player;
    }
    public Result(String trainingmode, String trainingname, String groupstr,
                  String unitno, String timesno, String groupresult,
                  String unitresult, String timesresult, String responsetime,
                  String grouptime, String distance, String time, String player) {
        super();
        this.trainingmode = trainingmode;
        this.trainingname = trainingname;
        this.groupstr = groupstr;
        this.unitno = unitno;
        this.timesno = timesno;
        this.groupresult = groupresult;
        this.unitresult = unitresult;
        this.timesresult = timesresult;
        this.responsetime = responsetime;
        this.grouptime = grouptime;
        this.distance = distance;
        this.time = time;
        this.player = player;
    }
    public Result() {
        super();
        // TODO Auto-generated constructor stub
    }
}
