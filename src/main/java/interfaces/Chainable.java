package interfaces;


import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Chainable {
    public boolean match(String str);
    public boolean execute(GuildMessageReceivedEvent event);
    public boolean expire();

}
