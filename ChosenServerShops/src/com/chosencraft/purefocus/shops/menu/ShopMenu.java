package com.chosencraft.purefocus.shops.menu;

import com.chosencraft.purefocus.shops.WrappedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Set;

public class ShopMenu
{

	public static String title;
	private int size;

	private MenuItem[] items;

	public ShopMenu()
	{
		try
		{
			File file = new File("./plugins/ServerShop/shop.yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

			title = config.getString("title");
			size = config.getInt("rowCount") * 9;

			items = new MenuItem[size];

			ConfigurationSection sec = config.getConfigurationSection("items");
			Set<String> keys = sec.getKeys(false);

			int index;
			for (String key : keys)
			{
				index = Integer.valueOf(key);

				items[index] = MenuItem.parse(sec.getConfigurationSection(key));
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getTitle()
	{
		return title;
	}

	public boolean onClick(Player player, int slot)
	{
		if (slot > 0 && slot < items.length && items[slot] != null)
		{
			if (items[slot].onClick(new WrappedPlayer(player)))
			{
				display(player);
			}
			return true;
		}
		return false;
	}

	public void display(Player p)
	{
		WrappedPlayer player = new WrappedPlayer(p);

		Inventory inv = new ShopInventory(size, title).getInventory();

		ItemStack[] contents = inv.getContents();
		for (int i = 0; i < size; i++)
		{
			if (items[i] != null)
			{
				contents[i] = items[i].generateDisplay(player);
			}
		}

		inv.setContents(contents);


		player.getPlayer().openInventory(inv);
	}

	private class ShopInventory
			implements InventoryHolder
	{

		private final Inventory inv;

		public ShopInventory(int size, String title)
		{
			inv = Bukkit.createInventory(this, size, title);
		}

		@Override
		public Inventory getInventory()
		{
			return inv;
		}
	}

}
