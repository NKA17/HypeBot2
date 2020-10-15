package io.actions.executions;

import enums.Attributes;
import global.App;
import global.MessageUtils;
import hypebot.HypeBotContext;
import io.actions.AbstractMessageReceivedAction;
import io.actions.sends.BlankResponse;
import io.actions.actions.BlankAction;
import io.actions.aliases.Alias;
import io.structure.Body;
import org.json.JSONArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateActionCommand extends Command {

    private String name,type,in,out,description,likelihood;
    private boolean global;
    public CreateActionCommand(){
        super();
        getBody().setDescription("*"+ App.BOT_NAME+", create <meme|action|alias|response> name = \"<name>\".\n"+
                "Create a custom action.");
        getBody().setName("CreateThings");
        getBody().setOut(MessageUtils.affirmative);
        getBody().getIn().add("create[\\s\\S]+?(?<type>response|alias|action)[\\s\\S]+?name(\\s*=\\s*)?\"(?<name>[\\s\\S]+?)\"");
        getBody().setOut(MessageUtils.affirmative);
    }
    @Override
    public boolean execute(boolean response) {
        removeType(type,name);

        Body body = new Body();
        body.setName(name);
        body.setAuthorId(getEvent().getAuthor().getId());
        body.setAuthor(getEvent().getAuthor().getName());
        body.setDescription(description);
        body.getAttributes().add(Attributes.CUSTOM);
        body.getAttributes().add(Attributes.ACTION);
        body.setGuildId(getEvent().getGuild().getId());
        body.setChannelId(getEvent().getChannel().getId());
        body.setGlobal(global);

        try{
            double d = Double.parseDouble(likelihood.trim());
            if(d > 1 || d < 0){
                throw new IllegalArgumentException();
            }
            body.setLikelihood(d);
        }catch (Exception e){
            sendResponse(MessageUtils.chooseString(MessageUtils.failedCommand));
            sendResponse("Make sure likelihood = a double between value of 1.0 and 0.0");
        }

        try{
            JSONArray arr = new JSONArray(in);
            for(int i = 0; i < arr.length(); i++){
                body.getIn().add(arr.getString(i));
            }
        }catch (Exception e){
            if(in!=null)
            body.getIn().add(in);
        }

        try{
            JSONArray arr = new JSONArray(out);
            for(int i = 0; i < arr.length(); i++){
                body.getOut().add(arr.getString(i));
            }
        }catch (Exception e){
            if(out!=null)
            body.getOut().add(out);
        }

        String id = getEvent().getGuild().getId();
        //response|alias|action
        AbstractMessageReceivedAction action;
        switch (type){
            case "response":
                body.getAttributes().add(Attributes.SEND);
                action = new BlankResponse();
                action.setBody(body);
                HypeBotContext hbc = App.HYPEBOT.getContexts().get(id);
                hbc.getActions().add(action);
                App.HYPEBOT.saveResponses();
                break;
            case "alias":
                body.getAttributes().add(Attributes.ALIAS);
                Alias alias = new Alias();
                alias.setBody(body);
                App.HYPEBOT.getContexts().get(id).getAliases().add(alias);
                App.HYPEBOT.saveAliases();
                break;
            case "action":
                body.getAttributes().add(Attributes.PERFORM);
                BlankAction ba = new BlankAction();
                ba.setBody(body);
                App.HYPEBOT.getContexts().get(id).getActions().add(ba);
                App.HYPEBOT.saveActions();
                break;

            case "meme":
                body.getAttributes().add(Attributes.MEME);
                sendResponse("Sorry, that's still in progress. But you can make something else!");
                return false;

        }

        if(response)
            sendResponse();
        return true;
    }

    @Override
    public boolean build() {
        try{
            name = getMatcher().group("name");
        }catch (Exception e){
            //thats ok
        }

        try{
            Matcher m = Pattern.compile("in(\\s*=\\s*)?(?<in>(\\[[\\s\\S]+?])|(\"[\\s\\S]+?[^\\\\]\"))").matcher(getContent());
            m.find();
            in = m.group("in");
            if(in.startsWith("\""))
                in = in.substring(1);
            if(in.endsWith("\""))
                in = in.substring(0,in.length()-1);
        }catch (Exception e){
            //thats ok
        }

        try{
            Matcher m = Pattern.compile("out(\\s*=\\s*)?(?<out>(\\[[\\s\\S]+?])|(\"[\\s\\S]+?[^\\\\]\"))").matcher(getContent());
            m.find();
            out = m.group("out");
            if(out.startsWith("\""))
                out = out.substring(1);
            if(out.endsWith("\""))
                out = out.substring(0,out.length()-1);
        }catch (Exception e){
            //thats ok
        }

        try{
            Matcher m = Pattern.compile("(description|desc)(\\s*=\\s*)?\"(?<desc>[\\s\\S]+?[^\\\\])\"").matcher(getContent());
            m.find();
            description = m.group("desc");
        }catch (Exception e){
            //thats ok
        }

        try{
            type = getMatcher().group("type");
        }catch (Exception e){
            //thats ok
        }

        try{
            Matcher m = Pattern.compile("(likelihood|like)(\\s*=\\s*)?\\s*(\")?(?<likelihood>(\\d*)\\.\\d+)(\")?").matcher(getContent());
            m.find();
            likelihood = m.group("likelihood");
        }catch (Exception e){
            likelihood = "1.0";
            //thats ok
        }

        try{
            Matcher m = Pattern.compile("(?i)(scope)(\\s*=\\s*)?\\s*(\")?(?<scope>guild|channel)(\")?").matcher(getContent());
            m.find();
            global = m.group("scope").equalsIgnoreCase("guild");
        }catch (Exception e){
            global = true;
            //thats ok
        }

        return true;
    }

    @Override
    public boolean prebuild(){
        name=null;
        type=null;
        in=null;
        out=null;
        description=null;

        return true;
    }

    private void removeType(String type,String name){
        AbstractMessageReceivedAction ar;
        HypeBotContext hbc = App.HYPEBOT.getContext(getEvent());
        switch (type.toLowerCase()){
            case "meme":
                ar = hbc.getAction(name,Attributes.MEME);
                hbc.getActions().remove(ar);
                break;
            case "action":
                ar = hbc.getAction(name,Attributes.PERFORM);
                hbc.getActions().remove(ar);
                break;
            case "response":
                ar = hbc.getAction(name,Attributes.SEND);
                hbc.getActions().remove(ar);
                break;
            case "alias":
                Alias as = hbc.getAlias(name);
                hbc.getAliases().remove(as);
                break;
        }
    }
}
