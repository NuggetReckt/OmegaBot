package fr.nuggetreckt.omegabot.listener;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.statistics.MemberStats;
import fr.nuggetreckt.omegabot.statistics.StatsHandler;
import fr.nuggetreckt.omegabot.util.ParseUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MemberMessageListener extends ListenerAdapter {

    private final OmegaBot instance;

    public MemberMessageListener(OmegaBot instance) {
        this.instance = instance;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getChannel().getId().equals(instance.getConfig().getCountChannel().getId())) return;

        StatsHandler statsHandler = instance.getStatsHandler();
        Message message = event.getMessage();
        String content = ParseUtil.splitMessage(message.getContentRaw());
        MemberStats memberStats = statsHandler.getMemberStats(event.getAuthor().getId());
        long count;

        if (!ParseUtil.isMessageValid(content)) {
            handleInvalidMessage(message);
            return;
        }
        count = Long.parseLong(content);

        if (count != statsHandler.getCurrentNum() + 1) {
            handleInvalidMessage(message);
            return;
        }
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
    }

    private void handleInvalidMessage(@NotNull Message message) {
        message.addReaction(Emoji.fromUnicode("❌")).queue();
    }
}
