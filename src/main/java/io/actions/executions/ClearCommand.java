package io.actions.executions;

import enums.Attributes;
import global.App;
import global.MessageUtils;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;

public class ClearCommand extends Command {

    public ClearCommand(){
        super();
        getBody().setDescription("*"+ App.BOT_NAME+", clear <meme|action|response|alias|all>.*" +
                "\nRemoves all custom built actions of the specified type.");
        getBody().getIn().add("(clear|empty|reset|remove|delete).*?(?<type>(meme|action|response|alias|all|every))(\\w*$)");
        //getBody().getIn().add("(clear|empty|reset|remove|delete) (all|everything)$");
        getBody().setOut(MessageUtils.affirmative);
        getBody().setName("ClearCommand");
    }
    @Override
    public boolean execute(boolean response) {
        String type ="all";
        try{
            type = getMatcher().group("type");
        } catch (Exception e){}

        switch (type.toLowerCase()){
            case "meme":
                for(int i = 0; i < App.messageEvent.memeActions.size(); i++){
                    if(App.messageEvent.memeActions.get(i).getBody().getAttributes().contains(Attributes.CUSTOM)){
                        App.messageEvent.memeActions.remove(i);
                        i--;
                    }
                }
                App.saveMemes();
                break;
            case "action":
                for(int i = 0; i < App.messageEvent.performActions.size(); i++){
                    if(App.messageEvent.performActions.get(i).getBody().getAttributes().contains(Attributes.CUSTOM)){
                        App.messageEvent.performActions.remove(i);
                        i--;
                    }
                }
                App.saveActions();
                break;
            case "response":
                for(int i = 0; i < App.messageEvent.sendActions.size(); i++){
                    if(App.messageEvent.sendActions.get(i).getBody().getAttributes().contains(Attributes.CUSTOM)){
                        App.messageEvent.sendActions.remove(i);
                        i--;
                    }
                }
                App.saveResponses();
                break;
            case "alias":
                for(int i = 0; i < App.ALIASES.size(); i++){
                    if(App.ALIASES.get(i).getBody().getAttributes().contains(Attributes.CUSTOM)){
                        App.ALIASES.remove(i);
                        i--;
                    }
                }
                App.saveAliases();
                break;
            case "all":
                for(int i = 0; i < App.ALIASES.size(); i++){
                    if(App.ALIASES.get(i).getBody().getAttributes().contains(Attributes.CUSTOM)){
                        App.ALIASES.remove(i);
                        i--;
                    }
                }
                for(int i = 0; i < App.messageEvent.sendActions.size(); i++){
                    if(App.messageEvent.sendActions.get(i).getBody().getAttributes().contains(Attributes.CUSTOM)){
                        App.messageEvent.sendActions.remove(i);
                        i--;
                    }
                }
                for(int i = 0; i < App.messageEvent.performActions.size(); i++){
                    if(App.messageEvent.performActions.get(i).getBody().getAttributes().contains(Attributes.CUSTOM)){
                        App.messageEvent.performActions.remove(i);
                        i--;
                    }
                }
                for(int i = 0; i < App.messageEvent.memeActions.size(); i++){
                    if(App.messageEvent.memeActions.get(i).getBody().getAttributes().contains(Attributes.CUSTOM)){
                        App.messageEvent.memeActions.remove(i);
                        i--;
                    }
                }
                App.saveAliases();
                App.saveMemes();
                App.saveResponses();
                App.saveActions();
                break;


        }
        if(response)
            sendResponse();
        return true;
    }

    @Override
    public boolean build() {
        return true;
    }
}
