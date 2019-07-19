package io.structure;

import cron.HypeBotCronJob;
import global.App;
import global.MessageUtils;
import hypebot.HypeBotContext;
import io.MessageSender;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class TextBody {
    private Point point;
    private ArrayList<String> text;
    private String name;
    private String fontName;
    private int fontStyle = Font.BOLD;
    private int fontSize;
    private boolean elastic = true;
    private int textBorder;
    private boolean centered = true;
    private int maxImageWidth = 100;


    public TextBody(String name, Point p1,String textBody){
        this.name = name;
        this.point = p1;
        text = new ArrayList<>();
        fontName = App.getFontName();
        fontStyle = App.getFontStyle();
        fontSize = App.getFontSize();
        textBorder = App.FONT_BORDER_THICKNESS;
        text = new ArrayList<>();
        text.add(textBody);
    }

    public TextBody(String name, Point p1){
        this.name = name;
        this.point = p1;
        text = new ArrayList<>();
        fontName = App.getFontName();
        fontStyle = App.getFontStyle();
        fontSize = App.getFontSize();
        textBorder = App.FONT_BORDER_THICKNESS;
    }

    public int getTextBorder() {
        return textBorder;
    }

    public void setTextBorder(int textBorder) {
        this.textBorder = textBorder;
    }

    public boolean isElastic() {
        return elastic;
    }

    public void setElastic(boolean elastic) {
        this.elastic = elastic;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
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

    public String chooseText() {
        return MessageUtils.chooseString(text);
    }

    public int getMaxImageWidth() {
        return maxImageWidth;
    }

    public void setMaxImageWidth(int maxImageWidth) {
        this.maxImageWidth = maxImageWidth;
    }

    //    public String chooseTextAndApplyAliases(GuildMessageReceivedEvent event){
//        String mess = MessageUtils.chooseString(text);
//        mess = MessageUtils.applyAliases(event,mess,App.ALIASES);
//        return mess;
//    }

    public String chooseTextAndApplyAliases(GuildMessageReceivedEvent event){
        HypeBotContext hbc = App.HYPEBOT.getContext(event.getGuild().getId());
        String mess = MessageUtils.chooseString(text);
        mess = MessageUtils.applyAliases(event,mess,hbc.getAliases());
        return mess;
    }

    public ArrayList<String> getText(){
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }

    public void setText(String s){
        ArrayList<String> arr = new ArrayList<>();
        arr.add(s);
        setText(arr);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCentered() {
        return centered;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public TextBody copy(){
        TextBody tb = new TextBody(null,null);
        tb.setPoint(getPoint());
        tb.setText(getText());
        tb.setName(getName());
        tb.setFontName(getFontName());
        tb.setFontStyle(getFontStyle());
        tb.setFontSize(getFontSize());
        tb.setElastic(isElastic());
        tb.setTextBorder(getTextBorder());
        tb.setCentered(isCentered());

        return tb;
    }
}
