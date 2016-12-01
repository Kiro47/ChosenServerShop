package com.chosencraft.purefocus.shops.menu;

import com.chosencraft.purefocus.shops.WrappedPlayer;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BalanceItem
		extends MenuItem
{

	public boolean onClick(WrappedPlayer player)
	{
		return false;
	}

	public boolean owns(WrappedPlayer player)
	{
		return true;
	}

	public ItemStack generateDisplay(WrappedPlayer player)
	{
		ItemStack stack = new ItemStack(material, 1, data);

		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(itemName);

		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_GREEN + "Points: " + player.getPoints() + "gp");
		lore.add(ChatColor.DARK_AQUA + "Votes: " + player.getVotes() + "vp");


		meta.setLore(lore);


		stack.setItemMeta(meta);

		return stack;
	}
}
