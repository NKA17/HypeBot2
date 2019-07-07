package io.actions.executions;

import global.App;
import io.structure.Body;

public class HowToActionCommand extends Command {
    public HowToActionCommand(){
        super();
        getBody().getIn().add("how.*?(create|make|build|customize).*?(?<type>thing|alias|response|action)");
        getBody().setName("HowToCreateAction");
        getBody().setDescription("Provides a template for creating actions. You can include the word *'detail'* " +
                "to get an in-depth explanation of all the steps.");
    }
    @Override
    public boolean execute(boolean response) {

        sendEmbed();
        return true;
    }

    @Override
    public boolean build() {
        String thing = getMatcher().group("type");
        if(thing.toLowerCase().contains("thing"))
            thing = "*thing*";
        if(getContent().toLowerCase().contains("detail")) {
            getEmbed()
                    .setTitle("Customize")
                    .setDescription("An explanation for creating custom actions.")
                    .addField("***Start by copying this!***", "\n```\n" +
                            App.BOT_NAME + ", create "+thing+" \n" +
                            "name = \"Name\" \n" +
                            "in = [\"one\",\"two\"]\n" +
                            "out = [\"one\",\"two\"]\n" +
                            "description = \"A test\"\n" +
                            "likelihood = 1.0" +
                            "\n```", true)
                    .addField("Action", "*Action* refers to anything " + App.BOT_NAME + " does. You" +
                            " create all actions the same way, with the exception of a *Meme* action which requires " +
                            "a bit more involvement from the users (*You guys*). Say *\"" + App.BOT_NAME + ", how do I " +
                            "create a Meme?\"* to find out more.", true)
                    .addField("Set a Field", "All fields are optional, except the *name* field. If you " +
                            "choose not to include a declaration for a field right now, you can always edit the action " +
                            "later by referencing its name. Say *\"" + App.BOT_NAME + ", how do I edit bodies?\" to find out more.", true)
                    .addField("", "Every action includes an element called a *Body*. The body holds " +
                            "a set of variables that are uniform across all actions. They are listed below.\n", true)
                    .addField("***Name***",
                            "A name for this action. This can be used to reference this action later. Whenever " +
                                    "an action is created with the same name as an existing action (and of the same type *Meme,Alias,etc*), " +
                                    "the existing one is removed and the new one added. Thus, you've overwritten the old one.\n" +
                                    "***Required***\n" +
                                    "Syntax: name = \"*string*\"", true)
                    .addField("***Description***", "A description of the action. What does it do? What is it's purpose?\n" +
                            "***Default=" + Body.DescDefault + "***\n" +
                            "Syntax: description = \"*string*\"", true)
                    .addField("***In***", "A list of regex patterns. When someone says something, I try to find a match with " +
                            "a regex pattern in the *In* list. If I find one, I will try to proceed with my action.\n" +
                            "***Default=[]***\n" +
                            "Syntax: in = [\"one\",\"two\",...] ***or*** in = \"one\"", true)
                    .addField("***Out***", "This is the *action* part. Memes, Aliases, Responses, Actions all interpret this one" +
                            " differently. Meme will draw and send a meme, Response will post a message to the TextChannel, " +
                            "alias will replace the *In* match with the *Out* value, and action will perform a command. If " +
                            "there is more than one *Out* element, I will pick the one I most fancy at the time. Say, " +
                            "*\"" + App.BOT_NAME + ", explain <meme|action|response|alias>\"* to find out more.\n" +
                            "***Default=[]***\n" +
                            "Syntax: out = [\"one\",\"two\"...] ***or*** out = \"one\"", true)
                    .addField("***Likelihood***",
                            "After I find a match, I decide whether I will ignore it, or execute my action. " +
                                    "This tells me how often I should choose to execute this particular action. *ie - A likelihood which " +
                                    "equals 0.75 means I will ignore it 25% of the time.\n" +
                                    "***Default=1.0***\n" +
                                    "Syntax: likelihood = *double*", true);


        }else{
            sendResponse("***Use This!***",
                            "\n```\n" +
                                    App.BOT_NAME + ", create "+thing+" \n" +
                                    "name = \"Name\" \n" +
                                    "in = [\"one\",\"two\"]\n" +
                                    "out = [\"one\",\"two\"]\n" +
                                    "description = \"A test\"\n" +
                                    "likelihood = 1.0" +
                                    "\n```");
        }
        return true;
    }
}
