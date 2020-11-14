package external;

import global.App;
import global.Defaults;
import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedCommandHandler implements ExternalCommandHandler{
    @Override
    public void handle(ExternalCommand command) {
        EmbedBuilder eb = Defaults.getEmbedBuilder();
        String[] parts = command.content.split(";");
        eb.setTitle(parts[0]);

        if(parts.length==3){
            eb.addField(parts[1],parts[2],false);
        }else if(parts.length == 2){
            eb.addField(".",parts[1],false);
        }else {
            System.out.println("ERROR: Need at least 2 or 3 args for embed command, but got "+parts.length+".");
        }

        App.findChannel(
                command.guildId,
                command.channelId)
                .sendMessage(eb.build())
                .queue();
    }
}
