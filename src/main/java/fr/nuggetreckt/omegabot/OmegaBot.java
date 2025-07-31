package fr.nuggetreckt.omegabot;

import fr.nuggetreckt.omegabot.button.ButtonListener;
import fr.nuggetreckt.omegabot.command.CommandListener;
import fr.nuggetreckt.omegabot.command.CommandManager;
import fr.nuggetreckt.omegabot.listener.*;
import fr.nuggetreckt.omegabot.statistics.StatsHandler;
import fr.nuggetreckt.omegabot.statistics.leaderboard.LeaderboardHandler;
import fr.nuggetreckt.omegabot.task.TasksHandler;
import fr.nuggetreckt.omegabot.util.SaveUtil;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;

import java.util.List;

public class OmegaBot {

    private JDA jda;
    private final Dotenv dotenv;

    private final String token;
    private final OmegaBot instance;
    private final Config config;

    private final Logger logger;

    private final StatsHandler statsHandler;
    private final TasksHandler tasksHandler;
    private final LeaderboardHandler leaderboardHandler;

    private List<Member> members;

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
        leaderboardHandler = new LeaderboardHandler(this);

        getLogger().info("Token OK. Launching JDA...");

        Signal.handle(new Signal("INT"), signal -> SaveUtil.saveAndExit(this));

        build();
    }

    public void build() {
        jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        registerEvents();
    }

    public void registerEvents() {
        //Simple Events
        jda.addEventListener(new ReadyListener(instance));
        jda.addEventListener(new MemberJoinListener(instance));
        jda.addEventListener(new MemberLeaveListener(instance));
        jda.addEventListener(new MemberMessageListener(instance));
        jda.addEventListener(new ShutdownListener(instance));

        //Register Commands
        jda.addEventListener(new CommandManager());

        //Interactions Events
        jda.addEventListener(new CommandListener(this));
        jda.addEventListener(new ButtonListener(this));
        jda.addEventListener(new StringSelectListener(this));
    }

    @Nullable
    public Member getMemberById(String id) {
        for (Member m : members) {
            if (m.getId().equals(id))
                return m;
        }
        return null;
    }

    public void loadMembers() {
        members = config.getGuild().loadMembers().get();
    }

    public List<Member> getMembers() {
        return members;
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

    public LeaderboardHandler getLeaderboardHandler() {
        return leaderboardHandler;
    }

    public String getVersion() {
        return getClass().getPackage().getImplementationVersion();
    }
}
