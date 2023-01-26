package fr.nuggetreckt.omegabot.commands;

import fr.nuggetreckt.omegabot.commands.impl.PollCommand;
import fr.nuggetreckt.omegabot.commands.impl.SuggestionCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CommandListener extends ListenerAdapter {

    HashMap<String, Command> commands = new HashMap<>();

    public CommandListener(@NotNull JDA jda) {
        jda.addEventListener(this);

        commands.put("sondage", new PollCommand());
        commands.put("suggestion", new SuggestionCommand());
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
