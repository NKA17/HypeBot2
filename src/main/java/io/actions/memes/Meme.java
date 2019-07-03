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

public abstract class Meme extends AbstractMessageReceivedAction {

    private ArrayList<MemeBody> memes = new ArrayList<>();

    /**
     * This is where you should set the MemeBody image and text fields
     */
    public abstract void populateMeme();

    public Meme(){
        Body body = new Body();
        setBody(body);
        body.getAttributes().add(Attributes.MEME);
    }

    protected MemeBody chooseMeme(){
        Random rand = new Random();
        return memes.get(rand.nextInt(memes.size()));
    }



    @Override
    public boolean build() {
        try{
            //pick a random meme
            MemeBody memeBody = chooseMeme();
            memeBody.build();
            getState().put("meme",memeBody);

            memeBody.openImage();
            Graphics g = memeBody.getImage().getGraphics();
            for(TextBody tb: memeBody.getTextBoxes()){
                MemePainter.drawTextBody(memeBody.getImage(),tb,getEvent());
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
            File outputfile = new File(App.RESOURCES_PATH+App.tempFileName);
            ImageIO.write(
                    ((MemeBody) getState()
                            .get("meme"))
                    .getImage(),
                    "png", outputfile);

//            File temp = new File(App.RESOURCES_PATH+App.tempFileName);
//            String str = temp.toURI().toURL().toString();
//            getEmbed().setImage(temp.toURI().toURL().toString());
//            getEvent().getChannel().sendMessage(getEmbed().build()).queue();
//
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

    public void clearAllMemes(){
        for(MemeBody mb: getMemes()){
            mb.openImage();
            mb.clearAllTextBoxes();
        }
    }

//    @Override
//    public void purge(){
//        super.purge();
//        for(MemeBody mb : memes){
//            mb.purge();
//        }
//    }
}
