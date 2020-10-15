package io.actions.memes;

import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class ScienceBitchMeme extends Meme {
    public ScienceBitchMeme(){
        super();
        getBody().setName("Pinkman");
        getBody().setDescription("Jesse Pinkman yells in excitement.");
        getBody().getIn().add("(?<item>(\\S+\\s*){1,3})bitch!");
        getBody().getIn().add("^(?<item>.+?)(,)? bitch(!)?");

        populateMeme();
    }
    @Override
    public void populateMeme() {
        MemeBody meme = new MemeBody();
        meme.setImageUrl(App.RESOURCES_PATH+"science_bitch.jpg");
        TextBody tb = new TextBody("t1",new Point(440,50));
        tb.setText("YEAH!!");
        tb.setFontSize(100);
        meme.getTextBoxes().add(tb);
        TextBody tb2 = new TextBody("t2",new Point(440,680));
        tb2.setText("#item, BITCH!");
        tb2.setFontSize(80);
        meme.getTextBoxes().add(tb2);
        getMemes().add(meme);
    }
}
