package io.actions.aliases;

import enums.Attributes;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class AuthPicUrlAlias extends Alias {
    public AuthPicUrlAlias(){
        super();
        getBody().getAttributes().add(Attributes.VANILLA);
        getBody().getIn().add("#picauth");
        getBody().setName("Author");
        getBody().setDescription("The author of the triggering message.");
        getBody().getOut().add("*compiled function*");
        getBody().setAuthor("HypeBot");
    }

    public String apply(String str, GuildMessageReceivedEvent event){
        Random rand = new Random();
        for(String alias: getBody().getIn()){
            str = str.replaceAll(alias,event.getMessage().getAuthor().getAvatarUrl());
        }
        return str;
    }
}
