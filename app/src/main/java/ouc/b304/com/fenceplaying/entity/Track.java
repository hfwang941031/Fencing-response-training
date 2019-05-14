package ouc.b304.com.fenceplaying.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Track
 * 跑道
 *
 * @author Skx
 * @date 2018/12/13
 */
public class Track implements Serializable, Cloneable{
    /**
     * 跑道状态 0 正常 1 关闭
     */
    private int mState;
    /**
     * 跑道编号
     */
    private int mIndex;
    /**
     * 跑道里包含的光标
     */
    private List<DbLight> mLightList = new ArrayList<>();

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public List<DbLight> getLightList() {
        return mLightList;
    }

    public void setLightList(List<DbLight> lightList) {
        mLightList = lightList;
    }

    @Override
    public String toString() {
        return "Track{" +
                "mState=" + mState +
                ", mIndex=" + mIndex +
                ", mLightList=" + mLightList +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Track track = null;
        try {
            track = (Track) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return track;
    }
}
