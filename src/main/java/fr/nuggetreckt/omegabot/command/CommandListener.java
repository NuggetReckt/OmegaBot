package fr.nuggetreckt.omegabot.command;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.command.impl.LeaderBoardCommand;
import fr.nuggetreckt.omegabot.command.impl.ShutdownCommand;
import fr.nuggetreckt.omegabot.command.impl.StatsCommand;
import fr.nuggetreckt.omegabot.command.impl.SuggestionCommand;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CommandListener extends ListenerAdapter {

    private final HashMap<String, Command> commands;
    private final OmegaBot instance;

    public CommandListener(@NotNull OmegaBot instance) {
        this.instance = instance;
        this.commands = new HashMap<>();

        registerCommands();
    }

    private void registerCommands() {
        registerCommand("suggestion", new SuggestionCommand(instance));
        registerCommand("stats", new StatsCommand(instance));
        registerCommand("leaderboard", new LeaderBoardCommand(instance));
        registerCommand("shutdown", new ShutdownCommand(instance));
    }

    private void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        MessageChannel botchannel = instance.getConfig().getBotChannel();

        if (!event.getChannel().equals(botchannel)) {
            event.reply("> Vous n'êtes pas dans le salon " + botchannel.getAsMention() + "!")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        for (String command : commands.keySet()) {
            if (event.getName().equalsIgnoreCase(command)) {
                commands.get(command).execute(event);
            }
        }
    }
}
