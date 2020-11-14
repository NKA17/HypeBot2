package io.actions.aliases;

import enums.Attributes;
import global.App;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


public class BurnAfterReadingAlias extends Alias {
    public BurnAfterReadingAlias(){
        super();
        getBody().getAttributes().add(Attributes.VANILLA);
        getBody().getIn().add("#bar$|#bardr$");
        getBody().setName("BurnAfterReading");
        getBody().setDescription("Removes the message from channel after it is read. \n" +
                "#bardr - Burn After Reading, Don't Respond.");
        getBody().getOut().add("*compiled function*");
        getBody().setAuthor(App.BOT_NAME);
    }

    public String apply(String str, GuildMessageReceivedEvent event){

        for(String alias: getBody().getIn()){
            str = str.replaceAll(alias,"");
        }
        return str;
    }
}
