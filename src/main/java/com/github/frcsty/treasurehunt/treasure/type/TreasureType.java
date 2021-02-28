package com.github.frcsty.treasurehunt.treasure.type;

import com.github.frcsty.treasurehunt.TreasureHuntPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public final class TreasureType {

    private static final SplittableRandom RANDOM = new SplittableRandom();
    private static final TreasureHuntPlugin PLUGIN = JavaPlugin.getPlugin(TreasureHuntPlugin.class);
    private static final List<TreasureSubType> TREASURE_TYPES = new ArrayList<>();

    public static void init() {
        final FileConfiguration configuration = PLUGIN.getConfig();
        final ConfigurationSection section = configuration.getConfigurationSection("treasures");
        if (section == null) {
            throw new RuntimeException("Configuration Section for Treasure Types is null!");
        }

        for (final String key : section.getKeys(false)) {
            if (key.equals("chance-range")) continue;

            final String texture = section.getString(String.format("%s.texture", key));
            final int points = section.getInt(String.format("%s.points", key));
            final String chance = section.getString(String.format("%s.chance", key));

            TREASURE_TYPES.add(new TreasureSubType(
                    key, points, texture, chance
            ));
        }
    }

    public static TreasureSubType getRandomTreasureType() {
        final int chance = RANDOM.nextInt(0, PLUGIN.getConfig().getInt("treasures.chance-range") + 1);
        TreasureSubType result = null;

        for (final TreasureSubType treasure : TREASURE_TYPES) {
            final String[] components = treasure.getChance().split(";");

            final int from = Integer.parseInt(components[0]);
            final int to = Integer.parseInt(components[1]);
            if (chance > from && chance <= to) {
                result = treasure;
                break;
            }
        }

        return result == null ? TREASURE_TYPES.get(0) : result;
    }

    public static class TreasureSubType {
        private final String type;
        private final int points;
        private final String texture;
        private final String chance;

        public TreasureSubType(final String type, final int points, final String texture, final String chance) {
            this.type = type;
            this.points = points;
            this.texture = texture;
            this.chance = chance;
        }

        public String getType() {
            return this.type;
        }

        public int getPoints() {
            return this.points;
        }

        public String getTexture() {
            return this.texture;
        }

        String getChance() {
            return this.chance;
        }

    }

}
