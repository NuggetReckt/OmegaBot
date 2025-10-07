package fr.nuggetreckt.omegabot.config.entity;

import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.NotNull;

public class ChannelConfig extends JDAEntity {

    public ChannelConfig(String id, String discordId) {
        super(id, discordId);
    }

    @Override
    public Object getJDAEntity(@NotNull JDA jda) {
        return jda.getTextChannelById(discordId);
    }
}
