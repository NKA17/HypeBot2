package io.actions.executions;

import cron.DayChangeAction;
import cron.Timeable;
import global.App;
import global.MessageUtils;
import global.Utilities;
import io.actions.memes.BlankMeme;
import io.actions.memes.CoolsvilleA;
import io.actions.memes.CoolsvilleB;
import io.actions.memes.Meme;

public class SilenceCommand extends Command {
    public SilenceCommand() {
        super();
        getBody().setName("Silence");
        getBody().getIn().add("take a break|chill|relax|sleep|silent|silence|quiet|shut the fuck up");
        getBody().setOut(MessageUtils.affirmative);
        getBody().setName("SilenceCommand");
    }


    @Override
    public boolean build() {
        if(!App.messageEvent.sendMessages)
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
        App.messageEvent.sendMessages = false;

        if(getContent().toLowerCase().contains("shut the fuck up")){
            sendResponse(
                    "My bad :(",
                    "You're right. I'll shut up.",
                    "wow.",
                    "Ok, no. You chill, #auth.",
                    "Fuck you, #auth!",
                    "https://media.tenor.co/images/fb8badab7ab4740e5ecfa250917867bb/raw",
                    "https://i.pinimg.com/736x/35/6a/09/356a09cdbd08a2da289e7e1fdd2ae995.jpg"
            );
        }else {


            Meme cv;
            if(getRandom().nextInt(100)%2==0){
                cv = new CoolsvilleA();
            }else {
                cv = new CoolsvilleB();
            }
            cv.setEvent(getEvent());
            cv.setContent(App.BOT_NAME + ", chill");
            cv.attemptToMatch();
            cv.prebuild();
            cv.build();
            cv.execute();

        }

        if(getContent().toLowerCase().contains("take a break")||
                getContent().toLowerCase().contains("a while")){

            sendResponse("See you all tomorrow!","Just a short break, then.","You got it, #auth. See you tomorrow!",
                    "I'll be back tomorrow, then. Bye, #guild!");

            Timeable t = new Timeable() {
                @Override
                public boolean execute() {

                    SpeakCommand sc = new SpeakCommand();
                    sc.setEvent(getEvent());
                    sc.setContent("HypeBot, back to work");
                    sc.getBody().setOut("I'm back from break!","Feels good to be back!","That was a nice nap!",
                            "Okay, I'm ready to get back to work!");
                    sc.attemptToMatch();
                    if(!sc.prebuild())
                        return false;
                    if(!sc.build())
                        return false;


                    return sc.execute();
                }

                public boolean trigger(){
                    return true;
                }
            };
            DayChangeAction dca = new DayChangeAction(t);
            Thread th = new Thread(dca);
            th.start();
        }
        return true;
    }
}
