package fr.nuggetreckt.omegabot.util;

import fr.nuggetreckt.omegabot.OmegaBot;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaveUtil {

    public static void saveAndExit(OmegaBot instance) {
        Thread thread = new Thread(() -> {
            instance.getLogger().info("Saving stats data...");
            List<Member> members = instance.getConfigHandler().getConfig().getGuild().loadMembers().get();

            saveFile(members, instance);
            instance.getLogger().info("Stats saved.");
            instance.getJDA().shutdown();
            Thread.currentThread().interrupt();
        });
        thread.start();
    }

    public static void saveFile(List<Member> members, @NotNull OmegaBot instance) {
        File jsonFile = instance.getStatsHandler().getFile();
        JSONObject obj = JsonUtil.createJson(members, instance.getStatsHandler());

        if (jsonFile.exists()) {
            if (!jsonFile.delete())
                instance.getLogger().warn("Failed to delete existing stats file");
        }
        try {
            if (!jsonFile.createNewFile())
                throw new IOException("Failed to create new file");
            JsonUtil.writeJSON(obj, jsonFile);
        } catch (RuntimeException e) {
            instance.getLogger().error("Failed to write JSON object", e);
        } catch (IOException e) {
            instance.getLogger().error("Failed to create JSON file", e);
        }
    }
}
