package fr.nuggetreckt.omegabot;

import fr.nuggetreckt.omegabot.buttons.ButtonListener;
import fr.nuggetreckt.omegabot.commands.CommandListener;
import fr.nuggetreckt.omegabot.commands.CommandManager;
import fr.nuggetreckt.omegabot.listeners.MemberJoinListener;
import fr.nuggetreckt.omegabot.listeners.MemberMessageListener;
import fr.nuggetreckt.omegabot.listeners.ReadyListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OmegaBot {

    private JDA jda;
    private final Dotenv dotenv;

    private final String token;
    private final OmegaBot instance;
    private final Config config;

    private final Logger logger = LoggerFactory.getLogger(OmegaBot.class);

    public OmegaBot() throws RuntimeException {
        instance = this;
        config = new Config(this);

        getLogger().info("Vérification du Token...");

        dotenv = Dotenv.configure()
                .directory("/env/")
                .filename(".env")
                .load();

        try {
            token = dotenv.get("TOKEN");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }

        getLogger().info("Token bon. Lancement du bot...");

        build();
    }

    public void build() {
        jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        registerEvents();
    }

    public void registerEvents() {
        //Simple Events
        jda.addEventListener(new ReadyListener(instance));
        jda.addEventListener(new MemberJoinListener(instance));
        jda.addEventListener(new MemberMessageListener(instance));

        //Register Commands
        jda.addEventListener(new CommandManager());

        //Commands/Buttons Events
        jda.addEventListener(new CommandListener(this));
        jda.addEventListener(new ButtonListener(this));
    }

    public OmegaBot getInstance() {
        return instance;
    }

    public JDA getJDA() {
        return jda;
    }

    public Logger getLogger() {
        return logger;
    }

    public Config getConfig() {
        return config;
    }

    public String getVersion() {
        return getClass().getPackage().getImplementationVersion();
    }
}
