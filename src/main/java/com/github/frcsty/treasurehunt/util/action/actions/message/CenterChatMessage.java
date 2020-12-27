package com.github.frcsty.treasurehunt.util.action.actions.message;

import com.github.frcsty.treasurehunt.util.action.Action;
import com.github.frcsty.treasurehunt.util.action.utils.DefaultFontInfo;
import org.bukkit.entity.Player;

public final class CenterChatMessage implements Action {
    @Override
    public String getID() {
        return "centermessage";
    }

    @Override
    public void run(final Player player, final String message) {
        player.sendMessage(DefaultFontInfo.centerMessage(message));
    }
}