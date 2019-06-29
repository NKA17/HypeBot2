package io.actions.memes;

import enums.Attributes;
import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class GrannyMeme extends Meme {

    public GrannyMeme(){
        super();
        getBody().setName("Granny");
        getBody().getAttributes().add(Attributes.VANILLA);
        getBody().getIn().add("(?<wtf>wtf|(what|wat) \\w+ fuck)");
        getBody().getIn().add(".{0,4}(?<wdhs>^(what|wat|wut|huh|\\?+).{0,4}$");
    }
    @Override
    public void populateMeme() {
        MemeBody meme = new MemeBody();
        meme.setImageUrl(App.RESOURCES_PATH+"granny.jpg");
        getMemes().add(meme);



        TextBody t1 = new TextBody("Granny",new Point(385,125));
        t1.setFontSize(20);
        t1.getText().add(" ");
        t1.getText().add("#auth");
        TextBody t2 = new TextBody("Caption",new Point(320,450));
        t2.setFontSize(25);
        meme.getTextBoxes().add(t1);
        meme.getTextBoxes().add(t2);
    }
}
