package io.actions.executions;

import global.App;
import global.MessageUtils;
import io.actions.memes.CoolsvilleA;

public class SilenceCommand extends Command {
    public SilenceCommand() {
        super();
        getBody().setName("Silence");
        getBody().getIn().add("chill|relax|sleep|silent|silence|quiet");
        getBody().setOut(MessageUtils.affirmative);
        getBody().setName("SilenceCommand");
    }


    @Override
    public boolean build() {
        return true;
    }

    @Override
    public boolean execute(boolean respond) {
        App.messageEvent.sendMessages = false;

        switch (getRandom().nextInt(2)) {
            case 0:
                if (respond)
                    sendResponse();
                break;
            case 1:
                CoolsvilleA cv = new CoolsvilleA();
                cv.setEvent(getEvent());
                cv.setContent(App.BOT_NAME+", chill");
                cv.attemptToMatch();
                cv.prebuild();
                cv.build();
                cv.execute();
        }

        return true;
    }
}
