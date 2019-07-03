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
        getBody().getIn().add(".{0,4}(?<wdhs>^(what|wat|wut|huh|\\?+)).{0,4}$");

        populateMeme();
    }
    @Override
    public void populateMeme() {
        MemeBody meme = new MemeBody();
        meme.setImageUrl(App.RESOURCES_PATH+"granny.jpg");
        getMemes().add(meme);



        TextBody t1 = new TextBody("t1",new Point(385,125));
        t1.setFontSize(40);
        TextBody t2 = new TextBody("t2",new Point(320,450));
        t2.setFontSize(50);
        meme.getTextBoxes().add(t1);
        meme.getTextBoxes().add(t2);
        addText2("WHAT THE FUCK?");
        addText1("#auth");
    }

    public boolean prebuild(){
        return true;
    }


    public void addText1(String text){
        getFirstMeme().getTextBodyByName("t1").getText().add(text);
    }
    public void addText2(String text){
        getFirstMeme().getTextBodyByName("t2").getText().add(text);
    }
}
