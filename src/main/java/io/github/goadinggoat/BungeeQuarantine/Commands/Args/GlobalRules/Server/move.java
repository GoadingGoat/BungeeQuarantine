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

public class move implements Args {

	private BungeeQuarantine plugin;

	public move(BungeeQuarantine pl) {
		plugin = pl;
	}

	public enum Direction {
		UP, DOWN
	}

	@Override
	public boolean execute(CommandSender sender, String server, String cmd, LinkedList<String> args) {
		if (args.size() < 3) {
			sender.sendMessage(plugin.msg(plugin.messages.TOOFEWARGS));
			return false;
		} else if (args.size() == 3) {
			if (RulesYAMLManager.YAMLrules.containsKey(server)) {
				int ruleNumtoMove;
				int newPos;
				Direction dir;
				try {
					ruleNumtoMove = Integer.parseInt(args.pollFirst());
				} catch (NumberFormatException e) {
					sender.sendMessage(plugin.msg(plugin.messages.INVALIDNUMBER));
					return false;
				}
				ruleNumtoMove -= 1;
				if (ruleNumtoMove > RulesYAMLManager.getNumRules(server) - 1 || ruleNumtoMove < 0) {
					sender.sendMessage(plugin.msg(plugin.messages.NUMBEROUTOFRANGE));
					return false;
				}
				if (args.getFirst().equalsIgnoreCase("up")) {
					dir = Direction.UP;
					args.remove();
				} else if (args.getFirst().equalsIgnoreCase("down")) {
					dir = Direction.DOWN;
					args.remove();
				} else {
					sender.sendMessage(plugin.msg(plugin.messages.INVALIDARG));
					return false;
				}
				try {
					newPos = Integer.parseInt(args.pollFirst());
				} catch (NumberFormatException e) {
					sender.sendMessage(plugin.msg(plugin.messages.INVALIDNUMBER));
					return false;
				}
				if (dir == Direction.UP) {
					newPos = ruleNumtoMove - newPos;
				} else {
					newPos = ruleNumtoMove + newPos;
				}
				if (newPos > RulesYAMLManager.getNumRules(server) - 1) {
					newPos = RulesYAMLManager.getNumRules(server) - 1;
				}
				if (newPos < 0) {
					newPos = 0;
				}
				RulesYAMLManager.moveRule(server, ruleNumtoMove, newPos);
				sender.sendMessage(plugin.msg(plugin.messages.MOVEDRULE.replace("{server}",server).replace("{pos}", String.valueOf(ruleNumtoMove)).replace("{num}", String.valueOf(newPos))));
				return true;
			} else {
				sender.sendMessage(plugin.msg(plugin.messages.YAMLFILENOTEXISTS.replace("{server}", server)));
				return true;
			}

		} else {
			sender.sendMessage(plugin.msg(plugin.messages.TOOMANYARGS));
			return false;
		}

	}

}
