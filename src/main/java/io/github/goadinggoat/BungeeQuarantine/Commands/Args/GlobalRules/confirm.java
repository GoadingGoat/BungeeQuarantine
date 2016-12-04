package io.github.goadinggoat.BungeeQuarantine.Commands.Args.GlobalRules;

import java.util.ArrayList;

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
import io.github.goadinggoat.BungeeQuarantine.managers.ConfirmableCommandManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
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

public class confirm implements Args{

	private BungeeQuarantine plugin;

	public confirm(BungeeQuarantine pl) {
		plugin = pl;
	}
	@Override
	public boolean execute(CommandSender sender, String label, String cmd, LinkedList<String> args) {
		if (args.size()==0){
			ArrayList<String> command = ConfirmableCommandManager.commands.remove(sender);
			if (command !=null){
				if(command.size()==2){
					ProxyServer.getInstance().getPluginManager().dispatchCommand(sender, command.get(0));
					return true;
				}
				else{
					sender.sendMessage(new TextComponent("Internal error, args passed to confirmable command are incorrect.... report to developer"));
					//internal error, args passed to confirmable command are wrong....
					return true;
				}
			}else{
				sender.sendMessage(plugin.msg(plugin.messages.NOCOMMANDTOCONFIRM));
				//there was no command to confirm, or it timed out.
				return true;
			}
		}else{
			sender.sendMessage(plugin.msg(plugin.messages.TOOMANYARGS));
			return false;
		}
		
		
	}
}

