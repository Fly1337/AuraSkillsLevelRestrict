package com.fly.auraskillslevelrestrict;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        org.bukkit.Material material = event.getBlock().getType();
        if(Addon.data.containsKey(material)) {
            com.fly.auraskillslevelrestrict.Data data = Addon.data.get(material);
            if(data.place) {
                if(player.getGameMode() == org.bukkit.GameMode.CREATIVE && !Addon.creativebypass) {
                    event.setCancelled(true);
                    String message = data.placeMessage == null ? Addon.global : data.placeMessage;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('§', message));
                    return;
                }
                if(player.isOp() && !Addon.opbypass) {
                    event.setCancelled(true);
                    String message = data.placeMessage == null ? Addon.global : data.placeMessage;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('§', message));
                    return;
                }
                if(data.deniedWorlds.contains(player.getWorld().getName())) {
                    return;
                }
                for(String permission : data.permissions) {
                    if(!player.hasPermission(permission)) {
                        event.setCancelled(true);
                        String message = data.placeMessage == null ? Addon.global : data.placeMessage;
                        player.sendMessage(ChatColor.translateAlternateColorCodes('§', message));
                        return;
                    }
                }
                SkillsUser user = AuraSkillsApi.get().getUser(player.getUniqueId());
                for(String level : data.levels.keySet()) {
                    if(user.getSkillLevel(Skills.valueOf(level.toUpperCase())) < data.levels.get(level)) {
                        event.setCancelled(true);
                        String message = data.placeMessage == null ? Addon.global : data.placeMessage;
                        player.sendMessage(ChatColor.translateAlternateColorCodes('§', message));
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        org.bukkit.Material material = event.getBlock().getType();
        if(Addon.data.containsKey(material)) {
            com.fly.auraskillslevelrestrict.Data data = Addon.data.get(material);
            if(data.breakBlock) {
                if(player.getGameMode() == org.bukkit.GameMode.CREATIVE && !Addon.creativebypass) {
                    event.setCancelled(true);
                    event.setDropItems(false);
                    String message = data.breakMessage == null ? Addon.global : data.breakMessage;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('§', message));
                    return;
                }
                if(player.isOp() && !Addon.opbypass) {
                    event.setCancelled(true);
                    event.setDropItems(false);
                    String message = data.breakMessage == null ? Addon.global : data.breakMessage;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('§', message));
                    return;
                }
                if(data.deniedWorlds.contains(player.getWorld().getName())) {
                    return;
                }
                for(String permission : data.permissions) {
                    if(!player.hasPermission(permission)) {
                        event.setCancelled(true);
                        event.setDropItems(false);
                        String message = data.breakMessage == null ? Addon.global : data.breakMessage;
                        player.sendMessage(ChatColor.translateAlternateColorCodes('§', message));
                        return;
                    }
                }
                SkillsUser user = AuraSkillsApi.get().getUser(player.getUniqueId());
                for(String level : data.levels.keySet()) {
                    if(user.getSkillLevel(Skills.valueOf(level.toUpperCase())) < data.levels.get(level)) {
                        event.setCancelled(true);
                        event.setDropItems(false);
                        String message = data.breakMessage == null ? Addon.global : data.breakMessage;
                        player.sendMessage(ChatColor.translateAlternateColorCodes('§', message));
                        return;
                    }
                }
            }
        }
    }
}
