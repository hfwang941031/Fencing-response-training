package ouc.b304.com.fenceplaying.Dao.entity;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;
import java.util.List;

import ouc.b304.com.fenceplaying.Dao.convert.StringConverter;

/**
 * @author 王海峰 on 2018/12/3 09:22
 */

@Entity
public class SingleLineScores {

    @org.greenrobot.greendao.annotation.Id
    private Long Id;

    private int trainingTimes;

    private float averageScores;

    @Convert(columnType = String.class,converter = StringConverter.class)
    private List<String> scoresList;

    private Date date;

    private long playerId;

    @Generated(hash = 297122603)
    public SingleLineScores(Long Id, int trainingTimes, float averageScores,
            List<String> scoresList, Date date, long playerId) {
        this.Id = Id;
        this.trainingTimes = trainingTimes;
        this.averageScores = averageScores;
        this.scoresList = scoresList;
        this.date = date;
        this.playerId = playerId;
    }

    @Generated(hash = 1258970968)
    public SingleLineScores() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public int getTrainingTimes() {
        return this.trainingTimes;
    }

    public void setTrainingTimes(int trainingTimes) {
        this.trainingTimes = trainingTimes;
    }

    public float getAverageScores() {
        return this.averageScores;
    }

    public void setAverageScores(float averageScores) {
        this.averageScores = averageScores;
    }

    public List<String> getScoresList() {
        return this.scoresList;
    }

    public void setScoresList(List<String> scoresList) {
        this.scoresList = scoresList;
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
