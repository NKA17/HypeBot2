package io.actions.actions;

import enums.Attributes;
import global.App;
import io.actions.AbstractMessageReceivedAction;
import io.actions.executions.Command;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class BlankAction extends AbstractMessageReceivedAction {
    public BlankAction(){
        super();
        getBody().getAttributes().add(Attributes.PERFORM);
    }

    @Override
    public boolean build() {
        return true;
    }

    @Override
    public boolean execute() {
        try{
            for(String step: getBody().getOut()) {
                if (step.matches("(?i)^send:[\\s\\S]*")) {
                    sendResponse(getBody().getGuildId(),getBody().getChannelId(),step.replaceAll("(?i)^send:", ""));
                } else if (step.matches("(?i)^(execute|exec|perform):[\\s\\S]*")) {
                    execAction(step.replaceAll("(?i)^(execute|exec|perform):", ""), getEvent());
                }else{
                    //Send is the default
                    sendResponse(getBody().getGuildId(),getBody().getChannelId(),step);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


    private boolean execAction(String exec, GuildMessageReceivedEvent event){
        for(AbstractMessageReceivedAction ar: App.messageEvent.exeActions){
            ar.setContent(App.BOT_NAME+", "+exec);
            ar.setEvent(event);
            ar.getState().put("tempPermission",getBody().getAuthorId());
            boolean matched = ar.attemptToMatch();
            if(!matched)
                continue;

            boolean prebuilt = ar.prebuild();
            boolean built = ar.build();
            if(!built)
                continue;

            boolean execed = ((Command)ar).execute(false);

            return execed;
        }
        return false;
    }
}
