package cron;

import global.DateUtils;
import io.MessageSender;
import io.actions.actions.BlankAction;
import io.structure.Body;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

public class WeeklyReminder extends CronJob {

    private ArrayList<Integer> days = new ArrayList<>();
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

    public ArrayList<Integer> getDays() {
        return days;
    }

    public void setDays(ArrayList<Integer> day) {
        this.days = days;
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
        int day = DateUtils.getDay();
        return days.contains(day) && DateUtils.isTime(""+time,"HHmm");
    }

    @Override
    public boolean action() {

        BlankAction ba = new BlankAction();
        Body bo = new Body();
        bo.setOut(message);
        ba.setEvent(getEvent());
        bo.setGuildId(getGuildId());
        bo.setChannelId(getChannelId());
        ba.setBody(bo);
        ba.execute();
        return true;
    }

    public JSONObject toJSON(){
        JSONObject json = super.toJSON();
        json.put("days",days);
        json.put("time",time);
        json.put("message",message);
        return json;
    }
}
