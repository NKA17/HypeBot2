package events;

import com.google.gson.Gson;
import enums.Attributes;
import global.App;
import global.Defaults;
import global.MessageUtils;
import global.Utilities;
import io.MessageSender;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;
import io.actions.executions.CheckInCommand;
import io.actions.executions.IntroduceCommand;
import io.actions.memes.Meme;
import io.actions.memes.NickNameMeme;
import io.structure.Body;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.user.update.UserUpdateNameEvent;
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

    public void onGuildMemberNickChange(GuildMemberNickChangeEvent event){
        String guildId = event.getGuild().getId();
        String channelId = App.findPrimaryChannelForGuild(guildId).getId();

        Meme nicknameMeme = new NickNameMeme();
        nicknameMeme.getBody().setAuthorId(event.getUser().getId());
        nicknameMeme.getBody().setGuildId(guildId);
        nicknameMeme.getBody().setChannelId(channelId);
        nicknameMeme.build();
        nicknameMeme.execute();
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        if(!App.BOT_ID.equalsIgnoreCase(event.getAuthor().getId())) {
            App.HYPEBOT.respondToEvent(event);
            if(event.getMessage().getContentRaw().toLowerCase().matches("[\\s\\S]*?(#bar|#bardr)$")){
                event.getChannel().purgeMessages(event.getMessage());
            }
        }
    }

    public void onGuildJoin(GuildJoinEvent event){
        App.HYPEBOT.createHypeBotContext(event.getGuild().getId());
        IntroduceCommand ic = new IntroduceCommand();
        ic.setEvent(null);
        ic.build();
        EmbedBuilder eb = ic.getEmbed();
        event.getGuild().getTextChannels().get(0).sendMessage(eb.build()).queue();
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

    public AbstractMessageReceivedAction getAction(ArrayList<AbstractMessageReceivedAction> list, String name){
        for(AbstractMessageReceivedAction ar: list){
            if(ar.getBody().getName().equalsIgnoreCase(name))
                return ar;
        }
        return null;
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
