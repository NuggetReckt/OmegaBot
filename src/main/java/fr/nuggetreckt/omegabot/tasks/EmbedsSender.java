package fr.nuggetreckt.omegabot.tasks;

import fr.nuggetreckt.omegabot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

import static fr.nuggetreckt.omegabot.Main.jda;

public class EmbedsSender {

    final TextChannel takeRoleChannel = jda.getTextChannelById(new Config().getTakeRoleChannelId());
    final TextChannel verifyChannel = jda.getTextChannelById(new Config().getVerifyChannelId());
    final TextChannel rulesChannel = jda.getTextChannelById(new Config().getRulesChannelId());

    public void SendEmbeds() {
        try {
            takeRoleEmbedSender();
            verifyEmbedSender();
            rulesEmbedSender();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takeRoleEmbedSender() {

        System.out.println("debug");

        /*MessageHistory history = MessageHistory.getHistoryFromBeginning(takeRoleChannel).complete();
        List<Message> messages = history.getRetrievedHistory();*/

        int messages = Objects.requireNonNull(takeRoleChannel).getHistory().size();

        if (messages == 1) {
            return;
        }

        if (messages == 0) {
            EmbedBuilder takeRoleEmbed = new EmbedBuilder();

            takeRoleEmbed.setTitle("\uD83D\uDCCC ・ Rôles")
                    .setDescription("Séléctionne les rôles à l'aide des boutons ci-dessous pour avoir des pings personnalisés et avoir accès à des salons sépcifiques !")
                    .addField("Mentions", """
                            \uD83D\uDCCA ・ Sondages
                            \uD83D\uDCE2 ・ Annonces
                            \uD83C\uDF89 ・ Events
                            \uD83D\uDCDA ・ Informations intéressantes
                            """, true)
                    .addField("Accès salons spécifiques", """
                            \uD83C\uDF33 ・ Minecraft
                            \uD83D\uDD28 ・ Hardware/Tech
                            """, true)
                    .setColor(new Color(255, 255, 255, 1))
                    .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png")
                    .setTimestamp(new Date().toInstant());

            takeRoleChannel.sendMessageEmbeds(takeRoleEmbed.build())
                    .setActionRow(
                            Button.primary("ROLE_POLLS", Emoji.fromFormatted("\uD83D\uDCCA")),
                            Button.primary("ROLE_ANNOUNCEMENTS", Emoji.fromFormatted("\uD83D\uDCE2")),
                            Button.primary("ROLE_EVENTS", Emoji.fromFormatted("\uD83C\uDF89")),
                            Button.primary("ROLE_INTERESTING-INFORMATIONS", Emoji.fromFormatted("\uD83D\uDCDA")),
                            Button.primary("ROLE_MINECRAFT", Emoji.fromFormatted("\uD83C\uDF33")),
                            Button.primary("ROLE_HARDWARE-TECH", Emoji.fromFormatted("\uD83D\uDD28"))
                    )
                    .queue();
        }
    }

    public void verifyEmbedSender() {

        System.out.println("debug");

        int messages = Objects.requireNonNull(verifyChannel).getHistory().size();

        if (messages == 1) {
            return;
        }

        if (messages == 0) {
            EmbedBuilder verifyEmbed = new EmbedBuilder();

            verifyEmbed.setTitle(" ・ Vérification")
                    .setDescription("Bienvenue sur le discord ! Vérifie-toi en cliquant sur le bouton ci-dessous !")
                    .setImage("https://media.discordapp.net/attachments/712679066872053810/1008100888398798978/unknown.png?width=554&height=554")
                    .setColor(new Color(255, 255, 255, 1))
                    .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png")
                    .setTimestamp(new Date().toInstant());

            verifyChannel.sendMessageEmbeds(verifyEmbed.build())
                    .setActionRow(
                            Button.primary("VERIFY", "Acceder au Discord")
                    )
                    .queue();
        }
    }

    public void rulesEmbedSender() {

        System.out.println("debug");

        int messages = Objects.requireNonNull(rulesChannel).getHistory().size();

        if (messages == 1) {
            return;
        }

        if (messages == 0) {
            EmbedBuilder rulesEmbed = new EmbedBuilder();

            rulesEmbed.setTitle("✅ ・ Règlement du discord")
                    .setDescription("Le règlement ci-dessous est certes léger, mais ne signifie pas fais de la merde, ça veut dire fais preuve de bon sens.")
                    .addField("Règles", """
                            🔹Pas de contenu nsfw/raciste/homophobe/...
                            🔹Respecte tout le monde (y compris toi)
                            🔹Pas d'insultes premier degré
                            🔹Pas de publicité
                            """, false)
                    .setColor(new Color(255, 255, 255, 1))
                    .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png")
                    .setTimestamp(new Date().toInstant());

            rulesChannel.sendMessageEmbeds(rulesEmbed.build())
                    .queue();
        }
    }
}
