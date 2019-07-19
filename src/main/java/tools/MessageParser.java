package tools;

import io.structure.Body;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {
    private String content;
    public MessageParser(String str){
        content = str;
    }

    public Body getBody(){
        Body body = new Body();
        body.setName(getSingleString("name"));
        body.setDescription(getSingleString("description","desc"));
        body.setIn(getList("in"));
        body.setOut(getList("out"));
        body.setGlobal(true);//TODO
        body.setLikelihood(getDouble(1.0,"likelihood","like"));

        return body;
    }

    public double getDouble(double defaultVal,String... labels){
        String doub = getSingleString(false,labels);
        try {
            return Double.parseDouble(doub.trim());
        }catch (Exception e){
            return defaultVal;
        }
    }
    public ArrayList<String> getList(String...labels){
        String labelRegex = buildLabels(labels);
        ArrayList<String> ret = new ArrayList<>();
        labelRegex = "("+labelRegex+")";
        Matcher m = Pattern.compile("(?i)"+labelRegex + "(\\s*=\\s*)?\\s*(?<target>(\\[[\\s\\S]+?])|(\"[\\s\\S]+?[^\\\\]\"))").matcher(content);
        if(m.find()){
            String target = m.group("target");
            if(target.startsWith("\""))
                target = target.substring(1);
            if(target.endsWith("\""))
                target = target.substring(0,target.length()-1);

            try{
                JSONArray arr = new JSONArray(target);
                for(int i = 0; i < arr.length(); i++){
                    ret.add(arr.getString(i));
                }
            }catch (Exception e){
                ret.add(target);
            }
        }

        return ret;
    }

    public String getStringList(String... labels){
        String labelRegex = buildLabels(labels);

        ArrayList<String> ret = new ArrayList<>();
        labelRegex = "("+labelRegex+")";
        Matcher m = Pattern.compile("(?i)"+labelRegex + "(\\s*=\\s*)?\\s*(?<target>(\\[[\\s\\S]+?])|(\"[\\s\\S]+?[^\\\\]\"))").matcher(content);
        if(m.find()){
            String target = m.group("target");
            if(target.startsWith("\""))
                target = target.substring(1);
            if(target.endsWith("\""))
                target = target.substring(0,target.length()-1);

            return target;
        }
        return null;
    }

    public String getSingleString(String... labels){
        String label = buildLabels(labels);
        Matcher m = Pattern.compile("(?i)"+label+"(\\s*=\\s*)?\\s*\"(?<target>[\\s\\S]+?)\"").matcher(content);
        return extractString(m);
    }

    public String getSingleString(boolean requireQuotes,String... labels){
        String label = buildLabels(labels);
        if(requireQuotes){
            return getSingleString(labels);
        }else {
            Matcher m = Pattern.compile("(?i)" + label + "(\\s*=\\s*)?\\s*(?<target>\\w+|\"[\\s\\S]+?\")").matcher(content);
            return extractString(m);
        }

    }

    private String extractString(Matcher m){
        if(m.find()){
            String target = m.group("target");
            if(target.startsWith("\""))
                target = target.substring(1);
            if(target.endsWith("\""))
                target = target.substring(0,target.length()-1);

            return target;
        }else {
            return "null";
        }
    }

    private String buildLabels(String... labels){
        String labelRegex = "";
        for(int i = 0; i < labels.length; i++){
            if(i > 0)
                labelRegex+="|";
            labelRegex += labels[i];
        }
        return "("+labelRegex+")";
    }
}
