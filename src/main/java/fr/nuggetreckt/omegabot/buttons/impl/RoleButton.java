package fr.nuggetreckt.omegabot.buttons.impl;

import fr.nuggetreckt.omegabot.Config;
import fr.nuggetreckt.omegabot.buttons.Button;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RoleButton extends Button {

    @Override
    public void execute(@NotNull ButtonInteractionEvent event) {
        if (event.getComponentId().contains("ROLE")) {
            Member member = event.getMember();
            assert member != null;

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_POLLS")) {
                Role role = new Config().getPollsRole();

                if (!member.getRoles().contains(role)) {
                    Objects.requireNonNull(event.getGuild()).addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    Objects.requireNonNull(event.getGuild()).removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_ANNOUNCEMENTS")) {
                Role role = new Config().getAnnouncementsRole();

                if (!member.getRoles().contains(role)) {
                    Objects.requireNonNull(event.getGuild()).addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    Objects.requireNonNull(event.getGuild()).removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_EVENTS")) {
                Role role = new Config().getEventsRole();

                if (!member.getRoles().contains(role)) {
                    Objects.requireNonNull(event.getGuild()).addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    Objects.requireNonNull(event.getGuild()).removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_INTERESTING-INFORMATIONS")) {
                Role role = new Config().getInterestingInformationsRole();

                if (!member.getRoles().contains(role)) {
                    Objects.requireNonNull(event.getGuild()).addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    Objects.requireNonNull(event.getGuild()).removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_MINECRAFT")) {
                Role role = new Config().getMinecraftRole();

                if (!member.getRoles().contains(role)) {
                    Objects.requireNonNull(event.getGuild()).addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    Objects.requireNonNull(event.getGuild()).removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_HARDWARE-TECH")) {
                Role role = new Config().getHardwareRole();

                if (!member.getRoles().contains(role)) {
                    Objects.requireNonNull(event.getGuild()).addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    Objects.requireNonNull(event.getGuild()).removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }
        }
    }
}
