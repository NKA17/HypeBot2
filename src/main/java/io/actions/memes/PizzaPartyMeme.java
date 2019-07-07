package io.actions.memes;

import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class PizzaPartyMeme extends Meme {

    public PizzaPartyMeme(){
        super();

        getBody().setName("PizzaParty");
        getBody().setDescription("Pizza Party!");
        getBody().getIn().add("pizza party|get pizza|buy pizza");

        populateMeme();
    }

    @Override
    public void populateMeme() {
        MemeBody meme = new MemeBody();
        meme.setImageUrl(App.RESOURCES_PATH+"pizza_dj.png");
        TextBody tb = new TextBody("t1",new Point(150,272));
        tb.setText("PIZZA PARTY!!");
        tb.setFontSize(30);
        meme.getTextBoxes().add(tb);
        getMemes().add(meme);
    }
}
