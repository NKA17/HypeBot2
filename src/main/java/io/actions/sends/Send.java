package io.actions.sends;

import io.actions.AbstractMessageReceivedAction;

public class Send extends AbstractMessageReceivedAction {
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
