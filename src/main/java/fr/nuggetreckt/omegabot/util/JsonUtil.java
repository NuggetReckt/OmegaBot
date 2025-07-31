package fr.nuggetreckt.omegabot.util;

import fr.nuggetreckt.omegabot.statistics.MemberStats;
import fr.nuggetreckt.omegabot.statistics.StatsHandler;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class JsonUtil {

    @NotNull
    public static JSONObject createJson(@NotNull List<Member> members, @NotNull StatsHandler statsHandler) {
        JSONArray memberArray = new JSONArray();
        JSONObject obj = new JSONObject();
        long currentNum = statsHandler.getCurrentNum();
        HashMap<String, MemberStats> stats = statsHandler.getMembersStats();

        obj.put("lastUpdated", LocalTime.now().toString());
        obj.put("currentNum", currentNum);
        obj.put("activeMembers", members.size());

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
                MemberStats memberStats = statsHandler.getMemberStats(member.getId());
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
        return obj;
    }

    public static void writeJSON(@NotNull JSONObject obj, @NotNull File file) throws RuntimeException {
        try {
            FileWriter fw = new FileWriter(file.getName());
            fw.write(obj.toJSONString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
