package com.github.frcsty.treasurehunt.util.action.actions.message;

import com.github.frcsty.treasurehunt.util.action.Action;
import org.bukkit.entity.Player;

public final class ChatMessage implements Action {
    @Override
    public String getID() {
        return "message";
    }

    @Override
    public void run(final Player player, final String data) {
        player.sendMessage(data);
    }
}
