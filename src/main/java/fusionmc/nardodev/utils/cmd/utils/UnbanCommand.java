package fusionmc.nardodev.utils.cmd.utils;

import fusionmc.nardodev.utils.cmd.Commands;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class UnbanCommand extends Commands {
    public UnbanCommand(){
        super("unban", "unb", "desbanir", "removerban");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if(args.length < 1){
            sender.sendMessage(ChatColor.RED + "Uso correto: /unban <jogador>");
            return;
        }

        if (!sender.hasPermission("nardocore.staff.banir")){
            sender.sendMessage(ChatColor.RED + "Você não tem permissão para usar esse comando.");
            return;
        }

        String targetName = args[0];
        BanList banList = Bukkit.getBanList(BanList.Type.NAME);
        BanEntry ban = banList.getBanEntry(targetName);

        if (ban == null) {
            sender.sendMessage(ChatColor.RED + "Esse jogador não está banido.");
            return;
        }

        banList.pardon(targetName);
        sender.sendMessage(ChatColor.GREEN + "O jogador " + targetName + " foi desbanido com sucesso!");

    }
}
