package fr.nuggetreckt.omegabot;

import fr.nuggetreckt.omegabot.commands.SuggestionCommand;
import fr.nuggetreckt.omegabot.listeners.MemberJoinListener;
import fr.nuggetreckt.omegabot.listeners.ReadyListener;
import fr.nuggetreckt.omegabot.listeners.RoleButtonListener;
import fr.nuggetreckt.omegabot.listeners.VerifyButtonListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

    public static JDA jda;
    public static Dotenv dotenv;
    public static String token;

    public static void main(String[] args) throws RuntimeException {

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
                //Basic Listeners
                .addEventListeners(new ReadyListener())
                .addEventListeners(new MemberJoinListener())

                //Button Listeners
                .addEventListeners(new VerifyButtonListener())
                .addEventListeners(new RoleButtonListener())

                //Command Listeners
                .addEventListeners(new SuggestionCommand())

                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();

        jda.upsertCommand("suggestion", "Permet de faire une suggestion pour contribuer à l'amélioration du serveur.")
                .addOption(OptionType.STRING, "description", "Description de votre suggestion")
                .queue();
    }
}
