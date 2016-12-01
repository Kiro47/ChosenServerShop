package com.chosencraft.purefocus.shops.menu;

import com.chosencraft.purefocus.pinfo.Chat;
import com.chosencraft.purefocus.pinfo.GlobalPoints;
import com.chosencraft.purefocus.shops.WrappedPlayer;
import com.chosencraft.purefocus.shops.actions.ActionType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuItem
		implements IMenuItem
{

	protected ActionType action;

	protected Material material;
	protected String itemName;
	protected List<String> description;
	protected short data;
	protected int count;

	protected String perm;

	protected int gpCost;
	protected int vpCost;

	public MenuItem()
	{

	}

	public MenuItem(String itemName, int gpCost, int vpCost, Material material, List<String> description)
	{
		this.itemName = itemName;
		this.gpCost = gpCost;
		this.vpCost = vpCost;
		this.material = material;
		this.description = description;
	}

	public MenuItem setDescription(List<String> description)
	{
		this.description = description;
		return this;
	}

	protected boolean canPurchase(WrappedPlayer player)
	{
		return player.hasPoints(gpCost) && player.hasVotes(vpCost);
	}

	public boolean onClick(WrappedPlayer player)
	{
		if (owns(player))
		{
			player.getPlayer().sendMessage(ChatColor.RED + "You already own this item!");
		}
		else
		{
			if (canPurchase(player))
			{
				if (action != null)
				{
					action.execute(player);
				}
				player.removePoints(gpCost);
				if (perm != null)
				{
					player.addPermission(perm);
				}
				player.getPlayer().sendMessage(ChatColor.GREEN + "Thank you for shopping with us!");
				return true;
			}
			else
			{
				player.getPlayer().sendMessage(ChatColor.RED + "You are unable to purchase this item!");
			}
		}
		return false;
	}

	public boolean owns(WrappedPlayer player)
	{
		return perm != null && player.hasPermission(perm);
	}

	public ItemStack generateDisplay(WrappedPlayer player)
	{
		ItemStack stack = new ItemStack(material, count, data);

		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(itemName);

		List<String> lore = new ArrayList<>();

		if (description != null)
		{
			lore.addAll(description);
		}

		lore.add(" ");

		if (owns(player))
		{
			lore.add(ChatColor.BLUE + "Owned");

		}
		else
		{
			int bal;
			if (gpCost > 0)
			{
				bal = player.getPoints();
				lore.add(costColor(bal, gpCost, "Points: ", "gp"));
			}

			if (vpCost > 0)
			{
				bal = player.getVotes();
				lore.add(costColor(bal, vpCost, "Votes: ", "vp"));
			}
		}

		meta.setLore(lore);


		stack.setItemMeta(meta);

		return stack;
	}

	private static String costColor(int bal, int cost, String title, String unit)
	{
		ChatColor color = bal < cost ? ChatColor.RED : ChatColor.GREEN;
		return color + title + cost + unit;
	}

	public static MenuItem parse(ConfigurationSection sec)
	{
		MenuItem item;
		if (sec.contains("balance"))
		{
			item = new BalanceItem();
		}
		else
		{
			item = new MenuItem();
		}

		item.itemName = Chat.format(sec.getString("name"));
		if (sec.contains("description"))
		{
			item.description = sec.getStringList("description");
		}
		item.gpCost = sec.getInt("points", 0);
		item.vpCost = sec.getInt("votes", 0);

		item.material = Material.valueOf(sec.getString("item.type"));
		item.data = (short) sec.getInt("item.data", 0);
		item.count = sec.getInt("item.count", 1);

		if (sec.contains("purchasePerm"))
		{
			item.perm = sec.getString("purchasePerm");
		}
		else
		{
			item.perm = null;
		}

		if (sec.contains("cmd"))
		{
			item.action = ActionType.command(sec.getString("cmd"));
		}
		else if (sec.contains("msg"))
		{
			item.action = ActionType.message(sec.getString("msg"));
		}
		else if (sec.contains("perm"))
		{
			item.action = ActionType.message(sec.getString("msg"));
		}

		return item;

	}
}
