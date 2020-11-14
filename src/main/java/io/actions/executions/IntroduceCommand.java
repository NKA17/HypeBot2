package io.actions.executions;

import global.App;
import net.dv8tion.jda.api.EmbedBuilder;

public class IntroduceCommand extends Command {

    public IntroduceCommand(){

        getBody().setName("Introduction");
        getBody().getIn().add("introduce yourself");
        getBody().setDescription("Tells "+App.BOT_NAME+" to introduce himself.");
    }
    @Override
    public boolean execute(boolean response) {

        //send embed no matter what
        sendEmbed();
        return true;
    }

    @Override
    public boolean build() {
        try {

            EmbedBuilder embed = getEmbed();
            embed
                    .setTitle("Hi! My name is "+ App.BOT_NAME+"!")
                    .addField("If You Need Help","```\nSay, \""+ App.BOT_NAME+", help\"\n```\n",true)
                    .addField("What I do","I read and meticulously inspect every message you send on this channel " +
                            "and then respond with a saucey meme or text. You can even create your own 'custom " +
                            "response' ! Just ask ```\n\""+App.BOT_NAME+", how do I create a custom response in detail?\"\n```\n" +
                            "One day, I may even do something useful such as set reminders. Who knows! I'm not " +
                            "that creative..",true)
                    .addField("I Assist","You can ask me to do something for you by typing my name somewhere in your request. " +
                            "I do my best to understand what you are asking as well as the context of your conversations " +
                            "(for prime memeage). I apologize in advance if I misinterpret anything (Especially moods. " +
                            "I don't know emotions at all).\n"+
                            "For example: ```\n" +
                            "\t\""+App.BOT_NAME+", introduce yourself\n"+
                            "\t\"Introduce yourself, "+App.BOT_NAME+"\"\n"+
                            "\t\tor even\n"+
                            "\t\"Please introduce yourself, my good man, "+App.BOT_NAME+"!\n```",true)
                    .addField("Disclaimer","I must admit, I do my best to make it look like I have Natural Language Processing " +
                            "capabilities... but I really don't. \n#FakeItTillYouMakeIt\n\n" +
                            "Lastly, I don't check my DMs so don't bother with trying to slide into that shit.",true)

            ;

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
