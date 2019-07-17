package io.actions.sends;

import enums.Attributes;
import io.actions.AbstractMessageReceivedAction;

public class Send extends AbstractMessageReceivedAction {

    public Send(){
        super();
        getBody().getAttributes().add(Attributes.SEND);
        getBody().getAttributes().add(Attributes.VANILLA);
    }

    @Override
    public boolean build() {
        return true;
    }

    @Override
    public boolean execute() {
        sendResponse();
        return true;
    }
}
