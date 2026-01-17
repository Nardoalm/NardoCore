package fusionmc.nardodev.utils.bungee.plugin.config.interfaces;

import java.io.File;
import java.util.List;
import java.util.Set;

public abstract class YamlConfigurationn {
   protected File file;

   public YamlConfigurationn(File file) {
      this.file = file;
   }

   public abstract boolean createSection(String var1);

   public abstract boolean set(String var1, Object var2);

   public abstract boolean contains(String var1);

   public abstract Object get(String var1);

   public abstract int getInt(String var1);

   public abstract int getInt(String var1, int var2);

   public abstract double getDouble(String var1);

   public abstract double getDouble(String var1, double var2);

   public abstract String getString(String var1);

   public abstract boolean getBoolean(String var1);

   public abstract List<String> getStringList(String var1);

   public abstract Set<String> getKeys(boolean var1);

   public abstract boolean reload();

   public abstract boolean save();
}
