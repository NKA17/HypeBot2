package io.structure;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
            setImage(ImageIO.read(new URL(getImageUrl())));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public void purge(){
        openImage();
    }
}
