package io.actions.aliases;

import enums.Attributes;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class ChannelAlias extends Alias  {
    public ChannelAlias(){
        super();
        getBody().getAttributes().add(Attributes.VANILLA);
        getBody().getIn().add("#channel");
        getBody().setName("Channel");
        getBody().setDescription("The channel which the triggering message was sent to.");
        getBody().getOut().add("*compiled function*");
        getBody().setAuthor("HypeBot");
    }

    public String apply(String str, GuildMessageReceivedEvent event){
        Random rand = new Random();
        for(String alias: getBody().getIn()){
            str = str.replaceAll(alias,event.getChannel().getName());
        }
        return str;
    }
}
