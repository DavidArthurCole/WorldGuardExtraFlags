package io.github.davidarthurcole.wgef.v8;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.SetFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import io.github.davidarthurcole.wgef.v8.wrapper.SessionManagerWrapper;
import io.github.davidarthurcole.wgef.abstraction.IWGFork;
import io.github.davidarthurcole.wgef.abstraction.flags.helpers.CustomSetFlag;
import io.github.davidarthurcole.wgef.abstraction.wrapper.AbstractSessionManagerWrapper;
import io.github.davidarthurcole.wgef.abstraction.wrapper.IRegionContainerWrapper;
import io.github.davidarthurcole.wgef.v8.wrapper.RegionContainerWrapper;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class IWG7Fork implements IWGFork {

    protected AbstractSessionManagerWrapper sessionManager;
    protected IRegionContainerWrapper regionContainer;

    @Override
    public void enable(JavaPlugin plugin) {
        this.sessionManager = new SessionManagerWrapper(WorldGuard.getInstance().getPlatform().getSessionManager());
        this.regionContainer = new RegionContainerWrapper();
    }

    @Override
    public FlagRegistry getFlagReg() {
        return WorldGuard.getInstance().getFlagRegistry();
    }

    @Override
    public AbstractSessionManagerWrapper getSessionManager() {
        return this.sessionManager;
    }

    @Override
    public IRegionContainerWrapper getRegionContainer() {
        return this.regionContainer;
    }

    @Override
    public LocalPlayer wrap(Player player) {
        return WorldGuardPlugin.inst().wrapPlayer(player);
    }

    @Override
    public <T> SetFlag<T> getCustomSetFlag(String name, Flag<T> flag) {
        return new CustomSetFlag<T>(name, flag);
    }

}
