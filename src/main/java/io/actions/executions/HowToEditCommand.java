package io.actions.executions;

import global.App;

public class HowToEditCommand extends Command {
    public HowToEditCommand(){
        super();
        getBody().setName("HowToEditCommand");
        getBody().getIn().add("how.*?edit");
        getBody().setDescription("Explains how to edit actions.");
    }
    @Override
    public boolean execute(boolean response) {
        sendEmbed();
        return true;
    }

    @Override
    public boolean build() {
        getEmbed()
                .setTitle("Editing Actions")
                .addField("Syntax", App.BOT_NAME+", edit <meme|alias|response|action> \"<name>\" " +
                        "<add|set|clear> <field> <value>",true)
                .addField("A few examples",
                        App.BOT_NAME+", edit alias \"myAlias\" set in = [\"input1\",\"input2\"]\n" +
                                App.BOT_NAME+", edit response \"myResponse\" add in = [\"input1\",\"input2\"]\n" +
                                App.BOT_NAME+", edit response \"myResponse\" add in = \"A single addition\"\n" +
                                App.BOT_NAME+", edit action \"myAction\" clear out\n"
                        ,true);
        return true;
    }
}
