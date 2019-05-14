package ouc.b304.com.fenceplaying.order;

import android.os.SystemClock;
import android.util.Log;

import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;


/**
 * RetryOrderThread
 *
 * @author Skx
 * @date 2018/12/6
 */
public class RetryOrderThread extends Thread {
    private boolean mIsStop = true;
    @Override
    public void run() {
        super.run();
        while (mIsStop) {
            boolean isSend = false;
            for (OrderEntity orderEntity : AppConfig.sOrderEntityList) {
                if ("".equals(orderEntity.getOrder())) {
                    continue;
                }
                if (orderEntity.getSendNum() > AppConfig.sNum) {
                    orderEntity.setOrder("");
                    Log.d("timeInfo","clear:"+orderEntity.getName());
                    continue;
                }
                if (orderEntity.getTime() > 0) {
                    orderEntity.setTime((int) (orderEntity.getTime() - AppConfig.sSleep));
                    continue;
                }
                if (!isSend) {
                    isSend = true;
                    orderEntity.setTime(500);
                    orderEntity.setSendNum(orderEntity.getSendNum() + 1);
                    String upperCase = orderEntity.getOrder().toUpperCase();
                    if (orderEntity.getOrderId().equals("03")) {
                        orderEntity.setOrder("");
                    }
                    OrderUtils.getInstance().retrySendOrder(upperCase);
                }
            }
            SystemClock.sleep(AppConfig.sSleep);
        }
    }

    public void stopRetry() {
        mIsStop = false;
    }
}
