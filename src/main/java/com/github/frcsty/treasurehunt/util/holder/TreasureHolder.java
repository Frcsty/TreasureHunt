package com.github.frcsty.treasurehunt.util.holder;

import com.github.frcsty.treasurehunt.treasure.type.TreasureType;
import org.apache.commons.lang.WordUtils;
import org.bukkit.block.Block;

public final class TreasureHolder {

    private final TreasureType type;
    private final Block block;

    public TreasureHolder(final Block block, final TreasureType type) {
        this.block = block;
        this.type = type;
    }

    public Block getBlock() {
        return this.block;
    }

    public TreasureType getTreasureType() {
        return this.type;
    }

    public String getFormattedType() {
        return WordUtils.capitalize(type.name().replace("_", " ").toLowerCase());
    }

}
