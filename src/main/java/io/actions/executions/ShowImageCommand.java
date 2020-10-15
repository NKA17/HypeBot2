package io.actions.executions;

import apis.SplashImageApi;
import global.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class ShowImageCommand extends Command {
    public ShowImageCommand(){
        super();
        getBody().getIn().add("show image \"(?<img>.*?)\"");
        getBody().getIn().add("show me a (?<img>\\w+)");
        getBody().getIn().add("show me some (?<img>\\w+)");
        getBody().getIn().add("show me (?<img>\\w+)");
        getBody().getIn().add("what (is|are) (a )?(?<img>\\w+)");
    }
    @Override
    public boolean execute(boolean response) {

        String str = SplashImageApi.getImage(getMatcher().group("img"));
        if(str!=null) {
            sendResponse(new String[]{
                    "Ok. Here's #img... Maybe.",
                    "This might be #img.",
                    "Is... is this #img?",
                    "There's a good chance this is a picture of a #img."
            });
            sendResponse(str);
            sendResponse( "*Courtesy of Unsplash.com*");
        }else {
            sendResponse("Strange... I couldn't find an image for that.");
        }
        return true;
    }

    @Override
    public boolean build() {
        return true;
    }
}
