package io.github.davidarthurcole.wgef.abstraction.flags;

import com.sk89q.worldguard.protection.flags.*;
import io.github.davidarthurcole.wgef.abstraction.WGEFUtils;
import io.github.davidarthurcole.wgef.abstraction.flags.helpers.*;
import io.github.davidarthurcole.wgef.abstraction.flags.helpers.EntityTypeFlag;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class WGEFlags {

    public final static LocationFlag TELEPORT_ON_ENTRY = new LocationFlag("teleport-on-entry");
    public final static LocationFlag TELEPORT_ON_EXIT = new LocationFlag("teleport-on-exit");

    public final static SetFlag<String> COMMAND_ON_ENTRY = WGEFUtils.getFork().getCustomSetFlag("command-on-entry", new CommandStringFlag(null));
    public final static SetFlag<String> COMMAND_ON_EXIT = WGEFUtils.getFork().getCustomSetFlag("command-on-exit", new CommandStringFlag(null));

    public final static SetFlag<String> CONSOLE_COMMAND_ON_ENTRY = WGEFUtils.getFork().getCustomSetFlag("console-command-on-entry", new CommandStringFlag(null));
    public final static SetFlag<String> CONSOLE_COMMAND_ON_EXIT = WGEFUtils.getFork().getCustomSetFlag("console-command-on-exit", new CommandStringFlag(null));

    public final static DoubleFlag WALK_SPEED = new DoubleFlag("walk-speed");
    public final static DoubleFlag FLY_SPEED = new DoubleFlag("fly-speed");

    public final static BooleanFlag KEEP_INVENTORY = new BooleanFlag("keep-inventory");
    public final static BooleanFlag KEEP_EXP = new BooleanFlag("keep-exp");

    public final static StringFlag CHAT_PREFIX = new StringFlag("chat-prefix");
    public final static StringFlag CHAT_SUFFIX = new StringFlag("chat-suffix");

    public final static SetFlag<PotionEffectType> BLOCKED_EFFECTS = new SetFlag<>("blocked-effects", new PotionEffectTypeFlag(null));

    public final static StateFlag GOD_MODE = new StateFlag("godmode", false);

    public final static StateFlag WORLD_EDIT = new StateFlag("worldedit", true);

    public final static SetFlag<PotionEffect> GIVE_EFFECTS = new SetFlag<>("give-effects", new PotionEffectFlag(null));

    public final static StateFlag FLY = new StateFlag("fly", false);

    public final static StateFlag FROST_WALKER = new StateFlag("frostwalker", true);

    public final static StateFlag NETHER_PORTALS = new StateFlag("nether-portals", true);

    public final static SetFlag<Material> ALLOW_BLOCK_PLACE = new SetFlag<>("allow-block-place", new BlockMaterialFlag(null));
    public final static SetFlag<Material> DENY_BLOCK_PLACE = new SetFlag<>("deny-block-place", new BlockMaterialFlag(null));
    public final static SetFlag<Material> ALLOW_BLOCK_BREAK = new SetFlag<>("allow-block-break", new BlockMaterialFlag(null));
    public final static SetFlag<Material> DENY_BLOCK_BREAK = new SetFlag<>("deny-block-break", new BlockMaterialFlag(null));

    public final static SetFlag<Material> ALLOWED_BLOCK_DROPS = new SetFlag<>("allowed-block-drops", new MaterialFlag(null));
    public final static SetFlag<Material> BLOCKED_BLOCK_DROPS = new SetFlag<>("blocked-block-drops", new MaterialFlag(null));

    public final static BooleanFlag DENY_TRAPDOOR_INTERACT = new BooleanFlag("deny-trapdoor-interact");

    public final static StateFlag GLIDE = new StateFlag("glide", true);

    public final static StateFlag ITEM_DURABILITY = new StateFlag("item-durability", true);

    public final static StateFlag VILLAGER_TRADE = new StateFlag("villager-trade", true);

    public final static SetFlag<EntityType> ALLOW_ENTITY_PLACE = new SetFlag<>("allow-entity-place", new EntityTypeFlag(null));
    public final static SetFlag<EntityType> DENY_ENTITY_PLACE = new SetFlag<>("deny-entity-place", new EntityTypeFlag(null));

    public final static SetFlag<EntityType> ALLOW_ENTITY_DAMAGE = new SetFlag<>("allow-entity-damage", new EntityTypeFlag(null));
    public final static SetFlag<EntityType> DENY_ENTITY_DAMAGE = new SetFlag<>("deny-entity-damage", new EntityTypeFlag(null));

    public final static SetFlag<EntityType> ALLOW_ENTITY_DESTROY = new SetFlag<>("allow-entity-destroy", new EntityTypeFlag(null));
    public final static SetFlag<EntityType> DENY_ENTITY_DESTROY = new SetFlag<>("deny-entity-destroy", new EntityTypeFlag(null));

    public final static BooleanFlag DISABLE_COLLISION = new BooleanFlag("disable-collision");

    public static Set<Flag<?>> values() {
        return Arrays.stream(WGEFlags.class.getFields())
                .map(field -> {
                    try {
                        return (Flag<?>) field.get(null);
                    } catch (IllegalAccessException ignored) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

}
