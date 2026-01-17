package fusionmc.nardodev.utils.cmd.utils;

import fusionmc.nardodev.utils.cmd.Commands;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CbanCommand extends Commands {
    public CbanCommand(){
        super("cban", "cb");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if(args.length < 1){
            sender.sendMessage(ChatColor.RED + "Uso correto: /cban <jogador>");
            return;
        }

        if (!sender.hasPermission("nardocore.staff.banir")){
            sender.sendMessage(ChatColor.RED + "Você não tem permissão para usar esse comando.");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline()){
            sender.sendMessage(ChatColor.RED + "Jogador não existe ou não está online.");
            return;
        }

        if (target.getName().equals(sender.getName())){
            sender.sendMessage(ChatColor.RED + "Você não pode se banir!");
            return;
        }

        String reason = "Uso de trapaças";

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date expires = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String kickMessage =
                ChatColor.RED + "Você foi banido temporariamente.\n" +
                        ChatColor.RED +"Motivo: "+ ChatColor.GRAY  + reason + "\n\n" +
                        ChatColor.RED + "\n\n" +
                        ChatColor.RED + "Expira em: " + ChatColor.GRAY + sdf.format(expires) + "\n\n" +
                        ChatColor.YELLOW + "Adquira seu unban em: " + ChatColor.AQUA + "www.fusionmc.com.br\n" +
                        ChatColor.RED + "Banido injustamente? Contate-nos via\n" +
                        ChatColor.BLUE + "discord.gg/fusionmc";

        Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, expires, sender.getName());
        sender.sendMessage(ChatColor.GREEN + "Jogador ( " + target.getName() + " ) banido por 30 dias com sucesso! Vai xitar no inferno agora!");

        target.kickPlayer(kickMessage);
    }
}
