package hypebot;

import cron.CronJob;
import cron.CronMonitor;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;

public class HypeBotExecutor {

    private ArrayList<AbstractMessageReceivedAction> commands = new ArrayList<>();
    private ArrayList<AbstractMessageReceivedAction> actions = new ArrayList<>();
    private ArrayList<Alias> aliases = new ArrayList<>();
    private ArrayList<CronJob> jobs = new ArrayList<>();

    public void execute(GuildMessageReceivedEvent event){
        for(AbstractMessageReceivedAction command: commands){

        }
    }

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

    public ArrayList<Alias> getAliases() {
        return aliases;
    }

    public void setAliases(ArrayList<Alias> aliases) {
        this.aliases = aliases;
    }

    public ArrayList<CronJob> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<CronJob> jobs) {
        this.jobs = jobs;
    }
}
