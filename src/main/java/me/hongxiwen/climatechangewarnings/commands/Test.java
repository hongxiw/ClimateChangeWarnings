package me.hongxiwen.climatechangewarnings.commands;

import me.hongxiwen.climatechangewarnings.ClimateChangeWarnings;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {

    private ClimateChangeWarnings plugin;

    public Test(ClimateChangeWarnings plugin) {
        this.plugin = plugin;
        plugin.getCommand("test").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            double yInc = player.getHeight() / 100;
            /*double radius = 1;
            double y = 0;
            for (int i = 1; i < 360; i++) {
                double zChange = radius * Math.sin(i * Math.PI / 90);
                double xChange = radius * Math.cos(i * Math.PI / 90);
                player.getWorld().spawnParticle(Particle.FLAME,
                        player.getLocation().getX() + xChange,
                        player.getLocation().getY() + y,
                        player.getLocation().getZ() + zChange,
                        0, 0, 0, 0);
                player.getWorld().spawnParticle(Particle.FLAME,
                        player.getLocation().getX() - xChange,
                        player.getLocation().getY() + y,
                        player.getLocation().getZ() - zChange,
                        0, 0, 0, 0);
                y += yInc;
            }*/
            BukkitRunnable spiral = new BukkitRunnable() {

                int degree = 0;
                double y = 0, radius = 1;

                @Override
                public void run() {
                    if(degree >= 360) {
                        this.cancel();
                        return;
                    }
                    double zChange = radius * Math.sin(degree * Math.PI / 90);
                    double xChange = radius * Math.cos(degree * Math.PI / 90);
                    player.getWorld().spawnParticle(Particle.FLAME,
                            player.getLocation().getX() + xChange,
                            player.getLocation().getY() + y,
                            player.getLocation().getZ() + zChange,
                            0, 0, 0, 0);
                    player.getWorld().spawnParticle(Particle.FLAME,
                            player.getLocation().getX() - xChange,
                            player.getLocation().getY() + y,
                            player.getLocation().getZ() - zChange,
                            0, 0, 0, 0);
                    degree += 3.6;
                    y += yInc;
                }
            };
            spiral.runTaskTimer(plugin, 0, 1);
            return true;
        }
        return false;
    }
}
