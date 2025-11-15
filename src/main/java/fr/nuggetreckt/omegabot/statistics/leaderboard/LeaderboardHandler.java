package fr.nuggetreckt.omegabot.statistics.leaderboard;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.statistics.leaderboard.impl.MagicNumberLeaderboard;
import fr.nuggetreckt.omegabot.statistics.leaderboard.impl.OverallLeaderboard;

import java.util.HashMap;

public class LeaderboardHandler {

    private final OmegaBot instance;

    private final HashMap<String, Leaderboard> leaderboards;

    public LeaderboardHandler(OmegaBot instance) {
        this.instance = instance;
        this.leaderboards = new HashMap<>();
    }

    public void init() {
        setLeaderboards();
        updateLeaderboards();
    }

    public HashMap<String, Leaderboard> getLeaderboards() {
        return leaderboards;
    }

    public Leaderboard getLeaderboard(String name) {
        return leaderboards.get(name);
    }

    public void updateLeaderboards() {
        for (Leaderboard leaderboard : leaderboards.values()) {
            leaderboard.update();
        }
    }

    private void setLeaderboards() {
        setLeaderboard("overall", new OverallLeaderboard(instance));
        setLeaderboard("magic_number", new MagicNumberLeaderboard(instance));
    }

    private void setLeaderboard(String id, Leaderboard leaderboard) {
        leaderboards.putIfAbsent(id, leaderboard);
    }
}
