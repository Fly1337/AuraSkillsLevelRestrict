package com.fly.auraskillslevelrestrict;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Listener implements org.bukkit.event.Listener {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        // For Code Comments check the BlockBreakEvent below
        Player player = event.getPlayer();
        Material material = event.getBlock().getType();
        if(Addon.data.containsKey(material)) {
            com.fly.auraskillslevelrestrict.Data data = Addon.data.get(material);
            if(data.place) {

                if(data.deniedWorlds.contains(player.getWorld().getName())) {
                    return;
                }

                SkillsUser user = AuraSkillsApi.get().getUser(player.getUniqueId());
                for(String level : data.levels.keySet()) {
                    if(user.getSkillLevel(Skills.valueOf(level.toUpperCase())) < data.levels.get(level)) {
                        event.setCancelled(true);
                        String message = data.placeMessage == null ? Addon.global : data.placeMessage;
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return;
                    }
                }

                for(String permission : data.permissions) {
                    if(!player.hasPermission(permission)) {
                        event.setCancelled(true);
                        String message = data.placeMessage == null ? Addon.global : data.placeMessage;
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return;
                    }
                }

                if(player.getGameMode() == GameMode.CREATIVE && !Addon.creativebypass) {
                    event.setCancelled(true);
                    String message = data.placeMessage == null ? Addon.global : data.placeMessage;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    return;
                }
                if(player.isOp() && !Addon.opbypass) {
                    event.setCancelled(true);
                    String message = data.placeMessage == null ? Addon.global : data.placeMessage;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material material = event.getBlock().getType();
        if(Addon.data.containsKey(material)) {
            Data data = Addon.data.get(material);
            if(data.breakBlock) {

                // Checking for denied worlds that can be ignored
                if(data.deniedWorlds.contains(player.getWorld().getName())) {
                    return;
                }

                // Check for the skill levels as those are the main focus
                SkillsUser user = AuraSkillsApi.get().getUser(player.getUniqueId());
                for(String level : data.levels.keySet()) {
                    if(user.getSkillLevel(Skills.valueOf(level.toUpperCase())) < data.levels.get(level)) {
                        event.setCancelled(true);
                        event.setDropItems(false); // Prevents messes with async events
                        String message = data.breakMessage == null ? Addon.global : data.breakMessage;
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return;
                    }
                }

                // Check for permissions that are required besides the levels
                for(String permission : data.permissions) {
                    if(!player.hasPermission(permission)) {
                        event.setCancelled(true);
                        event.setDropItems(false);
                        String message = data.breakMessage == null ? Addon.global : data.breakMessage;
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        return;
                    }
                }

                if(player.getGameMode() == GameMode.CREATIVE && !Addon.creativebypass) {
                    event.setCancelled(true);
                    event.setDropItems(false);
                    String message = data.breakMessage == null ? Addon.global : data.breakMessage;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    return;
                }

                if(player.isOp() && !Addon.opbypass) {
                    event.setCancelled(true);
                    event.setDropItems(false);
                    String message = data.breakMessage == null ? Addon.global : data.breakMessage;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
    }
}
