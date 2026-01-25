package me.nardodev.utils.mikoImports.welcomeMessages;

import me.nardodev.utils.mikoImports.utils.FusionConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class WMConfig {
    private static FusionConfig config;

    // Lista contendo todas as mensagens carregadas na memória
    private static final List<WelcomeData> MESSAGES_LIST = new ArrayList<>();

    // Classe auxiliar para armazenar texto e permissão
    public static class WelcomeData {
        String text;
        String permission;

        public WelcomeData(String text, String permission) {
            this.text = text;
            this.permission = permission;
        }
    }

    // Array com seus defaults para gerar o arquivo na primeira vez
    private static final WelcomeData[] DEFAULTS = {
            new WelcomeData("{player} §6entrou no lobby!", ""),
            new WelcomeData("{player} §6invadiu o lobby!", "welcome.invasor"),
            new WelcomeData("{player} §6tá na área!", "welcome.area"),
            new WelcomeData("{player} §6saiu da ventilação!", "welcome.amongus"),
            new WelcomeData("{player} §6spawnou no bloco de grama!", "welcome.minecraft"),
            new WelcomeData("{player} §6pousou no ônibus de batalha!", "welcome.fortnite"),
            new WelcomeData("{player} §6pulou do cano do lobby!", "welcome.mario"),
            new WelcomeData("{player} §6foi teleportado pelo Prof. Carvalho!", "welcome.pokemon"),
            new WelcomeData("{player} §6wakka wakka chegou!", "welcome.pacman"),
            new WelcomeData("{player} §6ouviu a ocarina e apareceu!", "welcome.zelda"),
            new WelcomeData("{player} §6KO! Round 1!", "welcome.streetfighter"),
            new WelcomeData("{player} §6veio farmar no lobby!", "welcome.stardewvalley"),
            new WelcomeData("{player} §6foi perseguido até o lobby!", "welcome.tomjerry"),
            new WelcomeData("{player} §6está pronto, capitão!", "welcome.bobesponja"),
            new WelcomeData("{player} §6ha-ha-ha-ha apareceu!", "welcome.picapau"),
            new WelcomeData("{player} §6fugiu do fantasma!", "welcome.scooby"),
            new WelcomeData("{player} §6D'oh! Chegou atrasado!", "welcome.simpsons"),
            new WelcomeData("{player} §6voltou para o quintal!", "welcome.coragem"),
            new WelcomeData("{player} §6chegou voando na nuvem voadora!", "welcome.dragonball"),
            new WelcomeData("{player} §6dattebayo no lobby!", "welcome.naruto"),
            new WelcomeData("{player} §6explodiu o cosmo aqui!", "welcome.cavaleiros"),
            new WelcomeData("{player} §6encontrou o tesouro: este lobby!", "welcome.onepiece"),
            new WelcomeData("{player} §6fez o salto hiperespacial!", "welcome.starwars"),
            new WelcomeData("{player} §6engoliu a pedra filosofal!", "welcome.harrypotter"),
            new WelcomeData("{player} §6saiu do Mundo Invertido!", "welcome.stranger"),
            new WelcomeData("{player} §6swingou até o lobby!", "welcome.homemaranha"),
            new WelcomeData("{player} §6chegou na Batcaverna!", "welcome.batman"),
            new WelcomeData("{player} §6Problem? (acabou de chegar)", "welcome.trollface"),
            new WelcomeData("{player} §6wow. such lobby. very join.", "welcome.doge"),
            new WelcomeData("{player} §6não queria estar aqui mesmo...", "welcome.grumpycat"),
            new WelcomeData("{player} §6saiu do clipe do Michael Jackson!", "welcome.thriller"),
            new WelcomeData("{player} §6GOOOOL! Entrou no lobby!", "welcome.futebol"),
            new WelcomeData("{player} §6escapou das correntes e apareceu!", "welcome.houdini"),
            new WelcomeData("{player} §6chegou montado em seu cavalo!", "welcome.cavaleiro"),
            new WelcomeData("{player} §6chegou em 2ª marcha!", "welcome.velozes")
    };

    public static void init(JavaPlugin plugin) {
        config = new FusionConfig(plugin, plugin.getDataFolder().getPath(), "welcomeMessages");
        loadMessages();
    }

    public static void loadMessages() {
        MESSAGES_LIST.clear();

        // Se o arquivo estiver vazio, cria os defaults
        if (!config.contains("messages")) {
            int id = 1;
            for (WelcomeData def : DEFAULTS) {
                String path = "messages.msg" + id;
                config.set(path + ".text", def.text);
                config.set(path + ".permission", def.permission);
                id++;
            }
            config.save();
        }

        // Carrega do arquivo para a lista na memória
        ConfigurationSection section = config.getSection("messages");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                String text = section.getString(key + ".text");
                String perm = section.getString(key + ".permission", "");

                if (text != null) {
                    MESSAGES_LIST.add(new WelcomeData(text, perm));
                }
            }
        }
    }

    /**
     * Pega uma mensagem aleatória para o jogador.
     * Retorna NULL se o jogador não tiver permissão para NENHUMA das mensagens disponíveis.
     */
    public static String getRandomMessage(Player player) {
        if (MESSAGES_LIST.isEmpty()) return null;

        // Cria uma cópia e embaralha para garantir aleatoriedade total
        List<WelcomeData> randomList = new ArrayList<>(MESSAGES_LIST);
        Collections.shuffle(randomList);

        // Percorre a lista embaralhada
        for (WelcomeData data : randomList) {
            // Se não tiver permissão definida OU se o player tiver a permissão
            if (data.permission.isEmpty() || player.hasPermission(data.permission)) {
                return data.text.replace("{player}", player.getName());
            }
            // Se não tiver permissão, o loop continua para a próxima mensagem da lista
        }

        // Se chegou aqui, não encontrou nenhuma mensagem permitida
        return null;
    }
}