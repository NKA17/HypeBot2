package io.structure;

import java.awt.*;

public class TextBody {
    private Point point;
    private String text;
    private String name;
    private Font font;
    private int fontSize;


    public TextBody(String name, Point p1,String textBody){
        this.name = name;
        this.point = p1;
        text = textBody;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
