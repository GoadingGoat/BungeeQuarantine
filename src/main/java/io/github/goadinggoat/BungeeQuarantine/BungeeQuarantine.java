package io.github.goadinggoat.BungeeQuarantine;

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
import java.util.Random;
import java.util.Set;

import io.github.goadinggoat.BungeeQuarantine.Commands.AcceptRules;
import io.github.goadinggoat.BungeeQuarantine.Commands.GlobalRules;
import io.github.goadinggoat.BungeeQuarantine.Commands.ServerRules;
import io.github.goadinggoat.BungeeQuarantine.Config.MainConfig;
import io.github.goadinggoat.BungeeQuarantine.Config.MessagesConfig;
import io.github.goadinggoat.BungeeQuarantine.Config.RulesConfig;
import io.github.goadinggoat.BungeeQuarantine.Database.PlayerDataBase;
import io.github.goadinggoat.BungeeQuarantine.Database.RulesDataBase;
import io.github.goadinggoat.BungeeQuarantine.Listeners.*;
import io.github.goadinggoat.BungeeQuarantine.managers.ConfigManager;
import io.github.goadinggoat.BungeeQuarantine.managers.ConfirmableCommandManager;
import io.github.goadinggoat.BungeeQuarantine.managers.PlayerManager;
import io.github.goadinggoat.BungeeQuarantine.managers.RulesManager;
import io.github.goadinggoat.BungeeQuarantine.managers.RulesYAMLManager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeQuarantine extends Plugin {

	public ProxyServer proxy;
	
	public  BungeeQuarantine instance;
	public ConfigManager configManager;
	public Set<String> players;
	public MainConfig config;
	public MessagesConfig messages;
	public Map<String, RulesConfig> rules;
	public Map<String, ServerInfo> servers;
	public ArrayList<String> serverNames;
	public ArrayList<String> hiddenServers;
	public HashMap<String,String> aliasedServers;
	public byte[] salt;
	public PlayerDataBase playerDataBase;
	public RulesDataBase rulesDataBase;

	public void onEnable() {
		instance = this;
		proxy = ProxyServer.getInstance();
		if (!loadConfig() || !loadServersFromConfig()) {
			getLogger().info("Disabling BungeeQuarantine...");
			return;
		}
		rulesDataBase = new RulesDataBase(instance);
		RulesManager.setInstance(instance);
		RulesManager.load();
		RulesYAMLManager.setInstance(instance);
		RulesYAMLManager.load();
		ConfirmableCommandManager.setInstance(instance);
		ProxyServer.getInstance().getPluginManager().registerCommand(instance, new GlobalRules(instance));
		if (instance.config.AcceptRules==1) {

			playerDataBase = new PlayerDataBase(instance);
			playerDataBase.createTable("player");
			PlayerManager.setInstance(instance);
			// generate salt
			salt = getSaltString().getBytes();
			ProxyServer.getInstance().getPluginManager().registerListener(instance, new PlayerListener(instance));
			ProxyServer.getInstance().getPluginManager().registerCommand(instance, new AcceptRules(instance));
		}
		
		if (instance.config.PerServerRules) {
			ProxyServer.getInstance().getPluginManager().registerCommand(instance, new ServerRules(instance));
		}
	}

	public void onDisable() {
		instance = null;
	}


	
	Boolean loadConfig() {
		configManager = new ConfigManager(instance);
		if (!configManager.InitConfig()) {
			getLogger().info("Failed to initialise config!");
			return false;
		}
		config = configManager.getConfigInstance();
		messages = configManager.getMessagesInstance();
		//validate Aliases here, adding them if not valid
		//Validate HiddenServers, skip them if not valid
		aliasedServers = new HashMap<String,String>();
		hiddenServers = new ArrayList<String>();
		return true;
	}

	Boolean loadServersFromConfig() {
		serverNames = new ArrayList<String>();
		if (instance.config.GlobalRules) {
			serverNames.add("Global");
		}
		if (instance.config.PerServerRules) {
			servers = ProxyServer.getInstance().getServers();
			for (Entry<String, ServerInfo> entry : servers.entrySet()) {
				serverNames.add(entry.getKey());
			}
		}
		return true;
	}



	public Boolean reload() {
		if (loadConfig() && loadServersFromConfig()) {
			return true;
		}
		return false;
	}

	protected String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 32) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	public boolean checkPlayerHasAccepted(ProxiedPlayer player) {
		if (players.contains(player.getUniqueId().toString())) {
			return true;
		}
		return false;
	}

	public TextComponent msg(String string) {
		return new TextComponent(ChatColor.translateAlternateColorCodes((char) '&', string));

	}

}
