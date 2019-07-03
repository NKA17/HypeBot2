package io.structure;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;

public class MemeBody {

    private String imageUrl;
    private ArrayList<TextBody> textBoxes = new ArrayList<>();
    private String name = "Untitled";
    private BufferedImage image = null;

    public boolean build(){
        //TODO
        //This is where text is pasted on image
        //Use another class MemeBuilder to implement the drawString stuff


        return true;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<TextBody> getTextBoxes() {
        return textBoxes;
    }

    public void setTextBoxes(ArrayList<TextBody> textBoxes) {
        this.textBoxes = textBoxes;
    }

    public boolean openImage(){
        try{
            setImage(ImageIO.read(new File(getImageUrl()).toURI().toURL()));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public void purge(){
        openImage();
    }
    public TextBody getTextBodyByName(String name){
        for(TextBody tb: getTextBoxes()){
            if(tb.getName().equalsIgnoreCase(name))
                return tb;
        }
        return null;
    }

    public void clearAllTextBoxes(){
        for(TextBody tb: getTextBoxes()){
            tb.setText(new ArrayList<>());
        }
    }

    public MemeBody copy(){
        MemeBody mb = new MemeBody();
        mb.setImageUrl(getImageUrl());
        mb.setName(getName());
        mb.setImage(getImage());
        ArrayList<TextBody> textBoxes = new ArrayList<>();
        for(TextBody tb: getTextBoxes()){
            textBoxes.add(tb.copy());
        }
        mb.setTextBoxes(textBoxes);

        return mb;
    }
}
