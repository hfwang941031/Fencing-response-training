package ouc.b304.com.fenceplaying.Bean;

public class Player {
    private int id;
    private String name;
    private String age;
    private String height;
    private String weight;
    private String sex;
    private String playMode;
    private String groupname;
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getPlayMode() {
        return playMode;
    }

    public void setPlayMode(String playMode) {
        this.playMode = playMode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Player(int id, String name, String age, String height, String weight, String groupname, String sex, String playMode) {
        super();
        this.id = id;

        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.groupname = groupname;
        this.sex = sex;
        this.playMode = playMode;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", sex='" + sex + '\'' +
                ", playMode='" + playMode + '\'' +
                ", groupname='" + groupname + '\'' +
                ", test='" + test + '\'' +
                '}';
    }

    public Player(String name, String age, String height, String weight, String groupname, String sex, String playMode) {
        super();
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.groupname = groupname;
        this.sex = sex;
        this.playMode = playMode;
    }

    public Player() {
        super();
        // TODO Auto-generated constructor stub
    }


}
