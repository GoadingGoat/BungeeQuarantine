package io.github.goadinggoat.BungeeQuarantine.Commands.Util;


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
import java.util.ArrayList;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;

import io.github.goadinggoat.BungeeQuarantine.Database.DataBase.SQLStates;
import io.github.goadinggoat.BungeeQuarantine.managers.RulesYAMLManager;
import net.md_5.bungee.api.chat.TextComponent;

public class displayrules {

	private BungeeQuarantine plugin;

	public displayrules(BungeeQuarantine pl) {
		plugin = pl;
	}

	public TextComponent getFormattedRules(String server, boolean getLiveVersion) {
		if (getLiveVersion) {
			if (plugin.rulesDataBase.checkIfServerExists(server).equals(SQLStates.TRUE)) {
				return generateFormattedRules(server, plugin.rulesDataBase.getRules(server));
			} else {
				return null;
			}
		} else {
			if (RulesYAMLManager.exists(server)) {
				return generateFormattedRules(server, RulesYAMLManager.getRules(server));
			} else {
				return null;
			}
		}
	}

	private TextComponent generateFormattedRules(String server, ArrayList<String> rules) {
		if (rules == null){
			return new TextComponent("SQL ERROR during get rules!");
		}
		String message = "";
		if (server == "Global") {
			;
			message = message.concat(plugin.messages.GLOBALRULESHEADER);
		} else {
			message = message.concat(plugin.messages.SERVERRULESHEADER.replace("{server}", server));
		}
		message = message.concat("\n");
		int i = 1;
		for (String r : rules) {
			message = message.concat("&2" + i + ": &f");
			message = message.concat(r.toString());
			message = message.concat("\n");
			i++;
		}
		return plugin.msg(message);
	}
}
