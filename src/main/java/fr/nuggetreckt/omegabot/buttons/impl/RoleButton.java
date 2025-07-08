package fr.nuggetreckt.omegabot.buttons.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.buttons.Button;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class RoleButton extends Button {

    private final OmegaBot instance;
    private final HashMap<String, Role> roleButtons;

    public RoleButton(OmegaBot instance) {
        this.instance = instance;
        this.roleButtons = new HashMap<>();
    }

    public void setupRoleButtons() {
        setupRoleButton("ROLE_POLLS", instance.getConfig().getPollsRole());
        setupRoleButton("ROLE_ANNOUNCEMENTS", instance.getConfig().getAnnouncementsRole());
        setupRoleButton("ROLE_EVENTS", instance.getConfig().getEventsRole());
        setupRoleButton("ROLE_INTERESTING_INFORMATIONS", instance.getConfig().getInterestingInformationsRole());
        setupRoleButton("ROLE_MINECRAFT", instance.getConfig().getMinecraftRole());
        setupRoleButton("ROLE_HARDWARE_TECH", instance.getConfig().getHardwareRole());
    }

    private void setupRoleButton(String name, Role role) {
        this.roleButtons.put(name, role);
    }

    @Override
    public void execute(@NotNull ButtonInteractionEvent event) {
        if (event.getComponentId().contains("ROLE")) {
            Member member = event.getMember();
            assert member != null;

            for (String button : roleButtons.keySet()) {
                if (Objects.requireNonNull(event.getButton().getId()).equals(button)) {
                    Role role = roleButtons.get(button);

                    if (!member.getRoles().contains(role)) {
                        addRoleToMember(member, role);
                        event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                                .queue();
                    } else {
                        removeRoleFromMember(member, role);
                        event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                                .queue();
                    }
                    break;
                }
            }
        }
    }

    private void addRoleToMember(@NotNull Member member, Role role) {
        Objects.requireNonNull(member.getGuild()).addRoleToMember(member, role)
                .queue();
    }

    private void removeRoleFromMember(@NotNull Member member, Role role) {
        Objects.requireNonNull(member.getGuild()).removeRoleFromMember(member, role)
                .queue();
    }
}
