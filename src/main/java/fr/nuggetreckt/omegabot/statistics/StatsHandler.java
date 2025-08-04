package fr.nuggetreckt.omegabot.statistics;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.exception.MemberNotFoundException;
import fr.nuggetreckt.omegabot.util.Bar;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsHandler {

    private final OmegaBot instance;

    private HashMap<String, MemberStats> membersStats;
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
            initJSON(members); //TODO: use SaveUtil instead of initJSON() method
            return;
        }
        instance.getLogger().info("Stats file found. Loading data...");
        membersStats = new HashMap<>();
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

            if (!membersStats.containsKey(memberId) || membersStats.get(memberId) == null)
                initMemberStats(memberId);
            MemberStats memberStats = this.membersStats.get(memberId);
            memberStats.counted = counted;
            memberStats.magicNumberCount = magicNumberCount;
            memberStats.hundredsCount = hundredsCount;
            memberStats.thousandsCount = thousandsCount;
        });
        long expectedCount = getExpectedCountFromLastMessage();

        if (currentNum != expectedCount) { //TODO: A TESTER
            instance.getLogger().warn("Current number was " + currentNum + ", but expected " + expectedCount + ". Set current number to expected value.");
            currentNum = expectedCount;
        }
    }

    public MemberStats getMemberStats(String id) throws MemberNotFoundException {
        if (!membersStats.containsKey(id) || membersStats.get(id) == null) throw new MemberNotFoundException();
        return membersStats.get(id);
    }

    public void initMemberStats(String id) {
        membersStats.putIfAbsent(id, new MemberStats());
    }

    private void initJSON(@NotNull List<Member> members) {
        JSONObject obj = JsonUtil.createJson(members, this);

        //Write JSON object to file
        try {
            if (!jsonFile.createNewFile())
                throw new IOException("Failed to create new file");
            JsonUtil.writeJSON(obj, jsonFile);
        } catch (RuntimeException e) {
            instance.getLogger().error("Failed to write JSON object", e);
        } catch (IOException e) {
            instance.getLogger().error("Failed to create JSON file", e);
        }
    }

    private void initStats() {
        membersStats = new HashMap<>();
        MessageChannel channel = instance.getConfig().getCountChannel();
        MessageHistory history = MessageHistory.getHistoryFromBeginning(channel).complete();
        List<Message> messages = history.getRetrievedHistory().reversed();

        if (messages.isEmpty()) return;
        long expectedCount = getExpectedCountFromLastMessage();
        long counted = 0;
        Bar progressBar = new Bar(50, messages.size());
        int i = 0;
        int j = 1;

        for (Message message : messages) {
            progressBar.update(j);
            progressBar.display();
            j++;

            if (message.getAuthor().isBot()) continue;
            if (!ParseUtil.isMessageValid(message.getContentRaw())) continue;

            if (i > 0) { //TODO: A TESTER
                long beforeNb = ParseUtil.parseMessage(messages.get(i - 1).getContentRaw());
                long currentNb = ParseUtil.parseMessage(message.getContentRaw());

                if (beforeNb != currentNb - 1) continue;
            }

            String userId = message.getAuthor().getId();

            if (!membersStats.containsKey(userId) || membersStats.get(userId) == null)
                initMemberStats(userId);

            MemberStats ms = membersStats.get(userId);
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
            i++;
        }
        System.out.println();
        currentNum = counted;
        if (currentNum != expectedCount) { //TODO: A TESTER
            instance.getLogger().warn("Current number was " + currentNum + ", but expected " + expectedCount + ". Set current number to expected value.");
            currentNum = expectedCount;
        }
    }

    private long getExpectedCountFromLastMessage() {
        MessageChannel channel = instance.getConfig().getCountChannel();
        Message lastMessage = channel.retrieveMessageById(channel.getLatestMessageId()).complete();
        long expectedCount = -1;

        if (lastMessage.getAuthor().isBot()) {
            MessageHistory history = MessageHistory.getHistoryBefore(channel, lastMessage.getId()).complete();
            List<Message> messages = history.getRetrievedHistory();

            for (Message message : messages) {
                if (!message.getAuthor().isBot()) {
                    expectedCount = ParseUtil.parseMessage(message.getContentRaw());
                    break;
                }
            }
        } else {
            expectedCount = ParseUtil.parseMessage(lastMessage.getContentRaw());
        }
        return expectedCount;
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

        membersStats.forEach((id, stats) -> {
            if (stats.counted > 0)
                members.add(currentGuild.getMemberById(id));
        });
        return members;
    }

    public HashMap<String, MemberStats> getMembersStats() {
        return membersStats;
    }

    public final File getFile() {
        return jsonFile;
    }
}
