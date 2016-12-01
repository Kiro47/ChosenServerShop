package com.chosencraft.purefocus.shops;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.chosencraft.purefocus.shops.menu.MenuListener;
import com.chosencraft.purefocus.shops.menu.ShopMenu;

public class ServerShops
		extends JavaPlugin
{

	private static ShopMenu menu;
	private static String PLUGIN_NAME = "ServerShop";
	public void onEnable()
	{
		menu = new ShopMenu();

		getCommand("servershop").setExecutor(new ServerShopCommand());

		getServer().getPluginManager().registerEvents(new MenuListener(), this);
	}

	public void onDisable()
	{
		menu = null;
	}

	public static void reload()
	{
		menu = new ShopMenu();
	}

	public static ShopMenu getMenu()
	{
		return menu;
	}

	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin(PLUGIN_NAME);
	}
}
