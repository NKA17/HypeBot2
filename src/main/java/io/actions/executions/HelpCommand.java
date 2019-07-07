package io.actions.executions;

import global.App;
import io.actions.memes.BlankMeme;
import io.actions.memes.DrowningMeme;
import io.actions.memes.GrannyMeme;
import io.actions.memes.Meme;
import io.structure.MemeBody;

public class HelpCommand extends Command {

    Meme bm = new BlankMeme();
    public HelpCommand(){
        super();
        getBody().setName("Help");
        getBody().getIn().add("help");
        getBody().setDescription("*"+App.BOT_NAME+", help.*\nOpens a Help page.");

        DrowningMeme dm1 = new DrowningMeme();
        GrannyMeme gm = new GrannyMeme();


        dm1.populateMeme();
        gm.clearAllMemes();
        dm1.addText1(App.BOT_NAME);
        dm1.addText2("Making Dank Memes");
        dm1.addText2("Literally\n Anything Else");
        dm1.addText3("#auth \nasking for help");
        dm1.addText3("Actually Developing\n a Help Page");

        gm.populateMeme();
        gm.clearAllMemes();
        gm.addText1("#auth");
        gm.addText2("How the hell do I work this?");
        gm.addText2("Trying to learn "+App.BOT_NAME+".");
        gm.addText2("Trying to find the Help page.");

        MemeBody m1 = dm1.getFirstMeme().copy();
        MemeBody m2 = gm.getFirstMeme().copy();
        bm.getMemes().add(m1);
        bm.getMemes().add(m1);
        bm.getMemes().add(m2);

    }
    @Override
    public boolean execute(boolean response) {
        bm.execute();
        return true;
    }

    @Override
    public boolean build() {
        bm.setEvent(getEvent());
        return bm.build();
    }

//    @Override
//    public void purge(){
//        super.purge();
//        ((Meme)getState().get("meme")).purge();
//    }
}
