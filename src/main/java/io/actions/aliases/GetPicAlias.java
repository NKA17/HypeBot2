package io.actions.aliases;

import apis.SplashImageApi;
import enums.Attributes;
import global.App;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetPicAlias extends Alias {
    public GetPicAlias(){
        super();
        getBody().getAttributes().add(Attributes.VANILLA);
        getBody().getIn().add("#getpic\\(\\s*(?<search>\\w+)\\s*\\)");
        getBody().setName("GetPic");
        getBody().setDescription("Searches for an image based on a single word within the parenthesis. The API does " +
                "not accept more than one word. So don't try it.");
        getBody().getOut().add("*compiled function*");
        getBody().setAuthor(App.BOT_NAME);
    }

    public String apply(String str, GuildMessageReceivedEvent event){

        for(String alias: getBody().getIn()){
            Matcher m = Pattern.compile(alias).matcher(str);
            if(m.find()){
                String picurl = SplashImageApi.getImage(m.group("search"));
                str = str.replaceAll(alias,picurl);
            }
        }
        return str;
    }
}
