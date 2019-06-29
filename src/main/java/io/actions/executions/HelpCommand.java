package io.actions.executions;

import global.App;
import io.actions.memes.DrowningMeme;

public class HelpCommand extends Command {

    DrowningMeme dm1 = new DrowningMeme();
    public HelpCommand(){
        super();
        getBody().setName("Help");
        getBody().getIn().add("help");


        dm1.addText1(App.BOT_NAME);
        dm1.addText2("Making Dank Memes");
        dm1.addText2("Literally\n Anything Else");
        dm1.addText3("#auth asking for help");
        dm1.addText3("Actually Developing\n a Help Page");

    }
    @Override
    public boolean execute(boolean response) {
        dm1.execute();
        return true;
    }

    @Override
    public boolean build() {
        dm1.setEvent(getEvent());
        return dm1.build();
    }

//    @Override
//    public void purge(){
//        super.purge();
//        ((Meme)getState().get("meme")).purge();
//    }
}
