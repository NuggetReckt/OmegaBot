package fr.nuggetreckt.omegabot.util;

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
}
