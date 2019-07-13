package hypebot;

import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class HypeBot {

    private HashMap<String,HypeBotContext> contexts = new HashMap<>();
    private HypeBotContext globalContext = new HypeBotContext();

    public void respondToEvent(GuildMessageReceivedEvent event){

        String guildId = event.getGuild().getId();
        HypeBotContext hbc = contexts.get(guildId);

        if(hbc.runCommands(event))return;

        hbc.runActions(event);
    }

    public void addGlobalCommand(AbstractMessageReceivedAction ar){
        Iterator<String> iter = contexts.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();
            contexts.get(key).getCommands().add(ar);
        }
    }


    public void addGlobalAction(AbstractMessageReceivedAction ar){
        Iterator<String> iter = contexts.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();
            contexts.get(key).getActions().add(ar);
        }
    }

    public void addGlobalAlias(Alias ar){
        Iterator<String> iter = contexts.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();
            contexts.get(key).getAliases().add(ar);
        }
    }
}
