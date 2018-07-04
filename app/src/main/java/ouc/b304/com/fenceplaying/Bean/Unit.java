package ouc.b304.com.fenceplaying.Bean;

public class Unit {
    private int id;
    private String name ;
    private String unitstr;
    private String times;
    private String time;
    private String remark ;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUnitstr() {
        return unitstr;
    }
    public void setUnitstr(String unitstr) {
        this.unitstr = unitstr;
    }
    public String getTimes() {
        return times;
    }
    public void setTimes(String times) {
        this.times = times;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Override
    public String toString() {
        return "Unit [id=" + id + ", name=" + name + ", unitstr=" + unitstr
                + ", times=" + times + ", time=" + time + ", remark=" + remark
                + "]";
    }
    public Unit(int id, String name, String unitstr, String times, String time,
                String remark) {
        super();
        this.id = id;
        this.name = name;
        this.unitstr = unitstr;
        this.times = times;
        this.time = time;
        this.remark = remark;
    }
    public Unit(String name, String unitstr, String times, String time,
                String remark) {
        super();
        this.name = name;
        this.unitstr = unitstr;
        this.times = times;
        this.time = time;
        this.remark = remark;
    }
    public Unit() {
        super();
        // TODO Auto-generated constructor stub
    }


}
