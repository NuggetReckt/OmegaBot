package fr.nuggetreckt.omegabot.buttons;

import fr.nuggetreckt.omegabot.buttons.impl.RoleButton;
import fr.nuggetreckt.omegabot.buttons.impl.VerifyButton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ButtonListener extends ListenerAdapter {

    HashMap<String, Button> buttons = new HashMap<>();

    public ButtonListener(JDA jda) {
        jda.addEventListener(this);

        buttons.put("role", new RoleButton());
        buttons.put("verify", new VerifyButton());
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        for (String command : buttons.keySet()) {
            if (event.getComponentId().equalsIgnoreCase(command)) {
                buttons.get(command).execute(event);
            }
        }
    }
}
