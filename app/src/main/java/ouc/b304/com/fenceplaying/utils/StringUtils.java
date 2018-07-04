package ouc.b304.com.fenceplaying.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {


    //只截取数字
    public static String SplitString(String text) {

        String regex = "\\d+(\\.\\d+)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }else {
            return text;
        }

    }

    /**
     * 将数字转为对应字母
     * for example: "1"->"A"  "2"->"B"
     * @param num
     * @return
     */
    public static String numToString(String num){
        String str = null;
        switch (num){
            case "1":
                str = "A";
                break;
            case "2":
                str = "B";
                break;
            case "3":
                str = "C";
                break;
            case "4":
                str = "D";
                break;
            case "5":
                str = "E";
                break;
            case "6":
                str = "F";
                break;
        }
        return str;
    }

    public static String numToString1(String num){
        String str = null;
        switch (num){
            case "1":
                str = "A";
                break;
            case "2":
                str = "B";
                break;
            case "3":
                str = "C";
                break;
            case "4":
                str = "D";
                break;
            case "5":
                str = "E";
                break;
            case "6":
                str = "F";
                break;
            case "7":
                str = "G";
                break;
            case "8":
                str = "H";
                break;
        }
        return str;
    }

    /**
     * 将字母转为对应数字
     * for example: "A"->"1"  "B"->"2"
     * @param str
     * @return
     */
    public static String stringToNum(String str){
        String num = null;
        switch (str){
            case "A":
                num = "1";
                break;
            case "B":
                num = "2";
                break;
            case "C":
                num = "3";
                break;
            case "D":
                num = "4";
                break;
            case "E":
                num = "5";
                break;
            case "F":
                num = "6";
                break;
        }
        return num;
    }
    /**
     * 将数字转为对应字母
     * for example: "1"->"G"  "2"->"H"
     * @param num
     * @return
     */
    public static String numPlusToString(String num){
        String str = null;
        switch (num){
            case "1":
                str = "G";
                break;
            case "2":
                str = "H";
                break;
            case "3":
                str = "I";
                break;
            case "4":
                str = "J";
                break;
            case "5":
                str = "K";
                break;
            case "6":
                str = "L";
                break;
        }
        return str;
    }
}
