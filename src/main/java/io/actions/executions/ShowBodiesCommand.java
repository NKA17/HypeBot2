package io.actions.executions;

import enums.Attributes;
import global.App;
import global.MessageUtils;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;
import io.structure.Body;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowBodiesCommand extends Command {

    public ShowBodiesCommand(){
        super();
        getBody().setName("Show");
        getBody().setDescription("*"+App.BOT_NAME+", show <custom|built-in|all> <memes|actions|aliases|responses>*" +
                "\nShows actions.");
        getBody().getIn().add("(show|list|display|tell).*?(?<filter>all|custom|built-in|builtin|built in)" +
                ".*?(?<type>command|alias|response|action|meme)");
        getBody().getIn().add("(show|list|display|tell)" +
                ".*?(?<type>command|alias|response|action|meme).*?(?<filter>all|custom|built-in|builtin|built in)");
        getBody().getIn().add("(show|list|display|tell)" +
                ".*?(?<type>command|alias|response|action|meme)");
    }
    @Override
    public boolean execute(boolean response) {

        sendEmbed();

        return true;
    }

    @Override
    public boolean build() {
        String filter = "all";
        Attributes att = Attributes.ACTION;
        try{
            filter = getMatcher().group("filter");
        }catch (Exception e){}

        if(filter!=null) {
            switch (filter.toLowerCase()) {
                case "custom":
                    att = Attributes.CUSTOM;
                    break;
                case "built-in":
                    att = Attributes.VANILLA;
                    break;
                case "all":
                    att = Attributes.ACTION;
                    break;
            }
        }else {
            filter = "all";
            att = Attributes.ACTION;
        }

        ArrayList<Body> bodies = new ArrayList<>();
        String type = getMatcher().group("type");
        switch (type.toLowerCase()){
            case "alias":
                bodies.addAll(getReturnBodies(getAsBodiesFromAlias(App.ALIASES),att));
                break;
            case "response":
                bodies.addAll(getReturnBodies(getAsBodies(App.messageEvent.sendActions),att));
                break;
            case "meme":
                bodies.addAll(getReturnBodies(getAsBodies(App.messageEvent.memeActions),att));
                break;
            case "action":
                bodies.addAll(getReturnBodies(getAsBodies(App.messageEvent.performActions),att));
                break;
            case "command":
                bodies.addAll(getReturnBodies(getAsBodies(App.messageEvent.exeActions),Attributes.VANILLA));
                break;
        }

        try{
            Matcher m = Pattern.compile("\"(\\w+)\"").matcher(getContent());
            if(m.find()){
                String name = m.group(1);
                bodies = applyNameFilter(bodies,name);
            }
        }catch (Exception e){  }

        if(bodies.size()>0)
        populateEmbed(type,filter,bodies);
        else {
            sendResponse(MessageUtils.chooseString("None on file.", "You don't have any.",
                    "You should make some, first. Then, I can show you what you made.",
                    "What "+type+"?"));
            return false;
        }


        return true;
    }

    private ArrayList<Body> applyNameFilter(ArrayList<Body> list, String name){
        ArrayList<Body> ret = new ArrayList<>();
        for(Body b: list){
            if(b.getName().toLowerCase().contains(name.toLowerCase()))
                ret.add(b);
        }
        return ret;
    }
    private void populateEmbed(String type, String filter,ArrayList<Body> bodies){
        getEmbed().setTitle(Character.toUpperCase(type.charAt(0))+type.toLowerCase().substring(1));
        getEmbed().setDescription(
                "Showing "+filter.toLowerCase()+" "+type.toLowerCase()+".");
        for(Body b: bodies){
            if(b.getAttributes().contains(Attributes.EXECUTE)){
                getEmbed().addField("**"+b.getName()+"**",
                        "*by " + b.getAuthor() + "*\n``` \nDescription = \"" + b.getDescription()+"\n ```"
                        , true);
            }else {
                getEmbed().addField("**"+b.getName()+"**",
                        "*by " + b.getAuthor() + "* \n```\nDescription = \"" + b.getDescription() +
                                "\"\nIn = " + b.getIn() + "\nOut = " + b.getOut() + "\nLikelihood = " + b.getLikelihood()
                                +"\nScope = "+(b.isGlobal()?"GUILD":"CHANNEL")+"\n```", true);
            }
        }
    }

    private ArrayList<Body> getAsBodiesFromAlias(ArrayList<Alias> list){
        ArrayList<Body> ret = new ArrayList<>();
        for(Alias ar: list){
            ret.add(ar.getBody());
        }

        return ret;
    }
    private ArrayList<Body> getAsBodies(ArrayList<AbstractMessageReceivedAction> list){
        ArrayList<Body> ret = new ArrayList<>();
        for(AbstractMessageReceivedAction ar: list){
            ret.add(ar.getBody());
        }

        return ret;
    }

    private ArrayList<Body> getReturnBodies(ArrayList<Body> list, Attributes att){
        ArrayList<Body> ret = new ArrayList<>();
        for(Body b: list) {
            boolean f = App.TEST_MODE;
            boolean c = getEvent().getGuild().getId().equalsIgnoreCase(b.getGuildId());
            boolean d = b.isGlobal();
            boolean e = b.getChannelId().equalsIgnoreCase(getEvent().getChannel().getId());
                if (!f) {
                    if (!c) {
                        continue;
                    } else {
                        if (!d && !e) {
                            continue;
                        }
                    }
                }
            if (b.getAttributes().contains(att))
                ret.add(b);
        }
        return ret;
    }
}
