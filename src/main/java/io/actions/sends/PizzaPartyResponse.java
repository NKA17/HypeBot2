package io.actions.sends;

import io.actions.AbstractMessageReceivedAction;
import io.actions.memes.PizzaPartyMeme;

public class PizzaPartyResponse extends Send {

    public PizzaPartyResponse(){
        super();

        getBody().setName("PizzaParty");
        getBody().setDescription("Pizza Party!");
        getBody().getIn().add("pizza party|get pizza");
        getBody().getOut().add("https://media.giphy.com/media/bj09BK2BzLLQk/giphy.gif");
        getBody().getOut().add("**PIZZA PARTY!!** I would buy you all pizza, but I'm not real.");
        getBody().getOut().add("Make it a *sexy* pizza party, and I'm in.");
        getBody().getOut().add("What #auth said!");
    }
}
