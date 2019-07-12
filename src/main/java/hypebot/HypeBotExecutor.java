package hypebot;

import cron.CronJob;
import cron.CronMonitor;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;

import java.util.ArrayList;

public class HypeBotExecutor {

    private ArrayList<AbstractMessageReceivedAction> commands = new ArrayList<>();
    private ArrayList<AbstractMessageReceivedAction> actions = new ArrayList<>();
    private ArrayList<Alias> aliases = new ArrayList<>();
    private ArrayList<CronJob> jobs = new ArrayList<>();
}
