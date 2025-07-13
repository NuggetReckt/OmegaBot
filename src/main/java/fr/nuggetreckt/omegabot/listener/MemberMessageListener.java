package fr.nuggetreckt.omegabot.listener;

import fr.nuggetreckt.omegabot.OmegaBot;
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

        Message message = event.getMessage();
        String content = message.getContentRaw().split(" ")[0];
        long count;

        if (!ParseUtil.isMessageValid(content)) {
            message.delete().queue();
            return;
        }
        if (content.endsWith("69"))
            message.addReaction(Emoji.fromFormatted("\uD83D\uDE0F")).queue();
        count = Long.parseLong(content);
    }
}
