package fr.nuggetreckt.omegabot;

import fr.nuggetreckt.omegabot.button.ButtonListener;
import fr.nuggetreckt.omegabot.command.CommandListener;
import fr.nuggetreckt.omegabot.command.CommandManager;
import fr.nuggetreckt.omegabot.config.ConfigHandler;
import fr.nuggetreckt.omegabot.listener.*;
import fr.nuggetreckt.omegabot.statistics.StatsHandler;
import fr.nuggetreckt.omegabot.statistics.leaderboard.LeaderboardHandler;
import fr.nuggetreckt.omegabot.task.TasksHandler;
import fr.nuggetreckt.omegabot.util.SaveUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.concurrent.Task;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class OmegaBot {

    private JDA jda;

    private final OmegaBot instance;
    private final ConfigHandler configHandler;

    private final Logger logger;

    private final StatsHandler statsHandler;
    private final TasksHandler tasksHandler;
    private final LeaderboardHandler leaderboardHandler;

    private final Queue<Member> members;
    private boolean isShuttingDown;

    public OmegaBot() throws RuntimeException {
        instance = this;
        logger = LoggerFactory.getLogger(OmegaBot.class);
        isShuttingDown = false;
        members = new ConcurrentLinkedDeque<>();

        getLogger().info("Setting up configuration file...");

        //Loading modules
        configHandler = new ConfigHandler(this);
        statsHandler = new StatsHandler(this);
        tasksHandler = new TasksHandler(this);
        leaderboardHandler = new LeaderboardHandler(this);

        getLogger().info("Config OK. Launching JDA...");

        Signal.handle(new Signal("INT"), signal -> {
            if (jda == null) {
                logger.info("Detected SIGINT before JDA is ready, Forcing shutting down.");
                System.exit(1);
            }
            if (isShuttingDown) {
                logger.info("Detected SIGINT twice, Forcing shutting down.");
                System.exit(1);
            }
            isShuttingDown = true;
            SaveUtil.saveAndExit(this);
        });

        build();
    }

    public void build() {
        jda = JDABuilder.createDefault(configHandler.getConfig().getToken())
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

    public Queue<Member> loadMembers() {
        Task<List<Member>> memberGetTask = configHandler.getConfig().getGuild().loadMembers();

        new Thread(() -> members.addAll(memberGetTask.get())).start();
        return members;
    }

    public Queue<Member> getMembers() {
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

    public ConfigHandler getConfigHandler() {
        return configHandler;
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
