package fr.nuggetreckt.omegabot.statistics.leaderboard.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.statistics.MemberStats;
import fr.nuggetreckt.omegabot.statistics.leaderboard.Leaderboard;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class MagicNumberLeaderboard extends Leaderboard {

    public MagicNumberLeaderboard(@NotNull OmegaBot instance) {
        super(instance, "Nombre magique");
    }

    @Override
    public void update() {
        membersStats = membersStats.entrySet()
                .stream()
                .sorted((o1, o2) -> (int) (o2.getValue().magicNumberCount - o1.getValue().magicNumberCount))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    @Override
    public MessageEmbed getEmbed(@NotNull Member member) {
        List<Member> members = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        long counted = membersStats.get(member.getId()).magicNumberCount;
        String memberRanking = getFormattedRanking(getMemberRanking(member));
        int i = 0;

        membersStats.forEach((id, stats) -> members.add(instance.getMemberById(id)));

        for (Member m : members) {
            if (m == null) continue;
            if (m.getUser().isBot()) continue;

            String name = m.getEffectiveName();
            String nb = getFormattedRanking(i + 1);

            if (m.getId().equals(member.getId())) {
                memberRanking = nb;
                name = "**" + m.getEffectiveName() + "**";
            }
            if (i < 10) {
                MemberStats stats = membersStats.get(m.getId());

                sb.append(nb).append(" ").append(name).append(" (").append(stats.magicNumberCount).append(")\n");
            }
            i++;
        }
        if (i >= 10)
            sb.append("...\n");

        embedBuilder.clearFields();
        embedBuilder.addField("__Classement__ :", sb.toString(), false)
                .addField("__Toi__ :", String.format("""
                        ・Ta place : %s
                        ・Comptés : `%d`
                        """, memberRanking, counted), false)
                .setTimestamp(new Date().toInstant());
        return embedBuilder.build();
    }
}
