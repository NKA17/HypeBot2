package events;

import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class NickNameChangeEvent extends ListenerAdapter {


    public void onGuildMemberNickChange(GuildMemberNickChangeEvent event){
        System.out.println(event.getNewNick());
    }
}
