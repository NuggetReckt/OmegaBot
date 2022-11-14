package fr.nuggetreckt.omegabot.buttons;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public abstract class Button {

    public abstract void execute(ButtonInteractionEvent event);

}
