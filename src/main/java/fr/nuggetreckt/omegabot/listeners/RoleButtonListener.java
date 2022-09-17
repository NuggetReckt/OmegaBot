package fr.nuggetreckt.omegabot.listeners;

import fr.nuggetreckt.omegabot.Config;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RoleButtonListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (event.getComponentId().contains("ROLE")) {
            Member member = event.getMember();
            assert member != null;

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_POLLS")) {
                Role role = Objects.requireNonNull(event.getGuild()).getRoleById(new Config().getPollsRoleId());
                assert role != null;

                if (!member.getRoles().contains(role)) {
                    event.getGuild().addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    event.getGuild().removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_ANNOUNCEMENTS")) {
                Role role = Objects.requireNonNull(event.getGuild()).getRoleById(new Config().getAnnouncementRoleId());
                assert role != null;

                if (!member.getRoles().contains(role)) {
                    event.getGuild().addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    event.getGuild().removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_EVENTS")) {
                Role role = Objects.requireNonNull(event.getGuild()).getRoleById(new Config().getEventRoleId());
                assert role != null;

                if (!member.getRoles().contains(role)) {
                    event.getGuild().addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    event.getGuild().removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_INTERESTING-INFORMATIONS")) {
                Role role = Objects.requireNonNull(event.getGuild()).getRoleById(new Config().getInterestingInformationsRoleId());
                assert role != null;

                if (!member.getRoles().contains(role)) {
                    event.getGuild().addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    event.getGuild().removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_MINECRAFT")) {
                Role role = Objects.requireNonNull(event.getGuild()).getRoleById(new Config().getMinecraftRoleId());
                assert role != null;

                if (!member.getRoles().contains(role)) {
                    event.getGuild().addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    event.getGuild().removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }

            if (Objects.requireNonNull(event.getButton().getId()).equals("ROLE_HARDWARE-TECH")) {
                Role role = Objects.requireNonNull(event.getGuild()).getRoleById(new Config().getHardwareRoleId());
                assert role != null;

                if (!member.getRoles().contains(role)) {
                    event.getGuild().addRoleToMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " ajouté avec succès.").setEphemeral(true)
                            .queue();
                } else {
                    event.getGuild().removeRoleFromMember(member, role)
                            .queue();

                    event.reply("> Rôle " + role.getAsMention() + " retiré avec succès.").setEphemeral(true)
                            .queue();
                }
            }
        }
    }
}
