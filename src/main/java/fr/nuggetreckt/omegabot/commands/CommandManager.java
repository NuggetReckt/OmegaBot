package fr.nuggetreckt.omegabot.commands;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        OptionData suggestionDesc = new OptionData(OptionType.STRING, "description", "Description de votre suggestion", true);

        commandData.add(Commands.slash("suggestion", "Permet de faire une suggestion pour contribuer à l'amélioration du serveur.")
                .addOptions(suggestionDesc));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
