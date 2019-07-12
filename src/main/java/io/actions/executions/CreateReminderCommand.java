package io.actions.executions;

import cron.CronJob;
import cron.WeeklyReminder;
import global.App;
import global.DateUtils;
import global.MessageUtils;
import io.MessageSender;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateReminderCommand extends Command {

    public CreateReminderCommand(){
        super();
        getBody().setName("ReminderCommand");
        getBody().getIn().add("(create|make|set).*?reminder.*?(?<freq>sun|mon|tues|wed|thur|fri|sat|day)\\w*.*?(?<time>\\d{4})");
        getBody().getIn().add("(create|make|set).*?reminder.*?(?<time>\\d{4}).*?(?<freq>sun|mon|tues|wed|thur|fri|sat|day)\\w*");
    }

    @Override
    public boolean execute(boolean response) {

        int day = (int)(getState().get("freq"));
        String time = getState().get("time").toString();
        String phrase = getState().get("phrase").toString();

        WeeklyReminder cj = new WeeklyReminder();
        cj.setGuildId(getEvent().getGuild().getId());
        cj.setChannelId(getEvent().getChannel().getId());
        cj.setName("Reminder");
        cj.setEvent(getEvent());
        cj.setDay(day);
        cj.setTime(time);
        cj.setMessage(phrase);

        App.CRON_MONITOR.addJob(cj);

        App.saveReminders();
        sendResponse(MessageUtils.affirmative);
        return true;
    }

    @Override
    public boolean build() {
        int day = 0;
        String f = getMatcher().group("freq");
        switch (getMatcher().group("freq").toLowerCase()){
            case "sun":
                day = Calendar.SUNDAY;
                break;
            case "mon":
                day = Calendar.MONDAY;
                break;
            case "tues":
                day = Calendar.TUESDAY;
                break;
            case "wed":
                day = Calendar.WEDNESDAY;
                break;
            case "thur":
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

        getState().put("freq",day);
        getState().put("time",getMatcher().group("time"));


        Matcher m = Pattern.compile("\"([\\s\\S]*?)\"").matcher(getContent());
        if(m.find())
            getState().put("phrase",m.group(1));
        return true;
    }
}
