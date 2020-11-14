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

        TextBody tb1 = new TextBody("t1",new Point(130,150));
        tb1.setText("#picauth");
        TextBody tb2 = new TextBody("t2",new Point(350,40));
        tb2.setCentered(true);
        tb2.setFontSize(45);
        tb2.getText().add("Now introducing...");
        tb2.getText().add("Ladies and Gentlemen...");
        tb2.getText().add("Holy Fuck! Its...");
        tb2.getText().add("Jesus Christ, its...");
        tb2.getText().add("For the first time in history!");
        tb2.getText().add("Hey guys, #auth changed his name to");
        tb2.getText().add("Is that... It is! OMG ITS...");
        tb2.getText().add("Its not #oldnick anymore! Its...");
        TextBody tb3 = new TextBody("t3",new Point(350,300));
        tb3.setFontSize(55);
        tb3.setText("#newnick!");

        MemeBody m1 = new MemeBody();
        m1.setName("JasonBourne");
        m1.setImageUrl(App.RESOURCES_PATH+"jasonBourne.jpg");
        m1.getTextBoxes().add(tb1.copy());
        m1.getTextBodyByName("t1").setPoint(new Point(380,80));
        m1.getTextBoxes().add(tb2);
        m1.getTextBoxes().add(tb3);

        MemeBody m2 = new MemeBody();
        m2.setName("Parade");
        m2.setImageUrl(App.RESOURCES_PATH+"parade.jpg");
        m2.getTextBoxes().add(tb1.copy());
        m2.getTextBodyByName("t1").setPoint(new Point(120,200));
        m2.getTextBoxes().add(tb2.copy());
        m2.getTextBodyByName("t2").setPoint(new Point(320,60));
        m2.getTextBodyByName("t2").setFontSize(70);
        m2.getTextBoxes().add(tb3.copy());
        m2.getTextBodyByName("t3").setFontSize(55);
        m2.getTextBodyByName("t3").setPoint(new Point(320,375));


        MemeBody m3 = new MemeBody();
        TextBody m3T1 = new TextBody("m3T1",new Point(400,150));
        m3T1.setFontSize(50);
        m3T1.setText("It's");
        m3.setName("Whos That Pokemon");
        m3.setImageUrl(App.RESOURCES_PATH+"whosthat.jpg");
        m3.getTextBoxes().add(tb1.copy());
        m3.getTextBoxes().add(m3T1.copy());
        m3.getTextBoxes().add(tb3.copy());
        m3.getTextBodyByName("t3").setFontSize(55);
        m3.getTextBodyByName("t3").setPoint(new Point(400,200));
        m3.getTextBodyByName("t1").setPoint(new Point(130,150));

        getMemes().add(m1);
        getMemes().add(m2);
        getMemes().add(m3);
    }
}
