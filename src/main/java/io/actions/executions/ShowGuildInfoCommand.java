package io.actions.executions;

import global.App;
import global.Defaults;
import global.MessageUtils;
import net.dv8tion.jda.core.entities.TextChannel;

public class ShowGuildInfoCommand extends Command {
    public ShowGuildInfoCommand(){
        super();

        getBody().getIn().add("show guild info|describe.{0,15}?guild");
        getBody().setName("ShowGuildInfo");
        getBody().setDescription("Shows the channels and their IDs.");
    }

    @Override
    public boolean build() {
        setEmbed(Defaults.getEmbedBuilder());
        getEmbed().setTitle(getEvent().getGuild().getName());
        getEmbed().setImage(getEvent().getGuild().getIconUrl());
        getEmbed().setDescription("**ID** - "+getEvent().getGuild().getId()+
                "\n**Owner** - "+getEvent().getGuild().getOwner().getUser().getName());

        for(TextChannel tc: getEvent().getGuild().getTextChannels()){
            buildChannelInfo(tc);
        }
        return true;
    }

    private void buildChannelInfo(TextChannel tc){
        getEmbed().addField(tc.getName(),"**ID** - "+tc.getId(),false);
    }

    @Override
    public boolean execute(boolean response) {
        sendEmbed();
        return true;
    }
}
