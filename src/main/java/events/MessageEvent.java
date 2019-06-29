package events;

import enums.Attributes;
import global.App;
import io.actions.AbstractMessageReceivedAction;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MessageEvent extends ListenerAdapter {

    public ArrayList<AbstractMessageReceivedAction> sendActions = new ArrayList<>();
    public ArrayList<AbstractMessageReceivedAction> exeActions = new ArrayList<>();
    public ArrayList<AbstractMessageReceivedAction> memeActions = new ArrayList<>();
    public ArrayList<ArrayList<AbstractMessageReceivedAction>> allActions = new ArrayList<>();
    public boolean sendMessages = true;

    public MessageEvent(){
        allActions.add(exeActions);
        allActions.add(sendActions);
        allActions.add(memeActions);
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        if(event.getAuthor().getId().equalsIgnoreCase(App.BOT_ID))
            return;

        for(ArrayList<AbstractMessageReceivedAction> actionList: allActions){
            runList(actionList,event);
        }
    }

    public void runList(ArrayList<AbstractMessageReceivedAction> list,GuildMessageReceivedEvent event){
        ArrayList<AbstractMessageReceivedAction> valid = new ArrayList<>();
        for(AbstractMessageReceivedAction action: list){

            //build action
            action.setContent(event.getMessage().getContentRaw());
            action.setEvent(event);
            action.setState(new HashMap<>());
            boolean matchSuccess = action.attemptToMatch();

            if(matchSuccess){
                valid.add(action);
                if(action.getBody().getAttributes().contains(Attributes.EXECUTE))
                    break;
            }
        }

        if(valid.size()>0) {
            AbstractMessageReceivedAction chosen = chooseEvent(valid);

            //attempt to build
            chosen.setEvent(event);
            chosen.setContent(event.getMessage().getContentRaw());
            boolean buildSuccess = chosen.build();
            if (!buildSuccess)
                return;

            //attempt to execute the action
            boolean executeSuccess = chosen.execute();

            //reset the action
            //chosen.purge();
        }
    }

    private AbstractMessageReceivedAction chooseEvent(ArrayList<AbstractMessageReceivedAction> list){
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public boolean removeSendAction(String name){
        return removeFromList(sendActions,name);
    }
    public boolean removeFromList(ArrayList<AbstractMessageReceivedAction> list,String name){

        AbstractMessageReceivedAction target = null;
        for(AbstractMessageReceivedAction ar: list){
            if(ar.getBody().getName().equalsIgnoreCase(name)){
                target = ar;
                break;
            }
        }
        if(target!=null){
            list.remove(target);
            return true;
        }else {
            return false;
        }
    }
}
