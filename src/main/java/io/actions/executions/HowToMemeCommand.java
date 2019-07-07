package io.actions.executions;

import global.MessageUtils;

public class HowToMemeCommand extends Command {

    public HowToMemeCommand(){
        super();
        getBody().setDescription("Explains how to create a meme.");
        getBody().setName("HowToMeme");
        getBody().getIn().add("how.*?(create|make|build).*?meme");
    }
    @Override
    public boolean execute(boolean response) {
        sendResponse(
                MessageUtils.chooseString("I actually don't even know how yet",
                        "I'm still working on that, myself.",
                        "That's still up in the air, honestly.",
                        "TBD... sorry."
                        ));

        return false;
    }

    @Override
    public boolean build() {
        return true;
    }
}
