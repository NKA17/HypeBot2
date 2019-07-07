package io.actions.memes;

public class ScienceMeme extends Meme {
    public ScienceMeme(){
        super();
        getBody().setName("Pinkman");
        getBody().setDescription("Jesse Pinkman yells in excitement.");
        getBody().getIn().add("(?<item>(\\S+\\s*){1,3})bitch!");
        getBody().getIn().add("^(?<item>.+?), bitch(!)?");
    }

    @Override
    public void populateMeme() {

    }
}
