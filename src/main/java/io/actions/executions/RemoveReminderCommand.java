package io.actions.executions;

import cron.CronJob;
import global.App;
import global.MessageUtils;
import hypebot.HypeBotContext;
import io.actions.AbstractMessageReceivedAction;

public class RemoveReminderCommand extends Command {

    public RemoveReminderCommand(){
        super();

        getBody().setName("RemoveReminder");
        getBody().getIn().add("(remove|delete) (reminder|job) \"(?<name>.*?)\"");
    }

    @Override
    public boolean execute(boolean response) {
        String name = getMatcher().group("name");
        HypeBotContext hbc = App.HYPEBOT.getContext(getEvent());
        AbstractMessageReceivedAction job = hbc.getBotJob(name);
        if(job!=null){
            hbc.getBotjobs().remove(job);
            sendResponse(MessageUtils.affirmative);
            App.HYPEBOT.saveCronJobs();
        }else {
            String mess = MessageUtils.chooseString(MessageUtils.notOnFile);
            mess = mess.replaceAll("#name",name);
            sendResponse(mess);
        }
        return true;
    }

    @Override
    public boolean build() {
        return true;
    }
}
