package io.actions.executions;

import global.App;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HypebotSays  extends Command {

    public HypebotSays() {
        super();
        getBody().setDescription("*"+ App.BOT_NAME+" says \"Something to say\"");
        getBody().setName(App.BOT_NAME+"Says");
        getBody().getIn().add("say \"(?<say>.+?)\"");
        getBody().getIn().add("say, \"(?<say>.+?)\"");
        getBody().setOut("#say");
    }

    @Override
    public boolean build() {
        return true;
    }

    @Override
    public boolean execute(boolean response) {
        getEvent().getChannel().purgeMessages(getEvent().getMessage());

        Pattern p = Pattern.compile("say(,)? \"(.+?)\" to ([\\w-]+)");
        Matcher m = p.matcher(getContent());
        if(m.find()){
            String channelName = m.toMatchResult().group(3);
            TextChannel tc = App.findSubChannel(getEvent().getGuild().getId(),channelName);
            sendResponse(getEvent().getGuild().getId(), tc.getId(), m.group(2));
        }else {
            sendResponse();
        }
        return true;
    }

}
