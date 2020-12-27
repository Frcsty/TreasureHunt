package com.github.frcsty.treasurehunt.util.action.actions.message;

import com.github.frcsty.treasurehunt.util.action.Action;
import com.github.frcsty.treasurehunt.util.action.utils.StringUtil;
import org.bukkit.entity.Player;

public final class PermissionBroadcastMessage implements Action {
    @Override
    public String getID() {
        return "permissionbroadcast";
    }

    @Override
    public void run(final Player player, final String data) {
        if (data.contains(" ")) {
            final String[] parts = data.split(" ", 2);
            StringUtil.broadcast(parts[1], parts[0]);
        }
    }
}
