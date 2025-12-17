package fr.nuggetreckt.omegabot.command.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.command.Command;
import fr.nuggetreckt.omegabot.exception.MemberNotFoundException;
import fr.nuggetreckt.omegabot.statistics.MemberStats;
import fr.nuggetreckt.omegabot.statistics.StatsHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

public class StatsCommand extends Command {

    private final OmegaBot instance;

    public StatsCommand(OmegaBot instance) {
        this.instance = instance;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        Member target = event.getMember();

        if (event.getOption("membre") != null)
            target = Objects.requireNonNull(event.getOption("membre")).getAsMember();

        if (target == null) {
            event.reply("> Une erreur est survenue lors de la récupération du membre.").setEphemeral(true).queue();
            return;
        }
        try {
            event.replyEmbeds(getStatsEmbed(target))
                    .queue();
        } catch (MemberNotFoundException e) {
            event.reply("> Le membre n'est pas participant au compteur !").setEphemeral(true).queue();
        }
    }

    @NotNull
    private MessageEmbed getStatsEmbed(@NotNull Member member) {
        StatsHandler statsHandler = instance.getStatsHandler();
        MemberStats ms = statsHandler.getMemberStats(member.getId());
        EmbedBuilder stats = new EmbedBuilder();

        stats.setTitle("\uD83D\uDCCA ・ Stats (" + member.getEffectiveName() + ")")
                .setThumbnail(member.getEffectiveAvatarUrl())
                .addField("\uD83C\uDF10 __Générales__ :", String.format("""
                        ・Participants : `%d`
                        ・Nombre actuel : `%d`
                        """, statsHandler.getActiveMembers().size(), statsHandler.getCurrentNum()), false)
                .addField("\uD83D\uDC64 __Personnelles__ :", String.format("""
                        ・Score : `%d`
                        ・Nombres comptés : `%d`
                        ・Nombres magiques : `%d`
                        ・Centaines : `%d`
                        ・Millièmes : `%d`
                        ・Dix-millièmes : `%d`
                        """, ms.getScore(), ms.counted, ms.magicNumberCount, ms.hundredsCount, ms.thousandsCount, ms.tenThousandsCount), false)
                .setColor(new Color(255, 255, 255, 1))
                .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png")
                .setTimestamp(new Date().toInstant());
        return stats.build();
    }
}
