package io.actions.executions;

import enums.Attributes;
import global.App;
import global.MessageUtils;
import io.actions.AbstractMessageReceivedAction;

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
        switch (type){
            case "alias":
                if(App.messageEvent.removeAlias(name)) {
                    worked = true;
                    App.saveAliases();
                }
                break;
            case "response":
                AbstractMessageReceivedAction ar = App.messageEvent.getAction(App.messageEvent.sendActions,name);
                if(ar==null || !canDelete(ar))return false;
                if(App.messageEvent.removeSendAction(name)) {
                    worked = true;
                    App.saveResponses();
                }
                break;
            case "action":
                if(App.messageEvent.removeActionAction(name)) {
                    worked = true;
                    App.saveActions();
                }
                break;
            case "Meme":
                if(App.messageEvent.removeMemeAction(name)){
                    worked = true;
                    App.saveMemes();
                }
            case "null":
                if(App.messageEvent.removeAny(name)) {
                    worked = true;
                    App.saveMemes();
                    App.saveActions();
                    App.saveAliases();
                    App.saveResponses();
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

    private boolean canDelete(AbstractMessageReceivedAction ar){
        boolean b = App.TEST_MODE;
        boolean c = getEvent().getGuild().getId().equalsIgnoreCase(ar.getBody().getGuildId());
        boolean d = ar.getBody().isGlobal();
        boolean e = ar.getBody().getChannelId().equalsIgnoreCase(getEvent().getChannel().getId());
        if (!b) {
            if (!c) {
                return false;
            } else {
                if (!d && !e) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public boolean build() {
        return true;
    }
}
