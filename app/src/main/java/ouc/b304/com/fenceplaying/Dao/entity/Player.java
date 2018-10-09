package ouc.b304.com.fenceplaying.Dao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Player {

    @Id(autoincrement = true)
    private Long Id;


    private String name;

    private String gender;

    private String trainMode;

    private String groupId;

    private String height;

    private String weight;

    private String age;
    @Generated(hash = 991533776)
    public Player(Long Id, String name, String gender, String trainMode,
            String groupId, String height, String weight, String age) {
        this.Id = Id;
        this.name = name;
        this.gender = gender;
        this.trainMode = trainMode;
        this.groupId = groupId;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }
    @Generated(hash = 30709322)
    public Player() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getTrainMode() {
        return this.trainMode;
    }
    public void setTrainMode(String trainMode) {
        this.trainMode = trainMode;
    }
    public String getGroupId() {
        return this.groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getHeight() {
        return this.height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getWeight() {
        return this.weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    
}
