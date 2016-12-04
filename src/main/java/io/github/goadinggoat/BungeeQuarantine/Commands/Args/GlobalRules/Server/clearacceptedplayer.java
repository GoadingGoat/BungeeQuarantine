package io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server;

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

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.Args;
import io.github.goadinggoat.BungeeQuarantine.Commands.Util.ConfirmableCommand;
import io.github.goadinggoat.BungeeQuarantine.Database.DataBase.SQLStates;

import net.md_5.bungee.api.CommandSender;

public class clearacceptedplayer implements Args {

	private BungeeQuarantine plugin;

	public clearacceptedplayer(BungeeQuarantine pl) {
		plugin = pl;
	}

	@Override
	public boolean execute(CommandSender sender, String server, String cmd, LinkedList<String> args) {
		if (args.size() == 0) {
			sender.sendMessage(plugin.msg(plugin.messages.TOOFEWARGS));
			return false;
		} else if (args.size() == 1) {
			if (plugin.playerDataBase.checkIfTableExists(server) == SQLStates.TRUE) {
				if (plugin.playerDataBase.checkIfPlayerExists(server, args.getFirst()) == SQLStates.TRUE) {
					new ConfirmableCommand(plugin, sender,
							"rules {server} clearacceptedplayer {uuid} confirm".replace("{server}", server)
									.replace("{uuid}", args.getFirst()),
							plugin.config.ConfiramableCommandTimeout, TimeUnit.SECONDS);
					sender.sendMessage(plugin.msg(plugin.messages.CONFIRMCLEARPLAYER.replace("{server}", server)
							.replace("{uuid}", args.getFirst())));
					return true;
				} else {
					sender.sendMessage(plugin.msg(plugin.messages.UUIDNOTFOUNDINDB));
					return true;
				}
			} else {
				sender.sendMessage(
						plugin.msg(plugin.messages.ACCEPTEDPLAYERSERVERNOTEXISTS.replace("{server}", server)));
				return true;
			}
		} else if (args.size() == 2) {
			if (args.get(1).equalsIgnoreCase("confirm")) {
				if (plugin.playerDataBase.checkIfTableExists(server) == SQLStates.TRUE) {
					if (plugin.playerDataBase.checkIfPlayerExists(server, args.getFirst()) == SQLStates.TRUE) {
						plugin.playerDataBase.setPlayerAccepted(server, args.pollFirst(), false);
						sender.sendMessage(
								plugin.msg(plugin.messages.PLAYERSETUNNACCEPTED.replace("{UUID}", args.pollFirst())));
						return true;
					} else {
						sender.sendMessage(plugin.msg(plugin.messages.UUIDNOTFOUNDINDB));
						return true;
					}
				} else {
					sender.sendMessage(
							plugin.msg(plugin.messages.ACCEPTEDPLAYERSERVERNOTEXISTS.replace("{server}", server)));
					return true;
				}
			} else {
				sender.sendMessage(plugin.msg(plugin.messages.TOOMANYARGS));
				return false;
			}

		} else {
			sender.sendMessage(plugin.msg(plugin.messages.TOOMANYARGS));
			return false;
		}

	}
}
