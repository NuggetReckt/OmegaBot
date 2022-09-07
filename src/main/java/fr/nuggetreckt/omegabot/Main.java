package fr.nuggetreckt.omegabot;

import fr.nuggetreckt.omegabot.listeners.ReadyListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    public static JDA jda;
    public static Dotenv dotenv;
    public static String token;

    public static void main(String[] args) throws LoginException, RuntimeException {

        System.out.println("Vérification du Token...");

        dotenv = Dotenv.configure()
                .directory("/env/")
                .filename(".env")
                .load();

        try {
            token = dotenv.get("TOKEN");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Token bon. Lancement du bot...");

        jda = JDABuilder.createDefault(token)
                .addEventListeners(new ReadyListener())
                .build();
    }
}
