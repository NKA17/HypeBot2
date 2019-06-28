package io.actions.memes;

import enums.Attributes;
import global.App;
import io.actions.AbstractMessageReceivedAction;
import io.structure.Body;
import io.structure.MemeBody;
import io.structure.TextBody;
import utils.MemePainter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Meme extends AbstractMessageReceivedAction {

    private ArrayList<MemeBody> memes = new ArrayList<>();


    public Meme(){
        Body body = new Body();
        setBody(body);
        getBody().getAttributes().add(Attributes.MEME);
    }

    private MemeBody chooseMeme(){
        Random rand = new Random();
        return memes.get(rand.nextInt(memes.size()));
    }
    @Override
    public boolean build() {
        try{
            MemeBody memeBody = chooseMeme();
            memeBody.build();
            getState().put("meme",memeBody);

            memeBody.openImage();
            Graphics g = memeBody.getImage().getGraphics();
            for(TextBody tb: memeBody.getTextBoxes()){
                MemePainter.drawCenteredString(g,tb.getText(),tb.getPoint(),App.FONT,App.FONT_BORDER_THICKNESS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean execute() {
        //TODO send image
        try {
            File outputfile = new File(App.tempFileName);
            ImageIO.write(((MemeBody) getState().get("meme")).getImage(), "png", outputfile);
            getEvent().getChannel().sendFile(outputfile, App.tempFileName).queue();

            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<MemeBody> getMemes() {
        return memes;
    }

    /**
     * Returns the first meme in the list
     * @return
     */
    public MemeBody getFirstMeme(){
        return getMemes().get(0);
    }

    public void setMemes(ArrayList<MemeBody> memes) {
        this.memes = memes;
    }

//    @Override
//    public void purge(){
//        super.purge();
//        for(MemeBody mb : memes){
//            mb.purge();
//        }
//    }
}
