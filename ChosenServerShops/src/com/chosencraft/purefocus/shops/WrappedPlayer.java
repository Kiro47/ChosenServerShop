package com.chosencraft.purefocus.shops;

import com.chosencraft.purefocus.pinfo.GlobalPoints;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class WrappedPlayer
{

	private Player player;
	private int votes;
	private int points;

	public WrappedPlayer(Player player)
	{
		this.player = player;
		this.points = GlobalPoints.getPoints(player);
		this.votes = GlobalPoints.getVotes(player);
	}

	public Player getPlayer()
	{
		return player;
	}

	public int getVotes()
	{
		return votes;
	}

	public int getPoints()
	{
		return points;
	}

	public boolean hasVotes(int votes)
	{
		return this.votes >= votes;
	}

	public boolean hasPoints(int points)
	{
		return this.points >= points;
	}

	public void removePoints(int points)
	{
		this.points -= points;
		GlobalPoints.takePoints(player, points);
	}

	public void addPermission(String node)
	{
		PermissionsEx.getUser(player).addPermission(node);
	}

	public void removePermission(String node)
	{
		PermissionsEx.getUser(player).removePermission(node);
	}

	public boolean hasPermission(String node)
	{
		return PermissionsEx.getUser(player).has(node);
	}
}
