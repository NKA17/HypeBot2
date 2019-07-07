package io.actions.memes;

import global.App;
import io.structure.MemeBody;
import io.structure.TextBody;

import java.awt.*;

public class IsNotAMeme extends Meme {

    TextBody t1;
    public IsNotAMeme(){
        super();
        getBody().setName("MockingB");
        getBody().getIn().add("(?<quote>a.*?is not.*?)");
        populateMeme();
    }

    @Override
    public void populateMeme() {
        MemeBody meme = new MemeBody();
        meme.setImageUrl(App.RESOURCES_PATH+"mocking_spongebob2.jpg");
        getMemes().add(meme);


        t1 = new TextBody("t1",new Point(460,100));
        t1.setFontSize(70);
        t1.setTextBorder(4);

        TextBody t2 = new TextBody("t2",new Point(290,440));
        t2.setFontSize(45);
        t2.getText().add("#auth");
        t2.getText().add(" ");
        t2.setTextBorder(4);
        meme.getTextBoxes().add(t1);
    }


    public boolean prebuild(){
        String text = getEvent().getMessage().getContentRaw();
        String s = "";
        for(int i = 0; i < text.length(); i++){
            s+=(i%2==0)?
                    (Character.toLowerCase(text.charAt(i))):
                    (Character.toUpperCase(text.charAt(i)));
        }
        t1.setText(s);
        return true;
    }
}
