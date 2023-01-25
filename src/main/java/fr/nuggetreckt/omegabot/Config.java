package fr.nuggetreckt.omegabot;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class Config {

    //Channels ids
    public String verifyChannelId = "1020311084760047626";
    public String rulesChannelId = "1020311186123796511";
    public String takeRoleChannelId = "1020311310220673054";
    public String pollChannelId = "1020323045790990446";
    public String memeCompetitionChannelId = "1021107083267026975";
    public String joinChannelId = "995102579413487686";
    public String botChannelId = "998975933987967028";
    public String suggestionChannelId = "999414109781102613";

    //Roles ids
    public String memberRoleId = "1020311604585312286";
    public String pollsRoleId = "1020311656477229096";
    public String announcementsRoleId = "1020311696440573962";
    public String eventsRoleId = "1020311742359806022";
    public String interestingInformationsRoleId = "1020311776065241190";
    public String minecraftRoleId = "1020311837532758046";
    public String hardwareRoleId = "1020311879282855997";

    private final Main main = Main.getInstance();

    public MessageChannel getVerifyChannel() {
        return main.getJDA().getTextChannelById(verifyChannelId);
    }

    public MessageChannel getRulesChannel() {
        return main.getJDA().getTextChannelById(rulesChannelId);
    }

    public MessageChannel getTakeRoleChannel() {
        return main.getJDA().getTextChannelById(takeRoleChannelId);
    }

    public MessageChannel getPollChannel() {
        return main.getJDA().getTextChannelById(pollChannelId);
    }

    public MessageChannel getMemeCompetitionChannel() {
        return main.getJDA().getTextChannelById(memeCompetitionChannelId);
    }

    public MessageChannel getJoinChannel() {
        return main.getJDA().getTextChannelById(joinChannelId);
    }

    public MessageChannel getBotChannel() {
        return main.getJDA().getTextChannelById(botChannelId);
    }

    public MessageChannel getSuggestionChannel() {
        return main.getJDA().getTextChannelById(suggestionChannelId);
    }

    public Role getMemberRole() {
        return main.getJDA().getRoleById(memberRoleId);
    }

    public Role getPollsRole() {
        return main.getJDA().getRoleById(pollsRoleId);
    }

    public Role getAnnouncementsRole() {
        return main.getJDA().getRoleById(announcementsRoleId);
    }

    public Role getEventsRole() {
        return main.getJDA().getRoleById(eventsRoleId);
    }

    public Role getInterestingInformationsRole() {
        return main.getJDA().getRoleById(interestingInformationsRoleId);
    }

    public Role getMinecraftRole() {
        return main.getJDA().getRoleById(minecraftRoleId);
    }

    public Role getHardwareRole() {
        return main.getJDA().getRoleById(hardwareRoleId);
    }
}
