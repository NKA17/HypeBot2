package io.actions.memes;

import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class AllOfChinaMeme extends Meme{
    public AllOfChinaMeme(){
        super();
        getBody().setName("China");
        getBody().setDescription("Now all of China knows you're here.");
        getBody().getIn().add("^((hey|yo)\\s)?(i'm|im|i am)\\s*(?<item>.*?)(\\.|,|!|\\?|$)");
        getBody().setLikelihood(.5);

        populateMeme();
    }
    @Override
    public void populateMeme() {
        MemeBody meme = new MemeBody();
        meme.setImageUrl(App.RESOURCES_PATH+"all_of_china.jpg");

        TextBody tb2 = new TextBody("t2",new Point(400,415));
        tb2.getText().add("Now, all of china knows you're #item");
        tb2.getText().add("Now, all of china knows you're... \"#item\"");
        tb2.getText().add("Now, "+App.BOT_NAME+" knows you're #item");
        tb2.setFontSize(80);
        meme.getTextBoxes().add(tb2);
        getMemes().add(meme);
    }
}
