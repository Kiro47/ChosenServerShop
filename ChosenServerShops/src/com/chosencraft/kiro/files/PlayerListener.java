package com.chosencraft.kiro.files;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.chosencraft.purefocus.shops.WrappedPlayer;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerListener implements Listener {

	private static  String world = Bukkit.getServer().getWorlds().get(0).getName();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (FileManager.getData().containsPlayer(player.getUniqueId())) {

			FileManager.addToList(player.getUniqueId());

			WrappedPlayer wrappedPlayer = new WrappedPlayer(player);

			for (String data : FileManager.getData().loadPlayerData(player.getUniqueId())) {
				String[] parse = data.split(";");
				wrappedPlayer.addPermission(parse[0],
						timeDilation(Integer.parseInt(parse[1]), Long.parseLong(parse[2])));
			}

		}

	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		UUID playerUUID = event.getPlayer().getUniqueId();
		if (FileManager.listContainsPlayer(playerUUID)) {
			removePlayer(event.getPlayer());
		}
	}

	/**
	 * 
	 * @param time
	 * @param previousTimeStamp
	 * @return
	 */
	private int timeDilation(Integer time, Long previousTimeStamp) {
		return (int) (time - ((System.currentTimeMillis() - previousTimeStamp) / 100));

	}

	/**
	 * 
	 * @param player
	 */
	public static void removePlayer (Player player) {


		List<String> list = PermissionsEx.getUser(player).getTimedPermissions(world);
		String[] info = new String[list.size()];
		int i = 0;

		for (String permission : list) {
			info[i] = permission + ";"
					+ PermissionsEx.getUser(player).getTimedPermissionLifetime(permission, world);
			i++;
		}

		FileManager.getData().savePlayerData(player.getUniqueId(), info);
	
	}
	
	/**
	 * 
	 */
	public static void removeAllPlayers() {
		for (Player player: Bukkit.getServer().getOnlinePlayers()) {
			if (FileManager.listContainsPlayer(player.getUniqueId())) {
				removePlayer(player);
			}
		}
	}
}
