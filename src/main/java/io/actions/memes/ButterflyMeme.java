package io.actions.memes;

import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class ButterflyMeme extends Meme {
    public ButterflyMeme(){
        super();

        getBody().setName("ButterflyMeme");
        getBody().setDescription("Is this a pigeon?");


        getBody().getIn().add("^is (?<item>.+?) a (?<caption>.+?)(\\s*now\\*)?(!|\\.|,|\\?|$)");
        getBody().getIn().add("(?<item>.+?) (is|has been|was) a (?<caption>.+?)(\\s*now\\s*)?(,|\\?|!|\\.|$)");

        populateMeme();
    }

    public boolean prebuild(){
        boolean skip = false;

        skip = skip || getMatcher().group("item").toLowerCase().startsWith("there");

        return !skip;
    }
    @Override
    public void populateMeme() {
        MemeBody memeBody = new MemeBody();
        memeBody.setName("IsThisAPigeon");
        memeBody.setImageUrl(App.RESOURCES_PATH+"is_this_a.jpg");

        TextBody caption = new TextBody("caption",new Point(500,550));
        caption.setFontSize(40);
        caption.setTextBorder(3);
        caption.setText("Is this a @caption?");

        TextBody target = new TextBody("target",new Point(350,120));
        target.setFontSize(40);
        target.setText("#auth");
        target.setTextBorder(3);

        TextBody item = new TextBody("item",new Point(750,100));
        item.setFontSize(40);
        item.setTextBorder(3);
        item.setText("@item");

        memeBody.getTextBoxes().add(caption);
        memeBody.getTextBoxes().add(target);
        memeBody.getTextBoxes().add(item);

        getMemes().add(memeBody);
    }
}
