package fr.nuggetreckt.omegabot.buttons;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.buttons.impl.RoleButton;
import fr.nuggetreckt.omegabot.buttons.impl.VerifyButton;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ButtonListener extends ListenerAdapter {

    private final HashMap<String, Button> buttons;
    private final OmegaBot instance;

    public ButtonListener(OmegaBot instance) {
        this.buttons = new HashMap<>();
        this.instance = instance;

        registerButtons();
    }

    private void registerButtons() {
        registerButton("role", new RoleButton(instance));
        registerButton("verify", new VerifyButton(instance));
    }

    private void registerButton(String name, Button button) {
        buttons.put(name, button);
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        for (String command : buttons.keySet()) {
            if (event.getComponentId().toLowerCase().startsWith(command)) {
                buttons.get(command).execute(event);
            }
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        RoleButton roleButton = (RoleButton) buttons.get("role");

        roleButton.setupRoleButtons();
    }
}
