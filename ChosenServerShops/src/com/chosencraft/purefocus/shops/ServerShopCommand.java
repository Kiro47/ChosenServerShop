package com.chosencraft.purefocus.shops;

import com.chosencraft.purefocus.shops.menu.ShopMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerShopCommand
		implements CommandExecutor
{

	public ServerShopCommand()
	{

	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{

		if (sender.isOp() && args.length > 0 && args[0].equalsIgnoreCase("reload"))
		{
			ServerShops.reload();
			sender.sendMessage(ChatColor.GREEN + "ServerShops has been reloaded!");
		}
		else
		{
			ShopMenu menu = ServerShops.getMenu();

			menu.display((Player) sender);
		}


		return true;
	}

}
