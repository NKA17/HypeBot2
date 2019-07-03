package io.actions.executions;

import global.App;
import io.structure.Body;

public class SpeakCommand extends Command {
    public SpeakCommand() {
        super();
        getBody().setName("Speak");
        getBody().getIn().add("wake up");
        getBody().getIn().add("focus|pay attention");
        getBody().setName("CheckInCommand");
    }


    @Override
    public boolean build() {
        return true;
    }

    @Override
    public boolean execute(boolean respond) {
        App.messageEvent.sendMessages = true;
        if(respond)
            sendResponse();

        return true;
    }
}
