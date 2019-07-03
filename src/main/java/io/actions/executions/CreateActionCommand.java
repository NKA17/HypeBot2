package io.actions.executions;

import enums.Attributes;
import global.App;
import global.MessageUtils;
import io.actions.AbstractMessageReceivedAction;
import io.actions.SendAction;
import io.actions.aliases.Alias;
import io.structure.Body;
import org.json.JSONArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateActionCommand extends Command {

    private String name,type,in,out,description;
    public CreateActionCommand(){
        super();
        getBody().setOut(MessageUtils.affirmative);
        getBody().getIn().add("create\\s+(?<type>response|alias|action)\\s+name(\\s*=\\s*)?\"(?<name>[\\s\\S]+?)\"");
        getBody().setOut(MessageUtils.affirmative);
    }
    @Override
    public boolean execute(boolean response) {
        App.messageEvent.removeSendAction(name);

        Body body = new Body();
        body.setName(name);
        body.setAuthor(getEvent().getAuthor().getId());
        body.setDescription(description);
        body.getAttributes().add(Attributes.CUSTOM);

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
}
