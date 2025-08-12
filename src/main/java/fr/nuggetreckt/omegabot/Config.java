package fr.nuggetreckt.omegabot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class Config {

    private final OmegaBot instance;

    public Config(OmegaBot instance) {
        this.instance = instance;
    }

    private final boolean dev = false;

    //Guilds ids
    public String devGuildId = "986026862406950993";
    public String guildId = "690132625398300767";

    //Channels ids
    public String verifyChannelId = "828653158720733224";
    public String rulesChannelId = "712708388349542511";
    public String takeRoleChannelId = "828653260135071785";
    public String countChannelId = "828654209787756594";
    public String joinChannelId = "1404776871920406600";
    public String botChannelId = "828655293806149643";
    public String suggestionChannelId = "1404778441345335367";

    //Roles ids
    public String memberRoleId = "828638607584788531";
    public String pollsRoleId = "829261806157430825";
    public String announcementsRoleId = "829261499973238794";
    public String eventsRoleId = "829261934955986944";
    public String interestingInformationsRoleId = "951806862213386272";
    public String minecraftRoleId = "829263205880168479";
    public String hardwareRoleId = "829263540044824637";

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
