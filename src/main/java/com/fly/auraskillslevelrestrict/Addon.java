package com.fly.auraskillslevelrestrict;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Set;

public final class Addon extends JavaPlugin {

    public static HashMap<Material, Data> data = new HashMap<>();
    public static String global;
    public static Addon instance;
    public static boolean opbypass;
    public static boolean creativebypass;

    @Override
    public void onEnable() {
        getLogger().info("Enabling AuraSkillsLevelRestrict with the Version 1.0-SNAPSHOT by phoenixfly1337");
        instance = this;

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        saveDefaultConfig();

        getLogger().info("Loading the config");
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
        ConfigurationSection blocks = config.getConfigurationSection("blocks");

        if(blocks == null) {
            instance.getLogger().info("The \"blocks\" section is not formatted right!");
            return;
        }

        Set<String> keys = blocks.getKeys(false);

        if(keys.isEmpty()) {
            instance.getLogger().info("No blocks found in the config.");
            return;
        }

        int count = 0;
        for(String key : keys) {
            Material material = Material.getMaterial(key);
            if(material == null) {
                instance.getLogger().warning("Material " + key + " not found.");
                continue;
            }
            Data data = new Data();
            data.place = blocks.getBoolean(key + ".types.place.use");
            data.breakBlock = blocks.getBoolean(key + ".types.break.use");
            data.placeMessage = blocks.getString(key + ".types.place.message");
            data.breakMessage = blocks.getString(key + ".types.break.message");
            ConfigurationSection levels = blocks.getConfigurationSection(key + ".levels");
            if(levels != null) {
                for(String level : levels.getKeys(false)) {
                    data.levels.put(level, blocks.getInt(key + ".levels." + level));
                }
            }
            data.permissions.addAll(blocks.getStringList(key + ".permissions"));
            data.deniedWorlds.addAll(blocks.getStringList(key + ".excluded-worlds"));
            Addon.data.put(material, data);
            count++;
        }
        instance.getLogger().info("Loaded " + count + " blocks.");
    }
}
