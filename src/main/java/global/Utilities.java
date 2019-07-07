package global;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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

    public static String consolidateQuotes(String str){
        String ret = "";
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == 8220 || str.charAt(i)==8221){
                ret += "\"";
            }else if(str.charAt(i)==8216 || str.charAt(i)==8217){
                ret += "'";
            }else {
                ret += str.charAt(i);
            }
        }

        return ret;
    }

    public static String getFromAPI(String add) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        try {
            url = new URL(add);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            String str = "";
            while ((line = br.readLine()) != null) {
                str += line;
            }
            return str;
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }

        return "{}";
    }
}
