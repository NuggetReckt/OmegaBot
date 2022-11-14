package fr.nuggetreckt.omegabot.listeners;

import fr.nuggetreckt.omegabot.tasks.BotStatus;
import fr.nuggetreckt.omegabot.tasks.EmbedsSender;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import static fr.nuggetreckt.omegabot.Main.jda;

public class ReadyListener implements EventListener {
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            System.out.println(jda.getSelfUser().getName() + " lancé avec succès. " + jda.getEventManager().getRegisteredListeners().size() + " Listeners chargés.");
            System.out.println("""
                      ____                             ____        _
                     / __ \\                           |  _ \\      | |
                    | |  | |_ __ ___   ___  __ _  __ _| |_) | ___ | |_
                    | |  | | '_ ` _ \\ / _ \\/ _` |/ _` |  _ < / _ \\| __|
                    | |__| | | | | | |  __/ (_| | (_| | |_) | (_) | |_
                     \\____/|_| |_| |_|\\___|\\__, |\\__,_|____/ \\___/ \\__|
                                            __/ |
                                           |___/""");

            new EmbedsSender().SendEmbeds();
            new BotStatus().setStatus();
        }
    }
}
