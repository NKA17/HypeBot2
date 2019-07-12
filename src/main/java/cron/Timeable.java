package cron;

public interface Timeable {

    public boolean execute();

    public boolean trigger();
}
