package com.github.frcsty.treasurehunt.util.action.actions.misc;

import com.github.frcsty.treasurehunt.util.action.Action;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public final class SpawnParticle implements Action {

    @Override
    public String getID() {
        return "particle";
    }

    @Override
    public void run(final Player player, final String data) {
        final String[] args = data.split(";");

        final World world = player.getWorld();
        final String[] coords = args[1].split(" ");

        final Location location = player.getLocation();
        location.setX(Integer.parseInt(coords[0].trim()));
        location.setY(Integer.parseInt(coords[1].trim()));
        location.setZ(Integer.parseInt(ChatColor.stripColor(coords[2].trim())));

        world.spawnParticle(
                Particle.valueOf(args[0].trim().toUpperCase()),
                location,
                2
        );
    }
}
