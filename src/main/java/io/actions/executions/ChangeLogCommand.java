package io.actions.executions;

import global.App;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeLogCommand extends Command {

    public ChangeLogCommand(){
        super();
        getBody().setName("ChangeLogCommand");
        getBody().setAuthor(App.BOT_NAME);
        getBody().setDescription("Shows Change Logs.\n" +
                "*Syntax: "+App.BOT_NAME+", show all <changelogs>* \n\t***or***\n" +
                "*Syntax: "+App.BOT_NAME+", list <changelogs>* \n\t***or***\n" +
                "*Syntax: "+App.BOT_NAME+", show <versionNumber>*\n\t***or***\n" +
                "*Syntax: "+App.BOT_NAME+", show latest");
        getBody().getIn().add("show (change|changelog|change log|cl)\\w* (?<entry>latest|(@\\w+)?\\s*\\d+\\.\\d+\\.\\d+|all)");
        getBody().getIn().add("show (?<entry>all|latest) (changes|changelog|change log|cl)");
        getBody().getIn().add("(?<entry>list) (changes|changelog|change log|cl)");
    }
    private Pattern entryMatcher = Pattern.compile("\\w+ \\d+\\.\\d+\\.\\d+");
    private Matcher tempEntryMatcher;
    private String changeLog = "";
    @Override
    public boolean execute(boolean response) {
        if(getBody().getOut().size()>0)
            sendResponse();
        else
            sendEmbed();
        return true;
    }


    public boolean prebuild(){
        getBody().setOut(new ArrayList<>());
        try {
            Scanner sc = new Scanner(new File(App.RESOURCES_PATH+"changelogs.txt"));
            changeLog = "";
            while(sc.hasNextLine()){
                changeLog += sc.nextLine();
            }
            tempEntryMatcher = entryMatcher.matcher(changeLog);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean build() {
        String entry = getMatcher().group("entry").toLowerCase();
        if(entry.contains("latest")){
            String content = getLatestLog();
            Matcher vm = Pattern.compile("\\w+ \\d+\\.\\d+\\.\\d+").matcher(content);
            Matcher features = Pattern.compile("#features(?<notes>([\\s\\S])*?)(#|@)").matcher(content);
            Matcher bugs = Pattern.compile("#bugs(?<notes>([\\s\\S])*?)(#|@)").matcher(content);

            if(vm.find()){
                getEmbed().setTitle("**ChangeLog - "+vm.group()+"**");
            }

            if(features.find()){
                getEmbed().addField("Features",features.group("notes"),true);
            }
            if(bugs.find()){
                getEmbed().addField("Bugs",bugs.group("notes"),true);
            }
        }else if(entry.contains("all")||entry.contains("list")){
            String str = "";
            while (true){
                String temp = nextEntry();
                if(temp==null)
                    break;

                str+="*"+temp+"*\n";
            }
            getBody().setOut("***ChangeLog Entries***\n"+str);
        }else{
            String content = getVersion(entry);
            if(content!=null) {
                Matcher vm = Pattern.compile("\\w+ \\d+\\.\\d+\\.\\d+").matcher(content);
                Matcher features = Pattern.compile("#features(?<notes>([\\s\\S])*?)(#|@)").matcher(content);
                Matcher bugs = Pattern.compile("#bugs(?<notes>([\\s\\S])*?)(#|@)").matcher(content);

                if(vm.find()){
                    getEmbed().setTitle("**ChangeLog - "+vm.group()+"**");
                }

                if(features.find()){
                    getEmbed().addField("Features",features.group("notes"),true);
                }
                if(bugs.find()){
                    getEmbed().addField("Bugs",bugs.group("notes"),true);
                }
            }else{
                getBody().setOut("Could not find entry for \""+getMatcher().group("entry")+"\".");
                sendResponse();
                return false;
            }
        }


        return true;
    }

    private String getLatestLog(){
        String v = "";
        while(true){
            String temp = nextEntry();
            if(temp==null)
                break;
            v = temp;
        }

        return getVersion(v);
    }

    private String getVersion(String v){
        tempEntryMatcher.reset();
        Matcher m = Pattern.compile("(?i)(@)?(?<vers>(\\w+ )?"+v+")[\\s\\S]*?@s(?<cont>[\\s\\S]*?@f)").matcher(changeLog);
        if(m.find()){
            return "**"+m.group("vers")+"**\n"+
                    m.group("cont").replaceAll("\\s{2,}","\n\t");
        }else {
            return null;
        }
    }

    private String nextEntry(){
        if(tempEntryMatcher.find()){
            return tempEntryMatcher.group();
        }else {
            return null;
        }
    }

    public static void main(String[] args){
        ChangeLogCommand cc = new ChangeLogCommand();
        cc.setContent("HypeBot, show changelogs @Beta 1.2.3");
        cc.attemptToMatch();
        cc.prebuild();
        cc.build();
    }
}
