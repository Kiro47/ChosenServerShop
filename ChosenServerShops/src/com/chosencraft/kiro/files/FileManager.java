package com.chosencraft.kiro.files;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.chosencraft.purefocus.shops.ServerShops;

public class FileManager {

	private static ArrayList<UUID> specialPermissionPlayers = new ArrayList<UUID>();
	
	public static void addToList(UUID playerUUID) {
		specialPermissionPlayers.add(playerUUID);
	}
	
	public static void removeFromList(UUID playerUUID) {
		specialPermissionPlayers.remove(playerUUID);
	}
	
	public static boolean listContainsPlayer(UUID playerUUID) {
		return specialPermissionPlayers.contains(playerUUID);
	}
	
	public static final FileManager
		data = new FileManager("data")
		;
	
	public static FileManager getData() {
		return data;
	}
	
	private File file;
	private FileConfiguration config;
	
	private FileManager(String filename) {
		if (!ServerShops.getPlugin().getDataFolder().exists()){
			ServerShops.getPlugin().getDataFolder().mkdir();
		}
		
		file = new File(ServerShops.getPlugin().getDataFolder(), filename + ".yml");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
			config = YamlConfiguration.loadConfiguration(file);
	}
	
	/**
	 * 
	 * @param playerUUID UUID of player to be saved.
	 * @param info Info array, of Strings with format (permissionNode;timeInSeconds)
	 */
	public void  savePlayerData(UUID playerUUID, String[] info) {
		if (info != null) {
			String moddifable = "";
			for (String string : info) {
				moddifable += (":" + string);
			}
			config.set(playerUUID.toString().replace("-", ""), moddifable);
		}
	}
	
	/**
	 * loadPlayerData
	 * 
	 * @param playerUUID UUID of player to be loaded.
	 * @return	info in form of String[permission #] where string = (permissionNode;timeInSeconds)
	 */
	public String[] loadPlayerData (UUID playerUUID) {
		// In format of UUID:permission;time...
		String[] info;
		if (!containsPlayer(playerUUID)){
			info = null;
		}
		else {
			info = ((String)config.get(playerUUID.toString().replace("-", ""))).split(":");
		}
		
		return  info;
	}
	
	/**
	 * hasSpecialPermissions
	 * 
	 * @param playerUUID  UUID of player to be checked.
	 * @return if the player has special Permissions
	 */
	public boolean hasSpecialPermissions(UUID playerUUID) {
		return specialPermissionPlayers.contains(playerUUID);
	}
	
	public boolean containsPlayer(UUID playerUUID) {
		return config.contains(playerUUID.toString().replace("-", ""));
	}
	
	
	/**
	 * addPlayer
	 * 
	 * @param playerUUID UUID of player to be added.
	 * @param permission Permission Node to be added.
	 * @param timeInSeconds Time of the permission to be added in seconds.
	 */
	public void addPlayer(UUID playerUUID, String permission, int timeInSeconds) {
		// If they don't have perms  already.
		if (!containsPlayer(playerUUID)) {
			// Added in format: String UUID , String permission;int timeInSeconds
			config.set(playerUUID.toString().replace("-", ""), permission + ";" + timeInSeconds);
		}
		// If they do.
		else {
			String moddifable = (String) config.get(playerUUID.toString().replace("-", ""));
			moddifable += ( ":" + permission + ";" + timeInSeconds);
			config.set(playerUUID.toString().replace("-", ""), moddifable);
		}
	}
	/**
	 * removePlayer
	 * 
	 * @param playerUUID UUID of player to be removed.
	 * 
	 * Removes a  player from the file system.
	 */
	public void removePlayer(UUID playerUUID){
		config.set(playerUUID.toString().replace("-", ""), null);
	}
	
	/**
	 * Saves the file.
	 */
	public void save() {
		
		try {
			config.save(file);
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
