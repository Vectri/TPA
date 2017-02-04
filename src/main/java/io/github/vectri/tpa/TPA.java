package io.github.vectri.tpa;

import io.github.vectri.tpa.Requests.RequestHandler;
import io.github.vectri.tpa.Commands.TPACommand;
import io.github.vectri.tpa.Commands.TPRCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *  The main file for TPA.
 */
public final class TPA extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("tpa").setExecutor(new TPACommand());
        this.getCommand("tpr").setExecutor(new TPRCommand());
        new RequestHandler(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
