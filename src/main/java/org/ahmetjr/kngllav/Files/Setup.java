 package org.ahmetjr.kngllav.Files;
 
 import java.io.File;
 import org.ahmetjr.kngllav.Main;
        import org.bukkit.Material;

 
 public class Setup
 {
   Main plugin = (Main) Main.getPlugin(Main.class);
   
   public void config() {
     Config.setup();
     if ((new File(this.plugin.getDataFolder(), "Config.yml")).length() < 1L) {
       Config.get().set("YükselmeSeviyesi", Integer.valueOf(1));
       Config.get().set("BaşlangıçYüksekliği", Integer.valueOf(2));
       Config.get().set("YükselecekBlok", Material.LAVA.toString());
       Config.get().set("BorderGenişliği", Integer.valueOf(150));
       Config.get().set("YükselmeAralığı", Integer.valueOf(3));
       Config.get().set("OyunBaşlamaSüresi", Integer.valueOf(10));
       Config.get().set("Kazmaİsmi", "&eKngl&Lav");
       Config.get().set("RocketSayisi", Integer.valueOf(5));
       Config.save();
     } 
   }
 }
