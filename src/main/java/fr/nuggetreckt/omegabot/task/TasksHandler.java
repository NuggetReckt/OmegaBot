package fr.nuggetreckt.omegabot.task;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.task.impl.ChangeStatusTask;
import fr.nuggetreckt.omegabot.task.impl.LeaderboardUpdateTask;
import fr.nuggetreckt.omegabot.task.impl.SendEmbedsTask;
import fr.nuggetreckt.omegabot.task.impl.StatsSaveTask;

import java.util.ArrayList;
import java.util.List;

public class TasksHandler {

    private final OmegaBot instance;

    private final List<Task> tasks;

    public TasksHandler(OmegaBot instance) {
        this.instance = instance;
        this.tasks = new ArrayList<>();
        setupTasks();
    }

    private void setupTasks() {
        setupTask(new ChangeStatusTask(instance));
        setupTask(new StatsSaveTask(instance));
        setupTask(new SendEmbedsTask(instance));
        setupTask(new LeaderboardUpdateTask(instance));
    }

    public void runTasks() {
        for (Task task : tasks) {
            task.launch();
        }
    }

    public void stopTasks() {
        for (Task task : tasks) {
            if (task.isRunning()) {
                while (true) {
                    long startTime = System.currentTimeMillis();

                    instance.getLogger().info("Waiting for task to finish.");
                    if (!task.isRunning()) {
                        task.stop();
                        break;
                    }

                    long elapsed = System.currentTimeMillis() - startTime;
                    long sleepTime = 1000 - elapsed;
                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
                continue;
            }
            task.stop();
        }
    }

    private void setupTask(Task task) {
        tasks.add(task);
    }
}
