package io.actions.aliases;

import enums.Attributes;
import io.structure.Body;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class GuildAlias extends Alias {

    public GuildAlias(){
        super();
        getBody().getAttributes().add(Attributes.VANILLA);
        getBody().getIn().add("#guild");
        getBody().setName("Guild");
        getBody().setDescription("The guild which the triggering message was sent to.");
        getBody().getOut().add("*compiled function*");
        getBody().setAuthor("HypeBot");
    }

    public String apply(String str, GuildMessageReceivedEvent event){
        Random rand = new Random();
        for(String alias: getBody().getIn()){
            str = str.replaceAll(alias,event.getGuild().getName());
        }
        return str;
    }
}
