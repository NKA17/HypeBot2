package io.actions.memes;

import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class KKK extends Meme {

    public KKK(){
        super();
        getBody().setName("KKK");
        getBody().setDescription("Did someone say KKK?");
        getBody().getIn().add("kkk");

        populateMeme();
    }
    @Override
    public void populateMeme() {
        TextBody tb1 = new TextBody("t1",new Point(432,30));
        tb1.setFontSize(40);
        tb1.setText("DID SOMEONE SAY");
        TextBody tb2 = new TextBody("t2",new Point(432,400));
        tb2.setFontSize(55);
        tb2.setText("KKK?");

        MemeBody m1 = new MemeBody();
        m1.setName("kkk1");
        m1.setImageUrl(App.RESOURCES_PATH+"kkk1.png");
        m1.getTextBoxes().add(tb1);
        m1.getTextBoxes().add(tb2);

        MemeBody m2 = new MemeBody();
        m2.setName("kkk2");
        m2.setImageUrl(App.RESOURCES_PATH+"kkk2.png");
        m2.getTextBoxes().add(tb1.copy());
        m2.getTextBodyByName("t1").setPoint(new Point(121,30));
        m2.getTextBoxes().add(tb2.copy());
        m2.getTextBodyByName("t2").setPoint(new Point(400,400));


        MemeBody m3 = new MemeBody();
        m3.setName("kkk2");
        m3.setImageUrl(App.RESOURCES_PATH+"kkk3.jpg");
        m3.getTextBoxes().add(tb1.copy());
        m3.getTextBodyByName("t1").setPoint(new Point(530,30));
        m3.getTextBoxes().add(tb2.copy());
        m3.getTextBodyByName("t2").setPoint(new Point(530,580));

        getMemes().add(m1);
        getMemes().add(m2);
        getMemes().add(m3);
    }
}
