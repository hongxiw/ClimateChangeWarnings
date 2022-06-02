package me.hongxiwen.climatechangewarnings.commands;

import me.hongxiwen.climatechangewarnings.ClimateChangeWarnings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClimateInfo implements CommandExecutor {

    public ClimateInfo(ClimateChangeWarnings plugin) {
        plugin.getCommand("climateinfo").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Component info = Component.text("Here are some useful links and all of the videos and articles referenced as you find stuff: ", NamedTextColor.AQUA);
            Component github = Component.text("- GitHub Page", NamedTextColor.AQUA).hoverEvent(HoverEvent.showText(Component.text("Click to visit the GitHub page"))).clickEvent(ClickEvent.openUrl("https://github.com/hongxiw/ClimateChangeWarnings"));
            Component meat = Component.text("- Eating Meat or Plants and Climate Change (Mark Rober)", NamedTextColor.YELLOW).hoverEvent(HoverEvent.showText(Component.text("Click to see the video by Mark Rober"))).clickEvent(ClickEvent.openUrl("https://youtu.be/-k-V3ESHcfA"));
            Component overfish = Component.text("- Overfishing on Climate Change (Greenpeace)", NamedTextColor.YELLOW).hoverEvent(HoverEvent.showText(Component.text("Click to see the article"))).clickEvent(ClickEvent.openUrl("https://www.greenpeace.org/aotearoa/story/how-does-overfishing-make-climate-change-worse/"));
            Component litter = Component.text("- Littering and Recycling on Climate Change (Thinking Sustainably)", NamedTextColor.YELLOW).hoverEvent(HoverEvent.showText(Component.text("Click to see the article"))).clickEvent(ClickEvent.openUrl("https://www.thinkingsustainably.com/littering-affect-climate-change/"));
            Component unequal = Component.text("- Developing Parts of the World Experience Global Warming Unequally (United Nations)", NamedTextColor.RED).hoverEvent(HoverEvent.showText(Component.text("Click to see the article"))).clickEvent(ClickEvent.openUrl("https://www.un.org/press/en/2019/gaef3516.doc.htm"));
            Component warmOcean = Component.text("- Ocean Warming and Acidification (Triona McGrath)", NamedTextColor.RED).hoverEvent(HoverEvent.showText(Component.text("Click to see the TED Talk video by Triona McGrath"))).clickEvent(ClickEvent.openUrl("https://youtu.be/8m1X26Auw6Q"));
            Component trees = Component.text("- Planting Trees to Combat Global Warming (MrBeast)", NamedTextColor.YELLOW).hoverEvent(HoverEvent.showText(Component.text("Click to see the video by MrBeast"))).clickEvent(ClickEvent.openUrl("https://youtu.be/HPJKxAhLw5I"));
            Component coal = Component.text("- Coal and Fossil Fuels on Climate Change (United States Energy Information Administration)", NamedTextColor.RED).hoverEvent(HoverEvent.showText(Component.text("Click to see the article by the EIA"))).clickEvent(ClickEvent.openUrl("https://www.eia.gov/energyexplained/coal/coal-and-the-environment.php"));
            Component coral = Component.text("- Coral Bleaching from Climate Change (National Geographic", NamedTextColor.RED).hoverEvent(HoverEvent.showText(Component.text("Click to see the video by National Geographic"))).clickEvent(ClickEvent.openUrl("https://youtu.be/mQ10xBl8XMQ"));
            player.sendMessage(info);
            player.sendMessage(github);
            player.sendMessage(meat);
            player.sendMessage(overfish);
            player.sendMessage(litter);
            player.sendMessage(unequal);
            player.sendMessage(warmOcean);
            player.sendMessage(trees);
            player.sendMessage(coal);
            player.sendMessage(coral);
        }
        return false;
    }
}
