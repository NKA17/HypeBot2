package io.actions.memes;

import enums.Attributes;
import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class DrowningMeme extends Meme {

    public DrowningMeme(){
        super();
        getBody().setName("Drowning");
        getBody().getAttributes().add(Attributes.VANILLA);

        populateMeme();
    }


    public void populateMeme() {
        MemeBody meme = new MemeBody();
        meme.setImageUrl(App.RESOURCES_PATH+"drowning.png");
        getMemes().add(meme);


        TextBody t1 = new TextBody("t1",new Point(430,130));
        t1.setFontSize(20);
        TextBody t2 = new TextBody("t2",new Point(259,95));
        t2.setFontSize(25);
        TextBody t3 = new TextBody("t3",new Point(35,337));
        t3.setFontSize(25);
        t3.setCentered(false);
        t3.setTextBorder(0);
        meme.getTextBoxes().add(t1);
        meme.getTextBoxes().add(t2);
        meme.getTextBoxes().add(t3);

    }

    public boolean prebuild(){
        return true;
    }


    public void addText1(String str){
        getFirstMeme().getTextBodyByName("t1").getText().add(str);
    }

    public void addText2(String str){
        getFirstMeme().getTextBodyByName("t2").getText().add(str);
    }

    public void addText3(String str){
        getFirstMeme().getTextBodyByName("t3").getText().add(str);
    }

}
