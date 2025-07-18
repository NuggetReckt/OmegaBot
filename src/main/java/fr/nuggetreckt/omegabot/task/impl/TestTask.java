package fr.nuggetreckt.omegabot.task.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.task.Task;

public class TestTask extends Task {

    private final OmegaBot instance;

    public TestTask(OmegaBot instance) {
        super(10, 5);
        this.instance = instance;
    }

    @Override
    protected void execute() {
        instance.getLogger().info("TestTask launched!");
    }
}
