package com.feisty.lfmp;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import com.feisty.lfmp.Pair;
import java.util.UUID;


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


}
