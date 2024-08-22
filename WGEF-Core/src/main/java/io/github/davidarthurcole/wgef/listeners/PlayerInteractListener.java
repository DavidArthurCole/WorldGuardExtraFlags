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

public class PlayerInteractListener implements Listener {

    private final WGEFPlugin plugin;

    public PlayerInteractListener(WGEFPlugin plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onPlayerInteract(PlayerInteractEvent e) {
        final var player = e.getPlayer();
        final var block = e.getClickedBlock();
        if (block == null) return;
        final var location = block.getLocation();

        final var result = resolveInteractResult(player, block, location);
        if(result == Event.Result.DENY) e.setCancelled(true);
    }

    private Event.Result resolveInteractResult(final Player player, final Block block, final Location location) {
        final var regions = plugin.getFork().getRegionContainer().createQuery().getApplicableRegions(location);
        final Material clickedMaterial = block.getType();

        // Check if the material is a trapdoor by checking if the name ends with "_TRAPDOOR"
        if (!clickedMaterial.name().endsWith("_TRAPDOOR")) return Event.Result.DEFAULT;

        final var allowed = WGEFUtils.queryValue(player, location.getWorld(), regions.getRegions(), WGEFlags.ALLOW_TRAPDOOR_INTERACT);
        if (allowed != null && allowed) return Event.Result.ALLOW;

        final var deny = WGEFUtils.queryValue(player, location.getWorld(), regions.getRegions(), WGEFlags.DENY_TRAPDOOR_INTERACT);
        if (deny != null && deny) return Event.Result.DENY;

        return Event.Result.DEFAULT;
    }
}
