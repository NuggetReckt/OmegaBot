package fr.nuggetreckt.omegabot.listener;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.task.EmbedsSender;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class ReadyListener implements EventListener {

    private final OmegaBot instance;

    public ReadyListener(OmegaBot instance) {
        this.instance = instance;
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            instance.getLogger().info(instance.getJDA().getSelfUser().getName() + " v" + instance.getVersion() + " launched successfully.");
            instance.getLogger().info(instance.getJDA().getEventManager().getRegisteredListeners().size() + " loaded listeners.");
            System.out.println("""
                      ____                             ____        _
                     / __ \\                           |  _ \\      | |
                    | |  | |_ __ ___   ___  __ _  __ _| |_) | ___ | |_
                    | |  | | '_ ` _ \\ / _ \\/ _` |/ _` |  _ < / _ \\| __|
                    | |__| | | | | | |  __/ (_| | (_| | |_) | (_) | |_
                     \\____/|_| |_| |_|\\___|\\__, |\\__,_|____/ \\___/ \\__|
                                            __/ |
                                           |___/""");

            Thread initTask = new Thread(() -> {
                instance.getLogger().info("Member stats initialization...");
                instance.getStatsHandler().init();
                instance.getLogger().info("Member stats initialized successfully.");
            });
            initTask.start();

            instance.getLogger().info("Starting tasks...");
            instance.getTasksHandler().runTasks();
            instance.getLogger().info("Tasks started.");

            EmbedsSender.sendEmbeds(instance);
        }
    }
}
