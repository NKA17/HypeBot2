package io;

import global.App;
import global.Defaults;
import hypebot.HypeBotContext;
import global.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import sun.applet.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;

public class MessageSender {


    private GuildMessageReceivedEvent event;

    public MessageSender(GuildMessageReceivedEvent event){
        this.event = event;
    }

    public MessageSender(){}




    /**
     * Sends a random index from the given list as a response to the TextChannel
     * App-wide Aliases are applied before sending
     * @param messageOptions
     */
    public void sendMessage(ArrayList<String> messageOptions){
        sendMessage(messageOptions,true);
    }
    public void sendMessage(ArrayList<String> messageOptions,boolean applyAliases){
        String[] arr = new String[messageOptions.size()];
        sendMessage(messageOptions.toArray(arr),applyAliases);
    }
    /**
     * Sends a random index from the given list as a response to the TextChannel
     * App-wide Aliases are applied before sending
     * @param messageOptions
     */
    public void sendMessage(String[] messageOptions){
        sendMessage(messageOptions,true);
    }
    public void sendMessage(String[] messageOptions,boolean applyAliases){
        String message = chooseMessage(messageOptions);
        sendMessage(message,applyAliases);
    }

    public void sendMessage(String gid, String cid, String message, boolean applyAliases){

        TextChannel tc = App.findChannel(gid,cid);
        if(tc==null)
            return;

        if(applyAliases) {
            HypeBotContext hbc = App.HYPEBOT.getContext(gid);
            message = MessageUtils.applyAliases(event, message, hbc.getAliases());
        }

        if(!message.matches("http.*") || message.endsWith("gif")) {
            tc.sendMessage(message).queue();
        }else {
            try {
                EmbedBuilder embed = Defaults.getEmbedBuilder();
                embed.setImage(message);
                tc.sendMessage(embed.build()).queue();
                embed.setImage(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(String message, boolean applyAliases){
        if(applyAliases) {
            HypeBotContext hbc = App.HYPEBOT.getContext(getEvent().getGuild().getId());
            message = MessageUtils.applyAliases(event, message, hbc.getAliases());
        }

        if(!message.matches("http.*") || message.endsWith("gif")) {

            event.getChannel().sendMessage(message).queue();
        }else {
            try {
                EmbedBuilder embed = Defaults.getEmbedBuilder();
                embed.setImage(message);
                event.getChannel().sendMessage(embed.build()).queue();
                embed.setImage(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends the embed as a response to the TextChannel
     * @param embed
     */
    public void sendMessage(EmbedBuilder embed){
        event.getChannel().sendMessage(embed.build()).queue();
    }

    /**
     * Returns a single randomly selected index from the given list
     * @param messageOptions
     * @return
     */
    public String chooseMessage(ArrayList<String> messageOptions){
        String[] arr = new String[messageOptions.size()];
        return chooseMessage(messageOptions.toArray(arr));
    }


    /**
     * Returns a single randomly selected index from the given list
     * @param messageOptions
     * @return
     */
    public String chooseMessage(String[] messageOptions){
        Random rand = new Random();
        return messageOptions[rand.nextInt(messageOptions.length)];
    }

    public GuildMessageReceivedEvent getEvent() {
        return event;
    }

    public void setEvent(GuildMessageReceivedEvent event) {
        this.event = event;
    }
}
