package fr.nuggetreckt.omegabot.statistics.leaderboard.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.statistics.MemberStats;
import fr.nuggetreckt.omegabot.statistics.leaderboard.Leaderboard;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class OverallLeaderboard extends Leaderboard {

    private final OmegaBot instance;

    public OverallLeaderboard(@NotNull OmegaBot instance) {
        super(instance.getStatsHandler().getMembersStats(), "Général");
        this.instance = instance;
    }

    @Override
    public void update() {
        membersStats = membersStats.entrySet()
                .stream()
                .sorted((o1, o2) -> (int) (o2.getValue().getScore() - o1.getValue().getScore()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    @Override
    public MessageEmbed getEmbed(@NotNull Member member) {
        List<Member> members = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        long memberScore = membersStats.get(member.getId()).getScore();
        String memberRanking = "";
        int i = 0;

        membersStats.forEach((id, stats) -> members.add(instance.getMemberById(id)));

        for (Member m : members) {
            if (m == null) continue;
            if (m.getUser().isBot()) continue;

            String name = m.getEffectiveName();
            String nb = getNumber(i + 1);

            if (m.getId().equals(member.getId())) {
                memberRanking = nb;
                name = "**" + m.getEffectiveName() + "**";
            }
            if (i < 10) {
                MemberStats stats = membersStats.get(m.getId());

                sb.append(nb).append(" ").append(name).append(" (").append(stats.getScore()).append(")\n");
            }
            i++;
        }

        embedBuilder.clearFields();
        embedBuilder.addField("__Classement__ :", sb.toString(), false)
                .addField("__Toi__ :", String.format("""
                        ・Ta place : %s
                        ・Ton score : `%d`
                        """, memberRanking, memberScore), false)
                .setTimestamp(new Date().toInstant());
        return embedBuilder.build();
    }

    private String getNumber(int i) {
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
