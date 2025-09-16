package io.github.davidarthurcole.wgef;

import io.github.davidarthurcole.wgef.events.WGEFEntityMoveEvent;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WGEFEntityListener implements Listener {
    
    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        if (event.isCancelled() || !event.hasChangedPosition() || !event.hasChangedBlock()) return;
        
        var from = event.getFrom();
        var to = event.getTo();
        var entity = event.getEntity();

        Bukkit.getPluginManager().callEvent(new WGEFEntityMoveEvent(entity, from, to));
    }
}
