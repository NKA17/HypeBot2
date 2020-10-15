package io.actions.executions;

import com.sun.deploy.ui.UITextArea;
import enums.Attributes;
import global.App;
import global.MessageUtils;
import global.Utilities;
import hypebot.HypeBotContext;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;
import io.structure.Body;

public class RemoveActionCommand extends Command {

    public RemoveActionCommand(){
        super();

        getBody().setName("RemoveCommand");
        getBody().setDescription("*"+App.BOT_NAME+", remove <meme|action|alias|response> \"<name>\"*\n" +
                "Removes the specified action");

        getBody().getIn().add("(remove|delete|discard)\\s+(?<type>alias|response|action)?\\s*\"(?<name>[\\s\\S]+?)\"");
        getBody().setOut(MessageUtils.affirmative);

    }
    @Override
    public boolean execute(boolean response) {
        String type = getMatcher().group("type");
        String name = getMatcher().group("name");
        if(type!=null)
            type = type.toLowerCase();
        else
            type = "null";

        boolean worked = false;
        HypeBotContext hbc;
        AbstractMessageReceivedAction ar;
        switch (type){
            case "alias":
                hbc = App.HYPEBOT.getContext(getEvent());
                Alias a = hbc.getAlias(name,Attributes.CUSTOM);
                if(a==null || !canDelete(a.getBody()))return false;
                if(hbc.getAliases().remove(a)) {
                    worked = true;
                    App.HYPEBOT.saveAliases();
                }
                break;
            case "response":
                hbc = App.HYPEBOT.getContext(getEvent());
                ar = hbc.getAction(name,Attributes.SEND,Attributes.CUSTOM);
                if(ar==null || !canDelete(ar.getBody()))return false;
                if(hbc.getActions().remove(ar)) {
                    worked = true;
                    App.HYPEBOT.saveResponses();
                }
                break;
            case "action":
                hbc = App.HYPEBOT.getContext(getEvent());
                ar = hbc.getAction(name,Attributes.PERFORM,Attributes.CUSTOM);
                if(ar==null || !canDelete(ar.getBody()))return false;
                if(hbc.getActions().remove(ar)) {
                    worked = true;
                    App.HYPEBOT.saveActions();
                }
                break;
            case "Meme":
                hbc = App.HYPEBOT.getContext(getEvent());
                ar = hbc.getAction(name,Attributes.MEME,Attributes.CUSTOM);
                if(ar==null || !canDelete(ar.getBody()))return false;
                if(hbc.getActions().remove(ar)) {
                    worked = true;
                    App.HYPEBOT.saveMemes();
                }
            case "null":
                hbc = App.HYPEBOT.getContext(getEvent());
                ar = hbc.getAction(name,Attributes.CUSTOM);
                if(ar!=null) {
                    worked = hbc.getActions().remove(ar);
                    App.HYPEBOT.saveMemes();
                    App.HYPEBOT.saveActions();
                    App.HYPEBOT.saveResponses();
                }else{
                    Alias ab = hbc.getAlias(name,Attributes.CUSTOM);
                    worked = hbc.getAliases().remove(ab);
                    App.HYPEBOT.saveAliases();
                }
                break;
        }

        if(worked) {
            if (response) {
                sendResponse();
            }
            return true;
        }else{
            if (response) {
                String mess = MessageUtils.chooseString(MessageUtils.notOnFile);
                mess = mess.replaceAll("#name",name);
                sendResponse(mess);
            }
            return false;
        }
    }

    private boolean canDelete(Body ar){

        return ar.getAuthorId().equalsIgnoreCase(getEvent().getAuthor().getId()) ||
                getEvent().getAuthor().getId().equalsIgnoreCase(Utilities.getOwner(getEvent().getChannel()).getId());
    }
    @Override
    public boolean build() {
        return true;
    }
}
