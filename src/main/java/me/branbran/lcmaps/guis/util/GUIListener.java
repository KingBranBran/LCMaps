package me.branbran.lcmaps.guis.util;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class GUIListener implements Listener {

    private static HashMap<Inventory, GUI> guis;
    
    public GUIListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        GUI.guis = new HashMap<>();
        guis = GUI.guis;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        GUI gui = guis.get(e.getClickedInventory());
        if (gui != null)
            gui.onClick.run(e);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        GUI gui = guis.get(e.getInventory());
        if (gui != null)
            gui.onDrag.run(e);
    }
}

