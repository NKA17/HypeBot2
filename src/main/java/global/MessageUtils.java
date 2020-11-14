package global;

import global.Utilities;
import io.actions.aliases.Alias;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Random;

public class MessageUtils {
    /**
     * Returns a String where each alias is re-written as specified
     * @param str
     * @return
     */
    public static String applyAliases(GuildMessageReceivedEvent event, String str, ArrayList<Alias> aliases){
//        str =  str.replaceAll("#auth", event.getMessage().getAuthor().getName())
//                .replaceAll("#owner", Utilities.getOwner(event.getChannel()).getName())
//                .replaceAll("#timestamp", Utilities.makeTimeReadable(event))
//                .replaceAll("#channel",event.getChannel().getName())
//                .replaceAll("#guild",event.getGuild().getName());

        for (Alias a : aliases) {

            try {

                str = a.apply(str, event);
            }catch (Exception e){/*I'd rather it didn't but that's ok for now. //TODO */
            //e.printStackTrace();
            }
        }

        return str;
    }

    public static String chooseString(ArrayList<String> arr){
        Random rand = new Random();
        return arr.get(rand.nextInt(arr.size()));
    }

    public static String chooseString(String... arr){
        Random rand = new Random();
        return arr[rand.nextInt(arr.length)];
    }

    public static final String[] affirmative = new String[]{
            "Aye Aye, Cap'n!",
            "Doney Buns!",
            "Can Do!",
            "Got it!",
            "Done!",
            "Phew! That was tough, but it's done!",
            "Affirmative!",
            "Success!",
            "I was going to do that anyways.",
            "If it please thee, I shall.",
            "You're the boss, #auth."
    };

    public static final String[] notOwnerFail = new String[]{
            "No can do, boss.",
            "Sorry I can't do that.",
            //"ERROR: HypeBot threw IFuckedUpWhileDoingYourShitException during runtime"
            "You're not my dad!",
            "I only do that for #owner.",
            "I only do that for #owner.  ;)",
            "https://i.ytimg.com/vi/kCNMbhdlrHo/hqdefault.jpg \n",
            "https://i.imgflip.com/33bfx1.jpg",
    };

    public static final String[] failedCommand = new String[]{
            "ERROR: HypeBot threw FuckedUpWhileDoingSomeShitForYouException during runtime",
            "Oh... Shit... I messed something up.",
            "I tried that, it didn't work.",
            "No dice. I messed up trying to do that.",
            "Oh, god. Ctrl+Z! That was wrong!"
    };

    public static final String[] notOnFile = new String[]{
            "Sorry, I don't know \"#name\"."
            ,"Check your spelling. I couldn't find \"#name\"."
            ,"\"#name\" isn't in my records."
            ,"I don't have \"#name\" on file."
            ,"\"#name\" doesn't exist."
    };

    public static final String[] notAllowed = new String[]{
            "https://i.ytimg.com/vi/kCNMbhdlrHo/hqdefault.jpg",
            "https://i.imgflip.com/33bfx1.jpg"
    };
}
