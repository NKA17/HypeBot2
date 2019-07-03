package io.actions.memes;

import enums.Attributes;
import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;
import java.util.ArrayList;

public class CoolsvilleA extends Meme {

    TextBody t1 = new TextBody("t1",new Point(306,432));
    public CoolsvilleA(){
        super();
        getBody().setName("CoolsvilleA");
        getBody().getAttributes().add(Attributes.VANILLA);

        getBody().getIn().add("(?i)(?<self>I'm|I am|Im|I)\\s+(((going to|goin|ganna|wanna|want to|just)\\s+)*\\s*)?(chill'n|chilling|chillin|chill|cool|relaxing|relaxed|relax)");
        getBody().getIn().add("((?<you>\\w+)(,)? chill)");
        getBody().getIn().add("(chill(,)? (?<you>\\w+))");

        populateMeme();
    }
    @Override
    public void populateMeme() {
        MemeBody meme = new MemeBody();
        meme.setImageUrl(App.RESOURCES_PATH+"coolsville.jpg");
        getMemes().add(meme);


        t1.setFontSize(20);
        t1.setCentered(false);
        getFirstMeme().getTextBoxes().add(t1);

    }

    public boolean prebuild(){

        try{
            String auth = getMatcher().group("self");
            t1.setText("#auth");
            return true;
        }catch (Exception e){
            //that's ok
        }

        try{

            String victim = getMatcher().group("you");
            t1.setText(victim);
            return true;
        }catch (Exception e){
            //that's ok
        }


        return false;
    }

}
