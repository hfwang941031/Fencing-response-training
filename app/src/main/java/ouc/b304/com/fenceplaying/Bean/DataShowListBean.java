package ouc.b304.com.fenceplaying.Bean;

/**
 * @author 王海峰 on 2018/12/19 10:07
 */
public class DataShowListBean {
    private String name;
    private String trainMode;
    private int trainTimes;
    private String averageScore;

    public DataShowListBean() {
    }

    public DataShowListBean(String name, String trainMode, int trainTimes, String averageScore) {
        this.name = name;
        this.trainMode = trainMode;
        this.trainTimes = trainTimes;
        this.averageScore = averageScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainMode() {
        return trainMode;
    }

    public void setTrainMode(String trainMode) {
        this.trainMode = trainMode;
    }

    public int getTrainTimes() {
        return trainTimes;
    }

    public void setTrainTimes(int trainTimes) {
        this.trainTimes = trainTimes;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    @Override
    public String toString() {
        return "DataShowListBean{" +
                "name='" + name + '\'' +
                ", trainMode='" + trainMode + '\'' +
                ", trainTimes=" + trainTimes +
                ", averageScore=" + averageScore +
                '}';
    }

}
