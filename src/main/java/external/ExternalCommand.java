package external;

public class ExternalCommand {
    public String guildId;
    public String channelId;
    public String content;
    public String type;

    public ExternalCommand(String type, String guild, String channel, String content) {
        this.guildId = guild;
        this.channelId = channel;
        this.content = content;
        this.type = type;
    }
}
