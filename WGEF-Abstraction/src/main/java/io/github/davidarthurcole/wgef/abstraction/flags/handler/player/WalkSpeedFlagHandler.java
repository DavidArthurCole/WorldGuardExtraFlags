package io.github.davidarthurcole.wgef.abstraction.flags.handler.player;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import io.github.davidarthurcole.wgef.abstraction.WGEFUtils;
import io.github.davidarthurcole.wgef.abstraction.flags.WGEFlags;
import io.github.davidarthurcole.wgef.abstraction.flags.handler.AbstractFlagHandler;
import org.bukkit.entity.Player;

import java.util.Set;

public class WalkSpeedFlagHandler extends AbstractFlagHandler<Double> {

    public static Factory FACTORY = new Factory();

    public static class Factory extends Handler.Factory<WalkSpeedFlagHandler> {
        @Override
        public WalkSpeedFlagHandler create(Session session) {
            return new WalkSpeedFlagHandler(session);
        }
    }

    protected WalkSpeedFlagHandler(Session session) {
        super(session, WGEFlags.WALK_SPEED);
    }

    @Override
    protected void onInitialValue(LocalPlayer localPlayer, ApplicableRegionSet set, Double value) {}

    @Override
    protected boolean onSetValue(LocalPlayer localPlayer, Location from, Location to, ApplicableRegionSet set, Double currentValue, Double lastValue, MoveType moveType) {
        final Player player = WGEFUtils.wrapPlayer(localPlayer);
        Double newSpeed = WGEFUtils.queryValue(player, player.getWorld(), set.getRegions(), WGEFlags.WALK_SPEED);
        this.handle(player, newSpeed);
        return true;
    }

    @Override
    protected boolean onAbsentValue(LocalPlayer localPlayer, Location from, Location to, ApplicableRegionSet set, Set<ProtectedRegion> exited, Double lastValue, MoveType moveType) {
        final Player player = WGEFUtils.wrapPlayer(localPlayer);
        Double newSpeed = WGEFUtils.queryValue(player, player.getWorld(), set.getRegions(), WGEFlags.WALK_SPEED);
        this.handle(player, newSpeed);
        return true;
    }

    @Override
    public void tick(LocalPlayer localPlayer, ApplicableRegionSet set) {
        final Player player = WGEFUtils.wrapPlayer(localPlayer);
        Double newSpeed = WGEFUtils.queryValue(player, player.getWorld(), set.getRegions(), WGEFlags.WALK_SPEED);
        this.handle(player, newSpeed);
    }

    private Float originalSpeed;

    public void handle(Player player, Double speed) {
        if (speed != null) {
            if (speed > 1.0) {
                speed = 1.0;
            } else if (speed < -1.0) {
                speed = -1.0;
            }

            if (player.getWalkSpeed() != speed.floatValue()) {
                if (this.originalSpeed == null) {
                    this.originalSpeed = player.getWalkSpeed();
                }

                player.setWalkSpeed(speed.floatValue());
            }
        } else {
            if (this.originalSpeed != null) {
                player.setWalkSpeed(this.originalSpeed);
                this.originalSpeed = null;
            }
        }
    }

    public Float getOriginalSpeed() {
        return originalSpeed;
    }

}
