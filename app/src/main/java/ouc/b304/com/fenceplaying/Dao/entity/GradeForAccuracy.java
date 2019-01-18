package ouc.b304.com.fenceplaying.Dao.entity;

import java.io.Serializable;

public class GradeForAccuracy implements Serializable {
 //运动员姓名
    private String name;
    //运动员id
    private Long id;
    //训练时长（min）
    private int trainingTime;
    //命中正确光标次数
    private int hitRightNum;
    //命中错误光标次数
    private int hitWrongNum;
    //命中率
    private float hitRate;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTrainingTime(int trainingTime) {
        this.trainingTime = trainingTime;
    }

    public void setHitRightNum(int hitRightNum) {
        this.hitRightNum = hitRightNum;
    }

    public void setHitWrongNum(int hitWrongNum) {
        this.hitWrongNum = hitWrongNum;
    }

    public void setHitRate(float hitRate) {
        this.hitRate = hitRate;
    }

    public String getName() {

        return name;
    }

    public Long getId() {
        return id;
    }

    public int getTrainingTime() {
        return trainingTime;
    }

    public int getHitRightNum() {
        return hitRightNum;
    }

    public int getHitWrongNum() {
        return hitWrongNum;
    }

    public float getHitRate() {
        return hitRate;
    }

}
