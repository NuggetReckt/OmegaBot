package fr.nuggetreckt.omegabot.statistics;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.exception.MemberNotFoundException;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class StatsHandler {

    private final OmegaBot instance;
    private final HashMap<String, MemberStats> stats;

    private File jsonFile;

    public StatsHandler(OmegaBot instance) {
        this.instance = instance;
        this.stats = new HashMap<>();
    }

    public void init() {
        List<Member> members = instance.getConfig().getGuild().loadMembers().get();
        jsonFile = new File("stats.json");

        for (Member member : members) {
            if (member.getUser().isBot()) continue;

            initMemberStats(member.getId());
        }
        if (!jsonFile.exists()) {
            instance.getLogger().info("No stats file found, creating JSON file...");
            initJSON(members);
        }
        instance.getLogger().info("Loading stats data...");

        //TODO: Read JSON file and set member stats
    }

    public MemberStats getMemberStats(String id) {
        if (!stats.containsKey(id) || stats.get(id) == null) throw new MemberNotFoundException();
        return stats.get(id);
    }

    private void initMemberStats(String id) {
        stats.put(id, new MemberStats());
    }

    private void initJSON(List<Member> members) {
        MessageChannel channel = instance.getConfig().getCountChannel();
        MessageHistory history = MessageHistory.getHistoryFromBeginning(channel).complete();
        JSONArray memberArray = new JSONArray();
        JSONObject obj = new JSONObject();

        try {
            jsonFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        obj.put("lastUpdated", LocalTime.now().toString());
        //TODO: Put general stats data here

        for (Member member : members) {
            if (member.getUser().isBot()) continue;

            JSONObject memberObj = new JSONObject();

            memberObj.put("id", member.getId());
            memberObj.put("name", member.getEffectiveName());
            memberObj.put("joinDate", member.getTimeJoined().toString());
            //TODO: Put member stats data here: maybe create a new JSONObject?

            memberArray.add(memberObj);
        }
        obj.put("members", memberArray);

        //Write JSON to file
        try {
            FileWriter fw = new FileWriter(jsonFile.getName());
            fw.write(obj.toJSONString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
