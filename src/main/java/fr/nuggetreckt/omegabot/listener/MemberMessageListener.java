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
            message.delete().queue();
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
        count = Long.parseLong(content);

        //TODO: verify count and stored current value
        instance.getStatsHandler().setCurrentNum(instance.getStatsHandler().getCurrentNum() + 1);
    }
}
