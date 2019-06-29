package utils;

import global.App;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class MemePainter {


    public static void drawCenteredString(BufferedImage img, String text, Point p, Font font) {
        drawCenteredString(img,text,p,font, App.FONT_BORDER_THICKNESS);
    }
    public static void drawCenteredString(BufferedImage img, String text, Point p, Font font, int thickness){
        Font temp = font;
        Font f = resizeFont(img,font,text);
        img.getGraphics().setFont(f);
        drawCenteredString(img.getGraphics(),text,p,f,thickness);
        img.getGraphics().setFont(temp);
    }
    public static void drawCenteredString(Graphics g, String text, Point p, Font font){
        drawCenteredString(g,text,p,font,4);
    }
    public static void drawCenteredString(Graphics g, String text, Point p, Font font,int thickness) {
        // Get the FontMetrics
        Font temp = font;
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = p.x - (metrics.stringWidth(text) / 2);
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = p.y - ((metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.setColor(Color.WHITE);
        drawOutlinedText(g,text, x,y,thickness);
        //g.setColor(Color.RED);
        //g.fillRect(p.x,p.y,2,2);
    }

    public static void drawOutlinedText(Graphics g, String text, int x, int y){
        drawOutlinedText(g,text,x,y,4);
    }
    public static void drawOutlinedText(Graphics g, String text, int x, int y,int thickness){
        g.setColor(Color.BLACK);
        g.drawString(text, x-thickness, y);
        g.drawString(text, x-thickness, y+thickness);
        g.drawString(text, x-thickness, y-thickness);
        g.drawString(text, x, y+thickness);
        g.drawString(text, x, y-thickness);
        g.drawString(text, x+thickness, y);
        g.drawString(text, x+thickness, y+thickness);
        g.drawString(text, x+thickness, y-thickness);

        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }

    /**
     * Returns a smaller sized version of the Font provided. Small enough to fit all text on screen.
     * @param img
     * @param f
     * @param text
     * @return
     */
    public static Font resizeFont(BufferedImage img,Font f,String text){
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        int textwidth = (int)(f.getStringBounds(text, frc).getWidth());
        while (textwidth >= img.getWidth()-10){
            f = new Font(f.getName(),f.getStyle(),f.getSize()-5);
            textwidth = (int)(f.getStringBounds(text, frc).getWidth());
        }

        return f;
    }

    public static void drawImage(Graphics g, BufferedImage image, Point p){
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(new ImageIcon(image).getImage(),p.x-image.getWidth()/2,p.y-image.getHeight()/2,null);

    }
    public static BufferedImage scaleImage(BufferedImage image, int w, int h) {
        Image scaledImage = image.getScaledInstance(w,h,Image.SCALE_SMOOTH);

        BufferedImage bimage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();
        return bimage;
    }
}
