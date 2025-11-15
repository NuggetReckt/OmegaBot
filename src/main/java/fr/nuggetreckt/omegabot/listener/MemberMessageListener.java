package fr.nuggetreckt.omegabot.listener;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.exception.MemberNotFoundException;
import fr.nuggetreckt.omegabot.statistics.MemberStats;
import fr.nuggetreckt.omegabot.statistics.StatsHandler;
import fr.nuggetreckt.omegabot.util.MessageUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MemberMessageListener extends ListenerAdapter {

    private final OmegaBot instance;
    private final List<String> messagesToSkip;

    private final Emoji INVALID_EMOJI = Emoji.fromUnicode("❌");

    public MemberMessageListener(OmegaBot instance) {
        this.instance = instance;
        this.messagesToSkip = new ArrayList<>();
    }

    @Override
    public void onMessageUpdate(@NotNull MessageUpdateEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getChannel().getId().equals(instance.getConfigHandler().getConfig().getCountChannel().getId())) return;

        Member author = event.getMember();
        Message message = event.getMessage();

        assert author != null;
        if (!handleMessage(author, message)) {
            handleInvalidMessage(message);
            return;
        }
        MessageHistory history = event.getChannel().getHistoryAfter(message, 100).complete();
        List<Message> messages = history.getRetrievedHistory();

        handleValidMessage(message);
        for (Message msg : messages) {
            Member mb = msg.getMember();

            if (mb == null || msg.getAuthor().isBot()) continue;
            if (handleMessage(mb, msg)) {
                handleValidMessage(msg);
            } else {
                handleInvalidMessage(msg);
            }
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getChannel().getId().equals(instance.getConfigHandler().getConfig().getCountChannel().getId())) return;

        Member author = event.getMember();
        Message message = event.getMessage();

        assert author != null;
        if (!handleMessage(author, message)) {
            handleInvalidMessage(message);
        }
    }

    private boolean handleMessage(@NotNull Member author, @NotNull Message message) {
        StatsHandler statsHandler = instance.getStatsHandler();
        Message validBefore = MessageUtil.getValidMessageBefore(message);
        Message countBefore = MessageUtil.getMessageBefore(message);
        MemberStats memberStats;
        long validBeforeValue;
        long count;

        try {
            statsHandler.getMemberStats(author.getId());
        } catch (MemberNotFoundException e) {
            statsHandler.initMemberStats(author.getId());
        }
        memberStats = statsHandler.getMemberStats(author.getId());
        if (memberStats == null || validBefore == null || countBefore.getAuthor().getId().equals(message.getAuthor().getId()) || !MessageUtil.isMessageValid(message.getContentRaw())) {
            return false;
        }
        String content = MessageUtil.splitMessage(message.getContentRaw());
        count = MessageUtil.parseMessage(message.getContentRaw());
        validBeforeValue = MessageUtil.parseMessage(validBefore.getContentRaw());

        if (count != validBeforeValue + 1) {
            return false;
        }
        if (messagesToSkip.contains(message.getId())) {
            return true;
        }
        messagesToSkip.add(message.getId());
        if (content.endsWith("69")) {
            message.addReaction(Emoji.fromFormatted("\uD83D\uDE0F")).queue();
            memberStats.magicNumberCount++;
        } else if (content.endsWith("000")) {
            memberStats.thousandsCount++;
        } else if (content.endsWith("00")) {
            memberStats.hundredsCount++;
        }
        memberStats.counted++;
        statsHandler.setCurrentNum(statsHandler.getCurrentNum() + 1);
        return true;
    }

    private void handleInvalidMessage(@NotNull Message message) {
        message.addReaction(INVALID_EMOJI).queue();
    }

    private void handleValidMessage(@NotNull Message message) {
        if (!messagesToSkip.contains(message.getId())) {
            messagesToSkip.add(message.getId());
        }
        message.removeReaction(INVALID_EMOJI).queue();
    }
}
