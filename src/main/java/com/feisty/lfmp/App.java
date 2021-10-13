package com.feisty.lfmp;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Map;
import java.util.HashMap;
import com.feisty.lfmp.Pair;
import java.util.UUID;
import org.bukkit.Bukkit;


public class App extends JavaPlugin
{
    // This is how I will store the total time that a player has spent logged on to the server
    // Format: UUID : (Name, time)
    public static HashMap<UUID, Pair<String, Long>> playerTotalTimeMap;

    // Stores the time the player logged in.
    // Format: UUID : time
    public static HashMap<UUID, Long> playerCurrentTimeMap;


    @Override
    public void onEnable() {
        playerTotalTimeMap = new HashMap<>();
        playerCurrentTimeMap = new HashMap<>();
        getCommand("lfmpboard").setExecutor(new BoardCommand());
        System.out.println("L'FMP Plugin has been Enabled!");
    }

    // Flushes the playerCurrentTimeMap to playerTotalTimeMap
    public static void flushMaps() {
        for (Map.Entry<UUID, Long> m : playerCurrentTimeMap.entrySet()) {
            UUID u = m.getKey();
            // https://www.spigotmc.org/threads/get-player-name-from-uuid.77232/?__cf_chl_jschl_tk__=pmd_f0cwy3htxRmNN7cjhF0lbgacwC1zqWfaW46MEWLPWpY-1634124544-0-gqNtZGzNAiWjcnBszQg9
            String name = Bukkit.getPlayer(u).getDisplayName();
            long timeDiff = System.currentTimeMillis() - m.getValue();
            timeDiff += playerTotalTimeMap.get(u).val;
            playerTotalTimeMap.put(u, new Pair(name, timeDiff));
        }
        playerCurrentTimeMap = new HashMap<>();

    }


}
