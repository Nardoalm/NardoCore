package fusionmc.nardodev.utils.bungee;

import escolhendo.apexstore.services.utils.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import net.md_5.bungee.api.ProxyServer;
import fusionmc.nardodev.utils.bungee.plugin.config.FusionConfig;
import fusionmc.nardodev.utils.bungee.plugin.config.FusionWriter;
import fusionmc.nardodev.utils.bungee.plugin.logger.FusionLogger;

public class Settings {
   public static String maintenance$kick = "§c§lMANUTENÇÃO - REDE HYZEN\n \n§cNeste momento, nossa rede está\n§cem manutenção. Veja mais em\n§b§nredehyzen.com.br/discord§c.";
   public static List<String> blocked_commands$list = Arrays.asList("me", "pl");
   public static String blocked_commands$message = "§fComando desconhecido.";
   public static String blocked_commands$message_staff = "§e{player} tentou executar o comando §7(§f{command}§7)§e.";
   public static List<String> lobby$blacklist = Collections.singletonList("login");
   public static boolean lobby$command$enabled = false;
   public static final FusionLogger LOGGER = ((FusionLogger)Bungee.getInstance().getLogger()).getModule("LANGUAGE");
   private static final FusionConfig CONFIG = FusionConfig.getConfig("language");

   public static void setupSettings() {
      boolean save = false;
      FusionWriter writer = new FusionWriter(CONFIG.getFile(), "HyzenUtils - Maked by Escolhendo\nVersão da configuração: " + Bungee.getInstance().getDescription().getVersion());
      Field[] var2 = Settings.class.getDeclaredFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field field = var2[var4];
         if (field.getName().contains("$") && !Modifier.isFinal(field.getModifiers())) {
            String nativeName = field.getName().replace("$", ".").replace("_", "-");

            try {
               FusionWriter.YamlEntryInfo entryInfo = (FusionWriter.YamlEntryInfo)field.getAnnotation(FusionWriter.YamlEntryInfo.class);
               Object value;
               List l;
               ArrayList list;
               Iterator var11;
               Object v;
               if (CONFIG.contains(nativeName)) {
                  value = CONFIG.get(nativeName);
                  if (value instanceof String) {
                     value = StringUtils.formatColors((String)value).replace("\\n", "\n");
                  } else if (value instanceof List) {
                     l = (List)value;
                     list = new ArrayList(l.size());
                     var11 = l.iterator();

                     while(var11.hasNext()) {
                        v = var11.next();
                        if (v instanceof String) {
                           list.add(StringUtils.formatColors((String)v).replace("\\n", "\n"));
                        } else {
                           list.add(v);
                        }
                     }

                     value = list;
                  }

                  field.set((Object)null, value);
                  writer.set(nativeName, new FusionWriter.YamlEntry(new Object[]{entryInfo == null ? "" : entryInfo.annotation(), CONFIG.get(nativeName)}));
               } else {
                  value = field.get((Object)null);
                  if (value instanceof String) {
                     value = StringUtils.deformatColors((String)value).replace("\n", "\\n");
                  } else if (value instanceof List) {
                     l = (List)value;
                     list = new ArrayList(l.size());
                     var11 = l.iterator();

                     while(var11.hasNext()) {
                        v = var11.next();
                        if (v instanceof String) {
                           list.add(StringUtils.deformatColors((String)v).replace("\n", "\\n"));
                        } else {
                           list.add(v);
                        }
                     }

                     value = list;
                  }

                  save = true;
                  writer.set(nativeName, new FusionWriter.YamlEntry(new Object[]{entryInfo == null ? "" : entryInfo.annotation(), value}));
               }
            } catch (ReflectiveOperationException var13) {
               LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", var13);
            }
         }
      }

      if (save) {
         writer.write();
         CONFIG.reload();
         ProxyServer.getInstance().getScheduler().runAsync(Bungee.getInstance(), () -> {
            LOGGER.info("A config §6language.yml §afoi modificada ou criada.");
         });
      }

   }
}
