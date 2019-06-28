package global;

import events.MessageEvent;
import io.actions.executions.CheckInCommand;
import io.actions.executions.HelpCommand;
import io.actions.executions.SilenceCommand;
import io.actions.executions.SpeakCommand;
import net.dv8tion.jda.core.JDABuilder;

import java.awt.*;
import java.util.ArrayList;

public class App {
    public static final String VERSION = "Alpha 0.0";
    public static final String BOT_NAME = "HypeBot";
    public static ArrayList<Object> ALIASES = new ArrayList<>();
    public static final String BOT_ID = "590356017976573960";
    public static final String tempFileName = "aSaucyMeme.png";
    public static final String RESOURCES_PATH = "C:\\Users\\Nate\\IdeaProjects\\HypeBot2\\src\\main\\resources\\";
    public static MessageEvent messageEvent;
    public static Font FONT = new Font("Arial Black", Font.BOLD, 20);
    public static int FONT_BORDER_THICKNESS = 4;

    public static void main(String[] args) throws Exception{
        JDABuilder jda = new JDABuilder("NTkwMzU2MDE3OTc2NTczOTYw.XQhJUw.4cwTnNLXz_fZpIVBmHax6BdPu0k");
        messageEvent = new MessageEvent();

        messageEvent.exeActions.add(new SilenceCommand());
        messageEvent.exeActions.add(new SpeakCommand());
        messageEvent.exeActions.add(new CheckInCommand());
        messageEvent.exeActions.add(new HelpCommand());

        jda.addEventListener(messageEvent);
        jda.build();

    }

    public static void setFontSize(int size){
        FONT = new Font(FONT.getName(),FONT.getStyle(),size);
    }

    public static void setFontName(String name){
        FONT = new Font(name,FONT.getStyle(),FONT.getSize());
    }
}
