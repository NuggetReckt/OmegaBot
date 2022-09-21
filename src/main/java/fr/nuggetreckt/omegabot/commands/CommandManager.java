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

        OptionData poll_desc = new OptionData(OptionType.STRING, "description", "Description du sondage.", true);
        OptionData poll_user1 = new OptionData(OptionType.MENTIONABLE, "user1", "Ajoute l'utilisateur au concours.", true);
        OptionData poll_user2 = new OptionData(OptionType.MENTIONABLE, "user2", "Ajoute l'utilisateur au concours.", true);
        OptionData poll_user3 = new OptionData(OptionType.MENTIONABLE, "user3", "Ajoute l'utilisateur au concours.", false);
        OptionData poll_user4 = new OptionData(OptionType.MENTIONABLE, "user4", "Ajoute l'utilisateur au concours.", false);
        OptionData poll_user5 = new OptionData(OptionType.MENTIONABLE, "user5", "Ajoute l'utilisateur au concours.", false);
        OptionData poll_user6 = new OptionData(OptionType.MENTIONABLE, "user6", "Ajoute l'utilisateur au concours.", false);
        OptionData poll_user7 = new OptionData(OptionType.MENTIONABLE, "user7", "Ajoute l'utilisateur au concours.", false);
        OptionData poll_user8 = new OptionData(OptionType.MENTIONABLE, "user8", "Ajoute l'utilisateur au concours.", false);
        OptionData poll_choice1 = new OptionData(OptionType.STRING, "choice1", "Ajoute le choix 1 au sondage", true);
        OptionData poll_choice2 = new OptionData(OptionType.STRING, "choice2", "Ajoute le choix 1 au sondage", true);
        OptionData poll_choice3 = new OptionData(OptionType.STRING, "choice3", "Ajoute le choix 1 au sondage", false);
        OptionData poll_choice4 = new OptionData(OptionType.STRING, "choice4", "Ajoute le choix 1 au sondage", false);

        SubcommandGroupData subcommandGroupData = new SubcommandGroupData("create", "Permet de créer des sondages. (Admin uniquement)")
                .addSubcommands(new SubcommandData("sondage", "Créer un sondage simple")
                        .addOptions(poll_desc, poll_choice1, poll_choice2, poll_choice3, poll_choice4))
                .addSubcommands(new SubcommandData("concours-de-memes", "Créer un concours de memes")
                        .addOptions(poll_desc, poll_user1, poll_user2, poll_user3, poll_user4, poll_user5, poll_user6, poll_user7, poll_user8));
        commandData.add(Commands.slash("sondage", "Permet de créer des sondages. (Admin uniquement)")
                .addSubcommandGroups(subcommandGroupData));

        OptionData suggestion_desc = new OptionData(OptionType.STRING, "description", "Description de votre suggestion", true);

        commandData.add(Commands.slash("suggestion", "Permet de faire une suggestion pour contribuer à l'amélioration du serveur.")
                .addOptions(suggestion_desc));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
