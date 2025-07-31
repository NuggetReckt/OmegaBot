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
    public MessageEmbed getEmbed() {
        List<Member> members = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        membersStats.forEach((id, stats) -> members.add(instance.getMemberById(id)));

        for (int i = 0; i < members.size(); i++) {
            if (i >= 10)
                break;
            Member member = members.get(i);
            MemberStats stats = membersStats.get(member.getId());
            String nb = getNumber(i + 1);

            sb.append(nb).append(" ").append(member.getEffectiveName()).append(" (").append(stats.getScore()).append(")\n");
        }

        embedBuilder.clearFields();
        embedBuilder.addField(" __Classement__ :", sb.toString(), false)
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
                break;
            }
        }
        return nb;
    }
}
