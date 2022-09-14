package fr.nuggetreckt.omegabot.tasks;

import fr.nuggetreckt.omegabot.Config;
import fr.nuggetreckt.omegabot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

public class EmbedSenders {

    public static void main(String[] args) {
        try {
            new EmbedSenders().takeRoleEmbedSender();
            new EmbedSenders().verifyEmbedSender();
            new EmbedSenders().rulesEmbedSender();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void takeRoleEmbedSender() {
        String takeRoleChannelId = new Config().getTakeRoleChannelId();
        TextChannel takeRoleChannel = Main.jda.getTextChannelById(takeRoleChannelId);

        if (Objects.requireNonNull(takeRoleChannel).getHistory().isEmpty()) {
            EmbedBuilder takeRoleEmbed = new EmbedBuilder();

            takeRoleEmbed.setTitle("")
                    .setDescription("")
                    .addField("Mentions", "", true)
                    .addField("Accès salons spécifiques", "", true)
                    .setColor(new Color(61, 189, 201, 1))
                    .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png")
                    .setTimestamp(new Date().toInstant());

            takeRoleChannel.sendMessageEmbeds(takeRoleEmbed.build())
                    .setActionRow(

                    )
                    .queue();
        }
    }

    public void verifyEmbedSender() {
        String verifyRoleChannelId = new Config().getVerifyChannelId();
        TextChannel verifyChannel = Main.jda.getTextChannelById(verifyRoleChannelId);

        if (Objects.requireNonNull(verifyChannel).getHistory().isEmpty()) {

        }
    }

    public void rulesEmbedSender() {
        String rulesRoleChannelId = new Config().getRulesChannelId();
        TextChannel rulesChannel = Main.jda.getTextChannelById(rulesRoleChannelId);

        if (Objects.requireNonNull(rulesChannel).getHistory().isEmpty()) {

        }
    }
}
