package io.github.davidarthurcole.wgef.listeners;

import io.github.davidarthurcole.wgef.WGEFPlugin;
import io.github.davidarthurcole.wgef.abstraction.WGEFUtils;
import io.github.davidarthurcole.wgef.abstraction.flags.WGEFlags;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static org.bukkit.Material.*;

public class PlayerInteractListener implements Listener {

    private final WGEFPlugin plugin;

    public PlayerInteractListener(WGEFPlugin plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        final var player = e.getPlayer();
        final var block = e.getClickedBlock();
        if (block == null) return;
        final var location = block.getLocation();
        final var hand = e.getHand();

        final var result = resolveInteractResult(player, block, location, hand);
        if(result == Event.Result.DENY) e.setCancelled(true);
    }

    private Event.Result resolveInteractResult(final Player player, final Block block, final Location location, final EquipmentSlot hand) {
        final var trapdoorResult = resolveInteractTrapdoorResult(player, block, location);
        if (trapdoorResult != Event.Result.DEFAULT) return trapdoorResult;

        final var fenceGateResult = resolveInteractFenceGateResult(player, block, location);
        if (fenceGateResult != Event.Result.DEFAULT) return fenceGateResult;

        final var eggResult = resolveInteractSpawnEggResult(player, block, location, hand);
        if (eggResult != Event.Result.DEFAULT) return eggResult;

        final var goatHornResult = resolveInteractGoatHornResult(player, location, hand);
        if (goatHornResult != Event.Result.DEFAULT) return goatHornResult;

        return resolveInteractDecoratedPotResult(player, block, location);
    }

    private Event.Result resolveInteractTrapdoorResult(final Player player, final Block block, final Location location) {
        final var regions = plugin.getFork().getRegionContainer().createQuery().getApplicableRegions(location);
        final Material clickedMaterial = block.getType();

        // Check if the material is a trapdoor by checking if the name ends with "_TRAPDOOR"
        if (!clickedMaterial.name().endsWith("_TRAPDOOR")) return Event.Result.DEFAULT;

        final var deny = WGEFUtils.queryValue(player, location.getWorld(), regions.getRegions(), WGEFlags.DENY_TRAPDOOR_INTERACT);
        if (deny != null && deny) return Event.Result.DENY;

        return Event.Result.DEFAULT;
    }

    private Event.Result resolveInteractFenceGateResult(final Player player, final Block block, final Location location) {
        final var regions = plugin.getFork().getRegionContainer().createQuery().getApplicableRegions(location);
        final Material clickedMaterial = block.getType();

        // Check if the material is a fence gate by checking if the name ends with "_FENCE_GATE"
        if (!clickedMaterial.name().endsWith("_FENCE_GATE")) return Event.Result.DEFAULT;

        final var deny = WGEFUtils.queryValue(player, location.getWorld(), regions.getRegions(), WGEFlags.DENY_FENCE_GATE_INTERACT);
        if (deny != null && deny) return Event.Result.DENY;

        return Event.Result.DEFAULT;
    }

    private Event.Result resolveInteractDecoratedPotResult(final Player player, final Block block, final Location location) {
        final var regions = plugin.getFork().getRegionContainer().createQuery().getApplicableRegions(location);
        final Material clickedMaterial = block.getType();

        if (clickedMaterial != DECORATED_POT) return Event.Result.DEFAULT;

        final var deny = WGEFUtils.queryValue(player, location.getWorld(), regions.getRegions(), WGEFlags.DENY_POT_INTERACT);
        if (deny != null && deny) return Event.Result.DENY;

        return Event.Result.DEFAULT;
    }

    private Event.Result resolveInteractGoatHornResult(final Player player, final Location location, final EquipmentSlot hand) {
        final boolean isHorn;
        if (hand == EquipmentSlot.HAND) {
            isHorn = player.getInventory().getItemInMainHand().getType() == GOAT_HORN;
        } else if (hand == EquipmentSlot.OFF_HAND) {
            isHorn = player.getInventory().getItemInOffHand().getType() == GOAT_HORN;
        } else isHorn = false;
        if (!isHorn) return Event.Result.DEFAULT;

        final var regions = plugin.getFork().getRegionContainer().createQuery().getApplicableRegions(location);
        final var deny = WGEFUtils.queryValue(player, location.getWorld(), regions.getRegions(), WGEFlags.DENY_GOAT_HORN_USE);
        if (deny != null && deny) return Event.Result.DENY;

        return Event.Result.DEFAULT;
    }

    private Event.Result resolveInteractSpawnEggResult(final Player player, final Block block, final Location location, final EquipmentSlot hand) {
        final boolean isEgg;
        if (hand == EquipmentSlot.HAND) {
            isEgg = player.getInventory().getItemInMainHand().getType().name().endsWith("_SPAWN_EGG");
        } else if (hand == EquipmentSlot.OFF_HAND) {
            isEgg = player.getInventory().getItemInOffHand().getType().name().endsWith("_SPAWN_EGG");
        } else isEgg = false;
        if (!isEgg) return Event.Result.DEFAULT;

        final var regions = plugin.getFork().getRegionContainer().createQuery().getApplicableRegions(location);
        final Material clickedMaterial = block.getType();

        final Boolean deny;
        if (clickedMaterial == SPAWNER) {
            deny = WGEFUtils.queryValue(player, location.getWorld(), regions.getRegions(), WGEFlags.DENY_SPAWN_EGG_SPAWNER);
        } else {
            deny = WGEFUtils.queryValue(player, location.getWorld(), regions.getRegions(), WGEFlags.DENY_SPAWN_EGG_SPAWN);
        }
        if (deny != null && deny) return Event.Result.DENY;

        return Event.Result.DEFAULT;
    }
}
