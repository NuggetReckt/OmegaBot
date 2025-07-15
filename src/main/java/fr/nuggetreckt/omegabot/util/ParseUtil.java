package fr.nuggetreckt.omegabot.util;

import org.jetbrains.annotations.NotNull;

public class ParseUtil {

    public static boolean isMessageValid(String message) {
        if (message == null || message.isEmpty()) return false;

        try {
            Long.parseLong(message);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static Long parseMessage(@NotNull String message) {
        String content = message.split(" ")[0];

        if (!isMessageValid(content)) return -1L;
        return Long.parseLong(content);
    }
}
