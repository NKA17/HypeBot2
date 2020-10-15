package apis;

import global.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Random;

public class SplashImageApi {

    public static String getImage(String searchWord){
        try {
            Random rand = new Random();
            JSONObject res = Utilities.makeAPIRequest("https://api.unsplash.com/search/photos?query=" + searchWord
                    , "Client-ID 6907d47a0edc1cf20d2abac466cd687fab9ae3c4bcf9c0b121e59afef1538176");
            JSONArray jarr = res.getJSONArray("results");
            JSONObject use = jarr.getJSONObject(rand.nextInt(jarr.length()));
            JSONObject urls = use.getJSONObject("urls");
            Iterator<String> iter = urls.keySet().iterator();
            if (iter.hasNext()) {
                return (urls.getString(iter.next()));
            }
            return null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
}
