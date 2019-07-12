package cron;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayChangeAction implements Runnable{

    private Timeable timeable;
    public DayChangeAction(Timeable t){
        timeable = t;
    }


    private String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    private long getTSLong(){
        return Long.parseLong(getCurrentTimeStamp());
    }
    @Override
    public void run() {
        long start = getTSLong();
        while (start-getTSLong()==0){
            try {
                Thread.sleep(60000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        timeable.execute();

    }

}
