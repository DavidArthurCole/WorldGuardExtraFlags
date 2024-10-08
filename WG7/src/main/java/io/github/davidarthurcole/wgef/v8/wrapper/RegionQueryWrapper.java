package io.github.davidarthurcole.wgef.v8.wrapper;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import io.github.davidarthurcole.wgef.abstraction.wrapper.IRegionQueryWrapper;
import org.bukkit.Location;

public class RegionQueryWrapper implements IRegionQueryWrapper {

    private final RegionQuery regionQuery;

    public RegionQueryWrapper(RegionQuery query) {
        this.regionQuery = query;
    }

    @Override
    public RegionQuery getRegionQuery() {
        return regionQuery;
    }

    @Override
    public ApplicableRegionSet getApplicableRegions(Location location) {
        return regionQuery.getApplicableRegions(BukkitAdapter.adapt(location));
    }

}
