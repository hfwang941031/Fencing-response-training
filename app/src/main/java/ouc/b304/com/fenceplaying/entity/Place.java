package ouc.b304.com.fenceplaying.entity;

import java.util.List;

/**
 * @author lenovo
 * @date 2018/3/17
 */

public class Place {
    /**
     * 跑道集合
     */
    private List<Track> mTrackList;
    /**
     * 场地编号
     */
    private int index;
    /**
     * 场地状态 0 正常
     */
    private int state;

    public List<Track> getTrackList() {
        return mTrackList;
    }

    public void setTrackList(List<Track> trackList) {
        mTrackList = trackList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
