package fr.nuggetreckt.omegabot.task;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.task.impl.ChangeStatusTask;
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
        //setupTask(new TestTask(instance)); FOR TESTING ONLY
    }

    public void runTasks() {
        for (Task task : tasks) {
            task.launch();
        }
    }

    public void stopTasks() {
        for (Task task : tasks) {
            task.stop();
        }
    }

    private void setupTask(Task task) {
        tasks.add(task);
    }
}
