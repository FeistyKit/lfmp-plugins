package com.feisty.lfmp;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import com.feisty.lfmp.Pair;
import java.util.UUID;

public class LFMPHandler implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String name = p.getDisplayName();

        long now = System.currentTimeMilliSeconds();

        UUID u = player.getUniqueId();

        App.playerCurrentTimeMap.put(u, Pair(name, now));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        String name = p.getDisplayName();

        long now = System.currentTimeMilliSeconds();

        UUID u = player.getUniqueId();

        long time_passed = System.currentTimeMilliSeconds() - App.playerCurrentTimeMap.get(u).val;

        App.playerCurrentTimeMap.getOrDefault(u, Pair(name, time_passed)).val = time_passed;
    }


}
