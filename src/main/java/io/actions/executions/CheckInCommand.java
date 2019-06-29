package io.actions.executions;

import io.structure.Body;

public class CheckInCommand extends Command {

    private String[] testResponses = new String[]{
            "https://i.ytimg.com/vi/OhMXQ360aiA/maxresdefault.jpg",
            "https://pbs.twimg.com/profile_images/653700295395016708/WjGTnKGQ_400x400.png",
            "I'm awake!","Present!","Did you need something?","HypeBot here. ",
            "Can I have a moment to myself, please?","Shhh... I'm napping.",
            "Oh, am I relevant again?","I was just taking a break.","I was here the whole time.",
            "Uh... yea?","Hi, #auth!"
    };

    public CheckInCommand() {
        super();
        Body body = getBody();
        getBody().setName("CheckIn");
        body.getIn().add("check in|you there|(^| )u there|^hypebot(\\?|!)?$");
        body.setOut(testResponses);
        body.setName("CheckInCommand");
        setBody(body);
    }

    @Override
    public boolean build() {
        return true;
    }

    @Override
    public boolean execute(boolean response) {
        sendResponse();
        return true;
    }

}
