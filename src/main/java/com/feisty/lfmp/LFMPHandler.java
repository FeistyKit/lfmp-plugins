package com.feisty.lfmp;

import org.bukkit.entity.Player;
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

        long now = System.currentTimeMillis();

        UUID u = p.getUniqueId();

        App.playerCurrentTimeMap.put(u, now);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        String name = p.getDisplayName();

        long now = System.currentTimeMillis();

        UUID u = p.getUniqueId();

        long time_passed = System.currentTimeMillis() - App.playerCurrentTimeMap.get(u); // The player will always have joined before leaving

        App.playerTotalTimeMap.getOrDefault(u, new Pair(name, time_passed)).val = time_passed;
    }


}
