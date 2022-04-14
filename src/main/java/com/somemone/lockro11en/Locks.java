package com.somemone.lockro11en;

import org.bukkit.plugin.java.JavaPlugin;

public final class Locks extends JavaPlugin {

    private static JavaPlugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        getServer().getPluginManager().registerEvents(new InteractListener(), this);

        getCommand("lock").setExecutor(new LockCommand());
        getCommand("removelock").setExecutor(new RemoveLockCommand());

    }

    public static JavaPlugin getInstance() {
        return INSTANCE;
    }
}
