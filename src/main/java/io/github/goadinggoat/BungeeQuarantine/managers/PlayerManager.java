package io.github.goadinggoat.BungeeQuarantine.managers;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;

import java.util.*;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Database.DataBase.SQLStates;
import io.github.goadinggoat.BungeeQuarantine.Objects.GoatPlayer;

public class PlayerManager {
	private static BungeeQuarantine plugin;

	public static HashMap<UUID, GoatPlayer> cachedPlayers = new HashMap<>();

	public static void setInstance(BungeeQuarantine pl) {
		plugin = pl;
	}

	public static boolean cachedPlayerExists(ProxiedPlayer player) {
		return playerExists(player.getUniqueId());
	}

	public static boolean playerExists(UUID player) {
		// needs a lot of work here
		plugin.playerDataBase.checkIfPlayerExists("player", player.toString());
		return false;
	}

	public static void initPlayer(final PendingConnection connection, final LoginEvent event) {
		ProxyServer.getInstance().getScheduler().runAsync(plugin.instance, new Runnable() {
			@Override
			public void run() {

				Boolean playerExists = playerExists(connection.getUniqueId());
				GoatPlayer goatPlayer;
				if (playerExists) {
					goatPlayer = getPlayer(connection.getUniqueId());
					if (goatPlayer == null) {
						goatPlayer = plugin.playerDataBase.loadPlayer("player", connection.getUniqueId().toString());
						for (String server : plugin.serverNames) {
							if (plugin.playerDataBase.checkIfTableExists(server) == SQLStates.TRUE) {
								if (plugin.playerDataBase.checkIfPlayerExists(server,goatPlayer.getUUID()) == SQLStates.TRUE) {
									plugin.playerDataBase.loadPlayerAccepted(server, goatPlayer);
								}
							}
						}
					}
				} else {
					goatPlayer = new GoatPlayer(connection.getName());
				}
				cachedPlayers.put(connection.getUniqueId(), goatPlayer);
				event.completeIntent(plugin.instance);
			}
		});
	}

	public static GoatPlayer getPlayer(UUID id) {
		return cachedPlayers.get(id);
	}

	public static GoatPlayer getPlayer(ProxiedPlayer player) {
		return cachedPlayers.get(player.getUniqueId());
	}
}
