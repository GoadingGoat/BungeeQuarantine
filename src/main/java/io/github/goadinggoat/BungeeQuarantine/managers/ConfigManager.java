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

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.Config.MainConfig;
import io.github.goadinggoat.BungeeQuarantine.Config.MessagesConfig;

import net.cubespace.Yamler.Config.InvalidConfigurationException;

public class ConfigManager {
	private BungeeQuarantine plugin;
	private MainConfig config = null;
	private MessagesConfig messages = null;
	
	public ConfigManager(BungeeQuarantine pl){
		plugin = pl;
	}

	public boolean InitConfig(){
		try {
		    config = new MainConfig(plugin);
		    messages = new MessagesConfig(plugin);
		    config.init();
		    messages.init();
		    
		} catch(InvalidConfigurationException ex) {
		    System.out.println("Your Config YML is incorrect");
		    ex.printStackTrace();
		    return false;
		}
		return true;
	}
	
	public MainConfig getConfigInstance(){
		return config;
	}
	
	public MessagesConfig getMessagesInstance(){
		return messages;
	}
	

}
