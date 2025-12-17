package fr.nuggetreckt.omegabot.task.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.task.Task;
import net.dv8tion.jda.api.entities.Activity;

import java.util.*;

public class ChangeStatusTask extends Task {

    private final OmegaBot instance;

    private final List<String> status;
    private final Random random;

    public ChangeStatusTask(OmegaBot instance) {
        super(0, 30);

        this.instance = instance;
        this.status = new ArrayList<>();
        this.random = new Random();

        setupStatuses();
    }

    @Override
    public void execute() {
        int a = random.nextInt(status.size() - 1);
        instance.getJDA().getPresence().setActivity(Activity.playing(String.valueOf(status.get(a))));
    }

    private void setupStatuses() {
        addStatus("Dev avec ❤ par NuggetReckt");
        addStatus("OmegaBot !");
        addStatus("Herbyvor >>>> ALL");
        addStatus("\uD83C\uDF5E");
        addStatus("PogBot");
        addStatus("Version " + instance.getVersion());
    }

    private void addStatus(String message) {
        status.add(message);
    }
}