package utils;

import global.App;
import io.structure.TextBody;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemePainter {

    private static Matcher matcher = null;

    public static void drawCenteredString(BufferedImage img, String text, Point p, Font font) {
        drawCenteredString(img,text,p,font, App.FONT_BORDER_THICKNESS);
    }
    public static void drawCenteredString(BufferedImage img, String text, Point p, Font font, int thickness){
        Matcher m = Pattern.compile("([\\s\\S]*?)\\n([\\s\\S]*)").matcher(text);
        if (m.find()){
            String pText = m.toMatchResult().group(1);
            drawCenteredStringHelper(img,pText,p,font,thickness);
            Point newP = calcIndentPoint(text,font,p);
            drawCenteredString(img,m.toMatchResult().group(2),newP,font,thickness);
        }else {
            drawCenteredStringHelper(img,text,p,font,thickness);
        }
    }
    private static void drawCenteredStringHelper(BufferedImage img, String text, Point p, Font font, int thickness){
        Font temp = font;
        Font f = resizeFont(img,p,font,text);
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


        if(thickness==0)
            return;

        g.setColor(Color.BLACK);
        for(int i = -thickness; i <= thickness; i++) {
            for (int j = -thickness; j <= thickness; j++) {
                if (i == j) continue;

                g.drawString(text, x + i, y + j);
            }
        }

//        g.drawString(text, x-thickness, y);
//        g.drawString(text, x-thickness, y+thickness);
//        g.drawString(text, x-thickness, y-thickness);
//        g.drawString(text, x, y+thickness);
//        g.drawString(text, x, y-thickness);
//        g.drawString(text, x+thickness, y);
//        g.drawString(text, x+thickness, y+thickness);
//        g.drawString(text, x+thickness, y-thickness);

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
    public static Font resizeFont(BufferedImage img,Point p,Font f,String text){
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        int textwidth = (int)(f.getStringBounds(text, frc).getWidth());
        int imwidth = img.getWidth();
        while((p.x - textwidth/2  < 10) || (textwidth / 2 + p.x > img.getWidth()-10)){
            if(f.getSize()-5 <= 1)
                break;
            f = new Font(f.getName(),f.getStyle(),f.getSize()-5);
            textwidth = (int)(f.getStringBounds(text, frc).getWidth());
        }

        return f;
    }

    public static void drawString(BufferedImage bim, String text, Point p, Font f, int thickness){
        Matcher m = Pattern.compile("([\\s\\S]*?)\\n([\\s\\S]*)").matcher(text);
        if (m.find()){
            String pText = m.toMatchResult().group(1);
            drawStringHelper(bim,pText,p,f,thickness);
            Point newP = calcIndentPoint(text,f,p);
            drawString(bim,m.toMatchResult().group(2),newP,f,thickness);
        }else {
            drawStringHelper(bim,text,p,f,thickness);
        }
    }

    private static void drawStringHelper(BufferedImage bim, String text, Point p, Font f, int thickness){
        Graphics g = bim.getGraphics();
        g.setFont(f);
        drawOutlinedText(g,text,p.x,p.y,thickness);
        g.drawString(text, p.x, p.y);
    }

    public static void drawTextBody(BufferedImage bim, TextBody tb){
        if(!tb.isCentered()){
            drawString(
                    bim,
                    tb.chooseText(),
                    tb.getPoint(),
                    new Font(tb.getName(),tb.getFontStyle(),tb.getFontSize()),
                    tb.getTextBorder());
        }else {
            if (tb.isElastic())
                MemePainter.drawCenteredString(
                        bim,
                        tb.chooseText(),
                        tb.getPoint(),
                        new Font(tb.getName(), tb.getFontStyle(), tb.getFontSize()),
                        tb.getTextBorder());
            else
                MemePainter.drawCenteredString(
                        bim.getGraphics(),
                        tb.chooseText(),
                        tb.getPoint(),
                        new Font(tb.getName(), tb.getFontStyle(), tb.getFontSize()),
                        tb.getTextBorder());
        }
    }

    public static void drawTextBody(BufferedImage bim, TextBody tb, GuildMessageReceivedEvent event){
        if(!tb.isCentered()){
            drawString(
                    bim,
                    tb.chooseTextAndApplyAliases(event),
                    tb.getPoint(),
                    new Font(tb.getName(),tb.getFontStyle(),tb.getFontSize()),
                    tb.getTextBorder());
        }else {
            if (tb.isElastic())
                MemePainter.drawCenteredString(
                        bim,
                        tb.chooseTextAndApplyAliases(event),
                        tb.getPoint(),
                        new Font(tb.getName(), tb.getFontStyle(), tb.getFontSize()),
                        tb.getTextBorder());
            else
                MemePainter.drawCenteredString(
                        bim.getGraphics(),
                        tb.chooseTextAndApplyAliases(event),
                        tb.getPoint(),
                        new Font(tb.getName(), tb.getFontStyle(), tb.getFontSize()),
                        tb.getTextBorder());
        }
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

    private static Point calcIndentPoint(String text, Font f, Point p){
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        int th = (int)(f.getStringBounds(text, frc).getHeight());

        return new Point(p.x,p.y+th-2);
    }

    public static Matcher getMatcher() {
        return matcher;
    }

    public static void setMatcher(Matcher matcher) {
        matcher = matcher;
    }
}
