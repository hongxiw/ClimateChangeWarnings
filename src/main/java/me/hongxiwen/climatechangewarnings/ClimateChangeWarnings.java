package me.hongxiwen.climatechangewarnings;

import me.hongxiwen.climatechangewarnings.commands.ClimateInfo;
import me.hongxiwen.climatechangewarnings.commands.Test;
import org.bukkit.plugin.java.JavaPlugin;

import me.hongxiwen.climatechangewarnings.listeners.*;

public final class ClimateChangeWarnings extends JavaPlugin {

    @Override
    public void onEnable() {
        new Listeners(this);
        new ClimateInfo(this);
        new Test(this);
    }

    @Override
    public void onDisable() {

    }
}