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

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Commands.Args.Args;
import io.github.goadinggoat.BungeeQuarantine.managers.RulesYAMLManager;
import net.md_5.bungee.api.CommandSender;

public class append implements Args{

	private BungeeQuarantine plugin;

	public append(BungeeQuarantine pl) {
		plugin = pl;
	}
	@Override
	public boolean execute(CommandSender sender, String server, String cmd, LinkedList<String> args) {
		if(RulesYAMLManager.YAMLrules.containsKey(server)){
			String rule = "";
			for (String s : args) {
			    rule=rule.concat(s+" ");
			}
			rule=rule.trim();
			if(RulesYAMLManager.addRule(server,rule)){
				if (!RulesYAMLManager.SaveRules(server)){
					sender.sendMessage(plugin.msg(plugin.messages.FAILEDTOSAVERULES.replace("{server}", server)));
					return true;
				}else{
					sender.sendMessage(plugin.msg(plugin.messages.ADDEDRULETOYAMLFILE.replace("{server}", server)));
					return true;
				}
			}else{
				sender.sendMessage(plugin.msg(plugin.messages.FAILEDTOADDRULE.replace("{server}", server)));
				return true;
			}
		}else{
			sender.sendMessage(plugin.msg(plugin.messages.YAMLFILENOTEXISTS.replace("{server}", server)));
			return true;
		}	
	}
}
