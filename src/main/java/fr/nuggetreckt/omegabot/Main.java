package fr.nuggetreckt.omegabot;

import fr.nuggetreckt.omegabot.commands.PollCommand;
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
                .addEventListeners(new PollCommand())

                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();

        jda.upsertCommand("suggestion", "Permet de faire une suggestion pour contribuer à l'amélioration du serveur.")
                .addOption(OptionType.STRING, "description", "Description de votre suggestion")
                .queue();
        jda.upsertCommand("sondage", "Permet de créer des sondages pour les concours de memes. (Admin uniquement)")
                .addOption(OptionType.STRING, "description", "Description du sondage.", true)
                .addOption(OptionType.MENTIONABLE, "user1", "Ajoute l'utilisateur au sondage du concours.", true)
                .addOption(OptionType.MENTIONABLE, "user2", "Ajoute l'utilisateur au sondage du concours.", true)
                .addOption(OptionType.MENTIONABLE, "user3", "Ajoute l'utilisateur au sondage du concours.", false)
                .addOption(OptionType.MENTIONABLE, "user4", "Ajoute l'utilisateur au sondage du concours.", false)
                .addOption(OptionType.MENTIONABLE, "user5", "Ajoute l'utilisateur au sondage du concours.", false)
                .addOption(OptionType.MENTIONABLE, "user6", "Ajoute l'utilisateur au sondage du concours.", false)
                .addOption(OptionType.MENTIONABLE, "user7", "Ajoute l'utilisateur au sondage du concours.", false)
                .addOption(OptionType.MENTIONABLE, "user8", "Ajoute l'utilisateur au sondage du concours.", false)
                .queue();
    }
}
