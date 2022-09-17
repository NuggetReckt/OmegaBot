package fr.nuggetreckt.omegabot.tasks;

import net.dv8tion.jda.api.entities.Activity;

import java.util.*;

import static fr.nuggetreckt.omegabot.Main.jda;

public class BotStatus {
    final int ChangeStatusInterval = 24000;
    final int second = 1000;

    public void setStatus() {
        List<String> status = new ArrayList<>();

        status.add("Dev avec ❤️ par NuggetReckt");
        status.add("OmegaBot !");
        status.add("(Rejoignez play.noskillworld.fr)");
        status.add("Herbyvor >>>> ALL");
        status.add(":bread:");
        status.add("Pog");
        status.add("Stanley Kubrick était chargé par le gouvernement américain de faire des fausses vidéos de pas sur la lune. Mais il était si perfectionniste qu'il l'a finalement fait sur place.");

        Random r = new Random();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int a = r.nextInt(status.size() - 1);
                jda.getPresence().setActivity(Activity.playing(String.valueOf(status.get(a))));
            }
        }, second, ChangeStatusInterval);
    }
}