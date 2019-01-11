package ouc.b304.com.fenceplaying.Dao.entity;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import ouc.b304.com.fenceplaying.Dao.CoachDao;
import ouc.b304.com.fenceplaying.Dao.DaoSession;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;

/**
 * @author 王海峰 on 2019/1/11 10:09
 */
@Entity
public class Coach {

    @org.greenrobot.greendao.annotation.Id
    private Long Id;

    private String name;

    private String gender;

    private String trainMode;

    private String level;

    private String height;

    private String weight;

    private String age;
    @ToMany(referencedJoinProperty ="coachId")
    private List<Player> listOfPlayers;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 610117219)
    private transient CoachDao myDao;
    @Generated(hash = 1016557288)
    public Coach(Long Id, String name, String gender, String trainMode,
            String level, String height, String weight, String age) {
        this.Id = Id;
        this.name = name;
        this.gender = gender;
        this.trainMode = trainMode;
        this.level = level;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }
    @Generated(hash = 2103376203)
    public Coach() {
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
    public String getLevel() {
        return this.level;
    }
    public void setLevel(String level) {
        this.level = level;
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
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1792860420)
    public List<Player> getListOfPlayers() {
        if (listOfPlayers == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlayerDao targetDao = daoSession.getPlayerDao();
            List<Player> listOfPlayersNew = targetDao._queryCoach_ListOfPlayers(Id);
            synchronized (this) {
                if (listOfPlayers == null) {
                    listOfPlayers = listOfPlayersNew;
                }
            }
        }
        return listOfPlayers;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 546764636)
    public synchronized void resetListOfPlayers() {
        listOfPlayers = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 229074640)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCoachDao() : null;
    }
}
