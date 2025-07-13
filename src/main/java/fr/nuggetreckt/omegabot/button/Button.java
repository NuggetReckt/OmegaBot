package fr.nuggetreckt.omegabot.button;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public abstract class Button {

    public abstract void execute(ButtonInteractionEvent event);

}
