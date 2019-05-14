package ouc.b304.com.fenceplaying.order;


import android.util.Log;



import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.entity.Place;
import ouc.b304.com.fenceplaying.entity.Track;
import ouc.b304.com.fenceplaying.usbUtils.UsbUtil;
import ouc.b304.com.fenceplaying.utils.newUtils.AppConfig;

/**
 * OrderUtils
 *
 * @author Skx
 * @date 2018/11/23
 */
public class OrderUtils {
    private static OrderUtils instance;
    private final ExecutorService mExecutorService;

    private OrderUtils() {
        mExecutorService = Executors.newCachedThreadPool();
    }

    public static OrderUtils getInstance() {
        if (instance == null) {
            instance = new OrderUtils();
        }
        return instance;
    }

    /**
     * 初始化协调器
     */
    public void initDevice() {
        //第五位 00不响应 01响应
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                String data = "FE0A0000000000020000000000";
                UsbUtil.getInstance().sendMessage(data);
            }
        });

    }

    /**
     * 允许入网
     */
    public void allowAccessNetwork() {
        //第五位 00不响应 01响应
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                String data = "FE0B0000010000020101000F0000";
                UsbUtil.getInstance().sendMessage(data);
            }
        });
    }

    /**
     * 重置协调器
     */
    public void resetCoordinator() {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                String data = "FE0A0000000000020200000000";
                UsbUtil.getInstance().sendMessage(data);
            }
        });
    }

    /**
     * 获取数据
     *
     * @return
     */
    public String getData() {
        return UsbUtil.getInstance().readData();
    }

    /**
     * 发送命令
     *
     * @param commandNew 命令
     * @param address    灯的短地址
     */
    public void sendCommand(CommandNew commandNew, String address, SetOnOrderListener onOrderListener) {
        OrderEntity orderEntity = operateOrderEntityList(address, "06");
        String frameId = Integer.toHexString(orderEntity.getID());
        if (frameId.length() == 1) {
            frameId = "0" + frameId;
        }
        String data = getOrder(commandNew);
        data = "FE1100" + frameId + AppConfig.sEnable_rsp + address + "02060700" + data + "0000";
        orderEntity.setOrder(data);
        onOrderListener.onOrderListener(data);

    }

    /**
     * 发送命令
     *
     * @param commandNew 命令
     * @param address    灯的短地址
     */
    public void sendCommand(CommandNew commandNew, String address) {
        OrderEntity orderEntity = operateOrderEntityList(address, "06");
        String frameId = Integer.toHexString(orderEntity.getID());
        if (frameId.length() == 1) {
            frameId = "0" + frameId;
        }
        String data = getOrder(commandNew);
        data = "FE1100" + frameId + AppConfig.sEnable_rsp + address + "02060700" + data + "0000";
        orderEntity.setOrder(data);
        Log.d("timeInfo", "555555:" + orderEntity.toString());
    }

    /**
     * 发送跳绳命令
     *
     * @param commandNew
     * @param address
     *//*
    public void sendRopeCommand(CommandNew commandNew, String address) {
        OrderEntity orderEntity = operateOrderEntityList(address, "06");
        String frameId = Integer.toHexString(orderEntity.getID());
        if (frameId.length() == 1) {
            frameId = "0" + frameId;
        }
        String data = getRopeOrder(commandNew);
        data = "FE1100" + frameId + AppConfig.sEnable_rsp + address + "02060700" + data + "0000";
        orderEntity.setOrder(data);

    }*/


    /**
     * 重新发送命令
     *
     * @param order
     */
    public void retrySendOrder(String order) {
        Log.d("retrySendOrder", order);
        UsbUtil.getInstance().sendMessage(order);
    }

    /**
     * 发送命令
     *
     * @param orderList
     * @param setOnOrderListener
     */
    public void sendCommand(List<Order> orderList, SetOnOrderListener setOnOrderListener) {
        for (Order order : orderList) {
            String address = order.getLight().getAddress();
            CommandNew commandNew = order.getCommandNew();
            OrderEntity orderEntity = operateOrderEntityList(address, "06");
            String frameId = Integer.toHexString(orderEntity.getID());
            if (frameId.length() == 1) {
                frameId = "0" + frameId;
            }
            String data = getOrder(commandNew);
            data = "FE1100" + frameId + AppConfig.sEnable_rsp + address + "02060700" + data + "0000";
            String upperCase = data.toUpperCase();
            orderEntity.setOrder(data);
            setOnOrderListener.onOrderListener(upperCase);
        }
    }

    /**
     * 发送命令
     *
     * @param orderList
     */
    public void sendCommand(List<Order> orderList) {
        for (Order order : orderList) {
            String address = order.getLight().getAddress();
            CommandNew commandNew = order.getCommandNew();
            OrderEntity orderEntity = operateOrderEntityList(address, "06");
            String frameId = Integer.toHexString(orderEntity.getID());
            if (frameId.length() == 1) {
                frameId = "0" + frameId;
            }
            String data = getOrder(commandNew);
            data = "FE1100" + frameId + AppConfig.sEnable_rsp + address + "02060700" + data + "0000";
            orderEntity.setOrder(data);
        }
    }

    /**
     * 休眠
     *
     * @param dbLights
     */
    public void sleepAllLight(List<DbLight> dbLights) {
        //第五位 00不响应 01响应
        for (DbLight dbLight : dbLights) {
            if (!"0000".equals(dbLight.getPower())) {
                OrderEntity orderEntity = operateOrderEntityList(dbLight.getAddress(), "05");
                String frameId = Integer.toHexString(orderEntity.getID());
                if (frameId.length() == 1) {
                    frameId = "0" + frameId;
                }
                String data = "FE0A00" + frameId + AppConfig.sEnable_rsp + dbLight.getAddress() + "020500000000";
                orderEntity.setOrder(data);
            }
        }

    }

    /**
     * 开全灯
     */
    public void turnOnAllLight(List<DbLight> dbLights) {
        for (DbLight dbLight : dbLights) {
            if (!"0000".equals(dbLight.getPower()) ) {
                OrderEntity orderEntity = operateOrderEntityList(dbLight.getAddress(), "06");
                String frameId = Integer.toHexString(orderEntity.getID());
                if (frameId.length() == 1) {
                    frameId = "0" + frameId;
                }
                String data = "FE1100" + frameId + AppConfig.sEnable_rsp + dbLight.getAddress() + "02060700010800080201000000";
                orderEntity.setOrder(data);
            }
        }
    }

    /**
     * 关全灯
     */
    public void closeAllLight(List<DbLight> dbLights) {
        for (DbLight dbLight : dbLights) {
            if (!"0000".equals(dbLight.getPower()) ) {
                OrderEntity orderEntity = operateOrderEntityList(dbLight.getAddress(), "06");
                String frameId = Integer.toHexString(orderEntity.getID());
                if (frameId.length() == 1) {
                    frameId = "0" + frameId;
                }
                String data = "FE1100" + frameId + AppConfig.sEnable_rsp + dbLight.getAddress() + "02060700010000080201000000";
                orderEntity.setOrder(data);
            }
        }
    }

    /**
     * 退网
     *
     * @param dbLights
     */
    public void removeNet(List<DbLight> dbLights) {
        for (DbLight dbLight : dbLights) {
            OrderEntity orderEntity = operateOrderEntityList(dbLight.getAddress(), "03");
            String frameId = Integer.toHexString(orderEntity.getID());
            if (frameId.length() == 1) {
                frameId = "0" + frameId;
            }
            String data = "FE0A00" + frameId + AppConfig.sEnable_rsp + dbLight.getAddress() + "020300000000";
            orderEntity.setOrder(data);
        }
    }

    /**
     * 退网
     *
     * @param address
     */
    public void removeNet(String address) {
        OrderEntity orderEntity = operateOrderEntityList(address, "03");
        String frameId = Integer.toHexString(orderEntity.getID());
        if (frameId.length() == 1) {
            frameId = "0" + frameId;
        }
        String data = "FE0A00" + frameId + AppConfig.sEnable_rsp + address + "020300000000";
        orderEntity.setOrder(data);
    }

    /**
     * 关灯
     *
     * @param dbLight 要操作的灯
     */
    public void turnOffLight(DbLight dbLight) {
        OrderEntity orderEntity = operateOrderEntityList(dbLight.getAddress(), "06");
        String frameId = Integer.toHexString(orderEntity.getID());
        if (frameId.length() == 1) {
            frameId = "0" + frameId;
        }
        String data = "FE1100" + frameId + AppConfig.sEnable_rsp + dbLight.getAddress() + "02060700010000080201000000";
        orderEntity.setOrder(data);
    }

    /**
     * 开灯
     *
     * @param dbLight 要操作的灯
     */
    public void turnOnLight(DbLight dbLight) {
        OrderEntity orderEntity = operateOrderEntityList(dbLight.getAddress(), "06");
        String frameId = Integer.toHexString(orderEntity.getID());
        if (frameId.length() == 1) {
            frameId = "0" + frameId;
        }
        String data = "FE1100" + frameId + AppConfig.sEnable_rsp + dbLight.getAddress() + "02060700010800080201000000";
        orderEntity.setOrder(data);
    }

    /**
     * 开灯
     *
     * @param trackList 要操作的跑道列表
     */
    public void turnOnLight(List<Track> trackList) {
        int a = 0;
        CommandNew command = new CommandNew();
        for (Track track : trackList) {
            command.setBeforeOutColor(CommandRules.OutColor.values()[a % 7]);
            List<DbLight> lightList = track.getLightList();
            for (DbLight dbLight : lightList) {
                OrderEntity orderEntity = operateOrderEntityList(dbLight.getAddress(), "06");
                String frameId = Integer.toHexString(orderEntity.getID());
                if (frameId.length() == 1) {
                    frameId = "0" + frameId;
                }
                String order = getOrder(command);
                String data = "FE1100" + frameId + AppConfig.sEnable_rsp + dbLight.getAddress() + "02060700" + order + "0000";
                orderEntity.setOrder(data);
            }
            a += 1;
        }
    }

    /**
     * 开灯
     *
     * @param places 场地
     */
    public void turnOnPlaceLight(List<Place> places) {
        int a = 0;
        CommandNew command = new CommandNew();
        for (Place place : places) {
            List<Track> trackList = place.getTrackList();
            for (Track track : trackList) {
                command.setBeforeOutColor(CommandRules.OutColor.values()[a % 7]);
                List<DbLight> lightList = track.getLightList();
                for (DbLight dbLight : lightList) {
                    OrderEntity orderEntity = operateOrderEntityList(dbLight.getAddress(), "06");
                    String frameId = Integer.toHexString(orderEntity.getID());
                    if (frameId.length() == 1) {
                        frameId = "0" + frameId;
                    }
                    String order = getOrder(command);
                    String data = "FE1100" + frameId + AppConfig.sEnable_rsp + dbLight.getAddress() + "02060700" + order + "0000";
                    orderEntity.setOrder(data);
                }
                a += 1;
            }
        }
    }

    /**
     * 关灯
     *
     * @param trackList 要操作的跑道列表
     */
    public void turnOffLight(List<Track> trackList) {
        for (Track track : trackList) {
            List<DbLight> lightList = track.getLightList();
            for (DbLight dbLight : lightList) {
                dbLight.setOpen(false);
                OrderEntity orderEntity = operateOrderEntityList(dbLight.getAddress(), "06");
                String frameId = Integer.toHexString(orderEntity.getID());
                if (frameId.length() == 1) {
                    frameId = "0" + frameId;
                }
                String data = "FE1100" + frameId + AppConfig.sEnable_rsp + dbLight.getAddress() + "02060700010000080201000000";
                orderEntity.setOrder(data);
            }
        }
    }

    /**
     * 关灯
     *
     * @param lights
     */
    public void turnOffLightList(List<DbLight> lights) {
        for (DbLight dbLight : lights) {
            dbLight.setOpen(false);
            OrderEntity orderEntity = operateOrderEntityList(dbLight.getAddress(), "06");
            String frameId = Integer.toHexString(orderEntity.getID());
            if (frameId.length() == 1) {
                frameId = "0" + frameId;
            }
            String data = "FE1100" + frameId + AppConfig.sEnable_rsp + dbLight.getAddress() + "02060700010000080201000000";
            orderEntity.setOrder(data);
        }
    }

    /**
     * 操作OrderEntityList
     *
     * @param address
     * @param orderType
     * @return
     */
    private OrderEntity operateOrderEntityList(String address, String orderType) {
        boolean isHave = false;
        OrderEntity orderEntity_1 = null;
        for (OrderEntity orderEntity : AppConfig.sOrderEntityList) {
            if (address.equals(orderEntity.getAddress())) {
                orderEntity_1 = orderEntity;
                isHave = true;
                int id = orderEntity.getID();
                if (id >= 255) {
                    id = 0;
                } else {
                    id += 1;
                }
                orderEntity.setID(id);
                orderEntity.setTime(0);
                orderEntity.setSendNum(0);
                break;
            }
        }
        if (!isHave) {
            orderEntity_1 = new OrderEntity();
            orderEntity_1.setAddress(address);
            orderEntity_1.setOrderId(orderType);
            orderEntity_1.setTime(0);
            AppConfig.sOrderEntityList.add(orderEntity_1);
        }

        return orderEntity_1;
    }


    private String getOrder(CommandNew commandNew) {
        String order = "";
        String b_i_c = orderToBinaryString(commandNew.getBeforeInColor().ordinal(), 3);
        String b_i_b = orderToBinaryString(commandNew.getBeforeInBlink().ordinal(), 5);

        String b_o_c = orderToBinaryString(commandNew.getBeforeOutColor().ordinal(), 3);
        String b_o_b = orderToBinaryString(commandNew.getBeforeOutBlink().ordinal(), 5);

        String b_b = orderToBinaryString(commandNew.getBeforeBuzzerModel().ordinal(), 2);
        String a_b = orderToBinaryString(commandNew.getAfterBuzzerModel().ordinal(), 6);

        String b_i_e = orderToBinaryString(commandNew.getInfraredEmission().ordinal(), 1);
        String b_i_i = orderToBinaryString(commandNew.getInfraredInduction().ordinal(), 1);
        String b_i_m = orderToBinaryString(commandNew.getInfraredModel().ordinal(), 1);
        String b_i_h = orderToBinaryString(commandNew.getInfraredHeight().ordinal(), 5);

        String b_v_i = orderToBinaryString(commandNew.getVibrationInduced().ordinal(), 1);
        String b_v_s = orderToBinaryString(commandNew.getVibrationStrength().ordinal(), 2);
        String b_v_d = orderToBinaryString(commandNew.getVibrationDetails().ordinal(), 5);

        String a_i_c = orderToBinaryString(commandNew.getAfterInColor().ordinal(), 3);
        String a_i_b = orderToBinaryString(commandNew.getAfterInBlink().ordinal(), 5);

        String a_o_c = orderToBinaryString(commandNew.getAfterOutColor().ordinal(), 3);
        String a_o_b = orderToBinaryString(commandNew.getAfterOutBlink().ordinal(), 5);

        String order1 = binaryString2hexString(b_i_b + b_i_c);
        String order2 = binaryString2hexString(b_o_b + b_o_c);
        String order3 = binaryString2hexString(a_b + b_b);
        String order4 = binaryString2hexString(b_i_h + b_i_m + b_i_i + b_i_e);
        String order5 = binaryString2hexString(b_v_d + b_v_s + b_v_i);
        String order6 = binaryString2hexString(a_i_b + a_i_c);
        String order7 = binaryString2hexString(a_o_b + a_o_c);

        order = order1 + order2 + order3 + order4 + order5 + order6 + order7;

        return order;
    }
