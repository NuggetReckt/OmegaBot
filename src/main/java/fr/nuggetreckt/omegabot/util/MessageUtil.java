package fr.nuggetreckt.omegabot.util;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import org.jetbrains.annotations.NotNull;

public class MessageUtil {

    public static boolean isMessageValid(String message) {
        if (message == null || message.isEmpty()) return false;

        String content = splitMessage(message);
        try {
            Long.parseLong(content);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String splitMessage(@NotNull String message) {
        return message.split(" ")[0];
    }

    public static long parseMessage(@NotNull String message) {
        String content = splitMessage(message);

        if (!isMessageValid(message)) return -1L;
        return Long.parseLong(content);
    }

    public static Message getValidMessageBefore(@NotNull Message message) {
        MessageHistory history = message.getChannel().getHistoryBefore(message, 10).complete();
        Message before = null;

        for (Message msg : history.getRetrievedHistory()) {
            if (msg.getAuthor().isBot()) continue;
            if (msg.getAuthor().getId().equals(message.getAuthor().getId())) continue;

            if (isMessageValid(msg.getContentRaw())) {
                before = msg;
                break;
            }
        }
        return before;
    }

    public static Message getMessageBefore(@NotNull Message message) {
        MessageHistory history = message.getChannel().getHistoryBefore(message, 5).complete();
        Message before = null;

        for (Message msg : history.getRetrievedHistory()) {
            if (!msg.getAuthor().isBot()) {
                before = msg;
                break;
            }
        }
        return before;
    }
}
