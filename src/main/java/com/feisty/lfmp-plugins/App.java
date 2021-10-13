package com.feisty.lfmp;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin
{
    @Override
    public void onEnable() {
        System.out.println("Enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println("Disabled!");
    }

}
