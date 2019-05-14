package ouc.b304.com.fenceplaying.order;




import ouc.b304.com.fenceplaying.entity.DbLight;

/**
 * Order
 *
 * @author Skx
 * @date 2018/12/8
 */
public class Order {
    private CommandNew mCommandNew;
    private DbLight mLight;

    public CommandNew getCommandNew() {
        return mCommandNew;
    }

    public void setCommandNew(CommandNew commandNew) {
        mCommandNew = commandNew;
    }

    public DbLight getLight() {
        return mLight;
    }

    public void setLight(DbLight light) {
        mLight = light;
    }
}
