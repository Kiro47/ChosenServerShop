package com.chosencraft.purefocus.shops.menu;

import com.chosencraft.purefocus.shops.ServerShops;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MenuListener
		implements Listener
{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event)
	{
		Inventory inv = event.getInventory();
		if (inv.getName().equals(ShopMenu.title))
		{
			event.setCancelled(true);
			event.setResult(Event.Result.DENY);

			int slot = event.getRawSlot();
			ServerShops.getMenu().onClick((Player) event.getWhoClicked(), slot);
		}
	}

}
