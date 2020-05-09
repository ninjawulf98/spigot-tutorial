package com.ninjawulf98.quartermaster.listeners;

import com.ninjawulf98.quartermaster.QuarterMaster;
import com.ninjawulf98.quartermaster.utils.GUIManager;
import com.ninjawulf98.quartermaster.utils.LockUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListeners implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA + "Lock chest?")){
            e.setCancelled(true);

            if (e.getCurrentItem() == null) {
                return;
            }

            if(e.getCurrentItem().getType().equals(Material.TOTEM_OF_UNDYING)){
                player.sendMessage("Creating a new lock...");
                LockUtils.createNewLock(player, QuarterMaster.Locks_being_created.get(player));
                player.closeInventory();

            }else if (e.getCurrentItem().getType().equals(Material.BARRIER)){
                player.closeInventory();
            }
        } else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_RED + "Your Locks:")){
            e.setCancelled(true);
            if (e.getCurrentItem().getType().equals(null)) {
                return;
            } else if (e.getCurrentItem().getType().equals(Material.CHEST)) {
                GUIManager.openLockManagerGUI(player, LockUtils.getLock(GUIManager.getLocksListGUI().getItem(e.getSlot()).getItemMeta().getLore().get(7)));
            }
        }
    }
}
