package global;

import global.Utilities;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;

public class MessageUtils {
    /**
     * Returns a String where each alias is re-written as specified
     * @param str
     * @return
     */
    public static String applyAliases(GuildMessageReceivedEvent event, String str, ArrayList<Object> aliases){
        str =  str.replaceAll("#auth", event.getMessage().getAuthor().getName())
                .replaceAll("#owner", Utilities.getOwner(event.getChannel()).getName())
                .replaceAll("#timestamp", Utilities.makeTimeReadable(event));

        for (Object a : aliases) {

            //str = str.replaceAll(a.getAlias(), chooseMessage(a.getResponses()));
        }

        return str;
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
            "I was going to do that anyways."
    };

    public static final String[] notOwnerFail = new String[]{
            "No can do, boss.",
            "Sorry I can't do that.",
            //"ERROR: HypeBot threw IFuckedUpWhileDoingYourShitException during runtime"
            "You're not my dad!",
            "I only do that for #owner."
    };

    public static final String[] failedCommand = new String[]{
            "ERROR: HypeBot threw FuckedUpWhileDoingSomeShitForYouException during runtime",
            "Oh... Shit... I messed something up.",
            "I tried that, it didn't work.",
            "No dice. I messed up trying to do that."
    };

    public static final String[] notOnFile = new String[]{
            "Sorry, I don't know \"#name\"."
            ,"Check your spelling. I couldn't find \"#name\"."
            ,"\"#name\" isn't in my records."
            ,"I don't have \"#name\" on file."
            ,"\"#name\" doesn't exist."
    };
}
