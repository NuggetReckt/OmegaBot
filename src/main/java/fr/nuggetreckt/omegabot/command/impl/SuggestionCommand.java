package fr.nuggetreckt.omegabot.command.impl;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

public class SuggestionCommand extends Command {

    private final OmegaBot instance;

    public SuggestionCommand(OmegaBot instance) {
        this.instance = instance;
    }

    @Override
    public void execute(@NotNull SlashCommandInteractionEvent event) {
        MessageChannel suggestionchannel = instance.getConfig().getSuggestionChannel();

        if (event.getOption("description") == null) {
            event.reply("> La description ne peut pas être vide !")
                    .setEphemeral(true)
                    .queue();
            return;
        }
        EmbedBuilder suggestion = new EmbedBuilder();

        suggestion.setTitle("💡 ・ Suggestion")
                .addField("\uD83D\uDC64・__Membre__ ", Objects.requireNonNull(event.getMember()).getAsMention(), true)
                .addField("\uD83D\uDDD2️・__Suggestion__ ", Objects.requireNonNull(event.getOption("description")).getAsString(), true)
                .setColor(new Color(255, 255, 255, 1))
                .setFooter("OmegaBot - NuggetReckt", "https://media.discordapp.net/attachments/712679066872053810/1017822877799686225/unknown.png")
                .setTimestamp(new Date().toInstant());

        Message message = suggestionchannel.sendMessageEmbeds(suggestion.build())
                .complete();

        message.createThreadChannel("Suggestion de " + event.getMember().getEffectiveName())
                .queue();

        message.addReaction(Emoji.fromFormatted("✅"))
                .queue();
        message.addReaction(Emoji.fromFormatted("❌"))
                .queue();

        event.reply("> Suggestion envoyée avec succès !")
                .setEphemeral(true)
                .queue();
    }
}
