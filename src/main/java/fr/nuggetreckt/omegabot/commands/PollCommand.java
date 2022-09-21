package fr.nuggetreckt.omegabot.commands;

import fr.nuggetreckt.omegabot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static fr.nuggetreckt.omegabot.Main.jda;

public class PollCommand extends ListenerAdapter {

    EmbedBuilder pollEmbed = new EmbedBuilder();
    List<String> emojis = new ArrayList<>();
    List<OptionMapping> member;
    Message message;
    int users;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("sondage") && Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR)) {

            TextChannel botChannel = jda.getTextChannelById(new Config().getBotChannelId());
            //TextChannel pollChannel = jda.getTextChannelById(new Config().getPollChannelId());
            TextChannel memeCompetitionChannel = jda.getTextChannelById(new Config().getMemeCompetitionChannelId());

            if (Objects.equals(event.getSubcommandName(), "concours-de-memes")) {
                if (event.getChannel().equals(botChannel)) {
                    users = event.getOptionsByType(OptionType.MENTIONABLE).size();
                    member = event.getOptionsByType(OptionType.MENTIONABLE);

                    emojis.add("⬜");
                    emojis.add("\uD83D\uDFE7");
                    emojis.add("\uD83D\uDFE6");
                    emojis.add("\uD83D\uDFE5");
                    emojis.add("\uD83D\uDFEB");
                    emojis.add("\uD83D\uDFEA");
                    emojis.add("\uD83D\uDFE9");
                    emojis.add("\uD83D\uDFE8");

                    pollEmbed.setTitle("\uD83D\uDCCA ・ Concours de memes")
                            .setDescription(Objects.requireNonNull(event.getOption("description")).getAsString())
                            .addField("__Informations__", "\uD83D\uDC65 ・ **" + users + "** Participants \n\uD83D\uDCE3 ・ Organisé par "
                                    + event.getMember().getAsMention() + "\n\uD83D\uDCBE ・ Statut : " + getStatus(1), true)
                            .addField("__Participants__ ", getParticipants(), true)
                            .setColor(new Color(255, 255, 255, 1))
                            .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png")
                            .setTimestamp(new Date().toInstant());

                    message = Objects.requireNonNull(memeCompetitionChannel).sendMessageEmbeds(pollEmbed.build()).complete();

                    for (int j = 0; j < users; j++) {
                        message.addReaction(Emoji.fromFormatted(emojis.get(j))).queue();
                    }

                    event.reply("> Sondage envoyé avec succès.")
                            .setEphemeral(true)
                            .queue();
                } else {
                    event.reply("> Vous êtes dans le mauvais channel !")
                            .setEphemeral(true)
                            .queue();
                }
            } else {
                event.reply("> Vous n'avez pas la permission d'exectuter cette commande !")
                        .setEphemeral(true)
                        .queue();
            }
        }
    }

    public String getParticipants() {
        List<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < users; i++) {
            String s = emojis.get(i) + " " + Objects.requireNonNull(member.get(i).getAsMember()).getAsMention() + "\n";
            list.add(s);
        }
        for (String i : list) {
            builder.append(i);
        }
        return builder.toString();
    }

    public String getStatus(int id) {
        String status = null;
        if (id == 1) {
            status = "**Phase de vote**";
        }
        if (id == 2) {
            status = "**Phase de vote terminée**";
        }
        return status;
    }
}
