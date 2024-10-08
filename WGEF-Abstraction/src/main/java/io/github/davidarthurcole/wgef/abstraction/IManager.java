package io.github.davidarthurcole.wgef.abstraction;

import io.github.davidarthurcole.wgef.abstraction.dependencies.IEssentialsDependency;

import java.util.Optional;

public interface IManager {

    void load();
    void dependency();
    void enable();

    void disable();

    IWGFork getFork();

    Optional<IEssentialsDependency> getEssentials();

}
