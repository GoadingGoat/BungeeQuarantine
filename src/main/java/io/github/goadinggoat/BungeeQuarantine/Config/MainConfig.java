package io.github.goadinggoat.BungeeQuarantine.Config;

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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.Comments;
import net.cubespace.Yamler.Config.YamlConfig;
import net.md_5.bungee.api.plugin.Plugin;


public class MainConfig extends YamlConfig {


	public MainConfig(Plugin plugin) {
        CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");

    }
	@Comment ("The name of the bungeecord server you would like to restrict players who have not accepted the rules too")
	public ArrayList<String> QuarantineServers = new ArrayList<String>(Arrays.asList("Hub"));
	
	@Comment ("Enable Global Rules. If false, will disable accepting of rules, and all quarantining/event management")
	public Boolean GlobalRules = true;
	
	@Comment("Wheter accepting rules should be enabled, 0 is disabled, 1 is global, and 2 is per server(but only if it has live rules defined. ")
	public int AcceptRules = 1;
	
	@Comment ("Should the rules be displayed on join?")
	public Boolean AcceptRulesOnJoin = true;
	
	@Comment("Enable Per Server Rules")
	public Boolean PerServerRules = true;
	
	@Comment("Confirmable command timeout in seconds")
	public int ConfiramableCommandTimeout = 20;
	
	@Comment ("Should chat events be cancelled before the rules are accepeted?")
	public Boolean CancelChat = true;
	
	
	@Comments ({"add commands (/ included) that you would like to be allowed before rules are accepted here",
		"NB: the command and it's args will be exempt, there is no provison for an"
		+ "exempting argumnets."})
	public ArrayList<String> EXEMPTCOMMANDS = new ArrayList<String>();
	
	@Comment("Alias another servers rules here")
	public Map<String,String> AliasedServers = new HashMap<String,String>();
	
	@Comment("Alias another servers rules here")
	public ArrayList<String> HiddenServers = new ArrayList<String>(); 
	
	
	
}