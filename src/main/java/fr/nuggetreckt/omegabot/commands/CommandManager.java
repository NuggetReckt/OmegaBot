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
        OptionData poll_user1 = new OptionData(OptionType.USER, "user1", "Ajoute l'utilisateur au concours", true);
        OptionData poll_user2 = new OptionData(OptionType.USER, "user2", "Ajoute l'utilisateur au concours", true);
        OptionData poll_user3 = new OptionData(OptionType.USER, "user3", "Ajoute l'utilisateur au concours", false);
        OptionData poll_user4 = new OptionData(OptionType.USER, "user4", "Ajoute l'utilisateur au concours", false);
        OptionData poll_user5 = new OptionData(OptionType.USER, "user5", "Ajoute l'utilisateur au concours", false);
        OptionData poll_user6 = new OptionData(OptionType.USER, "user6", "Ajoute l'utilisateur au concours", false);
        OptionData poll_user7 = new OptionData(OptionType.USER, "user7", "Ajoute l'utilisateur au concours", false);
        OptionData poll_user8 = new OptionData(OptionType.USER, "user8", "Ajoute l'utilisateur au concours", false);
        OptionData poll_choice1 = new OptionData(OptionType.STRING, "choice1", "Ajoute le choix 1 au sondage", true);
        OptionData poll_choice2 = new OptionData(OptionType.STRING, "choice2", "Ajoute le choix 2 au sondage", true);
        OptionData poll_choice3 = new OptionData(OptionType.STRING, "choice3", "Ajoute le choix 3 au sondage", false);
        OptionData poll_choice4 = new OptionData(OptionType.STRING, "choice4", "Ajoute le choix 4 au sondage", false);

        SubcommandGroupData createSubCommandGroupData = new SubcommandGroupData("create", "Permet de créer des sondages. (Admin uniquement)")
                .addSubcommands(new SubcommandData("poll", "Crée un sondage simple")
                        .addOptions(poll_desc, poll_choice1, poll_choice2, poll_choice3, poll_choice4))
                .addSubcommands(new SubcommandData("meme-contest", "Crée un concours de memes")
                        .addOptions(poll_desc, poll_user1, poll_user2, poll_user3, poll_user4, poll_user5, poll_user6, poll_user7, poll_user8));

        SubcommandGroupData endVoteSubCommandGroupData = new SubcommandGroupData("endvote", "Permet de terminer la phase de vote et annoncer les résultats des sondages. (Admin uniquement)")
                .addSubcommands(new SubcommandData("poll", "Termine la phase de vote et annonce les résultats du dernier concours de memes"))
                .addSubcommands(new SubcommandData("meme-contest", "Termine la phase de vote et annonce les résultats du dernier sondage"));

        commandData.add(Commands.slash("sondage", "Permet d'interagir avec les sondages. (Admin uniquement)")
                .addSubcommandGroups(createSubCommandGroupData)
                .addSubcommandGroups(endVoteSubCommandGroupData));

        OptionData suggestion_desc = new OptionData(OptionType.STRING, "description", "Description de votre suggestion", true);

        commandData.add(Commands.slash("suggestion", "Permet de faire une suggestion pour contribuer à l'amélioration du serveur.")
                .addOptions(suggestion_desc));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
