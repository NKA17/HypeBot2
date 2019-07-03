package global;

import com.google.gson.Gson;
import enums.Attributes;
import events.MessageEvent;
import io.actions.AbstractMessageReceivedAction;
import io.actions.SendAction;
import io.actions.aliases.Alias;
import io.actions.executions.*;
import io.actions.memes.*;
import io.structure.Body;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.json.JSONArray;

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static final String VERSION = "Alpha 0.0";
    public static final String BOT_NAME = "HypeBot";
    public static ArrayList<Alias> ALIASES = new ArrayList<>();
    public static final String BOT_ID = "590356017976573960";
    public static final String tempFileName = "aSaucyMeme.png";
    public static final String RESOURCES_PATH = "C:\\Users\\Nate\\IdeaProjects\\HypeBot2\\src\\main\\resources\\";
    public static MessageEvent messageEvent;
    public static Font FONT = new Font("Arial Black", Font.BOLD, 20);
    public static int FONT_BORDER_THICKNESS = 2;

    public static void main(String[] args) throws Exception{
        JDA jda = new JDABuilder("NTkwMzU2MDE3OTc2NTczOTYw.XQhJUw.4cwTnNLXz_fZpIVBmHax6BdPu0k").build();
        messageEvent = new MessageEvent();

        loadActions();
        loadAliases();
        loadMemes();
        loadResponses();

        messageEvent.exeActions.add(new SilenceCommand());
        messageEvent.exeActions.add(new SpeakCommand());
       // messageEvent.exeActions.add(new CheckInCommand());
        messageEvent.exeActions.add(new HelpCommand());
        //messageEvent.exeActions.add(new EditBodyCommand());
        //messageEvent.exeActions.add(new CreateActionCommand());
        //messageEvent.exeActions.add(new RemoveActionCommand());


        messageEvent.memeActions.add(new GrannyMeme());
        messageEvent.memeActions.add(new MockingSpongeBobMeme());
        messageEvent.memeActions.add(new CoolsvilleB());
        messageEvent.memeActions.add(new CoolsvilleA());

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
//        ArrayList<Body> bodies = new ArrayList<>();
//        for(AbstractMessageReceivedAction ar: messageEvent.memeActions){
        //if(ar.getBody().getAttributes().contains(Attributes.CUSTOM))
//            bodies.add(ar.getBody());
//        }
//        saveBodies("actions.txt",bodies);
    }
    public static void saveAliases(){
        ArrayList<Body> bodies = new ArrayList<>();
        for(Alias ar: App.ALIASES){
            if(ar.getBody().getAttributes().contains(Attributes.CUSTOM))
            bodies.add(ar.getBody());
        }
        saveBodies("aliases.txt",bodies);
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
    public static void loadMemes(){
        ArrayList<Body> bodies = loadBodies("memes.txt");
        for(Body b: bodies){
            BlankMeme meme = new BlankMeme();
            meme.setBody(b);
            messageEvent.memeActions.add(meme);
        }
    }
    public static void loadActions(){
//        ArrayList<Body> bodies = loadBodies("memes.txt");
//        for(Body b: bodies){
//            BlankMeme meme = new BlankMeme();
//            meme.setBody(b);
//            messageEvent.memeActions.add(meme);
//        }
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
            SendAction send = new SendAction(b);
            messageEvent.sendActions.add(send);
        }
    }
}
