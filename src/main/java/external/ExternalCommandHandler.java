package external;

import global.App;
import io.actions.actions.BlankAction;
import io.structure.Body;

public interface ExternalCommandHandler {
    void handle(ExternalCommand command);
//    {
//        App.findChannel(
//                command.guildId,
//                command.channelId)
//                .sendMessage(command.content)
//                .queue();
//    }
}
