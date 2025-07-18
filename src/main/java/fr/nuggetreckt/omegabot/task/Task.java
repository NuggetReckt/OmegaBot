package fr.nuggetreckt.omegabot.task;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Task {

    /**
     * Delay in milliseconds before task is to be executed
     */
    private final long delay;
    /**
     * Time in milliseconds between successive task executions.
     */
    private final long executeInterval;

    /**
     * The timer that launches the task
     */
    private final Timer timer;

    /**
     * Task abstract class constructor
     *
     * @param delay    Delay in seconds before task is to be executed
     * @param interval Time in seconds between successive task executions.
     */
    public Task(long delay, long interval) {
        this.timer = new Timer();

        this.delay = delay * 1000;
        this.executeInterval = interval * 1000;
    }

    /**
     * Task to execute
     */
    protected abstract void execute();

    /**
     * Launches task
     */
    public void launch() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                execute();
            }
        }, delay, executeInterval);
    }

    /**
     * Stops task
     */
    void stop() {
        timer.cancel();
    }
}
