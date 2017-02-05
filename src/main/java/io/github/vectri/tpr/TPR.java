package io.github.vectri.tpr;

import io.github.vectri.tpr.Commands.TPACommand;
import io.github.vectri.tpr.Commands.TPRCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *  The main file for TPR.
 */
public final class TPR extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("tpa").setExecutor(new TPACommand(this));
        this.getCommand("tpr").setExecutor(new TPRCommand(this));
    }
}
