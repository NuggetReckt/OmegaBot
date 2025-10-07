package fr.nuggetreckt.omegabot.command;

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
        OptionData statsTargetMember = new OptionData(OptionType.USER, "membre", "Membre cible pour les stats à visualiser", false);

        commandData.add(Commands.slash("suggestion", "Permet de faire une suggestion pour contribuer à l'amélioration du serveur.")
                .addOptions(suggestionDesc));

        commandData.add(Commands.slash("stats", "Permet de voir ses propres stats ou celles d'un autre membre")
                .addOptions(statsTargetMember));

        commandData.add(Commands.slash("leaderboard", "Permet de visualiser le leaderboard."));

        commandData.add(Commands.slash("shutdown", "Shutdown le bot."));

        commandData.add(Commands.slash("reload", "Reload les leaderboards."));
        commandData.add(Commands.slash("reloadconfig", "Reload la configuration."));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
