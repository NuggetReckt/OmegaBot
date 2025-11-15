package fr.nuggetreckt.omegabot.statistics.leaderboard;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.statistics.MemberStats;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Leaderboard {

    /**
     * The bot instance
     */
    protected final OmegaBot instance;

    /**
     * The display name of the leaderboard
     */
    protected final String displayName;

    /**
     * The member statistics stored as an HashMap (See {@link MemberStats})
     */
    protected HashMap<String, MemberStats> membersStats;

    /**
     * Embed builder for building current leaderboard embed
     */
    protected final EmbedBuilder embedBuilder;

    /**
     * Constructor for leaderboard
     *
     * @param instance    The bot instance
     * @param displayName The display name of the leaderboard
     */
    public Leaderboard(@NotNull OmegaBot instance, String displayName) {
        this.instance = instance;
        this.displayName = displayName;
        this.membersStats = instance.getStatsHandler().getMembersStats();
        this.embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("\uD83C\uDFC6 ・ Leaderboard (" + displayName + ")")
                .setColor(new Color(255, 255, 255, 1))
                .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png");
    }

    /**
     * Updates the member list content
     */
    public abstract void update();

    /**
     * Get the display name of the leaderboard
     *
     * @return The leaderboard's display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get the embed of the current leaderboard
     *
     * @param member The member that sees the leaderboard
     * @return A {@link MessageEmbed} built with {@link EmbedBuilder}
     */
    public abstract MessageEmbed getEmbed(@NotNull Member member);

    /**
     * Get the member's ranking
     *
     * @param member The member to get the ranking for
     * @return The member's ranking
     */
    protected int getMemberRanking(@NotNull Member member) {
        AtomicInteger ranking = new AtomicInteger();

        membersStats.forEach((id, stats) -> {
            if (id.equals(member.getId()))
                return;
            ranking.getAndIncrement();
        });
        return ranking.get();
    }

    /**
     * Get formatted number for leaderboard
     *
     * @param i The number to format
     * @return The formatted number
     */
    protected String getFormattedRanking(int i) {
        String nb = String.valueOf(i);

        switch (i) {
            case 1: {
                nb = ":one:";
                break;
            }
            case 2: {
                nb = ":two:";
                break;
            }
            case 3: {
                nb = ":three:";
                break;
            }
            default: {
                nb = "#" + nb;
                break;
            }
        }
        return nb;
    }
}
