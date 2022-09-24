package me.branbran.lcmaps;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.branbran.gui.GUI;
import me.branbran.lcmaps.commands.MenuCommand;


public class LCMaps extends JavaPlugin
{
    public static GUI menuGui;

    @Override
    @SuppressWarnings("deprecation")
    public void onEnable() {
        getLogger().info("Starting LCMaps!");
        getCommand("menu").setExecutor(new MenuCommand());

        menuGui = GUI.createGui(9, "Maps");

        ItemStack[] items = new ItemStack[9];

        for (int i = 0; i < items.length; i++)
            items[i] = new ItemStack(Material.getMaterial(35), i + 1, (short) i);
        
        menuGui.init(items);

        menuGui.onClick(e -> {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage("You click slot " + e.getSlot());
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("Closing LCMaps!");
    }
}
