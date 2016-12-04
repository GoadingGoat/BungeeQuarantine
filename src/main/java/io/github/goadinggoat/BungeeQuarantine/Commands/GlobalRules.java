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

import java.util.Arrays;
import java.util.LinkedList;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.confirm;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.help;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.listservers;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.reload;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.server;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class GlobalRules extends Command {
	private BungeeQuarantine plugin;

	public GlobalRules(BungeeQuarantine pl) {
		super("rules", null, new String[] { "rl" });
		plugin = pl;
	}

	public void execute(CommandSender sender, String[] a) {
		if (a.length == 0) {
			new server(plugin).execute(sender, "Global", "rules", new LinkedList<String>());
		} else {
			if (a[0].equalsIgnoreCase("help")) {
				LinkedList<String> list = new LinkedList<String>(Arrays.asList(a));
				list.pollFirst();
				new help(plugin).execute(sender, a[0], "rules", list);
			} else if (a[0].equalsIgnoreCase("listservers")) {
				LinkedList<String> list = new LinkedList<String>(Arrays.asList(a));
				list.pollFirst();
				new listservers(plugin).execute(sender, a[0], "rules", list);
			} else if (sender.hasPermission("bq.admin")) {
				if (a[0].equalsIgnoreCase("reload")) {
					LinkedList<String> list = new LinkedList<String>(Arrays.asList(a));
					list.pollFirst();
					new reload(plugin).execute(sender, a[0], "rules", list);
				} else if (a[0].equalsIgnoreCase("confirm")) {
					LinkedList<String> list = new LinkedList<String>(Arrays.asList(a));
					list.pollFirst();
					new confirm(plugin).execute(sender, a[0], "rules", list);
				} else {
					LinkedList<String> list = new LinkedList<String>(Arrays.asList(a));
					list.pollFirst();
					new server(plugin).execute(sender, a[0], "rules", list);
				}
			} else {
				if (a.length == 1) {
					LinkedList<String> list = new LinkedList<String>(Arrays.asList(a));
					list.pollFirst();
					new server(plugin).execute(sender, a[0], "rules", list);
				} else {
					sender.sendMessage(plugin.msg(plugin.messages.TOOMANYARGS));
				}
			}
		}
	}
}