/*
    /**
     * 格式化跳绳命令
     *
     * @param commandNew
     * @return
     */
    /*private String getRopeOrder(CommandNew commandNew) {
        String order = "";

        String order_type = orderToBinaryString(commandNew.getOrderType().ordinal(), 8);
        String rope_name = orderToBinaryString(commandNew.getRopeName(), 8);
        String time = orderToBinaryString(commandNew.getTime(), 16);
        String count = orderToBinaryString(commandNew.getCount(), 16);
        String spare = orderToBinaryString(commandNew.getSpare(), 8);

        String order1 = binaryString2hexString(order_type);
        String order2 = binaryString2hexString(rope_name);
        String order3 = binaryString2hexString(time);
        String order4 = binaryString2hexString(count);
        String order5 = binaryString2hexString(spare);

        order = order1 + order2 + order3 + order4 + order5;

        return order;
    }*/

    private String orderToBinaryString(int order, int length) {
        String res = Integer.toBinaryString(order);
        int j = length - res.length();
        for (int i = 0; i < j; i++) {
            res = "0" + res;
        }
        return res;
    }

    public String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0) {
            return null;
        }
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp).toUpperCase());
        }
        return tmp.toString();
    }


    public interface SetOnOrderListener {
        /**
         * 返回命令
         *
         * @param order
         */
        void onOrderListener(String order);
    }
}
