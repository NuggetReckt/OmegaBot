package fr.nuggetreckt.omegabot.statistics.leaderboard;

import fr.nuggetreckt.omegabot.statistics.MemberStats;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;

public abstract class Leaderboard {

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
     * @param membersStats The member statistics stored as an HashMap (See {@link MemberStats})
     * @param displayName  The display name of the leaderboard
     */
    public Leaderboard(HashMap<String, MemberStats> membersStats, String displayName) {
        this.displayName = displayName;
        this.membersStats = membersStats;
        this.embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("\uD83C\uDFC6 ・ Leaderboard (" + displayName + ")")
                .setColor(new Color(255, 255, 255, 1))
                .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png");
    }

    /**
     * Get the display name of the leaderboard
     *
     * @return The leaderboard's display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get sorted member stats map for current leaderboard
     *
     * @return A members stats map
     */
    public HashMap<String, MemberStats> getMembersStats() {
        return membersStats;
    }

    /**
     * Updates the member list content
     */
    public abstract void update();

    /**
     * Get the embed of the current leaderboard
     *
     * @param member The member that sees the leaderboard
     * @return A {@link MessageEmbed} built with {@link EmbedBuilder}
     */
    public abstract MessageEmbed getEmbed(@NotNull Member member);
}
