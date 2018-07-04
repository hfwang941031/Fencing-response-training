package ouc.b304.com.fenceplaying.Bean;

public class Sequence {
    private String id;
    private String name ;
    private String sequence;
    private String time;
    private String times;
    private String setter;
    private String remark;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSequence() {
        return sequence;
    }
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getTimes() {
        return times;
    }
    public void setTimes(String times) {
        this.times = times;
    }
    public String getSetter() {
        return setter;
    }
    public void setSetter(String setter) {
        this.setter = setter;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Override
    public String toString() {
        return "Sequence [id=" + id + ", name=" + name + ", sequence=" + sequence
                + ", time=" + time + ", times=" + times + ", setter=" + setter
                + ", remark=" + remark + "]";
    }
    public Sequence(String id, String name, String sequence, String time,
                    String times, String setter, String remark) {
        super();
        this.id = id;
        this.name = name;
        this.sequence = sequence;
        this.time = time;
        this.times = times;
        this.setter = setter;
        this.remark = remark;
    }
    public Sequence() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Sequence(String name, String sequence, String time, String times,
                    String setter, String remark) {
        super();
        this.name = name;
        this.sequence = sequence;
        this.time = time;
        this.times = times;
        this.setter = setter;
        this.remark = remark;
    }


}
