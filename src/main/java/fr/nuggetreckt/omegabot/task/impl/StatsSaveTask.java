package fr.nuggetreckt.omegabot.task.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.task.Task;

public class StatsSaveTask extends Task {

    private final OmegaBot instance;

    public StatsSaveTask(OmegaBot instance) {
        super(720 * 60, 720 * 60);

        this.instance = instance;
    }

    @Override
    protected void execute() {
        instance.getLogger().info("[AUTOSAVE] Saving stats...");
        save();
    }

    private void save() {
        Thread thread = new Thread(() -> {
            //TODO: Save stats data into JSON file
        });
        thread.start();
    }
}
