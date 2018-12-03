package ouc.b304.com.fenceplaying.Dao.convert;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * @author 王海峰 on 2018/11/28 14:40
 */
public class StringConverter implements PropertyConverter<List<String>,String>{
    @Override
    public List<String> convertToEntityProperty(String scoreValue) {
        if (scoreValue==null)
        {return null;}
        else{
            List<String> list = Arrays.asList(scoreValue.split(","));
            return list;
        }
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if (entityProperty == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (String link : entityProperty) {
                sb.append(link);
                sb.append(",");
            }
            return sb.toString();
        }
    }
}
