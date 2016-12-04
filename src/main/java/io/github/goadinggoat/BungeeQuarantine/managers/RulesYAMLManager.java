package io.github.goadinggoat.BungeeQuarantine.managers;


import java.io.File;
import java.nio.file.Paths;

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
import java.util.Map;
import java.util.Map.Entry;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Config.MessagesConfig;
import io.github.goadinggoat.BungeeQuarantine.Config.RulesConfig;
import net.cubespace.Yamler.Config.InvalidConfigurationException;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class RulesYAMLManager {
	private static BungeeQuarantine plugin;
	MessagesConfig messages = null;
	public static Map<String,RulesConfig> YAMLrules;
	
	public RulesYAMLManager(){
	}

	public static void setInstance(BungeeQuarantine instance){
		plugin = instance;
	}
	
	public static void load(){
		YAMLrules = new HashMap<String, RulesConfig>();
		if(plugin.config.GlobalRules){
			if(fileExists("Global")){
				InitRules("Global");
			}
		}
		if(plugin.config.PerServerRules){
			for (Entry<String, ServerInfo> serverInfo : ProxyServer.getInstance().getServers().entrySet()){
				if(fileExists(serverInfo.getKey())){
					InitRules(serverInfo.getKey());
				}
			}
		}
	}
	
	public static boolean InitRules(String server){
		try {
		    YAMLrules.put(server,new RulesConfig(plugin,server));
		    YAMLrules.get(server).init();
		    return true;
		    
		} catch(InvalidConfigurationException ex) {
		    System.out.println("Your "+server+".YML is incorrect");
		    ex.printStackTrace();
		    return false;
		}
	}
	
	public static boolean SaveRules(String server){
		try {
			YAMLrules.get(server).save();
			return true;
		} catch(InvalidConfigurationException ex) {
		    System.out.println("Error in saving the "+server+".YML");
		    ex.printStackTrace();
		    return false;
		}
	}
	
	public static boolean addRule(String server, String rule) {
		return YAMLrules.get(server).Rules.add(rule);

	}
	
	public static void clearRules(String server) {
		YAMLrules.get(server).Rules.clear();
	}
	
	public static ArrayList<String> getRules(String server) {
		return YAMLrules.get(server).Rules;
	}

	public static void insertRule(String server, String rule, int pos) {
		YAMLrules.get(server).Rules.add(pos, rule);
	}

	public static void moveRule(String server, int pos, int newPos) {
		String rule = YAMLrules.get(server).Rules.get(pos);
		YAMLrules.get(server).Rules.remove(pos);
		YAMLrules.get(server).Rules.add(newPos, rule);
	}

	public static int getNumRules(String server) {
		return YAMLrules.get(server).Rules.size();
	}

	public static void removeRule(String server, int pos) {
		YAMLrules.get(server).Rules.remove(pos);
	}

	public static Boolean exists(String server) {
		if (YAMLrules.get(server) != null) {
			return true;
		}
		return false;
	}
	
	public static Boolean fileExists(String server) {
		File file = new File(plugin.getDataFolder(),Paths.get("Rules", server + ".yml").toString());
		if(file.exists()){
			return true;
		}
		return false;
	}
	
}
