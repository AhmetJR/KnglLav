        package org.ahmetjr.kngllav.Files;
 
 import java.io.File;
 import java.io.IOException;
 import org.bukkit.Bukkit;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.configuration.file.YamlConfiguration;
 import org.ahmetjr.kngllav.Main;
 
 public class Config
 {
   private static File file;
   private static FileConfiguration customFile;
   private static Main plugin = (Main) Main.getPlugin(Main.class);
   
   public static void setup() {
     if (!plugin.getDataFolder().exists()) {
       plugin.getDataFolder().mkdir();
     }
     file = new File(plugin.getDataFolder(), "Config.yml");
    if (!file.exists()) {
       try {
        file.createNewFile();
         Bukkit.getConsoleSender().sendMessage("§aCreated Config File.");
       } catch (IOException e) {
         e.printStackTrace();
       } 
     }
     customFile = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
   }
   
   public static FileConfiguration get() {
     return customFile;
   }
   
   public static void save() {
     try {
       customFile.save(file);
    } catch (IOException e) {
       e.printStackTrace();
     } 
   }
   
   public static void reload() {
     customFile = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
   }
 }

