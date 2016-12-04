package io.github.goadinggoat.BungeeQuarantine.Objects;

/**
 * 
 * BungeeQuarantine Copyright (C) 2016 Sean Fleck email: goadinggoat@gmail.com
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

import java.util.HashMap;

public class GoatPlayer {
	private String uuid;
	public HashMap<String, Boolean> servers = new HashMap<String, Boolean>();

	public GoatPlayer(String uuid) {
		this.uuid = uuid;
	}

	public String getUUID() {
		return uuid;
	}
	
	public void setPlayerAcceptedRules(String server, boolean accepted) {
		servers.put(server, accepted);
	}

}
