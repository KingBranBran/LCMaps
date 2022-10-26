package me.branbran.lcmaps.guis.util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GUI {

    private JavaPlugin plugin;

    public static HashMap<Inventory, GUI> guis;

    private Inventory inv;
    private String name;

    public OnInventoryClick onClick;
    public OnInventoryDrag onDrag;

    public GUI(int size, String name) {
        inv = Bukkit.createInventory(null, size, name);
        defaultEvents();
    }

    public GUI(InventoryType type, String name) {
        inv = Bukkit.createInventory(null, type, name);
        defaultEvents();
    }

    public void defaultEvents() {
        onClick = e -> e.setCancelled(true);
        onDrag = e -> e.setCancelled(true);
    }

    public void init(ItemStack[] items) {
        if (items.length != inv.getSize()) {
            plugin.getLogger().severe("The amount of items " + items.length +" does not mach the amount of items inventory " + inv.getSize());
            return;
        }
        
        inv.addItem(items);
    }

    public void open(HumanEntity entity) {
        entity.openInventory(inv);
    }

    public void onClick(OnInventoryClick onClick) {
        this.onClick = onClick;
    }

    
    public Inventory getInv() {
        return inv;
    }

    public String getName() {
        return name;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    public GUI cloneSize() {
        GUI gui = new GUI(inv.getSize(), name);
        gui.inv.setContents(inv.getContents());
        gui.onClick = onClick;
        gui.onDrag = onDrag;

        return gui;
    }

    public GUI cloneType() {
        GUI gui = new GUI(inv.getType(), name);
        gui.inv.setContents(inv.getContents());
        gui.onClick = onClick;
        gui.onDrag = onDrag;

        return gui;
    }

    // This will just remove it from the HashMap.
    public void delete() {
        guis.remove(inv);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((inv == null) ? 0 : inv.hashCode());
        return result;
    }

    // Gui and Inventory are considered equal if Gui inv and the Inventory is the same object.
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() == obj.getClass()) {
            GUI other = (GUI) obj;
            if (inv == null) {
                if (other.inv != null)
                    return false;
            } else if (!inv.equals(other.inv))
                return false;
            return true;
        } else if (obj.getClass() == Inventory.class) {
            Inventory other = (Inventory) obj;
            if (inv == null) {
                if (other != null)
                    return false;
            } else if (!inv.equals(inv))
                return false;
            return true;
        } 
        return false;     
    }

    public static GUI createGui(InventoryType type, String name) {
        GUI gui = new GUI(type, name);
        guis.put(gui.getInv(), gui);
        return gui;
    }

    public static GUI createGui(int size, String name) {
        GUI gui = new GUI(size, name);
        guis.put(gui.getInv(), gui);
        return gui;
    }

    public static void addGui(GUI gui) {
        guis.put(gui.getInv(), gui);
    }

    public interface OnInventoryClick {
        public void run(InventoryClickEvent e);
    }

    public interface OnInventoryDrag {
        public void run(InventoryDragEvent e);
    }
}

