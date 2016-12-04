package io.github.goadinggoat.BungeeQuarantine.Commands;

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

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Commands.Util.displayrules;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ServerRules extends Command {
	private BungeeQuarantine plugin;

	public ServerRules(BungeeQuarantine pl) {
		super("srules", null, new String[] {});
		plugin = pl;
	}

	public void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof ProxiedPlayer)) {
				sender.sendMessage(plugin.msg(plugin.messages.MUSTBERUNASPLAYER));
				return;
			}
			ProxiedPlayer player = (ProxiedPlayer) sender;
			String server = player.getServer().getInfo().getName();
			TextComponent message = new displayrules(plugin).getFormattedRules( server, true);
			if (message!=null){
				sender.sendMessage(message);
				
			}else{
				sender.sendMessage(plugin.msg(plugin.messages.NORULES));
				
			}
		} else{
			sender.sendMessage(plugin.msg(plugin.messages.TOOMANYARGS));
		}
	}
}
