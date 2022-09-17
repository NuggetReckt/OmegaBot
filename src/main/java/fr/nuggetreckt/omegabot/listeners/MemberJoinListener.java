package fr.nuggetreckt.omegabot.listeners;

import fr.nuggetreckt.omegabot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

import static fr.nuggetreckt.omegabot.Main.jda;

public class MemberJoinListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        EmbedBuilder join = new EmbedBuilder();
        Member member = event.getMember();

        join.setTitle("\uD83D\uDE80 ・ Wa, y'a un nouveau !")
                .setDescription("Bienvenue sur **OmegaPog®**, " + member.getAsMention() + " 👋 !\n")
                .appendDescription("On est maintenant **" + (long) event.getGuild().getMemberCount() + "** membres.")
                .setThumbnail(event.getMember().getEffectiveAvatarUrl())
                .setColor(new Color(255, 255, 255, 1))
                .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png")
                .setTimestamp(new Date().toInstant());

        Objects.requireNonNull(jda.getTextChannelById(new Config().getJoinChannelId())).sendMessageEmbeds(join.build())
                .queue();
    }
}
