package io.github.goadinggoat.BungeeQuarantine.Listeners;

/**
 * 
 *  BungeeQuarantine
    Copyright (C) 2016  Sean Fleck
    email: goadinggoat@gmail.com

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Objects.GoatPlayer;
import io.github.goadinggoat.BungeeQuarantine.managers.PlayerManager;
import io.github.goadinggoat.BungeeQuarantine.managers.RulesManager;

public class PlayerListener implements Listener {

	private BungeeQuarantine plugin;

	public PlayerListener(BungeeQuarantine pl) {
		plugin = pl;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void playerLogin(LoginEvent event) {
		event.registerIntent(plugin);
		PlayerManager.initPlayer(event.getConnection(), event);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void playerLogout(final PlayerDisconnectEvent e) {
		PlayerManager.cachedPlayers.remove(e.getPlayer().getUniqueId());
	}

	// send rules to player on join if set true in config
	@EventHandler
	public void onJoin(ServerConnectedEvent event) {
		ProxiedPlayer player = event.getPlayer();
		GoatPlayer gplayer = PlayerManager.getPlayer(player.getUniqueId());
		String server = event.getServer().getInfo().getName();
		// check server has rules...
		if (RulesManager.serverRules.containsKey(server)) {
			if (gplayer.servers.containsKey(event.getServer().getInfo().getName())) {
				if (gplayer.servers.get(server) == true) {
					return;
				}
			}
		}
		if (plugin.config.AcceptRulesOnJoin) {
			ProxyServer.getInstance().getScheduler().schedule(plugin, new Runnable() {
				public void run() {
					plugin.proxy.getPluginManager().dispatchCommand(player, "/accpetrules");
				}
			}, 5, TimeUnit.SECONDS);
		}
	}

	// disable commands and chat for that player
	@EventHandler
	public void onCommand(ChatEvent event) {
		if (event.getSender() instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer) event.getSender();
			GoatPlayer goatPlayer = PlayerManager.getPlayer(player.getUniqueId());
			if (RulesManager.serverRules.containsKey(player.getServer().getInfo().getName())) {
				if (!goatPlayer.servers.containsKey(player.getServer().getInfo().getName())) {
					if (event.isCommand()) {
						String[] parts = event.getMessage().split(" ");
						// cancel command events unless command is acceptrules
						// or in exempt commands.
						if (parts[0].equalsIgnoreCase("/acceptrules")) {
							return;
						}
						for (String cmd : plugin.config.EXEMPTCOMMANDS) {
							if (parts[0].equalsIgnoreCase(cmd)) {
								return;
							}
						}
						player.sendMessage(plugin.msg(plugin.messages.COMMANDEVENTCANCELLED));
						event.setCancelled(true);
					} else {
						if (plugin.config.CancelChat) {
							player.sendMessage(plugin.msg(plugin.messages.CHATEVENTCANCELLED));
							event.setCancelled(true);
						}

					}
				}
			}

		}
	}

	// intercept server change events to force the player onto the TargetServer
	@EventHandler
	public void interceptServer(ServerConnectEvent event) {
		ProxiedPlayer player = event.getPlayer();
		GoatPlayer goatPlayer = PlayerManager.getPlayer(player.getUniqueId());
		if (goatPlayer == null) {
			// should never happen
			plugin.getLogger().info("Error: GoatPlayer was null on server connect event!");
			goatPlayer = new GoatPlayer(player.getUniqueId().toString());
			PlayerManager.cachedPlayers.put(player.getUniqueId(),goatPlayer);
		}
		if (!goatPlayer.servers.containsKey(event.getTarget())) {
			ArrayList<String> QuarantineServers = plugin.config.QuarantineServers;
			if (QuarantineServers.size() > 0) {
				ServerInfo server = plugin.servers.get(QuarantineServers.get(0));
				if (server != null) {
					if (event.getTarget() != server) {
						event.setCancelled(true); // cancel event
						plugin.getLogger().info("Cancelling server connect as not target server");
						// schedule new event to ping server with new variable
						// referneces, as iu assume the references
						// above will likely get cleaned up by the garbage
						// collector
						// before
						// the ping event returns, leaving the player in
						// limbo...
						// where they will eventually timeout...
						ProxyServer.getInstance().getScheduler().runAsync(plugin, new Runnable() {
							// create new references, so garbage collector does
							// not
							// clean them up...
							volatile ServerInfo targetserver = server;
							volatile ProxiedPlayer tplayer = player;

							@Override
							public void run() {
								// ping the server to see if it's online
								targetserver.ping(new Callback<ServerPing>() { // ping
									// TargetServer
									@Override
									public void done(ServerPing result, Throwable error) {
										plugin.getLogger().info("Server has been pinged");
										if (error != null || result == null) {
											// Target Server is offline, give
											// player
											// approriate error message
											TextComponent message = plugin.msg(plugin.messages.TARGETSERVEROFFLINE);
											message.addExtra(plugin.msg(plugin.messages.CONTACTADDRESS));
											tplayer.disconnect(message);
											return;
										}
										// Target Server is online, connect
										// player
										// to it.
										plugin.getLogger()
												.info("Player has been redirected to: " + targetserver.getName());
										tplayer.connect(targetserver);
									}
								});
							}
						});
					}
				} else {
					// target server in config not valid.
					event.setCancelled(true);
					TextComponent message = plugin.msg(plugin.messages.TARGETSERVERMISCONFIGURED);
					message.addExtra(plugin.msg(plugin.messages.CONTACTADDRESS));
					player.disconnect(message);
				}
			} else {
				// quarantine servers in config is empty .
				event.setCancelled(true);
				TextComponent message = plugin.msg(plugin.messages.NOQUARANTINESERVERS);
				message.addExtra(plugin.msg(plugin.messages.CONTACTADDRESS));
				player.disconnect(message);
			}

		}
	}

}
