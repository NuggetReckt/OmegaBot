package fr.nuggetreckt.omegabot.tasks;

import fr.nuggetreckt.omegabot.Main;
import net.dv8tion.jda.api.entities.Activity;

import java.util.*;

public class BotStatus {
    final int ChangeStatusInterval = 24000;
    final int second = 1000;

    private final Main main = Main.getInstance();

    public void setStatus() {
        List<String> status = new ArrayList<>();

        status.add("Dev avec ❤ par NuggetReckt");
        status.add("OmegaBot !");
        status.add("(Rejoignez play.noskillworld.fr)");
        status.add("Herbyvor >>>> ALL");
        status.add("\uD83C\uDF5E");
        status.add("PogBot");
        status.add("Stanley Kubrick était chargé par le gouvernement américain de faire des fausses vidéos de pas sur la lune. Mais il était si perfectionniste qu'il l'a finalement fait sur place.");

        Random r = new Random();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int a = r.nextInt(status.size() - 1);
                main.getJDA().getPresence().setActivity(Activity.playing(String.valueOf(status.get(a))));
            }
        }, second, ChangeStatusInterval);
    }
}