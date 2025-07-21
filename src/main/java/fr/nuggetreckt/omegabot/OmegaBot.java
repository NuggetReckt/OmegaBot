package fr.nuggetreckt.omegabot;

import fr.nuggetreckt.omegabot.button.ButtonListener;
import fr.nuggetreckt.omegabot.command.CommandListener;
import fr.nuggetreckt.omegabot.command.CommandManager;
import fr.nuggetreckt.omegabot.listener.MemberJoinListener;
import fr.nuggetreckt.omegabot.listener.MemberMessageListener;
import fr.nuggetreckt.omegabot.listener.ReadyListener;
import fr.nuggetreckt.omegabot.listener.ShutdownListener;
import fr.nuggetreckt.omegabot.statistics.StatsHandler;
import fr.nuggetreckt.omegabot.task.TasksHandler;
import fr.nuggetreckt.omegabot.util.SaveUtil;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;

public class OmegaBot {

    private JDA jda;
    private final Dotenv dotenv;

    private final String token;
    private final OmegaBot instance;
    private final Config config;

    private final Logger logger;

    private final StatsHandler statsHandler;
    private final TasksHandler tasksHandler;

    public OmegaBot() throws RuntimeException {
        instance = this;
        logger = LoggerFactory.getLogger(OmegaBot.class);
        config = new Config(this);

        getLogger().info("Reading token...");

        //Loading token
        dotenv = Dotenv.configure()
                .directory("/env/")
                .filename(".env")
                .load();

        try {
            token = dotenv.get("TOKEN");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        if (token == null || token.isEmpty())
            throw new RuntimeException("Token is null or empty");

        //Loading modules
        statsHandler = new StatsHandler(this);
        tasksHandler = new TasksHandler(this);

        getLogger().info("Token OK. Launching JDA...");

        Signal.handle(new Signal("INT"), signal -> SaveUtil.saveAndExit(this));

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
        jda.addEventListener(new ShutdownListener(instance));

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

    public StatsHandler getStatsHandler() {
        return statsHandler;
    }

    public TasksHandler getTasksHandler() {
        return tasksHandler;
    }

    public String getVersion() {
        return getClass().getPackage().getImplementationVersion();
    }
}
