package fr.nuggetreckt.omegabot.task;

import fr.nuggetreckt.omegabot.OmegaBot;
import net.dv8tion.jda.api.entities.Activity;

import java.util.*;

public class BotStatus {

    private final static long CHANGE_STATUS_INTERVAL = 24000;

    public static void setStatus(OmegaBot instance) {
        List<String> status = new ArrayList<>();
        Random r = new Random();
        Timer timer = new Timer();

        status.add("Dev avec ❤ par NuggetReckt");
        status.add("OmegaBot !");
        status.add("Herbyvor >>>> ALL");
        status.add("\uD83C\uDF5E");
        status.add("PogBot");
        status.add("Stanley Kubrick était chargé par le gouvernement américain de faire des fausses vidéos de pas sur la lune. Mais il était si perfectionniste qu'il l'a finalement fait sur place.");

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int a = r.nextInt(status.size() - 1);
                instance.getJDA().getPresence().setActivity(Activity.playing(String.valueOf(status.get(a))));
            }
        }, 0, CHANGE_STATUS_INTERVAL);
    }
}