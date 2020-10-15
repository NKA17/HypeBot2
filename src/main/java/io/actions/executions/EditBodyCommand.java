package io.actions.executions;

import cron.HypeBotCronJob;
import enums.Attributes;
import global.App;
import global.MessageUtils;
import global.Utilities;
import hypebot.HypeBotContext;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;
import io.structure.Body;
import org.json.JSONArray;

import java.util.ArrayList;

public class EditBodyCommand extends Command {

    private String name,action,field,value,type;
    private Body editBody;
    //syntax
    //Hypebot, edit "<itemName>" <set|add> <name|in|out|description> "<value>"
    public EditBodyCommand(){
        super();

        getBody().setOut(MessageUtils.affirmative);
        getBody().getIn().add("edit\\s+(?<type>reminder|meme|response|alias|action)?\\s*\"(?<name>[\\s\\S]+?)\"\\s+(?<action>add|set|clear)\\s+" +
                "(?<field>name|in|out|description|desc|likelihood|like|scope|channel)\\s*(\\s*=\\s*)?(?<value>(\\[[\\s\\S]+?])|(\"[\\s\\S]+?[^\\\\]\")|(\\d*\\.\\d+)|(\\w+))");
        getBody().getIn().add("edit\\s+(?<type>reminder|meme|response|alias|action)?\\s*\"(?<name>[\\s\\S]+?)\"\\s+(?<action>clear)\\s+" +
                "(?<field>name|in|out|description|desc|likelihood|like|scope|channel)");
        getBody().setName("EditThings");
        getBody().setDescription("*"+App.BOT_NAME+", edit <reminder|meme|action|alias|response>? \"<name>\" <set|add|clear> <field> <value>*\n" +
                "Edit existing actions.");
    }
    @Override
    public boolean execute(boolean response) {

        edit(editBody,action,field,value);
        App.HYPEBOT.saveAliases();
        App.HYPEBOT.saveResponses();
        App.HYPEBOT.saveActions();
        App.HYPEBOT.saveMemes();
        App.HYPEBOT.saveCronJobs();

        if(response)
            sendResponse();
        return true;
    }

    public boolean attemptToMatch(){
        boolean superbool = super.attemptToMatch();
        if(getContent().matches("(?i)^"+App.BOT_NAME+"(,)? create[\\s\\S]*"))
            return false;
        return superbool;
    }

