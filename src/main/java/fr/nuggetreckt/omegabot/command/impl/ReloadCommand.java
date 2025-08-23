package fr.nuggetreckt.omegabot.command.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.command.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends Command {

    private final OmegaBot instance;

    public ReloadCommand(OmegaBot instance) {
        this.instance = instance;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        Member member = event.getMember();

        assert member != null;
        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("> Vous n'avez pas la permission !").setEphemeral(true)
                    .queue();
            return;
        }
        event.reply("> Mise à jour des leaderboards.").setEphemeral(true)
                .queue();

        instance.getLeaderboardHandler().updateLeaderboards();
    }
}
