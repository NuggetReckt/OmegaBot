package fr.nuggetreckt.omegabot.statistics;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.exception.MemberNotFoundException;
import fr.nuggetreckt.omegabot.util.JsonUtil;
import fr.nuggetreckt.omegabot.util.ParseUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsHandler {

    private final OmegaBot instance;
    private HashMap<String, MemberStats> stats;

    private long currentNum;

    private File jsonFile;

    public StatsHandler(OmegaBot instance) {
        this.instance = instance;
        this.currentNum = 0;
    }

    public void init() {
        List<Member> members = instance.getConfig().getGuild().loadMembers().get();
        jsonFile = new File("stats.json");

        if (!jsonFile.exists()) {
            instance.getLogger().info("No stats file found, reading data...");
            initStats();
            instance.getLogger().info("Stats loaded. Saving into file...");
            initJSON(members);
            return;
        }
        instance.getLogger().info("Stats file found. Loading data...");
        stats = new HashMap<>();
        JSONObject obj = null;

        try {
            obj = (JSONObject) new JSONParser().parse(new FileReader(jsonFile));
        } catch (IOException e) {
            instance.getLogger().error("Failed to load file: " + jsonFile.getAbsolutePath());
        } catch (ParseException e) {
            instance.getLogger().error("Failed to parse file: " + jsonFile.getAbsolutePath());
        }
        if (obj == null) return;

        JSONArray memberList = (JSONArray) obj.get("members");
        currentNum = Long.parseLong(obj.get("currentNum").toString());

        memberList.forEach(m -> {
            JSONObject member = (JSONObject) m;

            String memberId = (String) member.get("id");
            JSONObject statsObj = (JSONObject) member.get("stats");
            long counted = Long.parseLong(statsObj.get("counted").toString());
            long magicNumberCount = Long.parseLong(statsObj.get("magicNumberCount").toString());
            long hundredsCount = Long.parseLong(statsObj.get("hundredsCount").toString());
            long thousandsCount = Long.parseLong(statsObj.get("thousandsCount").toString());

            if (!stats.containsKey(memberId) || stats.get(memberId) == null)
                initMemberStats(memberId);
            MemberStats memberStats = stats.get(memberId);
            memberStats.counted = counted;
            memberStats.magicNumberCount = magicNumberCount;
            memberStats.hundredsCount = hundredsCount;
            memberStats.thousandsCount = thousandsCount;
        });
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
        obj.put("currentNum", currentNum);
        obj.put("activeMembers", getActiveMembers().size());

        for (Member member : members) {
            if (member.getUser().isBot()) continue;

            JSONObject memberObj = new JSONObject();
            JSONObject statsObj = new JSONObject();

            memberObj.put("id", member.getId());
            memberObj.put("name", member.getEffectiveName());

            long counted = 0;
            long magicNumberCount = 0;
            long hundredsCount = 0;
            long thousandsCount = 0;

            if (stats.containsKey(member.getId()) || stats.get(member.getId()) != null) {
                MemberStats memberStats = getMemberStats(member.getId());
                counted = memberStats.counted;
                magicNumberCount = memberStats.magicNumberCount;
                hundredsCount = memberStats.hundredsCount;
                thousandsCount = memberStats.thousandsCount;
            }
            statsObj.put("counted", counted);
            statsObj.put("magicNumberCount", magicNumberCount);
            statsObj.put("hundredsCount", hundredsCount);
            statsObj.put("thousandsCount", thousandsCount);

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

    private void initStats() {
        stats = new HashMap<>();
        MessageChannel channel = instance.getConfig().getCountChannel();
        MessageHistory history = MessageHistory.getHistoryFromBeginning(channel).complete();
        List<Message> messages = history.getRetrievedHistory().reversed();
        Message lasMessage = messages.get(history.getRetrievedHistory().size() - 1);
        long expectedCount = ParseUtil.parseMessage(lasMessage.getContentRaw());
        long counted = 0;

        for (Message message : messages) {
            if (message.getAuthor().isBot()) continue;
            if (!ParseUtil.isMessageValid(message.getContentRaw())) continue;

            String userId = message.getAuthor().getId();
            if (!stats.containsKey(userId) || stats.get(userId) == null) {
                initMemberStats(userId);
            }
            MemberStats ms = stats.get(userId);
            String content = ParseUtil.splitMessage(message.getContentRaw());

            if (content.endsWith("69")) {
                ms.magicNumberCount++;
            } else if (content.endsWith("000")) {
                ms.thousandsCount++;
            } else if (content.endsWith("00")) {
                ms.hundredsCount++;
            }
            ms.counted++;
            counted++;
        }
        currentNum = counted;
    }

    public long getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(long value) {
        currentNum = value;
    }

    public List<Member> getActiveMembers() {
        List<Member> members = new ArrayList<>();
        Guild currentGuild = instance.getConfig().getGuild();

        stats.forEach((id, stats) -> {
            if (stats.counted > 0)
                members.add(currentGuild.getMemberById(id));
        });
        return members;
    }
}
