package io.actions;

import io.structure.Body;
import io.MessageSender;

public class SendAction extends AbstractMessageReceivedAction {


    public SendAction(Body body) {
        super(body);
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
