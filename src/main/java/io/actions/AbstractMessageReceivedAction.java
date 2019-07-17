package io.actions;

import enums.Attributes;
import global.App;
import global.Defaults;
import global.MessageUtils;
import io.structure.Body;
import io.MessageSender;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractMessageReceivedAction {
    private Body body;

    /**
     * State - used to dynamically create instance variables accessable between
     *  each stage of the action's process
     */
    private HashMap<String,Object> state = new HashMap<>();

    /**
     * Used to pass capture group names and their region's to the applyAliases stage
     */
    private Matcher matcher ;

    /**
     * The raw contents of the event message
     */
    private String content;

    /**
     * Use for random stuff
     */
    private Random random = new Random();


    /**
     * The Event which triggered this action
     */
    private GuildMessageReceivedEvent event;

    private EmbedBuilder embed = Defaults.getEmbedBuilder();


    public AbstractMessageReceivedAction(Body body){
        super();
        this.body = body;
    }

    public AbstractMessageReceivedAction(){

        Body body = new Body();
        setBody(body);
        body.getAttributes().add(Attributes.ACTION);
        body.setAuthor(App.BOT_NAME);
    }


    public String toString(){
        return getBody().getName();
    }
    /**
     * Attempts to find a match with the Body's regex
     * This is called before at the start of the process
     * @return
     */
    public boolean attemptToMatch(){
        try{
            for(String regex : body.getIn()){
                Matcher m = Pattern.compile("(?i)"+regex).matcher(content);
                if(m.find()){
                    matcher = m;
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Called immediately before build()
     * allows you to do some logic after receiving the event, before the reaction is constructed
     * @return
     */
    public  boolean prebuild(){
        return true;
    }

    /**
     * Use to build necessary items for execution
     * This is called between attemptToMatch() and execute(...)
     */
    public abstract boolean build();

    /**
     * Sends Body.out as response to TextChannel
     * @return
     */
    public boolean sendResponse(){
        MessageSender ms = new MessageSender(getEvent());

        String choose = MessageUtils.chooseString(getBody().getOut());

        choose = applyCaptureAliases(choose);

        ms.sendMessage(choose,true);

        return true;
    }
    public boolean sendResponse(String guildid, String channelid, String str){
        MessageSender ms = new MessageSender(getEvent());

        str = applyCaptureAliases(str);

        ms.sendMessage(guildid,channelid,str,true);
        return true;
    }
    public boolean sendResponse(String str){
        MessageSender ms = new MessageSender(getEvent());

        str = applyCaptureAliases(str);

        ms.sendMessage(str,true);
        return true;
    }
    public boolean sendResponse(String... str){
        MessageSender ms = new MessageSender(getEvent());

        String choose = MessageUtils.chooseString(str);

        choose = applyCaptureAliases(choose);
        ms.sendMessage(choose,true);
        return true;
    }

    protected String applyCaptureAliases(String str){
        for(String r: getBody().getIn()){
            Matcher reg = Pattern.compile("\\(\\?<(?<name>.*?)>").matcher(r);
            while (reg.find()){
                String name = reg.group("name");
                try {
                    str = str.replaceAll("@" + name, matcher.group(name));
                }catch (Exception e){
                    // that's ok
                }
            }
        }
        return str;
    }

    /**
     * Builds and sends the embed
     */
    public boolean sendEmbed(){
        getEvent().getChannel().sendMessage(getEmbed().build()).queue();
        return true;
    }

    /**
     * Uses the likelihood field to determine whether this 'happens'
     *  ie
     *      if likelihood = .5, this method will return true 50% of the time
     * @return
     */
    public boolean happens(){
        Random rand = new Random();
        return rand.nextDouble() <= getBody().getLikelihood();
    }


    /**
     * This is what your action should perform. Some actions send messages, some edit local variables,
     * some even create new Actions to be used later.
     * This is called at the end of the process
     * @return
     */
    public abstract boolean execute();

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public HashMap<String, Object> getState() {
        return state;
    }

    public void setState(HashMap<String, Object> state) {
        this.state = state;
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GuildMessageReceivedEvent getEvent() {
        return event;
    }

    public void setEvent(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    public EmbedBuilder getEmbed() {
        return embed;
    }

    public void setEmbed(EmbedBuilder embed) {
        this.embed = embed;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }


    /**
     * Called after execute(...) used to reset items that should not be used across events
     */
//    public void purge(){
//        setContent(null);
//        setEvent(null);
//        setState(new HashMap<>());
//        setEmbed(Defaults.getEmbedBuilder());
//    }
}
