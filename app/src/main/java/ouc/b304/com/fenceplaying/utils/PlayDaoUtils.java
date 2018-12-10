package ouc.b304.com.fenceplaying.utils;

import java.util.ArrayList;
import java.util.List;

import ouc.b304.com.fenceplaying.Dao.PlayerDao;
import ouc.b304.com.fenceplaying.Dao.entity.Player;

/**
 * @author 王海峰 on 2018/12/5 10:35
 *
 * 返回实体Player的各项数据，并组成一个集合
 */
//返回Player的name并组成一个集合
public class PlayDaoUtils {
    public static List<String> nameList(PlayerDao playerDao, List<String> nameList) {
        List<Player> playerList = new ArrayList<>();
        playerList = playerDao.loadAll();
        for (Player player : playerList) {
            nameList.add(player.getName());
        }
        return nameList;
    }
}
