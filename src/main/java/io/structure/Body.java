package io.structure;

import enums.Attributes;

import java.util.ArrayList;

public class Body {

    /**
     * All these fields are in here to make custom actions from command line easy
     * as well as interfacing with those actions
     */
    private String name = "Untitled";
    private String description = "No description";
    private boolean on = true;
    private String author = "";
    private ArrayList<String> in = new ArrayList<String>();
    private ArrayList<String> out = new ArrayList<String>();
    private ArrayList<Attributes> attributes = new ArrayList<Attributes>();

    public String[] getInAsArray(){
        String[] arr = new String[in.size()];
        return in.toArray(arr);
    }
    public String[] getOutAsArray(){
        String[] arr = new String[out.size()];
        return out.toArray(arr);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Attributes> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Attributes> attributes) {
        this.attributes = attributes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<String> getIn() {
        return in;
    }

    public void setIn(ArrayList<String> in) {
        this.in = in;
    }

    public ArrayList<String> getOut() {
        return out;
    }

    public void setOut(ArrayList<String> out) {
        this.out = out;
    }

    public void setIn(String[] in){
        ArrayList<String> n = new ArrayList<>();
        for(String s: in){
            n.add(s);
        }
        setIn(n);
    }

    public void setOut(String[] out){
        ArrayList<String> n = new ArrayList<>();
        for(String s: out){
            n.add(s);
        }
        setOut(n);
    }
}
