package fr.nuggetreckt.omegabot.listener;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.statistics.leaderboard.LeaderboardHandler;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class StringSelectListener extends ListenerAdapter {

    private final OmegaBot instance;

    public StringSelectListener(OmegaBot instance) {
        this.instance = instance;
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        LeaderboardHandler leaderboardHandler = instance.getLeaderboardHandler();

        if (event.getComponentId().equals("choose-leaderboard")) {
            String id = event.getValues().getFirst().replace("leaderboard-", "");
            MessageEmbed embed = leaderboardHandler.getLeaderboard(id).getEmbed();
            event.editMessageEmbeds(embed).queue();
        }
    }
}
