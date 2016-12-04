package io.github.goadinggoat.BungeeQuarantine.managers;

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
import java.util.HashMap;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Database.DataBase.SQLStates;

public class RulesManager {

	public static HashMap<String, ArrayList<String>> serverRules = new HashMap<>();

	private static BungeeQuarantine plugin;

	public static void setInstance(BungeeQuarantine instance) {
		plugin = instance;
	}

	public static ArrayList<String> getRules(String server) {
		return serverRules.get(server);
	}

	public static boolean setrules(String server, ArrayList<String> rules) {
		if (plugin.rulesDataBase.checkIfServerExists(server).equals(SQLStates.FALSE)) {
			if (plugin.rulesDataBase.createTable(server)) {
				int i = 0;
				for (String rule : rules) {
					plugin.rulesDataBase.setRule(server, rule, i);
					i++;
				}
			}else{
				plugin.getLogger().info("[Rules] Failed to create table for: "+server);
				return false;
			}
		}
		serverRules.put(server, rules);
		if (plugin.config.AcceptRules == 1) {
			if (server.equals("Global")) {
				if (plugin.playerDataBase.checkIfTableExists("Global") == SQLStates.TRUE) {
					if (!plugin.playerDataBase.setAllUnaccepted("Global")) {
						return false;
					}
				}
			}
		}
		if (plugin.config.AcceptRules == 2) {
			if (!server.equals("Global")) {
				if (plugin.playerDataBase.checkIfTableExists(server) == SQLStates.TRUE) {
					if (!plugin.playerDataBase.setAllUnaccepted(server)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static void load() {
		ArrayList<String> servers = plugin.rulesDataBase.listServers();
		if (servers != null) {
			for (String server : servers) {
				serverRules.put(server, plugin.rulesDataBase.getRules(server));
			}
		}
	}

	public static boolean drop(String server) {
		return plugin.rulesDataBase.dropServer(server);
	}
}
