package io.actions.executions;

import enums.Attributes;
import global.App;
import global.MessageUtils;
import io.actions.AbstractMessageReceivedAction;
import io.actions.SendAction;
import io.actions.actions.BlankAction;
import io.actions.aliases.Alias;
import io.structure.Body;
import org.json.JSONArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateActionCommand extends Command {

    private String name,type,in,out,description,likelihood;
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
            body.getIn().add(in);
        }

        try{
            JSONArray arr = new JSONArray(out);
            for(int i = 0; i < arr.length(); i++){
                body.getOut().add(arr.getString(i));
            }
        }catch (Exception e){
            body.getOut().add(out);
        }

        //response|alias|action
        AbstractMessageReceivedAction action;
        switch (type){
            case "response":
                action = new SendAction(body);
                App.messageEvent.sendActions.add(action);
                App.saveResponses();
                break;
            case "alias":
                Alias alias = new Alias();
                alias.setBody(body);
                App.ALIASES.add(alias);
                App.saveAliases();
                break;
            case "action":
                BlankAction ba = new BlankAction();
                ba.setBody(body);
                App.messageEvent.performActions.add(ba);
                App.saveActions();
                break;

            case "meme":
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
        switch (type.toLowerCase()){
            case "meme":
                App.messageEvent.removeMemeAction( name);
                break;
            case "action":
                App.messageEvent.removeActionAction(name);
                break;
            case "response":
                App.messageEvent.removeSendAction(name);
                break;
            case "alias":
                App.messageEvent.removeAlias(name);
                break;
            default:
                App.messageEvent.getAny(name);
                break;
        }
    }
}
