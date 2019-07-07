package events;

import com.google.gson.Gson;
import enums.Attributes;
import global.App;
import global.Defaults;
import global.MessageUtils;
import global.Utilities;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;
import io.structure.Body;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MessageEvent extends ListenerAdapter {

    public ArrayList<AbstractMessageReceivedAction> sendActions = new ArrayList<>();
    public ArrayList<AbstractMessageReceivedAction> performActions = new ArrayList<>();
    public ArrayList<AbstractMessageReceivedAction> exeActions = new ArrayList<>();
    public ArrayList<AbstractMessageReceivedAction> memeActions = new ArrayList<>();
    public ArrayList<ArrayList<AbstractMessageReceivedAction>> allActions = new ArrayList<>();
    public boolean sendMessages = true;

    public MessageEvent(){
        allActions.add(exeActions);
        allActions.add(sendActions);
        allActions.add(memeActions);
        allActions.add(performActions);
    }

    private boolean commandIssued = false;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        if(event.getAuthor().getId().equalsIgnoreCase(App.BOT_ID))
            return;


        if(event.getGuild().getId().equalsIgnoreCase("524035064523259924"))
            return;
        commandIssued = false;

        ArrayList<AbstractMessageReceivedAction> all = new ArrayList<>();
        for(ArrayList<AbstractMessageReceivedAction> list: allActions){
            all.addAll(list);
        }

        runList(all,event);

    }

    public void runList(ArrayList<AbstractMessageReceivedAction> list,GuildMessageReceivedEvent event){
        ArrayList<AbstractMessageReceivedAction> valid = new ArrayList<>();
        for(AbstractMessageReceivedAction action: list){

            //build action
            action.setContent(Utilities.consolidateQuotes(event.getMessage().getContentRaw()));
            action.setEvent(event);
            action.setState(new HashMap<>());
            boolean matchSuccess = action.attemptToMatch();

            if(matchSuccess){
                valid.add(action);
                if(action.getBody().getAttributes().contains(Attributes.EXECUTE))
                    break;
            }
        }

        AbstractMessageReceivedAction chosen = chooseEvent(valid);
        if(chosen==null)
            return;

        if(!chosen.getBody().getAttributes().contains(Attributes.EXECUTE) && !sendMessages)
            return;

        //prebuild
        boolean prebuilt = chosen.prebuild();
        if(!prebuilt)
            return;

        //attempt to build
        chosen.setEmbed(Defaults.getEmbedBuilder());
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

    private AbstractMessageReceivedAction getRandom(ArrayList<AbstractMessageReceivedAction> list){
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    private AbstractMessageReceivedAction chooseEvent(ArrayList<AbstractMessageReceivedAction> list){
        Random rand = new Random();
        while(list.size() > 0){
            AbstractMessageReceivedAction ar = getRandom(list);
            if(ar.happens()){
                return ar;
            }else{
                list.remove(ar);
            }
        }
        return  null;
    }

    public boolean removeSendAction(String name){
        return removeFromList(sendActions,name);
    }
    public boolean removeActionAction(String name){
        return removeFromList(performActions, name);
    }
    public boolean removeMemeAction(String name){
        return removeFromList(memeActions,name);
    }
    public boolean removeAny(String name){
        boolean y;
        y = removeSendAction(name);
        if(y)return true;

        y = removeActionAction(name);
        if(y)return true;

        y = removeMemeAction(name);
        if(y)return true;

        y = removeAlias(name);
        if(y)return true;

        return false;
    }
    public boolean removeAlias(String name){
        Alias target = null;
        for(Alias ar: App.ALIASES){
            if(ar.getBody().getName().equalsIgnoreCase(name)){
                target = ar;
                break;
            }
        }
        if(target!=null){
            App.ALIASES.remove(target);
            return true;
        }else {
            return false;
        }
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




    public Body getSendAction(String name){
        return getFromList(sendActions,name);
    }
    public Body getActionAction(String name){
        return getFromList(performActions, name);
    }
    public Body getMemeAction(String name){
        return getFromList(memeActions,name);
    }
    public Body getAny(String name){
        Body y;
        y = getSendAction(name);
        if(y!=null)return y;

        y = getActionAction(name);
        if(y!=null)return y;

        y = getMemeAction(name);
        if(y!=null)return y;

        y = getAlias(name);
        if(y!=null)return y;

        return null;
    }
    public Body getAlias(String name){
        Alias target = null;
        for(Alias ar: App.ALIASES){
            if(ar.getBody().getName().equalsIgnoreCase(name)){
                return ar.getBody();
            }
        }

        return null;
    }
    public Body getFromList(ArrayList<AbstractMessageReceivedAction> list,String name){

        AbstractMessageReceivedAction target = null;
        for(AbstractMessageReceivedAction ar: list){
            if(ar.getBody().getName().equalsIgnoreCase(name)){
                return ar.getBody();
            }
        }
        return null;
    }
}
