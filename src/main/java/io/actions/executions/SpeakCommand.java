package io.actions.executions;

import global.App;
import io.structure.Body;

public class SpeakCommand extends Command {
    public SpeakCommand() {
        super();
        Body body = getBody();
        body.getIn().add("wake up");
        body.getIn().add("focus|pay attention");
        body.setName("CheckInCommand");
        setBody(body);
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
