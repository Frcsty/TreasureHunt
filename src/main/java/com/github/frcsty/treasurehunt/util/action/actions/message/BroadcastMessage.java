package com.github.frcsty.treasurehunt.util.action.actions.message;

import com.github.frcsty.treasurehunt.util.action.Action;
import com.github.frcsty.treasurehunt.util.action.utils.StringUtil;
import org.bukkit.entity.Player;

public final class BroadcastMessage implements Action {
    @Override
    public String getID() {
        return "broadcast";
    }

    @Override
    public void run(final Player player, final String message) {
        StringUtil.broadcast(message);
    }

}
