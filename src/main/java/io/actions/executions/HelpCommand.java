package io.actions.executions;

import global.App;
import io.actions.memes.Meme;
import io.actions.memes.DrowningMeme;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class HelpCommand extends Command {

    Meme helpMeme = new DrowningMeme();
    public HelpCommand(){
        super();
        getBody().getIn().add("help");
        helpMeme = new DrowningMeme();
        MemeBody mBody = helpMeme.getFirstMeme();


        TextBody t1 = new TextBody("Life Guard",new Point(430,130), App.BOT_NAME);
        TextBody t2 = new TextBody("Trophy",new Point(259,102),"Making Dank Memes");
        TextBody t3 = new TextBody("Drowning",new Point(35,337),"#auth");
        mBody.getTextBoxes().add(t1);
        mBody.getTextBoxes().add(t2);
        mBody.getTextBoxes().add(t3);

    }
    @Override
    public boolean execute(boolean response) {
        helpMeme.execute();
        return true;
    }

    @Override
    public boolean build() {
        return helpMeme.build();
    }

//    @Override
//    public void purge(){
//        super.purge();
//        ((Meme)getState().get("meme")).purge();
//    }
}
