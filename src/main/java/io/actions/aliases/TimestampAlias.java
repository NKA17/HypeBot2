package io.actions.aliases;

import enums.Attributes;
import global.Utilities;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class TimestampAlias extends Alias {
    public TimestampAlias(){
        super();
        getBody().getAttributes().add(Attributes.VANILLA);
        getBody().getIn().add("#timestamp");
        getBody().setName("Timestamp");
        getBody().setDescription("The time at which the triggering message was sent.");
        getBody().getOut().add("*compiled function*");
        getBody().setAuthor("HypeBot");
    }

    public String apply(String str, GuildMessageReceivedEvent event){
        Random rand = new Random();
        for(String alias: getBody().getIn()){
            str = str.replaceAll(alias, Utilities.makeTimeReadable(event));
        }
        return str;
    }
}
