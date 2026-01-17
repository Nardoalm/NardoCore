package fusionmc.nardodev.utils.bungee.plugin.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import fusionmc.nardodev.utils.bungee.Bungee;
import fusionmc.nardodev.utils.bungee.plugin.config.interfaces.BungeeYamlConfiguration;
import fusionmc.nardodev.utils.bungee.plugin.config.interfaces.YamlConfigurationn;
import fusionmc.nardodev.utils.bungee.plugin.logger.FusionLogger;

public class FusionConfig {
   private File file;
   private YamlConfigurationn config;
   public static final FusionLogger LOGGER = ((FusionLogger)Bungee.getInstance().getLogger()).getModule("CONFIG");
   private static Map<String, FusionConfig> cache = new HashMap();

   private FusionConfig(String path, String name) {
      this.file = new File(path + "/" + name + ".yml");
      if (!this.file.exists()) {
         this.file.getParentFile().mkdirs();
         InputStream in = Bungee.getInstance().getResourceAsStream(name + ".yml");
         if (in != null) {
            FileUtils.copyFile(in, this.file);
         } else {
            try {
               this.file.createNewFile();
            } catch (IOException var6) {
               LOGGER.log(Level.SEVERE, "Unexpected error ocurred creating file " + this.file.getName() + ": ", var6);
            }
         }
      }

      try {
         this.config = new BungeeYamlConfiguration(this.file);
      } catch (IOException var5) {
         LOGGER.log(Level.SEVERE, "Unexpected error ocurred creating config " + this.file.getName() + ": ", var5);
      }

   }

   public boolean createSection(String path) {
      return this.config.createSection(path);
   }

   public boolean set(String path, Object obj) {
      return this.config.set(path, obj);
   }

   public boolean contains(String path) {
      return this.config.contains(path);
   }

   public Object get(String path) {
      return this.config.get(path);
   }

   public int getInt(String path) {
      return this.config.getInt(path);
   }

   public int getInt(String path, int def) {
      return this.config.getInt(path, def);
   }

   public double getDouble(String path) {
      return this.config.getDouble(path);
   }

   public double getDouble(String path, double def) {
      return this.config.getDouble(path, def);
   }

   public String getString(String path) {
      return this.config.getString(path);
   }

   public boolean getBoolean(String path) {
      return this.config.getBoolean(path);
   }

   public List<String> getStringList(String path) {
      return this.config.getStringList(path);
   }

   public Set<String> getKeys(boolean flag) {
      return this.config.getKeys(flag);
   }

   public boolean reload() {
      return this.config.reload();
   }

   public boolean save() {
      return this.config.save();
   }

   public File getFile() {
      return this.file;
   }

   public static FusionConfig getConfig(String name) {
      return getConfig(name, "plugins/lUtils");
   }

   public static FusionConfig getConfig(String name, String path) {
      if (!cache.containsKey(path + "/" + name)) {
         cache.put(path + "/" + name, new FusionConfig(path, name));
      }

      return (FusionConfig)cache.get(path + "/" + name);
   }
}
