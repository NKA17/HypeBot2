package io.actions.memes;

import enums.Attributes;
import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class NickNameMeme extends Meme {
    protected TextBody t1;
    protected TextBody t2;
    public NickNameMeme(){
        super();

        getBody().setName("NickName");
        getBody().setDescription("Introducing!");
        getBody().getAttributes().add(Attributes.VANILLA);
        getBody().getIn().add("*Compiled code*");

        getBody().setLikelihood(1.0);
        populateMeme();
    }
    @Override
    public void populateMeme() {
        TextBody tb1 = new TextBody("t1",new Point(432,30));
        tb1.setFontSize(40);
        tb1.getText().add("Now introducing...");
        tb1.getText().add("Ladies and Gentlemen...");
        tb1.getText().add("Holy Fuck! Its...");
        tb1.getText().add("Jesus Christ, its...");
        tb1.getText().add("For the first time in history!");
        tb1.getText().add("Hey guys, #auth changed his name to");
        TextBody tb2 = new TextBody("t2",new Point(432,400));
        tb2.setFontSize(55);
        tb2.setText("#nickname");

        MemeBody m1 = new MemeBody();
        m1.setName("Trumpets");
        m1.setImageUrl(App.RESOURCES_PATH+"trumpets.jpg");
        m1.getTextBoxes().add(tb1);
        m1.getTextBoxes().add(tb2);

        MemeBody m2 = new MemeBody();
        m2.setName("Parade");
        m2.setImageUrl(App.RESOURCES_PATH+"parade.png");
        m2.getTextBoxes().add(tb1.copy());
        m2.getTextBodyByName("t1").setPoint(new Point(121,30));
        m2.getTextBoxes().add(tb2.copy());
        m2.getTextBodyByName("t2").setPoint(new Point(400,400));

        getMemes().add(m1);
        //getMemes().add(m2);
    }
}
