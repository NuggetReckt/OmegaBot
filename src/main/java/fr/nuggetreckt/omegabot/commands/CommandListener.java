package fr.nuggetreckt.omegabot.commands;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.commands.impl.SuggestionCommand;
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
    }

    private void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for (String command : commands.keySet()) {
            if (event.getName().equalsIgnoreCase(command)) {
                commands.get(command).execute(event);
            }
        }
    }
}
