package ouc.b304.com.fenceplaying.utils.newUtils;

import android.content.Intent;
import java.util.ArrayList;
import java.util.List;
import io.realm.RealmList;
import ouc.b304.com.fenceplaying.entity.BackCommand;
import ouc.b304.com.fenceplaying.entity.DbDevice;
import ouc.b304.com.fenceplaying.entity.DbLight;
import ouc.b304.com.fenceplaying.entity.ShockInfo;
import ouc.b304.com.fenceplaying.entity.TimeInfo;
import ouc.b304.com.fenceplaying.order.OrderEntity;

/**
 * AnalysisUtils
 * 解析数据
 *
 * @author Skx
 * @date 2018/11/23
 */
public class AnalysisUtils {
    /**
     * 解析协调器
     *
     * @param data
     * @return
     */
    public static void AnalysisData(String data) {
        List<BackCommand> backCommandList = new ArrayList<>();
        synchronized (AppConfig.LOCK_READ) {
            List<DbLight> dbLightList = new ArrayList<>();
            a:
            while (data.contains("FE")) {
                if (data.length() > 4) {
                    while ("FE".equals(String.valueOf(data.charAt(0)) + String.valueOf(data.charAt(1)))) {
                        String d = String.valueOf(data.charAt(2)) + String.valueOf(data.charAt(3));
                        Integer integer = Integer.valueOf(d, 16);
                        switch (integer) {
                            case 0x15:
                                //协调器
                                if (data.length() >= (0x15 + 3) * 2) {
                                    String substring = data.substring(0, 48);
                                    data = data.substring(48, data.length());
                                    String[] strings = splitString(substring);
                                    DbDevice device = new DbDevice();
                                    device.setChannelNumber(Integer.valueOf(strings[11], 16) + "");
                                    device.setPanId(strings[13] + strings[12]);
                                    device.setMacAddress(strings[14] + strings[15] + strings[16] + strings[17] + strings[18] + strings[19] + strings[20] + strings[21]);
                                    device.setDbLights(new RealmList<DbLight>());
                                    AppConfig.sDevice = device;
                                    Utils.getContext().sendBroadcast(new Intent().setAction(AppConfig.ACTION_ANALYSIS_DEVICE));
                                } else {
                                    AppConfig.sData = data;
                                    break a;
                                }
                                break;
                            case 0x0f:
                                //心跳包
                                if (data.length() >= (0x0f + 3) * 2) {
                                    String substring = data.substring(0, 36);
                                    data = data.substring(36, data.length());
                                    String[] strings = splitString(substring);

                                    DbLight light = new DbLight();
                                    light.setName("00");
                                    light.setAddress(strings[5] + strings[6]);
                                    light.setPower(strings[13] + strings[12]);
                                    dbLightList.add(light);
                                } else {
                                    AppConfig.sData = data;
                                    break a;
                                }
                                break;
                            case 0x0e:
                                //时间
                                if (data.length() >= (0x0e + 3) * 2) {
                                    String substring = data.substring(0, 34);
                                    data = data.substring(34, data.length());
                                    String[] strings = splitString(substring);
                                    TimeInfo timeInfo = new TimeInfo();
                                    timeInfo.setAddress(strings[5] + strings[6]);
                                    for (DbLight light : AppConfig.sDbLights) {
                                        if (timeInfo.getAddress().equals(light.getAddress())) {

                                            if ("00".equals(strings[10])) {
                                                timeInfo.setReactionModel("红外");
                                            } else {
                                                timeInfo.setReactionModel("震动");
                                            }
                                            String time = strings[14] + strings[13] + strings[12] + strings[11];
                                            Long time_ms = Long.valueOf(time, 16);
                                            timeInfo.setTime(time_ms);
                                            timeInfo.setName(light.getName());
                                            break;
                                        }
                                    }
                                    Utils.getContext().sendBroadcast(new Intent().putExtra("timeInfo", timeInfo).setAction(AppConfig.ACTION_TIME_INFO));
                                } else {
                                    AppConfig.sData = data;
                                    break a;
                                }
                                break;
                            case 0x45:
                                //震动详情
                                if (data.length() >= (0x45 + 3) * 2) {
                                    String substring = data.substring(0, 144);
                                    data = data.substring(144, data.length());
                                    String[] strings = splitString(substring);

                                    ShockInfo shockInfo = new ShockInfo();
                                    shockInfo.setAddress(strings[5] + strings[6]);
                                    String[] shock_x = new String[10];
                                    String[] shock_y = new String[10];
                                    String[] shock_z = new String[10];
                                    shock_x[0] = strings[11] + strings[10];
                                    shock_y[0] = strings[13] + strings[12];
                                    shock_z[0] = strings[15] + strings[14];

                                    shock_x[1] = strings[17] + strings[16];
                                    shock_y[1] = strings[19] + strings[18];
                                    shock_z[1] = strings[21] + strings[20];

                                    shock_x[2] = strings[23] + strings[22];
                                    shock_y[2] = strings[25] + strings[24];
                                    shock_z[2] = strings[27] + strings[26];

                                    shock_x[3] = strings[29] + strings[28];
                                    shock_y[3] = strings[31] + strings[30];
                                    shock_z[3] = strings[33] + strings[32];

                                    shock_x[4] = strings[35] + strings[34];
                                    shock_y[4] = strings[37] + strings[36];
                                    shock_z[4] = strings[39] + strings[38];

                                    shock_x[5] = strings[41] + strings[40];
                                    shock_y[5] = strings[43] + strings[42];
                                    shock_z[5] = strings[45] + strings[44];

                                    shock_x[6] = strings[47] + strings[46];
                                    shock_y[6] = strings[49] + strings[48];
                                    shock_z[6] = strings[51] + strings[50];

                                    shock_x[7] = strings[53] + strings[52];
                                    shock_y[7] = strings[55] + strings[54];
                                    shock_z[7] = strings[57] + strings[56];

                                    shock_x[8] = strings[59] + strings[58];
                                    shock_y[8] = strings[61] + strings[60];
                                    shock_z[8] = strings[63] + strings[62];

                                    shock_x[9] = strings[65] + strings[64];
                                    shock_y[9] = strings[67] + strings[66];
                                    shock_z[9] = strings[69] + strings[68];
                                    shockInfo.setShack_x(shock_x);
                                    shockInfo.setShack_y(shock_y);
                                    shockInfo.setShack_z(shock_z);
                                    Utils.getContext().sendBroadcast(new Intent().putExtra("shackInfo", shockInfo).setAction(AppConfig.ACTION_SHACK_INFO));
                                } else {
                                    AppConfig.sData = data;
                                    break a;
                                }
                                break;
                            case 0x11:
                                //子节点退网
                                if (data.length() >= (0x11 + 3) * 2) {
                                    String substring = data.substring(0, 40);
                                    data = data.substring(40, data.length());
                                    String[] strings = splitString(substring);
                                    String address = strings[5] + strings[6];
                                    Intent intent = new Intent();
                                    intent.putExtra("address", address);
                                    intent.setAction(AppConfig.ACTION_REMOVE_LIGHT);
                                    Utils.getContext().sendBroadcast(intent);
                                } else {
                                    break a;
                                }
                                break;
                            case 0x13:
                                if (data.length() >= (0x13 + 3) * 2) {
                                    String substring = data.substring(0, 44);
                                    data = data.substring(44, data.length());
//                                String[] strings = splitString(substring);
                                } else {
                                    AppConfig.sData = data;
                                    break a;
                                }
                                break;
                            case 0x08:
                                //默认响应帧
                                if (data.length() >= (0x08 + 3) * 2) {
                                    String substring = data.substring(0, 22);
                                    data = data.substring(22, data.length());
                                    String[] strings = splitString(substring);
                                    String address;
                                    String state;
                                    switch (strings[7]) {
                                        case "05":
                                            //休眠指令
                                            address = strings[5] + strings[6];
                                            state = strings[8];
                                            if ("00".equals(state)) {
                                                c:
                                                for (DbLight dbLight : AppConfig.sDbLights) {
                                                    if (dbLight.getAddress().equals(address)) {
                                                        dbLight.setPower("0000");
                                                        dbLight.setSleep(true);
                                                        for (OrderEntity orderEntity : AppConfig.sOrderEntityList) {
                                                            if (orderEntity.getAddress().equals(address)) {
                                                                orderEntity.setOrder("");
                                                                orderEntity.setSendNum(0);
                                                                orderEntity.setTime(500);
                                                                break c;
                                                            }
                                                        }
                                                        break;
                                                    }
                                                }
                                            }
                                            break;
                                        case "06":
                                            //进入工作模式指令
                                            address = strings[5] + strings[6];
                                            state = strings[8];
                                            if ("00".equals(state)) {
                                                for (OrderEntity orderEntity : AppConfig.sOrderEntityList) {
                                                    if (orderEntity.getAddress().equals(address)) {
                                                        orderEntity.setOrder("");
                                                        orderEntity.setSendNum(0);
                                                        orderEntity.setTime(500);
                                                        break;
                                                    }
                                                }
                                            }
                                            BackCommand backCommand = new BackCommand();
                                            backCommand.setStatus(state);
                                            backCommand.setAddress(address);
                                            backCommand.setCommandId("06");
                                            backCommandList.add(backCommand);

                                            break;
                                        default:
                                    }
                                } else {
                                    AppConfig.sData = data;
                                    break a;
                                }
                                break;
                            case 0x0a:
                                //测试
                                //FE 0A 00 12 01 00 00 0A 01 00 00 D6 64
                                if (data.length() >= (0x0a + 3) * 2) {
                                    String substring = data.substring(0, 26);
                                    data = data.substring(26, data.length());
                                } else {
                                    break a;
                                }
                                break;
                            default:
                        }
                        if (data.length() <= 4) {
                            break;
                        }
                    }
                    if (data.length() > 4) {
                        data = data.substring(2, data.length());
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (dbLightList.size() > 0) {
                for (DbLight light : dbLightList) {
                    boolean isHave = false;
                    for (DbLight dbLight : AppConfig.sDbLights) {
                        if (light.getAddress().equals(dbLight.getAddress())) {
                            isHave = true;
                            dbLight.setSleep(false);
                            dbLight.setUsable(true);
                            dbLight.setReadTime(System.currentTimeMillis());
                            dbLight.setPower(light.getPower());
                            break;
                        }
                    }
                    if (!isHave) {
                        light.setReadTime(System.currentTimeMillis());
                        AppConfig.sDbLights.add(light);
                    }
                }
            }
        }
    }

    private static String[] splitString(String strData) {
        int m = strData.length() / 2;
        if (m * 2 < strData.length()) {
            m++;
        }
        String[] strings = new String[m];
        int j = 0;
        for (int i = 0; i < strData.length(); i++) {
            if (i % 2 == 0) {
                strings[j] = "" + strData.charAt(i);
            } else {
                strings[j] = strings[j] + strData.charAt(i);
                j++;
            }
        }
        return strings;
    }

}
