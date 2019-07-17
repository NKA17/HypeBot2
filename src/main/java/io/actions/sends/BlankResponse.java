package io.actions.sends;

import enums.Attributes;
import io.actions.AbstractMessageReceivedAction;
import io.structure.Body;
import io.MessageSender;

public class BlankResponse extends AbstractMessageReceivedAction {


    public BlankResponse() {
        super();
        getBody().getAttributes().add(Attributes.SEND);
    }

    @Override
    public boolean build() {
        return true;
    }

    @Override
    public boolean execute() {
        try{
            MessageSender ms = new MessageSender(getEvent());
            ms.sendMessage(getBody().getOut());
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
