package hypebot;

import com.google.gson.Gson;
import cron.CronJob;
import cron.WeeklyReminder;
import enums.Attributes;
import global.App;
import io.actions.AbstractMessageReceivedAction;
import io.actions.sends.BlankResponse;
import io.actions.actions.BlankAction;
import io.actions.aliases.Alias;
import io.actions.memes.BlankMeme;
import io.structure.Body;
import org.json.JSONArray;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class HypeBotStore {


    public static void saveBodies(String filename, ArrayList<Body> arr){
        try{
            PrintWriter pw = new PrintWriter(App.RESOURCES_PATH+filename);
            Gson gson = new Gson();
            String json = gson.toJson(arr);
            pw.println(json);
            pw.flush();
            pw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//
//    public static void saveMemes(){
//        ArrayList<Body> bodies = new ArrayList<>();
//        for(AbstractMessageReceivedAction ar: messageEvent.memeActions){
//            if(ar.getBody().getAttributes().contains(Attributes.CUSTOM))
//                bodies.add(ar.getBody());
//        }
//        saveBodies("memes.txt",bodies);
//    }
//    public static void saveActions(){
//        ArrayList<Body> bodies = new ArrayList<>();
//        for(AbstractMessageReceivedAction ar: messageEvent.performActions){
//            if(ar.getBody().getAttributes().contains(Attributes.CUSTOM))
//                bodies.add(ar.getBody());
//        }
//        saveBodies("actions.txt",bodies);
//    }
//    public static void saveAliases(){
//        ArrayList<Body> bodies = new ArrayList<>();
//        for(Alias ar: App.ALIASES){
//            if(ar.getBody().getAttributes().contains(Attributes.CUSTOM))
//                bodies.add(ar.getBody());
//        }
//        saveBodies("aliases.txt",bodies);
//    }
    public void saveReminders(){
        try{
            PrintWriter pw = new PrintWriter(App.RESOURCES_PATH+"weekly_reminders.txt");
            Gson gson = new Gson();
            JSONArray jarr = new JSONArray();
            while(App.CRON_MONITOR.isLocked());
            App.CRON_MONITOR.lock();
            for(HypeBotContext hbc: App.HYPEBOT.getContextsAsArrayList()) {
                for (CronJob cj : hbc.getJobs()) {
                    jarr.put(cj.toJSON());
                }
            }
            String json = jarr.toString();
            App.CRON_MONITOR.unlock();
            pw.println(json);
            pw.flush();
            pw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    public static void saveResponses(){
//        ArrayList<Body> bodies = new ArrayList<>();
//        for(AbstractMessageReceivedAction ar: messageEvent.sendActions){
//            if(ar.getBody().getAttributes().contains(Attributes.CUSTOM))
//                bodies.add(ar.getBody());
//        }
//        saveBodies("responses.txt",bodies);
//    }

    public void saveItems(ArrayList<HypeBotContext> contexts, Attributes att){
        ArrayList<Body> toSave = new ArrayList<>();
        for(HypeBotContext hbc: contexts){
            for(AbstractMessageReceivedAction ar: hbc.getActions()){
                ArrayList<Attributes> atts = ar.getBody().getAttributes();
                if(atts.contains(att) && atts.contains(Attributes.CUSTOM))
                    toSave.add(ar.getBody());
            }
        }

        String filename;
        switch (att){
            case SEND:
                filename = "responses.txt";
                break;
            case ACTION:
                filename = "actions.txt";
                break;
            case MEME:
                filename = "memes.txt";
                break;
            default:
                filename = "errordata.txt";
        }
        saveBodies(filename,toSave);
    }

    public void saveAliases(ArrayList<HypeBotContext> contexts){
        ArrayList<Body> toSave = new ArrayList<>();
        for(HypeBotContext hbc: contexts){
            for(Alias ar: hbc.getAliases()){
                if(ar.getBody().getAttributes().contains(Attributes.CUSTOM))
                    toSave.add(ar.getBody());
            }
        }

        saveBodies("aliases.txt",toSave);
    }

    public ArrayList<Body> loadBodies(String filename){
        try{
            ArrayList<Body> bodies = new ArrayList<>();
            Gson gson = new Gson();
            Scanner s = new Scanner(new File(App.RESOURCES_PATH+filename));
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

    public ArrayList<CronJob> loadWeeklyReminders(){
        try{
            ArrayList<CronJob> ret = new ArrayList<>();
            Gson gson = new Gson();
            Scanner s = new Scanner(new File(App.RESOURCES_PATH+"weekly_reminders.txt"));
            String jarr = s.nextLine();
            JSONArray arr = new JSONArray(jarr);
            for(int i = 0; i < arr.length(); i++){
                WeeklyReminder body = gson.fromJson(arr.getJSONObject(i).toString(),WeeklyReminder.class);
                ret.add(body);
            }
            s.close();
            return ret;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ArrayList<AbstractMessageReceivedAction>  loadMemes(){
        ArrayList<Body> bodies = loadBodies("memes.txt");

        ArrayList<AbstractMessageReceivedAction> ret = new ArrayList<>();
        for(Body b: bodies){
            BlankMeme meme = new BlankMeme();
            meme.setBody(b);
            ret.add(meme);
        }
        return ret;
    }
    public  ArrayList<AbstractMessageReceivedAction> loadActions(){
        ArrayList<Body> bodies = loadBodies("actions.txt");

        ArrayList<AbstractMessageReceivedAction> ret = new ArrayList<>();
        for(Body b: bodies){
            BlankAction action = new BlankAction();
            action.setBody(b);
            ret.add(action);
        }
        return ret;
    }
    public ArrayList<Alias> loadAliases(){
        ArrayList<Body> bodies = loadBodies("aliases.txt");
        ArrayList<Alias> ret = new ArrayList<>();
        for(Body b: bodies){
            Alias alias = new Alias();
            alias.setBody(b);
            ret.add(alias);
        }
        return ret;
    }
    public ArrayList<AbstractMessageReceivedAction>  loadResponses(){
        ArrayList<Body> bodies = loadBodies("responses.txt");
        ArrayList<AbstractMessageReceivedAction> ret = new ArrayList<>();
        for(Body b: bodies){
            BlankResponse send = new BlankResponse();
            send.setBody(b);
            ret.add(send);
        }
        return ret;
    }
}
