package io.github.davidarthurcole.wgef.abstraction.flags.helpers;

import com.sk89q.worldguard.protection.flags.FlagContext;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormatException;
import org.bukkit.Material;

public class BlockMaterialFlag extends MaterialFlag {
    public BlockMaterialFlag(String name) {
        super(name);
    }

    @Override
    public Material parseInput(FlagContext context) throws InvalidFlagFormatException {
        Material material = super.parseInput(context);
        if (!material.isBlock()) {
            throw new InvalidFlagFormatException("This material isn't seen as 'placeable block', use alternative id");
        }
        return material;
    }
}
