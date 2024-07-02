 package org.ahmetjr.kngllav.Donate;
 
 import java.util.ArrayList;
 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.Material;
 import org.bukkit.entity.Player;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.Listener;
 import org.bukkit.event.inventory.InventoryClickEvent;
 import org.bukkit.event.inventory.InventoryMoveItemEvent;
 import org.bukkit.event.player.PlayerDropItemEvent;
 import org.bukkit.event.player.PlayerJoinEvent;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.meta.BookMeta;
 import org.bukkit.inventory.meta.ItemMeta;
 import org.ahmetjr.kngllav.Events.GameHandler;
 
 public class Book implements Listener {
   private String donateUrl = "https://www.instagram.com/ahmetjr.exe/";
   private String bookName = "&eLava&eRisingJR 2.0";
   public void removeBook() {
     for (Player player : Bukkit.getOnlinePlayers()) {
       for (int i = 0; i <= 36; i++) {
         if (player.getInventory().getItem(i) != null &&
           player.getInventory().getItem(i).getType() == Material.WRITTEN_BOOK) {
           ItemStack book = player.getInventory().getItem(i);
           BookMeta bookMeta = (BookMeta)book.getItemMeta();
           if (bookMeta.getAuthor()
             .equals("AhmetJR")) {
             book.setAmount(0);
           }
         } 
       } 
     } 
   }
 
 
   
   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
     Player player = event.getPlayer();
     getBook(player);
   }
   
   public void getBook(Player player) {
     ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
     BookMeta bookMeta = (BookMeta)book.getItemMeta();
     bookMeta.setAuthor("AhmetJR");
     bookMeta.setTitle(ChatColor.translateAlternateColorCodes('&', this.bookName));
     ArrayList<String> pages = new ArrayList<>();
     pages.add(ChatColor.translateAlternateColorCodes('&', "&6Developed By AhmetJR\n\n&0Bana destek olmak için aşağıdaki bağlantıya tıklayabilirsiniz\n\nİyi Oyunlar Dilerim :)\n\n\n&0&l         ↓↓↓\n\n&3&n» " + this.donateUrl));
 
 
 
     
     bookMeta.setPages(pages);
     book.setItemMeta((ItemMeta)bookMeta);
     player.getInventory().setItem(8, book);
   }
   
   @EventHandler
   public void onClickBook(InventoryClickEvent event) {
     if (event.getCurrentItem() == null) {
       return;
     }
     ItemStack book = event.getCurrentItem();
     if (book.getType() == Material.WRITTEN_BOOK) {
       BookMeta bookMeta = (BookMeta)book.getItemMeta();
       if (bookMeta.getAuthor()
         .equals("AhmetJR") &&
         !GameHandler.gameStatus) {
         event.setCancelled(true);
       }
     } 
   }
 
 
   
   @EventHandler
   public void onMoveBook(InventoryMoveItemEvent event) {
     ItemStack book = event.getItem();
     BookMeta bookMeta = (BookMeta)book.getItemMeta();
     if (bookMeta.getAuthor()
       .equals("AhmetJR") &&
       !GameHandler.gameStatus) {
       event.setCancelled(true);
     }
   }
 
   
   @EventHandler
   public void onDropBook(PlayerDropItemEvent event) {
     ItemStack book = event.getItemDrop().getItemStack();
     if (event.getItemDrop().getItemStack().getType() == Material.WRITTEN_BOOK) {
       BookMeta bookMeta = (BookMeta)book.getItemMeta();
       if (bookMeta.getAuthor()
         .equals("AhmetJR") &&
         !GameHandler.gameStatus)
         event.setCancelled(true);
     } 
   }
 }

