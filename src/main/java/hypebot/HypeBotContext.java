package hypebot;

import cron.CronJob;
import enums.Attributes;
import global.Utilities;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Random;

public class HypeBotContext {

    private ArrayList<AbstractMessageReceivedAction> commands = new ArrayList<>();
    private ArrayList<AbstractMessageReceivedAction> actions = new ArrayList<>();
    private ArrayList<CronJob> jobs = new ArrayList<>();
    private ArrayList<Alias> aliases = new ArrayList<>();

    public ArrayList<AbstractMessageReceivedAction> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<AbstractMessageReceivedAction> commands) {
        this.commands = commands;
    }

    public ArrayList<AbstractMessageReceivedAction> getActions() {
        return actions;
    }

    public void setActions(ArrayList<AbstractMessageReceivedAction> actions) {
        this.actions = actions;
    }

    public ArrayList<CronJob> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<CronJob> jobs) {
        this.jobs = jobs;
    }

    public ArrayList<Alias> getAliases() {
        return aliases;
    }

    public void setAliases(ArrayList<Alias> aliases) {
        this.aliases = aliases;
    }

    public boolean runCommands(GuildMessageReceivedEvent event){
        ArrayList<AbstractMessageReceivedAction> valid = run(event,commands,true);

        if(valid.size()>0){
            execute(valid);
            return true;
        }else {
            return false;
        }
    }

    public Alias getAlias(String name,Attributes... atts){
        for(Alias ar: getAliases()){
            if(ar.getBody().getName().equalsIgnoreCase(name)){
                boolean has = true;
                for(Attributes a: atts){
                    has = has && ar.getBody().getAttributes().contains(a);
                }
                if(has)
                    return ar;
            }
        }
        return null;
    }

    public AbstractMessageReceivedAction getAction(String name, Attributes... atts){
        for(AbstractMessageReceivedAction ar: getActions()){
            if(ar.getBody().getName().equalsIgnoreCase(name)){
                boolean has = true;
                for(Attributes a: atts){
                    has = has && ar.getBody().getAttributes().contains(a);
                }
                if(has)
                    return ar;
            }
        }
        return null;
    }

    public boolean runActions(GuildMessageReceivedEvent event){
        ArrayList<AbstractMessageReceivedAction> valid = run(event,actions,false);
        if(valid.size()>0){
            execute(valid);
            return true;
        }else{
            return false;
        }
    }

    private void execute(ArrayList<AbstractMessageReceivedAction> list){
        Random rand = new Random();
        AbstractMessageReceivedAction ar = list.get(rand.nextInt(list.size()));

        if(!ar.prebuild())return;
        if(!ar.build())return;
        if(!ar.execute())return;
    }

    private ArrayList<AbstractMessageReceivedAction> run(GuildMessageReceivedEvent event, ArrayList<AbstractMessageReceivedAction> list, boolean single){
        ArrayList<AbstractMessageReceivedAction> ret = new ArrayList<>();
        for(AbstractMessageReceivedAction ar: list){
            ar.setEvent(event);
            ar.setContent(Utilities.consolidateQuotes(event.getMessage().getContentRaw()));

            if(!ar.attemptToMatch())
                continue;

            ret.add(ar);

            if(single)
                return ret;
        }

        return ret;
    }
}
