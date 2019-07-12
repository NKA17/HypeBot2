package global;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static String getTimeStamp(String format){
        SimpleDateFormat sdfDate = new SimpleDateFormat(format);//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    public static boolean isDay(int day){
        Date now = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        return cal.get(Calendar.DAY_OF_WEEK) == day;
    }

    public static int dayToInt(){
        Date now = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static boolean isTime(String time){
        long tlong = Long.parseLong(time);
        long curTime = Long.parseLong(getTimeStamp("yyyMMddHHmm"));
        return tlong - curTime == 0;
    }

    public static boolean isTime(String time,String format){
        long tlong = Long.parseLong(time);
        long curTime = Long.parseLong(getTimeStamp(format));
        return tlong - curTime == 0;
    }
}
