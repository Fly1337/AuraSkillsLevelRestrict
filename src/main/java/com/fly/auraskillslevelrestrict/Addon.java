package com.fly.auraskillslevelrestrict;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Addon extends JavaPlugin {

    public static HashMap<Material, Data> data = new HashMap<>();
    public static String global;
    public static Addon instance;
    public static boolean opbypass;
    public static boolean creativebypass;

    @Override
    public void onEnable() {
        instance = this;

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        saveDefaultConfig();

        load();

        getCommand("aurarestrictreload").setExecutor(new ReloadCommand());

        getServer().getPluginManager().registerEvents(new Listener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static void load() {
        FileConfiguration config = instance.getConfig();
        global = config.getString("global-message");
        opbypass = config.getBoolean("op-bypass");
        creativebypass = config.getBoolean("creative-bypass");
        for(String key : config.getConfigurationSection("blocks").getKeys(false)) {
            Material material = Material.getMaterial(key);
            Data data = new Data();
            data.place = config.getBoolean("blocks." + key + ".types.place.use");
            data.breakBlock = config.getBoolean("blocks." + key + ".types.break.use");
            data.placeMessage = config.getString("blocks." + key + ".messages.place.message");
            data.breakMessage = config.getString("blocks." + key + ".messages.break.message");
            for(String level : config.getConfigurationSection("blocks." + key + ".levels").getKeys(false)) {
                data.levels.put(level, config.getInt("blocks." + key + ".levels." + level));
            }
            data.deniedWorlds.addAll(config.getStringList("blocks." + key + ".denied-worlds"));
            Addon.data.put(material, data);
        }
    }
}
