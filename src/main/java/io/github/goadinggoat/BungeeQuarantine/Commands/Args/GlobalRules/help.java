package io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules;

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

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.Args;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class help implements Args {

	private BungeeQuarantine plugin;

	public help(BungeeQuarantine pl) {
		plugin = pl;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String cmd, LinkedList<String> args) {
		if (args.size() == 0) {
			TextComponent message = new TextComponent();
			TextComponent firstline = new TextComponent("---------------------------------------------\n");
			firstline.setStrikethrough(true);
			firstline.setBold(true);
			firstline.setColor(ChatColor.GREEN);
			message.addExtra(firstline);
			message.addExtra(plugin.msg(plugin.messages.HELP + "\n"));
			if (plugin.config.GlobalRules) {
				message.addExtra(plugin.msg(plugin.messages.HELPRULES + "\n"));
			}
			if (plugin.config.PerServerRules || plugin.config.GlobalRules) {
				message.addExtra(plugin.msg(plugin.messages.HELPRULESSERVER + "\n"));
				message.addExtra(plugin.msg(plugin.messages.HELPRULESLISTSERVERS + "\n"));
			}
			if (sender instanceof ProxiedPlayer && plugin.config.PerServerRules) {
				message.addExtra(plugin.msg(plugin.messages.HELPSERVERRULES + "\n"));
			}
			if (sender.hasPermission("bq.admin")) {
				message.addExtra(plugin.msg(plugin.messages.HELPRELOAD + "\n"));
				message.addExtra(plugin.msg(plugin.messages.HELPCONFIRM + "\n"));
				if (plugin.config.PerServerRules || plugin.config.GlobalRules) {
					message.addExtra(plugin.msg(plugin.messages.HELPADD + "\n"));
					message.addExtra(plugin.msg(plugin.messages.HELPINSERT + "\n"));
					message.addExtra(plugin.msg(plugin.messages.HELPMOVE + "\n"));
					message.addExtra(plugin.msg(plugin.messages.HELPREMOVE + "\n"));
					message.addExtra(plugin.msg(plugin.messages.HELPCLEAR + "\n"));
					message.addExtra(plugin.msg(plugin.messages.HELPPUSHLIVE + "\n"));
					message.addExtra(plugin.msg(plugin.messages.HELPPULLLIVE + "\n"));
				}
				if (plugin.config.AcceptRules == 1 || plugin.config.AcceptRules == 2) {
					message.addExtra(plugin.msg(plugin.messages.HELPCLEARACCEPTEDPLAYER + "\n"));
					message.addExtra(plugin.msg(plugin.messages.HELPCLEARACCEPTEDPLAYERS + "\n"));
				}
			}
			message.addExtra(firstline);
			sender.sendMessage(message);
			return true;
		} else {
			sender.sendMessage(plugin.msg(plugin.messages.TOOMANYARGS));
			return false;
		}

	}

}
