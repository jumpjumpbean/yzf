package co.yzf;

import java.util.*;

/**
 * Created by root on 2014/5/28.
 */
public class CommonUtil {
    public static java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
    public static java.text.SimpleDateFormat yyMM = new java.text.SimpleDateFormat("yyyy年MM月");
    public static java.text.SimpleDateFormat yyMMdf = new java.text.SimpleDateFormat("yyMM");

    public static String trimStr(String str){
        if(str == null){
            return null;
        }else{
            str = str.trim();
            if(str.length() == 0){
                return null;
            }else {
                return str;
            }
        }
    }

    public static Date parseDate(String pStr){
        pStr = trimStr(pStr);
        if(pStr == null){
            return null;
        }else{
           try {
               return df.parse(pStr);
           }catch(Exception e){
               return null;
           }
        }
    }

    public static String getCurMonth(){
        return yyMMdf.format(new Date());
    }
    public static String yyyymmFormat(Date date){
        return yyMM.format(date);
    }
    public static String yyyymmdd(Date date){
        return df.format(date);
    }

    private static java.util.Random rnd = new java.util.Random();
    public static int randomNum(int limit){
        int res = rnd.nextInt(limit - 1);
        return res + 1;
    }

    public static String random(int bit) {
        if (bit == 0)
            bit = 6; // 默认6位
        // 因为o和0,l和1很难区分,所以,去掉大小写的o和l
        String str = "";
        str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";// 初始化种子
        return org.apache.commons.lang.RandomStringUtils.random(bit, str);// 返回6位的字符串
    }

    public static String getAttitude(int atti){
        if(atti == 1){
            return "拒绝通过";
        }else if(atti == 2){
            return "一般通过";
        }else if(atti == 3){
            return "满意";
        }else if(atti == 4){
            return "非常满意";
        }
        return "";
    }

    public static List<String> getFilterDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        List<String> res = new ArrayList<String>();
        Date curDate = calendar.getTime();
        res.add(yyyymmFormat(curDate) + ":" + yyyymmdd(curDate));
        calendar.add(Calendar.MONTH, -1);
        curDate = calendar.getTime();
        res.add(yyyymmFormat(curDate) + ":" + yyyymmdd(curDate));
        calendar.add(Calendar.MONTH,-1);
        curDate = calendar.getTime();
        res.add(yyyymmFormat(curDate) + ":" + yyyymmdd(curDate));
        return res;
    }

    public static Date getNextMonth(Date curDate){
        if(curDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(curDate);
            calendar.add(Calendar.MONTH, 1);
            return calendar.getTime();
        }
        return null;
    }
}
