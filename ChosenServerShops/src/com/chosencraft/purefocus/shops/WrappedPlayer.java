package com.chosencraft.purefocus.shops;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.chosencraft.kiro.files.FileManager;
import com.chosencraft.purefocus.pinfo.GlobalPoints;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class WrappedPlayer {

	private String world = 	Bukkit.getServer().getWorlds().get(0).getName();
	
	private Player player;
	private int votes;
	private int points;

	public WrappedPlayer(Player player) {
		this.player = player;
		this.points = GlobalPoints.getPoints(player);
		this.votes = GlobalPoints.getVotes(player);
	}

	public Player getPlayer() {
		return player;
	}

	public int getVotes() {
		return votes;
	}

	public int getPoints() {
		return points;
	}

	public boolean hasVotes(int votes) {
		return this.votes >= votes;
	}

	public boolean hasPoints(int points) {
		return this.points >= points;
	}

	public void removePoints(int points) {
		this.points -= points;
		GlobalPoints.takePoints(player, points);
	}

	public void addPermission(String node, int timeInSeconds) {
		PermissionsEx.getUser(player).addTimedPermission(node, world, timeInSeconds);
		FileManager.getData().addPlayer(player.getUniqueId(), node, timeInSeconds);
	}

	public void removePermission(String node) {
		PermissionsEx.getUser(player).removeTimedPermission(node, world);
		FileManager.getData().removePlayer(player.getUniqueId());
	}

	public boolean hasPermission(String node) {
		return PermissionsEx.getUser(player).has(node);
	}
}
