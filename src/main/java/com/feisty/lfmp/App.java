package com.feisty.lfmp;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.HashMap;
import com.feisty.lfmp.Pair;
import java.util.UUID;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.Arrays;
import com.feisty.lfmp.CustomBot;
import java.io.IOException;
import java.io.FileNotFoundException;

public class App extends JavaPlugin
{
    // This is how I will store the total time that a player has spent logged on to the server
    // Format: UUID : (Name, time)
    public static HashMap<UUID, Pair<String, Long>> playerTotalTimeMap;

    // Stores the time the player logged in.
    // Format: UUID : time
    public static HashMap<UUID, Long> playerCurrentTimeMap;

    // the location where it is saved
    private static String serName;


    public static CustomBot bot;

    @Override
    public void onEnable() {
        createBot();

        playerTotalTimeMap = new HashMap<>();
        playerCurrentTimeMap = new HashMap<>();

        getCommand("lfmpboard").setExecutor(new BoardCommand());
        System.out.println("L'FMP Plugin has been Enabled!");
    }

    @Override
    public void onDisable() {
        flushMaps();
        saveMaps();
    }


    // save hashmaps
    private static void saveMaps() {
        try {
            // https://www.geeksforgeeks.org/serialization-in-java/
            FileOutputStream file = new FileOutputStream(serName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(playerTotalTimeMap);
            out.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // initialize hashmaps
    private static void initMaps() {
        try {
            playerCurrentTimeMap = new HashMap<>();

            FileInputStream file = new FileInputStream(serName);
            ObjectInputStream in = new ObjectInputStream(file);
            playerTotalTimeMap = (HashMap<UUID, Pair<String, Long>>) in.readObject();
        } catch (Exception e) {

            e.printStackTrace();
            System.exit(1);
        }
    }




    //creates the bot. duh.
    private static void createBot() {
        try {
            bot = new CustomBot("authentication.txt", 0);
            System.out.println("Bot has been enabled!");
        } catch (Exception e) {
            System.err.println("Bot could not be enabled: ");
            e.printStackTrace();
            System.exit(1);
        }
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
            playerCurrentTimeMap.put(u, System.currentTimeMillis());
        }
    }


    // sorts the list of players and their scores and returns them in a sorted list of strings.
    public static ArrayList<String> fmtScores() {
        flushMaps();

        ArrayList<String> toReturn = new ArrayList<>();

        Pair<String, Long>[] scores = (Pair<String, Long>[]) playerTotalTimeMap.values().toArray();

        Arrays.sort(scores, (Pair<String, Long> score1,  Pair<String, Long> score2) -> {
                return Math.toIntExact(score2.val - score1.val);
            });

        for (Pair<String, Long> score : scores ) {
            toReturn.add(score.key + " : " + Long.toString(score.val) + " milliseconds.");
        }
        return toReturn;
    }
}
