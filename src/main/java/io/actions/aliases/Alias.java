package io.actions.aliases;

import enums.Attributes;
import io.actions.AbstractMessageReceivedAction;
import io.structure.Body;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class Alias {

    private Body body;

    public Alias(){
        body = new Body();
        body.getAttributes().add(Attributes.ALIAS);
        body.getAttributes().add(Attributes.ACTION);
        setBody(body);
    }

    public String apply(String str, GuildMessageReceivedEvent event){
        Random rand = new Random();
        for(String alias: body.getIn()){
            str = str.replaceAll(alias,body.getOut().get(rand.nextInt(body.getOut().size())));
        }
        return str;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
