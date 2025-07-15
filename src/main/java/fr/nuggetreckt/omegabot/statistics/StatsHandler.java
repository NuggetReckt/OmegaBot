package fr.nuggetreckt.omegabot.statistics;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.exception.MemberNotFoundException;
import fr.nuggetreckt.omegabot.util.JsonUtil;
import fr.nuggetreckt.omegabot.util.ParseUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.jetbrains.annotations.NotNull;
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
            instance.getLogger().info("JSON file created. Setting up data...");
            setJsonData();
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

    private void initJSON(@NotNull List<Member> members) {
        JSONArray memberArray = new JSONArray();
        JSONObject obj = new JSONObject();

        obj.put("lastUpdated", LocalTime.now().toString());
        obj.put("currentNum", 0);
        obj.put("activeMembers", 0);

        for (Member member : members) {
            if (member.getUser().isBot()) continue;

            JSONObject memberObj = new JSONObject();
            JSONObject statsObj = new JSONObject();

            memberObj.put("id", member.getId());
            memberObj.put("name", member.getEffectiveName());
            memberObj.put("joinDate", member.getTimeJoined().toString());

            statsObj.put("counted", 0);
            statsObj.put("magicNumberCount", 0);
            statsObj.put("hundredsCount", 0);
            statsObj.put("thousandsCount", 0);

            memberObj.put("stats", statsObj);
            memberArray.add(memberObj);
        }
        obj.put("members", memberArray);

        //Write JSON object to file
        try {
            jsonFile.createNewFile();
            JsonUtil.writeJSON(obj, jsonFile);
        } catch (RuntimeException e) {
            instance.getLogger().error("Failed to write JSON object", e);
        } catch (IOException e) {
            instance.getLogger().error("Failed to create JSON file", e);
        }
    }

    private void setJsonData() {
        MessageChannel channel = instance.getConfig().getCountChannel();
        MessageHistory history = MessageHistory.getHistoryFromBeginning(channel).complete();
        List<Message> messages = history.getRetrievedHistory().reversed();
        HashMap<String, MemberStats> tmp = new HashMap<>();
        Message lasMessage = messages.get(history.getRetrievedHistory().size() - 1);
        long expectedCount = ParseUtil.parseMessage(lasMessage.getContentRaw());
        long counted = 0;

        for (Message message : messages) {
            if (message.getAuthor().isBot()) continue;
            if (!ParseUtil.isMessageValid(message.getContentRaw())) continue;

            String userId = message.getAuthor().getId();

            System.out.println("DEBUG: msg=" + message.getContentRaw());
            System.out.println("DEBUG: member=" + userId);

            if (!tmp.containsKey(userId) || tmp.get(userId) == null) {
                tmp.get(userId);
            } else {
                tmp.put(userId, new MemberStats());
            }
            counted++;
        }
        System.out.println("DEBUG: counted=" + counted);
        System.out.println("DEBUG: lastMessage=" + expectedCount);
    }
}
