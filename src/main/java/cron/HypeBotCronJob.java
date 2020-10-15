package cron;

import enums.Attributes;
import global.DateUtils;
import io.actions.AbstractMessageReceivedAction;
import io.actions.actions.BlankAction;
import io.structure.Body;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.swing.*;
import java.util.Calendar;

public class HypeBotCronJob extends AbstractMessageReceivedAction{

    public HypeBotCronJob(){
        super();
        getBody().getAttributes().add(Attributes.CRON);
        getBody().getAttributes().add(Attributes.ACTION);
    }

    @Override
    public boolean build() {
        for(String in: getBody().getIn()){
            if(isDateAndTime(in))
                return true;
        }
        return false;
    }

    @Override
    public boolean execute() {
        BlankAction ba = new BlankAction();
        Body bo = new Body();
        bo.setOut(getBody().getOut());
        ba.setEvent(getEvent());
        bo.setAuthorId(getBody().getAuthorId());
        bo.setGuildId(getBody().getGuildId());
        bo.setChannelId(getBody().getChannelId());
        ba.setBody(bo);
        ba.execute();
        return true;
    }

    private boolean isDateAndTime(String str){
        Pair<Integer,String> pair = getExecutionTime(str);
        return isDateAndTime(pair);
    }

    private boolean isDateAndTime(Pair<Integer,String> pair){
        int day = DateUtils.getDay();
        return day == pair.getKey() && DateUtils.isTime(pair.getValue(),"HHmm");
    }

    public Pair<Integer,String> getExecutionTime(String str){
        String[] split = str.split("@");
        int day = 0;
        String dayStr = split[0].trim().substring(0,3).toLowerCase();
        switch (dayStr){
            case "sun":
                day = Calendar.SUNDAY;
                break;
            case "mon":
                day = Calendar.MONDAY;
                break;
            case "tue":
                day = Calendar.TUESDAY;
                break;
            case "wed":
                day = Calendar.WEDNESDAY;
                break;
            case "thu":
                day = Calendar.THURSDAY;
                break;
            case "fri":
                day = Calendar.FRIDAY;
                break;
            case "sat":
                day = Calendar.SATURDAY;
                break;
            case "day":
                day = -1;
                break;
        }

        return new Pair<>(day,split[1].trim());
    }

    class Pair<K,V>{
        private K key;
        private V val;
        public Pair(K key, V val){
            this.key = key;
            this.val = val;
        }

        public K getKey(){
            return key;
        }
        public V getValue(){
            return val;
        }
    }
}
