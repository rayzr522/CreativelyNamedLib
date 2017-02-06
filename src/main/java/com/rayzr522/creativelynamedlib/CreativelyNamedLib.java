package com.rayzr522.creativelynamedlib;

import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.creativelynamedlib.gui.GUIListener;

public class CreativelyNamedLib extends JavaPlugin {
    private static CreativelyNamedLib instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /*
     * @SuppressWarnings("deprecation")
     * 
     * @Override
     * public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
     * if (!(sender instanceof Player)) {
     * sender.sendMessage(RED + "These tests are player-only");
     * return true;
     * }
     * 
     * Player player = (Player) sender;
     * 
     * if (args.length < 1) {
     * player.sendMessage(RED + "Usage: " + GRAY + "/cnltest [testName]");
     * player.sendMessage(RED + "Available tests: " + GRAY + "gui, json, item, dye");
     * return true;
     * }
     * 
     * String sub = args[0].toLowerCase();
     * 
     * if (sub.equals("gui")) {
     * 
     * new GUI(5, "Hello &cWorld!")
     * .add(
     * Component.create().named(" ").ofSize(9, 5).withDurability(15).build(),
     * at(0, 0))
     * .add(
     * Component.create().named("&cClick to &kDIE").withLore("&7Clicking this is probably", "&7a bad idea!").type(Material.BANNER).colored(DyeColor.RED).ofSize(7, 3)
     * .onClick(e -> e.getPlayer().setHealth(0)).build(),
     * at(1, 1))
     * .open(player);
     * 
     * } else if (sub.equals("json")) {
     * 
     * JSONMessage.create("Hello, ")
     * .then("world").color(RED).style(BOLD).runCommand("/suicide").send(player);
     * 
     * JSONMessage.actionbar(String.format("Hey, &c%s&e, how are you?", player.getDisplayName()), player);
     * 
     * JSONMessage
     * .create("This").color(RED)
     * .then(" IS ").color(YELLOW)
     * .then("minecraft :D").style(BOLD).style(UNDERLINE)
     * .title(45, 60, 25, player);
     * 
     * JSONMessage
     * .create("Almost a ").color(GRAY)
     * .then("reference ;)").color(AQUA).style(ITALIC)
     * .subtitle(player);
     * 
     * } else if (sub.equals("item")) {
     * 
     * ItemStack item = player.getItemInHand();
     * 
     * player.sendMessage(AQUA + "Item type: " + BLUE + item.getType().name().toLowerCase().replace('_', ' '));
     * player.sendMessage(AQUA + "Amount: " + BLUE + item.getAmount());
     * player.sendMessage(AQUA + "Damage: " + BLUE + item.getDurability());
     * player.sendMessage(AQUA + "Item meta type: " + BLUE + item.getItemMeta().getClass().getSimpleName());
     * 
     * } else if (sub.equals("dye")) {
     * 
     * for (DyeColor color : DyeColor.values()) {
     * 
     * player.sendMessage(color.name() + ": " + color.getData());
     * 
     * }
     * 
     * }
     * 
     * return true;
     * }
     */

    public static CreativelyNamedLib getInstance() {
        return instance;
    }
}
