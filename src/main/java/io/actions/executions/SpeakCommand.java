package io.actions.executions;

import global.App;
import global.MessageUtils;
import global.Utilities;
import io.structure.Body;

public class SpeakCommand extends Command {
    public SpeakCommand() {
        super();
        getBody().setName("Speak");
        getBody().getIn().add("wake up");
        getBody().getIn().add("focus|pay attention|back to work");
        getBody().setName("CheckInCommand");
        getBody().getOut().add("I'm focused!");
        getBody().getOut().add("I'm awake!");
        getBody().getOut().add("Back in off the bench!");
        getBody().getOut().add("I missed you guys!");
        getBody().getOut().add("That was a nice break.");
        getBody().getOut().add("Back to work!");
        getBody().getOut().add("You got it, #auth!");
    }


    @Override
    public boolean build() {
        if(App.messageEvent.sendMessages)
            return false;

        if(getEvent().getAuthor().getId().equals(
                Utilities.getOwner(getEvent().getChannel()).getId())){
            return true;
        }else {
            sendResponse(MessageUtils.notOwnerFail);
            return false;
        }
    }

    @Override
    public boolean execute(boolean respond) {
        App.messageEvent.sendMessages = true;
        if(respond)
            sendResponse();

        return true;
    }
}
