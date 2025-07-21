package fr.nuggetreckt.omegabot.listener;

import fr.nuggetreckt.omegabot.OmegaBot;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ShutdownListener extends ListenerAdapter {

    private final OmegaBot instance;

    public ShutdownListener(OmegaBot instance) {
        this.instance = instance;
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        instance.getLogger().info("Shutting down...");
        instance.getLogger().info("Stopping tasks...");
        instance.getTasksHandler().stopTasks();
        instance.getLogger().info("Tasks stopped.");
        instance.getLogger().info("Goodbye :)");
    }
}
