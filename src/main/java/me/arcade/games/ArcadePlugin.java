package me.arcade.games;

import me.arcade.games.arena.ArenaManager;
import me.arcade.games.command.ArcadeCommand;
import me.arcade.games.game.GameListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class ArcadePlugin extends JavaPlugin {
    private ArenaManager manager;

    @Override
    public void onEnable() {
        manager = new ArenaManager(this);
        getServer().getPluginManager().registerEvents(new GameListener(this), this);
        ArcadeCommand cmd = new ArcadeCommand(this);
        PluginCommand pc = getCommand("arcade");
        if (pc != null) {
            pc.setExecutor(cmd);
            pc.setTabCompleter(cmd);
        }
        // load next tick so every world (Multiverse etc.) is available
        getServer().getScheduler().runTask(this, manager::load);
    }

    @Override
    public void onDisable() {
        if (manager != null) manager.shutdown();
    }

    public ArenaManager manager() {
        return manager;
    }
}
