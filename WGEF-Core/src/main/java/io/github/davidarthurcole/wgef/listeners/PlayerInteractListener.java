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

import java.util.Objects;

import static org.bukkit.Material.DECORATED_POT;
import static org.bukkit.Material.GOAT_HORN;

public class PlayerInteractListener implements Listener {

    private final WGEFPlugin plugin;

    public PlayerInteractListener(WGEFPlugin plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        final var player = e.getPlayer();
        final var block = e.getClickedBlock();
        if (block == null) return;
        final var location = block.getLocation();

        final var result = resolveInteractResult(player, block, location);
        if(result == Event.Result.DENY) e.setCancelled(true);
    }

    private Event.Result resolveInteractResult(final Player player, final Block block, final Location location) {
        final var trapdoorResult = resolveInteractTrapdoorResult(player, block, location);
        if (trapdoorResult != Event.Result.DEFAULT) return trapdoorResult;

        final var fenceGateResult = resolveInteractFenceGateResult(player, block, location);
        if (fenceGateResult != Event.Result.DEFAULT) return fenceGateResult;

        final var decoratedPotResult = resolveInteractDecoratedPotResult(player, block, location);
        if (decoratedPotResult != Event.Result.DEFAULT) return decoratedPotResult;

        return resolveInteractGoatHornResult(player, location);
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

    private Event.Result resolveInteractGoatHornResult(final Player player, final Location location) {
        final var regions = plugin.getFork().getRegionContainer().createQuery().getApplicableRegions(location);

        final var isGoatHorn = Objects.requireNonNull(player.getItemInUse()).getType() == GOAT_HORN;
        if (!isGoatHorn) return Event.Result.DEFAULT;

        final var deny = WGEFUtils.queryValue(player, location.getWorld(), regions.getRegions(), WGEFlags.DENY_GOAT_HORN_USE);
        if (deny != null && deny) return Event.Result.DENY;

        return Event.Result.DEFAULT;
    }
}
