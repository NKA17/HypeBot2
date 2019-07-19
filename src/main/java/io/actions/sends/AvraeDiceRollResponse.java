package io.actions.sends;

import global.Utilities;

public class AvraeDiceRollResponse extends Send {
    public AvraeDiceRollResponse(){
        super();
        getBody().setName("DiceResponse");
        getBody().setDescription("Responds to Avrae's roll outcomes.");
        getBody().getIn().add("<@(?<auth>\\d+)>.*?:game_die:[\\s\\S]*?\\*\\*Result:\\*\\*.*?(?<count>\\d+)d(?<size>\\d+)[\\s\\S]*" +
                "\\*\\*Total:\\*\\*\\s+?(?<res>\\d+)");
    }

    String authName,authTag;
    int numDie,dieSize,result;
    public boolean build(){
        numDie = Integer.parseInt(getMatcher().group("count"));
        dieSize = Integer.parseInt(getMatcher().group("size"));
        result = Integer.parseInt(getMatcher().group("res"));
        authTag = "<@"+getMatcher().group("auth")+">";
        authName = Utilities.getUserById(getMatcher().group("auth"),getEvent().getChannel()).getName();

        int max = numDie*dieSize;
        int score = (int)((((result+0.0) / (max+0.0))) * 100);

        if(score >= 80){
            getBody().setOut(
                    "https://media.giphy.com/media/26h0pHNtHKjmDo4WQ/giphy.gif",
                    "https://media.giphy.com/media/UkhHIZ37IDRGo/giphy.gif",
                    "https://media.giphy.com/media/7rj2ZgttvgomY/giphy.gif","Success!",
                    "Holy #holyrobin, "+authName+"! You got 80% or higher on your outcome!"
            );
            return true;
        }else if(result == numDie){
            getBody().setOut(
                    "https://thumbs.gfycat.com/EveryFarflungFiddlercrab-max-1mb.gif",
                    "Well... that went as badly as it could have.",
                    "Murhpy's Law, folks."
            );
            return true;
        }


        return false;
    }

}
