package hypebot;

import interfaces.Chainable;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ChainMonitor {

    private Chainable chain;

    public Chainable getChain() {
        return chain;
    }

    public void setChain(Chainable chain) {
        this.chain = chain;
        Thread th = new Thread(new ExpireTimer());
        th.start();
    }

    public boolean interact(GuildMessageReceivedEvent event){
        if(chain==null)
            return false;

        if(chain.match(event.getMessage().getContentRaw())){
            return chain.execute(event);
        }

        return false;
    }

    private class ExpireTimer implements Runnable{

        @Override
        public void run() {
            while(true) {
                if (chain.expire()) {
                    chain = null;
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
