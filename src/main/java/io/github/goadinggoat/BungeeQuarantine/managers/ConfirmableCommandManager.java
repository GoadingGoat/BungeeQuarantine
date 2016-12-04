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

import net.md_5.bungee.api.CommandSender;

public class ConfirmableCommandManager {
	static BungeeQuarantine plugin;
	
	public static HashMap<CommandSender,ArrayList<String>> commands = new HashMap<CommandSender,ArrayList<String>>();
	
	public static void setInstance(BungeeQuarantine instance){
		plugin = instance;
	}
	
	
	

}
