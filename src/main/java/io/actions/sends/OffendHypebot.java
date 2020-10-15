package io.actions.sends;

import global.App;

public class OffendHypebot  extends Send {

    public OffendHypebot(){
        super();

        getBody().setName("FuckMeFuckYou");
        getBody().setDescription("When you offend "+ App.BOT_NAME);
        getBody().getIn().add("fuck you");
        getBody().getIn().add("kill yourself");
        getBody().getIn().add("you suck");
        getBody().getIn().add("shut up|shut the fuck up|stfu");
        getBody().getOut().add("https://pics.onsizzle.com/confused-screaming-29643438.png");
        getBody().getOut().add("If you were a spice, you would be flour.");
        getBody().getOut().add("Fuck me? No, no, no, fuck you.");
        getBody().getOut().add("https://www.memecreator.org/static/images/memes/4061955.jpg");
        getBody().getOut().add("https://media.tenor.com/images/8c9cc2232850daa2479f1bd02f91ca74/tenor.gif");
        getBody().getOut().add("https://media1.tenor.com/images/ccee9460272928577f2c48fed9359c17/tenor.gif?itemid=5388990");
        getBody().getOut().add("https://gifimage.net/wp-content/uploads/2017/02/Make-it-Rain-GIF-Image-18.gif");
        getBody().getOut().add("https://66.media.tumblr.com/a6e095cf77d09140838cc2fb4dd10ab6/tumblr_mnsvmpW2Xl1rqfhi2o1_400.gif");
        getBody().getOut().add("https://68.media.tumblr.com/301dd50f56b81499d7a1474cbfbad0d1/tumblr_nsvga2y8mw1r5rk9to2_r1_500.gif");

    }

}
