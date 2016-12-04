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
import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.Args;
import io.github.goadinggoat.BungeeQuarantine.Commands.Util.ConfirmableCommand;
import io.github.goadinggoat.BungeeQuarantine.managers.RulesManager;
import io.github.goadinggoat.BungeeQuarantine.managers.RulesYAMLManager;

import net.md_5.bungee.api.CommandSender;

public class delete implements Args {

	private BungeeQuarantine plugin;

	public delete(BungeeQuarantine pl) {
		plugin = pl;
	}

	@Override
	public boolean execute(CommandSender sender, String server, String cmd, LinkedList<String> args) {
		if (args.size() == 0) {
			if (RulesYAMLManager.exists(server)) {
				File file = new File(plugin.getDataFolder(), Paths.get("Rules", server + ".yml").toString());
				if (file.exists()) {
					new ConfirmableCommand(plugin, sender, "rules {server} delete confirm".replace("{server}", server),
							plugin.config.ConfiramableCommandTimeout, TimeUnit.SECONDS);
					sender.sendMessage(plugin.msg(plugin.messages.CONFIRMDELETE.replace("{server}", server)));
					return true;
				} else {
					sender.sendMessage(plugin.msg(plugin.messages.FILEDOESNOTEXIST));
					return true;
				}
			} else {
				sender.sendMessage(plugin.msg(plugin.messages.YAMLNOTLOADEDINMEMORY));
				return true;
			}
		} else if (args.size() == 1) {
			if (args.pollFirst().equalsIgnoreCase("confirm")) {
				if (RulesYAMLManager.exists(server)) {
					File file = new File(plugin.getDataFolder(), Paths.get("Rules", server + ".yml").toString());
					if (file.exists()) {
						RulesManager.serverRules.remove(server);
						file.delete();
						RulesYAMLManager.YAMLrules.remove(server);
						sender.sendMessage(plugin.msg(plugin.messages.YAMLFILEDELETED.replace("{server}", server)));
						return true;
					} else {
						sender.sendMessage(plugin.msg(plugin.messages.FILEDOESNOTEXIST));
						return true;
					}
				} else {
					sender.sendMessage(plugin.msg(plugin.messages.YAMLNOTLOADEDINMEMORY));
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
