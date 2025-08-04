package fr.nuggetreckt.omegabot.command.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.command.Command;
import fr.nuggetreckt.omegabot.statistics.leaderboard.Leaderboard;
import fr.nuggetreckt.omegabot.statistics.leaderboard.LeaderboardHandler;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;

public class LeaderBoardCommand extends Command {

    private final OmegaBot instance;

    public LeaderBoardCommand(OmegaBot instance) {
        this.instance = instance;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        LeaderboardHandler leaderboardHandler = instance.getLeaderboardHandler();
        Leaderboard leaderboard = leaderboardHandler.getLeaderboard("overall");
        StringSelectMenu.Builder menu = StringSelectMenu.create("choose-leaderboard");

        leaderboardHandler.getLeaderboards().forEach((id, board) -> menu.addOption(board.getDisplayName(), "leaderboard-" + id, ""));

        if (!menu.getOptions().isEmpty())
            menu.getOptions().getFirst().withDefault(true);

        assert member != null;
        event.replyEmbeds(leaderboard.getEmbed(member))
                .setActionRow(menu.build())
                .queue();
    }
}
