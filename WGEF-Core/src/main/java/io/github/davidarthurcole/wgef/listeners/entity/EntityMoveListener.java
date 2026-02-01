package io.github.davidarthurcole.wgef.listeners.entity;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.davidarthurcole.wgef.WGEFPlugin;
import io.github.davidarthurcole.wgef.abstraction.WGEFUtils;
import io.github.davidarthurcole.wgef.abstraction.flags.WGEFlags;
import io.github.davidarthurcole.wgef.events.WGEFEntityMoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;
import java.util.stream.Collectors;

public class EntityMoveListener implements Listener {

    private final WGEFPlugin plugin;

    public EntityMoveListener(WGEFPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityMove(WGEFEntityMoveEvent event) {
        if (!didRegionChange(event)) return;
        var destroyForLeave = resolveLeaveShouldDestroy(event, event.leftRegions);
        var destroyForEnter = resolveEntryShouldDestroy(event, event.enteredRegions);
        if (destroyForLeave == Result.DESTROY || destroyForEnter == Result.DESTROY) {
            event.getEntity().remove();
        }
    }

    private enum Result {
        IGNORE,
        DESTROY,
        NO_DESTROY,
    }

    private Result resolveLeaveShouldDestroy(WGEFEntityMoveEvent event, Set<ProtectedRegion> regions) {
        final var entity = event.getEntity();
        final var destroy = WGEFUtils.queryValue(entity, entity.getWorld(), regions, WGEFlags.DESTROY_ENTITY_ON_LEAVE);
        if (destroy != null) return destroy.contains(entity.getType()) ? Result.DESTROY : Result.NO_DESTROY;
        return Result.IGNORE;
    }

    private Result resolveEntryShouldDestroy(WGEFEntityMoveEvent event, Set<ProtectedRegion> regions) {
        final var entity = event.getEntity();
        final var destroy = WGEFUtils.queryValue(entity, entity.getWorld(), regions, WGEFlags.DESTROY_ENTITY_ON_ENTRY);
        if (destroy != null) return destroy.contains(entity.getType()) ? Result.DESTROY : Result.NO_DESTROY;
        return Result.IGNORE;
    }

    private boolean didRegionChange(WGEFEntityMoveEvent event) {
        var from = event.getFrom();
        var to = event.getTo();
        event.fromRegions = plugin.getFork().getRegionContainer().createQuery().getApplicableRegions(from).getRegions();
        event.toRegions = plugin.getFork().getRegionContainer().createQuery().getApplicableRegions(to).getRegions();
        event.leftRegions = event.fromRegions.stream().filter(r -> !event.toRegions.contains(r)).collect(Collectors.toSet());
        event.enteredRegions = event.toRegions.stream().filter(r -> !event.fromRegions.contains(r)).collect(Collectors.toSet());
        return !event.leftRegions.isEmpty() || !event.enteredRegions.isEmpty();
    }
}
