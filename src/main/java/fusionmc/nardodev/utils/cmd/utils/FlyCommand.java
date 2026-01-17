package fusionmc.nardodev.utils.cmd.utils;

import fusionmc.nardodev.utils.cmd.Commands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand extends Commands {

    public FlyCommand() {
        super("fly", "voar");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        Player target;

        if (args.length > 0) {
            if (!sender.hasPermission("nardocore.admin.fly.others")) { // Sugestão: Permissão diferente para outros
                sender.sendMessage(ChatColor.RED + "Você não tem permissão para dar fly em outros jogadores.");
                return;
            }

            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
                return;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Apenas jogadores podem usar este comando em si mesmos.");
                return;
            }
            target = (Player) sender;

            if (!target.hasPermission("nardocore.member.fly")) {
                sender.sendMessage(ChatColor.RED + "Você não possui permissão para executar este comando.");
                return;
            }
        }


        boolean newStatus = !target.getAllowFlight();

        target.setAllowFlight(newStatus);

        if (!newStatus) {
            target.setFlying(false);
        }

        String status = newStatus ? ChatColor.GREEN + "ativado" : ChatColor.GREEN + "desativado";

        target.sendMessage(ChatColor.GREEN + "Fly está " + status + ".");

        if (sender != target) {
            sender.sendMessage(ChatColor.GREEN + "Fly está " + target.getName() + " " + status + ".");
        }
    }
}