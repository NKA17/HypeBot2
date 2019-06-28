package global;

import net.dv8tion.jda.core.EmbedBuilder;

import java.awt.*;

public class Defaults {
    public static EmbedBuilder getEmbedBuilder(){
        EmbedBuilder em = new EmbedBuilder();
        em
                .setColor(new Color(218, 247, 166))
                .setFooter(App.BOT_NAME+" v"+ App.VERSION+" | Help ","https://st4.depositphotos.com/1703608/21210/i/1600/depositphotos_212102742-stock-photo-portrait-funny-raccoon-showing-sign.jpg")
        ;

        return em;
    }
}
