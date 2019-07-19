package io.actions.aliases;

import global.App;

public class HolyRobinAlias extends Alias {
    public HolyRobinAlias(){
        super();
        getBody().setName("HolyRobin");
        getBody().setDescription("When your responses need a little extra stupid.");
        getBody().setAuthorId(App.BOT_ID);
        getBody().setAuthor(App.BOT_NAME);
        getBody().getIn().add("#holyrobin");
        getBody().setOut(
                "Oleo",
                "Graf Zeppelin",
                "Tintinnabulation",
                "Pianola",
                "Bunions",
                "Bouncy Boiler Plate",
                "Hole in a Doughnut",
                "Holy rat trap",
                "Fireworks",
                "Hood-Wink",
                "Masked Potatoes",
                "Red Snapper",
                "Human Pearls",
                "Jelly Moulds",
                "Nick of Time",
                "Movie Moguls",
                "Mechanical Marvel",
                "Stomach Ache",
                "Remote Control Robot",
                "Wedding Cake",
                "Frankenstein",
                "Hollywood",
                "Crystal Ball",
                "Human Collector’s Item",
                "Uncanny Photographic Mental Processes",
                "Nerve Center",
                "Oil Factory",
                "Shit",
                "Flapping Ears",
                "Banana Hammock",
                "Fuck",
                "Top Surgery"
        );
    }
}
