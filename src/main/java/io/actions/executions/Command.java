package io.actions.executions;

import enums.Attributes;
import global.App;
import io.structure.Body;
import io.actions.AbstractMessageReceivedAction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Command extends AbstractMessageReceivedAction{

    public Command(){
        getBody().getAttributes().add(Attributes.EXECUTE);
        getBody().getAttributes().add(Attributes.VANILLA);
    }

    @Override
    /**
     * Attempts to find a match with the Body's regex
     * This is called before at the start of the process
     * @return
     */
    public boolean attemptToMatch(){
        try{
            if(!getContent().toLowerCase().contains(App.BOT_NAME.toLowerCase()))
                return false;

            for(String regex : getBody().getIn()){
                Matcher matcher = Pattern.compile("(?i)"+regex).matcher(getContent());
                if(matcher.find()){
                    setMatcher(matcher);
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public abstract boolean execute(boolean response);

    public boolean execute(){
        return execute(true);
    }
}
