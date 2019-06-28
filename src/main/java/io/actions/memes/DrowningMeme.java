package io.actions.memes;

import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class DrowningMeme extends Meme {

    public DrowningMeme(){
        super();
        MemeBody meme = new MemeBody();
        meme.setImageUrl(App.RESOURCES_PATH+"drowning.png");
        getMemes().add(meme);
    }


    /**
     * This might not be necessary
     * @return
     */
    public MemeBody buildMeme1(){
        MemeBody meme1 = new MemeBody();
        meme1.setImageUrl(App.RESOURCES_PATH+"drowning.png");
        TextBody t1 = new TextBody("meme1",new Point(430,130),"");
        TextBody t2 = new TextBody("meme1",new Point(259,102),"");
        TextBody t3 = new TextBody("meme1",new Point(35,337),"");
        meme1.getTextBoxes().add(t1);
        meme1.getTextBoxes().add(t2);
        meme1.getTextBoxes().add(t3);

        return meme1;
    }
}
