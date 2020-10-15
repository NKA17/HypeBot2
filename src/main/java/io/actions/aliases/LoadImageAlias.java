package io.actions.aliases;

import apis.SplashImageApi;
import enums.Attributes;
import global.App;
import global.Defaults;
import io.actions.sends.BlankResponse;
import io.structure.MemeBody;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadImageAlias extends Alias {
    public LoadImageAlias(){
        super();
        getBody().getAttributes().add(Attributes.VANILLA);
        getBody().getIn().add("#loadimage\\(\\s*(?<name>\\S+?)\\s*\\)");
        getBody().setName("LoadImage");
        getBody().setDescription("Loads and sends an image from "+App.BOT_NAME+"'s local storage.");
        getBody().getOut().add("*compiled function*");
        getBody().setAuthor(App.BOT_NAME);
    }

    public String apply(String str, GuildMessageReceivedEvent event){

        for(String alias: getBody().getIn()){
            Matcher m = Pattern.compile(alias).matcher(str);
            if(m.find()){
                String name = m.group("name");

//
                App.findChannel(event.getGuild().getId(),event.getChannel().getId())
                        .sendFile(new File(App.RESOURCES_PATH+name), App.tempFileName).queue();
            }
            str = str.replaceAll(alias,"");
        }
        return str;
    }
}
