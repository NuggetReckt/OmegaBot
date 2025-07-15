package fr.nuggetreckt.omegabot.command.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.command.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class LeaderBoardCommand extends Command {

    private final OmegaBot instance;

    public LeaderBoardCommand(OmegaBot instance) {
        this.instance = instance;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

    }
}
