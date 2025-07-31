package fr.nuggetreckt.omegabot.listener;

import fr.nuggetreckt.omegabot.OmegaBot;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MemberLeaveListener extends ListenerAdapter {

    private final OmegaBot instance;

    public MemberLeaveListener(OmegaBot instance) {
        this.instance = instance;
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        instance.getMembers().remove(event.getMember());
    }
}
