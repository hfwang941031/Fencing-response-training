/*
package ouc.b304.com.fenceplaying.Dao.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

import ouc.b304.com.fenceplaying.entity.DbLight;

*/
/**
 * @author 王海峰 on 2019/3/20 15:30
 *//*

public class DbLightConverter implements PropertyConverter<List<DbLight>,String> {

    @Override
    public List<DbLight> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        // 先得获得这个，然后再typeToken.getType()，否则会异常
        TypeToken<List<DbLight>> typeToken = new TypeToken<List<DbLight>>() {
        };
        return new Gson().fromJson(databaseValue, typeToken.getType());
    }

    @Override
    public String convertToDatabaseValue(List<DbLight> list) {
        if (list == null || list.size() == 0) {
            return null;
        } else {
            String sb = new Gson().toJson(list);
            return sb;
        }
    }
}
*/
