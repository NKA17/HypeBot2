package cron;

import org.json.JSONObject;

public abstract class CronJob {

    private String name;
    private String guildId;
    private String channelId;

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean trigger();
    public abstract boolean action();

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("guildId",guildId);
        json.put("channelId",channelId);
        return json;
    }
}
