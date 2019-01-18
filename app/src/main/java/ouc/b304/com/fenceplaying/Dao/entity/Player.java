package ouc.b304.com.fenceplaying.Dao.entity;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import ouc.b304.com.fenceplaying.Dao.DaoSession;
import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.SingleSpotScoresDao;
import ouc.b304.com.fenceplaying.Dao.SingleLineScoresDao;
import ouc.b304.com.fenceplaying.Dao.LScoresDao;
import ouc.b304.com.fenceplaying.Dao.MatrixScoresDao;
import ouc.b304.com.fenceplaying.Dao.AccuracyScoresDao;

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

    private Long coachId;

    @ToMany(referencedJoinProperty = "playerId")
    private List<SingleSpotScores> singleSpotScores;

    @ToMany(referencedJoinProperty ="playerId")
    private List<SingleLineScores> singleLineScores;

    @ToMany(referencedJoinProperty ="playerId")
    private List<MatrixScores> matrixScores;

    @ToMany(referencedJoinProperty ="playerId")
    private List<LScores> lScores;

    @ToMany(referencedJoinProperty ="playerId")
    private List<AccuracyScores> accuracyScores;



    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


    /** Used for active entity operations. */
    @Generated(hash = 2108114900)
    private transient PlayerDao myDao;

    @Generated(hash = 1763133379)
    public Player(Long Id, String name, String gender, String trainMode, String groupId, String height,
            String weight, String age, Long coachId) {
        this.Id = Id;
        this.name = name;
        this.gender = gender;
        this.trainMode = trainMode;
        this.groupId = groupId;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.coachId = coachId;
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
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1252224634)
    public List<SingleSpotScores> getSingleSpotScores() {
        if (singleSpotScores == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SingleSpotScoresDao targetDao = daoSession.getSingleSpotScoresDao();
            List<SingleSpotScores> singleSpotScoresNew = targetDao
                    ._queryPlayer_SingleSpotScores(Id);
            synchronized (this) {
                if (singleSpotScores == null) {
                    singleSpotScores = singleSpotScoresNew;
                }
            }
        }
        return singleSpotScores;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1275023709)
    public synchronized void resetSingleSpotScores() {
        singleSpotScores = null;
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
    @Generated(hash = 1600887847)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlayerDao() : null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 426061896)
    public List<SingleLineScores> getSingleLineScores() {
        if (singleLineScores == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SingleLineScoresDao targetDao = daoSession.getSingleLineScoresDao();
            List<SingleLineScores> singleLineScoresNew = targetDao._queryPlayer_SingleLineScores(Id);
            synchronized (this) {
                if (singleLineScores == null) {
                    singleLineScores = singleLineScoresNew;
                }
            }
        }
        return singleLineScores;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 390282284)
    public synchronized void resetSingleLineScores() {
        singleLineScores = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 729814454)
    public List<MatrixScores> getMatrixScores() {
        if (matrixScores == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MatrixScoresDao targetDao = daoSession.getMatrixScoresDao();
            List<MatrixScores> matrixScoresNew = targetDao._queryPlayer_MatrixScores(Id);
            synchronized (this) {
                if (matrixScores == null) {
                    matrixScores = matrixScoresNew;
                }
            }
        }
        return matrixScores;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1328242864)
    public synchronized void resetMatrixScores() {
        matrixScores = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1120248599)
    public List<LScores> getLScores() {
        if (lScores == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LScoresDao targetDao = daoSession.getLScoresDao();
            List<LScores> lScoresNew = targetDao._queryPlayer_LScores(Id);
            synchronized (this) {
                if (lScores == null) {
                    lScores = lScoresNew;
                }
            }
        }
        return lScores;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1095514738)
    public synchronized void resetLScores() {
        lScores = null;
    }
    public Long getCoachId() {
        return this.coachId;
    }
    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 483741235)
    public List<AccuracyScores> getAccuracyScores() {
        if (accuracyScores == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AccuracyScoresDao targetDao = daoSession.getAccuracyScoresDao();
            List<AccuracyScores> accuracyScoresNew = targetDao._queryPlayer_AccuracyScores(Id);
            synchronized (this) {
                if (accuracyScores == null) {
                    accuracyScores = accuracyScoresNew;
                }
            }
        }
        return accuracyScores;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 271157214)
    public synchronized void resetAccuracyScores() {
        accuracyScores = null;
    }
    
}
