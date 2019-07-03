package io.actions.executions;

import enums.Attributes;
import global.App;
import global.MessageUtils;
import io.actions.AbstractMessageReceivedAction;
import io.actions.aliases.Alias;
import io.structure.Body;
import org.json.JSONArray;

import java.util.ArrayList;

public class EditBodyCommand extends Command {

    private String name,action,field,value;
    private Body editBody;
    //syntax
    //Hypebot, edit "<itemName>" <set|add> <name|in|out|description> "<value>"
    public EditBodyCommand(){
        super();

        getBody().setOut(MessageUtils.affirmative);
        getBody().getIn().add("edit\\s+\"(?<name>[\\s\\S]+?)\"\\s+(?<action>add|set|clear)\\s+" +
                "(?<field>name|in|out|description|desc)\\s*(\\s*=\\s*)?(?<value>(\\[[\\s\\S]+?])|(\"[\\s\\S]+?[^\\\\]\"))");
    }
    @Override
    public boolean execute(boolean response) {

        edit(editBody,action,field,value);
        App.saveAliases();
        App.saveResponses();
        App.saveActions();
        App.saveMemes();

        if(response)
            sendResponse();
        return true;
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
            value = getMatcher().group("value");
            editBody = getActionByName(name);

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
