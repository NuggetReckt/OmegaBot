package fr.nuggetreckt.omegabot.task.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.task.Task;

public class LeaderboardUpdateTask extends Task {

    private final OmegaBot instance;

    public LeaderboardUpdateTask(OmegaBot instance) {
        super(15, 15 * 60);
        this.instance = instance;
    }

    @Override
    protected void execute() {
        instance.getLeaderboardHandler().updateLeaderboards();
    }
}
