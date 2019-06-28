package io.actions.executions;

import global.App;
import global.MessageUtils;
import io.structure.Body;

public class SilenceCommand extends Command {
    public SilenceCommand() {
        super();
        Body body = getBody();
        body.getIn().add("chill|relax|sleep|silent|silence|quiet");
        body.setOut(MessageUtils.affirmative);
        body.setName("SilenceCommand");
        setBody(body);
    }


    @Override
    public boolean build() {
        return true;
    }

    @Override
    public boolean execute(boolean respond) {
        App.messageEvent.sendMessages = false;
        if(respond)
            sendResponse();

        return true;
    }
}
