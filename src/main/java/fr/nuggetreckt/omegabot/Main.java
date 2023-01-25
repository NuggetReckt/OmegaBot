package fr.nuggetreckt.omegabot;

import fr.nuggetreckt.omegabot.buttons.ButtonListener;
import fr.nuggetreckt.omegabot.commands.CommandListener;
import fr.nuggetreckt.omegabot.commands.CommandManager;
import fr.nuggetreckt.omegabot.listeners.MemberJoinListener;
import fr.nuggetreckt.omegabot.listeners.ReadyListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public JDA jda;
    public Dotenv dotenv;

    private String token;

    private static Main instance;

    private final Logger logger = LoggerFactory.getLogger(Main.class);

    public void main(String[] args) throws RuntimeException {
        instance = this;

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

        this.Build();
    }

    public void Build() {
        jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();

        this.RegisterEvents();
    }

    public void RegisterEvents() {
        //Simple Events
        jda.addEventListener(new ReadyListener());
        jda.addEventListener(new MemberJoinListener());

        //Register Commands
        jda.addEventListener(new CommandManager());

        //Commands/Buttons Events
        jda.addEventListener(new CommandListener(jda));
        jda.addEventListener(new ButtonListener(jda));
    }

    public static Main getInstance() {
        return instance;
    }

    public JDA getJDA() {
        return jda;
    }

    public Logger getLogger () {
        return logger;
    }

    public String getVersion() {
        return getInstance().getClass().getPackage().getImplementationVersion();
    }
}