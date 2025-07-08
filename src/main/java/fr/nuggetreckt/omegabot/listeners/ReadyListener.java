package fr.nuggetreckt.omegabot.listeners;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.tasks.BotStatus;
import fr.nuggetreckt.omegabot.tasks.EmbedsSender;
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
            instance.getLogger().info(instance.getJDA().getSelfUser().getName() + " v" + instance.getVersion() + " lancé avec succès.");
            instance.getLogger().info(instance.getJDA().getEventManager().getRegisteredListeners().size() + " Listeners chargés.");
            System.out.println("""
                      ____                             ____        _
                     / __ \\                           |  _ \\      | |
                    | |  | |_ __ ___   ___  __ _  __ _| |_) | ___ | |_
                    | |  | | '_ ` _ \\ / _ \\/ _` |/ _` |  _ < / _ \\| __|
                    | |__| | | | | | |  __/ (_| | (_| | |_) | (_) | |_
                     \\____/|_| |_| |_|\\___|\\__, |\\__,_|____/ \\___/ \\__|
                                            __/ |
                                           |___/""");

            EmbedsSender.sendEmbeds(instance);
            BotStatus.setStatus(instance);
        }
    }
}
