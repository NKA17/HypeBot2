package io.actions.executions;

import global.App;
import global.MessageUtils;

public class RemoveActionCommand extends Command {

    public RemoveActionCommand(){
        super();

        getBody().setName("RemoveCommand");

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

    @Override
    public boolean build() {
        return true;
    }
}
