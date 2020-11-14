package global;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    public static String getTimeStamp(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM-dd-yyyy @ HH:mm");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
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
        return makeTimeReadable(event.getMessage().getTimeCreated().toString());
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

    public static JSONObject makeAPIRequest(String urlstr, String auth){
        try {

            URL url = new URL(urlstr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", auth);

            if (conn.getResponseCode() != 1000 && conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String jsonstr = "";
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                jsonstr += output;
            }

            conn.disconnect();
            return new JSONObject(jsonstr);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return new JSONObject();
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
