package com.rayzr522.creativelynamedlib;

import static org.bukkit.ChatColor.*;
import static com.rayzr522.creativelynamedlib.utils.types.Point.at;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.creativelynamedlib.gui.Component;
import com.rayzr522.creativelynamedlib.gui.GUI;
import com.rayzr522.creativelynamedlib.gui.GUIListener;
import com.rayzr522.creativelynamedlib.utils.ItemFactory;
import com.rayzr522.creativelynamedlib.utils.text.JSONMessage;

public class CreativelyNamedLib extends JavaPlugin {
    private static CreativelyNamedLib instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new GUIListener(this), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(RED + "These tests are player-only");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(RED + "Usage: " + GRAY + "/cnltest [testName]");
            player.sendMessage(RED + "Available tests: " + GRAY + "gui, json, item, dye");
            return true;
        }

        String sub = args[0].toLowerCase();

        if (sub.equals("gui")) {

            new GUI(5, "Hello &cWorld!")
                    .add(
                            Component.create().named(" ").ofSize(9, 5).withDurability(15).build(),
                            at(0, 0))
                    .add(
                            Component.create().named("&cClick to &kDIE").withLore("&7Clicking this is probably", "&7a bad idea!").type(Material.BANNER).colored(DyeColor.RED).ofSize(7, 3)
                                    .onClick(e -> e.getPlayer().setHealth(0)).build(),
                            at(1, 1))
                    .open(player);

        } else if (sub.equals("json")) {

            JSONMessage.create("Hello, ")
                    .then("world").color(RED).style(BOLD).runCommand("/suicide").send(player);

            JSONMessage.actionbar(String.format("Hey, &c%s&e, how are you?", player.getDisplayName()), player);

            JSONMessage
                    .create("This").color(RED)
                    .then(" IS ").color(YELLOW)
                    .then("minecraft :D").style(BOLD).style(UNDERLINE)
                    .title(45, 60, 25, player);

            JSONMessage
                    .create("Almost a ").color(GRAY)
                    .then("reference ;)").color(AQUA).style(ITALIC)
                    .subtitle(player);

        } else if (sub.equals("item")) {

            ItemStack item = player.getItemInHand();

            player.sendMessage(AQUA + "Item type: " + BLUE + item.getType().name().toLowerCase().replace('_', ' '));
            player.sendMessage(AQUA + "Amount: " + BLUE + item.getAmount());
            player.sendMessage(AQUA + "Damage: " + BLUE + item.getDurability());
            player.sendMessage(AQUA + "Item meta type: " + BLUE + item.getItemMeta().getClass().getSimpleName());

        } else if (sub.equals("dye")) {

            GUI gui = new GUI(6, "&4All &8Colors");

            int i = 0;
            for (DyeColor color : DyeColor.values()) {
                gui.add(Component.create().colored(color).named(color.name()).build(), at(i % 9, i / 9));
                i++;
                gui.add(Component.create().type(Material.WOOL).colored(color).named(color.name()).build(), at(i % 9, i / 9));
                i++;
                gui.add(Component.create().type(Material.INK_SACK).colored(color).named(color.name()).build(), at(i % 9, i / 9));
                i++;
                player.sendMessage(color.name() + ": " + color.getData());
            }

            gui.open(player);

        } else if (sub.equals("enchant")) {

            player.getInventory().addItem(
                    ItemFactory.of(Material.DIAMOND_SWORD)
                            .addEnchant(Enchantment.DURABILITY, 5)
                            .clearEnchants()
                            .addEnchant(Enchantment.DURABILITY, 10)
                            .clearEnchants()
                            .addEnchant(Enchantment.DAMAGE_ALL, 10).build());

        }

        return true;
    }

    public static CreativelyNamedLib getInstance() {
        return instance;
    }
}
