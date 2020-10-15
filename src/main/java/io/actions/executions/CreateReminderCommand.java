package io.actions.executions;

import cron.CronJob;
import cron.HypeBotCronJob;
import cron.WeeklyReminder;
import enums.Attributes;
import global.App;
import global.DateUtils;
import global.MessageUtils;
import hypebot.HypeBotContext;
import io.MessageSender;
import io.structure.Body;
import javafx.util.Pair;
import tools.MessageParser;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateReminderCommand extends Command {

    public CreateReminderCommand(){
        super();
        getBody().setName("ReminderCommand");
        //getBody().getIn().add("(create|make|set).*?reminder.*?(?<freq>sun|mon|tues|wed|thur|fri|sat|day)\\w*.*?(?<time>\\d{4})");
        //getBody().getIn().add("(create|make|set).*?reminder.*?(?<time>\\d{4}).*?(?<freq>sun|mon|tues|wed|thur|fri|sat|day)\\w*");
        getBody().getIn().add("(create|make|set).*?reminder[\\s\\S]+?name(\\s*=\\s*)?\"(?<name>[\\s\\S]+?)\"");
    }

    private Body body;
    @Override
    public boolean build() {
        MessageParser mp = new MessageParser(getContent());

        body = mp.getBody();
        body.setAuthor(getEvent().getAuthor().getName());
        body.setAuthorId(getEvent().getAuthor().getId());
        body.setGuildId(getEvent().getGuild().getId());
        body.setChannelId(getEvent().getChannel().getId());
        body.getAttributes().add(Attributes.CUSTOM);
        body.getAttributes().add(Attributes.ACTION);
        body.getAttributes().add(Attributes.CRON);
        return true;
    }

    @Override
    public boolean execute(boolean response) {

        HypeBotContext hbc = App.HYPEBOT.getContext(getEvent());
        HypeBotCronJob hbcj = new HypeBotCronJob();
        hbcj.setBody(body);
        hbc.getBotjobs().add(hbcj);
        App.HYPEBOT.saveCronJobs();
        sendResponse(MessageUtils.affirmative);
        return true;
    }


//
//    @Override
//    public boolean execute(boolean response) {
//
//        int day = (int)(getState().get("freq"));
//        String time = getState().get("time").toString();
//        String phrase = getState().get("phrase").toString();
//
//        WeeklyReminder cj = new WeeklyReminder();
//        cj.setGuildId(getEvent().getGuild().getId());
//        cj.setChannelId(getEvent().getChannel().getId());
//        cj.setName("Reminder");
//        cj.setEvent(getEvent());
//        cj.setDay(day);
//        cj.setTime(time);
//        cj.setMessage(phrase);
//
//        HypeBotContext hbc = App.HYPEBOT.getContexts().get(getEvent().getGuild().getId());
//        hbc.getJobs().add(cj);
//
//        App.HYPEBOT.saveCronJobs();
//        sendResponse(MessageUtils.affirmative);
//        return true;
//    }
//
//    @Override
//    public boolean build() {
//        int day = 0;
//        String f = getMatcher().group("freq");
//        switch (getMatcher().group("freq").toLowerCase()){
//            case "sun":
//                day = Calendar.SUNDAY;
//                break;
//            case "mon":
//                day = Calendar.MONDAY;
//                break;
//            case "tues":
//                day = Calendar.TUESDAY;
//                break;
//            case "wed":
//                day = Calendar.WEDNESDAY;
//                break;
//            case "thur":
//                day = Calendar.THURSDAY;
//                break;
//            case "fri":
//                day = Calendar.FRIDAY;
//                break;
//            case "sat":
//                day = Calendar.SATURDAY;
//                break;
//            case "day":
//                day = -1;
//                break;
//        }
//
//        getState().put("freq",day);
//        getState().put("time",getMatcher().group("time"));
//
//
//        Matcher m = Pattern.compile("\"([\\s\\S]*?)\"").matcher(getContent());
//        if(m.find())
//            getState().put("phrase",m.group(1));
//        return true;
//    }
//
//    public Pair<Integer,String> getExecutionTime(String str){
//        String[] split = str.split("@");
//        int day = 0;
//        switch (split[0].trim()){
//            case "sun":
//                day = Calendar.SUNDAY;
//                break;
//            case "mon":
//                day = Calendar.MONDAY;
//                break;
//            case "tues":
//                day = Calendar.TUESDAY;
//                break;
//            case "wed":
//                day = Calendar.WEDNESDAY;
//                break;
//            case "thur":
//                day = Calendar.THURSDAY;
//                break;
//            case "fri":
//                day = Calendar.FRIDAY;
//                break;
//            case "sat":
//                day = Calendar.SATURDAY;
//                break;
//            case "day":
//                day = -1;
//                break;
//        }
//    }
}
