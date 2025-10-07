package fr.nuggetreckt.omegabot.config;

import fr.nuggetreckt.omegabot.OmegaBot;
import fr.nuggetreckt.omegabot.config.entity.ChannelConfig;
import fr.nuggetreckt.omegabot.config.entity.RoleConfig;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private final OmegaBot instance;
    private final JSONObject config;

    private boolean isValid;

    private String token;
    private String guildId;
    private String configVersion;
    private boolean debug;
    private final List<ChannelConfig> channels;
    private final List<RoleConfig> roles;

    public Config(OmegaBot instance, JSONObject config) {
        this.instance = instance;
        this.config = config;
        this.channels = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.isValid = true;
        init();
    }

    private void init() {
        token = (String) config.get("token");
        guildId = (String) config.get("guild_id");
        configVersion = (String) config.get("version");
        debug = (boolean) config.get("debug");

        JSONArray jsonChannels = (JSONArray) config.get("channels");
        JSONArray jsonRoles = (JSONArray) config.get("roles");

        jsonChannels.forEach(m -> {
            JSONObject channel = (JSONObject) m;

            String id = (String) channel.get("id");
            String channelId = (String) channel.get("channel_id");

            if (channelId == null || channelId.isEmpty())
                isValid = false;

            channels.add(new ChannelConfig(id, channelId));
        });

        jsonRoles.forEach(m -> {
            JSONObject role = (JSONObject) m;

            String id = (String) role.get("id");
            String roleId = (String) role.get("role_id");

            if (roleId == null || roleId.isEmpty())
                isValid = false;

            roles.add(new RoleConfig(id, roleId));
        });

        if (token == null || token.isEmpty()) isValid = false;
        if (guildId == null || guildId.isEmpty()) isValid = false;
    }

    public String getConfigVersion() {
        return configVersion;
    }

    public String getToken() {
        return token;
    }

    public boolean isDebug() {
        return debug;
    }

    public Guild getGuild() {
        return instance.getJDA().getGuildById(guildId);
    }

    public MessageChannel getVerifyChannel() {
        return getChannel("verify");
    }

    public MessageChannel getRulesChannel() {
        return getChannel("rules");
    }

    public MessageChannel getTakeRoleChannel() {
        return getChannel("take_role");
    }

    public MessageChannel getCountChannel() {
        return getChannel("counter");
    }

    public MessageChannel getJoinChannel() {
        return getChannel("join");
    }

    public MessageChannel getBotChannel() {
        return getChannel("bot");
    }

    public MessageChannel getSuggestionChannel() {
        return getChannel("suggestions");
    }

    public Role getMemberRole() {
        return getRole("member");
    }

    public Role getPollsRole() {
        return getRole("polls");
    }

    public Role getAnnouncementsRole() {
        return getRole("announcements");
    }

    public Role getEventsRole() {
        return getRole("events");
    }

    public Role getInterestingInformationsRole() {
        return getRole("interesting_infos");
    }

    public Role getMinecraftRole() {
        return getRole("minecraft");
    }

    public Role getHardwareRole() {
        return getRole("hardware");
    }

    public Role getRole(String id) {
        RoleConfig role = getRoleConfig(id);
        if (role == null) return null;
        return (Role) role.getJDAEntity(instance.getJDA());
    }

    public MessageChannel getChannel(String id) {
        ChannelConfig channel = getChannelConfig(id);
        if (channel == null) return null;
        return (MessageChannel) channel.getJDAEntity(instance.getJDA());
    }

    public boolean isValid() {
        return isValid;
    }

    @Nullable
    private ChannelConfig getChannelConfig(String id) {
        for (ChannelConfig channel : channels) {
            if (channel.getId().equals(id))
                return channel;
        }
        return null;
    }

    @Nullable
    private RoleConfig getRoleConfig(String id) {
        for (RoleConfig role : roles) {
            if (role.getId().equals(id))
                return role;
        }
        return null;
    }
}