    @Override
    public boolean prebuild(){
        name=null;
        action=null;
        field=null;
        value=null;
        editBody=null;
        return true;
    }
    @Override
    public boolean build() {

        try {
            name = getMatcher().group("name");
            action = getMatcher().group("action");
            field = getMatcher().group("field");
            value = "";
            try{
                value = getMatcher().group("value").replaceAll("\\\\\"","\"");
            }catch (Exception e){}

            type = getMatcher().group("type");
            editBody = null;
            HypeBotContext hbc = App.HYPEBOT.getContexts().get(getEvent().getGuild().getId());
            if(type==null)
                type = "null";
            try {
                switch (type.toLowerCase()) {
                    case "meme":
                        editBody = hbc.getAction(name, Attributes.MEME).getBody();
                        break;
                    case "action":
                        editBody = hbc.getAction(name, Attributes.PERFORM).getBody();
                        break;
                    case "response":
                        editBody = hbc.getAction(name, Attributes.SEND).getBody();
                        break;
                    case "alias":
                        editBody = hbc.getAlias(name).getBody();
                        break;
                    case "reminder":
                        editBody = hbc.getBotJob(name).getBody();
                        break;
                    default:
                        AbstractMessageReceivedAction ar = hbc.getAction(name);
                        if (ar != null) {
                            editBody = ar.getBody();
                            break;
                        }
                        Alias al = hbc.getAlias(name);
                        if (al != null) {
                            editBody = al.getBody();
                            break;
                        }

                        AbstractMessageReceivedAction hbcj = hbc.getBotJob(name);
                        if(hbcj!=null){
                            editBody = hbcj.getBody();
                            break;
                        }
                        break;
                }
            }catch (Exception e){}

            if(editBody!=null ){
                if(getState().containsKey("tempPermission")){
                    if(!getState().get("tempPermission").toString().equalsIgnoreCase(editBody.getAuthorId())){
                        sendResponse(MessageUtils.chooseString(MessageUtils.notAllowed));
                    }
                    getState().remove("tempPermission");
                }else
                if(!Utilities.getOwner(getEvent().getChannel()).getId().equalsIgnoreCase(getEvent().getAuthor().getId()) &&
                        !editBody.getAuthorId().equalsIgnoreCase(getEvent().getAuthor().getId())){
                    sendResponse(MessageUtils.chooseString(MessageUtils.notAllowed));
                    return false;
                }

            }

            if(value.startsWith("\""))
                value = value.substring(1);
            if(value.endsWith("\""))
                value = value.substring(0,value.length()-1);

            if(editBody==null) {
                String response = MessageUtils.chooseString(MessageUtils.notOnFile);
                response = response.replaceAll("#name",name);
                sendResponse(response);
                return false;
            }else
                return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean edit(Body body, String action, String field, String value){
        try {
            boolean worked = true;
            switch (action.toLowerCase()) {
                case "add":
                    worked = worked && add(body, field, value);
                    break;
                case "set":
                    worked = worked && set(body, field, value);
                    break;
                case "clear":
                    worked = worked && clear(body, field);
                    break;
            }
            return worked;
        }catch (Exception e) {
            return false;
        }
    }

    private boolean add(Body body, String field, String value){
        try {
            boolean worked = true;
            switch (field.toLowerCase()) {
                case "name":
                    body.setName(value);
                    break;
                case "in":
                    try{
                        JSONArray arr = new JSONArray(value);
                        for(int i = 0; i < arr.length(); i++){
                            body.getIn().add(arr.getString(i));
                        }
                    }catch (Exception e){
                        body.getIn().add(value);
                    }
                    break;
                case "out":
                    try{
                        JSONArray arr = new JSONArray(value);
                        for(int i = 0; i < arr.length(); i++){
                            body.getOut().add(arr.getString(i));
                        }
                    }catch (Exception e){
                        body.getOut().add(value);
                    }
                    break;
                case "description":
                    body.setDescription(value);
                    break;
                case "likelihood":
                    body.setLikelihood(value);
                    break;
                case "scope":
                    body.setGlobal(value.equalsIgnoreCase("guild"));
                    break;
                case "channel":
                    body.setChannelId(value);
                    break;
            }
            return worked;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean set(Body body, String field, String value){
        try {
            boolean worked = true;
            switch (field.toLowerCase()) {
                case "name":
                    if(!value.matches("(\\S\\n)+"))
                        return false;
                    body.setName(value);
                    break;
                case "in":
                    body.setIn(new ArrayList<>());
                    try{
                        JSONArray arr = new JSONArray(value);
                        for(int i = 0; i < arr.length(); i++){
                            body.getIn().add(arr.getString(i));
                        }
                    }catch (Exception e){
                        body.getIn().add(value);
                    }
                    break;
                case "out":
                    body.setOut(new ArrayList<>());
                    try{
                        JSONArray arr = new JSONArray(value);
                        for(int i = 0; i < arr.length(); i++){
                            body.getOut().add(arr.getString(i));
                        }
                    }catch (Exception e){
                        body.getOut().add(value);
                    }
                    break;
                case "description":
                    body.setDescription(value);
                    break;
                case "likelihood":
                    body.setLikelihood(value);
                    break;
                case "scope":
                    body.setGlobal(value.equalsIgnoreCase("guild"));
                    break;
                case "channel":
                    body.setChannelId(value);
                    break;
            }
            return worked;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean clear(Body body, String field){
        try {
            boolean worked = true;
            switch (field.toLowerCase()) {
                case "name":
                    //body.setName("");
                    break;
                case "in":
                    body.setIn(new ArrayList<>());
                    break;
                case "out":
                    body.setOut(new ArrayList<>());
                    break;
                case "description":
                    body.setDescription("");
                    break;
                case "likelihood":
                    body.setLikelihood(1.0);
                    break;
                case "scope":
                    body.setGlobal(false);
                    break;
            }
            return worked;
        }catch (Exception e) {
            return false;
        }
    }

    private Body getActionByName(String name){
        for(ArrayList<AbstractMessageReceivedAction> list: App.messageEvent.allActions){
            for(AbstractMessageReceivedAction ar: list){
                if(ar.getBody().getName().equalsIgnoreCase(name))
                    return ar.getBody();
            }
        }
        for(Alias ar: App.ALIASES){
            if(ar.getBody().getName().equalsIgnoreCase(name))
                return ar.getBody();
        }
        return null;
    }
}
