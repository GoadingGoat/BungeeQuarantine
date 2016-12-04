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
import net.md_5.bungee.api.CommandSender;


public class reload implements Args{

	private BungeeQuarantine plugin;

	public reload(BungeeQuarantine pl) {
		plugin = pl;
	}
	@Override
	public boolean execute(CommandSender sender, String label, String cmd, LinkedList<String> args) {
		if (args.size()==0){
			if (plugin.reload()) {
				sender.sendMessage(plugin.msg(plugin.messages.CONFIGRELOADEDSUCCESSFULLY));
				return true;
			} else {
				sender.sendMessage(plugin.msg(plugin.messages.CONFIGRELOADFAILED));
				return true;
			}
		}else{
			sender.sendMessage(plugin.msg(plugin.messages.TOOMANYARGS));
			return false;
		}
		
		
	}
}

