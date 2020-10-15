package global;

import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;
import cron.CronJob;
import cron.CronMonitor;
import cron.WeeklyReminder;
import enums.Attributes;
import events.MessageEvent;
import external.ExternalCommandHandler;
import external.ExternalCommandServer;
import hypebot.HypeBot;
import io.actions.AbstractMessageReceivedAction;
import io.actions.actions.BlankAction;
import io.actions.aliases.*;
import io.actions.executions.*;
import io.actions.memes.*;
import io.actions.sends.BlankResponse;
import io.actions.sends.ChuckNorrisResponse;
import io.actions.sends.PizzaPartyResponse;
import io.structure.Body;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.soap.Text;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static final String VERSION = "Beta 1.5.0";
    public static final int MAX_EMBED_SIZE = 60000;
    public static String BOT_NAME = "HypeBot";
    public static ArrayList<Alias> ALIASES = new ArrayList<>();
    public static final String BOT_ID = "590356017976573960";
    public static final String tempFileName = "aSaucyMeme.png";
    public static String RESOURCES_PATH = "C:\\Users\\Nate\\IdeaProjects\\HypeBot2\\src\\main\\resources\\";
    //public static String RESOURCES_PATH = "";
    public static MessageEvent messageEvent;
    public static Font FONT = new Font("Arial Black", Font.BOLD, 20);
    public static int FONT_BORDER_THICKNESS = 2;
    public static final CronMonitor CRON_MONITOR = new CronMonitor();
    public static JDA jda;
    public static boolean TEST_MODE = false;
    public static HypeBot HYPEBOT = new HypeBot();

    //API doc  https://discordapp.com/developers/docs/intro
    //API url https://discordapp.com/api
    public static void main(String[] args) throws Exception{

        System.setProperty("http.agent", "Chrome");

        System.out.println("\n"+App.BOT_NAME+" v"+App.VERSION);
        String name = getArg("--botname",args);
        if(name==null)
            name = "HypeBot";
        BOT_NAME = name;

        String path = getArg("--path",args);
        if(path==null)
            path = System.getProperty("user.home")+"/"+App.BOT_NAME+"/storage/";

        String token = "NTkwMzU2MDE3OTc2NTczOTYw.XsiSdg.l1HNTaUCwqexchsO-kVooHWmDCo";
        String tokenFromConfig = getArg("--token",args);
        if(tokenFromConfig != null){
            token = tokenFromConfig;
        }

        Path p = Paths.get(path).normalize().toAbsolutePath();
        RESOURCES_PATH = p.toString();
        if(!RESOURCES_PATH.endsWith("/"))
            RESOURCES_PATH+="/";

        String mode = getArg("--mode",args);
        if(mode==null){
            TEST_MODE = false;
        }else{
            if(mode.equalsIgnoreCase("test"))
                TEST_MODE = true;
        }


        System.out.println(String.format("USING:\n\tPath: %s\n\tBotName: %s",RESOURCES_PATH,BOT_NAME));
        jda = new JDABuilder(token).build();
        messageEvent = new MessageEvent();



        ExternalCommandServer server = new ExternalCommandServer();
        server.open();


//        loadActions();
//        loadAliases();
//        loadMemes();
//        loadResponses();
//        loadWeeklyReminders();
//
//
//
//        messageEvent.exeActions.add(new SilenceCommand());
//        messageEvent.exeActions.add(new SpeakCommand());
//        messageEvent.exeActions.add(new CheckInCommand());
//        messageEvent.exeActions.add(new HelpCommand());
//        messageEvent.exeActions.add(new EditBodyCommand());
//        messageEvent.exeActions.add(new CreateActionCommand());
//        messageEvent.exeActions.add(new RemoveActionCommand());
//        messageEvent.exeActions.add(new ShowBodiesCommand());
//        messageEvent.exeActions.add(new HowToActionCommand());
//        messageEvent.exeActions.add(new ClearCommand());
//        messageEvent.exeActions.add(new IntroduceCommand());
//        messageEvent.exeActions.add(new HowToEditCommand());
//        messageEvent.exeActions.add(new HowToMemeCommand());
//        messageEvent.exeActions.add(new ChangeLogCommand());
//        messageEvent.exeActions.add(new CreateReminderCommand());
//
//
//        messageEvent.memeActions.add(new GrannyMeme());
//        messageEvent.memeActions.add(new MockingSpongeBobMeme());
//        messageEvent.memeActions.add(new CoolsvilleB());
//        messageEvent.memeActions.add(new CoolsvilleA());
//        messageEvent.memeActions.add(new KKK());
//        messageEvent.memeActions.add(new PizzaPartyMeme());
//        messageEvent.memeActions.add(new ButterflyMeme());
//        messageEvent.memeActions.add(new IsNotAMeme());
//
//        messageEvent.sendActions.add(new PizzaPartyResponse());
//        messageEvent.sendActions.add(new ChuckNorrisResponse());
//
//        App.ALIASES.add(new AuthorAlias());
//        App.ALIASES.add(new ChannelAlias());
//        App.ALIASES.add(new GuildAlias());
//        App.ALIASES.add(new OwnerAlias());
//        App.ALIASES.add(new TimestampAlias());
//

        jda.addEventListener(messageEvent);


        jda.getPresence().setGame(Game.watching(
                MessageUtils.chooseString(
                        "you guys have fun without him and trying to feel included.",
                        "you all the time",
                        //"the chat carefully",
                        //"for an opportunity to meme",
                        "his neighbors sleep",
                        "you through the window",
                        "you from a satellite",
                        "you read your phone",
                        "you watching him",
                        "you from a distance")));


        HYPEBOT.loadUserMade();
        HYPEBOT.loadGlobals();


        for(Guild g: jda.getGuilds()){
            System.out.println(g.getName()+", "+g.getId());
            for(TextChannel tc: g.getTextChannels()){
                System.out.println("\t"+tc.getName()+", "+tc.getId());
            }

        }

        Thread cronThread = new Thread(CRON_MONITOR);
        cronThread.start();

    }

    public static void setFontSize(int size){
        FONT = new Font(FONT.getName(),FONT.getStyle(),size);
    }

    public static void setFontName(String name){
        FONT = new Font(name,FONT.getStyle(),FONT.getSize());
    }

    public static void setFontStyle(int style){ FONT = new Font(FONT.getName(),style,FONT.getSize());}

    public static int getFontStyle(){return FONT.getStyle();}
    public static int getFontSize(){return FONT.getSize();}
    public static String getFontName(){ return FONT.getName();}

    public static void saveBodies(String filename, ArrayList<Body> arr){
        try{
            PrintWriter pw = new PrintWriter(RESOURCES_PATH+filename);
            Gson gson = new Gson();
            String json = gson.toJson(arr);
            pw.println(json);
            pw.flush();
            pw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void saveMemes(){
        ArrayList<Body> bodies = new ArrayList<>();
        for(AbstractMessageReceivedAction ar: messageEvent.memeActions){
            if(ar.getBody().getAttributes().contains(Attributes.CUSTOM))
            bodies.add(ar.getBody());
        }
        saveBodies("memes.txt",bodies);
    }
    public static void saveActions(){
        ArrayList<Body> bodies = new ArrayList<>();
        for(AbstractMessageReceivedAction ar: messageEvent.performActions){
            if(ar.getBody().getAttributes().contains(Attributes.CUSTOM))
                bodies.add(ar.getBody());
        }
        saveBodies("actions.txt",bodies);
    }
    public static void saveAliases(){
        ArrayList<Body> bodies = new ArrayList<>();
        for(Alias ar: App.ALIASES){
            if(ar.getBody().getAttributes().contains(Attributes.CUSTOM))
            bodies.add(ar.getBody());
        }
        saveBodies("aliases.txt",bodies);
    }
    public static void saveReminders(){
        try{
            PrintWriter pw = new PrintWriter(RESOURCES_PATH+"weekly_reminders.txt");
            Gson gson = new Gson();
            JSONArray jarr = new JSONArray();
            while(CRON_MONITOR.isLocked());
            CRON_MONITOR.lock();
            for(CronJob cj : CRON_MONITOR.getJobs()){
                jarr.put(cj.toJSON());
            }
            String json = jarr.toString();
            CRON_MONITOR.unlock();
            pw.println(json);
            pw.flush();
            pw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void saveResponses(){
        ArrayList<Body> bodies = new ArrayList<>();
        for(AbstractMessageReceivedAction ar: messageEvent.sendActions){
            if(ar.getBody().getAttributes().contains(Attributes.CUSTOM))
            bodies.add(ar.getBody());
        }
        saveBodies("responses.txt",bodies);
    }

    public static ArrayList<Body> loadBodies(String filename){
        try{
            ArrayList<Body> bodies = new ArrayList<>();
            Gson gson = new Gson();
            Scanner s = new Scanner(new File(RESOURCES_PATH+filename));
            String jarr = s.nextLine();
            JSONArray arr = new JSONArray(jarr);
            for(int i = 0; i < arr.length(); i++){
                Body body = gson.fromJson(arr.getJSONObject(i).toString(),Body.class);
                bodies.add(body);
            }
            s.close();
            return bodies;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Body>();
        }
    }

    public static void loadWeeklyReminders(){
        try{
            ArrayList<WeeklyReminder> bodies = new ArrayList<>();
            Gson gson = new Gson();
            Scanner s = new Scanner(new File(RESOURCES_PATH+"weekly_reminders.txt"));
            String jarr = s.nextLine();
            JSONArray arr = new JSONArray(jarr);
            for(int i = 0; i < arr.length(); i++){
                WeeklyReminder body = gson.fromJson(arr.getJSONObject(i).toString(),WeeklyReminder.class);
                CRON_MONITOR.addJob(body);
            }
            s.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void loadMemes(){
        ArrayList<Body> bodies = loadBodies("memes.txt");
        for(Body b: bodies){
            BlankMeme meme = new BlankMeme();
            meme.setBody(b);
            messageEvent.memeActions.add(meme);
        }
    }
    public static void loadActions(){
        ArrayList<Body> bodies = loadBodies("actions.txt");
        for(Body b: bodies){
            BlankAction action = new BlankAction();
            action.setBody(b);
            messageEvent.performActions.add(action);
        }
    }
    public static void loadAliases(){
        ArrayList<Body> bodies = loadBodies("aliases.txt");
        for(Body b: bodies){
            Alias alias = new Alias();
            alias.setBody(b);
            ALIASES.add(alias);
        }
    }
    public static void loadResponses(){
        ArrayList<Body> bodies = loadBodies("responses.txt");
        for(Body b: bodies){
            BlankResponse send = new BlankResponse();
            send.setBody(b);
            messageEvent.sendActions.add(send);
        }
    }

    private static String getArg(String arg, String[] args){
        for(int i = 0; i < args.length-1; i++){
            if(arg.equalsIgnoreCase(args[i]))
                return args[i+1];
        }

        return null;
    }

    public static TextChannel findChannel(String guildId, String channelId){
        for(Guild g: jda.getGuilds()){
            if(g.getId().equalsIgnoreCase(guildId)){
                for(TextChannel tc: g.getTextChannels()){
                    if(tc.getId().equalsIgnoreCase(channelId))
                        return tc;
                }
            }
        }
        return null;
    }

    public static User getUser(String userId){
        for(Guild g: jda.getGuilds()){
            for(Member m: g.getMembers()){
                if(m.getUser().getId().equalsIgnoreCase(userId)){
                    return m.getUser();
                }
            }
        }
        return null;
    }

    public static Guild findGuild(String guildId){
        for(Guild g: jda.getGuilds()){
            if(g.getId().equalsIgnoreCase(guildId)){
                return g;
            }
        }
        return null;
    }

    public static String getNickNameOnGuild(String guildId, String userId){
        for(Member m: findGuild(guildId).getMembers()){
            if(m.getUser().getId().equalsIgnoreCase(userId)){
                return m.getNickname();
            }
        }
        return null;
    }

    public static TextChannel findPrimaryChannelForGuild(String guildId){
        Guild g = findGuild(guildId);
        if(g != null){
            return g.getTextChannels().get(0);
        }
        return null;
    }

    public static TextChannel findSubChannel(String guildId, String channelName){
        for(Guild g: jda.getGuilds()){
            if(g.getId().equalsIgnoreCase(guildId)){
                for(TextChannel tc: g.getTextChannels()){
                    if(tc.getName().equalsIgnoreCase(channelName))
                        return tc;
                }
            }
        }
        return null;
    }
}
