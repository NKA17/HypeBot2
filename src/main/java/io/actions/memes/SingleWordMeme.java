package io.actions.memes;

import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class SingleWordMeme extends Meme{
    public SingleWordMeme(){
        super();
        getBody().setName("Word.");
        getBody().setDescription("For final thoughts.");
        getBody().getIn().add("^(?<item>\\w+?\\.)$");
        getBody().getIn().add("lips\\((?<item>.+?)\\)");

        populateMeme();
    }
    @Override
    public void populateMeme() {
        MemeBody meme = new MemeBody();
        meme.setImageUrl(App.RESOURCES_PATH+"Lips.jpg");
        TextBody tb = new TextBody("t1",new Point(280,390));
        tb.setText("#item");
        tb.setFontSize(100);
        meme.getTextBoxes().add(tb);
        getMemes().add(meme);
    }
}
