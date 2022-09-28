package fr.nuggetreckt.omegabot.commands.impl;

import fr.nuggetreckt.omegabot.Config;
import fr.nuggetreckt.omegabot.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.awt.*;
import java.util.*;
import java.util.List;

import static fr.nuggetreckt.omegabot.Main.jda;

public class PollCommand extends Command {

    EmbedBuilder pollEmbed = new EmbedBuilder();
    EmbedBuilder resultEmbed = new EmbedBuilder();
    List<String> emojis = new ArrayList<>();
    List<OptionMapping> memberlist, choicelist;
    Message message;
    boolean ended;
    int users, choices;
    Member organizer, winner;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR)) {
            TextChannel botChannel = jda.getTextChannelById(new Config().getBotChannelId());
            TextChannel pollChannel = jda.getTextChannelById(new Config().getPollChannelId());
            TextChannel memeCompetitionChannel = jda.getTextChannelById(new Config().getMemeCompetitionChannelId());

            if (event.getChannel().equals(botChannel)) {
                pollEmbed.clear();
                pollEmbed.setColor(new Color(255, 255, 255, 1))
                        .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png")
                        .setTimestamp(new Date().toInstant());

                resultEmbed.clear();
                resultEmbed.setColor(new Color(255, 255, 255, 1))
                        .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png")
                        .setTimestamp(new Date().toInstant());

                if (Objects.equals(event.getSubcommandGroup(), "create")) {
                    organizer = event.getMember();
                    ended = false;
                    pollEmbed.setDescription(Objects.requireNonNull(event.getOption("description")).getAsString());

                    if (Objects.equals(event.getSubcommandName(), "meme-contest")) {
                        users = event.getOptionsByType(OptionType.USER).size();
                        memberlist = event.getOptionsByType(OptionType.USER);

                        emojis.add("⬜");
                        emojis.add("\uD83D\uDFE7");
                        emojis.add("\uD83D\uDFE6");
                        emojis.add("\uD83D\uDFE5");
                        emojis.add("\uD83D\uDFEB");
                        emojis.add("\uD83D\uDFEA");
                        emojis.add("\uD83D\uDFE9");
                        emojis.add("\uD83D\uDFE8");

                        pollEmbed.setTitle("\uD83C\uDFC6 ・ Concours de memes")
                                .addField("__Informations__", "\uD83D\uDC65 ・ Participants: **" + users + "**\n\uD83D\uDCE3 ・ Organisateur: "
                                        + organizer.getAsMention() + "\n\uD83D\uDCBE ・ Statut: " + getStatus(), true)
                                .addField("__Participants__ ", getParticipants(), true);

                        message = Objects.requireNonNull(memeCompetitionChannel).sendMessageEmbeds(pollEmbed.build()).complete();

                        for (int j = 0; j < users; j++) {
                            message.addReaction(Emoji.fromFormatted(emojis.get(j))).queue();
                        }

                        event.reply("> Concours de memes envoyé avec succès.")
                                .setEphemeral(true)
                                .queue();
                    }
                    if (Objects.equals(event.getSubcommandName(), "poll")) {
                        organizer = event.getMember();
                        choices = event.getOptionsByName("choice").size();
                        choicelist = event.getOptionsByName("choice");

                        System.out.println("Debug: ");
                        System.out.println(choices);
                        System.out.println(choicelist);

                        if (choices == 2) {
                            emojis.add("✅");
                            emojis.add("❌");
                        } else {
                            emojis.add("\uD83D\uDFE6");
                            emojis.add("\uD83D\uDFE7");
                            emojis.add("\uD83D\uDFE9");
                            emojis.add("\uD83D\uDFEA");
                        }

                        pollEmbed.setTitle("\uD83D\uDCCA ・ Sondage")
                                .addField("__Informations__", "\uD83D\uDCE3 ・ Organisateur: "
                                        + organizer.getAsMention() + "\n\uD83D\uDCBE ・ Statut: " + getStatus(), true)
                                .addField("__Choix__ ", getChoices(), true);

                        message = Objects.requireNonNull(pollChannel).sendMessageEmbeds(pollEmbed.build()).complete();

                        for (int j = 0; j < choices; j++) {
                            message.addReaction(Emoji.fromFormatted(emojis.get(j))).queue();
                        }

                        event.reply("> Sondage envoyé avec succès.")
                                .setEphemeral(true)
                                .queue();
                    }
                }
                if (Objects.equals(event.getSubcommandGroup(), "endvote")) {

                    ended = true;
                    //int percentage;
                    HashMap<Integer, Member> votes = new HashMap<>();

                    if (Objects.equals(event.getSubcommandName(), "meme-contest")) {

                        int nbvotes;
                        int reactions = message.getReactions().size();
                        System.out.println("Reactions: " + reactions);

                        for (int i = 0; i < reactions; i++) {
                            nbvotes = Objects.requireNonNull(message.getReaction(Emoji.fromFormatted(emojis.get(i)))).getCount();
                            System.out.println("nbvotes: " + nbvotes);
                            votes.put(nbvotes, memberlist.get(i).getAsMember());
                            System.out.println("votes: " + votes);
                        }

                        pollEmbed.setTitle("\uD83C\uDFC6 ・ Concours de memes")
                                .addField("__Informations__", "\uD83D\uDC65 ・ Participants: **" + users + "**\n\uD83D\uDCE3 ・ Organisateur: "
                                        + organizer.getAsMention() + "\n\uD83D\uDCBE ・ Statut: " + getStatus(), true)
                                .addField("__Participants__ ", getParticipants(), true);

                        resultEmbed.setTitle("\uD83C\uDFC6 ・ Résultats")
                                .setDescription("**GG à "
                                        //+ winner.getAsMention()
                                        + " qui obtiens le grade <grade> avec " + "<%>" + " des voix !** \uD83C\uDF89");

                        message.replyEmbeds(resultEmbed.build()).queue();
                        message.editMessageEmbeds(pollEmbed.build()).queue();

                        event.reply("> Résultats envoyés avec succès.")
                                .setEphemeral(true)
                                .queue();
                    }
                    if (Objects.equals(event.getSubcommandName(), "poll")) {
                        pollEmbed.setTitle("\uD83D\uDCCA ・ Sondage")
                                .addField("__Informations__", "\uD83D\uDCE3 ・ Organisateur: "
                                        + organizer.getAsMention() + "\n\uD83D\uDCBE ・ Statut: " + getStatus(), true)
                                .addField("__Choix__ ", getChoices(), true);

                        message.editMessageEmbeds(pollEmbed.build()).queue();
                    }
                }
            } else {
                event.reply("> Vous êtes dans le mauvais channel !")
                        .setEphemeral(true)
                        .queue();
            }
        } else {
            event.reply("> Vous n'avez pas la permission d'executer cette commande !")
                    .setEphemeral(true)
                    .queue();
        }
    }

    public String getParticipants() {
        List<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < users; i++) {
            String s = emojis.get(i) + " " + Objects.requireNonNull(memberlist.get(i).getAsMember()).getAsMention() + "\n";
            list.add(s);
        }
        for (String i : list) {
            builder.append(i);
        }
        return builder.toString();
    }

    public String getChoices() {
        List<String> list2 = new ArrayList<>();
        StringBuilder builder2 = new StringBuilder();
        for (int i = 0; i < choices; i++) {
            String s = emojis.get(i) + " " + choicelist.get(i) + "\n";
            list2.add(s);
        }
        for (String i : list2) {
            builder2.append(i);
        }
        return builder2.toString();
    }

    public String getStatus() {
        String status = null;
        if (!ended) {
            status = "**Phase de vote**";
        }
        if (ended) {
            status = "**Phase de vote terminée**";
        }
        return status;
    }
}