package io.actions.aliases;

import enums.Attributes;
import global.Utilities;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.rmi.CORBA.Util;
import java.util.Random;

public class OwnerAlias extends Alias  {
    public OwnerAlias(){
        super();
        getBody().getAttributes().add(Attributes.VANILLA);
        getBody().getIn().add("#owner");
        getBody().setName("Owner");
        getBody().setDescription("The owner of the channel which the triggering message was sent to.");
        getBody().getOut().add("*compiled function*");
        getBody().setAuthor("HypeBot");
    }

    public String apply(String str, GuildMessageReceivedEvent event){
        Random rand = new Random();
        for(String alias: getBody().getIn()){
            str = str.replaceAll(alias, Utilities.getOwner(event.getChannel()).getName());
        }
        return str;
    }
}
