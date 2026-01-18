package me.nardodev.utils.cmd.utils;

import me.nardodev.utils.cmd.Commands;
import me.nardodev.utils.listeners.world.WorldProtectionListener;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvpCommand extends Commands {

    public PvpCommand() {
        super("pvp", "combate");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("nardocore.admin.pvp")) {
            sender.sendMessage("§c§lERRO ➔ §cVoce nao tem permissao para usar este comando.");
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
            }
            return;
        }

        if (args.length < 1) {
            sender.sendMessage("§c§lERRO ➔ §cUso correto: /pvp <on/off>");
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
            }
            return;
        }

        String action = args[0].toLowerCase();

        if (action.equals("on") || action.equals("true") || action.equals("ativar")) {
            WorldProtectionListener.setPvp(true);
            sender.sendMessage(ChatColor.GREEN + "PVP Global foi ATIVADO.");
            if (sender instanceof Player) {
                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        } else if (action.equals("off") || action.equals("false") || action.equals("desativar")) {
            WorldProtectionListener.setPvp(false);
            sender.sendMessage(ChatColor.RED + "PVP Global foi DESATIVADO.");
            if (sender instanceof Player) {
                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        } else {
            sender.sendMessage("§c§lERRO ➔ §cUso correto: /pvp <on/off>");
        }
    }
}