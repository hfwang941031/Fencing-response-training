package ouc.b304.com.fenceplaying.Dao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * @author 王海峰 on 2019/1/18 19:21
 */
@Entity
public class AccuracyScores {
    @org.greenrobot.greendao.annotation.Id
    private Long Id;
    //训练时长（min）
    private int trainingTime;
    //命中正确光标次数
    private int hitRightNum;
    //命中错误光标次数
    private int hitWrongNum;
    //命中率
    private float hitRate;
    
    private Date date;

    private long playerId;

    @Generated(hash = 451374632)
    public AccuracyScores(Long Id, int trainingTime, int hitRightNum,
            int hitWrongNum, float hitRate, Date date, long playerId) {
        this.Id = Id;
        this.trainingTime = trainingTime;
        this.hitRightNum = hitRightNum;
        this.hitWrongNum = hitWrongNum;
        this.hitRate = hitRate;
        this.date = date;
        this.playerId = playerId;
    }

    @Generated(hash = 1474253140)
    public AccuracyScores() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public int getTrainingTime() {
        return this.trainingTime;
    }

    public void setTrainingTime(int trainingTime) {
        this.trainingTime = trainingTime;
    }

    public int getHitRightNum() {
        return this.hitRightNum;
    }

    public void setHitRightNum(int hitRightNum) {
        this.hitRightNum = hitRightNum;
    }

    public int getHitWrongNum() {
        return this.hitWrongNum;
    }

    public void setHitWrongNum(int hitWrongNum) {
        this.hitWrongNum = hitWrongNum;
    }

    public float getHitRate() {
        return this.hitRate;
    }

    public void setHitRate(float hitRate) {
        this.hitRate = hitRate;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
