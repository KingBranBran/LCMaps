package me.branbran.lcmaps;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import me.branbran.lcmaps.commands.MenuCommand;
import me.branbran.lcmaps.guis.MapsGUI;
import me.branbran.lcmaps.guis.util.GUIListener;

public class LCMaps extends JavaPlugin
{
    @Override
    public void onEnable() {
        getLogger().info("Starting LCMaps!");

        // Make sure the required files exist.
        try {
            if (!getDataFolder().exists())
                getDataFolder().mkdir();
            File maps = new File(getDataFolder(), "maps.toml");
            if (!maps.exists())
                maps.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Setup GUI listeners
        new GUIListener(this);

        // Setup commands
        getCommand("menu").setExecutor(new MenuCommand());

        // Setup menus
        MapsGUI.init(this);     
    } 

    @Override
    public void onDisable() {
        getLogger().info("Closing LCMaps!");
    }
}
