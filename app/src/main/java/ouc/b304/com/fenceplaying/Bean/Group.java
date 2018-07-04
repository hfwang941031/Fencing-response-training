package ouc.b304.com.fenceplaying.Bean;

public class Group {

    private int id;
    private String trainingmode;
    private String groupname;
    private String unitno ;
    private String timesno;
    private String groupstr;
    private String time;
    private String Isdistance;
    private String distancestr;
    private String Ispai;
    private String paidistance ;
    private String remark ;
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
    public String getGroupname() {
        return groupname;
    }
    public void setGroupname(String groupname) {
        this.groupname = groupname;
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
    public String getGroupstr() {
        return groupstr;
    }
    public void setGroupstr(String groupstr) {
        this.groupstr = groupstr;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getIsdistance() {
        return Isdistance;
    }
    public void setIsdistance(String isdistance) {
        Isdistance = isdistance;
    }
    public String getDistancestr() {
        return distancestr;
    }
    public void setDistancestr(String distancestr) {
        this.distancestr = distancestr;
    }
    public String getIspai() {
        return Ispai;
    }
    public void setIspai(String ispai) {
        Ispai = ispai;
    }
    public String getPaidistance() {
        return paidistance;
    }
    public void setPaidistance(String paidistance) {
        this.paidistance = paidistance;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Override
    public String toString() {
        return "Group [id=" + id + ", trainingmode=" + trainingmode
                + ", groupname=" + groupname + ", unitno=" + unitno + ", timesno="
                + timesno + ", groupstr=" + groupstr + ", time=" + time
                + ", Isdistance=" + Isdistance + ", distancestr=" + distancestr
                + ", Ispai=" + Ispai + ", paidistance=" + paidistance + ", remark="
                + remark + "]";
    }
    public Group(int id, String trainingmode, String groupname, String unitno,
                 String timesno, String groupstr, String time, String isdistance,
                 String distancestr, String ispai, String paidistance, String remark) {
        super();
        this.id = id;
        this.trainingmode = trainingmode;
        this.groupname = groupname;
        this.unitno = unitno;
        this.timesno = timesno;
        this.groupstr = groupstr;
        this.time = time;
        Isdistance = isdistance;
        this.distancestr = distancestr;
        Ispai = ispai;
        this.paidistance = paidistance;
        this.remark = remark;
    }
    public Group(String trainingmode, String groupname, String unitno,
                 String timesno, String groupstr, String time, String isdistance,
                 String distancestr, String ispai, String paidistance, String remark) {
        super();
        this.trainingmode = trainingmode;
        this.groupname = groupname;
        this.unitno = unitno;
        this.timesno = timesno;
        this.groupstr = groupstr;
        this.time = time;
        Isdistance = isdistance;
        this.distancestr = distancestr;
        Ispai = ispai;
        this.paidistance = paidistance;
        this.remark = remark;
    }
    public Group() {
        super();
        // TODO Auto-generated constructor stub
    }



}
