package io.github.davidarthurcole.wgef.listeners.entity;

import io.github.davidarthurcole.wgef.WGEFPlugin;
import io.github.davidarthurcole.wgef.events.WGEFEntityMoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityMoveListener implements Listener {

    private final WGEFPlugin plugin;

    public EntityMoveListener(WGEFPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityMove(WGEFEntityMoveEvent event) {
        if (!didRegionChange(event)) return;
    }

    private boolean didRegionChange(WGEFEntityMoveEvent event) {
        var from = event.getFrom();
        var to = event.getTo();
        event.fromRegions = plugin.getFork().getRegionContainer().createQuery().getApplicableRegions(from).getRegions();
        event.toRegions = plugin.getFork().getRegionContainer().createQuery().getApplicableRegions(to).getRegions();
        var exitedRegions = event.fromRegions.stream().filter(r -> !event.toRegions.contains(r)).toList();
        var enteredRegions = event.toRegions.stream().filter(r -> !event.fromRegions.contains(r)).toList();
        return !exitedRegions.isEmpty() || !enteredRegions.isEmpty();
    }
}
