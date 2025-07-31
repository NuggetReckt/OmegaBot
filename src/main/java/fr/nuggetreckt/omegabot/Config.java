package fr.nuggetreckt.omegabot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class Config {

    private final OmegaBot instance;

    public Config(OmegaBot instance) {
        this.instance = instance;
    }

    private final boolean dev = true;

    //Guilds ids
    public String devGuildId = "986026862406950993";
    public String guildId = "690132625398300767";

    //Channels ids
    public String verifyChannelId = "1020311084760047626";
    public String rulesChannelId = "1020311186123796511";
    public String takeRoleChannelId = "1020311310220673054";
    public String countChannelId = "1021107083267026975";
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

    public Guild getGuild() {
        if (dev) return instance.getJDA().getGuildById(devGuildId);
        return instance.getJDA().getGuildById(guildId);
    }

    public MessageChannel getVerifyChannel() {
        return instance.getJDA().getTextChannelById(verifyChannelId);
    }

    public MessageChannel getRulesChannel() {
        return instance.getJDA().getTextChannelById(rulesChannelId);
    }

    public MessageChannel getTakeRoleChannel() {
        return instance.getJDA().getTextChannelById(takeRoleChannelId);
    }

    public MessageChannel getCountChannel() {
        return instance.getJDA().getTextChannelById(countChannelId);
    }

    public MessageChannel getJoinChannel() {
        return instance.getJDA().getTextChannelById(joinChannelId);
    }

    public MessageChannel getBotChannel() {
        return instance.getJDA().getTextChannelById(botChannelId);
    }

    public MessageChannel getSuggestionChannel() {
        return instance.getJDA().getTextChannelById(suggestionChannelId);
    }

    public Role getMemberRole() {
        return instance.getJDA().getRoleById(memberRoleId);
    }

    public Role getPollsRole() {
        return instance.getJDA().getRoleById(pollsRoleId);
    }

    public Role getAnnouncementsRole() {
        return instance.getJDA().getRoleById(announcementsRoleId);
    }

    public Role getEventsRole() {
        return instance.getJDA().getRoleById(eventsRoleId);
    }

    public Role getInterestingInformationsRole() {
        return instance.getJDA().getRoleById(interestingInformationsRoleId);
    }

    public Role getMinecraftRole() {
        return instance.getJDA().getRoleById(minecraftRoleId);
    }

    public Role getHardwareRole() {
        return instance.getJDA().getRoleById(hardwareRoleId);
    }
}
