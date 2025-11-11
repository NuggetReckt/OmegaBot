package fr.nuggetreckt.omegabot.task.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.task.Task;
import fr.nuggetreckt.omegabot.util.SaveUtil;
import net.dv8tion.jda.api.entities.Member;

import java.util.Queue;

public class StatsSaveTask extends Task {

    private final OmegaBot instance;

    public StatsSaveTask(OmegaBot instance) {
        super(720 * 60, 720 * 60);

        this.instance = instance;
    }

    @Override
    protected void execute() {
        Queue<Member> members = instance.loadMembers();

        instance.getLogger().info("[AUTOSAVE] Saving stats...");
        SaveUtil.saveFile(members, instance);
        instance.getLogger().info("[AUTOSAVE] Stats saved.");
    }
}
