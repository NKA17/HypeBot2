package io.actions.executions;

import enums.Attributes;
import global.App;
import global.Defaults;
import global.MessageUtils;
import hypebot.HypeBot;
import hypebot.HypeBotContext;
import interfaces.Chainable;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;
import io.actions.sends.BlankResponse;
import io.structure.Body;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.xml.bind.annotation.XmlType;
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
                ".*?(?<type>command|alias|response|action|meme|job|reminder)");
        getBody().getIn().add("(show|list|display|tell)" +
                ".*?(?<type>command|alias|response|action|meme|job|reminder).*?(?<filter>all|custom|built-in|builtin|built in)");
        getBody().getIn().add("(show|list|display|tell)" +
                ".*?(?<type>command|alias|response|action|meme|job|reminder)");
    }
    @Override
    public boolean execute(boolean response) {

        //sendEmbed();

        return true;
    }

    @Override
    public boolean build() {
        setEmbed(Defaults.getEmbedBuilder());
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
        HypeBotContext hbc = App.HYPEBOT.getContexts().get(getEvent().getGuild().getId());
        switch (type.toLowerCase()){
            case "alias":
                bodies.addAll(getReturnBodies(getAsBodiesFromAlias(hbc.getAliases()),att));
                break;
            case "response":
                bodies.addAll(getReturnBodies(getAsBodies(hbc.getActions()),att,Attributes.SEND));
                break;
            case "meme":
                bodies.addAll(getReturnBodies(getAsBodies(hbc.getActions()),att,Attributes.MEME));
                break;
            case "action":
                bodies.addAll(getReturnBodies(getAsBodies(hbc.getActions()),att,Attributes.PERFORM));
                break;
            case "command":
                bodies.addAll(getReturnBodies(getAsBodies(hbc.getCommands()),Attributes.VANILLA));
                break;
            case "job":
                bodies.addAll(getReturnBodies(getAsBodies(hbc.getBotjobs()),att));
                break;
            case "reminder":
                bodies.addAll(getReturnBodies(getAsBodies(hbc.getBotjobs()),att));
                break;
        }

        try{
            Matcher m = Pattern.compile("\"(\\w+)\"").matcher(getContent());
            if(m.find()){
                String name = m.group(1);
                bodies = applyNameFilter(bodies,name);
            }
        }catch (Exception e){  }

        if(bodies.size()>0){
            Chainable chainable = createChainable(type,filter,bodies);
            if(bodies.size() > 5) {
                hbc.getChainMonitor().setChain(chainable);
            }
            chainable.execute(getEvent());
        }
        else {
            sendResponse(MessageUtils.chooseString("None on file.", "You don't have any.",
                    "You should make some, first. Then, I can show you what you made.",
                    "What "+type+"?","This is awkward. Uh... there aren't any."));
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
                String instr = b.getIn().toString();
                String outstr = b.getOut().toString();
                if(bodies.size() > 1 && instr.length() > 40)
                    instr = instr.substring(0,40)+"...";
                if(bodies.size() > 1 && outstr.length() > 40)
                    outstr = outstr.substring(0,40)+"...";
                getEmbed().addField("**"+b.getName()+"**",
                        "*by " + b.getAuthor() + "* \n```\nDescription = \"" + b.getDescription() +
                                "\"\nIn = " + instr + "\nOut = " + outstr + "\nLikelihood = " + b.getLikelihood()
                                +"\nScope = "+(b.isGlobal()?"GUILD":"CHANNEL")+"\n```", false);
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

    private ArrayList<Body> getReturnBodies(ArrayList<Body> list, Attributes... att){
        ArrayList<Body> ret = new ArrayList<>();
        for(Body b: list) {
            boolean has = true;
            for(Attributes a: att){
                has = has && b.getAttributes().contains(a);
            }
            if(has)
                ret.add(b);
        }
        return ret;
    }

    private Chainable createChainable(String type, String filter, ArrayList<Body> loaded){
        Chainable chainable = new Chainable() {
            private ArrayList<Body> bodies = loaded;
            int i = 0;
            int pagesize = 5;
            String t = type;
            String f = filter;
            GuildMessageReceivedEvent event;
            long mostRecentInteraction = System.currentTimeMillis();
            @Override
            public boolean match(String str) {
                if(str.matches("next|n")){
                    i = Math.min(i+pagesize,bodies.size()-(bodies.size()%pagesize));
                    mostRecentInteraction = System.currentTimeMillis();
                    return true;
                }
                if(str.matches("previous|prev|p")){
                    i = Math.max(0, i-pagesize);
                    mostRecentInteraction = System.currentTimeMillis();
                    return true;
                }
                return false;
            }

            @Override
            public boolean execute(GuildMessageReceivedEvent event) {
                ArrayList<Body> forpage = new ArrayList<>();
                setEmbed(Defaults.getEmbedBuilder());
                for( int j = i; j < i+pagesize && j < bodies.size(); j++){
                    forpage.add(bodies.get(j));
                }
                populateEmbed(t,f,forpage);
                if(bodies.size() > pagesize)
                    getEmbed().addField("Page "+((i/pagesize)+1)+" of "+((bodies.size()/pagesize)+1),"*n - next | p - previous*",true);
                sendEmbed();
                return false;
            }

            @Override
            public boolean expire() {
                if(System.currentTimeMillis() - mostRecentInteraction > 30000) {
                    BlankResponse br = new BlankResponse();
                    br.getBody().setGuildId(getEvent().getGuild().getId());
                    br.getBody().setChannelId(getEvent().getChannel().getId());
                    br.getBody().setAuthorId(getEvent().getAuthor().getId());
                    br.getBody().setOut("*Page listener expired.*");
                    br.setContent("");
                    br.execute();
                    return true;
                }
                return false;
            }
        };
        return chainable;
    }
}
