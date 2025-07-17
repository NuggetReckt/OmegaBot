package fr.nuggetreckt.omegabot.task;

import fr.nuggetreckt.omegabot.OmegaBot;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class StatsSaver {

    private final static long SAVE_INTERVAL = 720 * 60 * 1000;

    public static void launch(OmegaBot instance) {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                save(instance);
            }
        }, SAVE_INTERVAL, SAVE_INTERVAL);
    }

    public static void save(@NotNull OmegaBot instance) {
        Thread thread = new Thread(() -> {
            instance.getLogger().info("[AUTOSAVE] Saving stats...");
            //TODO: Save stats data into JSON file
        });
        thread.start();
    }
}
