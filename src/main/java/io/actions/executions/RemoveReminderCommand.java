package io.actions.executions;

import global.App;
import global.MessageUtils;

public class RemoveReminderCommand extends Command {

    public RemoveReminderCommand(){
        super();

        getBody().setName("RemoveReminder");
        getBody().getIn().add("(remove|delete) reminder \"(?<name>.*?)\"");
    }

    @Override
    public boolean execute(boolean response) {
        String name = getMatcher().group("name");
        if(App.CRON_MONITOR.removeJob(name)){
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
