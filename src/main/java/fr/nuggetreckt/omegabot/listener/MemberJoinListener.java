package fr.nuggetreckt.omegabot.listener;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.exception.MemberNotFoundException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Date;

public class MemberJoinListener extends ListenerAdapter {

    private final OmegaBot instance;

    public MemberJoinListener(OmegaBot instance) {
        this.instance = instance;
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if (event.getUser().isBot()) return;
        EmbedBuilder join = new EmbedBuilder();
        Member member = event.getMember();

        join.setTitle("\uD83D\uDE80 ・ Wa, y'a un nouveau !")
                .setDescription("Bienvenue sur **OmegaPog®**, " + member.getAsMention() + " 👋 !\n")
                .appendDescription("On est maintenant **" + event.getGuild().getMemberCount() + "** membres.")
                .setThumbnail(event.getMember().getEffectiveAvatarUrl())
                .setColor(new Color(255, 255, 255, 1))
                .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png")
                .setTimestamp(new Date().toInstant());

        //Send embed message
        instance.getConfigHandler().getConfig().getJoinChannel().sendMessageEmbeds(join.build())
                .queue();

        try {
            instance.getStatsHandler().getMemberStats(event.getMember().getId());
        } catch (MemberNotFoundException e) {
            instance.getStatsHandler().initMemberStats(member.getId());
        }
        if (!instance.getMembers().contains(event.getMember()))
            instance.getMembers().add(member);
    }
}
