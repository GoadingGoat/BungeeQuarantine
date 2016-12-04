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
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.append;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.clear;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.clearacceptedplayer;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.clearacceptedplayers;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.create;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.delete;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.droplive;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.insert;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.move;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.pulllive;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.pushlive;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.remove;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules.Server.show;
import io.github.goadinggoat.BungeeQuarantine.Commands.Util.displayrules;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class server implements Args {

	private BungeeQuarantine plugin;

	public server(BungeeQuarantine pl) {
		plugin = pl;
	}

	@Override
	public boolean execute(CommandSender sender, String server, String cmd, LinkedList<String> args) {
		if (plugin.servers.get(server) != null) {
			return argHandler(sender, plugin.servers.get(server).getName(), args);// change this to check if hidden or aliased server
		}else if(server.equalsIgnoreCase("Global")){
			return argHandler(sender, "Global", args);
		} else {
			sender.sendMessage(plugin.msg(plugin.messages.INVALIDSERVER));
			return false;
			//give list of valid servers....
		}
		
	}
	
	
	boolean argHandler(CommandSender sender, String server,LinkedList<String> args){
		if (args.size() == 0) {
			args.pollFirst();
			TextComponent message = new displayrules(plugin).getFormattedRules( server, true);
			if (message!=null){
				sender.sendMessage(message);
				return true;
			}else{
				sender.sendMessage(plugin.msg(plugin.messages.NORULES));
				return true;
			}
		} else if (args.getFirst().equalsIgnoreCase("add")) {
			args.pollFirst();
			return new append(plugin).execute(sender, server, "rules", args);
		} else if (args.getFirst().equalsIgnoreCase("remove")) {
			args.pollFirst();
			return new remove(plugin).execute(sender, server, "rules", args);
		} else if (args.getFirst().equalsIgnoreCase("clear")) {
			args.pollFirst();
			return new clear(plugin).execute(sender, server, "rules", args);
		} else if (args.getFirst().equalsIgnoreCase("insert")) {
			args.pollFirst();
			return new insert(plugin).execute(sender, server, "rules", args);
		} else if (args.getFirst().equalsIgnoreCase("move")) {
			args.pollFirst();
			return new move(plugin).execute(sender, server, "rules", args);
		} else if (args.getFirst().equalsIgnoreCase("create")) {
			args.pollFirst();
			return new create(plugin).execute(sender, server, "rules", args);
		}else if (args.getFirst().equalsIgnoreCase("delete")) {
			args.pollFirst();
			return new delete(plugin).execute(sender, server, "rules", args);
		}else if (args.getFirst().equalsIgnoreCase("pulllive")) {
			args.pollFirst();
			return new pulllive(plugin).execute(sender, server, "rules", args);
		}else if (args.getFirst().equalsIgnoreCase("pushlive")) {
			args.pollFirst();
			return new pushlive(plugin).execute(sender, server, "rules", args);
		}else if (args.getFirst().equalsIgnoreCase("droplive")) {
			args.pollFirst();
			return new droplive(plugin).execute(sender, server, "rules", args);
		}else if (args.getFirst().equalsIgnoreCase("show")) {
			args.pollFirst();
			return new show(plugin).execute(sender, server, "rules", args);
		} else if (args.getFirst().equalsIgnoreCase("clearacceptedplayers")) {
			args.pollFirst();
			return new clearacceptedplayers(plugin).execute(sender, server, "rules", args);
		} else if (args.getFirst().equalsIgnoreCase("clearacceptedplayer")) {
			args.pollFirst();
			return new clearacceptedplayer(plugin).execute(sender, server, "rules", args);
		} else {
			sender.sendMessage(plugin.msg(plugin.messages.INVALIDARG));
			return false;
		}
	}
}
