package me.branbran.lcmaps.guis.util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class GUI {

    public static HashMap<Inventory, GUI> guis;

    private Inventory inv;
    private String name;

    public OnInventoryClick onClick;
    public OnInventoryDrag onDrag;
    public OnInventoryClose onClose;

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

    public interface OnInventoryClose {
        public void run(GUI g, InventoryCloseEvent e);
    }
}

