package io.actions.sends;

import global.Utilities;
import org.json.JSONObject;

public class ChuckNorrisResponse extends Send {

    public ChuckNorrisResponse(){
        super();
        getBody().setName("ChuckNorris");
        getBody().setDescription("Chuck Norris jokes");
        getBody().getIn().add("chuck norris|mr(\\.)? norris|c(\\.) norris");
    }

    public boolean build(){

        String json = Utilities.getFromAPI("http://api.icndb.com/jokes/random");

        JSONObject response = new JSONObject(json);
        JSONObject value = response.getJSONObject("value");
        getBody().setOut("***Did You Know?***\n"+value.getString("joke").replaceAll("&quot;","\""));

        return true;
    }
}
