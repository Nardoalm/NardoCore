package me.nardodev.utils.mikoImports.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class FusionConfig {

    private static final Map<String, FusionConfig> cache = new HashMap<>();
    private final JavaPlugin plugin;
    private final File file;
    private YamlConfiguration config;

    public FusionConfig(JavaPlugin plugin, String path, String name) {
        this.plugin = plugin;
        this.file = new File(path, name + ".yml");

        if (!file.exists()) {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            try (InputStream in = plugin.getResource(name + ".yml")) {
                if (in != null) {
                    Files.copy(in, file.toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Erro ao criar config: " + file.getName(), ex);
            }
        }
        reload();
    }

    public static FusionConfig getConfig(JavaPlugin plugin, String path, String name) {
        return cache.computeIfAbsent(path + File.separator + name, k -> new FusionConfig(plugin, path, name));
    }

    public boolean set(String path, Object obj) {
        this.config.set(path, obj);
        return save();
    }

    public boolean contains(String path) {
        return this.config.contains(path);
    }

    public Object get(String path) {
        return this.config.get(path);
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public ConfigurationSection getSection(String path) {
        return this.config.getConfigurationSection(path);
    }

    public void reload() {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            this.config = YamlConfiguration.loadConfiguration(reader);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Erro ao recarregar config: " + file.getName(), ex);
            this.config = new YamlConfiguration();
        }
    }

    public boolean save() {
        try {
            this.config.save(this.file);
            return true;
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Erro ao salvar config: " + file.getName(), ex);
            return false;
        }
    }
}