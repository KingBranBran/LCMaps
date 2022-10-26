package me.branbran.lcmaps.guis;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.moandjiezana.toml.Toml;

import me.branbran.lcmaps.LCMaps;
import me.branbran.lcmaps.guis.util.GUI;

public final class MapsGUI {

    private static File pluginFolderPath;
    private static HashMap<Integer, Map> maps;
    private static ItemStack[] mapItems;
    // private static HashMap<UUID, Integer> playerGuis; 

    public static void init(LCMaps plugin) {
        pluginFolderPath = plugin.getDataFolder();
        maps = new HashMap<>();
        loadMaps();
        createMenuItems();
    }

    public static void loadMaps() {
        
        File file = new File(pluginFolderPath, "maps.toml");
        Toml toml = new Toml().read(file);
        List<Toml> tomls = toml.getTables("Map");
        // Linked list is way more optimal to do this logic.
        LinkedList<Toml> tomlMaps = new LinkedList<>(tomls);

        int mapNum = tomlMaps.size();
        System.out.println("size is " + mapNum);

        // For hashmap, set the key to be the index, and value to be the Map class.
        for (int i = 0; i < mapNum; i++)
            maps.put(i, tomlMaps.pop().to(Map.class));
    }
    
    @SuppressWarnings("deprecation")
    public static void createMenuItems() {    
        mapItems = new ItemStack[maps.size()];

        // Populate mapItems with all the maps' info!
        for (int i = 0; i < maps.size(); i++)
        {
            Map m = maps.get(i);
            int item = m.item.get(0);
            int data = m.item.get(1);
            ItemStack is = new ItemStack(Material.getMaterial(item), 1, (byte) data);

            // Create lore and display info
            LinkedList<String> lore = new LinkedList<>();
            lore.add("Creator: " + m.creator);
            lore.add("Stars: " + m.star_rating);
            lore.add("PP: " + m.pp);
            lore.add("Length: " + m.length);

            System.out.println("put in " + m.name);

            ItemMeta im = is.getItemMeta();
            im.setDisplayName(m.name);
            im.setLore(lore);

            is.setItemMeta(im);

            mapItems[i] = is;
        };
    }

    public static void openMenu(HumanEntity player) {
        GUI menu = GUI.createGui(27, "Linkcraft Maps");
        ItemStack[] items = Arrays.copyOfRange(mapItems, 0, 27);
        Inventory inv = menu.getInv();

        for (int i = 0; i < items.length; i++)
            inv.setItem(i, items[i]);

        // delete menu when its closed
        menu.onClose = (g, e) -> {
            g.delete();
        };

        menu.open(player);
    }

    class Map {
        String name;
        String id;
        String creator;
        double star_rating;
        double pp;
        String length;
        List<Integer> item;
    }
}
