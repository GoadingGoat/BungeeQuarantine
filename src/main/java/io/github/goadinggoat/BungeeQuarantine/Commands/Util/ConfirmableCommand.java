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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.github.goadinggoat.BungeeQuarantine.BungeeQuarantine;
import io.github.goadinggoat.BungeeQuarantine.managers.ConfirmableCommandManager;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;


public class ConfirmableCommand{
	
	private BungeeQuarantine plugin;

	
	
	public ConfirmableCommand(BungeeQuarantine pl,CommandSender sender,String cmd, Integer time ,TimeUnit unit) {
		this.plugin = pl;
		String timestamp = new Timestamp(System.currentTimeMillis()).toString();
		ArrayList<String> cmdandtime = new ArrayList<String> (Arrays.asList(cmd,timestamp));
		ConfirmableCommandManager.commands.put(sender, cmdandtime);
		
		ProxyServer.getInstance().getScheduler().schedule(plugin, new Runnable() {
			public void run() {
				ConfirmableCommandManager.commands.remove(sender,cmdandtime);
			}
		}, time, unit);
	}




	
}
