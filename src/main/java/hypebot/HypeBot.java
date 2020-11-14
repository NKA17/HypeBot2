package hypebot;

import com.sun.javafx.scene.control.skin.HyperlinkSkin;
import cron.CronJob;
import enums.Attributes;
import global.App;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.*;
import io.actions.executions.*;
import io.actions.memes.*;
import io.actions.sends.AvraeDiceRollResponse;
import io.actions.sends.ChuckNorrisResponse;
import io.actions.sends.OffendHypebot;
import io.actions.sends.PizzaPartyResponse;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class HypeBot {

    private HashMap<String,HypeBotContext> contexts = new HashMap<>();
    private HypeBotContext globalContext = new HypeBotContext();

    public void respondToEvent(GuildMessageReceivedEvent event){

        String guildId = event.getGuild().getId();
        HypeBotContext hbc = contexts.get(guildId);
        if(hbc == null){
            createHypeBotContext(event.getGuild().getId());
            hbc = contexts.get(event.getGuild().getId());
        }

        if(hbc.runCommands(event))return;

        if(hbc.runChain(event))return;

        hbc.runActions(event);
    }

    public HashMap<String, HypeBotContext> getContexts() {
        return contexts;
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

    public void saveResponses(){
        HypeBotStore hbs = new HypeBotStore();
        hbs.saveItems(getContextsAsArrayList(), Attributes.SEND);
    }

    public void saveActions(){
        HypeBotStore hbs = new HypeBotStore();
        hbs.saveItems(getContextsAsArrayList(),Attributes.PERFORM);
    }

    public void saveMemes(){
        HypeBotStore hbs = new HypeBotStore();
        hbs.saveItems(getContextsAsArrayList(),Attributes.MEME);
    }

    public void saveCronJobs(){
        HypeBotStore hbs = new HypeBotStore();
        hbs.saveCronJobs(getContextsAsArrayList());
    }

    public void saveAliases(){
        HypeBotStore hbs = new HypeBotStore();
        hbs.saveAliases(getContextsAsArrayList());
    }

    public HypeBotContext getContext(String id){
        return getContexts().get(id);
    }

    public HypeBotContext getContext(GuildMessageReceivedEvent event){
        return getContexts().get(event.getGuild().getId());
    }


    public void loadUserMade(){
        HypeBotStore hbs = new HypeBotStore();
        ArrayList<AbstractMessageReceivedAction> ars = new ArrayList<>();

        ars.addAll(hbs.loadActions());
        ars.addAll(hbs.loadMemes());
        ars.addAll(hbs.loadResponses());
        distributeAMRA(ars);

        ArrayList<Alias> als = hbs.loadAliases();
        distributeAliases(als);

        ArrayList<AbstractMessageReceivedAction> cjs = hbs.loadCronJobs();
        distributeCronJobs(cjs);
    }

    public void createHypeBotContext(String guildId){
        if(contexts.containsKey(guildId))
            return;
        else {
            HypeBotContext hbc = new HypeBotContext();
            loadGlobals(hbc);
            contexts.put(guildId, hbc);
        }
    }

    public void loadGlobals(HypeBotContext hbc){
        hbc.getCommands().add(new HypebotSays());
        hbc.getCommands().add(new SilenceCommand());
        hbc.getCommands().add(new SpeakCommand());
        hbc.getCommands().add(new CheckInCommand());
        hbc.getCommands().add(new HelpCommand());
        hbc.getCommands().add(new EditBodyCommand());
        hbc.getCommands().add(new CreateActionCommand());
        hbc.getCommands().add(new RemoveActionCommand());
        hbc.getCommands().add(new ShowBodiesCommand());
        hbc.getCommands().add(new HowToActionCommand());
        hbc.getCommands().add(new ClearCommand());
        hbc.getCommands().add(new IntroduceCommand());
        hbc.getCommands().add(new HowToEditCommand());
        hbc.getCommands().add(new HowToMemeCommand());
        hbc.getCommands().add(new ChangeLogCommand());
        hbc.getCommands().add(new CreateReminderCommand());
        hbc.getCommands().add(new RemoveReminderCommand());
        hbc.getCommands().add(new ShowImageCommand());
        hbc.getCommands().add(new ShowGuildInfoCommand());


        hbc.getActions().add(new GrannyMeme());
        hbc.getActions().add(new MockingSpongeBobMeme());
        hbc.getActions().add(new CoolsvilleB());
        hbc.getActions().add(new CoolsvilleA());
        hbc.getActions().add(new KKK());
        hbc.getActions().add(new PizzaPartyMeme());
        hbc.getActions().add(new ButterflyMeme());
        hbc.getActions().add(new IsNotAMeme());
        hbc.getActions().add(new ScienceBitchMeme());
        hbc.getActions().add(new SingleWordMeme());
        hbc.getActions().add(new AllOfChinaMeme());

        hbc.getActions().add((new PizzaPartyResponse()));
        hbc.getActions().add(new ChuckNorrisResponse());
        hbc.getActions().add(new AvraeDiceRollResponse());
        hbc.getActions().add(new OffendHypebot());

        hbc.getAliases().add(new AuthorAlias());
        hbc.getAliases().add(new NickNameAlias());
        hbc.getAliases().add(new ChannelAlias());
        hbc.getAliases().add(new GuildAlias());
        hbc.getAliases().add(new OwnerAlias());
        hbc.getAliases().add(new TimestampAlias());
        hbc.getAliases().add(new HolyRobinAlias());
        hbc.getAliases().add(new AuthPicUrlAlias());
        hbc.getAliases().add(new GetPicAlias());
        hbc.getAliases().add(new LoadImageAlias());
        hbc.getAliases().add(new BurnAfterReadingAlias());
    }
    public void loadGlobals(){
        for(HypeBotContext hbc: getContextsAsArrayList()){
            loadGlobals(hbc);
        }
    }

    private void distributeAMRA(ArrayList<AbstractMessageReceivedAction> list){
        for(AbstractMessageReceivedAction ar: list){
            String guildid = ar.getBody().getGuildId();
            if(contexts.containsKey(guildid)){
                contexts.get(guildid).getActions().add(ar);
            }else {
                HypeBotContext hbc = new HypeBotContext();
                hbc.getActions().add(ar);
                contexts.put(guildid,hbc);
            }
        }
    }

    private void distributeAliases(ArrayList<Alias> list){
        for(Alias ar: list){
            String guildid = ar.getBody().getGuildId();
            if(contexts.containsKey(guildid)){
                contexts.get(guildid).getAliases().add(ar);
            }else {
                HypeBotContext hbc = new HypeBotContext();
                hbc.getAliases().add(ar);
                contexts.put(guildid,hbc);
            }
        }

    }

    private void distributeCronJobs(ArrayList<AbstractMessageReceivedAction> list){
        for(AbstractMessageReceivedAction ar: list){
            String guildid = ar.getBody().getGuildId();
            if(contexts.containsKey(guildid)){
                contexts.get(guildid).getBotjobs().add(ar);
            }else {
                HypeBotContext hbc = new HypeBotContext();
                hbc.getBotjobs().add(ar);
                contexts.put(guildid,hbc);
            }
        }
    }


    public ArrayList<HypeBotContext> getContextsAsArrayList(){
        ArrayList<HypeBotContext> ret = new ArrayList<>();
        Iterator<String> iter = contexts.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();
            ret.add(contexts.get(key));
        }

        return ret;
    }
}
