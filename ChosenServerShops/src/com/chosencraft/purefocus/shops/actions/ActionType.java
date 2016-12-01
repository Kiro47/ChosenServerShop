package com.chosencraft.purefocus.shops.actions;

import com.chosencraft.purefocus.shops.WrappedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class ActionType
{

	public abstract boolean execute(WrappedPlayer player);


	public static ActionType command(String cmd)
	{
		return new ActionCommand(cmd);
	}

	public static ActionType message(String msg)
	{
		return new ActionMessage(msg);
	}

	private static class ActionCommand
			extends ActionType
	{

		private final String cmd;

		private ActionCommand(String cmd)
		{
			this.cmd = cmd;
		}

		@Override
		public boolean execute(WrappedPlayer wplayer)
		{
			Player player = wplayer.getPlayer();
			String _cmd = cmd.replace("%1", player.getName());
			_cmd = _cmd.replace("%2", player.getDisplayName());
			_cmd = _cmd.replace("%3", player.getUniqueId().toString());

			return Bukkit.dispatchCommand(Bukkit.getConsoleSender(), _cmd);
		}

	}


	private static class ActionMessage
			extends ActionType
	{
		private final String message;

		private ActionMessage(String message)
		{
			this.message = message;
		}

		@Override
		public boolean execute(WrappedPlayer player)
		{
			player.getPlayer().sendMessage(message);
			return true;
		}
	}
}
