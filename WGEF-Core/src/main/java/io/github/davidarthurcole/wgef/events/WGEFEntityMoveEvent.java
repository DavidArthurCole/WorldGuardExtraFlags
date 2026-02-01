package io.github.davidarthurcole.wgef.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class WGEFEntityMoveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final LivingEntity entity;
    private final Location from;
    private final Location to;

    public Set<ProtectedRegion> fromRegions;
    public Set<ProtectedRegion> toRegions;

    public Set<ProtectedRegion> leftRegions;
    public Set<ProtectedRegion> enteredRegions;

    public LivingEntity getEntity() {
        return entity;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public WGEFEntityMoveEvent(LivingEntity entity, Location from, Location to) {
        super(true);
        this.entity = entity;
        this.from = from;
        this.to = to;
    }

    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
