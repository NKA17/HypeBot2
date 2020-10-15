package external;

import global.App;

public class TextCommandHandler implements ExternalCommandHandler{
    @Override
    public void handle(ExternalCommand command) {
        App.findChannel(
                command.guildId,
                command.channelId)
                .sendMessage(command.content)
                .queue();
    }
}
