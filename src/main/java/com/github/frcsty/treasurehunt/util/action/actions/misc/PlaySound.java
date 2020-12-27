package com.github.frcsty.treasurehunt.util.action.actions.misc;

import com.github.frcsty.treasurehunt.util.action.Action;
import com.google.common.primitives.Floats;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public final class PlaySound implements Action {
    private final List<String> sounds = new LinkedList<>();

    public PlaySound() {
        for (Sound sound : Sound.values()) {
            sounds.add(sound.name());
        }
    }

    @Override
    public String getID() {
        return "sound";
    }

    @Override
    public void run(final Player player, final String data) {
        if (!data.contains(" ")) {
            return;
        }

        final String[] parts = data.toUpperCase().split(" ");

        if (!sounds.contains(parts[0])) {
            return;
        }

        final Sound sound = Sound.valueOf(parts[0]);
        final Float volume = parts.length > 1 ? Floats.tryParse(parts[1]) : null;
        final Float pitch = parts.length > 2 ? Floats.tryParse(parts[2]) : null;

        player.playSound(player.getLocation(), sound, volume == null ? 0f : volume, pitch == null ? 1.0f : pitch);
    }
}
