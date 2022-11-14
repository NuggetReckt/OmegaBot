package fr.nuggetreckt.omegabot.buttons.impl;

import fr.nuggetreckt.omegabot.Config;
import fr.nuggetreckt.omegabot.buttons.Button;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VerifyButton extends Button {

    @Override
    public void execute(@NotNull ButtonInteractionEvent event) {
        if (event.getComponentId().equals("VERIFY")) {
            Member member = event.getMember();
            Role role = Objects.requireNonNull(event.getGuild()).getRoleById(new Config().getMemberRoleId());

            assert role != null;
            assert member != null;

            if (!member.getRoles().contains(role)) {
                event.getGuild().addRoleToMember(member, role)
                        .queue();

                event.reply("> Vérifié ! Amusues-toi bien sur le serveur !")
                        .setEphemeral(true)
                        .queue();
            } else {
                event.reply("> Vous êtes déjà vérifié !")
                        .setEphemeral(true)
                        .queue();
            }
        }
    }
}
