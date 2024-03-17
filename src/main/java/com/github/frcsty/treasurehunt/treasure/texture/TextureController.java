package com.github.frcsty.treasurehunt.treasure.texture;

import com.github.frcsty.treasurehunt.treasure.type.TreasureType;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;

import java.lang.reflect.Field;
import java.util.UUID;

public final class TextureController {

    public static Block getTexturedBlock(final Location location, final TreasureType.TreasureSubType type) {
        final Block block = location.getBlock();

        block.setType(Material.PLAYER_HEAD);
        final BlockState state = block.getState();
        final Skull skull = getTexturedMeta((Skull) state, type);

        skull.update();
        return block;
    }

    private static Skull getTexturedMeta(final Skull skullMeta, final TreasureType.TreasureSubType type) {
        final GameProfile profile = new GameProfile(UUID.randomUUID(), "Frcsty");
        profile.getProperties().put("textures", new Property("textures", type.getTexture()));

        try {
            final Field field = skullMeta.getClass().getDeclaredField("profile");

            field.setAccessible(true);
            field.set(skullMeta, profile);
        } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return skullMeta;
    }

}