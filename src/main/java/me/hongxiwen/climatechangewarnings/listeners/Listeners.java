package me.hongxiwen.climatechangewarnings.listeners;

import me.hongxiwen.climatechangewarnings.ClimateChangeWarnings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Listeners implements Listener {

    private static final Material[] meat = new Material[]{
            Material.BEEF, Material.COOKED_BEEF,
            Material.CHICKEN, Material.COOKED_CHICKEN,
            Material.PORKCHOP, Material.COOKED_PORKCHOP,
            Material.MUTTON, Material.COOKED_MUTTON,
            Material.RABBIT, Material.COOKED_RABBIT, Material.RABBIT_STEW,
            Material.ROTTEN_FLESH, Material.SPIDER_EYE
    };

    private static final Material[] fish = new Material[]{
            Material.COD, Material.COOKED_COD,
            Material.SALMON, Material.COOKED_SALMON,
            Material.PUFFERFISH, Material.TROPICAL_FISH
    };

    private static final Material[] wood = new Material[]{
            Material.ACACIA_WOOD, Material.ACACIA_LOG,
            Material.BIRCH_WOOD, Material.BIRCH_LOG,
            Material.DARK_OAK_WOOD, Material.DARK_OAK_LOG,
            Material.JUNGLE_WOOD, Material.JUNGLE_LOG,
            Material.OAK_WOOD, Material.OAK_LOG,
            Material.SPRUCE_WOOD, Material.SPRUCE_LOG
    };

    private static final Material[] saplings = new Material[]{
            Material.ACACIA_SAPLING, Material.BIRCH_SAPLING, Material.OAK_SAPLING, Material.DARK_OAK_SAPLING, Material.JUNGLE_SAPLING, Material.SPRUCE_SAPLING
    };

    private static final Biome[] waterBiomes = new Biome[]{
            Biome.RIVER,
            Biome.OCEAN, Biome.DEEP_OCEAN,
            Biome.COLD_OCEAN, Biome.DEEP_COLD_OCEAN,
            Biome.FROZEN_OCEAN, Biome.DEEP_FROZEN_OCEAN,
            Biome.LUKEWARM_OCEAN, Biome.DEEP_LUKEWARM_OCEAN,
            Biome.WARM_OCEAN
    };

    private static final Biome[] warmBiomes = new Biome[] {
            Biome.SWAMP,
            Biome.JUNGLE, Biome.SPARSE_JUNGLE, Biome.BAMBOO_JUNGLE,
            Biome.BEACH, Biome.DESERT,
            Biome.SAVANNA, Biome.SAVANNA_PLATEAU, Biome.WINDSWEPT_SAVANNA,
            Biome.BADLANDS, Biome.ERODED_BADLANDS, Biome.WOODED_BADLANDS
    };

    private static final ArrayList<Material> meatList = new ArrayList<>(Arrays.asList(meat));
    private static final ArrayList<Material> fishList = new ArrayList<>(Arrays.asList(fish));
    private static final ArrayList<Material> woodList = new ArrayList<>(Arrays.asList(wood));
    private static final ArrayList<Biome> oceanBiomes = new ArrayList<>(Arrays.asList(waterBiomes));
    private static final ArrayList<Biome> warmBiomeList = new ArrayList<>(Arrays.asList(warmBiomes));

    private ArrayList<UUID> inHotClimate;
    private ArrayList<UUID> inOcean;
    private ArrayList<UUID> tryThrow;
    private ArrayList<UUID> tryBreakTree;
    private ArrayList<UUID> tryBreakCoal;
    private ArrayList<UUID> tryBreakCoral;

    public Listeners(ClimateChangeWarnings plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        inHotClimate = new ArrayList<>();
        inOcean = new ArrayList<>();
        tryThrow = new ArrayList<>();
        tryBreakTree = new ArrayList<>();
        tryBreakCoal = new ArrayList<>();
        tryBreakCoral = new ArrayList<>();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        TextComponent message = Component.text("Welcome! This server is running the ClimateChangeWarnings plugin that send some notifications when an action in Minecraft relates to climate change").color(NamedTextColor.YELLOW).hoverEvent(HoverEvent.showText(Component.text("Run /climateinfo for more information and links")));
        event.getPlayer().sendMessage(message);
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item.getType().isEdible()) {
            TextComponent message1, message2, message3, message4;
            if (meatList.contains(item.getType())) {
                message1 = Component.text("Did you know that meat takes much more energy and many more resources to produce than vegetables or fruits?").color(NamedTextColor.YELLOW);
                message2 = Component.text("Raising livestock or other animals for food requires a lot more space and cleared trees").color(NamedTextColor.YELLOW);
                message3 = Component.text("Additionally, livestock like cows emit a lot of greenhouse gasses as they're raised").color(NamedTextColor.YELLOW);
                message4 = Component.text("Click here for an interesting video about eating meat and climate change").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the video by Mark Rober"))).clickEvent(ClickEvent.openUrl("https://youtu.be/-k-V3ESHcfA"));
            } else if (fishList.contains(item.getType())) {
                message1 = Component.text("You're probably not mass or commercial fishing in Minecraft, but...").color(NamedTextColor.YELLOW);
                message2 = Component.text("Mass overfishing can actually contribute to climate change").color(NamedTextColor.YELLOW);
                message3 = Component.text("The oceans are a huge natural carbon sink, and having more diverse life in them helps with  the process of removing carbon").color(NamedTextColor.YELLOW);
                message4 = Component.text("Click here for an article about overfishing and climate change").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the article by Greenpeace"))).clickEvent(ClickEvent.openUrl("https://www.greenpeace.org/aotearoa/story/how-does-overfishing-make-climate-change-worse/"));
            } else {
                message1 = Component.text("I've detected that you aren't eating meat. Good job!").color(NamedTextColor.GREEN);
                message2 = Component.text("Vegetables take much fewer resources, less space, and less energy to produce than meat").color(NamedTextColor.GREEN);
                message3 = Component.text("Growing fruits and vegetables means the plants that grew them helped absorb some carbon dioxide").color(NamedTextColor.GREEN);
                message4 = Component.text("Click here for an interesting video about eating meat versus plants and climate change").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the video by Mark Rober"))).clickEvent(ClickEvent.openUrl("https://youtu.be/-k-V3ESHcfA"));
            }
            showClimateTitle(event.getPlayer());
            event.getPlayer().sendMessage(message1);
            event.getPlayer().sendMessage(message2);
            event.getPlayer().sendMessage(message3);
            event.getPlayer().sendMessage(message4);
        }
    }

    @EventHandler
    public void onAttackAnimal(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        TextComponent message1 = null, message2 = null, message3 = null, message4 = null;
        if (event.getEntityType() == EntityType.CHICKEN
                || event.getEntityType() == EntityType.COW
                || event.getEntityType() == EntityType.PIG
                || event.getEntityType() == EntityType.SHEEP) {
            message1 = Component.text("Did you know that meat takes much more energy and many more resources to produce than vegetables or fruits?").color(NamedTextColor.YELLOW);
            message2 = Component.text("Using livestock as food requires a lot more space and cleared trees").color(NamedTextColor.YELLOW);
            message3 = Component.text("Additionally, livestock like cows emit a lot of greenhouse gasses as they're raised").color(NamedTextColor.YELLOW);
            message4 = Component.text("Click here for an interesting video about eating meat and climate change").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the video by Mark Rober"))).clickEvent(ClickEvent.openUrl("https://youtu.be/-k-V3ESHcfA"));
        }
        if (event.getEntityType() == EntityType.COD
                || event.getEntityType() == EntityType.SALMON
                || event.getEntityType() == EntityType.PUFFERFISH
                || event.getEntityType() == EntityType.TROPICAL_FISH) {
            message1 = Component.text("You're probably not mass or commercial fishing in Minecraft, but...").color(NamedTextColor.YELLOW);
            message2 = Component.text("Mass overfishing can actually contribute to climate change").color(NamedTextColor.YELLOW);
            message3 = Component.text("The oceans are a huge natural carbon sink, and having more diverse life in them helps with  the process of removing carbon").color(NamedTextColor.YELLOW);
            message4 = Component.text("Click here for an article about overfishing and climate change").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the article by Greenpeace"))).clickEvent(ClickEvent.openUrl("https://www.greenpeace.org/aotearoa/story/how-does-overfishing-make-climate-change-worse/"));
        }
        Player player = (Player) event.getDamager();
        if(message1 == null) {
            return;
        }
        showClimateTitle(player);
        player.sendMessage(message1);
        player.sendMessage(message2);
        player.sendMessage(message3);
        player.sendMessage(message4);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if(tryThrow.contains(event.getPlayer().getUniqueId())) {
            tryThrow.remove(event.getPlayer().getUniqueId());
            return;
        }
        event.setCancelled(true);
        tryThrow.add(event.getPlayer().getUniqueId());
        TextComponent message1, message2, message3, message4;
        message1 = Component.text("You should always consider reusing or recycling items before you throw them away and think about where trash goes").color(NamedTextColor.YELLOW);
        message2 = Component.text("It's left to decompose in landfills, which releases emissions as processed materials like plastic gradually break down").color(NamedTextColor.YELLOW);
        message3 = Component.text("If you want to drop this item, drop it again").color(NamedTextColor.AQUA);
        message4 = Component.text("Click here for an article about littering and climate change").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the article by Thinking Sustainably"))).clickEvent(ClickEvent.openUrl("https://www.thinkingsustainably.com/littering-affect-climate-change/"));
        showClimateTitle(event.getPlayer());
        event.getPlayer().sendMessage(message1);
        event.getPlayer().sendMessage(message2);
        event.getPlayer().sendMessage(message3);
        event.getPlayer().sendMessage(message4);
    }

    @EventHandler
    public void enterDesert(PlayerMoveEvent event) {
        if (!(warmBiomeList.contains(event.getTo().getBlock().getBiome()))) {
            inHotClimate.remove(event.getPlayer().getUniqueId());
        } else {
            if (!(inHotClimate.contains(event.getPlayer().getUniqueId()))) {
                inHotClimate.add(event.getPlayer().getUniqueId());
                TextComponent message1, message2, message3, message4;
                message1 = Component.text("You're venturing into a location with a hotter climate").color(NamedTextColor.YELLOW);
                message2 = Component.text("You can't feel it in Minecraft, but global warming has increased the frequency of severe heat waves internationally").color(NamedTextColor.YELLOW);
                message3 = Component.text("Unfortunately, less-developed parts of the world will experience this unequally and suffer from the disastrous effects first").color(NamedTextColor.YELLOW);
                message4 = Component.text("Click here for an article about how global warming affects parts of the world unequally").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the article by the United Nations"))).clickEvent(ClickEvent.openUrl("https://www.un.org/press/en/2019/gaef3516.doc.htm"));
                showClimateTitle(event.getPlayer());
                event.getPlayer().sendMessage(message1);
                event.getPlayer().sendMessage(message2);
                event.getPlayer().sendMessage(message3);
                event.getPlayer().sendMessage(message4);
            }
        }
    }

    @EventHandler
    public void enterOcean(PlayerMoveEvent event) {
        if (!(oceanBiomes.contains(event.getTo().getBlock().getBiome()))) {
            inOcean.remove(event.getPlayer().getUniqueId());
        } else {
            if (!(inOcean.contains(event.getPlayer().getUniqueId()))) {
                inOcean.add(event.getPlayer().getUniqueId());
                TextComponent message1, message2, message3, message4;
                message1 = Component.text("There are many climate change issues related to water and the ocean").color(NamedTextColor.YELLOW);
                message2 = Component.text("The ocean is a huge natural carbon sink and provides us with oxygen, but climate change is harming it").color(NamedTextColor.YELLOW);
                message3 = Component.text("Ocean warming and ocean acidification are two major issues").color(NamedTextColor.YELLOW);
                message4 = Component.text("Click here for a TED Talk video about littering and climate change").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the TED Talk video by Triona McGrath"))).clickEvent(ClickEvent.openUrl("https://youtu.be/8m1X26Auw6Q"));
                showClimateTitle(event.getPlayer());
                event.getPlayer().sendMessage(message1);
                event.getPlayer().sendMessage(message2);
                event.getPlayer().sendMessage(message3);
                event.getPlayer().sendMessage(message4);
            }
        }
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent event) {
        TextComponent message1 = null, message2 = null, message3 = null, message4 = null;
        Player player = event.getPlayer();
        if(woodList.contains(event.getBlock().getType())) {
            if(tryBreakTree.contains(player.getUniqueId())) {
                tryBreakTree.remove(player.getUniqueId());
                player.getInventory().addItem(new ItemStack(saplings[(int) (Math.random() * saplings.length)], 1));
                player.sendMessage(Component.text("Take a sapling and plant it. Regrow trees that are cut down so we don't lose our forests", NamedTextColor.GREEN));
                return;
            }
            event.setCancelled(true);
            tryBreakTree.add(event.getPlayer().getUniqueId());
            message1 = Component.text("Deforestation removes trees, which naturally suck carbon dioxide out of the air and produce oxygen").color(NamedTextColor.YELLOW);
            message2 = Component.text("Eventually, even huge systems of trees like the Amazon Rainforest can not regenerate, and the world will lose a major carbon sink").color(NamedTextColor.YELLOW);
            message3 = Component.text("If you want to break this tree log, break it again").color(NamedTextColor.AQUA);
            message4 = Component.text("Click here for a video about the #TeamTrees movement from 2019 to plant 20 million trees").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the video by MrBeast"))).clickEvent(ClickEvent.openUrl("https://youtu.be/HPJKxAhLw5I"));
        }
        if(event.getBlock().getType() == Material.COAL_ORE || event.getBlock().getType() == Material.COAL_BLOCK || event.getBlock().getType() == Material.DEEPSLATE_COAL_ORE) {
            if(tryBreakCoal.contains(player.getUniqueId())) {
                tryBreakCoal.remove(player.getUniqueId());
                return;
            }
            event.setCancelled(true);
            tryBreakCoal.add(event.getPlayer().getUniqueId());
            message1 = Component.text("Corporations that extracting coal and other fossil fuels are the largest contributor to global warming in human history").color(NamedTextColor.YELLOW);
            message2 = Component.text("A huge portion of the world's electricity comes from burning coal, and renewable, clean alternatives must replace fossil fuels immediately").color(NamedTextColor.YELLOW);
            message3 = Component.text("If you want to break this block, break it again").color(NamedTextColor.AQUA);
            message4 = Component.text("Click here for an article about the effects of coal mining").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the article by United States Energy Information Administration"))).clickEvent(ClickEvent.openUrl("https://www.eia.gov/energyexplained/coal/coal-and-the-environment.php"));
        }
        if(event.getBlock().getType().name().toLowerCase().contains("coral") || event.getBlock().getType() == Material.SEA_PICKLE) {
            if(tryBreakCoral.contains(player.getUniqueId())) {
                tryBreakCoral.remove(player.getUniqueId());
                return;
            }
            event.setCancelled(true);
            tryBreakCoral.add(event.getPlayer().getUniqueId());
            message1 = Component.text("Coral, like trees, are a major natural carbon sink and actually provide us with oxygen").color(NamedTextColor.YELLOW);
            message2 = Component.text("The rising water temperatures and ocean acidification cause coral bleaching, and coral takes thousands to millions of years to grow").color(NamedTextColor.YELLOW);
            message3 = Component.text("If you want to break this block, break it again").color(NamedTextColor.AQUA);
            message4 = Component.text("Click here for an video about how climate change is destroying coral in a positive feedback loop").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the video by National Geographic"))).clickEvent(ClickEvent.openUrl("https://youtu.be/mQ10xBl8XMQ"));
        }
        if(message1 == null) {
            return;
        }
        showClimateTitle(player);
        player.sendMessage(message1);
        player.sendMessage(message2);
        player.sendMessage(message3);
        player.sendMessage(message4);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if(!(event.getState() == PlayerFishEvent.State.FISHING)) {
            return;
        }
        Component message1, message2, message3, message4;
        message1 = Component.text("You're probably not mass or commercial fishing in Minecraft, but...").color(NamedTextColor.YELLOW);
        message2 = Component.text("Mass overfishing can actually contribute to climate change").color(NamedTextColor.YELLOW);
        message3 = Component.text("The oceans are a huge natural carbon sink, and having more diverse life in them helps with  the process of removing carbon").color(NamedTextColor.YELLOW);
        message4 = Component.text("Click here for an article about overfishing and climate change").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the article by Greenpeace"))).clickEvent(ClickEvent.openUrl("https://www.greenpeace.org/aotearoa/story/how-does-overfishing-make-climate-change-worse/"));
        showClimateTitle(event.getPlayer());
        event.getPlayer().sendMessage(message1);
        event.getPlayer().sendMessage(message2);
        event.getPlayer().sendMessage(message3);
        event.getPlayer().sendMessage(message4);
    }

    @EventHandler
    public void onFuelFurnace(InventoryClickEvent event) {
        if(event.getInventory().getType() != InventoryType.FURNACE || event.getInventory().getType() != InventoryType.BLAST_FURNACE) {
            return;
        }
        if(event.getCurrentItem().getType() != Material.COAL || event.getCurrentItem().getType() != Material.CHARCOAL || event.getCurrentItem().getType() != Material.COAL_BLOCK) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Component message1, message2, message3, message4;
        message1 = Component.text("Corporations that extracting coal and other fossil fuels are the largest contributor to global warming in human history").color(NamedTextColor.YELLOW);
        message2 = Component.text("A huge portion of the world's electricity comes from burning coal, and renewable, clean alternatives must replace fossil fuels immediately").color(NamedTextColor.YELLOW);
        message3 = Component.text("If you want to break this block, break it again").color(NamedTextColor.AQUA);
        message4 = Component.text("Click here for an article about the effects of coal mining").color(NamedTextColor.GREEN).hoverEvent(HoverEvent.showText(Component.text("Click to see the article by United States Energy Information Administration"))).clickEvent(ClickEvent.openUrl("https://www.eia.gov/energyexplained/coal/coal-and-the-environment.php"));
        showClimateTitle(player);
        player.sendMessage(message1);
        player.sendMessage(message2);
        player.sendMessage(message3);
        player.sendMessage(message4);
    }

    private void showClimateTitle(Player player) {
        Component mainTitle = Component.text("Climate Change Connection!").color(NamedTextColor.BLUE);
        Title title = Title.title(mainTitle, Component.text("Read your chat messages to see information about a climate change concern", NamedTextColor.YELLOW));
        player.showTitle(title);
    }

}