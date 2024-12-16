package io.github.davidarthurcole.wgef.abstraction.flags.helpers;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.FlagContext;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormatException;
import org.bukkit.entity.EntityType;

public class EntityTypeFlag extends Flag<EntityType> {

    public EntityTypeFlag(String name) {
        super(name);
    }

    @Override
    public Object marshal(EntityType o) {
        return formatEntityName(o.name());
    }

    @Override
    public EntityType parseInput(FlagContext context) throws InvalidFlagFormatException {
        try {
            return EntityType.valueOf(formatEntityName(context.getUserInput()));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidFlagFormatException("Unable to find the Entity type! Please refer to https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html for valid ids");
        }
    }

    @Override
    public EntityType unmarshal(Object o) {
        return EntityType.valueOf(formatEntityName(o.toString()));
    }

    private String formatEntityName(String rawName) {
        return rawName.replace("[", "").replace("]", "").toUpperCase();
    }
}