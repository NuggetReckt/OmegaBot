package fr.nuggetreckt.omegabot.util;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtil {

    public static void writeJSON(@NotNull JSONObject obj, @NotNull File file) throws RuntimeException {
        try {
            FileWriter fw = new FileWriter(file.getName());
            fw.write(obj.toJSONString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
