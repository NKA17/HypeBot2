package cron;

import global.DateUtils;
import io.MessageSender;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;

public class WeeklyReminder extends CronJob {

    private int day = 0;
    private String time;
    private String message;
    private GuildMessageReceivedEvent event;

    public GuildMessageReceivedEvent getEvent() {
        return event;
    }

    public void setEvent(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    public WeeklyReminder(){}

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean trigger() {
        return DateUtils.isDay(day) && DateUtils.isTime(""+time,"HHmm");
    }

    @Override
    public boolean action() {
        MessageSender ms = new MessageSender(event);
        ms.sendMessage(getGuildId(),getChannelId(),message,true);
        return true;
    }

    public JSONObject toJSON(){
        JSONObject json = super.toJSON();
        json.put("day",day);
        json.put("time",time);
        json.put("message",message);
        return json;
    }
}
