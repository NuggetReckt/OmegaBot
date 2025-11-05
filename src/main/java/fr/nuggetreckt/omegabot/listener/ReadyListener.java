package fr.nuggetreckt.omegabot.listener;

import fr.nuggetreckt.omegabot.OmegaBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
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

            new Thread(() -> {
                TextChannel channel = (TextChannel) instance.getConfigHandler().getConfig().getCountChannel();
                Guild guild = channel.getGuild();

                instance.loadMembers();
                channel.upsertPermissionOverride(guild.getPublicRole())
                        .setDenied(Permission.MESSAGE_SEND).queue();
                Message message = channel.sendMessage("> **Merci de patienter le temps de l'initialisation des données.**").complete();

                instance.getLogger().info("Member stats initialization...");
                instance.getStatsHandler().init();
                instance.getLogger().info("Member stats initialized successfully. Setting up leaderboards...");
                instance.getLeaderboardHandler().init();
                instance.getLogger().info("Leaderboards set up.");

                channel.upsertPermissionOverride(guild.getPublicRole())
                        .setAllowed(Permission.MESSAGE_SEND).queue();
                message.delete().queue();
            }).start();

            instance.getLogger().info("Starting tasks...");
            instance.getTasksHandler().runTasks();
            instance.getLogger().info("Tasks started.");
        }
    }
}
