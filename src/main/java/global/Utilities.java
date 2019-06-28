package global;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    /**
     * Provide a user id. If the user is a member of the TextChannel, they will
     * be returned
     * @param id
     * @param guild
     * @return
     */
    public static User getUserById(String id, TextChannel guild){
        for(Member m: guild.getMembers()){
            if(m.getUser().getId().equalsIgnoreCase(id))
                return m.getUser();
        }
        return null;
    }

    /**
     * Formats the Message.CreationTime as 'YYYY-MM-DD @ HH:mm'
     * @param event
     * @return
     */
    public static String makeTimeReadable(GuildMessageReceivedEvent event){
        return makeTimeReadable(event.getMessage().getCreationTime().toString());
    }

    /**
     * Formats the Message.CreationTime as 'YYYY-MM-DD @ HH:mm'
     * @param time
     * @return
     */
    public static String makeTimeReadable(String time){
        //2019-06-27T12:33:49.460Z
        Matcher m = Pattern.compile("(?<date>\\d+-\\d+-\\d+)T(?<time>\\d+:\\d+)").matcher(time);
        m.find();
        return m.group("date")+ " @ "+m.group("time");
    }

    /**
     * Returns the owner of the TextChannel
     * @param guild
     * @return
     */
    public static User getOwner(TextChannel guild){
        for(Member m: guild.getMembers()){
            if(m.isOwner())
                return m.getUser();
        }
        return null;
    }
}
