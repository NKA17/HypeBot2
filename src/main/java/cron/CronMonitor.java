package cron;

import global.App;
import global.DateUtils;
import hypebot.HypeBotContext;
import io.actions.AbstractMessageReceivedAction;

import java.sql.Time;
import java.util.ArrayList;

public class CronMonitor implements Runnable {

    private ArrayList<CronJob> jobs = new ArrayList<>();


    public ArrayList<CronJob> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<CronJob> jobs) {
        this.jobs = jobs;
    }

    public  void addJob(CronJob cj){
        while(isLocked());

        lock();
        jobs.add(cj);
        unlock();
    }

    public boolean removeJob(String name){
        while (isLocked());
        lock();
        CronJob rem = null;
        for(HypeBotContext hbc: App.HYPEBOT.getContextsAsArrayList()) {
            for (CronJob cj : hbc.getJobs()) {
                if (cj.getName().equalsIgnoreCase(name)) {
                    rem = cj;
                    break;
                }
            }
        }

        if(rem!=null){
            jobs.remove(rem);
            unlock();
            return true;
        }
        unlock();
        return false;
    }

    public void lock(){
        lock = true;
    }

    public void unlock(){
        lock = false;
    }

    public boolean isLocked(){
        return lock;
    }
    private boolean lock = false;
    @Override
    public  void run() {

        while(true) {
            while(lock);
            lock();
            for(HypeBotContext hbc: App.HYPEBOT.getContextsAsArrayList()) {
                for (AbstractMessageReceivedAction cj : hbc.getBotjobs()) {
                    if(!cj.prebuild())continue;
                    if(!cj.build())continue;
                    if(!cj.execute())continue;

                }
            }
            unlock();

            synchornizeByMinute();
        }
    }

    private void synchornizeByMinute(){
        long lastTime = Long.parseLong(DateUtils.getTimeStamp("mm"));
        long newTime = lastTime;
        while (lastTime==newTime){
            newTime = Long.parseLong(DateUtils.getTimeStamp("mm"));
        }
    }
}
