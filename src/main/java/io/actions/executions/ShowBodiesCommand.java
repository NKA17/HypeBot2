package io.actions.executions;

import enums.Attributes;
import global.App;
import global.MessageUtils;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;
import io.structure.Body;

import java.util.ArrayList;

public class ShowBodiesCommand extends Command {

    public ShowBodiesCommand(){
        super();
        getBody().setName("Show");
        getBody().setDescription("*"+App.BOT_NAME+", show <custom|built-in|all> <memes|actions|aliases|responses>*" +
                "\nShows actions.");
        getBody().getIn().add("(show|list|display|tell).*?(?<filter>all|custom|built-in|builtin|built in)" +
                ".*?(?<type>commands|aliases|responses|actions|memes)");
        getBody().getIn().add("(show|list|display|tell)" +
                ".*?(?<type>commands|aliases|responses|actions|memes).*?(?<filter>all|custom|built-in|builtin|built in)");
        getBody().getIn().add("(show|list|display|tell)" +
                ".*?(?<type>commands|aliases|responses|actions|memes)");
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
            case "aliases":
                for(Alias b: App.ALIASES){
                    if(b.getBody().getAttributes().contains(att))
                        bodies.add(b.getBody());
                }
                break;
            case "responses":
                for(AbstractMessageReceivedAction b: App.messageEvent.sendActions){
                    if(b.getBody().getAttributes().contains(att))
                        bodies.add(b.getBody());
                }
                break;
            case "memes":
                for(AbstractMessageReceivedAction b: App.messageEvent.memeActions){
                    if(b.getBody().getAttributes().contains(att))
                        bodies.add(b.getBody());
                }
                break;
            case "actions":
                for(AbstractMessageReceivedAction b: App.messageEvent.performActions){
                    if(b.getBody().getAttributes().contains(att))
                        bodies.add(b.getBody());
                }
                break;
            case "commands":
                for(AbstractMessageReceivedAction b: App.messageEvent.exeActions){
                    if(b.getBody().getAttributes().contains(att))
                        bodies.add(b.getBody());
                }
                break;
        }

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

    private void populateEmbed(String type, String filter,ArrayList<Body> bodies){
        getEmbed().setTitle(Character.toUpperCase(type.charAt(0))+type.toLowerCase().substring(1));
        getEmbed().setDescription(
                "Showing "+filter.toLowerCase()+" "+type.toLowerCase()+".");
        for(Body b: bodies){
            if(b.getAttributes().contains(Attributes.EXECUTE)){
                getEmbed().addField(b.getName(),
                        "*by " + b.getAuthor() + "* \n**Description** = \"" + b.getDescription()
                        , true);
            }else {
                getEmbed().addField(b.getName(),
                        "*by " + b.getAuthor() + "* \n**Description** = \"" + b.getDescription() +
                                "\"\n**In** = " + b.getIn() + "\n**Out** = " + b.getOut() + "\n**Likelihood** = " + b.getLikelihood()
                        , true);
            }
        }
    }

}
